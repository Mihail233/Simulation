package com.dobro.service;

import com.dobro.models.*;

import java.util.*;

public class Path {
    private final WorldMap worldMap;
    private final NodePath startingLocation;
    private final NodePath endingLocation;
    private NodePath currentPath;
    private HashSet<String> prohibitedEntities;
    private Set<NodePath> passedNodePath = new HashSet<>();
    private ArrayDeque<NodePath> unexaminedNodePath = new ArrayDeque<>();

    public Path(WorldMap worldMap, Cell startingLocation, Cell endingLocation) {
        this.worldMap = worldMap;
        this.startingLocation = new NodePath(startingLocation, startingLocation);
        this.endingLocation = new NodePath(endingLocation, endingLocation);
        this.prohibitedEntities = createProhibitedEntities();
    }

    public void setCurrentPath(NodePath currentPath) {
        this.currentPath = currentPath;
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
        ArrayList<NodePath> neighbors = getAllowedNeighboringNodePath(currentPath, prohibitedEntities);
        addNewNodePaths(neighbors);
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

    public HashSet<String> createProhibitedEntities() {
        //нельзя создавать путь через монетки и окружение
        HashSet<String> newProhibitedEntities = new HashSet<>();
        newProhibitedEntities.add(Rock.class.getName());
        newProhibitedEntities.add(Candle.class.getName());
        newProhibitedEntities.add(Vase.class.getName());
        newProhibitedEntities.add(Coin.class.getName());
        return newProhibitedEntities;
    }


    public ArrayList<NodePath> getAllowedNeighboringNodePath(NodePath currentNodePath, HashSet<String> prohibitedEntities) {
        ArrayList<Cell> allowedNeighboringCells = this.worldMap.getAllowedNeighboringCells(currentNodePath.getCurrentCell(), endingLocation.getCurrentCell(), prohibitedEntities);
        ArrayList<NodePath> allowedNeighboringNodePath = new ArrayList<>();
        for (Cell cell : allowedNeighboringCells) {
            NodePath NodePath = new NodePath(cell, currentNodePath.getCurrentCell());
            if (!passedNodePath.contains(NodePath)) {
                allowedNeighboringNodePath.add(NodePath);
            }
        }
        return allowedNeighboringNodePath;
    }

    public boolean isPathFound() {
        this.reachEndingNode();
        return currentPath.equals(endingLocation);
    }
}
