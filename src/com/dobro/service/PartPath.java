package com.dobro.service;

import java.util.Objects;

public class PartPath {
    //переименовать в узел пути?
    private Cell currentCell;
    private Cell previousCell;
    private Integer distance;

    public PartPath(Cell currentCell, Cell previousCell) {
        this.currentCell = currentCell;
        this.previousCell = previousCell;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public Cell getPreviousCell() {
        return currentCell;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        PartPath partPath = (PartPath) obj;
        return (partPath.getCurrentCell().equals(this.getCurrentCell()));

    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrentCell(), getPreviousCell());
    }
}
