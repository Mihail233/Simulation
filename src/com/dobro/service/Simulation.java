package com.dobro.service;

import com.dobro.actions.Action;
import com.dobro.actions.CreaturesMakeTurn;
import com.dobro.actions.spawn.*;
import com.dobro.models.CoinHunter;
import com.dobro.models.Ghost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.dobro.models.CoinHunter.getNumberOfCoinsCollected;

public class Simulation {
    private static final char PAUSE = '1';
    private static final char CONTINUE = '2';
    private static final int SIMULATION_FREQUENCY = 1000;
    private final WorldMap worldMap;
    private final RenderField renderField = new RenderField();
    private final List<Action> initActions = new ArrayList<>();
    private final List<Action> turnActions = new ArrayList<>();
    private final Object monitor = new Object();
    private boolean paused;
    private char inputUser;

    public Simulation(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void startSimulation() {
        initSimulation();
        setPaused(false);
        while (hasStruggleForExistence()) {
            nextTurn();
        }
        printResult();
        System.exit(0);
    }

    public void initSimulation() {
        setPaused(true);
        clearConsole();
        createAction();
        initStreamInput();
        executeAction(initActions);
        System.out.println("Стартовая расстановка");
        renderField.displayWorldMap(worldMap);
        printAnswerOptionsDuringPause();
        lockMainFlow();
        clearConsole();
    }

    public boolean hasStruggleForExistence() {
        return worldMap.hasEntity(CoinHunter.class) && worldMap.hasEntity(Ghost.class);
    }

    public void nextTurn() {
        executeAction(turnActions);
        renderField.displayWorldMap(worldMap);
        if (paused) {
            printAnswerOptionsDuringPause();
        } else {
            printAnswerOptionsDuringSimulation();
        }
        lockMainFlow();
        clearConsole();
    }

    public void printResult() {
        System.out.println("Игра закончилась");
        System.out.printf("Количество собранных монет %d \n", getNumberOfCoinsCollected());
    }

    public void createAction() {
        initActions.add(new SpawnObstacle());
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

    public void printAnswerOptionsDuringSimulation() {
        System.out.println("Введите цифру: ");
        System.out.printf("%s - поставить паузу\n", PAUSE);
    }

    public void printAnswerOptionsDuringPause() {
        System.out.println("Введите цифру: ");
        System.out.printf("%s - продолжить симуляцию\n", CONTINUE);
    }

    public void lockMainFlow() {
        synchronized (monitor) {
            changeMonitor();
        }
    }

    public void initStreamInput() {
        new Thread(() -> {
            try {
                synchronized (monitor) {
                    while (true) {
                        if (paused) {
                            getInputFromUser();
                            continueSimulation();
                        } else {
                            Thread.sleep(SIMULATION_FREQUENCY);
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
        int missingValue = 0;
        if (System.in.available() > missingValue) {
            inputUser = (char) System.in.read();
            System.in.skip(System.in.available());
        }
    }

    public void changeMonitor() {
        try {
            monitor.notify();
            monitor.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException("Вмешательство в работу потока");
        }
    }

    public void pauseSimulation() {
        if (inputUser == (int) PAUSE) {
            setPaused(true);
        }
        changeMonitor();
    }

    public void continueSimulation() {
        if (inputUser == (int) CONTINUE) {
            setPaused(false);
            changeMonitor();
        }
    }
}
