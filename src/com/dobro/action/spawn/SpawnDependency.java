package com.dobro.action.spawn;

import com.dobro.entity.Coin;
import com.dobro.entity.CoinHunter;
import com.dobro.entity.Entity;

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
