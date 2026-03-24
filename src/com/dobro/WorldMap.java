package com.dobro;

import com.dobro.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WorldMap {
    private final Map<Cell, Entity> entities = new HashMap<>();
    private final Cell originCell = new Cell(0, 0);
    private final int width;
    private final int height;
    private final float spawnRate;

    public WorldMap(int width, int height, float spawnRate) {
        this.width = width;
        this.height = height;
        this.spawnRate = normalizeSpawnRate(spawnRate);
    }

    public float normalizeSpawnRate(float spawnRate) {
        float scale = 0.125f;
        float offset = 0.875f;
        return scale * spawnRate + offset;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell getOriginWorldMap() {
        return originCell;
    }

    public Optional<Entity> getEntity(Cell cell) {
        return Optional.ofNullable(this.getEntities().get(cell));
    }

    public Map<Cell, Entity> getEntities() {
        return new HashMap<>(entities);
    }

    public void setEntity(Cell cell, Entity entity) {
        validate(cell);
        entities.put(cell, entity);
    }

    private void validate(Cell cell) {
        if (WorldMapUtils.isOffTheMap(cell, this)) {
            throw new RuntimeException(String.format("Клетка %s вне границы", cell));
        }
    }

    public void removeEntity(Cell cell) {
        entities.remove(cell);
    }

    public float getSpawnRate() {
        return spawnRate;
    }
}
