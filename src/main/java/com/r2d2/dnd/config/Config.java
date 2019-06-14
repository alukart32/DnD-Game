package com.r2d2.dnd.config;

import com.r2d2.dnd.game.Game;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public Game setGame(){
        return new Game();
    }
}
