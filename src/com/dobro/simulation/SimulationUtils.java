package com.dobro.simulation;

public class SimulationUtils {

    public static void clearConsole() {
        System.out.print("\033[H\033[J");
    }

    public static void printOptions() {
        System.out.println("Введите цифру: ");
        System.out.printf("%s - Поставить паузу\n", SimulationManager.PAUSE);
        System.out.printf("%s - Продолжить симуляцию\n", SimulationManager.CONTINUE);
    }
}
