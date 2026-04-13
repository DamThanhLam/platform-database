package org.sento.platform.database.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "platform.mongodb")
@Getter
@Setter
public class MongoProperties {

    private boolean enabled = true;

    private boolean auditingEnabled = true;
}