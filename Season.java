package sim.app.birds;

import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;

import java.util.*;

public class Season implements Steppable {
    
    private LinkedList<Mating> matings;
    
    public Season() { matings = new LinkedList<Mating>(); }

    public void addMating(Mating m) {
	matings.add(m);
    }

    public void step(SimState state) {
	System.out.println("Season stepping: " + state.schedule.getTimestamp("", "") + " Num Matings: " + matings.size());
	Continuous2D world = ((BirdsModel)state).getWorld();
	Bag birds = world.clear();

	// Cull the dead
	LinkedList<Bird> survivors = cull(birds);

	// Create the list of offspring
	LinkedList<Bird> young = doBirths();

	// Put the next generation of birds into the scheduler and the world
	((BirdsModel)state).reschedule(new LinkedList<Bird>(survivors, young));
	
	// Create a new list of matings for the next season.
	matings = new LinkedList<Mating>();
	
    }

    private LinkedList<Bird> doBirths() {
	Iterator iter = matings.iterator();
	LinkedList<Bird> young = new LinkedList<Bird>();

	try {
	    Mating m = (Mating)iter.next();
	    while( m != null ) {
		// Two offspring per mating 
		Bird b1 = new Bird();
		b1.setAge(0);
		b1.setPos(m.getPosition());

		Bird b2 = new Bird();
		b2.setAge(0);
		b2.setPos(m.getPosition());
		
		young.add(b1); young.add(b2);
		
		Bird mom = m.getFemale();
		mom.setPregnant(false);

		m = (Mating)iter.next();
	    }
	} catch( NoSuchElementException e ) {
	    
	}

	return young;
    }
    
    private LinkedList<Bird> cull(Bag birds) {
	Iterator iter = birds.iterator();
	LinkedList<Bird> retval = new LinkedList<Bird>();

	try {
	    Bird b = (Bird)iter.next();
	    while( b != null ) {
		retval.add(b);

		b = (Bird)iter.next();
	    }
	} catch( NoSuchElementException e ) {

	}
    }
}