package com.dobro.actions;

public enum SpawnProbability {
    OBSTACLE( 0.18f),
    COIN(0.04f),
    COIN_HUNTER(0.03f),
    GHOST(0.03f);

    private final float probability;

    SpawnProbability(float probability) {
        this.probability = probability;
    }

    public float getProbability() {
        return probability;
    }
}
