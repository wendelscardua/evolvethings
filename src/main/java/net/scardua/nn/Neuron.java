package net.scardua.nn;

import java.util.Random;

/**
* Created with IntelliJ IDEA.
* User: wendel
* Date: 11/9/13
* Time: 01:25
* To change this template use File | Settings | File Templates.
*/
public class Neuron {
    public final int numInputs;
    public final double[] weights;

    public Neuron(int numInputs) {
        Random random = new Random();
        this.numInputs = numInputs;
        this.weights = new double[numInputs + 1];
        for(int i = 0; i < numInputs + 1; i++)
            this.weights[i] = random.nextDouble() - random.nextDouble();
    }
}
