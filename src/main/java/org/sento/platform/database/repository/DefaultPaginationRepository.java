package org.sento.platform.database.repository;

import org.sento.platform.database.model.PaginationModel;
import org.sento.platform.database.pagination.DefaultPaginationFilter;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface DefaultPaginationRepository<R, F extends DefaultPaginationFilter> {

    Mono<PaginationModel<R>> getNextPagination(
        F filter,
        String nextPageToken,
        Pageable pageable
    );

    Mono<PaginationModel<R>> getPreviousPagination(
        F filter,
        String previousPageToken,
        Pageable pageable
    );
}
