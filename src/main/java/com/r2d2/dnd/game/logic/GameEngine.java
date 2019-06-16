package com.r2d2.dnd.game.logic;

import com.r2d2.dnd.game.Player;
import com.r2d2.dnd.game.dto.SkillCastDTO;
import com.r2d2.dnd.game.events.Event;
import com.r2d2.dnd.game.session.GameSession;
import com.r2d2.dnd.model.skill.SkillSideEffect;
import com.r2d2.dnd.repository.GameEventsRepository;
import com.r2d2.dnd.repository.GameSessionRepository;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Ядро игры
 */
@Component
public class GameEngine {
    private GameEventsRepository gameEventsRepository;

    private GameSessionRepository gameSessionRepository;

    // текущая сессия игры
    private GameSession gameSession;

    private Player playerOne;
    private Player playerTwo;

    private Scanner s = new Scanner(System.in);

    // текущий ход игры
    private int move = 1;

    // уровень, который надо достичь
    private int endLvl = 20;

    public GameEngine() {}

    public GameEngine(GameEventsRepository gameEventsRepository, GameSessionRepository gameSessionRepository,
                      GameSession gameSession, Player playerOne, Player playerTwo) {
        this.gameEventsRepository = gameEventsRepository;
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

        while (!getFinish){
            System.out.println("Move: " + move);

            System.out.println("\n---PlayerOne take your move---");
            System.out.println("\nAnd " + playerOne.getRace() + " takes a move");
            printCharacterStat(playerOne);

            printActionMenu(playerOne);
            /**
             * Если первый игрок не дошёл до 20-го уровня
             * второй игрок может совершать действия, иначе незачем ходить
             */
            getFinish = takeMove(playerOne, true);

            if(!getFinish) {
                System.out.println();
                System.out.println("---PlayerTwo take your move---");
                System.out.println("\nAnd " + playerTwo.getRace() + " takes a move");
                printCharacterStat(playerTwo);

                printActionMenu(playerTwo);
                getFinish = takeMove(playerTwo,false);

                System.out.println();
            }
            move++;
        }

        if(playerOne.getLvl() >= endLvl) {
            System.out.println("PlayerOne is winner");
            gameSession.setWinner(playerOne.getRace());
        }
        else
            if(playerTwo.getLvl() >= endLvl) {
                System.out.println("PlayerTwo is winner");
                gameSession.setWinner(playerOne.getRace());
            }
            else {
                System.out.println("The game was stopped!");
                gameSession.setWinner("stopped");
            }
            gameSessionRepository.save(gameSession);
    }

    /**
     * Ход игрока
     */
    private boolean takeMove(Player player, boolean playerOrder){
        int action = getAction();
        boolean doneMove = false;
        boolean notEnoughStamina = true;

        /**
         * Если кто-то захотел прервать игру, то будем считать, что "есть" победитель
         */
        if(action == 5)
            return true;
        /**
         * Исходя из применённого на одного персонажа skill другого и от выбранного действия,
         * совершаем действия персонажа.
         *
         * Если будет недостаточно выносливости(stamina), то надо перевыбрать действие
         */
        while (!doneMove) {
            switch (player.getOtherSkillBuff()) {
                case YOU_SHALL_NOT_PASS:
                    if (action == 1)
                        step(action, player);
                    break;
                case WITHOUT:
                case CHANGE_POS:
                case SUPER_LVL_DOWN:
                    if (action != 4) {
                        if (step(action, player)){
                           doneMove = true;
                           notEnoughStamina = false;
                        }
                    } else {
                        if (player.getCurrentStamina() >= player.getSkillStamina()) {
                            if (playerOrder) {
                                castSkill(player, playerTwo);
                            } else {
                                castSkill(player, playerOne);
                            }
                            doneMove = true;
                            notEnoughStamina = false;
                        }else {
                            printStepAlertMenu(player.getCurrentStamina(), player.getSkillStamina());
                        }
                    }
                    break;
            }

            if(notEnoughStamina){
                printActionMenu(player);
                action = getAction();
            }
        }

        setEvent(action, player);

        player.setOtherSkillBuff(SkillSideEffect.WITHOUT);

        if(playerOne.getLvl() >= endLvl)
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
    private boolean step(int action, Player player){
        switch (action){
            case 1:
                player.rest();
                break;
            case 2:
                if(player.getCurrentStamina() >= 5)
                    player.down();
                else {
                    printStepAlertMenu(player.getCurrentStamina(), 5);
                    return false;
                }
                break;
            case 3:
                if(player.getCurrentStamina() >= player.getFastDownStamina())
                    player.fastTravel();
                else{
                    printStepAlertMenu(player.getCurrentStamina(), player.getFastDownStamina());
                    return false;
                }
                break;
        }

        if(player.getCurrentStamina() + 2 <= player.getMaxStamina())
            player.setCurrentStamina(player.getCurrentStamina()+2);

        if(player.getCurrentStamina() > player.getMaxStamina())
            player.setCurrentStamina(player.getMaxStamina());

        return true;
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

    private void printActionMenu(Player p){
        System.out.println();
        System.out.println("*****Action menu*****");
        System.out.println("1 - to rest ( 0 )");
        System.out.println("2 - to go down ( 5 )");
        System.out.println("3 - to go fast down ( "+p.getFastDownStamina()+" )");
        System.out.println("4 - to use the skill ( "+p.getSkillStamina()+" )");
        System.out.println("5 - stop the game");
        System.out.println("Enter action: ");
    }

    private void printStepAlertMenu(int currStamina, int needMinStamina){
        System.out.println();
        System.out.println("Not enough stamina!!!");
        System.out.println("Current: " + currStamina + "    " + "Need: " + needMinStamina);
        System.out.println("Reselect your choice.");
    }

    private void printCharacterStat(Player p){
        System.out.println();
        System.out.println("lvl: " + p.getLvl() +"\t" + "stamina: " + p.getCurrentStamina());
    }

    private int getAction(){
        return s.nextInt();
    }

    private void setEvent(int action, Player p){
        Event e = new Event();

        String option = null;
        switch (action){
            case 1:
                option = "rest";
                break;
            case 2:
                option = "down";
                break;
            case 3:
                option = "fast down";
                break;
            case 4:
                option = p.getSkillBuff().toString();
                break;
        }

        e.setGameSession(gameSession);
        e.setMove(move);

        // Если gnome исплдьзовал skill, то другой персонаж ничего не делает (кроме отдыха?)
        if(p.getOtherSkillBuff() == SkillSideEffect.YOU_SHALL_NOT_PASS)
            e.setAction("do nothing");
        else
            e.setAction(option);

        e.setLvl(p.getLvl());
        e.setStamina(p.getCurrentStamina());
        e.setPlayer(p.getRace());

        gameSessionRepository.save(gameSession.addEvent(e));
        gameEventsRepository.save(e);
    }

    private void castSkill(Player player, Player other){
            SkillCastDTO skillCastDTO = player.skill(other);

            if (player.getRace().equals("mage")) {
                other.setLvl(skillCastDTO.getOther().getLvl());
            }
            other.setOtherSkillBuff(player.getSkillBuff());
    }
}