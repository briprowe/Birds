package sim.app.birds;

import sim.engine.*;
import sim.display.*;
import sim.portrayal.continuous.*;
import java.awt.*;
import javax.swing.*;

public class BirdsGUI extends GUIState {
    public Display2D display;
    public JFrame displayFrame;

    ContinuousPortrayal2D birdPortrayal = new ContinuousPortrayal2D();

    public static void main(String[] args) {
	Console c = new Console(new BirdsGUI());
	c.setVisible(true);
    }

    public BirdsGUI() { super(new BirdsModel(System.currentTimeMillis())); }
    public BirdsGUI(SimState state) { super(state); }

    public static String getName() { return "Birds"; }
    public Object getSimulationInspectedObject() { return state; }

    public void start() {
	super.start();
	
	setupPortrayal();
    }

    public void load(SimState state) {
	super.load(state);

	setupPortrayal();
    }

    public void setupPortrayal() {
	birdPortrayal.setPortrayalForAll( new sim.portrayal.simple.OvalPortrayal2D(Color.white, 5) );

	birdPortrayal.setField(((BirdsModel)state).getWorld());

	display.reset();
	display.repaint();
    }

    public void init(Controller c) {
	super.init(c);

	display = new Display2D(BirdsModel.WORLD_SIZE_X, BirdsModel.WORLD_SIZE_Y,this,1);
	//display.setScale(10);
	displayFrame = display.createFrame();

	c.registerFrame(displayFrame);
	displayFrame.setVisible(true);

	display.attach(birdPortrayal, "Birds");

	display.setBackdrop(Color.black);
    }
    
    public void quit() {
	super.quit();

	if( displayFrame != null ) displayFrame.dispose();

	displayFrame = null;
	display = null;
    }
}