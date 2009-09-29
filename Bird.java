package sim.app.birds;

import sim.engine.*;
import sim.util.*;

public class Bird implements Steppable {

    private int id;
    private Double2D pos;

    public Bird(int i) { 
	id = i;
    }

    public void setPos(Double2D p) {
	pos = p;
    }

    public Double2D getPos() {
	return pos;
    }

    public void step(SimState state) {
	System.out.println("Stepping: " + id + "\tPos: (" + pos.getX() + ", " + pos.getY() + ")");
    }

}