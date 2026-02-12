package com.dobro.actions.path;

import com.dobro.models.Entity;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.*;

public class Path {
    private final WorldMap worldMap;
    private final NodePath startingLocation;
    private final NodePath endingLocation;
    private final Set<NodePath> passedNodePath = new HashSet<>();
    private final ArrayDeque<NodePath> unexaminedNodePath = new ArrayDeque<>();
    private final HashSet<Class<? extends Entity>> allowedPaths;
    private NodePath currentPath;

    public Path(WorldMap worldMap, Cell startingLocation, Cell endingLocation) {
        this.worldMap = worldMap;
        this.startingLocation = new NodePath(startingLocation, startingLocation);
        this.endingLocation = new NodePath(endingLocation, endingLocation);
        this.allowedPaths = new AllowedPathsProvider().getAllowedPaths();
    }

    public void setCurrentPath(NodePath currentPath) {
        this.currentPath = currentPath;
    }

    public boolean isPathFound() {
        this.reachEndingNode();
        return currentPath.equals(endingLocation);
    }

    public void reachEndingNode() {
        initPath();
        while (!isReachDeadEnd() && !isReachEndingCell()) {
            reachNodePath();
        }
    }

    public void initPath() {
        setCurrentPath(startingLocation);
        unexaminedNodePath.add(currentPath);
    }

    public void reachNodePath() {
        setCurrentPath(unexaminedNodePath.removeFirst());
        ArrayList<NodePath> neighbors = getAllowedNeighboringNodePath();
        if (!neighbors.isEmpty()) {
            addNewNodePaths(neighbors);
        }
        passedNodePath.add(currentPath);
    }

    public boolean isReachEndingCell() {
        return currentPath.getCurrentCell().equals(endingLocation.getCurrentCell());
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
            if (hasEndingLocation(neighbors) || isAllowed(neighbor)) {
                NodePath nodePath = new NodePath(neighbor, currentPath.getCurrentCell());
                if (!hasInPassedNodePath(nodePath)) {
                    allowedNeighboringNodePath.add(nodePath);
                }
            }
        }
        return allowedNeighboringNodePath;
    }

    public boolean isAllowed(Cell neighbor) {
        Class<? extends Entity> clazz = worldMap.getEntities().get(neighbor).getClass();
        return allowedPaths.contains(clazz);
    }

    public boolean hasEndingLocation(ArrayList<Cell> neighbors) {
        return neighbors.contains(endingLocation.getCurrentCell());
    }

    public boolean hasInPassedNodePath(NodePath nodePath) {
        return passedNodePath.contains(nodePath);
    }
}
