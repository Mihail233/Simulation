import com.dobro.*;
import com.dobro.entity.Coin;

public class Main {

    static void main(String[] args) {
        Program program = new Program();
        Simulation simulation = new Simulation(new WorldMapFactory().initWorldMap());
        program.start(simulation);

//        Simulation simulation = new Simulation(new WorldMapFactory().initWorldMap());
//        simulation.initSimulation();
//        simulation.startSimulation();
    }
}