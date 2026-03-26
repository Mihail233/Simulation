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