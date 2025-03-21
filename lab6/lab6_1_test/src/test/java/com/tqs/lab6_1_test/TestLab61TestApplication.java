package com.tqs.lab6_1_test;

import org.springframework.boot.SpringApplication;

public class TestLab61TestApplication {

    public static void main(String[] args) {
        SpringApplication.from(Lab61TestApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
