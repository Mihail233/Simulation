package com.dobro.actions;

public enum SpawnProbability {
    OBSTACLE( 0.18f),
    COIN(0.06f),
    ROCK( 0.01f);

    private final float probability;

    SpawnProbability(float probability) {
        this.probability = probability;
    }

    public float getProbability() {
        return probability;
    }
}
