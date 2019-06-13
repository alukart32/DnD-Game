package com.r2d2.dnd.game.logic;

import com.r2d2.dnd.game.Player;
import com.r2d2.dnd.game.events.Event;
import com.r2d2.dnd.game.session.GameSession;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Ядро игры
 */
@Component
public class GameEngine {

    // текущая сессия игры
    private GameSession gameSession;
    // события текущей сессии
    private Event events;

    private Player playerOne;
    private Player playerTwo;

    private Scanner s = new Scanner(System.in);

    public GameEngine(GameSession gameSession, Player playerOne, Player playerTwo) {
        this.gameSession = gameSession;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    /**
     * Непосредственная логическая реализация игры.
     * Порядок игроков: playerOne, playerTwo (за каким какой игрок определяется при создании gameSession)
     */
    public void runGame(){
        printHelloMsg();

        // первый кто дойдёт до 20-го уровня
        boolean winner = false;

        while (!winner){
            printActionMenu();
            /**
             * Если первый игрок не дошёл до 20-го уровня
             * второй игрок может совершать действия, иначе незачем ходить
             */
            winner = takeMove(playerOne);
            if(!winner)
                winner = takeMove(playerTwo);
        }
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
        System.out.println("****DnD Match****");
        System.out.println(playerOne.getRace() + " vs " + playerTwo.getRace());
        System.out.println("****FIGHT*****");
        System.out.println();
    }

    private void printActionMenu(){
        System.out.println("*****Action menu*****");
        System.out.println("1 - to rest");
        System.out.println("2 - to go down");
        System.out.println("3 - to go fast down");
        System.out.println("4 - to use the skill");
        System.out.println("Enter action: ");
    }

    private int getAction(){
        return s.nextInt();
    }
}
