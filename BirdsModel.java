package sim.app.birds;
import sim.engine.*;
import sim.field.grid.*;
import sim.util.*;

public class BirdsModel extends SimState {
    public static int WORLD_SIZE_X = 62500 / 2;
    public static int WORLD_SIZE_Y = 62500 / 2;

    public static int DISPLAY_SIZE_X = 312;
    public static int DISPLAY_SIZE_Y = 312;

    public static int NUM_BIRDS = 300000 / 2 / 2;

    public BirdsModel(long seed) { super(seed); }

    public static void main(String[] args) {

	System.out.println("Hello!");

	System.exit(1);

    }
    
}