package de.hattrickorganizer.prediction.rjmlp;

import java.util.ArrayList;

class Layer {
	private ArrayList neurons;

	Layer () {
		this.neurons = new ArrayList();
	}
	
	void setForcedInputs (double[] inputs) {
		if (inputs.length == neurons.size()) {
			for (int i=0; i<getNeuronCount(); i++) {
				getNeuron(i).setForcedInput(inputs[i]);
			}
		} else {
			System.out.println ("Layer: Wrong number of input parameters");
		}
	}
	
	void addNeuron (Neuron neuron) {
		neurons.add(neuron);
	}
	
	ArrayList getNeuronList () {
		return neurons;
	}

	ArrayList getSynapseList () {
		ArrayList retList = new ArrayList();
		for (int i=0; i<getNeuronCount(); i++)
			retList.addAll(getNeuron(i).getSynapseList());
		return retList;
	}

	Neuron getNeuron (int num) {
		return (Neuron)neurons.get(num);
	}

	int getNeuronCount () {
		return neurons.size();
	}

	Synapse getSynapse (int num) {
		return (Synapse)getSynapseList().get(num);
	}

	int getSynapseCount () {
		return getSynapseList().size();
	}

	void setNormalization (int neuronNo, double delta, double multi) {
			getNeuron(neuronNo).setNormalization (delta, multi);
	}
	
	void setNormalization (int neuronNo, String values) {
		String[] splitted = values.split(" ");
		if (splitted.length == 2) {
			double multi = Double.parseDouble(splitted[0]);
			double delta = Double.parseDouble(splitted[1]); 
			setNormalization(neuronNo, delta, multi);
		} else
			System.out.println ("Invalid parameters for normalization: "+values);
	}

	public String toString() {
		String retVal = "Layer-Dump:\n";
		retVal += "\tNeurons:\n";
		for (int i=0; i<getNeuronCount(); i++) {
			retVal += "\t" + i + "\t" + getNeuron(i).toString() + "\n";
		}
		return retVal;
	}
}
