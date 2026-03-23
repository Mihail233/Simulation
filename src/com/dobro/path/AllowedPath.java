package com.dobro.path;

import com.dobro.entity.*;

public enum AllowedPath {
    GHOST(Ghost.class),
    COIN_HUNTER(CoinHunter.class);

    private final Class<? extends Entity> clazz;

    AllowedPath(Class<? extends Entity> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Entity> getClazz() {
        return clazz;
    }
}
