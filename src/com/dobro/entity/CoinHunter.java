package com.dobro.entity;

import com.dobro.callback.Callback;
import com.dobro.Cell;
import com.dobro.worldmap.WorldMap;
import com.dobro.item.MoneyBag;
import com.dobro.strategy.TurnStrategy;

public class CoinHunter extends Creature {
    private final static int SPEED = 2;
    private final static int INTERACTION_DISTANCE = 1;
    private final static int HEALTH_POINTS = 1;
    private final MoneyBag moneyBag = new MoneyBag();
    private Callback onMeetCoinHunterCallback;
    private Callback onCollectCallback;

    public CoinHunter(TurnStrategy turnStrategy) {
        super(SPEED, INTERACTION_DISTANCE, HEALTH_POINTS, turnStrategy);
    }

    public MoneyBag getMoneyBag() {
        return moneyBag;
    }

    public void setOnMeetCoinHunterCallback(Callback onMeetCoinHunterCallback) {
        this.onMeetCoinHunterCallback = onMeetCoinHunterCallback;
    }

    public void setOnCollectCallback(Callback onCollectCallback) {
        this.onCollectCallback = onCollectCallback;
    }

    @Override
    protected void interactWithEntity(Entity interactionEntity, Cell currentCell, Cell interactionCell, WorldMap worldMap) {
        printName(currentCell);
        switch (interactionEntity) {
            case Coin coin -> collect(interactionCell, worldMap);
            case CoinHunter coinHunter -> meetCoinHunter(interactionCell);
            case Ghost ghost -> meetGhost(interactionCell);
            default -> meetUnknownEntity(interactionCell);
        }
    }

    private void collect(Cell interactionCell, WorldMap worldMap) {
        worldMap.removeCell(interactionCell);
        moneyBag.incrementNumberOfCoins();
        if (onCollectCallback != null) {
            onCollectCallback.execute(interactionCell);
        }
    }

    private void meetCoinHunter(Cell interactionCell) {
        if (onMeetCoinHunterCallback != null) {
            onMeetCoinHunterCallback.execute(interactionCell);
        }
    }
}
