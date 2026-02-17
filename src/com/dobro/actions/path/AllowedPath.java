package com.dobro.actions.path;

import com.dobro.models.*;

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
