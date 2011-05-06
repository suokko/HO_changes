//// %3730484529:de.hattrickorganizer.gui.login%
//package de.hattrickorganizer.gui.login;
//
//import gui.UserParameter;
//
//import java.awt.Dimension;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.FocusEvent;
//import java.awt.event.FocusListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.WindowEvent;
//import java.awt.event.WindowListener;
//
//import javax.swing.JButton;
//import javax.swing.JCheckBox;
//import javax.swing.JDialog;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JPasswordField;
//import javax.swing.JTextField;
//
//import de.hattrickorganizer.gui.HOMainFrame;
//import de.hattrickorganizer.gui.InfoPanel;
//import de.hattrickorganizer.gui.templates.ImagePanel;
//import de.hattrickorganizer.model.HOVerwaltung;
//import de.hattrickorganizer.net.MyConnector;
//import de.hattrickorganizer.tools.Helper;
//import de.hattrickorganizer.tools.updater.UpdateController;
//
///**
// * LoginDialog Class
// * 
// * No longer in use with Oauth
// */
//@Deprecated
//public class LoginDialog extends JDialog implements ActionListener, FocusListener, KeyListener, WindowListener {
//	private static final long serialVersionUID = -6418386086443217106L;
//
//	// ~ Static fields/initializers
//	// -----------------------------------------------------------------
//
//	private static LoginWaitDialog loginWaitDialog;
//
//	// ~ Instance fields
//	// ----------------------------------------------------------------------------
//
//	private HOMainFrame m_clMainFrame;
//	private JButton m_jbAbbrechen = new JButton();
//
//	// private JLabel m_jlMeldungen = new JLabel();
//	private JButton m_jbOK = new JButton();
//	private JCheckBox m_jchProxyAktiv = new JCheckBox();
//	private JCheckBox m_jchProxyAuthAktiv = new JCheckBox();
//	private JPasswordField m_jpfPasswort = new JPasswordField();
//	private JPasswordField m_jpfProxyAuthPasswort = new JPasswordField();
//	private JTextField m_jtfName = new JTextField();
//	private JTextField m_jtfProxyAuthName = new JTextField();
//	private JTextField m_jtfProxyHost = new JTextField();
//	private JTextField m_jtfProxyPort = new JTextField();
//
//	/**
//	 * Konstuktor zum Erfassen einer Trennstelle
//	 */
//	public LoginDialog(HOMainFrame mainFrame) {
//		super(mainFrame, HOVerwaltung.instance().getLanguageString("Login"), true);
//
//		this.m_clMainFrame = mainFrame;
//
//		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//		initComponents();
//
//		// Um den Focus weiterzusetzen
//		addWindowListener(this);
//		m_jpfPasswort.setText(gui.UserParameter.instance().LoginPWD);
//
//		m_jtfName.setText(gui.UserParameter.instance().LoginName);
//		m_jbOK.setEnabled(((m_jtfName.getText().length() > 0) && (m_jpfPasswort.getPassword().length > 0)));
//		m_jtfProxyPort.setText(gui.UserParameter.instance().ProxyPort);
//		m_jtfProxyHost.setText(gui.UserParameter.instance().ProxyHost);
//		m_jchProxyAktiv.setSelected(gui.UserParameter.instance().ProxyAktiv);
//		m_jtfProxyHost.setEnabled(m_jchProxyAktiv.isSelected());
//		m_jtfProxyPort.setEnabled(m_jchProxyAktiv.isSelected());
//		m_jtfProxyAuthName.setText(gui.UserParameter.instance().ProxyAuthName);
//		m_jpfProxyAuthPasswort.setText(gui.UserParameter.instance().ProxyAuthPassword);
//		m_jchProxyAuthAktiv.setSelected(gui.UserParameter.instance().ProxyAuthAktiv);
//		m_jchProxyAuthAktiv.setEnabled(gui.UserParameter.instance().ProxyAktiv);
//		m_jtfProxyAuthName.setEnabled(m_jchProxyAuthAktiv.isSelected() && m_jchProxyAuthAktiv.isEnabled());
//		m_jpfProxyAuthPasswort.setEnabled(m_jchProxyAuthAktiv.isSelected() && m_jchProxyAuthAktiv.isEnabled());
//	}
//
//	public final void actionPerformed(ActionEvent actionEvent) {
//		if (actionEvent.getSource().equals(m_jbOK)) {
//			doLogin();
//		} else if (actionEvent.getSource().equals(m_jchProxyAktiv)) {
//			m_jtfProxyHost.setEnabled(m_jchProxyAktiv.isSelected());
//			m_jtfProxyPort.setEnabled(m_jchProxyAktiv.isSelected());
//			m_jchProxyAuthAktiv.setEnabled(m_jchProxyAktiv.isSelected());
//			m_jtfProxyAuthName.setEnabled(m_jchProxyAuthAktiv.isEnabled() && m_jchProxyAuthAktiv.isSelected());
//			m_jpfProxyAuthPasswort.setEnabled(m_jchProxyAuthAktiv.isEnabled() && m_jchProxyAuthAktiv.isSelected());
//		} else if (actionEvent.getSource().equals(m_jchProxyAuthAktiv)) {
//			m_jtfProxyAuthName.setEnabled(m_jchProxyAuthAktiv.isSelected());
//			m_jpfProxyAuthPasswort.setEnabled(m_jchProxyAuthAktiv.isSelected());
//		}
//		// Beenden
//		else {
//			this.dispose();
//		}
//	}
//
//	public final void focusGained(FocusEvent focusEvent) {
//		// Selektiert den Inhalt des Textfeldes beim eintreffen
//		if (focusEvent.getSource() instanceof JTextField) {
//			((JTextField) focusEvent.getSource()).selectAll();
//		}
//	}
//
//	public void focusLost(FocusEvent focusEvent) {
//		// nix
//	}
//
//	public void keyPressed(KeyEvent keyEvent) {
//		// nix
//	}
//
//	public final void keyReleased(KeyEvent keyEvent) {
//		// Return = ok
//		if ((keyEvent.getKeyCode() == KeyEvent.VK_ENTER) && m_jbOK.isEnabled()) {
//			m_jbOK.doClick();
//		}
//		// Esc = Exit
//		else if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
//			// System.exit ( 0 );
//		}
//		// Alle anderen Tasten
//		else {
//			// Name und Passwort vorhanden
//			if (!m_jtfName.getText().trim().equals("") && (m_jpfPasswort.getPassword().length > 0)) {
//				m_jbOK.setEnabled(true);
//			} else {
//				m_jbOK.setEnabled(false);
//			}
//		}
//	}
//
//	public void keyTyped(KeyEvent keyEvent) {
//		// nix
//	}
//
//	public void windowActivated(java.awt.event.WindowEvent windowEvent) {
//	}
//
//	public void windowClosed(java.awt.event.WindowEvent windowEvent) {
//	}
//
//	public void windowClosing(java.awt.event.WindowEvent windowEvent) {
//	}
//
//	public void windowDeactivated(java.awt.event.WindowEvent windowEvent) {
//	}
//
//	public void windowDeiconified(java.awt.event.WindowEvent windowEvent) {
//	}
//
//	public void windowIconified(java.awt.event.WindowEvent windowEvent) {
//	}
//
//	public final void windowOpened(WindowEvent e) {
//		// Wenn Username vorhanden Focus auf PWD
//		if (!m_jtfName.getText().equals("")) {
//			m_jpfPasswort.requestFocusInWindow();
//		}
//	}
//
//	// ------------------------------------------------------------
//
//	/**
//	 * Login versuchen
//	 */
//	private void doLogin() {
//		LoginDialog.loginWaitDialog.setVisible(true);
//
//		MyConnector.instance().setProxyHost(m_jtfProxyHost.getText());
//		MyConnector.instance().setUseProxy(m_jchProxyAktiv.isSelected());
//		MyConnector.instance().setProxyPort(m_jtfProxyPort.getText());
//		MyConnector.instance().setProxyAuthentifactionNeeded(m_jchProxyAuthAktiv.isSelected());
//		MyConnector.instance().setProxyUserName(m_jtfProxyAuthName.getText());
//		MyConnector.instance().setProxyUserPWD(new String(m_jpfProxyAuthPasswort.getPassword()));
//		MyConnector.instance().setUserPwd(new String(m_jpfPasswort.getPassword()));
//		MyConnector.instance().setUserName(m_jtfName.getText());
//		MyConnector.instance().enableProxy();
//
//		// HT- Ip bestimmen
//		try {
//			// vorgabe Server IP holen
//			gui.UserParameter.instance().htip = MyConnector.instance().getHattrickIPAdress();
//
//			if (gui.UserParameter.instance().htip.equals("")) {
//				gui.UserParameter.instance().htip = MyConnector.getInitialHTConnectionUrl();
//			}
//		} catch (Exception e) {
//			gui.UserParameter.instance().htip = MyConnector.getInitialHTConnectionUrl();
//		}
//
//		try {
//			if (!MyConnector.instance().isAuthenticated()) {
//				// check ob Sec PWd bereits gesetzt ist
//				boolean secCodeSet = false;
//				Exception tmpEx = null;
//				try {
//					secCodeSet = MyConnector.instance().hasSecLogin();
//				} catch (Exception eSec) {
//					tmpEx = eSec;
//				}
//				if (secCodeSet) {
//					if (!MyConnector.instance().login()) {
//						m_clMainFrame.getInfoPanel().setLangInfoText( //
//										HOVerwaltung.instance().getLanguageString("Downloadfehler")
//												+ ":\nError login Failed. Maybe wrong Password? Make sure that you use the securitycode, NOT the HT-Password! :"
//												+ gui.UserParameter.instance().htip, InfoPanel.FEHLERFARBE);
//						Helper.showMessage(this, //
//										HOVerwaltung.instance().getLanguageString("Downloadfehler")
//												+ ":\nError login Failed. Maybe wrong Password? Make sure that you use the securitycode, NOT the HT-Password! :"
//												+ gui.UserParameter.instance().htip, HOVerwaltung.instance().getLanguageString("Fehler"),
//										JOptionPane.ERROR_MESSAGE);
//					}
//				} else {
//					// Info
//					m_clMainFrame.getInfoPanel().setLangInfoText( HOVerwaltung.instance().getLanguageString("Downloadfehler") //
//									+ ":\nNo Secure Password set or general connection problem: " + gui.UserParameter.instance().htip,
//							InfoPanel.FEHLERFARBE);
//					Helper.showMessage(this, HOVerwaltung.instance().getLanguageString("Downloadfehler")
//							+ ":\nNo Secure Password set or general connection problem: "
//							+ gui.UserParameter.instance().htip
//							+ (tmpEx != null ? ("\n\nDetail: " + (tmpEx.getMessage() != null && tmpEx.getMessage().length() > 40 ? tmpEx
//									.getMessage().substring(0, 40)
//									+ "..." : tmpEx.getMessage())) : ""), HOVerwaltung.instance().getLanguageString("Fehler"),
//							JOptionPane.ERROR_MESSAGE);
//					LoginDialog.loginWaitDialog.setVisible(false);
//					return;
//				}
//			}
//		} catch (Exception e) {
//			// Info
//			m_clMainFrame.getInfoPanel().setLangInfoText(
//					HOVerwaltung.instance().getLanguageString("Downloadfehler") + " : Error login Failed. Maybe wrong Password? :"
//							+ gui.UserParameter.instance().htip, InfoPanel.FEHLERFARBE);
//			Helper.showMessage(this, HOVerwaltung.instance().getLanguageString("Downloadfehler")
//					+ " : Error login Failed. Maybe wrong Password? :" + gui.UserParameter.instance().htip, HOVerwaltung.instance()
//					.getLanguageString("Fehler"), JOptionPane.ERROR_MESSAGE);
//			LoginDialog.loginWaitDialog.setVisible(false);
//			return;
//		}
//
//		gui.UserParameter.instance().LoginPWD = new String(m_jpfPasswort.getPassword());
//
//		gui.UserParameter.instance().LoginName = m_jtfName.getText();
//		gui.UserParameter.instance().ProxyAktiv = m_jchProxyAktiv.isSelected();
//		gui.UserParameter.instance().ProxyHost = m_jtfProxyHost.getText();
//		gui.UserParameter.instance().ProxyPort = m_jtfProxyPort.getText();
//		gui.UserParameter.instance().ProxyAuthAktiv = m_jchProxyAuthAktiv.isSelected();
//		gui.UserParameter.instance().ProxyAuthName = m_jtfProxyAuthName.getText();
//		gui.UserParameter.instance().ProxyAuthPassword = new String(m_jpfProxyAuthPasswort.getPassword());
//
//		this.setVisible(false);
//
//		LoginDialog.loginWaitDialog.setVisible(false);
//
//		// update prüfen
//		if (UserParameter.instance().updateCheck) {
//			UpdateController.check4update();
//		}
//		if (UserParameter.instance().userCheck) {
//			UpdateController.updateUsers();
//		}
//		if (UserParameter.instance().newsCheck) {
//			UpdateController.checkNews();
//		}
//	}
//
//	/**
//	 * Komponenten des Panels initial setzen
//	 */
//	private void initComponents() {
//		JLabel label;
//		JPanel panel;
//
//		setContentPane(new ImagePanel());
//		getContentPane().setLayout(null);
//
//		// User Daten
//		panel = new ImagePanel();
//		panel.setLayout(null);
//
//		label = new JLabel(HOVerwaltung.instance().getLanguageString("Benutzername"));
//		label.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Login_Name"));
//		label.setLocation(10, 15);
//		label.setSize(180, 25);
//		panel.add(label);
//
//		m_jtfName.setLocation(205, 15);
//		m_jtfName.setSize(145, 25);
//		m_jtfName.addFocusListener(this);
//		m_jtfName.addKeyListener(this);
//		panel.add(m_jtfName);
//
//		label = new JLabel(HOVerwaltung.instance().getLanguageString("securitycode"));
//		label.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Login_Passwort"));
//		label.setLocation(10, 50);
//		label.setSize(185, 25);
//		panel.add(label);
//
//		m_jpfPasswort.setLocation(205, 50);
//		m_jpfPasswort.setSize(145, 25);
//		m_jpfPasswort.addFocusListener(this);
//		m_jpfPasswort.addKeyListener(this);
//		panel.add(m_jpfPasswort);
//
//		panel.setSize(355, 90);
//		panel.setLocation(5, 5);
//		panel.setBorder(new javax.swing.border.TitledBorder(HOVerwaltung.instance().getLanguageString("Benutzerdaten")));
//
//		getContentPane().add(panel);
//
//		// Proxy Daten
//		panel = new ImagePanel();
//		panel.setLayout(null);
//
//		m_jchProxyAktiv.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Login_Proxy"));
//		m_jchProxyAktiv.setLocation(5, 15);
//		m_jchProxyAktiv.setText(HOVerwaltung.instance().getLanguageString("ProxyAktiv"));
//		m_jchProxyAktiv.setSize(250, 25);
//		m_jchProxyAktiv.addActionListener(this);
//		m_jchProxyAktiv.setOpaque(false);
//		panel.add(m_jchProxyAktiv);
//
//		label = new JLabel(HOVerwaltung.instance().getLanguageString("ProxyHost"));
//		label.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Login_ProxyHost"));
//		label.setLocation(10, 40);
//		label.setSize(185, 25);
//		panel.add(label);
//
//		m_jtfProxyHost.setLocation(205, 40);
//		m_jtfProxyHost.setSize(145, 25);
//		m_jtfProxyHost.addFocusListener(this);
//		m_jtfProxyHost.addKeyListener(this);
//		panel.add(m_jtfProxyHost);
//
//		label = new JLabel(HOVerwaltung.instance().getLanguageString("ProxyPort"));
//		label.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Login_ProxyPort"));
//		label.setLocation(10, 75);
//		label.setSize(180, 25);
//		panel.add(label);
//
//		m_jtfProxyPort.setLocation(205, 75);
//		m_jtfProxyPort.setSize(145, 25);
//		m_jtfProxyPort.addFocusListener(this);
//		m_jtfProxyPort.addKeyListener(this);
//		panel.add(m_jtfProxyPort);
//
//		// Auth
//		m_jchProxyAuthAktiv.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Login_ProxyAuth"));
//		m_jchProxyAuthAktiv.setLocation(5, 120);
//		m_jchProxyAuthAktiv.setText(HOVerwaltung.instance().getLanguageString("ProxyAuthAktiv"));
//		m_jchProxyAuthAktiv.setSize(250, 25);
//		m_jchProxyAuthAktiv.addActionListener(this);
//		m_jchProxyAuthAktiv.setOpaque(false);
//		panel.add(m_jchProxyAuthAktiv);
//
//		label = new JLabel(HOVerwaltung.instance().getLanguageString("ProxyAuthName"));
//		label.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Login_ProxyAuthName"));
//		label.setLocation(10, 155);
//		label.setSize(185, 25);
//		panel.add(label);
//
//		m_jtfProxyAuthName.setLocation(205, 155);
//		m_jtfProxyAuthName.setSize(145, 25);
//		m_jtfProxyAuthName.addFocusListener(this);
//		panel.add(m_jtfProxyAuthName);
//
//		label = new JLabel(HOVerwaltung.instance().getLanguageString("ProxyAuthPassword"));
//		label.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Login_ProxyAuthPassword"));
//		label.setLocation(10, 190);
//		label.setSize(180, 25);
//		panel.add(label);
//
//		m_jpfProxyAuthPasswort.setLocation(205, 190);
//		m_jpfProxyAuthPasswort.setSize(145, 25);
//		m_jpfProxyAuthPasswort.addFocusListener(this);
//		panel.add(m_jpfProxyAuthPasswort);
//
//		panel.setSize(355, 225);
//		panel.setLocation(5, 100);
//		panel.setBorder(new javax.swing.border.TitledBorder(HOVerwaltung.instance().getLanguageString("Proxydaten")));
//
//		getContentPane().add(panel);
//
//		// Buttons
//		m_jbOK.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Login_Anmelden"));
//		m_jbOK.setText(HOVerwaltung.instance().getLanguageString("Anmelden"));
//		m_jbOK.setLocation(5, 340);
//		m_jbOK.setSize(170, 35);
//		m_jbOK.addActionListener(this);
//		getContentPane().add(m_jbOK);
//
//		m_jbAbbrechen.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Login_Abbrechen"));
//		m_jbAbbrechen.setText(HOVerwaltung.instance().getLanguageString("Abbrechen"));
//		m_jbAbbrechen.setLocation(190, 340);
//		m_jbAbbrechen.setSize(170, 35);
//		m_jbAbbrechen.addActionListener(this);
//		getContentPane().add(m_jbAbbrechen);
//
//		setSize(new java.awt.Dimension(370, 405));
//
//		final Dimension size = m_clMainFrame.getToolkit().getScreenSize();
//
//		if (size.width > this.getSize().width) { // open dialog in the middle of the screen
//			this.setLocation((size.width / 2) - (this.getSize().width / 2), (size.height / 2) - (this.getSize().height / 2));
//		}
//
//		setResizable(false);
//
//		loginWaitDialog = new LoginWaitDialog(this);
//	}
//}
