package pedigree;

import pedigree.sim.Sim;

public class Event implements Comparable<Event> {
    private Sim sim;
    private Type type;
    private double time;

    public enum Type { B, R, D }

    public Event(final Sim subject, final Type type, final double time) {
        this.sim = subject;
        this.type = type;
        this.time = time;
    }

    public Sim getSim() {
        return sim;
    }

    public Type getType() {
        return type;
    }

    public double getTime() {
        return time;
    }

    @Override
    public int compareTo(Event e) {
        return Double.compare(getTime(), e.getTime());
    }
}