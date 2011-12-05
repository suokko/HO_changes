package de.hattrickorganizer.gui.utils;

import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.player.Player;
import plugins.IMP3Player;
import de.hattrickorganizer.tools.HOLogger;

public class MP3PlayerWrapper extends Thread implements IMP3Player {

	// Startet den Player
	private Player m_clPlayer = null;
	private String m_sFile = "";

	@Override
	public void run() {
		if (!m_sFile.equals("")) {
			InputStream in = null;
			try {
				in = MP3PlayerWrapper.class.getResourceAsStream(m_sFile);
				if (in != null) {
					m_clPlayer = new Player(in);
					m_clPlayer.play();
				} else {
					HOLogger.instance().log(getClass(), "MP3 resource not found: " + m_sFile);
				}
			} catch (Exception e) {
				HOLogger.instance().log(getClass(), e);
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
					}
				}
			}
		} else {
			System.err.println("No soundfile set");
		}
	}

	public void setMP3File(String file) {
		m_sFile = file;
	}

	public void close() {
		try {
			if (m_clPlayer != null) {
				m_clPlayer.close();
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(), e);
		}
	}
}
