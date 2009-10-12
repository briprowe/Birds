package sim.app.birds;

import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;

public class BirdsModel extends SimState {
    // The standard world size is 62.5x62.5 km^2
    public static double WORLD_SIZE_X = 625.00 / 2.0;
    public static double WORLD_SIZE_Y = 625.00 / 2.0;

    public static double DISPLAY_SIZE_X = WORLD_SIZE_X * 20;
    public static double DISPLAY_SIZE_Y = WORLD_SIZE_X * 20;

    //public static int NUM_BIRDS = 300000 / 2 / 2;
    public static int NUM_BIRDS = 300;
    // The standard signal radius is 100 meters. Since the world is scaled down by
    // a factor of 100: 
    public static double STD_SIGNAL_RADIUS = 1;
    public static int    SEASON_LENGTH     = 50;

    private static BirdsModel instance;

    public BirdsModel(long seed) { super(seed); instance = this; }
    public BirdsModel(long seed, int width, int height, int count) {
	super(seed);

	instance = this;
    }

    public static BirdsModel getInstance() { 
	if (instance == null) { 
	    instance = new BirdsModel(System.currentTimeMillis());
	}

	return instance;
    }

    public String getName() { return "Birds"; }

    private Bird[] birds;
    private Season season;

    private Continuous2D bird_grid;
    
    public Continuous2D getWorld()  { return bird_grid; }
    public Season       getSeason() { return season; }

    public void start() {
	super.start();

	season = new Season();

	bird_grid = new Continuous2D(1, WORLD_SIZE_X, WORLD_SIZE_Y);
	birds = new Bird[NUM_BIRDS];

	for( int i = 0; i < NUM_BIRDS; i++ ) {
	    birds[i] = new Bird(i);

	    Double2D pos = new Double2D(WORLD_SIZE_X * random.nextDouble(),
					WORLD_SIZE_Y * random.nextDouble());
	    
	    birds[i].setPos(pos);

					
	}

	schedule.scheduleRepeating(new RandomSequence(birds));
	schedule.scheduleRepeating(new MultiStep(season, SEASON_LENGTH, true));
    }

    public double visibility(Double2D pos) {
	return pos.x / WORLD_SIZE_X;
    }

    public static void main(String[] args) {

	doLoop(BirdsModel.class, args);

	System.exit(0);

    }
    
}