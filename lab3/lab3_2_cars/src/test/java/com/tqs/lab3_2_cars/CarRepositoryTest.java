package com.tqs.lab3_2_cars;

import com.tqs.lab3_2_cars.model.Car;
import com.tqs.lab3_2_cars.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenSaveCar_thenFindById() {
        Car car = new Car("BMW", "M3");
        Car savedCar = carRepository.save(car);

        Car foundCar = carRepository.findByCarId(savedCar.getCarId());
        assertThat(foundCar).isNotNull();
        assertThat(foundCar.getMaker()).isEqualTo("BMW");
    }

    @Test
    void whenFindAll_thenReturnAllCars() {
        Car car1 = new Car("Audi", "A4");
        Car car2 = new Car("Mercedes", "C-Class");

        carRepository.save(car1);
        carRepository.save(car2);

        List<Car> allCars = carRepository.findAll();
        assertThat(allCars).hasSize(2);
    }
}
