package sim.app.birds;

import sim.engine.*;

public class Bird implements Steppable {

    public void step(SimState state) {
	System.out.println("Stepping");
    }

}