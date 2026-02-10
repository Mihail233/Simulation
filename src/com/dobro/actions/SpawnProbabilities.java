package com.dobro.actions;

public enum SpawnProbabilities {
    OBSTACLE( 0.18f),
    COIN(0.03f),
    ROCK( 0.01f);

    private final float spawnProbability;

    SpawnProbabilities(float spawnProbability) {
        this.spawnProbability = spawnProbability;
    }

    public float getSpawnProbability() {
        return spawnProbability;
    }
}
