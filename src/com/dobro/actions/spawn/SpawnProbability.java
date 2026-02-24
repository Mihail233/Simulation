package com.dobro.actions.spawn;

public enum SpawnProbability {
    OBSTACLE(0.18f),
    COIN(0.03f),
    COIN_HUNTER(0.04f),
    GHOST(0.02f);

    private final float probability;

    SpawnProbability(float probability) {
        this.probability = probability;
    }

    public float getProbability() {
        return probability;
    }
}
