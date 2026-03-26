package com.dobro.strategy;

import com.dobro.entity.Creature;
import com.dobro.Cell;
import com.dobro.worldmap.WorldMap;

import java.util.Optional;

public interface TurnStrategy {
    <T extends Creature> Optional<Cell> getInteractionCell(Cell currentCell, T entity, WorldMap worldMap);
}
