package com.dobro.actions.strategy;

import com.dobro.models.Creature;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.Optional;

public interface TurnStrategy {
    <T extends Creature> Optional<Cell> getInteractionCell(Cell currentLocation, T entity, WorldMap worldMap);
}
