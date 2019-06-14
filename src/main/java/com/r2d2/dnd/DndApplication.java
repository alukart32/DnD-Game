package com.r2d2.dnd;

import com.r2d2.dnd.game.Game;
import com.r2d2.dnd.game.logic.GameEngine;
import com.r2d2.dnd.game.session.GameSession;
import com.r2d2.dnd.repository.GameSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class DndApplication {
    private static Game game = null;

    public DndApplication(Game game) {
        DndApplication.game = game;
    }

    public static void main(String[] args) {
        SpringApplication.run(DndApplication.class, args);
        game.startGame();
    }
}
