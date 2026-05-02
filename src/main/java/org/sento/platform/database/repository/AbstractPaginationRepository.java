package org.sento.platform.database.repository;

import org.sento.platform.database.model.PaginationModel;
import org.sento.platform.database.pagination.DefaultPaginationFilter;
import org.sento.platform.database.pagination.PaginationTokenCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public abstract class AbstractPaginationRepository<R, F extends DefaultPaginationFilter>
    implements DefaultPaginationRepository<R, F> {

    @Autowired
    private ReactiveMongoTemplate template;

    @Autowired
    private PaginationTokenCodec tokenCodec;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public Mono<PaginationModel<R>> getNextPagination(
        F filter,
        String nextPageToken,
        Pageable pageable
    ) {
        return doPagination(filter, nextPageToken, pageable, true);
    }

    @Override
    public Mono<PaginationModel<R>> getPreviousPagination(
        F filter,
        String previousPageToken,
        Pageable pageable
    ) {
        return doPagination(filter, previousPageToken, pageable, false);
    }

    private Mono<PaginationModel<R>> doPagination(
        F filter,
        String pageToken,
        Pageable pageable,
        boolean isNextPage
    ) {
        List<Sort.Order> orders = new ArrayList<>();
        pageable.getSort().forEach(orders::add);
        Query query = buildQuery(filter, pageToken, pageable, orders, isNextPage);
        log.info("query={}", query);
        return execute(query)
            .collectList()
            .flatMap(items -> {
                if (items.isEmpty()) {
                    return Mono.just(
                        PaginationModel.<R>builder().build()
                    );
                }
                log.info("result items={}", items.size());
                R firstItem = items.get(0);
                R lastItem = items.get(items.size() - 1);
                String previousToken = getPageToken(firstItem, orders);
                String nextToken = getPageToken(lastItem, orders);
                Pageable pageableCheck = PageRequest.of(
                    0,
                    1,
                    pageable.getSort()
                );
                Mono<Boolean> hasPrevious = execute(buildQuery(
                    filter,
                    previousToken,
                    pageableCheck,
                    orders,
                    false
                ))
                    .hasElements();
                Mono<Boolean> hasNext = execute(buildQuery(
                    filter,
                    nextToken,
                    pageableCheck,
                    orders,
                    false
                ))
                    .hasElements();
                return Mono.zip(hasPrevious, hasNext)
                    .map(mapper ->
                        PaginationModel.<R>builder()
                            .hasPrevious(mapper.getT1())
                            .hasNext(mapper.getT2())
                            .items(items)
                            .nextPageToken(nextToken)
                            .previousPageToken(previousToken)
                            .build()
                    );
            });
    }

    private Flux<R> execute(
        Query query
    ) {
        return template.find(query, getResultType());
    }

    private Query buildQuery(
        F filter,
        String pageToken,
        Pageable pageable,
        List<Sort.Order> orders,
        boolean isNextPage
    ) {
        Assert.notNull(pageable, "pageable must not be null");
        log.info("buildQuery: pageToken={}", pageToken);
        log.info("buildQuery: orders={}", orders);
        log.info("buildQuery: pageable={}", pageable);
        Query query = new Query();

        for (CriteriaDefinition criteria : filter.queryOperator()) {
            query.addCriteria(criteria);
        }
        if (StringUtils.hasText(pageToken)) {
            query.addCriteria(generateCriteriaPageToken(pageToken, orders, isNextPage));
        }
        return query
            .limit(pageable.getPageSize())
            .skip(pageable.getOffset())
            .with(pageable.getSort().reverse());
    }

    private String getPageToken(R result, List<Sort.Order> orders) {
        Map<String, Object> keyToken = new HashMap<>();
        for (Sort.Order order : orders) {
            String property = order.getProperty();
            try {
                Method getter = getResultType().getMethod(
                    "get" + StringUtils.capitalize(property)
                );
                keyToken.put(property, getter.invoke(result));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                log.error("Cannot get value for property: {}", property, e);
            }
        }
        return tokenCodec.encode(keyToken);
    }

    private Criteria generateCriteriaPageToken(
        String pageToken,
        List<Sort.Order> orders,
        boolean isNextPage
    ) {
        Map<String, Object> keyToken = tokenCodec.decode(pageToken);
        Criteria criteria = new Criteria();
        for (Sort.Order order : orders) {
            String property = order.getProperty();
            Object value = keyToken.get(property);
            criteria = criteria.and(property);
            if (isNextPage && order.isDescending()) {
                criteria = criteria.lte(value);
            } else {
                criteria = criteria.gte(value);
            }
        }
        return criteria;
    }

    private Class<R> getResultType() {
        return (Class<R>)
            ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
}
