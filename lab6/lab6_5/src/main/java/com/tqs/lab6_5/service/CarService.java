package com.tqs.lab6_5.service;

import com.tqs.lab6_5.entity.Car;
import com.tqs.lab6_5.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository repo;

    public CarService(CarRepository repo) {
        this.repo = repo;
    }

    public List<Car> getAllCars() {
        return repo.findAll();
    }

    public Optional<Car> getCarById(Long id) {
        return repo.findById(id);
    }

    public Car saveCar(Car car) {
        return repo.save(car);
    }
}
