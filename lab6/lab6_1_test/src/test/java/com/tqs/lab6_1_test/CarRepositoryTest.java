package com.tqs.lab6_1_test;

import com.tqs.lab6_1_test.entity.Car;
import com.tqs.lab6_1_test.repository.CarRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private CarRepository carRepository;

    @Test
    @Order(1)
    void shouldSaveCar() {
        Car car = new Car("Toyota", "Corolla");
        carRepository.save(car);
        assertNotNull(car.getId());
    }

    @Test
    @Order(2)
    void shouldFindCarById() {
        Car car = new Car("Honda", "Civic");
        carRepository.save(car);

        Optional<Car> foundCar = carRepository.findById(car.getId());

        assertTrue(foundCar.isPresent());
        assertEquals("Honda", foundCar.get().getBrand());
        assertEquals("Civic", foundCar.get().getModel());
    }

    @Test
    @Order(3)
    void shouldUpdateCar() {
        Car car = new Car("Ford", "Fiesta");
        carRepository.save(car);

        car.setModel("Focus");
        carRepository.save(car);

        Optional<Car> updatedCar = carRepository.findById(car.getId());

        assertTrue(updatedCar.isPresent());
        assertEquals("Ford", updatedCar.get().getBrand());
        assertEquals("Focus", updatedCar.get().getModel());
    }

    @Test
    @Order(4)
    void shouldDeleteCar() {
        Car car = new Car("BMW", "320i");
        carRepository.save(car);

        carRepository.delete(car);

        Optional<Car> deletedCar = carRepository.findById(car.getId());

        assertFalse(deletedCar.isPresent());
    }

}
