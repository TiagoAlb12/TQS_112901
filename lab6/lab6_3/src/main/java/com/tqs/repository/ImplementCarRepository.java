package com.tqs.repository;

import com.tqs.entity.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class ImplementCarRepository implements CarRepository {

    private final List<Car> cars = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public List<Car> findAll() {
        return new ArrayList<>(cars);
    }

    @Override
    public Optional<Car> findById(Long id) {
        return cars.stream().filter(car -> car.getId().equals(id)).findFirst();
    }

    @Override
    public Car save(Car car) {
        if (car.getId() == null) {
            car.setId(idCounter.getAndIncrement());
        }
        cars.add(car);
        return car;
    }
}

