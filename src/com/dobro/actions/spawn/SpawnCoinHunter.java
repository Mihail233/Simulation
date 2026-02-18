package com.dobro.actions.spawn;

import com.dobro.actions.CoinHunterTurnStrategy;
import com.dobro.actions.SpawnProbability;
import com.dobro.models.CoinHunter;
import com.dobro.models.Entity;
import com.dobro.service.WorldMap;

public class SpawnCoinHunter extends SpawnCreature {

    @Override
    public void execute(WorldMap worldMap) {
        Class<? extends Entity> clazz = SpawnDependency.COIN_HUNTER.getClazz();
        float probability = SpawnProbability.COIN_HUNTER.getProbability();
        CoinHunter coinHunter = new CoinHunter(new CoinHunterTurnStrategy(worldMap));
        spawnCreature(worldMap, clazz, probability, coinHunter);
    }
}
