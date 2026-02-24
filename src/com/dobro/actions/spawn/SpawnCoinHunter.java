package com.dobro.actions.spawn;

import com.dobro.actions.SpawnProbability;
import com.dobro.actions.strategy.CoinHunterTurnStrategy;
import com.dobro.actions.strategy.GhostTurnStrategy;
import com.dobro.models.CoinHunter;
import com.dobro.models.Entity;
import com.dobro.models.Ghost;
import com.dobro.service.WorldMap;
import java.util.function.Supplier;


public class SpawnCoinHunter extends SpawnCreature {

    @Override
    public void execute(WorldMap worldMap) {
        Class<? extends Entity> clazz = SpawnDependency.COIN_HUNTER.getClazz();
        float probability = SpawnProbability.COIN_HUNTER.getProbability();
        Supplier<Entity> supplier = () -> new CoinHunter(new CoinHunterTurnStrategy());
        spawnCreature(worldMap, clazz, probability, supplier);
    }
}
