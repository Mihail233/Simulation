package com.dobro.actions.path;

import com.dobro.service.Cell;
import org.w3c.dom.Node;

import java.util.Objects;

public class NodePath {
    private Cell currentCell;
    private NodePath previousNodePath;
    private Integer distance;

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

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public void setPreviousNodePath(NodePath previousNodePath) {
        this.previousNodePath = previousNodePath;
    }
}
