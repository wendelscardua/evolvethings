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
    private static double mutationChance = 0.05;
    private static double crossoverChance = 0.7;

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

    public static class Chromosome {
        ArrayList<Value> genome = new ArrayList<Value>();
        double fitness = 0;

        public Chromosome(Chromosome original) {
            for(Gene gene: Gene.values()) {
                Value originalValue = original.getGenome().get(gene.position);
                Value clonedValue = null;
                if (gene.type == BinaryValue.class) {
                    clonedValue = new BinaryValue(originalValue.getBinaryValue());
                } else if (gene.type == IntegerValue.class) {
                    clonedValue = new IntegerValue(originalValue.getIntValue());
                } else if (gene.type == FloatValue.class) {
                    clonedValue = new FloatValue(originalValue.getFloatValue());
                }
                genome.add(clonedValue);
            }
        }

        public Chromosome() {
            for(Gene gene: Gene.values()) {
                Value value = null;
                if (gene.type == BinaryValue.class) {
                    value = new BinaryValue();
                } else if (gene.type == IntegerValue.class) {
                    value = new IntegerValue();
                } else if (gene.type == FloatValue.class) {
                    value = new FloatValue();
                }
                genome.add(value);
            }
        }

        public void setGenome(ArrayList<Value> newGenome) {
            this.genome = newGenome;
        }

        public ArrayList<Value> getGenome() {
            return this.genome;
        }

        public double getFitness() {
            return this.fitness;
        }

        public void setFitness(double fitness) {
            this.fitness = fitness;
        }

        public void mutate() {
            Random random = new Random();
            for(Value value : genome) {
                if (random.nextFloat() < mutationChance) {
                   value.mutate();
                }
            }
        }

        public void crossoverWith(Chromosome other) {
            int size = this.genome.size();
            int pos = new Random().nextInt(size);
            ArrayList<Value> myNewGenome = new ArrayList<Value>();
            ArrayList<Value> otherNewGenome = new ArrayList<Value>();
            myNewGenome.addAll(this.getGenome().subList(0, pos));
            myNewGenome.addAll(other.getGenome().subList(pos, size));
            otherNewGenome.addAll(other.getGenome().subList(0, pos));
            otherNewGenome.addAll(this.getGenome().subList(pos, size));
            this.setGenome(myNewGenome);
            other.setGenome(otherNewGenome);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for(Gene gene : Gene.values()) {
                sb.append("[").append(gene.name).append(":").append(this.genome.get(gene.position)).append("]");
            }
            return sb.append('(').append(this.fitness).append(')').toString();
        }
    }

    public static abstract class Value {
        public abstract void mutate();
        public abstract int getBinaryValue();
        public abstract int getIntValue();
        public abstract double getFloatValue();
    }

    public static class BinaryValue extends Value {
        private int value;

        public BinaryValue() {
            this(new Random().nextInt(2));
        }

        public BinaryValue(int value) {
            this.value = (value == 0 ? 0 : 1);
        }

        public int get() {
            return this.value;
        }

        public void mutate() {
            this.value = 1 - this.value;
        }

        @Override
        public int getBinaryValue() {
            return value;
        }

        @Override
        public int getIntValue() {
            return value;
        }

        @Override
        public double getFloatValue() {
            return (double) value;
        }

        public String toString() {
            return String.valueOf(this.value);
        }
    }

    public static class IntegerValue extends Value {
        private int value;

        public IntegerValue() {
            this(new Random().nextInt(256));
        }

        public IntegerValue(int value) {
            this.value = value;
        }

        public int get() {
            return this.value;
        }

        public void mutate() {
            this.value = new Random().nextInt(256);
        }

        @Override
        public int getBinaryValue() {
            if (this.value > 0) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public int getIntValue() {
            return this.value;
        }

        @Override
        public double getFloatValue() {
            return ((double) this.value) / 128.0 - 1.0;
        }

        public String toString() {
            return String.valueOf(this.value);
        }
    }

    public static class FloatValue extends Value {
        private double value;

        public FloatValue() {
            this(new Random().nextDouble() * 2 - 1);
        }

        public FloatValue(double value) {
            this.value = value;
        }

        public double get() {
            return this.value;
        }

        public void mutate() {
            this.value += new Random().nextGaussian();
            if (this.value > 1.0) this.value = 1.0;
            if (this.value < -1.0) this.value = -1.0;
        }

        @Override
        public int getBinaryValue() {
            if (this.value > 0) {
                return 1;
            }  else {
                return 0;
            }
        }

        @Override
        public int getIntValue() {
            if (this.value < 0) {
                return -1;
            } else if (this.value > 0) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public double getFloatValue() {
            return value;
        }

        public String toString() {
            return String.valueOf(this.value);
        }
    }
}
