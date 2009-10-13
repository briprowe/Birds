package sim.app.birds;

import sim.util.*;

public class Mating {
    private Bird male;
    private Bird female;

    private Double2D pos;

    public Mating() { }

    public Mating(Bird m, Bird f, Double2D p) {
	this.male = m; this.female = f; this.pos = p;
    }

    public Double2D getPosition() { return pos; }
}