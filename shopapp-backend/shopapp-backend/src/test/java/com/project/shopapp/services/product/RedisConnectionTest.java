package com.project.shopapp.services.product;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisConnectionTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testConnection() {
        try {
            redisTemplate.opsForValue().set("testKey", "testValue");
            String value = (String) redisTemplate.opsForValue().get("testKey");
            assertEquals("testValue", value);
        } catch (Exception e) {
            fail("Failed to connect to Redis: " + e.getMessage());
        }
    }
}