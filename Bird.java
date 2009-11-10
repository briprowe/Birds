package sim.app.birds;

import sim.engine.*;
import sim.util.*;

public class Bird implements Steppable {

    public static final boolean MALE = true;
    public static final boolean FEMALE = false;
    public static final double AVG_SPEED = 625;
    public static final double HUNGRY_THRESHOLD = 3;

    private boolean gender;
    private int age;
    private boolean pregnant;
    private boolean dead;

    private float strategy;

    private int id;
    private Double2D pos;
    private double energy;

    public Bird() {
	ec.util.MersenneTwisterFast random = BirdsModel.getInstance().random;

	id = random.nextInt();
	gender = random.nextBoolean();
	pregnant = false;
	
	strategy = random.nextFloat();
	age = 0;
	energy = 10;
    }

    public Bird(int i) { 
	ec.util.MersenneTwisterFast random = BirdsModel.getInstance().random;
	id = i;

	gender = random.nextBoolean();
	pregnant = false;

	strategy = random.nextFloat();
	age = 0;
	energy = 10;
    }

    public Bird(Bird mother, Bird father) {
	ec.util.MersenneTwisterFast random = BirdsModel.getInstance().random;

	gender = random.nextBoolean();
	pregnant = false;

	strategy = (mother.strategy + father.strategy) / 2;
	age = 0;
	energy = 10;
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

    private Double2D findFood(Energy e) {
	double x     = pos.getX();
	double y     = pos.getY();

	double x_max = x;
	double y_max = y;
	double max_val = e.get(pos);
	
	for(int i = -1; i < 2; i++)
	    for(int j = -1; j < 2; j++) {
		double en = e.get(new Double2D(x + i, y + j));
		if( en > max_val ) {
		    max_val = en;
		    x_max = x + i;
		    y_max = y + j;
		}
	    }

	return new Double2D(x_max, y_max);
    }

    public int manageEnergy(BirdsModel state) {
	Energy e = state.getEnergy();

	// if there's enough energy in the environment, increment current energy
	// decrement energy in environment
	double env = e.get(pos);
	if( env > 1 ) {
	    // There's enough energy
	    e.set(pos, env - 1);
	    energy++;
	} 
	
	// if our energy is <= HUNGRY_THRESHOLD go look for more food
	// (ie. do gradient ascent on the energy grid).

	if( energy <= HUNGRY_THRESHOLD ) {
	    Double2D food_pos = findFood(e);
	    this.setPos(food_pos);

	    return -1;
	} else {
	    return 1;
	}
    }

    public void step(final SimState state) {
	if( dead ) return;

	energy--;

	if( manageEnergy((BirdsModel)state) > 0 ) {
	    
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
}