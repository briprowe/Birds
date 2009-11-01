package sim.app.birds;

import sim.field.grid.*;
import sim.util.*;
import sim.engine.*;

class Energy implements Steppable {
    private DoubleGrid2D values;
    private double[][] field;

    private Double2D location;
    private double   width;
    private double   height;

    private int      num_cells_horiz;
    private int      num_cells_vert;

    private double upperBound;
    private double lowerBound;

    private double delta_energy;

    public Energy(Double2D loc, double w, double h, int nc_horiz, int nc_vert, double initVal) {
	values = new DoubleGrid2D(nc_horiz, nc_vert, initVal);
	field = values.getField();

	location = loc;
	width = w; height = h;

	num_cells_horiz = nc_horiz;
	num_cells_vert  = nc_vert;
    }

    public void setBounds(double u, double l) {
	upperBound = u;
	lowerBound = l;

	values.lowerBound(lowerBound);
	values.upperBound(upperBound);
    }

    public void setEnergyGain(double de) {
	delta_energy = de;
    }
    
    public void step(final SimState state) {
	values.add(delta_energy);
    }

    private Double2D transform(Double2D pt) {
	double x = pt.getX();
	double y = pt.getY();

	x -= location.getX();
	y -= location.getY();

	x /= width;
	y /= height;

	return new Double2D(x,y);
    }

    public double get(Double2D pos) {
	Double2D p = transform(pos);
	
	return values.get((int)Math.floor(p.getX()), (int)Math.floor(p.getY()));
    }

    public void set(Double2D pos, double val) {
	Double2D p = transform(pos);
	
	int x = (int)Math.floor(p.getX());
	int y = (int)Math.floor(p.getY());

	field[x][y] = val;
    }
}