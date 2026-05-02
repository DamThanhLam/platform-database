package org.sento.platform.database.service;

import lombok.AllArgsConstructor;
import org.sento.platform.database.model.PaginationModel;
import org.sento.platform.database.pagination.DefaultPaginationFilter;
import org.sento.platform.database.repository.DefaultPaginationRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@AllArgsConstructor
public abstract class AbstractPaginationService<M, R, F extends DefaultPaginationFilter>
    implements DefaultPaginationService<M, R, F> {

    private final Logger logger = Logger.getLogger(AbstractPaginationService.class.getName());
    private final DefaultPaginationRepository<R, F> repository;

    @Override
    public Mono<PaginationModel<M>> getNextPagination(
        F filter,
        String nextPageToken,
        Pageable pageable
    ) {
        logger.info("processing next pagination");
        return repository.getNextPagination(filter, nextPageToken, pageable)
            .map(items -> items.map(this::converterToModel));
    }

    @Override
    public Mono<PaginationModel<M>> getPreviousPagination(
        F filter,
        String previousPageToken,
        Pageable pageable
    ) {
        logger.info("processing previous pagination");
        return repository.getPreviousPagination(filter, previousPageToken, pageable)
            .map(items -> items.map(this::converterToModel));
    }

    protected abstract M converterToModel(R r);
}
