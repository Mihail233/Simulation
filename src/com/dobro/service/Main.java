package com.dobro.service;

import java.util.List;

public class Main {
    static void main(String[] args) {
        WorldMap worldMap = new WorldMap();
        Simulation simulation = new Simulation(worldMap);
        simulation.startSimulation();
    }
}