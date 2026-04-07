package org.sento.platform.database.autoconfigure;

import org.sento.platform.database.config.MongoAuditingConfig;
import org.sento.platform.database.config.MongoProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties(MongoProperties.class)
@ConditionalOnProperty(prefix = "platform.mongodb", name = "enabled", havingValue = "true", matchIfMissing = true)
@Import(MongoAuditingConfig.class)
public class ReactiveMongoDatabaseAutoConfiguration {
}