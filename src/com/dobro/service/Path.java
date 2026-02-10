package com.dobro.service;

import com.dobro.models.*;

import java.util.*;

public class Path {
    private WorldMap worldMap;
    private final Cell startingCell;
    private final Cell endingCell;
    private HashSet<String> prohibitedEntities;
    private Set<PartPath> passedPartPath = new HashSet<>();
    private ArrayDeque<PartPath> unexaminedPartPath = new ArrayDeque<>();

    public Path(WorldMap worldMap, Cell startingCell, Cell endingCell) {
        this.startingCell = startingCell;
        this.endingCell = endingCell;
        this.prohibitedEntities =  createProhibitedEntities();
        this.worldMap = worldMap;
    }

    public void reachEndingPath() {
        //заменить null начало точка отсчета
        PartPath currentPartPath = new PartPath(startingCell, null);
        PartPath endingPath = null;
        unexaminedPartPath.add(currentPartPath);
        while (!unexaminedPartPath.isEmpty() && endingPath == null) {
            endingPath = reachPartPath(unexaminedPartPath.removeFirst());
        }
    }

    public PartPath reachPartPath(PartPath currentPartPath) {
        if (isReachEndingCell(currentPartPath)) {
            System.out.println("Путь найден " + startingCell.getX() + " " + startingCell.getY() + " до " + endingCell.getX() + " " + endingCell.getY());
            return currentPartPath;
        }

        ArrayList<PartPath> neighbors = getAllowedNeighboringPartPath(currentPartPath, prohibitedEntities);
        addNewPartPaths(neighbors);
        if (isReachDeadEnd()) {
            System.out.println("Путь не найден");
        }
        passedPartPath.add(currentPartPath);
        return null;
    }

    public boolean isReachEndingCell(PartPath currentPartPath) {
        return currentPartPath.getCurrentCell().equals(endingCell);
    }

    public boolean isReachDeadEnd() {
        return unexaminedPartPath.isEmpty();
    }

    public void addNewPartPaths(ArrayList<PartPath> neighbors) {
        unexaminedPartPath.addAll(neighbors);
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


    public ArrayList<PartPath> getAllowedNeighboringPartPath(PartPath currentPartPath, HashSet<String> prohibitedEntities) {
        ArrayList<Cell> allowedNeighboringCells = this.worldMap.getAllowedNeighboringCells(currentPartPath.getCurrentCell(), endingCell, prohibitedEntities);
        ArrayList<PartPath> allowedNeighboringPartPath = new ArrayList<>();
        for (Cell cell: allowedNeighboringCells) {
            PartPath partPath = new PartPath(cell, currentPartPath.getCurrentCell());
            if (!passedPartPath.contains(partPath)) {
                allowedNeighboringPartPath.add(partPath);
            }
        }
        return allowedNeighboringPartPath;
    }
}
