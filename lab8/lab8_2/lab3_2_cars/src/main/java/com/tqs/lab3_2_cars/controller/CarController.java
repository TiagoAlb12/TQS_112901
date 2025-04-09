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

    // Endpoint PUT para atualizar um carro
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
        Optional<Car> carOptional = carManagerService.getCarDetails(id);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            car.setMaker(carDetails.getMaker());
            car.setModel(carDetails.getModel());
            return ResponseEntity.ok(carManagerService.save(car));  // Salva o carro atualizado
        }
        return ResponseEntity.notFound().build(); // Retorna 404 se o carro não for encontrado
    }

    // Endpoint DELETE para excluir um carro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        Optional<Car> carOptional = carManagerService.getCarDetails(id);
        if (carOptional.isPresent()) {
            carManagerService.delete(id);  // Método de exclusão no serviço
            return ResponseEntity.noContent().build(); // Retorna 204 No Content se o carro for deletado
        }
        return ResponseEntity.notFound().build(); // Retorna 404 se o carro não for encontrado
    }
}
