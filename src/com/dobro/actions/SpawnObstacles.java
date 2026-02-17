package com.dobro.actions;

import com.dobro.models.*;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SpawnObstacles extends Spawn {
    private final List<Supplier<? extends Entity>> obstaclesFactory = List.of(Candle::new, Rock::new, Vase::new);

    @Override
    public void execute(WorldMap worldMap) {
        for (int indexRow = 0; indexRow < worldMap.getMaxWidthField(); indexRow++) {
            for (int indexColumn = 0; indexColumn < worldMap.getMaxLengthField(); indexColumn++) {
                if (super.isPlaceEntity(worldMap.getSpawnRate(), SpawnProbability.OBSTACLE.getProbability())) {
                    int indexRandomObstacle = (int) (getRandomNumber() * obstaclesFactory.size());
                    Cell spawnLocation = new Cell(indexRow, indexColumn);
                    worldMap.setEntity(spawnLocation, obstaclesFactory.get(indexRandomObstacle).get());
                }
            }
        }
    }
}
