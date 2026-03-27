package com.dobro.action.spawn;

import com.dobro.entity.Entity;
import com.dobro.entity.Ghost;
import com.dobro.strategy.GhostTurnStrategy;

import java.util.function.Supplier;

public class SpawnGhost extends SpawnCreature {

    public SpawnGhost() {
        Class<? extends Entity> clazz = SpawnDependency.GHOST.getClazz();
        float probability = SpawnProbability.GHOST.getProbability();

        Supplier<Entity> supplier = () -> {
            Ghost ghost = new Ghost(new GhostTurnStrategy());
            ghost.setOnPrintNameCallback((currentCell) -> System.out.printf("Ghost %s ", currentCell));
            ghost.setOnNotFindInteractionCellCallback((currentCell -> System.out.printf("клетка для взаимодействия не найдена %s\n", currentCell)));
            ghost.setOnMoveCallback(((currentCell, nextCell) -> System.out.printf("Существо %s переместилось на %s\n", currentCell, nextCell)));

            ghost.setOnHitCallback((interactionCell) -> System.out.printf("ударил coinHunter на %s\n", interactionCell));
            ghost.setOnKillCallback((interactionCell) -> System.out.printf("убил существо на %s\n", interactionCell));
            ghost.setOnMeetGhostCallback((interactionCell) -> System.out.printf("встретил ghost %s, ход будет пропущен\n", interactionCell));
            ghost.setOnMeetUnknownEntityCallback((interactionCell) -> System.out.printf("Неизвестное существо на %s\n", interactionCell));
            return ghost;
        };
        super(clazz, probability, supplier);
    }
}
