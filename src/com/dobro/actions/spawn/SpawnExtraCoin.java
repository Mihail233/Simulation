package com.dobro.actions.spawn;

import com.dobro.models.Coin;
import com.dobro.service.WorldMap;

public class SpawnExtraCoin extends SpawnCoin {
    @Override
    public void execute(WorldMap worldMap) {
        int remainingEntities = 2;
        if (worldMap.getCellsOfCertainType(Coin.class).size() < remainingEntities) {
            super.execute(worldMap);
        }
    }
}
