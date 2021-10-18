package pedigree.simulation;

import pedigree.sim.AncestorSim;
import pedigree.structure.MaxHeap;

import java.util.*;

public class Coalescence {
    // Structure
    private Map<Integer, Integer> cpMale;
    private Map<Integer, Integer> cpFemale;
    private Set<Integer> ancestors;

    // Variables
    private int lines;
    private Simulation simulation;

    public Coalescence(Simulation simulation) {
        this.simulation = simulation;
        this.ancestors = new HashSet<>();
        cpMale = init(this.simulation.getMales());
        cpFemale = init(this.simulation.getFemales());
    }

    private Map<Integer, Integer> init(AncestorSim[] population) {
        Map<Integer, Integer> cp = new HashMap<>();
        MaxHeap<AncestorSim> sims = new MaxHeap<>(simulation.getSimulationSize(), 2);

        for (AncestorSim ancestor : population) {
            ancestors.add(ancestor.getIndent());
            sims.insert(ancestor);
        }

        sims.heapify(population);
        this.lines = ancestors.size();

        while(!sims.isEmpty()) {
            AncestorSim sim = new AncestorSim(sims.delete());
            ancestors.remove(sim.getIndent());

            if(!sim.isFounder()) {
                AncestorSim parent;
                if(sim.isFemale()) parent = new AncestorSim(sim.getMother());
                else parent = new AncestorSim(sim.getFather());

                if (!ancestors.contains(parent.getIndent())) {
                    sims.insert(parent);
                    ancestors.add(parent.getIndent());
                } else {
                    lines--;
                    cp.put((int) sim.getBirthTime(), lines);
                }
            }
        }

        cp.put(0, lines);
        return cp;
    }

    public Map<Integer, Integer> getCoalescencePointFemale() {
        return cpFemale;
    }

    public Map<Integer, Integer> getCoalescencePointMale() {
        return cpMale;
    }
}