package com.dobro.actions.spawn;

import com.dobro.strategy.CoinHunterTurnStrategy;
import com.dobro.entity.CoinHunter;
import com.dobro.entity.Entity;
import com.dobro.WorldMap;

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
