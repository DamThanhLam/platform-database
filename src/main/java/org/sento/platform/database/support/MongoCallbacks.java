package org.sento.platform.database.support;

import org.springframework.data.mongodb.core.mapping.event.ReactiveBeforeConvertCallback;
import reactor.core.publisher.Mono;

/**
 * Place holder so other services can create their own callbacks,
 * or you can extend this library later.
 */
public interface MongoCallbacks<T> extends ReactiveBeforeConvertCallback<T> {

    @Override
    default Mono<T> onBeforeConvert(T entity, String collection) {
        return Mono.just(entity);
    }
}
