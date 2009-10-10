package sim.app.birds;

import sim.engine.*;

public class Season implements Steppable {

    public Season() { }

    public void step(SimState state) {
	System.out.println("Season stepping: " + state.schedule.getTimestamp("", ""));
    }
}