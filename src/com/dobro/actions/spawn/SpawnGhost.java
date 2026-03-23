package com.dobro.actions.spawn;

import com.dobro.strategy.GhostTurnStrategy;
import com.dobro.entity.Entity;
import com.dobro.entity.Ghost;
import com.dobro.WorldMap;

import java.util.function.Supplier;

public class SpawnGhost extends SpawnCreature {

    @Override
    public void execute(WorldMap worldMap) {
        Class<? extends Entity> clazz = SpawnDependency.GHOST.getClazz();
        float probability = SpawnProbability.GHOST.getProbability();
        Supplier<Entity> supplier = () -> new Ghost(new GhostTurnStrategy());
        spawnCreature(worldMap, clazz, probability, supplier);
    }
}
