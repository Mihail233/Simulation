package com.dobro.strategy;

import com.dobro.WorldMapUtils;
import com.dobro.path.Path;
import com.dobro.entity.Creature;
import com.dobro.entity.Entity;
import com.dobro.Cell;
import com.dobro.WorldMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class InteractionCellFinder {
    public <T extends Creature> Optional<Cell> getInteractionCell(Cell location, T entity, WorldMap worldMap, Class<? extends Entity> targetClass) {
        Optional<Path> pathToNearestTarget = findPathToNearestTarget(location, worldMap, targetClass);

        if (pathToNearestTarget.isPresent()) {
            Cell interactionCell = findInteractionCell(entity.getInteractionDistance(), entity.getSpeed(), pathToNearestTarget.get());
            return Optional.of(interactionCell);
        }
        return Optional.empty();
    }

    public Cell findInteractionCell(int interactionDistance, int speed, Path pathToNearestTarget) {
        int distanceToTarget = pathToNearestTarget.getCurrentNodePath().getDistance();

        if (distanceToTarget <= interactionDistance) {
            return pathToNearestTarget.getCellDependingOnDistance(distanceToTarget);

        } else if (distanceToTarget == speed) {
            return pathToNearestTarget.getCellDependingOnDistance(distanceToTarget - interactionDistance);

        } else {
            return pathToNearestTarget.getCellDependingOnDistance(speed);
        }
    }

    public Optional<Path> findPathToNearestTarget(Cell location, WorldMap worldMap, Class<? extends Entity> targetClass) {
        List<Cell> targets = findAllTargets(targetClass, worldMap);

        return targets.stream()
                .map(cell -> new Path(worldMap, location, cell))
                .filter(Path::isPathFound)
                .min(Comparator.comparing(path -> path.getCurrentNodePath().getDistance()));
    }

    public List<Cell> findAllTargets(Class<? extends Entity> targetClass, WorldMap worldMap) {
        return WorldMapUtils.getCellsOfCertainType(targetClass, worldMap);
    }
}
