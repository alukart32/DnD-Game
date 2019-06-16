package com.r2d2.dnd.game;

import com.r2d2.dnd.game.events.Event;
import com.r2d2.dnd.game.logic.GameEngine;
import com.r2d2.dnd.game.session.GameSession;
import com.r2d2.dnd.model.Character;
import com.r2d2.dnd.model.Elf;
import com.r2d2.dnd.model.Gnome;
import com.r2d2.dnd.model.Mage;
import com.r2d2.dnd.model.skill.SkillCastElf;
import com.r2d2.dnd.model.skill.SkillCastGnome;
import com.r2d2.dnd.model.skill.SkillCastMage;
import com.r2d2.dnd.repository.GameEventsRepository;
import com.r2d2.dnd.repository.GameSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Стартовая точка игры
 */
@Service
public class Game {

    @Autowired
    GameEngine engine;

    @Autowired
    GameSessionRepository gameSessionRepository;

    @Autowired
    GameEventsRepository gameEventsRepository;

    private Player playerOne;
    private Player playerTwo;

    private Scanner s = new Scanner(System.in);


    public void startGame(){
        printHelloMsg();

        boolean run = true;

        while (run){
            printMainMenu();
            switch (getValue()){
                // начать новую игру
                case 1:
                    initGame();

                    // старт игры
                    GameSession gameSession = new GameSession();
                    gameSession.setPlayerOne(playerOne.getRace());
                    gameSession.setPlayerTwo(playerTwo.getRace());

                    engine = new GameEngine(gameEventsRepository,gameSessionRepository,
                            gameSession, playerOne, playerTwo);
                    engine.runGame();
                    break;
                case 2:
                    printGameEvents();
                    System.out.println();
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

    private int getValue(){
        return s.nextInt();
    }

    /**
     * Выбор персонажа и определение порядка игроков
     */
    private void initGame(){

        System.out.println();
        /**
         * Выбор персонажа для первого игрока
         */
        System.out.println("Choose your character PlayerOne");
        printCharacterMenu();
        Character characterOne = getPlayer(getValue());

        System.out.println();
        /**
         * Выбор персонажа для второго игрока
         */
        System.out.println("Choose your character PlayerTwo");
        printCharacterMenu();
        Character characterTwo = getPlayer(getValue());
        /**
         * Определение порядка игроков
         * чётное число - первый игрок
         * нечётное число - второй игрок
         */
        playerOne = new Player();
        playerTwo = new Player();

        if((Math.random()*50)%2 == 0) {
            setCharactersInOrder(characterOne, characterTwo);
        }else{
            setCharactersInOrder(characterTwo, characterOne);
        }
    }

    /**
     * Отображение событиев игры по id выбранной gameSession
     */
    private void printGameEvents(){
        System.out.println();
        System.out.println("***GameSession***");

        Set<GameSession> gameSessions = new TreeSet<>(Game::compareGameSession);
        Iterable<GameSession> sessions = gameSessionRepository.findAll();
        sessions.forEach(gameSessions::add);

        if(gameSessions.isEmpty())
            System.out.println("You haven't played any game!!!");
        else {
            for (GameSession session : gameSessions) {
                System.out.println(session.getId() + "      winner: " + session.getWinner());
            }

            System.out.println();
            System.out.println("Enter: ");

            showGameEvents((long) getValue());
        }
    }

    /**
     * Выборка и отображение событиев игры(ходов) относительно gameSession
     * @param id
     */
    private void showGameEvents(Long id){
        Set<Event> eventSet = new TreeSet<>(Game::compareGameEvent);
        Iterable<Event> events = gameEventsRepository.findAllByGameSessionId(id);
        events.forEach(eventSet::add);

        System.out.println();
        System.out.println("Game events");
        System.out.println("--------------------------------------------------------------");

        for (Event event : eventSet) {
            System.out.println( "move: "+event.getMove()+"   player: " + event.getPlayer() +
                    "   action: "+event.getAction() + "     lvl "+event.getLvl());
        }

        System.out.println("--------------------------------------------------------------");
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

    /**
     * Создание "участника игры"
     *
     * @param character
     *                      выбранный персонаж игроком
     * @return
     *          участника игры
     */
    private Player setPlayer(Character character){
        Player p = new Player();
        p.setCharacter(character);
        p.setCurrentStamina(character.getMaxStamina());
        p.setLvl(0);
        return p;
    }

    /**
     * Установление порядка игроков
     *
     * @param characterOne
     * @param characterTwo
     */
    private void setCharactersInOrder(Character characterOne, Character characterTwo){
        playerOne = setPlayer(characterOne);
        System.out.println("First player: " + playerOne.getRace());
        playerTwo = setPlayer(characterTwo);
        System.out.println("Second player: " + playerTwo.getRace());
    }
    /**
     * Определение метода compare для компаратора при создании TreeSet
     *
     * @param o1
     * @param o2
     * @return
     */

    private static int compareGameSession(GameSession o1, GameSession o2) {
        {
            if (Objects.equals(o1.getId(), o2.getId()))
                return 0;
            else if (o1.getId() < o2.getId())
                return -1;
            else
                return 1;
        }
    }

    private static int compareGameEvent(Event e1, Event e2) {
        {
            if (Objects.equals(e1.getId(), e2.getId()))
                return 0;
            else if (e1.getId() < e2.getId())
                return -1;
            else
                return 1;
        }
    }

}
