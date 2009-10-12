package sim.app.birds;

import sim.engine.*;

import java.util.*;

public class Season implements Steppable {
    
    private LinkedList<Mating> matings;

    public Season() { matings = new LinkedList<Mating>(); }

    public void addMating(Mating m) {
	matings.add(m);
    }

    public void step(SimState state) {
	System.out.println("Season stepping: " + state.schedule.getTimestamp("", "") + " Num Matings: " + matings.size());
    }
}