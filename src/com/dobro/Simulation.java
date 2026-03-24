package com.dobro;

import com.dobro.actions.Action;
import com.dobro.actions.CreaturesMakeTurn;
import com.dobro.actions.spawn.*;
import com.dobro.entity.CoinHunter;
import com.dobro.entity.Ghost;

import java.util.ArrayList;
import java.util.List;

import static com.dobro.entity.CoinHunter.getNumberOfCoinsCollected;

public class Simulation {
    private final WorldMap worldMap;
    private final GameMapRenderer GameMapRenderer = new GameMapRenderer();
    private final List<Action> initActions = new ArrayList<>();
    private final List<Action> turnActions = new ArrayList<>();

    public Simulation(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void startSimulation() {
        while (hasStruggleForExistence()) {
            nextTurn();
        }
        System.exit(0);
    }

    public void initSimulation() {
        clearConsole();
        createAction();
        executeAction(initActions);
        System.out.println("Стартовая расстановка");
        GameMapRenderer.render(worldMap);
    }

    public boolean hasStruggleForExistence() {
        return WorldMapUtils.hasEntity(CoinHunter.class, worldMap) && WorldMapUtils.hasEntity(Ghost.class, worldMap);
    }

    public void nextTurn() {
        clearConsole();
        executeAction(turnActions);
        GameMapRenderer.render(worldMap);
        printResultOfTurn();
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

    private void clearConsole() {
        System.out.print("\033[H\033[J");
    }

    private void printResultOfTurn() {
        System.out.println("Собранные монеты " + getNumberOfCoinsCollected());
    }
}
