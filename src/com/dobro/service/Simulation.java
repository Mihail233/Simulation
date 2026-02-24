package com.dobro.service;

import com.dobro.actions.Action;
import com.dobro.actions.CreaturesMakeTurn;
import com.dobro.actions.spawn.*;
import com.dobro.models.CoinHunter;
import com.dobro.models.Ghost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private boolean paused = true;
    private final Object monitor = new Object();
    private final List<Action> initActions = new ArrayList<>();
    private final List<Action> turnActions = new ArrayList<>();
    private RenderField renderField = new RenderField();
    private WorldMap worldMap;
    private char inputUser = 'q';

    public Simulation(WorldMap worldMap) {
        this.worldMap = worldMap;
        createAction();
    }

    public void initSimulation() throws InterruptedException {
        clearConsole();
        initStreamInput();
        executeAction(initActions);
        renderField.displayWorldMap(worldMap);

        printAnswerOptionsDuringPause();
        lockFlow();
        clearConsole();
    }

    //первый поток работает, останавливается в этот момент получаются данные из потока на 1000мс, потом поток 2 замораживается
    public void nextTurn() throws InterruptedException {
        //новый круг
        executeAction(turnActions);
        renderField.displayWorldMap(worldMap);
        if (paused) {
            printAnswerOptionsDuringPause();
        } else {
            printAnswerOptionsDuringSimulation();
        }
        lockFlow();
        clearConsole();
    }

    public void startSimulation() throws InterruptedException, IOException {
        initSimulation();
        paused = false;
        while (worldMap.hasEntity(CoinHunter.class) && worldMap.hasEntity(Ghost.class)) {
            nextTurn();
        }
        System.out.println("Игра закончилась");
        System.exit(0);
    }

    public void createAction() {
        initActions.add(new SpawnObstacles());
        initActions.add(new SpawnCoin());
        initActions.add(new SpawnCoinHunter());
        initActions.add(new SpawnGhost());
        turnActions.add(new CreaturesMakeTurn());
        turnActions.add(new SpawnExtraCoin());
    }

    public void executeAction(List<Action> actions) {
        for (Action action : actions) {
            action.execute(worldMap);
        }
    }

    public void clearConsole() {
        System.out.print("\033[H\033[J");
    }

    public void printAnswerOptionsDuringPause() {
        System.out.println("Введите цифру: ");
        System.out.println("2 - продолжить симуляцию");
    }

    public void printAnswerOptionsDuringSimulation() {
        System.out.println("Введите цифру: ");
        System.out.println("1 - поставить паузу");
    }

    public void lockFlow() throws InterruptedException {
        //не может продолжить пока монитор у другого потока
        synchronized (monitor) {
            changeMonitor();
        }
    }

    //симуляция может быть на паузе и мы ждем ответ пользователя бесконечно или не на паузе и ждем ответ пользователя несколько секунд
    public void initStreamInput() {
        new Thread(() -> {
            try {
                synchronized (monitor) {
                    while (true) {
                        if (paused) {
                            getInputFromUser();
                            continueSimulation();
                        } else {
                            Thread.sleep(1000);
                            getInputFromUser();
                            pauseSimulation();
                        }
                    }
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void getInputFromUser() throws IOException {
        if (System.in.available() > 0) {
            inputUser = (char) System.in.read();
            System.in.skip(System.in.available());
        }
    }

    public void changeMonitor() throws InterruptedException {
        monitor.notify();
        monitor.wait();
    }

    public void pauseSimulation() throws InterruptedException {
        if (inputUser == (int) '1') {
            paused = true;
        }
        changeMonitor();
    }

    public void continueSimulation() throws InterruptedException {
        if (inputUser == (int) '2') {
            paused = false;
            changeMonitor();
        }
    }
}
