package org.sento.platform.database.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateFileCommand {
    private String bucket;
    private String key;
    private String eTag;
    private long size;
    private String contentType;
    private String checksumStrategy;
    private String localSha256Hex;
}
