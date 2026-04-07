package org.sento.platform.database.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "platform.mongodb")
@Getter
@Setter
public class MongoProperties {

    /**
     * Bật/tắt bộ starter này.
     */
    private boolean enabled = true;

    /**
     * Tên database mặc định nếu service không set spring.data.mongodb.database.
     */
    private String database;

    /**
     * Bật auditing createdAt/updatedAt.
     */
    private boolean auditingEnabled = true;
}