package com.tqs.backend;

import com.tqs.backend.entity.WeatherForecast;
import com.tqs.backend.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    void testGetForecastForDate_ReturnsExpectedForecast() {
        LocalDate date = LocalDate.of(2025, 4, 14);

        Map<String, Object> daily = new HashMap<>();
        daily.put("time", List.of("2025-04-13", "2025-04-14"));
        daily.put("temperature_2m_max", List.of(18.0, 22.0));
        daily.put("temperature_2m_min", List.of(10.0, 12.0));
        daily.put("precipitation_sum", List.of(1.0, 0.0));

        Map<String, Object> response = new HashMap<>();
        response.put("daily", daily);

        when(restTemplate.getForObject(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.eq(Map.class)))
                .thenReturn(response);

        WeatherForecast forecast = weatherService.getForecastForDate(date);

        assertThat(forecast.getDate()).isEqualTo(date);
        assertThat(forecast.getMaxTemp()).isEqualTo(22.0);
        assertThat(forecast.getMinTemp()).isEqualTo(12.0);
        assertThat(forecast.getPrecipitation()).isEqualTo(0.0);
    }
}
