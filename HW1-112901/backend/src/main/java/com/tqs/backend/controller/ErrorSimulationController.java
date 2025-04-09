package com.tqs.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test-error")
public class ErrorSimulationController {

    @GetMapping
    public void simulateError() {
        throw new RuntimeException("Erro simulado");
    }
}

