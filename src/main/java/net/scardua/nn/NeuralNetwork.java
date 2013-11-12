package net.scardua.nn;

import java.util.ArrayList;

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
    private static final double dBias = -1;
    private static final double dActivationResponse = 1;

    private double[] tempInputVector;
    private double[] tempOutputVector;

    public NeuralNetwork(int numInputs, int numOutputs, int numHiddenLayers, int numNeuronsPerHiddenLayer) {
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
        this.numHiddenLayers = numHiddenLayers;
        this.numNeuronsPerHiddenLayer = numNeuronsPerHiddenLayer;
        this.layers = new ArrayList<NeuronLayer>();

        if (this.numHiddenLayers > 0)
        {
            //create first hidden layer
            this.layers.add(new NeuronLayer(this.numNeuronsPerHiddenLayer, numInputs));

            for (int i=0; i<this.numHiddenLayers-1; ++i)
            {

                this.layers.add(new NeuronLayer(this.numNeuronsPerHiddenLayer,
                        this.numNeuronsPerHiddenLayer));
            }

            //create output layer
            this.layers.add(new NeuronLayer(this.numOutputs, this.numNeuronsPerHiddenLayer));
        }

        else
        {
            //create output layer
            this.layers.add(new NeuronLayer(this.numOutputs, this.numInputs));
        }
        this.tempInputVector = new double[Math.max(Math.max(numInputs, numOutputs), numNeuronsPerHiddenLayer) + 1];
        this.tempOutputVector = new double[Math.max(Math.max(numInputs, numOutputs), numNeuronsPerHiddenLayer) + 1];
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

    public ArrayList<Double> update(ArrayList<Double> input) {

        //first check that we have the correct amount of inputs
        if (input.size() != numInputs)
        {
            // return null if incorrect input
            return null;
        }

        for(int i = 0; i < input.size(); i++) {
            tempInputVector[i] = input.get(i);
        }

        int outPos = 0;

        //For each layer....
        for (int i=0; i<numHiddenLayers + 1; ++i)
        {
            if ( i > 0 )
            {
                double[] temp = tempInputVector;
                tempInputVector = tempOutputVector;
                tempOutputVector = temp;
            }

            int cWeight = 0;

            outPos = 0;

            //for each neuron sum the (inputs * corresponding weights).Throw
            //the total at our sigmoid function to get the output.
            for (int j=0; j< layers.get(i).numNeurons; ++j)
            {
                double netInput = 0;

                int numInputs = layers.get(i).neurons[j].numInputs;

                //for each weight
                for (int k=0; k<numInputs - 1; ++k)
                {
                    //sum the weights x inputs
                    netInput += layers.get(i).neurons[j].weights[k] *
                            tempInputVector[cWeight++];
                }

                //add in the bias
                netInput += layers.get(i).neurons[j].weights[numInputs-1] *
                        dBias;
                //we can store the outputs from each layer as we generate them.
                //The combined activation is first filtered through the sigmoid
                //function
                tempOutputVector[outPos++] = sigmoid(netInput, dActivationResponse);

                cWeight = 0;
            }
        }
        ArrayList<Double> output = new ArrayList<Double>();
        for(int i = 0; i < outPos; i++) {
            output.add(tempOutputVector[i]);
        }
        return output;
    }

    private double sigmoid(double netInput, double response) {
        return ( 1 / ( 1 + Math.exp(-netInput / response)));
    }

}
