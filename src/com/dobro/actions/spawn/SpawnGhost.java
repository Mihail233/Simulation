package com.dobro.actions.spawn;

import com.dobro.entity.Entity;
import com.dobro.entity.Ghost;
import com.dobro.strategy.GhostTurnStrategy;

import java.util.function.Supplier;

public class SpawnGhost extends SpawnCreature {

    public SpawnGhost() {
        Class<? extends Entity> clazz = SpawnDependency.GHOST.getClazz();
        float probability = SpawnProbability.GHOST.getProbability();
        Supplier<Entity> supplier = () -> new Ghost(new GhostTurnStrategy());
        super(clazz, probability, supplier);
    }
}
