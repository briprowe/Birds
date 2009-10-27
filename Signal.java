package sim.app.birds;

import java.util.Iterator;
import java.util.NoSuchElementException;

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

	System.out.println("X: " + pos.getX() + " Range: " + this.getRange());

	sim.util.Bag birds = world.getObjectsWithinDistance(pos, this.getRange());

	Iterator iter = birds.iterator();

	try {
	    Bird b = (Bird)iter.next();
	    while( b != null ) {
		if( b != signaller ) 
		    if( b.respond(this) )
			// Only 1 response per signal.
			break;

		b = (Bird)iter.next();
	    }
	} catch( NoSuchElementException e ) {

	}
	// Bird[] b = (Bird[])birds.toArray(new Bird[0]);

	// try { 
	//     for(int i = 0; i < b.length; i++)
	// 	if( b[i] != signaller )
	// 	    b[i].respond(this);
	// } catch( NullPointerException e ) {
	//     System.out.println("Null pointer");
	// }

	
    }
}
