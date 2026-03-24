package com.dobro.actions.spawn;

import com.dobro.WorldMapUtils;
import com.dobro.entity.Coin;
import com.dobro.WorldMap;

public class SpawnExtraCoin extends SpawnCoin {
    @Override
    public void execute(WorldMap worldMap) {
        if (isFewCoinsLeft(worldMap)) {
            super.execute(worldMap);
        }
    }

    private boolean isFewCoinsLeft(WorldMap worldMap) {
        int minNumberOfCoins = 2;
        int remainingCoins = WorldMapUtils.getCellsOfCertainType(Coin.class, worldMap).size();
        return remainingCoins < minNumberOfCoins;
    }
}
