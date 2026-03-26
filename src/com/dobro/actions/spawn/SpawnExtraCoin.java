package com.dobro.actions.spawn;

import com.dobro.worldmap.WorldMapUtils;
import com.dobro.entity.Coin;
import com.dobro.worldmap.WorldMap;

public class SpawnExtraCoin extends SpawnCoin {
    @Override
    public void execute(WorldMap worldMap) {
        if (isFewCoinsLeft(worldMap)) {
            super.execute(worldMap);
        }
    }

    private boolean isFewCoinsLeft(WorldMap worldMap) {
        int minimumNumberCellsWithCoins = 2;

        int numberCellsWithCoins = WorldMapUtils.getCellsOfCertainType(Coin.class, worldMap).size();
        return minimumNumberCellsWithCoins > numberCellsWithCoins;
    }
}
