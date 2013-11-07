package net.scardua;

import net.scardua.ga.Gene;
import net.scardua.ga.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class EvolveThings
{
    public static void main( String[] args )
    {
        GeneticAlgorithm ga = new GeneticAlgorithm(20);
        Random random = new Random();
        for(int steps = 0; steps <= 2000; steps++) {
            if (steps > 0) {
                ga.stepOver();
            }
            // we will make random matches between the individuals:
            ArrayList<GeneticAlgorithm.Chromosome> individuals = ga.getChromosomes();
            int[] RGB = new int[individuals.size()];
            int[] K = new int[individuals.size()];
            int[] HP = new int[individuals.size()];
            int[] P = new int[individuals.size()];
            int[] R = new int[individuals.size()];
            for(int i = 0; i < individuals.size(); i++) {
                GeneticAlgorithm.Chromosome chromosome = individuals.get(i);
                K[i] = 0;
                HP[i] = (int) (30 * chromosome.getGeneValue(Gene.LIFE).getFloatValue() + 30);
                P[i] = (int) (5 * chromosome.getGeneValue(Gene.POWER).getFloatValue() + 5);
                R[i] = (int) (5 * chromosome.getGeneValue(Gene.RESISTANCE).getFloatValue() + 5);
                RGB[i] = chromosome.getGeneValue(Gene.COLOR_RED).getBinaryValue() * 4 +
                         chromosome.getGeneValue(Gene.COLOR_GREEN).getBinaryValue() * 2 +
                         chromosome.getGeneValue(Gene.COLOR_BLUE).getBinaryValue();
                HP[i] -= (P[i] + R[i]);
            }

            for(int time = 0; time < 2000; time++) {
                int leftPos = random.nextInt(individuals.size());
                int rightPos = random.nextInt(individuals.size());

                if (HP[leftPos] <= 0 || HP[rightPos] <= 0)
                    continue;

                if (leftPos != rightPos) {
                    if (RGB[leftPos] == RGB[rightPos]) {
                    } else {
                        if (P[leftPos] > R[rightPos]) {
                            HP[rightPos] -= P[leftPos] - R[rightPos];
                            if (RGB[leftPos] == 1) HP[leftPos] += P[leftPos];
                            if (HP[rightPos] <= 0) K[leftPos]++;
                            R[rightPos] = 0;
                        } else {
                            R[rightPos] -= P[leftPos];
                        }

                        if ((RGB[leftPos] == 4) && (R[rightPos] <= 0)) continue;

                        if (P[rightPos] > R[leftPos]) {
                            HP[leftPos] -= P[rightPos] - R[leftPos];
                            if (RGB[rightPos] == 2) HP[rightPos] += P[rightPos];
                            if (HP[leftPos] <= 0) K[rightPos]++;
                        } else {
                            R[leftPos] -= P[rightPos];
                        }
                    }
                }
            }
            System.err.println("========[" + steps + "]========");
            for(int i = 0; i < individuals.size(); i++) {
                GeneticAlgorithm.Chromosome chromosome = individuals.get(i);
                chromosome.setFitness(K[i] * 100);
                System.err.println(chromosome.toString());
            }
        }
    }
}
