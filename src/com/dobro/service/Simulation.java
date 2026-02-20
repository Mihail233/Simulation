package com.dobro.service;

import com.dobro.actions.*;
import com.dobro.actions.spawn.SpawnCoin;
import com.dobro.actions.spawn.SpawnCoinHunter;
import com.dobro.actions.spawn.SpawnGhost;
import com.dobro.actions.spawn.SpawnObstacles;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<Action> initActions = new ArrayList<>();
    private final List<Action> turnActions = new ArrayList<>();
    private RenderField renderField = new RenderField();
    private WorldMap worldMap;
    private int turnCounter;

    public Simulation(WorldMap worldMap) {
        this.worldMap = worldMap;
        turnCounter = 0;
        createAction();
    }

    public void initSimulation() {
        executeAction(initActions);
        renderField.displayWorldMap(worldMap);
    }

    public void nextTurn() {
        executeAction(turnActions);
        renderField.displayWorldMap(worldMap);
    }

    public void startSimulation() {
        initSimulation();
        System.out.println();
        int i = 2;
        while (i > 0) {
            nextTurn();
            i--;
        }
//        System.out.println(worldMap.sumEntities());

//        while (true) {
//            nextTurn();
////            if (System.in.available() > 0) {
////                Thread.sleep(1000);
////                System.out.println();
////            }
//        }
    }

    public void createAction() {
        initActions.add(new SpawnObstacles());
        initActions.add(new SpawnCoin());
        initActions.add(new SpawnCoinHunter());
        initActions.add(new SpawnGhost());
        turnActions.add(new CreaturesMakeTurn());
        //turnActions.add(new заextends от SpawnCoin() вызывать метод при определенных условиях);
    }

    public void pauseSimulation() {
    }

    public void executeAction(List<Action> actions) {
        for (Action action : actions) {
            action.execute(worldMap);
        }
    }
}
