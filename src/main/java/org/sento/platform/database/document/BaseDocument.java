package org.sento.platform.database.document;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public abstract class BaseDocument {

    @Id
    private String id;

    @CreatedDate
    @Field("created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private Instant updatedAt;

    @CreatedBy
    @Field("created_by")
    private String createdBy;

    @LastModifiedBy
    @Field("updated_by")
    private String updatedBy;

    public Instant getCreatedAt() {
        if (this.createdAt == null) {
            return Instant.ofEpochSecond(0);
        }
        return this.createdAt;
    }

    public Instant getUpdatedAt() {
        if (this.updatedAt == null) {
            return Instant.ofEpochSecond(0);
        }
        return this.updatedAt;
    }
}