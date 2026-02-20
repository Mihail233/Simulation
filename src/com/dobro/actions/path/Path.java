package com.dobro.actions.path;

import com.dobro.models.Entity;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.*;

public class Path {
    private final int DEFAULT_DISTANCE = 0;
    private final int DISTANCE_TO_NEIGHBOR = 1;
    private final WorldMap worldMap;
    private final Cell startingLocation;
    private final Cell endingLocation;
    private final Set<Cell> passedCells = new HashSet<>();
    private final ArrayDeque<NodePath> unexaminedNodePath = new ArrayDeque<>();
    private final HashSet<Class<? extends Entity>> allowedPaths;
    private NodePath currentPath;

    public Path(WorldMap worldMap, Cell startingLocation, Cell endingLocation) {
        this.worldMap = worldMap;
        this.startingLocation = startingLocation;
        this.endingLocation = endingLocation;
        this.allowedPaths = new AllowedPathsProvider().getAllowedPaths();
    }

    public int getDistanceToNeighbor() {
        return DISTANCE_TO_NEIGHBOR;
    }

    public NodePath getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(NodePath currentPath) {
        this.currentPath = currentPath;
    }

    public boolean isPathFound() {
        this.reachEndingNode();
        return isReachEndingCell();
    }

    public void reachEndingNode() {
        initPath();
        while (!isReachDeadEnd() && !isReachEndingCell()) {
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
        ArrayList<NodePath> neighbors = getAllowedNeighboringNodePath();
        if (!neighbors.isEmpty()) {
            addNewNodePaths(neighbors);
        }
        passedCells.add(currentPath.getCurrentCell());
    }

    public boolean isReachEndingCell() {
        return currentPath.getCurrentCell().equals(endingLocation);
    }

    public boolean isReachDeadEnd() {
        return unexaminedNodePath.isEmpty();
    }

    public void addNewNodePaths(ArrayList<NodePath> neighbors) {
        unexaminedNodePath.addAll(neighbors);
    }

    public ArrayList<NodePath> getAllowedNeighboringNodePath() {
        ArrayList<Cell> neighbors = worldMap.getNeighbors(currentPath.getCurrentCell());
        ArrayList<NodePath> allowedNeighboringNodePath = new ArrayList<>();

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

    public Cell getCellDependingOnDistance(int intermediateDistance) {
        NodePath nodePath = this.getCurrentPath();
        while (true) {
            if (intermediateDistance == nodePath.getDistance()) {
                return nodePath.getCurrentCell();
            }
            nodePath = nodePath.getPreviousNodePath();
        }
    }
}
