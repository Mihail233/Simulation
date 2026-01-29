package com.dobro.service;

import com.dobro.models.Entity;

import java.util.HashMap;
import java.util.Scanner;

public class WorldMap {
    private HashMap<Cell, Entity> entities = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
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
    }

    public int getMaxLengthField() {
        return maxLengthField;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setMaxLengthField() {
        do {
            System.out.println("Длина поля должна быть положительная");
            maxLengthField = scanner.nextInt();
        } while (maxLengthField < 1);
    }

    public int getMaxWidthField() {
        return maxWidthField;
    }

    public void setMaxWidthField() {
        do {
            System.out.println("Ширина поля должна быть положительная");
            maxWidthField = scanner.nextInt();
        } while (maxWidthField < 1);
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
        return entities;
    }

}
