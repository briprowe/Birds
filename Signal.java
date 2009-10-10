package sim.app.birds;

import sim.util.*;
import sim.engine.*;
import sim.field.continuous.Continuous2D;

public class Signal implements Steppable {
    private Double2D pos;
    private double visibility;

    private Bird signaller;

    
    public Signal(Bird signaller, Double2D pos, double vis) {
	this.signaller = signaller;
	this.pos = pos;
	this.visibility = vis;
    }

    public Bird getSignaller() { return signaller; }
    
    public Double2D getPos() { return pos; }
    public void     setPos(Double2D pos) { this.pos = pos; }

    public double getVisibility() { return visibility; }
    public void   setVisiblity(double vis) { this.visibility = vis; }

    public double getRange() {
	return sim.app.birds.BirdsModel.STD_SIGNAL_RADIUS * visibility;
    }

    public void step(SimState state) {
	Continuous2D world = ((BirdsModel)state).getWorld();

	sim.util.Bag birds = world.getObjectsWithinDistance(pos, this.getRange());
	System.out.println("Num birds in range: " + birds.size());
    }
}