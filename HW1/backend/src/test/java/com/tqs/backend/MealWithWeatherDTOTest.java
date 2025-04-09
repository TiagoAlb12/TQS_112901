package com.tqs.backend;

import com.tqs.backend.entity.Meal;
import com.tqs.backend.entity.WeatherForecast;
import com.tqs.backend.enums.MealType;
import com.tqs.backend.model.MealWithWeatherDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class MealWithWeatherDTOTest {

    @Test
    void testConstructorAndGetters() {
        Meal meal = new Meal();
        meal.setDescription("Sopa e arroz");
        meal.setDate(LocalDate.of(2025, 4, 10));
        meal.setType(MealType.ALMOCO);

        WeatherForecast forecast = new WeatherForecast();
        forecast.setMaxTemp(22.5);
        forecast.setMinTemp(12.0);
        forecast.setPrecipitation(0.0);

        MealWithWeatherDTO dto = new MealWithWeatherDTO(meal, forecast);

        assertThat(dto.getDescription()).isEqualTo("Sopa e arroz");
        assertThat(dto.getDate()).isEqualTo(LocalDate.of(2025, 4, 10));
        assertThat(dto.getType()).isEqualTo(MealType.ALMOCO);
        assertThat(dto.getForecast()).isEqualTo(forecast);
    }

    @Test
    void testSetters() {
        MealWithWeatherDTO dto = new MealWithWeatherDTO(new Meal(), new WeatherForecast());

        dto.setDescription("Lasanha");
        dto.setDate(LocalDate.of(2025, 4, 12));
        dto.setType(MealType.JANTAR);
        WeatherForecast forecast = new WeatherForecast();
        forecast.setPrecipitation(1.5);
        dto.setForecast(forecast);

        assertThat(dto.getDescription()).isEqualTo("Lasanha");
        assertThat(dto.getDate()).isEqualTo(LocalDate.of(2025, 4, 12));
        assertThat(dto.getType()).isEqualTo(MealType.JANTAR);
        assertThat(dto.getForecast().getPrecipitation()).isEqualTo(1.5);
    }
}
