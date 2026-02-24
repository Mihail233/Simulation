package com.dobro.actions.strategy;

import com.dobro.actions.path.Path;
import com.dobro.models.Creature;
import com.dobro.models.Entity;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.ArrayList;
import java.util.Comparator;
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
            //если брать interactionDistance - можно получить несуществующую клетку, ведь путь проложен только до distanceToTarget, но не до interactionDistance
            return pathToNearestTarget.getCellDependingOnDistance(distanceToTarget);

        } else if (distanceToTarget == speed) {
            return pathToNearestTarget.getCellDependingOnDistance(distanceToTarget - interactionDistance);

        } else {
            return pathToNearestTarget.getCellDependingOnDistance(speed);
        }
    }

    public Optional<Path> findPathToNearestTarget(Cell location, WorldMap worldMap, Class<? extends Entity> targetClass) {
        ArrayList<Cell> targets = findAllTargets(targetClass, worldMap);

        return targets.stream()
                .map(cell -> new Path(worldMap, location, cell))
                .filter(Path::isPathFound)
                .min(Comparator.comparing(path -> path.getCurrentNodePath().getDistance()));
    }

    public ArrayList<Cell> findAllTargets(Class<? extends Entity> targetClass, WorldMap worldMap) {
        return worldMap.getCellsOfCertainType(targetClass);
    }
}
