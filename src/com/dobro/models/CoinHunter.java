package com.dobro.models;

import com.dobro.actions.strategy.TurnStrategy;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.Optional;

public class CoinHunter extends Creature {
    private static int numberOfCoinsCollected = 0;
    private final TurnStrategy turnStrategy;

    public CoinHunter(TurnStrategy turnStrategy) {
        int interactionDistance = 1;
        int speed = 2;
        int healthPoints = 1;
        super(interactionDistance, speed, healthPoints);
        this.turnStrategy = turnStrategy;
    }

    public void incrementNumberOfCoinsCollected() {
        numberOfCoinsCollected++;
    }

    public static int getNumberOfCoinsCollected() {
        return numberOfCoinsCollected;
    }

    @Override
    public void makeTurn(Cell location, WorldMap worldMap) {
        Optional<Cell> interactionCell = turnStrategy.getInteractionCell(location, this, worldMap);
        interactionCell.ifPresentOrElse(presentInteractionCell -> {
                    Optional<? extends Entity> entity = worldMap.getEntity(presentInteractionCell);
                    entity.ifPresentOrElse(presentEntity -> {
                                System.out.printf("CoinHunter  %s ", location);
                                switch (presentEntity) {
                                    case Coin coin -> collectCoin(presentInteractionCell, worldMap);
                                    case CoinHunter coinHunter ->
                                            System.out.printf("встретил coinHunter %s, ход будет пропущен", presentInteractionCell);
                                    case Ghost ghost ->
                                            System.out.printf("встретил ghost %s, ход будет пропущен", presentInteractionCell);
                                    default -> System.out.print("Неизвестная стратегия");
                                }
                            },
                            () -> super.makeMove(location, presentInteractionCell, worldMap));
                },
                () -> System.out.printf("сущность для взаимодействия не найдена %s", location));
        System.out.println();
    }

    public void collectCoin(Cell location, WorldMap worldMap) {
        worldMap.removeEntity(location);
        incrementNumberOfCoinsCollected();
        System.out.printf("Собрал монету %s", location);
    }
}
