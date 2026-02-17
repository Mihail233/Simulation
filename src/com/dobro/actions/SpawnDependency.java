package com.dobro.actions;

import com.dobro.models.CoinHunter;
import com.dobro.models.Entity;
import com.dobro.models.Coin;
import com.dobro.models.Ghost;

import com.dobro.models.Ghost;

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
