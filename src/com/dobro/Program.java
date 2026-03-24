package com.dobro;

import java.io.IOException;

public class Program {
    private static final char PAUSE = '1';
    private static final char CONTINUE = '2';
    private static final int SIMULATION_FREQUENCY = 1000;
    private final Object monitor = new Object();
    private volatile boolean isPaused;
    private char inputUser;

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public void start(Simulation simulation) {
        setPaused(true);
        simulation.initSimulation();
        printOptions();
        initThreadUserInput(simulation);
    }

    public void initThreadUserInput(Simulation simulation) {
        new Thread(() -> {
            initThreadUserInput();
            delegateWorkToAnotherThread();
            synchronized (monitor) {
                while (true) {
                    if (simulation.hasStruggleForExistence()) {
                        simulation.nextTurn();
                    } else {
                        System.exit(0);
                    }
                    printOptions();
                    delegateWorkToAnotherThread();
                }
            }
        }).start();
    }

    public void initThreadUserInput() {
        new Thread(() -> {
            try {
                synchronized (monitor) {
                    while (true) {
                        getInputFromUser();
                        if (isPaused) {
                            continueSimulation();
                        } else {
                            pauseSimulation();
                            Thread.sleep(SIMULATION_FREQUENCY);
                        }
                    }
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void delegateWorkToAnotherThread() {
        synchronized (monitor) {
            changeMonitor();
        }
    }

    private void changeMonitor() {
        try {
            monitor.notify();
            monitor.wait();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    private void getInputFromUser() throws IOException {
        int missingValue = 0;
        if (System.in.available() > missingValue) {
            inputUser = (char) System.in.read();
            System.in.skip(System.in.available());
        }
    }

    private void pauseSimulation() {
        if (inputUser == (int) PAUSE) {
            setPaused(true);
        }
        changeMonitor();
    }

    private void continueSimulation() {
        if (inputUser == (int) CONTINUE) {
            setPaused(false);
            changeMonitor();
        }
    }

    private void printOptions() {
        System.out.println("Введите цифру: ");
        if (isPaused) {
            System.out.printf("%s - Продолжить симуляцию\n", CONTINUE);
        } else {
            System.out.printf("%s - Поставить паузу\n", PAUSE);
        }
    }
}
