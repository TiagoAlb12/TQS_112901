package com.tqs.lab6_4;

import com.tqs.lab6_4.controller.CarController;
import com.tqs.lab6_4.entity.Car;
import com.tqs.lab6_4.service.CarService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    private Car car1, car2;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        car1 = new Car(1L, "Toyota", "Corolla");
        car2 = new Car(2L, "Honda", "Civic");

        List<Car> cars = Arrays.asList(car1, car2);
        when(carService.getAllCars()).thenReturn(cars);
        when(carService.getCarById(1L)).thenReturn(Optional.of(car1));
        when(carService.saveCar(any(Car.class))).thenReturn(car1);
    }

    @Test
    void testGetAllCars() {
        RestAssuredMockMvc.given()
                .when().get("/api/cars")
                .then()
                .statusCode(200)
                .body("$", hasSize(2));
    }

    @Test
    void testGetCarById() {
        RestAssuredMockMvc.given()
                .when().get("/api/cars/1")
                .then()
                .statusCode(200)
                .body("brand", equalTo("Toyota"))
                .body("model", equalTo("Corolla"));
    }

    @Test
    void testCreateCar() {
        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\"brand\": \"Ford\", \"model\": \"Focus\"}")
                .when().post("/api/cars")
                .then()
                .statusCode(201);
    }
}

