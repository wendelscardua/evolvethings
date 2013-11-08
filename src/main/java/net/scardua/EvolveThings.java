package net.scardua;

import net.scardua.ga.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class EvolveThings
{
    enum MyGenes { RED, GREEN, BLUE, POWER, RESISTANCE, LIFE }

    public static void main( String[] args )
    {
        int maxSteps = 1000;

        Genes.getInstance()
                .addGene(MyGenes.RED.name(), BinaryValue.class)
                .addGene(MyGenes.GREEN.name(), BinaryValue.class)
                .addGene(MyGenes.BLUE.name(), BinaryValue.class)
                .addGene(MyGenes.POWER.name(), FloatValue.class)
                .addGene(MyGenes.RESISTANCE.name(), FloatValue.class)
                .addGene(MyGenes.LIFE.name(), FloatValue.class);

        int popSize = 100;
        GeneticAlgorithm ga = new GeneticAlgorithm(popSize);
        Random random = new Random();
        for(int steps = 0; steps <= maxSteps; steps++) {
            if (steps > 0) {
                ga.stepOver();
            }
            // we will make random matches between the individuals:
            ArrayList<Chromosome> individuals = ga.getChromosomes();
            int[] RGB = new int[individuals.size()];
            int[] K = new int[individuals.size()];
            int[] HP = new int[individuals.size()];
            int[] P = new int[individuals.size()];
            int[] R = new int[individuals.size()];
            for(int i = 0; i < individuals.size(); i++) {
                Chromosome chromosome = individuals.get(i);
                K[i] = 0;
                HP[i] = (int) (30 * chromosome.getGeneValue(MyGenes.LIFE.name()).getFloatValue() + 30);
                P[i] = (int) (5 * chromosome.getGeneValue(MyGenes.POWER.name()).getFloatValue() + 5);
                R[i] = (int) (5 * chromosome.getGeneValue(MyGenes.RESISTANCE.name()).getFloatValue() + 5);
                RGB[i] = chromosome.getGeneValue(MyGenes.RED.name()).getBinaryValue() * 4 +
                         chromosome.getGeneValue(MyGenes.GREEN.name()).getBinaryValue() * 2 +
                         chromosome.getGeneValue(MyGenes.BLUE.name()).getBinaryValue();
                HP[i] -= (P[i] + R[i]);
            }

            // red has first strike (kinda), green has trample (somewhat), blue has lifelink (ok)

            for(int time = 0; time < 10000; time++) {
                int alive = 0;
                for(int i = 0; i < individuals.size(); i++) {
                    if (HP[i] > 0) alive++;
                }

                if (alive < 2) {
                    System.err.println("Alive = " + alive + ", s = " + steps + " t = " + time);
                    break;
                }

                int leftPos = random.nextInt(alive) + 1;
                int rightPos = random.nextInt(alive - 1) + 1;

                for(int i = 0; i < individuals.size(); i++) {
                    if (HP[i] > 0) leftPos--;
                    if (leftPos <= 0) { leftPos = i; break; }
                }

                for(int i = 0; i < individuals.size(); i++) {
                    if (HP[i] > 0 && i != leftPos) rightPos--;
                    if (rightPos <= 0) { rightPos = i; break; }
                }

                if (leftPos != rightPos) {
                    if (RGB[leftPos] != RGB[rightPos]) {
                        if (P[leftPos] > R[rightPos]) {
                            if ((RGB[leftPos] & 2) != 0)
                                HP[rightPos] -= P[leftPos] - R[rightPos];
                            else if (R[rightPos] == 0) {
                                HP[rightPos] -= P[leftPos];
                            }
                            if ((RGB[leftPos] & 1) != 0) HP[leftPos] += P[leftPos];
                            if (HP[rightPos] <= 0) K[leftPos]++;
                            R[rightPos] = 0;
                        } else {
                            R[rightPos] -= P[leftPos];
                        }

                        if ((RGB[leftPos] & 4) != 0 && (RGB[rightPos] & 4) == 0 && (R[rightPos] <= 0)) continue;

                        if (P[rightPos] > R[leftPos]) {
                            HP[leftPos] -= P[rightPos] - R[leftPos];
                            if ((RGB[rightPos] & 1) != 0) HP[rightPos] += P[rightPos];
                            if (HP[leftPos] <= 0) K[rightPos]++;
                        } else {
                            R[leftPos] -= P[rightPos];
                        }
                    }
                }
            }
            for(int i = 0; i < individuals.size(); i++) {
                Chromosome chromosome = individuals.get(i);
                chromosome.setFitness(K[i] * 100 + HP[i] * 10);
            }
        }
        ArrayList<Chromosome> chromosomes = ga.getChromosomes();
        Collections.sort(chromosomes, new Comparator<Chromosome>() {
            @Override
            public int compare(Chromosome left, Chromosome right) {
                double delta = right.getFitness() - left.getFitness();
                if (delta > 0) return 1;
                else if (delta < 0) return -1;
                else return 0;
            }
        });
        for(Chromosome chromosome : chromosomes) {
            System.err.println(chromosome);
        }
    }
}
