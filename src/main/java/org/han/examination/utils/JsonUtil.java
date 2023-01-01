package org.han.examination.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String serialization(Object object) {
        String value;
        try {
            value = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return value;
    }
}
