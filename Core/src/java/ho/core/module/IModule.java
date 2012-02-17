package ho.core.module;

import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public interface IModule {

	public static final int PLAYEROVERVIEW 	= 1; 
	public static final int LINEUP 			= 2;
	public static final int SERIES 			= 3;
	public static final int MATCHES 		= 4;
	public static final int PLAYERANALYSIS 	= 5;
	public static final int STATISTICS 		= 6;
	public static final int TRANSFERS 		= 7;
	public static final int TRAINING 		= 8;
	public static final int MISC 			= 9;
	public static final int TEAMANALYZER 	= 10;
	public static final int TSFORECAST 		= 11;
	public static final int SPECIALEVENTS	= 12;
	public static final int TEAM_OF_THE_WEEK= 13;
	public static final int NTHRF			= 14;
	public static final int EVIL_CARD		= 15;
	
	
	public int getModuleId();
	public String getDescription();
	public boolean hasMainTab();
	public JPanel createTabPanel();
	public boolean hasConfigPanel();
	public JPanel createConfigPanel();
	public boolean isStartup();
	public void setStartup(boolean value);
	public boolean isActive();
	public void setActive(boolean value);
	public KeyStroke getKeyStroke();
	public boolean hasMenu();
	public JMenu getMenu();
	
}
