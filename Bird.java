package sim.app.birds;

import sim.engine.*;
import sim.util.*;

public class Bird implements Steppable {

    public static final boolean MALE = true;
    public static final boolean FEMALE = false;
    public static final double AVG_SPEED = 625;

    private boolean gender;
    private int age;
    private boolean pregnant;

    private float strategy;

    private int id;
    private Double2D pos;

    public Bird(int i) { 
	id = i;

	gender = BirdsModel.getInstance().random.nextBoolean();
    }

    public void setAge(int a) { age = a; }
    public int getAge() { return age; }

    public boolean getGender() { return gender; }

    public void setPos(Double2D p) {
	sim.field.continuous.Continuous2D world = BirdsModel.getInstance().getWorld();

	pos = p;

	world.setObjectLocation(this, pos);
    }

    public Double2D getPos() {
	return pos;
    }

    public void step(final SimState state) {
	ec.util.MersenneTwisterFast random = state.random;

	double visibility = ((BirdsModel)state).visibility(pos);

	double dx = random.nextDouble();
	double dy = random.nextDouble();

	if( random.nextBoolean() ) dx += pos.getX(); else dx = pos.getX() - dx;
	if( random.nextBoolean() ) dy += pos.getY(); else dy = pos.getY() - dy;

	Double2D p = new Double2D(dx, dy);
	this.setPos(p);

	// Should we signal?
	if( random.nextBoolean() && gender == MALE )
	    if( visibility >= strategy ) {
		Signal s = new Signal(this, pos, visibility);
		
		double current_time = state.schedule.getTime();
		state.schedule.scheduleOnce(current_time, s);
	    }
		
	
	//System.out.println("Stepping: " + id + "\tPos: (" + pos.getX() + ", " + pos.getY() + ")\tGender: " + gender);
    }

}