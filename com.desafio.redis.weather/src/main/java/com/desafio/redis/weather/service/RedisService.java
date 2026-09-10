package com.desafio.redis.weather.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    private final Duration DURATION = Duration.ofHours(12);

    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void salvar(String chave, String valor) {
        redisTemplate.opsForValue().set(chave, valor, DURATION);
    }

    public String buscar(String chave) {
        return redisTemplate.opsForValue().get(chave);
    }
}