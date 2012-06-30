/*
 * Created on 15-gen-2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ho.core.file.extension;

import ho.core.gui.HOMainFrame;
import ho.core.gui.RefreshManager;
import ho.core.model.HOVerwaltung;
import ho.core.net.OnlineWorker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JOptionPane;


/**
 * @author Mirtillo
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ExtensionListener implements Runnable {

	@Override
	public void run() {
		int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
		File dir = new File("Info/" + teamId);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, "hoe.ini");
		while (true) {
			try {
				if (file.exists() && HOMainFrame.getHOStatus() == HOMainFrame.READY) {
					Properties hoe = new Properties();
					hoe.load(new FileInputStream(file));
					boolean processed = false;
					try {
						if (hoe.getProperty("trainingUpdate", "off").equalsIgnoreCase("on")) {
							JOptionPane.showMessageDialog(
								HOMainFrame.instance(),
								"Training Update HOE");
							hoe.remove("trainingUpdate");
							processed = true;
							OnlineWorker.getHrf();
						}
						if (hoe.getProperty("economyUpdate", "off").equalsIgnoreCase("on")) {
							JOptionPane.showMessageDialog(
								HOMainFrame.instance(),
								"Economy Update HOE");
							OnlineWorker.getHrf();
							hoe.remove("economyUpdate");
							processed = true;
						}
						if (hoe.getProperty("seriesUpdate", "off").equalsIgnoreCase("on")) {
							seriesUpdate();
							matchUpdate();
							hoe.remove("seriesUpdate");
							processed = true;
						}
						if (hoe.getProperty("matchUpdate", "off").equalsIgnoreCase("on")) {
							matchUpdate();
							hoe.remove("matchUpdate");
							processed = true;
						}
					} catch (Exception e) {
					}

					try {
						if (processed) {
							RefreshManager.instance().doReInit();														
							OutputStream out = new FileOutputStream(file);
							hoe.store(out, "Configuration File");
						}
					} catch (Exception e1) {
					}
					
					hoe = null;
				}
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void seriesUpdate() {
		JOptionPane.showMessageDialog(HOMainFrame.instance(), "Series Update HOE");
		OnlineWorker.getSpielplan(-1, -1);
		StandingCreator.extractActual();
	}

	private void matchUpdate() {
		JOptionPane.showMessageDialog(HOMainFrame.instance(), "Match Update HOE");
		int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
		if (OnlineWorker.getMatches(teamId, false, true, false) != null) {
			OnlineWorker.getAllLineups();
			StadiumCreator.extractHistoric();
		}
	}

}
