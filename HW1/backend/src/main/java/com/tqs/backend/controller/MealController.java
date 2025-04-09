package com.tqs.backend.controller;

import com.tqs.backend.entity.Meal;
import com.tqs.backend.model.MealWithWeatherDTO;
import com.tqs.backend.entity.WeatherForecast;
import com.tqs.backend.repository.MealRepository;
import com.tqs.backend.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public List<MealWithWeatherDTO> getMealsWithWeather(@RequestParam Long restaurantId) {
        List<Meal> meals = mealRepository.findByRestaurantId(restaurantId);
        List<MealWithWeatherDTO> result = new ArrayList<>();

        for (Meal meal : meals) {
            WeatherForecast forecast = weatherService.getForecastForDate(meal.getDate());
            result.add(new MealWithWeatherDTO(meal, forecast));
        }

        return result;
    }
}
