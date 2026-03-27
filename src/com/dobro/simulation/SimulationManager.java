package com.dobro.simulation;

import com.dobro.Monitor;

import java.io.IOException;

public class SimulationManager {
    public static final char PAUSE = '1';
    public static final char CONTINUE = '2';
    private final Simulation simulation;
    private final Monitor monitor = new Monitor();
    private char inputUser;
    private volatile boolean isAvailableInputUser;

    public SimulationManager(Simulation simulation) {
        this.simulation = simulation;
    }

    public void start() {
        startThreadUserInput();
        startThreadSimulation();
        selectAction();
    }

    private void startThreadUserInput() {
        new Thread(() -> {
            try {
                synchronized (monitor) {
                    while (true) {
                        getInputFromUser();
                        changeThread();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void startThreadSimulation() {
        new Thread(() -> {
            try {
                while (true) {
                    if (isAvailableInputUser) {
                        if (!simulation.isPaused()) {
                            simulation.start();
                        }
                        isAvailableInputUser = false;
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void getInputFromUser() throws IOException {
        int missingValue = 0;
        if (System.in.available() > missingValue) {
            inputUser = (char) System.in.read();
            System.in.skip(System.in.available());
        }
    }

    private void selectAction() {
        while (true) {
            synchronized (monitor) {
                changeThread();
                changeStateOfSimulation();
            }
        }
    }

    private void changeStateOfSimulation() {
        switch (inputUser) {
            case PAUSE -> {
                simulation.setPaused(true);
                isAvailableInputUser = true;
            }
            case CONTINUE -> {
                simulation.setPaused(false);
                isAvailableInputUser = true;
            }
        }
    }

    private void changeThread() {
        try {
            monitor.notify();
            monitor.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}