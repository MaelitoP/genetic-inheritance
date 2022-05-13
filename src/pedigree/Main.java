package pedigree;

import pedigree.simulation.Coalescence;
import pedigree.simulation.Simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Projet 2 - IFT2015
 * Maël LE PETIT
 */
public class Main {
    public static void main(String[] args) {
        if (args.length != 2) System.out.println("args: [1] n  & [2] tMax");
        int n = Integer.parseInt(args[0]);
        int Tmax = Integer.parseInt(args[1]);

        // Simulation
        Simulation simulation = new Simulation(n, Tmax);
        simulation.simulate();

        // Coalescence
        Coalescence coalescence = new Coalescence(simulation);
        Map<Integer, Integer> cpMale = coalescence.getCoalescencePointMale();
        Map<Integer, Integer> cpFemale = coalescence.getCoalescencePointFemale();

        Map<Integer, Integer> sortedCpMale = new TreeMap<>(Collections.reverseOrder());
        sortedCpMale.putAll(cpMale);

        Map<Integer, Integer> sortedCpFemale = new TreeMap<>(Collections.reverseOrder());
        sortedCpFemale.putAll(cpFemale);

        ArrayList time = simulation.getTimeHistory();
        ArrayList populationHistory = simulation.getPopulationHistory();

        // Report
        System.out.println("\n--------- Authors ---------\n");
        System.out.println("-> Maël LE PETIT (20143452)");
        System.out.println("\n---------------------------\n");

        System.out.println("-> population\n");
        for (int i = 0; i < time.size(); i++) {
            System.out.println("(n = " + populationHistory.get(i) + ", t = " + time.get(i) + ")");
        }
        System.out.println("-> maternal lines\n");
        sortedCpFemale.forEach((key, value) -> System.out.println("(n = " + value + ", t = " + key + ")"));
        System.out.println("-> paternal lines\n");
        sortedCpMale.forEach((key, value) -> System.out.println("(n = " + value + ", t = " + key + ")"));
    }
}
