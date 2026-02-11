package com.dobro.actions;

import com.dobro.models.*;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.List;
import java.util.function.Supplier;

public class SpawnObstacles extends Spawn {
    private final List<Supplier<? extends Entity>> obstaclesFactory = List.of(Candle::new, Rock::new, Vase::new);

    @Override
    public void spawnEntity(Cell spawnLocation, WorldMap worldMap) {
        if (super.isPlaceEntity(worldMap.getSpawnRate(), SpawnProbabilities.OBSTACLE.getSpawnProbability())) {
            int indexRandomObstacle = (int) (getRandomNumber() * obstaclesFactory.size());
            worldMap.setEntity(spawnLocation, obstaclesFactory.get(indexRandomObstacle).get());
        } else {
            worldMap.setEntity(spawnLocation, new GraniteBlock());
        }
    }
}
