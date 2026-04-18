package org.sento.platform.database.model;

public abstract class AuditableModel {
    private long createdAt;
    private long updatedAt;
    private String createdBy;
    private String updatedBy;
}
