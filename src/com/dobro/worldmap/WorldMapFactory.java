package com.dobro.worldmap;

import com.dobro.UserInput;

public class WorldMapFactory {
    public WorldMap initWorldMap() {
        System.out.println("Введите начальные параметры");
        System.out.println("Введите ширину поля");
        int width = UserInput.getInteger();
        System.out.println("Введите высоту поля");
        int height = UserInput.getInteger();
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
        float spawnRate = UserInput.getFloat();
        return new WorldMap(width,height,spawnRate);
    }
}
