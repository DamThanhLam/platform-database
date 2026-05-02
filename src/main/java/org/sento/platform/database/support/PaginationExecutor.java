package org.sento.platform.database.support;

import lombok.experimental.UtilityClass;
import org.sento.platform.database.model.PaginationModel;
import org.sento.platform.database.pagination.DefaultPaginationFilter;
import org.sento.platform.database.service.DefaultPaginationService;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

@UtilityClass
public class PaginationExecutor {

    public <M, R, F extends DefaultPaginationFilter> Mono<PaginationModel<M>> getPagination(
        DefaultPaginationService<M, R, F> service,
        F filter,
        String nextPageToken,
        String prevPageToken,
        Pageable pageable
    ) {
        return prevPageToken != null
            ? service.getPreviousPagination(filter, prevPageToken, pageable)
            : service.getNextPagination(filter, nextPageToken, pageable);
    }
}
