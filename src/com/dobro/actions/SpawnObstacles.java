package com.dobro.actions;

import com.dobro.models.*;
import com.dobro.service.Cell;
import com.dobro.service.WorldMap;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class SpawnObstacles extends Action {
    private final Random random = new Random();

    @Override
    public void execute(WorldMap worldMap) {
        System.out.println("""
                Важное условие: каждое существо или объект занимают клетку поля целиком
                Нахождение в клетке нескольких объектов/существ - недопустимо
                """
        );
        placeCastleObjectsOnTheMap(worldMap);
    }

    public void placeCastleObjectsOnTheMap(WorldMap worldMap) {
        System.out.println("""
            Насколько плотно заполнить карту предметами?
            Введите значение от 1 до 10:
            1 — почти пусто,
            10 — карта максимально заполнена
        """
        );
        int objectsDensity = worldMap.getScanner().nextInt();
        List<Supplier<? extends Entity>> castleObjectsFactory = List.of(Candle::new, Rock::new, Vase::new);

        for (int indexRow = 0; indexRow < worldMap.getMaxWidthField(); indexRow++) {
            for (int indexColumn = 0; indexColumn < worldMap.getMaxLengthField(); indexColumn++) {
                if (isPlaceObstacle(objectsDensity)) {
                    int indexRandomObstacle = random.nextInt(castleObjectsFactory.size());
                    worldMap.getEntities().put(new Cell(indexRow, indexColumn), castleObjectsFactory.get(indexRandomObstacle).get());
                } else {
                    worldMap.getEntities().put(new Cell(indexRow, indexColumn), new GraniteBlock());
                }
            }
        }
    }

    public boolean isPlaceObstacle(int objectsDensity) {
        int MAX_OBJECTS_DENSITY = 10;
        //подумать над рандомизацией, чтобы оставалось место под другие объекты
        // резать шансы в 3 - 4 раза
        int randomNumber = random.nextInt(MAX_OBJECTS_DENSITY) + 1;
        return randomNumber <= objectsDensity;
    }
}
