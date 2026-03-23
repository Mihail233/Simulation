package com.dobro.strategy;

import com.dobro.entity.Creature;
import com.dobro.Cell;
import com.dobro.WorldMap;

import java.util.Optional;

public interface TurnStrategy {
    <T extends Creature> Optional<Cell> getInteractionCell(Cell currentLocation, T entity, WorldMap worldMap);
}
