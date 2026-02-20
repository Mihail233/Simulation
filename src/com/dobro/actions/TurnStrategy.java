package com.dobro.actions;

import com.dobro.models.Entity;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.Map;
import java.util.Optional;

public interface TurnStrategy {
    Cell getStrategy(Cell currentLocation, int speed, WorldMap worldMap);
}
