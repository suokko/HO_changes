package hoplugins;
 
import hoplugins.pluginIFA.PluginIfaPanel;

import java.io.File;

import plugins.IHOMiniModel;
import plugins.IOfficialPlugin;
import plugins.IPlugin;
 
public class PluginIFA implements IPlugin, IOfficialPlugin {
	public static IHOMiniModel MINIMODEL = null;
 
	public String getName() {
		return "Inter. Friendly Analyzer " + getVersion();
	}
 
	public void start(IHOMiniModel miniModel) {
		MINIMODEL = miniModel;
		PluginIfaPanel mainPanel = new PluginIfaPanel();
		miniModel.getGUI().addTab("Inter. Friendly Analyzer", mainPanel);
	}
 
	public double getVersion() {
		return 0.96D;
	}
 
	public int getPluginID() {
		return 26;
	}
 
	public String getPluginName() {
		return "Inter. Friendly Analyzer";
	}
 
	public File[] getUnquenchableFiles() {
		File[] files = new File[1];
		files[0] = new File("hoplugins/pluginIFA/config/config.xml");
		return files;
	}
}