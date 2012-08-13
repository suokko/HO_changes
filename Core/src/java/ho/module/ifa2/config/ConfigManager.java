package ho.module.ifa2.config;

import ho.module.ifa2.EmblemPanel;
import ho.module.ifa2.ImageDesignPanel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.net.URL;

public class ConfigManager {

	public static String getDefaultFromConfig(String tagName) {
		if (tagName.equalsIgnoreCase("visitedWidth"))
			return "8";
		if (tagName.equalsIgnoreCase("hostedWidth"))
			return "8";
		if (tagName.equalsIgnoreCase("visitedHeaderShow"))
			return "true";
		if (tagName.equalsIgnoreCase("visitedFooterShow"))
			return "true";
		if (tagName.equalsIgnoreCase("hostedHeaderShow"))
			return "true";
		if (tagName.equalsIgnoreCase("hostedFooterShow"))
			return "true";
		if (tagName.equalsIgnoreCase("visitedRoundly"))
			return "false";
		if (tagName.equalsIgnoreCase("visitedGrey"))
			return "true";
		if (tagName.equalsIgnoreCase("visitedBrightness"))
			return "50";
		if (tagName.equalsIgnoreCase("hostedRoundly"))
			return "false";
		if (tagName.equalsIgnoreCase("hostedGrey"))
			return "true";
		if (tagName.equalsIgnoreCase("hostedBrightness"))
			return "50";
		if (tagName.equalsIgnoreCase("visitedHeader"))
			return "Visited";
		if (tagName.equalsIgnoreCase("hostedHeader"))
			return "Hosted";
		if (tagName.equalsIgnoreCase("visitedPathEmblem"))
			return "";
		if (tagName.equalsIgnoreCase("hostedPathEmblem"))
			return "";
		if (tagName.equalsIgnoreCase("gifAnimated"))
			return "false";
		if (tagName.equalsIgnoreCase("gifDelay")) {
			return "5.0";
		}
		return "";
	}

	public static String getTextFromConfig(String tagName) {
		try {
			URL url = ImageDesignPanel.class.getResource("config/config.txt");
			FileInputStream in = new FileInputStream(new File(url.getPath()));
			LineNumberReader lineReader = new LineNumberReader(
					new InputStreamReader(in));
			String retVal = "";

			while (retVal != null) {
				retVal = lineReader.readLine();
				String[] tmp = retVal.split("=");
				if (tmp[0].equalsIgnoreCase(tagName)) {
					return tmp[1];
				}

			}

			lineReader.close();
			in.close();
		} catch (Exception e) {
			return getDefaultFromConfig(tagName);
		}
		return "";
	}

	public static void saveConfig(ImageDesignPanel imageDesignPanel)
			throws Exception {
		File file = new File(System.getProperty("user.dir") + File.separator
				+ "hoplugins" + File.separator + "pluginIFA" + File.separator
				+ "config" + File.separator + "config.txt");
		if (!file.exists())
			file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		PrintWriter pw = new PrintWriter(fos);

		EmblemPanel visitedEmblemPanel = imageDesignPanel.getEmblemPanel(true);
		EmblemPanel hostedEmblemPanel = imageDesignPanel.getEmblemPanel(false);
		pw.println("visitedWidth=" + visitedEmblemPanel.getFlagWidth());
		pw.println("hostedWidth=" + hostedEmblemPanel.getFlagWidth());
		pw.println("visitedHeaderShow=" + visitedEmblemPanel.isHeader());
		pw.println("visitedFooterShow=" + visitedEmblemPanel.isFooter());
		pw.println("visitedBrightness=" + visitedEmblemPanel.getBrightness());
		pw.println("visitedGrey=" + visitedEmblemPanel.isGrey());
		pw.println("visitedRoundly=" + visitedEmblemPanel.isRoundly());
		pw.println("hostedBrightness=" + hostedEmblemPanel.getBrightness());
		pw.println("hostedGrey=" + hostedEmblemPanel.isGrey());
		pw.println("hostedRoundly=" + hostedEmblemPanel.isRoundly());
		pw.println("hostedHeaderShow=" + hostedEmblemPanel.isHeader());
		pw.println("hostedFooterShow=" + hostedEmblemPanel.isFooter());
		pw.println("visitedHeader=" + visitedEmblemPanel.getHeaderText());
		pw.println("hostedHeader=" + hostedEmblemPanel.getHeaderText());
		pw.println("visitedPathEmblem=" + visitedEmblemPanel.getImagePath());
		pw.println("hostedPathEmblem=" + hostedEmblemPanel.getImagePath());
		pw.println("gifAnimated=" + imageDesignPanel.isAnimGif());
		pw.println("gifDelay="
				+ imageDesignPanel.getDelaySpinner().getValue().toString());

		pw.close();
		fos.flush();
		fos.close();
	}
}
