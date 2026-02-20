package com.dobro.models;

import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

public class Ghost extends Creature {

    public Ghost(int speed) {
        super(speed);
    }

    @Override
    public void makeTurn(Cell location, WorldMap worldMap) {

    }
}
