package com.dobro.action.spawn;

import com.dobro.Cell;
import com.dobro.worldmap.WorldMap;
import com.dobro.entity.Candle;
import com.dobro.entity.Entity;
import com.dobro.entity.Rock;
import com.dobro.entity.Vase;

import java.util.List;
import java.util.function.Supplier;

public class SpawnObstacle extends Spawn {
    private final List<Supplier<? extends Entity>> obstacleFactory = List.of(Candle::new, Rock::new, Vase::new);

    public SpawnObstacle() {
        super(SpawnProbability.OBSTACLE.getProbability());
    }

    @Override
    public void spawn(WorldMap worldMap, Cell spawnCell) {
        int indexRandomObstacle = (int) (getRandomNumber() * obstacleFactory.size());
        worldMap.setEntity(spawnCell, obstacleFactory.get(indexRandomObstacle).get());
    }
}
