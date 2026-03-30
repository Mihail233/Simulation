package com.dobro.entity;

import com.dobro.callback.Callback;
import com.dobro.Cell;
import com.dobro.worldmap.WorldMap;
import com.dobro.strategy.TurnStrategy;

public class CoinHunter extends Creature {
    private final static int SPEED = 2;
    private final static int INTERACTION_DISTANCE = 1;
    private final static int HEALTH_POINTS = 1;
    private Callback onMeetCoinHunterCallback;
    private Callback onCollectCallback;

    public CoinHunter(TurnStrategy turnStrategy) {
        super(SPEED, INTERACTION_DISTANCE, turnStrategy, HEALTH_POINTS);
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
