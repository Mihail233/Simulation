package com.dobro.entity;

import com.dobro.callback.Callback;
import com.dobro.Cell;
import com.dobro.worldmap.WorldMap;
import com.dobro.strategy.TurnStrategy;

public class Ghost extends Creature {
    private final static int SPEED = 1;
    private final static int INTERACTION_DISTANCE = 1;
    private final static int ATTACK = 1;
    private final static int HEALTH_POINTS = 2;
    private Callback onHitCallback;
    private Callback onKillCallback;

    public Ghost(TurnStrategy turnStrategy) {
        super(SPEED, INTERACTION_DISTANCE, turnStrategy, HEALTH_POINTS);
    }

    public void setOnHitCallback(Callback onHitCallback) {
        this.onHitCallback = onHitCallback;
    }

    public void setOnKillCallback(Callback onKillCallback) {
        this.onKillCallback = onKillCallback;
    }


    @Override
    protected void interactWithEntity(Entity interactionEntity, Cell currentCell, Cell interactionCell, WorldMap worldMap) {
        printName(currentCell);
        switch (interactionEntity) {
            case CoinHunter coinHunter -> {
                if (isAlive(coinHunter)) {
                    hit(coinHunter, interactionCell);
                } else {
                    kill(interactionCell, worldMap);
                }
            }
            case Ghost ghost -> meetGhost(interactionCell);
            default -> meetUnknownEntity(interactionCell);
        }
    }

    private <T extends Creature> void hit(T interactionEntity, Cell interactionCell) {
        interactionEntity.setHealthPoints(interactionEntity.getHealthPoints() - ATTACK);
        if (onHitCallback != null) {
            onHitCallback.execute(interactionCell);
        }
    }

    private void kill(Cell interactionCell, WorldMap worldMap) {
        worldMap.removeCell(interactionCell);
        if (onKillCallback != null) {
            onKillCallback.execute(interactionCell);
        }
    }

    private boolean isAlive(CoinHunter coinHunter) {
        return coinHunter.getHealthPoints() > getHealthPointsToDeath();
    }
}
