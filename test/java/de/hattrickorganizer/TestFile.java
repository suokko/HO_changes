package de.hattrickorganizer;

import java.io.File;
import java.io.IOException;

import de.hattrickorganizer.gui.menu.DownloadDialog;
import de.hattrickorganizer.net.MyConnector;
import de.hattrickorganizer.tools.updater.UpdateHelper;


public class TestFile {

	public static void main(String[] args) {
//		File old = new File("epv.dat");
//		File oldTmp = new File("epv.tmp");
//		old.renameTo(oldTmp);		
		File tmp = new File("tmp.dat");
		setProxy();		
		if (!UpdateHelper
			.instance()
			.download(
				"http://www.homemail.it/ht/epv.dat",
				tmp)) {
					System.out.println("Error");					
			//return;
		}
		tmp = null;
		File donwloaded = new File("tmp.dat");
		File target = new File("epv.dat");
		target.delete();
		donwloaded.renameTo(new File("epv2.dat"));
	}

	private static void setProxy() {
		MyConnector.instance().setProxyHost("proxy6");
		MyConnector.instance().setProxyPort("3128");
		MyConnector.instance().setProxyAuthentifactionNeeded(true);		
		MyConnector.instance().setProxyUserName("esp/ag50683");
		MyConnector.instance().setProxyUserPWD("Eloisa08");
		MyConnector.instance().enableProxy();
	}
}
