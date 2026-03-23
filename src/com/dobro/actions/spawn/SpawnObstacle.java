package com.dobro.actions.spawn;

import com.dobro.entity.Candle;
import com.dobro.entity.Entity;
import com.dobro.entity.Rock;
import com.dobro.entity.Vase;
import com.dobro.Cell;
import com.dobro.WorldMap;

import java.util.List;
import java.util.function.Supplier;

public class SpawnObstacle extends Spawn {
    private final List<Supplier<? extends Entity>> obstacleFactory = List.of(Candle::new, Rock::new, Vase::new);

    @Override
    public void execute(WorldMap worldMap) {
        for (int indexRow = worldMap.getOriginWorldMap().getY(); indexRow < worldMap.getMaxWidthField(); indexRow++) {
            for (int indexColumn = worldMap.getOriginWorldMap().getX(); indexColumn < worldMap.getMaxLengthField(); indexColumn++) {
                if (super.isPlaceEntity(worldMap.getSpawnRate(), SpawnProbability.OBSTACLE.getProbability())) {
                    int indexRandomObstacle = (int) (getRandomNumber() * obstacleFactory.size());
                    Cell spawnLocation = new Cell(indexRow, indexColumn);
                    worldMap.setEntity(spawnLocation, obstacleFactory.get(indexRandomObstacle).get());
                }
            }
        }
    }
}
