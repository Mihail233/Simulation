package com.dobro.actions.spawn;

import com.dobro.actions.SpawnProbability;
import com.dobro.actions.strategy.GhostTurnStrategy;
import com.dobro.models.Entity;
import com.dobro.models.Ghost;
import com.dobro.service.WorldMap;

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
