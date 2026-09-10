package com.desafio.redis.weather.controller;

import com.desafio.redis.weather.service.WeatherService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/weather")
@RateLimiter(name = "search")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public String search(@RequestParam String location, @RequestParam String country) {
        return weatherService.search(location, country);
    }

}
