package com.dobro.entity;

import com.dobro.callback.Callback;
import com.dobro.Cell;
import com.dobro.worldmap.WorldMap;
import com.dobro.callback.MovementCallback;
import com.dobro.strategy.TurnStrategy;

import java.util.Optional;

abstract public class Creature extends Entity {
    private static final int HEALTH_POINTS_TO_DEATH = 0;
    private final int speed;
    private final int interactionDistance;
    private int healthPoints;
    private final TurnStrategy turnStrategy;
    private Callback onPrintNameCallback;
    private Callback onNotFindInteractionCellCallback;
    private Callback onMeetGhostCallback;
    private Callback onMeetUnknownEntityCallback;
    private MovementCallback onMoveCallback;

    public Creature(int speed, int interactionDistance, int healthPoints, TurnStrategy turnStrategy) {
        this.speed = speed;
        this.interactionDistance = interactionDistance;
        this.healthPoints = healthPoints;
        this.turnStrategy = turnStrategy;
    }

    protected abstract void interactWithEntity(Entity entity, Cell currentCell, Cell interactionCell, WorldMap worldMap);

    public int getInteractionDistance() {
        return interactionDistance;
    }

    public int getSpeed() {
        return speed;
    }

    protected void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    protected int getHealthPoints() {
        return healthPoints;
    }

    protected int getHealthPointsToDeath() {
        return HEALTH_POINTS_TO_DEATH;
    }

    public void setOnMeetUnknownEntityCallback(Callback onMeetUnknownEntityCallback) {
        this.onMeetUnknownEntityCallback = onMeetUnknownEntityCallback;
    }

    public void setOnPrintNameCallback(Callback onPrintNameCallback) {
        this.onPrintNameCallback = onPrintNameCallback;
    }

    public void setOnNotFindInteractionCellCallback(Callback onNotFindInteractionCellCallback) {
        this.onNotFindInteractionCellCallback = onNotFindInteractionCellCallback;
    }

    public void setOnMeetGhostCallback(Callback onMeetGhostCallback) {
        this.onMeetGhostCallback = onMeetGhostCallback;
    }

    public void setOnMoveCallback(MovementCallback onMoveCallback) {
        this.onMoveCallback = onMoveCallback;
    }

    public void makeTurn(Cell currentCell, WorldMap worldMap) {
        Optional<Cell> interactionCell = turnStrategy.getInteractionCell(currentCell, this, worldMap);

        if (interactionCell.isPresent()) {
            Cell presentInteractionCell = interactionCell.get();
            interactWithCell(currentCell, presentInteractionCell, worldMap);
        } else {
            notFindInteractionCell(currentCell);
        }
        //System.out.println();
    }

    private void interactWithCell(Cell currentCell, Cell interactionCell, WorldMap worldMap) {
        Optional<? extends Entity> interactionEntity = worldMap.getEntity(interactionCell);
        if (interactionEntity.isPresent()) {
            Entity presentEntity = interactionEntity.get();
            interactWithEntity(presentEntity, currentCell, interactionCell, worldMap);
        } else {
            interactWithEmptyCell(currentCell, interactionCell, worldMap);
        }
    }

    private void interactWithEmptyCell(Cell currentCell, Cell interactionCell, WorldMap worldMap) {
        move(currentCell, interactionCell, worldMap);
    }

    private void move(Cell currentCell, Cell nextCell, WorldMap worldMap) {
        worldMap.removeCell(currentCell);
        worldMap.setEntity(nextCell, this);
        if (onMoveCallback != null) {
            onMoveCallback.execute(currentCell, nextCell);
        }
    }

    protected void printName(Cell currentCell) {
        if (onPrintNameCallback != null) {
            onPrintNameCallback.execute(currentCell);
        }
    }

    protected void meetGhost(Cell interactionCell) {
        if (onMeetGhostCallback != null) {
            onMeetGhostCallback.execute(interactionCell);
        }
    }

    protected void meetUnknownEntity(Cell interactionCell) {
        if (onMeetUnknownEntityCallback != null) {
            onMeetUnknownEntityCallback.execute(interactionCell);
        }
    }

    private void notFindInteractionCell(Cell currentCell) {
        if (onNotFindInteractionCellCallback != null) {
            onNotFindInteractionCellCallback.execute(currentCell);
        }
    }
}
