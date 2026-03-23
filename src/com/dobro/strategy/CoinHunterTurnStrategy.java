package com.dobro.strategy;


import com.dobro.entity.Coin;
import com.dobro.entity.Creature;
import com.dobro.entity.Entity;
import com.dobro.Cell;
import com.dobro.WorldMap;

import java.util.Optional;

public class CoinHunterTurnStrategy implements TurnStrategy {
    private final Class<? extends Entity> coinTargetClass = Coin.class;
    private final InteractionCellFinder InteractionCellFinder;

    public CoinHunterTurnStrategy() {
        InteractionCellFinder = new InteractionCellFinder();
    }

    //можно сделать преоритет (например, размножение существ выше чем получение ближайшей монеты)
    @Override
    public <T extends Creature> Optional<Cell> getInteractionCell(Cell location, T entity, WorldMap worldMap) {
        return InteractionCellFinder.getInteractionCell(location, entity, worldMap, coinTargetClass);
    }
}
