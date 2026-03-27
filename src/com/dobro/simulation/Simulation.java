package com.dobro.simulation;

import com.dobro.GameMapRenderer;
import com.dobro.action.Action;
import com.dobro.action.CreaturesMakeTurn;
import com.dobro.action.spawn.*;
import com.dobro.entity.CoinHunter;
import com.dobro.entity.Ghost;
import com.dobro.worldmap.WorldMap;
import com.dobro.worldmap.WorldMapUtils;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private static final int SIMULATION_FREQUENCY = 1000;
    private final WorldMap worldMap;
    private final GameMapRenderer GameMapRenderer = new GameMapRenderer();
    private final List<Action> initActions = new ArrayList<>();
    private final List<Action> turnActions = new ArrayList<>();
    private volatile boolean isPaused = false;

    public Simulation(WorldMap worldMap) {
        this.worldMap = worldMap;
        initSimulation();
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void start() throws InterruptedException {
        while (!isPaused) {
            if (isFinished()) {
                System.exit(0);
            }
            nextTurn();
            Thread.sleep(SIMULATION_FREQUENCY);
        }
    }

    private boolean isFinished() {
        return !(WorldMapUtils.hasEntity(CoinHunter.class, worldMap) && WorldMapUtils.hasEntity(Ghost.class, worldMap));
    }

    private void initSimulation() {
        SimulationUtils.clearConsole();
        createAction();
        executeAction(initActions);
        System.out.println("Стартовая расстановка");
        GameMapRenderer.render(worldMap);
        SimulationUtils.printOptions();
    }

    private void nextTurn() {
        SimulationUtils.clearConsole();
        executeAction(turnActions);
        GameMapRenderer.render(worldMap);
        SimulationUtils.printOptions();
    }

    private void createAction() {
        initActions.add(new SpawnObstacle());
        initActions.add(new SpawnCoin());
        initActions.add(new SpawnCoinHunter());
        initActions.add(new SpawnGhost());
        turnActions.add(new CreaturesMakeTurn());
        turnActions.add(new SpawnExtraCoin());
    }

    private void executeAction(List<Action> actions) {
        for (Action action : actions) {
            action.execute(worldMap);
        }
    }
}
