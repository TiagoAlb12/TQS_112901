package com.tqs.lab6_4.service;

import com.tqs.lab6_4.entity.Car;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CarService {

    private List<Car> cars = new CopyOnWriteArrayList<>();

    public List<Car> getAllCars() {
        return cars;
    }

    public Optional<Car> getCarById(Long id) {
        return cars.stream().filter(car -> car.getId().equals(id)).findFirst();
    }

    public Car saveCar(Car car) {
        car.setId((long) (cars.size() + 1));
        cars.add(car);
        return car;
    }
}

