package org.sento.platform.database.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
public class FileDocument {
    private String bucket;

    private String key;

    @Field("e_tag")
    private String eTag;

    private long size;

    @Field("content_type")
    private String contentType;

    @Field("checksum_strategy")
    private String checksumStrategy;

    @Field("local_sha_256_hex")
    private String localSha256Hex;
}
