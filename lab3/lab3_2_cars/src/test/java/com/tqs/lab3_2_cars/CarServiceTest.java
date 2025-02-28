package com.tqs.lab3_2_cars;

import com.tqs.lab3_2_cars.model.Car;
import com.tqs.lab3_2_cars.repository.CarRepository;
import com.tqs.lab3_2_cars.service.CarManagerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carService;

    @Test
    void whenGetAllCars_thenReturnList() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");

        when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2));

        List<Car> result = carService.getAllCars();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getMaker()).isEqualTo("Toyota");
    }

    @Test
    void whenValidId_thenReturnCar() {
        Car car = new Car("Ford", "Focus");
        when(carRepository.findByCarId(1L)).thenReturn(car);

        Optional<Car> found = carService.getCarDetails(1L);

        assertThat(found).isPresent();
        assertThat(found.get().getModel()).isEqualTo("Focus");
    }
}


