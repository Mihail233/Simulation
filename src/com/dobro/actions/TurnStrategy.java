package com.dobro.actions;

import com.dobro.models.Entity;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.Map;

public interface TurnStrategy {
    Map.Entry<Cell, Entity> getStrategy(Cell currentLocation, WorldMap worldMap);
}
