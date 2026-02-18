package com.dobro.actions.spawn;

import com.dobro.models.CoinHunter;
import com.dobro.models.Entity;
import com.dobro.models.Coin;

public enum SpawnDependency {
    COIN(Coin.class),
    COIN_HUNTER(Coin.class),
    GHOST(CoinHunter.class);

    private final Class<? extends Entity> clazz;

    SpawnDependency(Class<? extends Entity> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Entity> getClazz() {
        return clazz;
    }
}
