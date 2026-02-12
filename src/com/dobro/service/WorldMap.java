package com.dobro.service;

import com.dobro.models.Entity;
import com.dobro.models.GraniteBlock;

import java.util.*;

public class WorldMap {
    private final Cell originWorldMap = new Cell(0, 0);
    private final HashMap<Cell, Entity> entities = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    private final float spawnRate;
    private int maxWidthField;
    private int maxLengthField;
    private int numberOfGhosts;
    private int numberOfPeople;
    private int numberOfCoins;

    public WorldMap() {

        System.out.println("Введите начальные параметры");
        System.out.println("Введите длину поля");
        setMaxLengthField();
        System.out.println("Введите ширину поля");
        setMaxWidthField();
        System.out.println("""
                Важное условие: каждое существо или объект занимают клетку поля целиком
                Нахождение в клетке нескольких объектов/существ - недопустимо
                """
        );
        System.out.println("""
                Выберите сложность замка (1–10):
                1 — почти без препятствий
                10 — максимально сложный, большое количество препятсвий
                """
        );
        spawnRate = (scanner.nextFloat() + 3.5f) / 4.5f;
    }

    public int getMaxLengthField() {
        return maxLengthField;
    }

    public void setMaxLengthField() {
        do {
            System.out.println("Длина поля должна быть положительная");
            maxLengthField = scanner.nextInt();
        } while (maxLengthField < this.getOriginWorldMap().getX());
    }

    public int getMaxWidthField() {
        return maxWidthField;
    }

    public void setMaxWidthField() {
        do {
            System.out.println("Ширина поля должна быть положительная");
            maxWidthField = scanner.nextInt();
        } while (maxWidthField < this.getOriginWorldMap().getY());
    }

    public Cell getOriginWorldMap() {
        return originWorldMap;
    }

    public int getNumberOfGhosts() {
        return numberOfGhosts;
    }

    public void setNumberOfGhosts(int numberOfGhosts) {
        this.numberOfGhosts = numberOfGhosts;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public int getNumberOfCoins() {
        return numberOfCoins;
    }

    public void setNumberOfCoins(int numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }

    public HashMap<Cell, Entity> getEntities() {
        //возвращать надо копию
        return entities;
    }

    public void setEntity(Cell cell, Entity entity) {
        this.getEntities().put(cell, entity);
    }

    public float getSpawnRate() {
        return spawnRate;
    }

    public boolean isEmptyCell(Cell cell) {
        return this.getEntities().get(cell) instanceof GraniteBlock;
    }

    public int sumEntities() {
        int sum = 0;
        for (Map.Entry<Cell, Entity> entry : entities.entrySet()) {
            if (!isEmptyCell(entry.getKey())) {
                sum += 1;
            }
        }
        return sum;
    }

    //сделать алгоритм
    public ArrayList<Cell> getNeighbors(Cell currentCell) {
        Cell leftNeighbor = new Cell(currentCell.getX() - 1, currentCell.getY());
        Cell rightNeighbor = new Cell(currentCell.getX() + 1, currentCell.getY());
        Cell upperNeighbor = new Cell(currentCell.getX() , currentCell.getY() + 1);
        Cell lowerNeighbor = new Cell(currentCell.getX(), currentCell.getY() - 1);

        ArrayList<Cell> neighbors = new ArrayList<>(List.of(leftNeighbor, rightNeighbor, upperNeighbor, lowerNeighbor));
        neighbors.removeIf(this::isOffTheMap);
        return neighbors;
    }

    public boolean isOffTheMap(Cell currentCell) {
        return !this.getEntities().containsKey(currentCell);
    }

}
