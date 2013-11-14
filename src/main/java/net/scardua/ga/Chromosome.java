package net.scardua.ga;

import java.util.ArrayList;
import java.util.Random;

/**
* Created with IntelliJ IDEA.
* User: wendel
* Date: 11/7/13
* Time: 22:49
* To change this template use File | Settings | File Templates.
*/
public class Chromosome {
    private ArrayList<Value> genome = new ArrayList<Value>();
    double fitness = 0;

    public Chromosome(Chromosome original) {
        for(Genes.Gene gene: Genes.getInstance().values()) {
            Value originalValue = original.getGenome().get(gene.position);
            Value clonedValue = null;
            if (gene.type == BinaryValue.class) {
                clonedValue = new BinaryValue(originalValue.getBinaryValue());
            } else if (gene.type == FloatValue.class) {
                clonedValue = new FloatValue(originalValue.getFloatValue());
            }
            genome.add(clonedValue);
        }
    }

    public Chromosome() {
        for(Genes.Gene gene: Genes.getInstance().values()) {
            Value value = null;
            if (gene.type == BinaryValue.class) {
                value = new BinaryValue();
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

    public Value getGeneValue(String geneName) {
        return this.getGeneValue(Genes.getInstance().getGene(geneName));
    }

    public Value getGeneValue(Genes.Gene gene) {
        return this.genome.get(gene.position);
    }

    public void setGeneValue(String geneName, Value value) {
        setGeneValue(Genes.getInstance().getGene(geneName), value);
    }

    public void setGeneValue(Genes.Gene gene, Value value) {
        this.genome.set(gene.position, value);
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
            if (random.nextFloat() < GeneticAlgorithm.mutationChance) {
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
        for(Genes.Gene gene: Genes.getInstance().values()) {
            sb.append("[").append(gene.name).append(":").append(this.genome.get(gene.position)).append("]");
        }
        return sb.append('(').append(this.fitness).append(')').toString();
    }
}
