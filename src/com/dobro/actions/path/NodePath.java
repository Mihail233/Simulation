package com.dobro.actions.path;

import com.dobro.service.Cell;

public class NodePath {
    private final Integer distance;
    private final Cell currentCell;
    private final NodePath previousNodePath;

    public NodePath(Cell currentCell, NodePath previousNodePath, int distance) {
        this.currentCell = currentCell;
        this.previousNodePath = previousNodePath;
        this.distance = distance;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public NodePath getPreviousNodePath() {
        return previousNodePath;
    }

    public int getDistance() {
        return distance;
    }
}
