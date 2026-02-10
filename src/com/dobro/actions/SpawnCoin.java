package com.dobro.actions;

import com.dobro.models.Coin;
import com.dobro.service.Cell;
import com.dobro.service.Path;
import com.dobro.service.WorldMap;

import java.util.ArrayList;

public class SpawnCoin extends Spawn {
    //переимновать
    private ArrayList<Cell> cells = new ArrayList<>();

    @Override
    public void spawnEntity(Cell currentCell, WorldMap worldMap) {
        if (super.isPlaceEntity(worldMap.getSpawnRate(), SpawnProbabilities.COIN.getSpawnProbability())) {
            placeCreatureOnEmptyCell(currentCell, worldMap);
        }
    }

    public void placeCreatureOnEmptyCell(Cell currentCell, WorldMap worldMap) {
        if (worldMap.isEmptyCell(currentCell)) {
            Coin newCoin = new Coin();
            worldMap.setEntity(currentCell, newCoin);
            if (!cells.isEmpty()) {
                for (Cell cell: cells) {
                    Path path = new Path(worldMap, currentCell, cell);
                    path.reachEndingPath();
                }
            }
            cells.add(currentCell);
//            if (Path.isAllFind) {
//                worldMap.setEntity(cell, new Coin());
//
//            }
        }
    }
}
