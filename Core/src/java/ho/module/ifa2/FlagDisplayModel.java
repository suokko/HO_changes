package ho.module.ifa2;

import java.util.ArrayList;
import java.util.List;

public class FlagDisplayModel {

	private int brightness = 50;
	private int flagWidth = 8;
	private boolean grey = true;
	private boolean roundFlag = false;
	private List<ModelChangeListener> listeners = new ArrayList<ModelChangeListener>();

	public int getBrightness() {
		return brightness;
	}

	public void setBrightness(int brightness) {
		for (int i = this.listeners.size() - 1; i >= 0; i--) {
			this.listeners.get(i).brightnessChanged();
		}
		this.brightness = brightness;
	}

	public boolean isGrey() {
		return grey;
	}

	public void setGrey(boolean grey) {
		this.grey = grey;
		for (int i = this.listeners.size() - 1; i >= 0; i--) {
			this.listeners.get(i).brightnessChanged();
		}		
	}

	public boolean isRoundFlag() {
		return roundFlag;
	}

	public void setRoundFlag(boolean roundflag) {
		this.roundFlag = roundflag;
	}

	public int getFlagWidth() {
		return flagWidth;
	}

	public void setFlagWidth(int flagWidth) {
		this.flagWidth = flagWidth;
		for (int i = this.listeners.size() - 1; i >= 0; i--) {
			this.listeners.get(i).flagSizeChanged();
		}
	}

	public void addModelChangeListener(ModelChangeListener listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	public void removeModelChangeListener(ModelChangeListener listener) {
		this.listeners.remove(listener);
	}
}
