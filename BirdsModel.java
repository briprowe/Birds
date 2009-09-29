package sim.app.birds;

import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;

public class BirdsModel extends SimState {
    public static int WORLD_SIZE_X = 62500 / 2;
    public static int WORLD_SIZE_Y = 62500 / 2;

    public static int DISPLAY_SIZE_X = 312;
    public static int DISPLAY_SIZE_Y = 312;

    public static int NUM_BIRDS = 300000 / 2 / 2;

    public BirdsModel(long seed) { super(seed); }

    private Bird[] birds;
    private Continuous2D bird_grid;

    public void start() {
	super.start();

	bird_grid = new Continuous2D(100, WORLD_SIZE_X, WORLD_SIZE_Y);
	birds = new Bird[NUM_BIRDS];

	for( int i = 0; i < NUM_BIRDS; i++ ) {
	    birds[i] = new Bird(i);

	    Double2D pos = new Double2D(WORLD_SIZE_X * random.nextDouble(),
					WORLD_SIZE_Y * random.nextDouble());
	    birds[i].setPos(pos);

	    bird_grid.setObjectLocation(birds[i], birds[i].getPos());
					
	}

	schedule.scheduleRepeating(new RandomSequence(birds));
    }

    public static void main(String[] args) {

	doLoop(BirdsModel.class, args);

	System.exit(0);

    }
    
}