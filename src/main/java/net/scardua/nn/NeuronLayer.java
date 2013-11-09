package net.scardua.nn;

/**
* Created with IntelliJ IDEA.
* User: wendel
* Date: 11/9/13
* Time: 01:25
* To change this template use File | Settings | File Templates.
*/
public class NeuronLayer {
    public final int numNeurons;
    public final Neuron[] neurons;

    public NeuronLayer(int numNeurons, int inputsPerNeuron) {
        this.numNeurons = numNeurons;
        this.neurons = new Neuron[numNeurons];
        for(int i = 0; i < numNeurons; i++) {
            this.neurons[i] = new Neuron(inputsPerNeuron);
        }
    }
}
