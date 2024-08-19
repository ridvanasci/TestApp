package com.example.testapp;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SampleDataGenerator {

    private final GameRepository gameRepository;

    public SampleDataGenerator(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @PostConstruct
    public void generateData() {
        for (int i = 1; i < 100; i++) {
            gameRepository.save(new Game((long) i, "Game " + i, "Description " + i));
        }
    }
}
