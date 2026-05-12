package org.sento.platform.database.model;

import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Builder
@Getter
public class PaginationModel<M> {
    private final List<M> items;
    private final String nextPageToken;
    private final String previousPageToken;
    private final boolean hasNext;
    private final boolean hasPrevious;

    public <R> PaginationModel<R> map(Function<? super M, ? extends R> mapper) {
        List<R> mappedItems = items == null
            ? List.of()
            : items.stream()
            .map(mapper)
            .collect(Collectors.toList());

        return PaginationModel.<R>builder()
            .items(mappedItems)
            .nextPageToken(nextPageToken)
            .previousPageToken(previousPageToken)
            .hasNext(hasNext)
            .hasPrevious(hasPrevious)
            .build();
    }

    public <R> Mono<PaginationModel<R>> mapAsync(
        Function<? super M, ? extends Mono<? extends R>> mapper
    ) {
        return Flux.fromIterable(items == null ? List.of() : items)
            .flatMap(mapper)
            .collectList()
            .map(mappedItems ->
                PaginationModel.<R>builder()
                    .items(mappedItems)
                    .nextPageToken(nextPageToken)
                    .previousPageToken(previousPageToken)
                    .hasNext(hasNext)
                    .hasPrevious(hasPrevious)
                    .build()
            );
    }

    public <R> PaginationModel<R> withItems(List<R> newItems) {
        return PaginationModel.<R>builder()
            .items(newItems)
            .nextPageToken(nextPageToken)
            .previousPageToken(previousPageToken)
            .hasNext(hasNext)
            .hasPrevious(hasPrevious)
            .build();
    }
}
