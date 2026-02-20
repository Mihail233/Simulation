package com.dobro.actions;


import com.dobro.actions.path.Path;
import com.dobro.models.Coin;
import com.dobro.models.Entity;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class CoinHunterTurnStrategy implements TurnStrategy {

    //находится наиближайшая цель, если возращается пустая клетка значит нужно переместится, если предмет значит нужно взять
    //просто сделать преоритет на if(например, размножение существ выше чем получение ближайшей монеты и потом просто return)
    @Override
    public Cell getStrategy(Cell location, int speed, WorldMap worldMap) {
        Optional<Path> pathToNearestTarget = findPathToNearestTarget(location, worldMap);
        //ПЕРЕДЕЛАТЬ БЛОК
        if (pathToNearestTarget.isPresent()) {
            Cell cell = findCellToInteraction(speed, pathToNearestTarget.get());
            System.out.println(location + " до " + cell);
            return cell;
        }
        System.out.println("Стратегия не найдена");
        return null;
    }

    //взять можно ближайшее entity, но при этом можно передвинуться в зависимости от скорости
    public Cell findCellToInteraction(int speed, Path pathToNearestTarget) {
        int intermediateDistance = pathToNearestTarget.getCurrentPath().getDistance();
        int distanceToNeighbor = pathToNearestTarget.getDistanceToNeighbor();
        if (intermediateDistance == distanceToNeighbor) {
            return pathToNearestTarget.getCellDependingOnDistance(distanceToNeighbor);
        }
        if (intermediateDistance == speed) {
            return pathToNearestTarget.getCellDependingOnDistance(speed - distanceToNeighbor);
        } else {
            return pathToNearestTarget.getCellDependingOnDistance(speed);
        }
    }


    public Optional<Path> findPathToNearestTarget(Cell location, WorldMap worldMap) {
        ArrayList<Cell> targets = findAllTargets(Coin.class, worldMap);

        return targets.stream()
                .map(cell -> new Path(worldMap, location, cell))
                .filter(Path::isPathFound)
                .min(Comparator.comparing(path -> path.getCurrentPath().getDistance()));
    }

    public ArrayList<Cell> findAllTargets(Class<? extends Entity> clazz, WorldMap worldMap) {
        return worldMap.getCellsOfCertainType(clazz);
    }
}
