package org.sento.platform.database.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class MongoAuditHelper {

    private final ReactiveAuditorAware<String> auditorAware;

    public Mono<Update> applyAudit(Update update) {
        return auditorAware.getCurrentAuditor()
            .map(auditor -> {
                update.set("updated_at", Instant.now());
                update.set("updated_by", auditor);
                return update;
            });
    }
}