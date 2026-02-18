package com.dobro.actions;


import com.dobro.models.Coin;
import com.dobro.models.Entity;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.ArrayList;
import java.util.Map;

public class CoinHunterTurnStrategy implements TurnStrategy {
    private WorldMap worldMap;

    public CoinHunterTurnStrategy(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    //находится наиближайшая цель, если возращается пустая клетка значит нужно переместится, если предмет значит нужно взять
    //просто сделать преоритет на if(например, размножение существ выше чем получение ближайшей монеты и потом просто return)
    @Override
    public Map.Entry<Cell, Entity> getStrategy(Cell currentLocation, WorldMap worldMap) {
        findNearestTarget(worldMap);


        //return Map.entry(new Cell(0,0), null);
        return null;
    }

    public void findNearestTarget(WorldMap worldMap) {
        ArrayList<Cell> cells = findAllTargets(worldMap);
    }

    public ArrayList<Cell> findAllTargets(WorldMap worldMap) {
        return worldMap.getCellsOfCertainType(Coin.class);
    }
}
