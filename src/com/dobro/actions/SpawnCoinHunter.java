package com.dobro.actions;

import com.dobro.models.CoinHunter;
import com.dobro.models.Entity;
import com.dobro.service.WorldMap;

public class SpawnCoinHunter extends SpawnCreature {

    @Override
    public void execute(WorldMap worldMap) {
        Class<? extends Entity> clazz = SpawnDependency.COIN_HUNTER.getClazz();
        float probability = SpawnProbability.COIN_HUNTER.getProbability();
        CoinHunter coinHunter = new CoinHunter();
        spawnCreature(worldMap, clazz, probability, coinHunter);
    }
}
