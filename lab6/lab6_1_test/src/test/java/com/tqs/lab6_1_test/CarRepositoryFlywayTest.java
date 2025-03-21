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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarRepositoryFlywayTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private CarRepository carRepository;

    @Test
    @Order(1)
    void shouldLoadCarsFromFlywayMigration() {
        List<Car> cars = carRepository.findAll();

        assertEquals(3, cars.size());
        assertEquals("Toyota", cars.get(0).getBrand());
        assertEquals("Corolla", cars.get(0).getModel());
    }

    @Test
    @Order(2)
    void shouldInsertNewCar() {
        Car car = new Car("Tesla", "Model S");
        carRepository.save(car);

        Optional<Car> foundCar = carRepository.findById(car.getId());

        assertTrue(foundCar.isPresent());
        assertEquals("Tesla", foundCar.get().getBrand());
        assertEquals("Model S", foundCar.get().getModel());
    }

    @Test
    @Order(3)
    void shouldDeleteCar() {
        List<Car> carsBefore = carRepository.findAll();
        assertFalse(carsBefore.isEmpty());

        Car carToDelete = carsBefore.get(0);
        carRepository.delete(carToDelete);

        Optional<Car> deletedCar = carRepository.findById(carToDelete.getId());
        assertFalse(deletedCar.isPresent());
    }
}
