package sim.app.birds;

import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;

public class BirdsModel extends SimState {
    public static double WORLD_SIZE_X = 625.00 / 2.0;
    public static double WORLD_SIZE_Y = 625.00 / 2.0;

//     public static int DISPLAY_SIZE_X = 312;
//     public static int DISPLAY_SIZE_Y = 312;

    public static double DISPLAY_SIZE_X = WORLD_SIZE_X * 20;
    public static double DISPLAY_SIZE_Y = WORLD_SIZE_X * 20;

    //public static int NUM_BIRDS = 300000 / 2 / 2;
    public static int NUM_BIRDS = 300;

    private static BirdsModel instance;

    public BirdsModel(long seed) { super(seed); instance = this; }
    public BirdsModel(long seed, int width, int height, int count) {
	super(seed);
    }

    public static BirdsModel getInstance() { 
	if (instance == null) { 
	    instance = new BirdsModel(1);
	}

	return instance;
    }

    public String getName() { return "Birds"; }

    private Bird[] birds;
    private Continuous2D bird_grid;
    
    public Continuous2D getWorld() { return bird_grid; }

    public void start() {
	super.start();

	bird_grid = new Continuous2D(100, WORLD_SIZE_X, WORLD_SIZE_Y);
	//bird_grid = new Continuous2D(10, 400, 400);
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