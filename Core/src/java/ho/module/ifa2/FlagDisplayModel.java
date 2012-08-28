package ho.module.ifa2;

public class FlagDisplayModel {

	private int brightness = 50;
	private int flagWidth = 8;
	private boolean grey = true;
	private boolean roundFlag = false;

	public int getBrightness() {
		return brightness;
	}

	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}

	public boolean isGrey() {
		return grey;
	}

	public void setGrey(boolean grey) {
		this.grey = grey;
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
	}
}
