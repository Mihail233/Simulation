package com.dobro.path;

import com.dobro.Cell;
import com.dobro.worldmap.WorldMap;
import com.dobro.worldmap.WorldMapUtils;
import com.dobro.entity.Entity;

import java.util.*;

public class PathFinder {
    private final static int DISTANCE_TO_NEIGHBOR = 1;
    private final WorldMap worldMap;
    private final Cell startingCell;
    private final Cell endingCell;
    private final Set<Cell> passedCells = new HashSet<>();
    private final ArrayDeque<NodePath> unexaminedNodePath = new ArrayDeque<>();
    private final Set<Class<? extends Entity>> allowedPaths;
    private NodePath currentPath;

    public PathFinder(WorldMap worldMap, Cell startingCell, Cell endingCell) {
        this.worldMap = worldMap;
        this.startingCell = startingCell;
        this.endingCell = endingCell;
        this.allowedPaths = new AllowedPathsProvider().getAllowedPaths();
    }

    public NodePath getCurrentNodePath() {
        return currentPath;
    }

    private void setCurrentPath(NodePath currentPath) {
        this.currentPath = currentPath;
    }

    public boolean isPathFound() {
        this.findEndingNode();
        return isReachEndingCell();
    }

    public Cell getCellDependingOnDistance(int distanceToCell) {
        NodePath currentNodePath = this.getCurrentNodePath();
        while (true) {
            if (distanceToCell == currentNodePath.getDistance()) {
                return currentNodePath.getCurrentCell();
            }
            currentNodePath = currentNodePath.getPreviousNodePath();
        }
    }

    private void findEndingNode() {
        initPath();
        while (!isAllCellsPassed() && !isReachEndingCell()) {
            reachNodePath();
        }
    }

    private void initPath() {
        NodePath dummyNodePath = new NodePath(null, null, -1);
        NodePath startingNodePath = new NodePath(startingCell, dummyNodePath, 0);
        setCurrentPath(startingNodePath);
        unexaminedNodePath.add(currentPath);
    }

    private void reachNodePath() {
        NodePath currentPath = unexaminedNodePath.removeFirst();
        setCurrentPath(currentPath);
        List<NodePath> allowedNeighboringNodePath = getAllowedNeighboringNodePath();
        if (!allowedNeighboringNodePath.isEmpty()) {
            addNewNodePaths(allowedNeighboringNodePath);
        }
        passedCells.add(currentPath.getCurrentCell());
    }

    private boolean isReachEndingCell() {
        return currentPath.getCurrentCell().equals(endingCell);
    }

    private boolean isAllCellsPassed() {
        return unexaminedNodePath.isEmpty();
    }

    private void addNewNodePaths(List<NodePath> neighbors) {
        unexaminedNodePath.addAll(neighbors);
    }

    private List<NodePath> getAllowedNeighboringNodePath() {
        List<Cell> neighbors = WorldMapUtils.getNeighbors(currentPath.getCurrentCell(), worldMap);
        List<NodePath> allowedNeighboringNodePath = new ArrayList<>();

        for (Cell neighbor : neighbors) {
            if (hasAllowing(neighbor)) {
                NodePath nodePath = new NodePath(neighbor, currentPath, currentPath.getDistance() + DISTANCE_TO_NEIGHBOR);
                allowedNeighboringNodePath.add(nodePath);
            }
        }
        return allowedNeighboringNodePath;
    }

    private boolean hasAllowing(Cell neighbor) {
        return (hasEndingCell(neighbor) || hasInAllowedPaths(neighbor)) && !hasInPassedCells(neighbor);
    }

    private boolean hasInAllowedPaths(Cell neighbor) {
        Optional<? extends Entity> entity = worldMap.getEntity(neighbor);
        if (entity.isPresent()) {
            Class<? extends Entity> clazz = worldMap.getEntities().get(neighbor).getClass();
            return allowedPaths.contains(clazz);
        }
        return true;
    }

    private boolean hasEndingCell(Cell neighbor) {
        return neighbor.equals(endingCell);
    }

    private boolean hasInPassedCells(Cell cell) {
        return passedCells.contains(cell);
    }

}
