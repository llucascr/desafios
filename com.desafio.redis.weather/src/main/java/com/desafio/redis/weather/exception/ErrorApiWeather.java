package com.desafio.redis.weather.exception;

public class ErrorApiWeather extends RuntimeException {
    public ErrorApiWeather(String message) {
        super(message);
    }
}
