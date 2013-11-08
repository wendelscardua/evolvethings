package net.scardua.ga;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: wendel
 * Date: 11/7/13
 * Time: 00:09
 */
public class GeneticAlgorithm {

    private final int numChromosomes;
    private ArrayList<Chromosome> chromosomes;
    public static final double mutationChance = 0.05;
    private static final double crossoverChance = 0.7;

    public GeneticAlgorithm(int numChromosomes) {
        this.numChromosomes = numChromosomes;
        this.chromosomes = new ArrayList<Chromosome>();
        for(int i = 0; i < numChromosomes; i++) {
            this.chromosomes.add(new Chromosome());
        }
    }

    public ArrayList<Chromosome> getChromosomes() {
        return this.chromosomes;
    }

    public void stepOver() {
        ArrayList<Chromosome> nextGeneration = new ArrayList<Chromosome>();
        Random random = new Random();
        while(nextGeneration.size() < numChromosomes) {
            Chromosome left = getChromosomeByRoulette();
            Chromosome right = getChromosomeByRoulette();

            if (random.nextDouble() < crossoverChance) {
                left.crossoverWith(right);
            }

            left.mutate();
            right.mutate();

            nextGeneration.add(left);
            nextGeneration.add(right);
        }
        this.chromosomes = nextGeneration;
    }

    private Chromosome getChromosomeByRoulette() {
        double worstFit = Double.POSITIVE_INFINITY;
        for(Chromosome chromosome : chromosomes) {
            if (chromosome.fitness < worstFit) worstFit = chromosome.fitness;
        }
        double totalFit = 0;
        for(Chromosome chromosome : chromosomes) {
            totalFit += chromosome.fitness - worstFit;
        }
        double slice = new Random().nextDouble() * totalFit;
        for(Chromosome chromosome : chromosomes) {
            slice -= (chromosome.fitness - worstFit);
            if (slice <= 0.0) {
                return new Chromosome(chromosome);
            }
        }
        return chromosomes.get(0); // to avoid warnings
    }

}
