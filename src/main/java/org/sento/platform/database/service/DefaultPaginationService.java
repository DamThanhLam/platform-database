package org.sento.platform.database.service;

import org.sento.platform.database.model.PaginationModel;
import org.sento.platform.database.pagination.DefaultPaginationFilter;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface DefaultPaginationService<M, R, F extends DefaultPaginationFilter> {

    Mono<PaginationModel<M>> getNextPagination(
        F filter,
        String nextPageToken,
        Pageable pageable
    );

    Mono<PaginationModel<M>> getPreviousPagination(
        F filter,
        String previousPageToken,
        Pageable pageable
    );
}
