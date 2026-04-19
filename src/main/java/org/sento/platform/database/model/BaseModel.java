package org.sento.platform.database.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public abstract class BaseModel {
    private String id;
    private long createdAt;
    private long updatedAt;
    private String createdBy;
    private String updatedBy;
}
