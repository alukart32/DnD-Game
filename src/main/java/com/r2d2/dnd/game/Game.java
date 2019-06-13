package com.r2d2.dnd.game;

import com.r2d2.dnd.game.logic.GameEngine;
import com.r2d2.dnd.game.session.GameSession;
import com.r2d2.dnd.model.Character;
import com.r2d2.dnd.model.Elf;
import com.r2d2.dnd.model.Gnome;
import com.r2d2.dnd.model.Mage;
import com.r2d2.dnd.model.skill.SkillCastElf;
import com.r2d2.dnd.model.skill.SkillCastGnome;
import com.r2d2.dnd.model.skill.SkillCastMage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Стартовая точка игры
 */
@Component
public class Game {
    @Autowired
    GameEngine engine;

    Scanner s = new Scanner(System.in);

    public void startGame(){
        printHelloMsg();

        boolean run = true;

        while (run){
            printMainMenu();
            switch (getAction()){
                // начать новую игру
                case 1:
                    System.out.println();
                    /**
                     * Выбор персонажа для первого игрока
                     */
                    System.out.println("Choose your character PlayerOne");
                    printCharacterMenu();
                    Character characterOne = getPlayer(getAction());

                    System.out.println();
                    /**
                     * Выбор персонажа для второго игрока
                     */
                    System.out.println("Choose your character PlayerTwo");
                    printCharacterMenu();
                    Character characterTwo = getPlayer(getAction());
                    /**
                     * Определение порядка игроков
                     * чётное число - первый игрок
                     * нечётное число - второй игрок
                     */
                      Player playerOne = new Player();
                      Player playerTwo = new Player();

                     if(Math.random()%2 == 0) {
                         playerOne.setCharacter(characterOne);
                         playerTwo.setCharacter(characterTwo);
                     }else{
                         playerOne.setCharacter(characterTwo);
                         playerTwo.setCharacter(characterOne);
                     }

                    System.out.println(playerOne.getRace() + "vs" + playerTwo.getRace());

                     // старт игры
                     GameSession gameSession = new GameSession();
                     gameSession.setPlayerOne(playerOne.getRace());
                     gameSession.setPlayerTwo(playerTwo.getRace());

                     engine = new GameEngine(gameSession, playerOne, playerTwo);
                    // engine.runGame();

                    break;
                case 2:
                    break;
                case 3:
                    run = false;
                    break;
            }
        }

        System.out.println("****EXIT****");
    }

    private void printHelloMsg(){
        System.out.println("****DnD Game****");
        System.out.println();
    }

    private void printMainMenu(){
        System.out.println("****Main Menu****");
        System.out.println("1 - to start a new game");
        System.out.println("2 - list of games");
        System.out.println("3 - to exit");
        System.out.println("Enter: ");
    }

    private void printCharacterMenu(){
        System.out.println("****Choose character****");
        System.out.println("1 - mage");
        System.out.println("2 - gnome");
        System.out.println("3 - elf");
        System.out.println("Enter: ");
    }

    private int getAction(){
        return s.nextInt();
    }

    private Character getPlayer(int choice){
        Character character = null;

        switch (choice){
            case 1:
                character = new Mage(30, 13, 15, new SkillCastMage());
                break;
            case 2:
                character = new Gnome(50, 15, 20, new SkillCastGnome());
                break;
            case 3:
                character = new Elf(40, 12, 24, new SkillCastElf());
                break;
        }
        return character;
    }

    private Player setPlayer(Character character){
        Player p = new Player();
        p.setCharacter(character);
        p.setCurrStamina(character.getMaxStamina());
        p.setLvl(0);
        return p;
    }
}
