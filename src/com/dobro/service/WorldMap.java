package com.dobro.service;

import com.dobro.models.*;

import java.util.*;

public class WorldMap {
    private final static List<Cell> SHIFT_COORDINATES = List.of(new Cell(0, 1), new Cell(0, -1), new Cell(-1, 0), new Cell(1, 0));
    private final HashMap<Cell, Entity> entities = new HashMap<>();
    private final Cell originWorldMap = new Cell(0, 0);
    private final Scanner scanner = new Scanner(System.in);
    private float spawnRate;
    private int maxWidthField;
    private int maxLengthField;
    private int numberOfGhosts;
    private int numberOfPeople;
    private int numberOfCoins;

    public WorldMap() {
        initWorldMap();
    }

    public void initWorldMap() {

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

//    public Optional<Cell> getCell(Entity entity) {
//        for (Map.Entry<Cell,Entity> pieceOfMap : this.getEntities().entrySet()) {
//            if (pieceOfMap.getValue().equals(entity)) {
//                return Optional.of(pieceOfMap.getKey());
//            }
//        }
//        return Optional.empty();
//    }

    //ofNullable - если пустая клетка -> может возвратить optional.isEmpty
    public Optional<? extends Entity> getEntity(Cell cell) {
        return Optional.ofNullable(this.getEntities().get(cell));
    }

//    public boolean isEmptyCell(Cell cell) {
//        return !this.getEntities().containsKey(cell);
//    }

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

//    public int sumEntities() {
//        int sum = 0;
//        for (Map.Entry<Cell, Entity> entry : entities.entrySet()) {
//            if (!isEmptyCell(entry.getKey())) {
//                sum += 1;
//            }
//        }
//        return sum;
//    }

    public ArrayList<Cell> getCellsOfCertainType(Class<? extends Entity> clazz) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (Map.Entry<Cell, Entity> pieceOfMap : this.getEntities().entrySet()) {
            if (pieceOfMap.getValue().getClass().equals(clazz)) {
                cells.add(pieceOfMap.getKey());
            }
        }
        return cells;
    }


    public ArrayList<Cell> getNeighbors(Cell cell) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        for (Cell shift : SHIFT_COORDINATES) {
            Cell neighbor = new Cell(cell.getX() + shift.getX(), cell.getY() + shift.getY());
            neighbors.add(neighbor);
        }
        neighbors.removeIf(this::isOffTheMap);
        return neighbors;
    }

    public boolean isOffTheMap(Cell cell) {
        boolean isOutsideLeftBorder = cell.getX() < this.getOriginWorldMap().getX();
        boolean isOutsideRightBorder = cell.getX() >= this.getMaxLengthField();
        boolean isOutsideTop = cell.getY() < this.getOriginWorldMap().getY();
        boolean isOutsideBottomBorder = cell.getY() >= this.getMaxWidthField();
        return isOutsideLeftBorder || isOutsideRightBorder || isOutsideTop || isOutsideBottomBorder;
    }
}
