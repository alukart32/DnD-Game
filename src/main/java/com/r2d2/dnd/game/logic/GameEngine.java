package com.r2d2.dnd.game.logic;

import com.r2d2.dnd.game.Player;
import com.r2d2.dnd.game.events.Event;
import com.r2d2.dnd.game.session.GameSession;
import com.r2d2.dnd.repository.GameSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * Ядро игры
 */
@Service
public class GameEngine {


    private GameSessionRepository gameSessionRepository;

    // текущая сессия игры
    private GameSession gameSession;

    // события текущей сессии
    private Event events;

    private Player playerOne;
    private Player playerTwo;

    private Scanner s = new Scanner(System.in);

    public GameEngine() {}

    public GameEngine(GameSessionRepository gameSessionRepository, GameSession gameSession,
                      Player playerOne, Player playerTwo) {
        this.gameSessionRepository = gameSessionRepository;
        this.gameSession = gameSession;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    /**
     * Непосредственная логическая реализация игры.
     * Порядок игроков: playerOne, playerTwo (за каким какой игрок определяется при создании gameSession)
     */
    public void runGame(){
        gameSessionRepository.save(gameSession);

        printHelloMsg();

        // первый кто дойдёт до 20-го уровня
        boolean getFinish = false;
        // кол-во ходов игры
        int move = 0;

        while (!getFinish){
            System.out.println("Move: " + move);

            System.out.println("---PlayerOne take your move---");
            printCharacterStat(playerOne);

            printActionMenu();
            /**
             * Если первый игрок не дошёл до 20-го уровня
             * второй игрок может совершать действия, иначе незачем ходить
             */
            getFinish = takeMove(playerOne);
            printCharacterStat(playerOne);

            if(!getFinish) {
                System.out.println();
                System.out.println("---PlayerTwo take your move---");
                printCharacterStat(playerTwo);

                printActionMenu();
                getFinish = takeMove(playerTwo);
                printCharacterStat(playerTwo);

                System.out.println();
            }
            move++;
        }
        if(playerOne.getLvl() >= 20)
            System.out.println("PlayerOne is winner");
        else
            System.out.println("PlayerTwo is winner");
    }

    /**
     * Ход игрока
     */
    private boolean takeMove(Player player){
        step(getAction(), player);

        if(playerOne.getLvl() >= 20)
            return true;
        return false;
    }

    /**
     * Действие, совершаемое персонажем
     *
     * @param action
     *                  Объект, хранящий информацию о событии игрока. При действии 's' хранит
     *                  и состояние второго игрока
     *
     * @param player
     *                  Игрок (его персонаж), который совершил это действие
     */
    private void step(int action, Player player){
        switch (action){
            case 1:
                player.rest();
                break;
            case 2:
                player.down();
                break;
            case 3:
                player.fastTravel();
                break;
            case 4:
               // player.skill();
                break;
        }

        player.setCurrStamina(player.getCurrStamina()+2);
    }

    private void printHelloMsg(){
        System.out.println();
        System.out.println("****DnD Match****");
        System.out.println();
        System.out.println(playerOne.getRace() + " vs " + playerTwo.getRace());
        System.out.println();
        System.out.println("****FIGHT*****");
        System.out.println();
    }

    private void printActionMenu(){
        System.out.println();
        System.out.println("*****Action menu*****");
        System.out.println("1 - to rest");
        System.out.println("2 - to go down");
        System.out.println("3 - to go fast down");
        System.out.println("4 - to use the skill");
        System.out.println("Enter action: ");
    }

    private void printCharacterStat(Player p){
        System.out.println();
        System.out.println("lvl: " + p.getLvl() +"\t" + "stamina: " + p.getCurrStamina());
    }

    private int getAction(){
        return s.nextInt();
    }
}
