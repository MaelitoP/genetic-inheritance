package pedigree.simulation;

import java.util.ArrayList;
import java.util.Random;

import pedigree.Event;
import pedigree.Event.Type;
import pedigree.sim.AncestorSim;
import pedigree.sim.Sim;
import pedigree.model.AgeModel;
import pedigree.structure.MinHeap;

public class Simulation {
    // Structures
    private MinHeap<Event> events;
    private MinHeap<Sim> females;
    private MinHeap<Sim> males;

    // Model
    private AgeModel model;
    private Random rand;

    // Simulation constants
    private int n;
    private double tMax;

    // Simulation variables
    private final double r;
    private final double fidelity;
    private double crrTime;

    // Empirical study
    private int nextCentury;
    private ArrayList<Integer> populationHistory;
    private ArrayList<Integer> timeHistory;

    // Constants
    private static final double DEFAULT_FIDELITY = 0.9;
    private static final double REPRODUCTION = 2.0;

    public Simulation(int n, double tMax) {
        this.tMax = tMax;
        this.n = n;

        model = new AgeModel();

        // Structure
        events = new MinHeap<>(n, 4);
        females = new MinHeap<>(n, 4);
        males = new MinHeap<>(n, 4);

        rand = new Random();
        nextCentury = 0;
        populationHistory = new ArrayList<>();
        timeHistory = new ArrayList<>();

        this.fidelity = DEFAULT_FIDELITY;
        r = REPRODUCTION / model.expectedParenthoodSpan(Sim.MIN_MATING_AGE_F, Sim.MAX_MATING_AGE_F);
    }

    private void execEvent(Event e) {
        if (e.getTime() > nextCentury) {
            timeHistory.add((int) crrTime);
            populationHistory.add(males.size() + females.size());
            nextCentury += 100;
        }

        if (e.getType().equals(Type.D)) killSims();
        else if (e.getType().equals(Type.R)) reproduction(e.getSim());
        else if (e.getType().equals(Type.B)) birth(e);
    }

    public void simulate() {
        for (int i = 0; i < n; i++) {
            Sim sim = new Sim(Sim.randomSex());
            Event e = new Event(sim, Type.B, 0.0);
            events.insert(e);
        }

        while (!events.isEmpty()) {
            Event event = events.delete();
            crrTime = event.getTime();

            if (crrTime > tMax) {
                timeHistory.add((int) crrTime);
                populationHistory.add(this.males.size() + this.females.size());
                nextCentury += 100;
                break;
            }
            execEvent(event);
        }
    }

    AncestorSim[] getFemales() {
        int i = this.females.size();
        AncestorSim[] arr = new AncestorSim[i];

        for (int k = 0; k < i; k++) arr[k] = new AncestorSim(females.getElement(k));
        return arr;
    }

    AncestorSim[] getMales() {
        int i = this.males.size();
        AncestorSim[] arr = new AncestorSim[i];

        for (int k = 0; k < i; k++) arr[k] = new AncestorSim(males.getElement(k));
        return arr;
    }

    private void nextReproduction(Sim sim) {
        double waitingTime = AgeModel.randomWaitingTime(rand, r);

        if (sim.isMale()) return;
        Event e = new Event(sim, Type.R, waitingTime + crrTime);
        events.insert(e);
    }

    private void birth(Event e) {
        Sim sim = e.getSim();
        double death = model.randomAge(rand) + sim.getBirthTime();
        Event deathE = new Event(sim, Type.D, death);

        sim.setDeath(death);
        events.insert(deathE);

        if (sim.isMale()) males.insert(sim);
        else {
            nextReproduction(sim);
            females.insert(sim);
        }
    }

    private void killSims() {
        while (!females.isEmpty() && females.peek().getDeathTime() <= crrTime) females.delete();
        while (!males.isEmpty() && males.peek().getDeathTime() <= crrTime) males.delete();
    }

    private Sim fatherResearch(Sim sim) {
        boolean potentialMateExists = false;
        if (males.isEmpty()) return null;

        if (sim.isInARelationship(crrTime)) {
            Sim x = sim.getMate();
            if (rand.nextDouble() < fidelity) return x;
            else {
                for (int i = 0; i < males.size(); i++) {
                    Sim potentialMate = males.getElement(i);
                    if (potentialMate.getIndent() != x.getIndent() && potentialMate.isMatingAge(crrTime)) {
                        potentialMateExists = true;
                        break;
                    }
                }

                if (!potentialMateExists) return null;

                while (true) {
                    Sim y = males.getElement(rand.nextInt(males.size()));
                    if (y.isMatingAge(crrTime) && !y.equals(sim.getMate())) return y;
                }
            }
        }

        while (true) {
            Sim y = males.getElement(rand.nextInt(males.size()));
            if (!y.isInARelationship(crrTime)) return y;
            else if (rand.nextDouble() > fidelity) return y;
        }
    }

    private void reproduction(Sim sim) {
        if (!sim.isAlive(crrTime)) return;

        if (sim.isMatingAge(crrTime)) {
            Sim father = fatherResearch(sim);
            if (father != null) {
                father.setMate(sim);
                sim.setMate(father);
                Sim child = new Sim(sim, father, crrTime, Sim.randomSex());
                Event e = new Event(child, Type.B, crrTime);
                events.insert(e);
            }
        }
        nextReproduction(sim);
    }

    public ArrayList<Integer> getPopulationHistory() {
        return populationHistory;
    }

    public ArrayList<Integer> getTimeHistory() {
        return timeHistory;
    }

    int getSimulationSize() {
        return n;
    }
}