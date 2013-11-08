package net.scardua.nn;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: wendel
 * Date: 11/8/13
 * Time: 00:17
 */
public class NeuralNetwork {

    private final int numInputs;
    private final int numOutputs;
    private final int numHiddenLayers;
    private final int numNeuronsPerHiddenLayer;
    private final ArrayList<NeuronLayer> layers;

    public NeuralNetwork(int numInputs, int numOutputs, int numHiddenLayers, int numNeuronsPerHiddenLayer) {
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
        this.numHiddenLayers = numHiddenLayers;
        this.numNeuronsPerHiddenLayer = numNeuronsPerHiddenLayer;
        this.layers = new ArrayList<NeuronLayer>();

        if (numHiddenLayers > 0)
        {
            //create first hidden layer
            this.layers.add(new NeuronLayer(numNeuronsPerHiddenLayer, numInputs));

            for (int i=0; i<numHiddenLayers-1; ++i)
            {

                this.layers.add(new NeuronLayer(numNeuronsPerHiddenLayer,
                        numNeuronsPerHiddenLayer));
            }

            //create output layer
            layers.add(new NeuronLayer(numOutputs, numNeuronsPerHiddenLayer));
        }

        else
        {
            //create output layer
            layers.add(new NeuronLayer(numOutputs, numInputs));
        }
    }

    public ArrayList<Double> getWeights() {
        //this will hold the weights
        ArrayList<Double> weights = new ArrayList<Double>();

        //for each layer
        for (int i=0; i<numHiddenLayers + 1; ++i)
        {

            //for each neuron
            for (int j=0; j < layers.get(i).numNeurons; ++j)
            {
                //for each weight
                for (int k=0; k<layers.get(i).neurons[j].numInputs; ++k)
                {
                    weights.add(layers.get(i).neurons[j].weights[k]);
                }
            }
        }

        return weights;
    }

    public void putWeights(ArrayList<Double> weights) {
        int count = 0;
        //for each layer
        for (int i=0; i<numHiddenLayers + 1; ++i)
        {

            //for each neuron
            for (int j=0; j < layers.get(i).numNeurons; ++j)
            {
                //for each weight
                for (int k=0; k<layers.get(i).neurons[j].numInputs; ++k)
                {
                    layers.get(i).neurons[j].weights[k] = weights.get(count++);
                }
            }
        }
    }

    public int getNumWeights() {
        {

            int weights = 0;

            //for each layer
            for (int i=0; i<numHiddenLayers + 1; ++i)
            {

                //for each neuron
                for (int j=0; j<layers.get(i).numNeurons; ++j)
                {
                    //for each weight
                    for (int k=0; k<layers.get(i).neurons[j].numInputs; ++k)
                        weights++;
                }
            }

            return weights;
        }

    }

    // TODO: update

    public static class Neuron {
        private final int numInputs;
        private final double[] weights;

        public Neuron(int numInputs) {
            Random random = new Random();
            this.numInputs = numInputs;
            this.weights = new double[numInputs + 1];
            for(int i = 0; i < numInputs + 1; i++)
                this.weights[i] = random.nextDouble() - random.nextDouble();
        }
    }

    public static class NeuronLayer {
        private final int numNeurons;
        private final Neuron[] neurons;

        public NeuronLayer(int numNeurons, int inputsPerNeuron) {
            this.numNeurons = numNeurons;
            this.neurons = new Neuron[numNeurons];
            for(int i = 0; i < numNeurons; i++) {
                this.neurons[i] = new Neuron(inputsPerNeuron);
            }
        }
    }
}
