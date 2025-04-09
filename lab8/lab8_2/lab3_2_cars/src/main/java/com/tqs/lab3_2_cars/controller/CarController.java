package com.tqs.lab3_2_cars.controller;

import com.tqs.lab3_2_cars.model.Car;
import com.tqs.lab3_2_cars.service.CarManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarManagerService carManagerService;

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return ResponseEntity.ok(carManagerService.save(car));
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carManagerService.getAllCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Optional<Car> car = carManagerService.getCarDetails(id);
        return car.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
