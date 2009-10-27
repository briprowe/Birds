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

    public Bird() {
	ec.util.MersenneTwisterFast random = BirdsModel.getInstance().random;

	id = random.nextInt();
	gender = random.nextBoolean();
	pregnant = false;

	strategy = random.nextFloat();
	age = 0;
    }

    public Bird(int i) { 
	ec.util.MersenneTwisterFast random = BirdsModel.getInstance().random;
	id = i;

	gender = random.nextBoolean();

	strategy = random.nextFloat();
	age = 0;

	pregnant = false;
    }

    public Bird(Bird mother, Bird father) {
	ec.util.MersenneTwisterFast random = BirdsModel.getInstance().random;

	gender = random.nextBoolean();
	pregnant = false;
	age = 0;

	strategy = (mother.strategy + father.strategy) / 2;

    }

    public void setAge(int a) { age = a; }
    public int getAge() { return age; }

    public boolean getGender() { return gender; }
    public float   getStrategy() { return strategy; }

    public void setPos(Double2D p) {
	sim.field.continuous.Continuous2D world = BirdsModel.getInstance().getWorld();

	pos = p;

	world.setObjectLocation(this, pos);
    }

    public Double2D getPos() {
	return pos;
    }

    public void setPregnant(boolean val) {
	pregnant = val;
    }

    public boolean respond(Signal sig) {
	if( gender == FEMALE && pregnant == false ) {
	    // Maybe mate? For the time being always mate.
	    Mating m = new Mating(sig.getSignaller(), this, pos);
	    
	    Season s = BirdsModel.getInstance().getSeason();

	    s.addMating(m);
	    pregnant = true;
	    
	    return true;
	}

	return false;
    }

    public void step(final SimState state) {
	ec.util.MersenneTwisterFast random = state.random;

	double visibility = ((BirdsModel)state).visibility(pos);
	double audRange   = ((BirdsModel)state).audRange(pos);

	double dx = random.nextDouble();
	double dy = random.nextDouble();

	if( random.nextBoolean() ) dx += pos.getX(); else dx = pos.getX() - dx;
	if( random.nextBoolean() ) dy += pos.getY(); else dy = pos.getY() - dy;

	Double2D p = new Double2D(dx, dy);
	this.setPos(p);

	// Should we signal?
	if( random.nextBoolean() && gender == MALE ) {
	    if( visibility >= strategy ) {
		System.out.println("Signalling (Visually) - Vis: " + visibility + " Aud: " + audRange + " X: " + p.getX());
		Signal s = new Signal(this, pos, visibility);
		
		double current_time = state.schedule.getTime();
		state.schedule.scheduleOnce(current_time, s);
	    } else if( audRange >= (1 - strategy) ) {
		System.out.println("Signalling (Audibly) - Vis: " + visibility + " Aud: " + audRange + " X: " + p.getX());
		Signal s = new Signal(this, pos, audRange);
		
		double current_time = state.schedule.getTime();
		state.schedule.scheduleOnce(current_time, s);
	    }
	}

    }

}