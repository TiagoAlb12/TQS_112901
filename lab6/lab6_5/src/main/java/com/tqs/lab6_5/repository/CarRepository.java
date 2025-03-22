package com.tqs.lab6_5.repository;

import com.tqs.lab6_5.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
