package com.dobro.service;

import java.util.Objects;

public class NodePath {
    //переименовать в узел пути?
    private Cell currentCell;
    private Cell previousCell;
    private Integer distance;

    public NodePath(Cell currentCell, Cell previousCell) {
        this.currentCell = currentCell;
        this.previousCell = previousCell;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public Cell getPreviousCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public void setPreviousCell(Cell previousCell) {
        this.previousCell = previousCell;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        NodePath NodePath = (NodePath) obj;
        return (NodePath.getCurrentCell().equals(this.getCurrentCell()) && NodePath.getPreviousCell().equals(this.getPreviousCell()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrentCell(), getPreviousCell());
    }
}
