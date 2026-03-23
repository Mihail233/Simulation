package com.dobro.strategy;

import com.dobro.entity.CoinHunter;
import com.dobro.entity.Creature;
import com.dobro.entity.Entity;
import com.dobro.Cell;
import com.dobro.WorldMap;

import java.util.Optional;

public class GhostTurnStrategy implements TurnStrategy {
    private final Class<? extends Entity> coinHunterTargetClass = CoinHunter.class;
    private final InteractionCellFinder InteractionCellFinder;

    public GhostTurnStrategy() {
        InteractionCellFinder = new InteractionCellFinder();
    }

    @Override
    public <T extends Creature> Optional<Cell> getInteractionCell(Cell location, T entity, WorldMap worldMap) {
        return InteractionCellFinder.getInteractionCell(location, entity, worldMap, coinHunterTargetClass);
    }
}
