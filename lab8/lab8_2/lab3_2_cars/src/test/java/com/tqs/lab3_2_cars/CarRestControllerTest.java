package com.tqs.lab3_2_cars;

import com.tqs.lab3_2_cars.model.Car;
import com.tqs.lab3_2_cars.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
class CarRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setup() {
        carRepository.deleteAll();
    }

    @Test
    void whenGetCars_thenReturnJsonArray() throws Exception {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");
        carRepository.saveAll(List.of(car1, car2));

        mvc.perform(MockMvcRequestBuilders.get("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].maker", is(car1.getMaker())))
                .andExpect(jsonPath("$[1].maker", is(car2.getMaker())));
    }

    @Test
    void whenPostCar_thenCarIsCreated() throws Exception {
        Car newCar = new Car("Ford", "Focus");

        mvc.perform(MockMvcRequestBuilders.post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newCar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is(newCar.getMaker())))
                .andExpect(jsonPath("$.model", is(newCar.getModel())));

        List<Car> cars = carRepository.findAll();
        assertThat(cars).hasSize(1);
        assertThat(cars.get(0).getMaker()).isEqualTo("Ford");
    }
}
