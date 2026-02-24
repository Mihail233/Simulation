package com.dobro.actions.strategy;

import com.dobro.models.CoinHunter;
import com.dobro.models.Creature;
import com.dobro.models.Entity;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

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
