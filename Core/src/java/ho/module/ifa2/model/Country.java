package ho.module.ifa2.model;

import ho.core.gui.theme.ImageUtilities;
import ho.core.model.WorldDetailsManager;

import javax.swing.ImageIcon;

public class Country {

	private int countryId;

	public Country(int countryId) {
		this.countryId = countryId;
	}

	public int getCountryId() {
		return this.countryId;
	}

	public String getName() {
		return WorldDetailsManager.instance().getNameByCountryId(this.countryId);
	}

	public ImageIcon getCountryFlag() {
		return ImageUtilities.getFlagIcon(this.countryId);
	}

}
