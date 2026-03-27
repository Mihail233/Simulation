package com.dobro.action.spawn;

import com.dobro.strategy.CoinHunterTurnStrategy;
import com.dobro.entity.CoinHunter;
import com.dobro.entity.Entity;

import java.util.function.Supplier;


public class SpawnCoinHunter extends SpawnCreature {

    public SpawnCoinHunter() {
        Class<? extends Entity> clazz = SpawnDependency.COIN_HUNTER.getClazz();
        float probability = SpawnProbability.COIN_HUNTER.getProbability();

        Supplier<Entity> supplier = () -> {
            CoinHunter coinHunter = new CoinHunter(new CoinHunterTurnStrategy());
            coinHunter.setOnPrintNameCallback((currentCell) -> System.out.printf("CoinHunter %s ", currentCell));
            coinHunter.setOnNotFindInteractionCellCallback((currentCell -> System.out.printf("клетка для взаимодействия не найдена %s\n", currentCell)));

            coinHunter.setOnMoveCallback(((currentCell, nextCell) -> System.out.printf("Существо %s переместилось на %s\n", currentCell, nextCell)));
            coinHunter.setOnCollectCallback((interactionCell) -> System.out.printf("Собрал монету %s\n", interactionCell));
            coinHunter.setOnMeetCoinHunterCallback((interactionCell) ->  System.out.printf("встретил coinHunter %s, ход будет пропущен\n", interactionCell));
            coinHunter.setOnMeetGhostCallback((interactionCell) -> System.out.printf("встретил ghost %s, ход будет пропущен\n", interactionCell));
            coinHunter.setOnMeetUnknownEntityCallback((interactionCell) -> System.out.printf("Неизвестное существо на %s\n", interactionCell));
            return coinHunter;
        };

        super(clazz, probability,  supplier);
    }
}
