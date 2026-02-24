package com.dobro.service;

import java.io.IOException;

public class Main {

    static void main(String[] args) throws InterruptedException, IOException {
        WorldMap worldMap = new WorldMap();
        Simulation simulation = new Simulation(worldMap);
        simulation.startSimulation();
    }
}