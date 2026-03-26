package com.dobro.worldmap;

import com.dobro.Cell;
import com.dobro.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorldMapUtils {
    private final static List<Cell> SHIFT_COORDINATES = List.of(new Cell(0, 1), new Cell(0, -1), new Cell(-1, 0), new Cell(1, 0));

    public static List<Cell> getCellsOfCertainType(Class<? extends Entity> clazz, WorldMap worldMap) {
        List<Cell> cells = new ArrayList<>();
        for (Map.Entry<Cell, Entity> pieceOfWorldMap : worldMap.getEntities().entrySet()) {
            if (pieceOfWorldMap.getValue().getClass().equals(clazz)) {
                cells.add(pieceOfWorldMap.getKey());
            }
        }
        return cells;
    }

    public static boolean hasEntity(Class<? extends Entity> clazz, WorldMap worldMap) {
        return !getCellsOfCertainType(clazz, worldMap).isEmpty();
    }

    public static List<Cell> getNeighbors(Cell cell, WorldMap worldMap) {
        List<Cell> neighbors = new ArrayList<>();
        for (Cell shift : SHIFT_COORDINATES) {
            Cell neighbor = new Cell(cell.x() + shift.x(), cell.y() + shift.y());
            neighbors.add(neighbor);
        }
        neighbors.removeIf(_ -> isOffTheMap(cell, worldMap));
        return neighbors;
    }

    public static boolean isOffTheMap(Cell cell, WorldMap worldMap) {
        boolean isOutsideLeftBorder = cell.x() < worldMap.getOriginWorldMap().x();
        boolean isOutsideRightBorder = cell.x() >= worldMap.getHeight();
        boolean isOutsideTop = cell.y() < worldMap.getOriginWorldMap().y();
        boolean isOutsideBottomBorder = cell.y() >= worldMap.getWidth();
        return isOutsideLeftBorder || isOutsideRightBorder || isOutsideTop || isOutsideBottomBorder;
    }
}