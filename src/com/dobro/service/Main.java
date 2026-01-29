package com.dobro.service;

public class Main {
    static void main(String[] args)  {
        WorldMap worldMap = new WorldMap();
        Simulation simulation = new Simulation(worldMap);
        simulation.startSimulation();
    }
}