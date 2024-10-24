package com.pjs.urlshortenerservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void testRedis() {
        redisTemplate.opsForValue().set("email", "parth@gmail.com");
        Object name = redisTemplate.opsForValue().get("email");
        Assertions.assertEquals("parth@gmail.com", name);
    }
}
