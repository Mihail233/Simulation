package com.dobro;

import com.dobro.simulation.Simulation;
import com.dobro.simulation.SimulationManager;
import com.dobro.worldmap.WorldMapFactory;

public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(new WorldMapFactory().initWorldMap());
        SimulationManager simulationManager = new SimulationManager(simulation);
        simulationManager.start();
    }
}