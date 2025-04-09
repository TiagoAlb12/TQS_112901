package com.tqs.repository;

import com.tqs.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    List<Car> findAll();
    Optional<Car> findById(Long id);
    Car save(Car car);
}



