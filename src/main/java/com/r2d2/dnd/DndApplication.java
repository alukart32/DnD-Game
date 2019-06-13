package com.r2d2.dnd;

import com.r2d2.dnd.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DndApplication {
    public static void main(String[] args) {
        SpringApplication.run(DndApplication.class, args);
        Game game = new Game();
        game.startGame();
    }
}
