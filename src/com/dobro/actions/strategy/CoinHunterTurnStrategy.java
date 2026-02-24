package com.dobro.actions.strategy;


import com.dobro.models.Coin;
import com.dobro.models.Creature;
import com.dobro.models.Entity;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.Optional;

public class CoinHunterTurnStrategy implements TurnStrategy {
    private final Class<? extends Entity> coinTargetClass = Coin.class;
    private final InteractionCellFinder InteractionCellFinder;

    public CoinHunterTurnStrategy() {
        InteractionCellFinder = new InteractionCellFinder();
    }
    //просто сделать преоритет на if(например, размножение существ выше чем получение ближайшей монеты и потом просто return)
    //возращается либо клетка(внутри либо entity, либо пусто), либо возращается Optional.empty

    @Override
    public <T extends Creature> Optional<Cell> getInteractionCell(Cell location, T entity, WorldMap worldMap) {
        return InteractionCellFinder.getInteractionCell(location, entity, worldMap, coinTargetClass);
    }
}
