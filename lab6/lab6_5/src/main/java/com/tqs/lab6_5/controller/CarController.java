package com.tqs.lab6_5.controller;

import com.tqs.lab6_5.entity.Car;
import com.tqs.lab6_5.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping
    public List<Car> getAllCars() {
        return service.getAllCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return service.getCarById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        return ResponseEntity.status(201).body(service.saveCar(car));
    }
}
