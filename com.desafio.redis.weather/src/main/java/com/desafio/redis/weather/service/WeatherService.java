package com.desafio.redis.weather.service;

import com.desafio.redis.weather.exception.ErrorApiWeather;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService {

    private final RestClient restClient;
    private final RedisService redisService;

    @Value("${api.key.weather}")
    private String API_KEY;

    public String search(String location, String country) {
        String cacheKey = location + country;
        String cachedWeathers = redisService.buscar(cacheKey);

        if (cachedWeathers == null) {
            String result = fetchWeather(location, country);
            redisService.salvar(cacheKey, result);
            return result;
        }

        return cachedWeathers;
    }

    private String fetchWeather(String location, String country) {
        try {
            return restClient.get()
                    .uri("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"+location+","+country+"?key="+API_KEY)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            throw new ErrorApiWeather(e.getMessage());
        }
    }

}

