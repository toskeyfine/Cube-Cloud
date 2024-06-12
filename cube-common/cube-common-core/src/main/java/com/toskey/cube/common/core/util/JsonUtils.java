package com.toskey.cube.common.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JsonUtils
 *
 * @author toskey
 * @version 1.0
 * @since 2024/6/5
 */
@UtilityClass
public final class JsonUtils {

    private static ObjectMapper objectMapper = SpringContextHolder.getBean(ObjectMapper.class);

    private static final String JSON_OBJECT_PATTERN = "^\\{.*\\}$";
    private static final String JSON_ARRAY_PATTERN = "^\\[.*\\]$";

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
    }

    public <T> T toJavaObject(String json, Class<T> cls) {
        if (StringUtils.isBlank(json) || cls == null) {
            return null;
        }
        try {
            return objectMapper.readValue(json, cls);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T toJavaObject(String json, TypeReference<T> typeRef) {
        if (StringUtils.isBlank(json) || typeRef == null) {
            return null;
        }
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Map toMap(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <K, V> Map<K, V> toMap(String json, Class<K> kc, Class<V> vc) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, kc, vc);
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> toList(String json, TypeReference<List<T>> typeRef) {
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> toList(String json, Class<T> clazz) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            throw  new RuntimeException(e);
        }
    }

    public JsonNode toTree(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJsonPretty(Object obj) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isJson(String json) {
        return isJsonObject(json) && isJsonArray(json);
    }

    public boolean isJsonObject(String json) {
        return StringUtils.isNotBlank(json) && json.matches(JSON_OBJECT_PATTERN);
    }

    public boolean isJsonArray(String json) {
        return StringUtils.isNotBlank(json) && json.matches(JSON_ARRAY_PATTERN);
    }

    public JsonBuilder builder() {
        return new JsonBuilder();
    }

    public class JsonBuilder {
        private final Map<String, Object> map = new HashMap<>();

        private Boolean pretty = Boolean.FALSE;

        private JsonBuilder() {}

        public JsonBuilder put(String key, Object val) {
            map.put(key, val);
            return this;
        }

        public JsonBuilder pretty(boolean enabled) {
            this.pretty = enabled;
            return this;
        }

        public String build() {
            try {
                if (this.pretty) {
                    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.map);
                }
                return objectMapper.writeValueAsString(this.map);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
