package com.tqs.lab6_1_test.repository;

import com.tqs.lab6_1_test.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    /**
    List<Car> findByBrand(String brand);

    List<Car> findByModel(String model);

    long countByBrand(String brand);

    @Query("SELECT c FROM Car c WHERE c.brand = :brand AND c.model = :model")
    List<Car> findByBrandAndModel(@Param("brand") String brand, @Param("model") String model);


     **/
}

