package com.dobro;

import com.dobro.worldmap.WorldMapFactory;

public class Main {
    public static void main(String[] args){
        Program program = new Program();
        Simulation simulation = new Simulation(new WorldMapFactory().initWorldMap());
        program.start(simulation);

//        Simulation simulation = new Simulation(new WorldMapFactory().initWorldMap());
//        simulation.initSimulation();
//        simulation.startSimulation();
    }
}

//        Делаем класс, который будет печатать сообщения
//public class GameLogger {
//    public void show(String message) {
//        System.out.println(message);
//    }
//}
//
//
//Делаем класс Здание. Здание может быть разрушено противником
//public class Building {
//    private final String name;
//    private int hp;
//
//    public Building(String name, int hp) {
//        this.name = name;
//        this.hp = hp;
//    }
//
//    //разрушение
//    public void decreaseHp() {
//        if(isDestroyed()) {
//            return;
//        }
//        hp--;
//    }
//
//    //здание разрушено
//    public boolean isDestroyed() {
//        return hp == 0;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getHp() {
//        return hp;
//    }
//}
//
//
//Теперь нужно сообщить, когда здание будет разрушено.
//Напрямую это делать нельзя, поэтому вводим в проект интерфейс обратной связи Callback
//public interface Callback {
//    void execute(Building building);
//}
//
//
//И подключаем его к Зданию
//public class Building {
//    private final String name;
//    private int hp;
//    private Callback onDestroyCallback;
//
//    public Building(String name, int hp) {
//        this.name = name;
//        this.hp = hp;
//    }
//
//    //...
//    public void setOnDestroyCallback(Callback onDestroyCallback) {
//        this.onDestroyCallback = onDestroyCallback;
//    }
//
//}
//
//
//В момент разрушения здания будем дергать интерфейс
////разрушение
//public void decreaseHp() {
//    if(isDestroyed()) {
//        return;
//    }
//
//    hp--;
//    if(isDestroyed() && onDestroyCallback != null) {
//        onDestroyCallback.execute(this);
//    }
//}