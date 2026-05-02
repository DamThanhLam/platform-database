package org.sento.platform.database.pagination;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class PaginationTokenCodecImplement implements PaginationTokenCodec {

    private final ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule());;

    @Override
    public String encode(Map<String, Object> keyToken) {
        try {
            if (keyToken == null) {
                return null;
            }
            String json = objectMapper.writeValueAsString(keyToken);
            return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(json.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to encode pagination token", e);
        }
    }

    @Override
    public Map<String, Object> decode(String token) {
        if (token == null || token.isEmpty()) {
            return new HashMap<>();
        }
        try {
            return objectMapper.readValue(
                Base64.getDecoder()
                    .decode(token.getBytes(StandardCharsets.UTF_8)),
                new TypeReference<>() {}
            );
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
}