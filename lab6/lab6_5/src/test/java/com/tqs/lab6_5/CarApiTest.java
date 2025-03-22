package com.tqs.lab6_5;

import com.tqs.lab6_5.entity.Car;
import com.tqs.lab6_5.repository.CarRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class CarApiTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("testdb");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @LocalServerPort
    private int port;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        carRepository.deleteAll();
        carRepository.save(new Car(null, "Audi", "A3"));
        carRepository.save(new Car(null, "Fiat", "500"));
    }

    @Test
    void getAllCarsTest() {
        when()
                .get("/api/cars")
                .then()
                .statusCode(200)
                .body("$", hasSize(2));
    }

    @Test
    void getCarByIdTest() {
        Car car = carRepository.findAll().get(0);

        when()
                .get("/api/cars/" + car.getId())
                .then()
                .statusCode(200)
                .body("brand", equalTo(car.getBrand()))
                .body("model", equalTo(car.getModel()));
    }
}
