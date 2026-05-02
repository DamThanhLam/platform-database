package org.sento.platform.database.pagination;

import java.util.Map;

public interface PaginationTokenCodec {

    Map<String, Object> decode(String token);
    String encode(Map<String, Object> keyToken);
}
