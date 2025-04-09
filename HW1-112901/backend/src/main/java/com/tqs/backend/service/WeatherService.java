package com.tqs.backend.service;

import com.tqs.backend.entity.WeatherForecast;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Service
public class WeatherService {

    private static class CachedForecast {
        WeatherForecast forecast;
        Instant timestamp;

        CachedForecast(WeatherForecast forecast, Instant timestamp) {
            this.forecast = forecast;
            this.timestamp = timestamp;
        }
    }

    private final Map<LocalDate, CachedForecast> cache = new HashMap<>();
    private final Duration ttl = Duration.ofHours(1);
    private final RestTemplate restTemplate;

    private final String BASE_URL = "https://api.open-meteo.com/v1/forecast";
    private final double LAT = 40.64;
    private final double LON = -8.65;

    // Importante para as requisições HTTP à API externa do tempo
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherForecast getForecastForDate(LocalDate date) {
        if (cache.containsKey(date)) {
            CachedForecast cached = cache.get(date);
            if (cached.timestamp.plus(ttl).isAfter(Instant.now())) {
                return cached.forecast;
            }
        }

        WeatherForecast forecast = fetchForecastFromAPI(date);
        cache.put(date, new CachedForecast(forecast, Instant.now()));
        return forecast;
    }

    private WeatherForecast fetchForecastFromAPI(LocalDate date) {
        String url = BASE_URL +
                "?latitude=" + LAT +
                "&longitude=" + LON +
                "&daily=temperature_2m_max,temperature_2m_min,precipitation_sum" +
                "&timezone=auto";

        Map response = restTemplate.getForObject(url, Map.class);

        List<String> dates = (List<String>) ((Map) response.get("daily")).get("time");
        List<Double> tempsMax = (List<Double>) ((Map) response.get("daily")).get("temperature_2m_max");
        List<Double> tempsMin = (List<Double>) ((Map) response.get("daily")).get("temperature_2m_min");
        List<Double> precip = (List<Double>) ((Map) response.get("daily")).get("precipitation_sum");

        for (int i = 0; i < dates.size(); i++) {
            LocalDate forecastDate = LocalDate.parse(dates.get(i));
            if (forecastDate.equals(date)) {
                WeatherForecast wf = new WeatherForecast();
                wf.setDate(forecastDate);
                wf.setMaxTemp(tempsMax.get(i));
                wf.setMinTemp(tempsMin.get(i));
                wf.setPrecipitation(precip.get(i));
                return wf;
            }
        }

        WeatherForecast fallback = new WeatherForecast();
        fallback.setDate(date);
        fallback.setMaxTemp(0);
        fallback.setMinTemp(0);
        fallback.setPrecipitation(0);
        return fallback;
    }
}
