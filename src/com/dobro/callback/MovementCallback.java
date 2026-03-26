package com.dobro.callback;

import com.dobro.Cell;

public interface MovementCallback {
    void execute(Cell currentCell, Cell nextCell);
}
