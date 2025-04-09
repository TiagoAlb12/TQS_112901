import com.tqs.CarService;
import com.tqs.entity.Car;
import com.tqs.repository.CarRepository;
import com.tqs.repository.ImplementCarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CarServiceTest {

    private CarRepository carRepository;
    private CarService carService;

    @BeforeEach
    void setUp() {
        carRepository = new ImplementCarRepository();
        carService = new CarService(carRepository);
    }

    @Test
    void whenGetAllCars_thenReturnCarList() {
        carService.saveCar(new Car("Toyota", "Corolla"));
        carService.saveCar(new Car("Honda", "Civic"));

        List<Car> cars = carService.getAllCars();

        assertEquals(2, cars.size());
    }

    @Test
    void whenGetCarById_thenReturnCar() {
        Car car = carService.saveCar(new Car("Ford", "Fiesta"));

        Optional<Car> foundCar = carService.getCarById(car.getId());

        assertTrue(foundCar.isPresent());
        assertEquals("Ford", foundCar.get().getBrand());
    }

    @Test
    void whenSaveCar_thenReturnSavedCar() {
        Car car = carService.saveCar(new Car("Tesla", "Model S"));

        assertNotNull(car.getId());
    }
}
