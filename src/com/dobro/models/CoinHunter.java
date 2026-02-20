package com.dobro.models;

import com.dobro.actions.TurnStrategy;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.Map;
import java.util.Optional;

//CoinHunterTurnStrategy - подготавливает стратегию, coinHunter - реализует стратегию
public class CoinHunter extends Creature {
    TurnStrategy turnStrategy;

    public CoinHunter(TurnStrategy turnStrategy, int speed) {
        super(speed);
        this.turnStrategy = turnStrategy;
    }

    //существо делает за 1 ход - 1 действие
    @Override
    public void makeTurn(Cell location, WorldMap worldMap) {
        Cell strategy = turnStrategy.getStrategy(location, super.getSpeed(), worldMap);
        if (worldMap.getEntity(strategy).isEmpty()) {
            super.makeMove(location, strategy, worldMap);
        }

        //Сначала проверка на клетку: если существа нет -> пустая клетка
        //Если существо есть: в зависимости от полученного существа выполняются действия

//        collectCoin(new Cell(1, 1));
//        makeMove(new Cell(1, 1));
    }

    public void collectCoin(Cell cell) {
        System.out.printf("Собрал монету %d %d\n", cell.getX(), cell.getY());
    }
}
