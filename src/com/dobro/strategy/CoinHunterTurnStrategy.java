package com.dobro.strategy;


import com.dobro.Cell;
import com.dobro.worldmap.WorldMap;
import com.dobro.action.spawn.SpawnDependency;
import com.dobro.entity.Creature;

import java.util.Optional;

public class CoinHunterTurnStrategy implements TurnStrategy {
    private final InteractionCellFinder interactionCellFinder;

    public CoinHunterTurnStrategy() {
        interactionCellFinder = new InteractionCellFinder();
    }

    //можно сделать преоритет (например, размножение существ выше чем получение ближайшей монеты)
    @Override
    public <T extends Creature> Optional<Cell> getInteractionCell(Cell currentCell, T entity, WorldMap worldMap) {
        return interactionCellFinder.getInteractionCell(currentCell, entity, worldMap, SpawnDependency.COIN_HUNTER.getClazz());
    }
}
