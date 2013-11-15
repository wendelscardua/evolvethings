package net.scardua.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private int eliteChromosomes = 0;

    private static final Random random = new Random();

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

    public void setEliteChromosomes(int eliteChromosomes) {
        this.eliteChromosomes = eliteChromosomes;
    }

    public void stepOver() {
        ArrayList<Chromosome> nextGeneration = new ArrayList<Chromosome>();
        if (this.eliteChromosomes > 0) {
            sortChromosomes();
        }
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
        double totalFit = 0;
        int count = 0;
        for(Chromosome chromosome : chromosomes) {
            if (this.eliteChromosomes > 0 && ++count > this.eliteChromosomes) break;
            totalFit += chromosome.fitness;
        }
        double slice = random.nextDouble() * totalFit;
        for(Chromosome chromosome : chromosomes) {
            slice -= chromosome.fitness;
            if (slice <= 0.0) {
                return new Chromosome(chromosome);
            }
        }
        return chromosomes.get(0); // to avoid warnings
    }

    public void sortChromosomes() {
        Collections.sort(this.chromosomes, new Comparator<Chromosome>() {
            @Override
            public int compare(Chromosome left, Chromosome right) {
                double delta = right.getFitness() - left.getFitness();
                if (delta > 0) return 1;
                else if (delta < 0) return -1;
                else return 0;
            }
        });
    }
}
