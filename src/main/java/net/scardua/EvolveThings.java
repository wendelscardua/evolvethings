package net.scardua;

import net.scardua.ga.GeneticAlgorithm;

/**
 * Hello world!
 *
 */
public class EvolveThings
{
    public static void main( String[] args )
    {
        GeneticAlgorithm ga = new GeneticAlgorithm(200);
        for(int steps = 0; steps <= 5000; steps++) {
            if (steps > 0) {
                ga.stepOver();
            }
            int countGood = 0;
            for(GeneticAlgorithm.Chromosome chromosome : ga.getChromosomes()) {
                double fit = 0;
                for(GeneticAlgorithm.Value value : chromosome.getGenome()) {
                    fit += value.getBinaryValue();
                }
                chromosome.setFitness(fit);
                if (fit > 2.5) countGood++;
            }
            System.err.println(countGood);
        }
    }
}
