package org.sento.platform.database.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import reactor.core.publisher.Mono;

@Configuration
@EnableReactiveMongoAuditing
@ConditionalOnProperty(
    prefix = "platform.mongodb",
    name = "auditing-enabled",
    havingValue = "true",
    matchIfMissing = true
)
public class MongoAuditingConfig {

    /**
     * By default, it returns anonymous if the service hasn't defined an auditor itself.
     * Other services can override this bean.
     */
    @Bean
    public ReactiveAuditorAware<String> reactiveAuditorAware() {
        return () -> Mono.just("system");
    }
}