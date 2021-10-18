package pedigree.sim;

public class AncestorSim extends Sim {
    public AncestorSim(Sim sim) {
        super(sim.getMother(), sim.getFather(), sim.getBirthTime(), sim.getDeathTime(), sim.getSex(),
                sim.getIndent());
    }
}