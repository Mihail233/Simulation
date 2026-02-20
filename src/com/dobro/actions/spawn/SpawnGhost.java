package com.dobro.actions.spawn;

import com.dobro.actions.SpawnProbability;
import com.dobro.models.Entity;
import com.dobro.models.Ghost;
import com.dobro.service.WorldMap;

public class SpawnGhost extends SpawnCreature {

    @Override
    public void execute(WorldMap worldMap) {
        Class<? extends Entity> clazz = SpawnDependency.GHOST.getClazz();
        float probability = SpawnProbability.GHOST.getProbability();
        int speed = 1;
        Ghost ghost = new Ghost(speed);
        spawnCreature(worldMap, clazz, probability, ghost);
    }
}
