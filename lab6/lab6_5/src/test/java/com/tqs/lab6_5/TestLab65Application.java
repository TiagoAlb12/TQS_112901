package com.tqs.lab6_5;

import org.springframework.boot.SpringApplication;

public class TestLab65Application {

    public static void main(String[] args) {
        SpringApplication.from(Lab65Application::main).with(TestcontainersConfiguration.class).run(args);
    }

}
