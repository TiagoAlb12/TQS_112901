package com.tqs.backend;

import com.tqs.backend.controller.MealController;
import com.tqs.backend.entity.Meal;
import com.tqs.backend.entity.WeatherForecast;
import com.tqs.backend.model.MealWithWeatherDTO;
import com.tqs.backend.repository.MealRepository;
import com.tqs.backend.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MealControllerTest {

    private MealRepository mealRepository;
    private WeatherService weatherService;
    private MealController controller;

    @BeforeEach
    void setUp() {
        mealRepository = mock(MealRepository.class);
        weatherService = mock(WeatherService.class);
        controller = new MealController();

        injectField(controller, "mealRepository", mealRepository);
        injectField(controller, "weatherService", weatherService);
    }

    @Test
    void testGetMealsWithWeather() {
        Meal meal = new Meal();
        meal.setId(1L);
        meal.setDate(LocalDate.of(2025, 4, 10));
        meal.setDescription("Arroz de pato");

        WeatherForecast forecast = new WeatherForecast();
        forecast.setMaxTemp(22.0);
        forecast.setMinTemp(12.0);
        forecast.setPrecipitation(0.0);

        when(mealRepository.findByRestaurantId(1L)).thenReturn(List.of(meal));
        when(weatherService.getForecastForDate(meal.getDate())).thenReturn(forecast);

        List<MealWithWeatherDTO> result = controller.getMealsWithWeather(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDescription()).isEqualTo("Arroz de pato");
        assertThat(result.get(0).getForecast().getMaxTemp()).isEqualTo(22.0);

        verify(mealRepository).findByRestaurantId(1L);
        verify(weatherService).getForecastForDate(meal.getDate());
    }

    private void injectField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao injetar dependência: " + fieldName, e);
        }
    }
}
