package com.dobro.actions.spawn;

import com.dobro.models.Coin;
import com.dobro.service.WorldMap;

public class SpawnExtraCoin extends SpawnCoin {
    @Override
    public void execute(WorldMap worldMap) {
        if (worldMap.getCellsOfCertainType(Coin.class).size() < 2) {
            super.execute(worldMap);
        }
    }
}
