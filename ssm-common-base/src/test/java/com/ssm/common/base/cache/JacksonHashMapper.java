package com.ssm.common.base.cache;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.hash.HashMapper;

import java.util.Map;

/**
 * @see HashMapper
 */
public class JacksonHashMapper<T> implements HashMapper<T, String, Object> {

    private final ObjectMapper mapper;
    private final JavaType userType;
    private final JavaType mapType = TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class);

    public JacksonHashMapper(Class<T> type) {
        this(type, new ObjectMapper());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public JacksonHashMapper(Class<T> type, ObjectMapper mapper) {
        this.mapper = mapper;
        this.userType = TypeFactory.defaultInstance().constructType(type);
    }

    @SuppressWarnings("unchecked")
    public T fromHash(Map<String, Object> hash) {
        return (T) mapper.convertValue(hash, userType);
    }

    public Map<String, Object> toHash(T object) {
        return mapper.convertValue(object, mapType);
    }
}
