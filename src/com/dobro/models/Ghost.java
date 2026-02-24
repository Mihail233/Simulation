package com.dobro.models;

import com.dobro.actions.strategy.TurnStrategy;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.Optional;

public class Ghost extends Creature {
    private TurnStrategy turnStrategy;
    private int attack;

    public Ghost(TurnStrategy turnStrategy) {
        int interactionDistance = 1;
        int speed = 1;
        int healthPoints = 2;
        attack = 1;
        super(interactionDistance, speed, healthPoints);
        this.turnStrategy = turnStrategy;
    }

    @Override
    public void makeTurn(Cell location, WorldMap worldMap) {
        Optional<Cell> interactionCell = turnStrategy.getInteractionCell(location, this, worldMap);
        interactionCell.ifPresentOrElse(presentInteractionCell -> {
                    Optional<? extends Entity> entity = worldMap.getEntity(presentInteractionCell);
                    entity.ifPresentOrElse(presentEntity -> {
                                System.out.printf("Ghost %s ", location);
                                switch (presentEntity) {
                                    case CoinHunter coinHunter -> {
                                        hitEntity(presentInteractionCell, coinHunter);
                                        if (coinHunter.getHealthPoints() < 1) {
                                            initDeath(presentInteractionCell, worldMap);
                                        }
                                    }
                                    case Ghost ghost ->
                                            System.out.printf("встретило ghost %s, ход будет пропущен", presentInteractionCell);
                                    default -> System.out.print("Неизвестная стратегия");
                                }
                            },
                            () -> super.makeMove(location, presentInteractionCell, worldMap));
                },
                () -> System.out.printf("сущность для взаимодействия не найдено %s ", location));
        System.out.println();
    }

    public <T extends Creature> void hitEntity(Cell locationCoinHunter, T entity) {
        entity.setHealthPoints(entity.getHealthPoints() - attack);
        System.out.printf("ударил coinHunter %s на %d\n", locationCoinHunter, attack);
    }
}
