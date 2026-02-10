package com.dobro.service;

import com.dobro.actions.Action;
import com.dobro.actions.SpawnObstacles;
import com.dobro.actions.SpawnCreatures;
import com.dobro.actions.SpawnCoin;

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

    public void nextTurn() {
        executeAction(turnActions);
    }

    public void startSimulation() {
        executeAction(initActions);
        renderField.displayWorldMap(worldMap);
        System.out.println(worldMap.sumEntities());
//        nextTurn();

        //рендер
        //сгенерировать поле с предметами
        //а потом попросить пользователя ввести существ(сумма существ < пустые поля)
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
        initActions.add(new SpawnCreatures());
        //turnActions.add();
    }

    public void pauseSimulation() {
    }

    public void executeAction(List<Action> actions) {
        for (Action action : actions) {
            action.execute(worldMap);
        }
    }
}
