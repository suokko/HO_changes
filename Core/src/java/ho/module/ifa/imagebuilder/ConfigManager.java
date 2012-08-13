package ho.module.ifa.imagebuilder;

import ho.core.model.HOVerwaltung;
import ho.core.module.config.ModuleConfig;

public class ConfigManager {

	static String IFA_WIDTH = "IFA_Width";
	static String IFA_HEADERSHOW = "IFA_HeaderShow";
	static String IFA_ROUNDLY = "IFA_Roundly";
	static String IFA_GREY = "IFA_Grey";
	static String IFA_BRIGHTNESS = "IFA_Brightness";
	static String IFA_VISITEDHEADER = "IFA_visitedHeader";
	static String IFA_HOSTEDHEADER = "IFA_hostedHeader";
	static String IFA_VISITEDPATHEMBLEM = "IFA_visitedPathEmblem";
	static String IFA_HOSTEDPATHEMBLEM = "IFA_hostedPathEmblem";
	static String IFA_GIFANIMATED = "IFA_gifAnimated";
	static String IFA_GIFDELAY = "IFA_gifDelay";
	
	
	static void initialze(){
		ModuleConfig config = ModuleConfig.instance();
		config.setInteger(IFA_WIDTH, Integer.valueOf(8));
		config.setBoolean(IFA_HEADERSHOW,true);
		config.setInteger(IFA_BRIGHTNESS, Integer.valueOf(50));
		config.setBoolean(IFA_GREY, true);
		config.setBoolean(IFA_ROUNDLY,false);
		config.setString(IFA_VISITEDHEADER, HOVerwaltung.instance().getLanguageString("Visited"));
		config.setString(IFA_VISITEDPATHEMBLEM,"");

		config.setString(IFA_HOSTEDHEADER,HOVerwaltung.instance().getLanguageString("Hosted"));
		config.setString(IFA_HOSTEDPATHEMBLEM,"");

		config.setBoolean(IFA_GIFANIMATED,false);
		config.setString(IFA_GIFDELAY, "5.0");
		ModuleConfig.instance().save();
	}
	
	static String getTextFromConfig(String tagName) {
		Object obj = ModuleConfig.instance().getValue(tagName);
		if(obj == null){
			initialze();
			obj = ModuleConfig.instance().getValue(tagName);
		}
		return obj.toString();
	}

	public static void saveConfig(ImageDesignPanel imageDesignPanel)
			throws Exception {
		EmblemPanel visitedEmblemPanel = imageDesignPanel.getVisitedEmblemPanel();
		ModuleConfig config = ModuleConfig.instance();
		config.setInteger(IFA_WIDTH, Integer.valueOf(visitedEmblemPanel.getFlagWidth()));
		config.setBoolean(IFA_HEADERSHOW, visitedEmblemPanel.isHeader());
		config.setInteger(IFA_BRIGHTNESS, Integer.valueOf(visitedEmblemPanel.getBrightness()));
		config.setBoolean(IFA_GREY, visitedEmblemPanel.isGrey());
		config.setBoolean(IFA_ROUNDLY, visitedEmblemPanel.isRoundly());
		config.setString(IFA_VISITEDHEADER, visitedEmblemPanel.getHeaderText());
		config.setString(IFA_VISITEDPATHEMBLEM,visitedEmblemPanel.getImagePath());

		visitedEmblemPanel = imageDesignPanel.getHostedEmblemPanel();
		config.setString(IFA_HOSTEDHEADER,visitedEmblemPanel.getHeaderText());
		config.setString(IFA_HOSTEDPATHEMBLEM,visitedEmblemPanel.getImagePath());

//		config.setBoolean(IFA_GIFANIMATED,imageDesignPanel.isAnimGif());
		config.setString(IFA_GIFDELAY, imageDesignPanel.getDelaySpinner().getValue().toString());
		ModuleConfig.instance().save();
	}
}
