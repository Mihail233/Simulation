package com.dobro.path;

import com.dobro.WorldMapUtils;
import com.dobro.entity.Entity;
import com.dobro.Cell;
import com.dobro.WorldMap;

import java.util.*;

public class Path {
    private final static int DISTANCE_TO_NEIGHBOR = 1;
    private final WorldMap worldMap;
    private final Cell startingLocation;
    private final Cell endingLocation;
    private final Set<Cell> passedCells = new HashSet<>();
    private final ArrayDeque<NodePath> unexaminedNodePath = new ArrayDeque<>();
    private final Set<Class<? extends Entity>> allowedPaths;
    private NodePath currentPath;

    public Path(WorldMap worldMap, Cell startingLocation, Cell endingLocation) {
        this.worldMap = worldMap;
        this.startingLocation = startingLocation;
        this.endingLocation = endingLocation;
        this.allowedPaths = new AllowedPathsProvider().getAllowedPaths();
    }

    public NodePath getCurrentNodePath() {
        return currentPath;
    }

    public void setCurrentPath(NodePath currentPath) {
        this.currentPath = currentPath;
    }

    public boolean isPathFound() {
        this.reachEndingNode();
        return isReachEndingLocation();
    }

    public void reachEndingNode() {
        initPath();
        while (!isAllCellsPassed() && !isReachEndingLocation()) {
            reachNodePath();
        }
    }

    public void initPath() {
        NodePath dummyNodePath = new NodePath(null, null, -1);
        NodePath startingNodePath = new NodePath(startingLocation, dummyNodePath, 0);
        setCurrentPath(startingNodePath);
        unexaminedNodePath.add(currentPath);
    }

    public void reachNodePath() {
        NodePath currentPath = unexaminedNodePath.removeFirst();
        setCurrentPath(currentPath);
        List<NodePath> allowedNeighboringNodePath = getAllowedNeighboringNodePath();
        if (!allowedNeighboringNodePath.isEmpty()) {
            addNewNodePaths(allowedNeighboringNodePath);
        }
        passedCells.add(currentPath.getCurrentCell());
    }

    public boolean isReachEndingLocation() {
        return currentPath.getCurrentCell().equals(endingLocation);
    }

    public boolean isAllCellsPassed() {
        return unexaminedNodePath.isEmpty();
    }

    public void addNewNodePaths(List<NodePath> neighbors) {
        unexaminedNodePath.addAll(neighbors);
    }

    public List<NodePath> getAllowedNeighboringNodePath() {
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

    public boolean hasAllowing(Cell neighbor) {
        return (hasEndingLocation(neighbor) || hasInAllowedPaths(neighbor)) && !hasInPassedCells(neighbor);
    }

    public boolean hasInAllowedPaths(Cell neighbor) {
        Optional<? extends Entity> entity = worldMap.getEntity(neighbor);
        if (entity.isPresent()) {
            Class<? extends Entity> clazz = worldMap.getEntities().get(neighbor).getClass();
            return allowedPaths.contains(clazz);
        }
        return true;
    }

    public boolean hasEndingLocation(Cell neighbor) {
        return neighbor.equals(endingLocation);
    }

    public boolean hasInPassedCells(Cell cell) {
        return passedCells.contains(cell);
    }

    public Cell getCellDependingOnDistance(int distanceToTarget) {
        NodePath currentNodePath = this.getCurrentNodePath();
        while (true) {
            if (distanceToTarget == currentNodePath.getDistance()) {
                return currentNodePath.getCurrentCell();
            }
            currentNodePath = currentNodePath.getPreviousNodePath();
        }
    }
}
