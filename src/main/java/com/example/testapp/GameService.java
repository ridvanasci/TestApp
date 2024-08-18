package com.example.testapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> findGamesByName(String name) {
        return gameRepository.findByNameContains(name);
    }
}
