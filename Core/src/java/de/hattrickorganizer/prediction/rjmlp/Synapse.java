package de.hattrickorganizer.prediction.rjmlp;

class Synapse {
	private double weight = 0;
	Neuron fromNeuron;
	
	Synapse (Neuron fromNeuron) {
		this.fromNeuron = fromNeuron;
	}
	
	void setWeight (double weight) {
		this.weight = weight;
	}

	double getWeight () {
		return this.weight;
	}
	
	Neuron getFromNeuron() {
		return fromNeuron;
	}

	public String toString () {
		return "[SynWeight="+weight+"]";
	}
}
