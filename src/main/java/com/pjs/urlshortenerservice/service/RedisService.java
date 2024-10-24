package com.pjs.urlshortenerservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjs.urlshortenerservice.exception.InvalidInputException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> T get(String key, Class<T> con) {
        Object o = redisTemplate.opsForValue().get(key);
        ObjectMapper mapper = new ObjectMapper();
        log.info("Info log from sl4j");
        try {
            return o != null ? mapper.readValue(o.toString(), con) : null;
        } catch (JsonProcessingException e) {
            throw new InvalidInputException("Alias is not correct");
        }
    }

    public void set(String key, Object value, Long ttl) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonValue;
        try {
            jsonValue = mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new InvalidInputException(e);
        }
        redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
    }
}
