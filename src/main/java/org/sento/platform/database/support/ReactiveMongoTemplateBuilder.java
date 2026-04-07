package org.sento.platform.database.support;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;

public final class ReactiveMongoTemplateBuilder {

    private ReactiveMongoTemplateBuilder() {
    }

    public static ReactiveMongoTemplate build(
        ReactiveMongoDatabaseFactory factory,
        MongoConverter converter
    ) {
        return new ReactiveMongoTemplate(factory, converter);
    }
}