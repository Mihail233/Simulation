package com.dobro.strategy;

import com.dobro.Cell;
import com.dobro.worldmap.WorldMap;
import com.dobro.worldmap.WorldMapUtils;
import com.dobro.entity.Creature;
import com.dobro.entity.Entity;
import com.dobro.path.PathFinder;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class InteractionCellFinder {

    public <T extends Creature> Optional<Cell> getInteractionCell(Cell currentCell, T entity, WorldMap worldMap, Class<? extends Entity> entityClazz) {
        Optional<PathFinder> pathFinder = getPathFinderToTheNearestTargetCell(currentCell, worldMap, entityClazz);

        if (pathFinder.isPresent()) {
            Cell interactionCell = findInteractionCell(entity.getInteractionDistance(), entity.getSpeed(), pathFinder.get());
            return Optional.of(interactionCell);
        }
        return Optional.empty();
    }

    private Cell findInteractionCell(int interactionDistance, int speed, PathFinder pathFinder) {
        int distanceToCell = pathFinder.getCurrentNodePath().getDistance();

        if (distanceToCell <= interactionDistance) {
            return pathFinder.getCellDependingOnDistance(distanceToCell);

        } else if (distanceToCell == speed) {
            return pathFinder.getCellDependingOnDistance(distanceToCell - interactionDistance);

        } else {
            return pathFinder.getCellDependingOnDistance(speed);
        }
    }

    private Optional<PathFinder> getPathFinderToTheNearestTargetCell(Cell currentCell, WorldMap worldMap, Class<? extends Entity> entityClazz) {
        List<Cell> targets = findAllTargetCells(entityClazz, worldMap);

        return targets.stream()
                .map(cell -> new PathFinder(worldMap, currentCell, cell))
                .filter(PathFinder::isPathFound)
                .min(Comparator.comparing(pathFinder -> pathFinder.getCurrentNodePath().getDistance()));
    }

    private List<Cell> findAllTargetCells(Class<? extends Entity> entityClazz, WorldMap worldMap) {
        return WorldMapUtils.getCellsOfCertainType(entityClazz, worldMap);
    }
}
