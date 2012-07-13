// %2032796658:de.hattrickorganizer.gui.login%
package ho.core.net.login;

import ho.core.gui.HOMainFrame;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.net.MyConnector;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * ProxyDialog
 */
public class ProxyDialog extends JDialog {
	private static final long serialVersionUID = -2112562621278224332L;
	private HOMainFrame m_clMainFrame;
	private JButton m_jbAbbrechen = new JButton();
	private JButton m_jbOK = new JButton();
	private JCheckBox m_jchProxyAktiv = new JCheckBox();
	private JCheckBox m_jchProxyAuthAktiv = new JCheckBox();
	private JPasswordField m_jpfProxyAuthPasswort = new JPasswordField();
	private JTextField m_jtfProxyAuthName = new JTextField();
	private JTextField m_jtfProxyHost = new JTextField();
	private JTextField m_jtfProxyPort = new JTextField();

	public ProxyDialog(HOMainFrame mainFrame) {
		super(mainFrame, "Proxy", true);

		this.m_clMainFrame = mainFrame;

		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		initComponents();

		m_jtfProxyPort.setText(UserParameter.instance().ProxyPort);
		m_jtfProxyHost.setText(UserParameter.instance().ProxyHost);
		m_jchProxyAktiv.setSelected(UserParameter.instance().ProxyAktiv);
		m_jtfProxyHost.setEnabled(m_jchProxyAktiv.isSelected());
		m_jtfProxyPort.setEnabled(m_jchProxyAktiv.isSelected());
		m_jtfProxyAuthName.setText(UserParameter.instance().ProxyAuthName);
		m_jpfProxyAuthPasswort
				.setText(UserParameter.instance().ProxyAuthPassword);
		m_jchProxyAuthAktiv
				.setSelected(UserParameter.instance().ProxyAuthAktiv);
		m_jchProxyAuthAktiv.setEnabled(UserParameter.instance().ProxyAktiv);
		m_jtfProxyAuthName.setEnabled(m_jchProxyAuthAktiv.isSelected()
				&& m_jchProxyAuthAktiv.isEnabled());
		m_jpfProxyAuthPasswort.setEnabled(m_jchProxyAuthAktiv.isSelected()
				&& m_jchProxyAuthAktiv.isEnabled());

		setVisible(true);
	}

	/**
	 * Komponenten des Panels initial setzen
	 */
	private void initComponents() {
		setContentPane(new ImagePanel());
		getContentPane().setLayout(null);

		// Proxy Daten
		JPanel panel = new ImagePanel();
		panel.setLayout(null);

		m_jchProxyAktiv.setToolTipText(HOVerwaltung.instance()
				.getLanguageString("tt_Login_Proxy"));
		m_jchProxyAktiv.setLocation(5, 15);
		m_jchProxyAktiv.setText(HOVerwaltung.instance().getLanguageString(
				"ProxyAktiv"));
		m_jchProxyAktiv.setSize(250, 25);
		m_jchProxyAktiv.setOpaque(false);
		panel.add(m_jchProxyAktiv);

		JLabel label = new JLabel(HOVerwaltung.instance().getLanguageString(
				"ProxyHost"));
		label.setToolTipText(HOVerwaltung.instance().getLanguageString(
				"tt_Login_ProxyHost"));
		label.setLocation(10, 40);
		label.setSize(185, 25);
		panel.add(label);

		m_jtfProxyHost.setLocation(205, 40);
		m_jtfProxyHost.setSize(145, 25);
		panel.add(m_jtfProxyHost);

		label = new JLabel(HOVerwaltung.instance().getLanguageString(
				"ProxyPort"));
		label.setToolTipText(HOVerwaltung.instance().getLanguageString(
				"tt_Login_ProxyPort"));
		label.setLocation(10, 75);
		label.setSize(180, 25);
		panel.add(label);

		m_jtfProxyPort.setLocation(205, 75);
		m_jtfProxyPort.setSize(145, 25);
		panel.add(m_jtfProxyPort);

		// Auth
		m_jchProxyAuthAktiv.setToolTipText(HOVerwaltung.instance()
				.getLanguageString("tt_Login_ProxyAuth"));
		m_jchProxyAuthAktiv.setLocation(5, 120);
		m_jchProxyAuthAktiv.setText(HOVerwaltung.instance().getLanguageString(
				"ProxyAuthAktiv"));
		m_jchProxyAuthAktiv.setSize(250, 25);
		m_jchProxyAuthAktiv.setOpaque(false);
		panel.add(m_jchProxyAuthAktiv);

		label = new JLabel(HOVerwaltung.instance().getLanguageString(
				"ProxyAuthName"));
		label.setToolTipText(HOVerwaltung.instance().getLanguageString(
				"tt_Login_ProxyAuthName"));
		label.setLocation(10, 155);
		label.setSize(185, 25);
		panel.add(label);

		m_jtfProxyAuthName.setLocation(205, 155);
		m_jtfProxyAuthName.setSize(145, 25);
		panel.add(m_jtfProxyAuthName);

		label = new JLabel(HOVerwaltung.instance().getLanguageString(
				"ProxyAuthPassword"));
		label.setToolTipText(HOVerwaltung.instance().getLanguageString(
				"tt_Login_ProxyAuthPassword"));
		label.setLocation(10, 190);
		label.setSize(180, 25);
		panel.add(label);

		m_jpfProxyAuthPasswort.setLocation(205, 190);
		m_jpfProxyAuthPasswort.setSize(145, 25);
		panel.add(m_jpfProxyAuthPasswort);

		panel.setSize(355, 225);
		panel.setLocation(5, 5);
		panel.setBorder(new TitledBorder(HOVerwaltung.instance()
				.getLanguageString("Proxydaten")));

		getContentPane().add(panel);

		// Buttons
		m_jbOK.setToolTipText(HOVerwaltung.instance().getLanguageString(
				"tt_Login_Anmelden"));
		m_jbOK.setText(HOVerwaltung.instance().getLanguageString("Anmelden"));
		m_jbOK.setLocation(5, 245);
		m_jbOK.setSize(170, 35);
		getContentPane().add(m_jbOK);

		m_jbAbbrechen.setToolTipText(HOVerwaltung.instance().getLanguageString(
				"tt_Login_Abbrechen"));
		m_jbAbbrechen.setText(HOVerwaltung.instance().getLanguageString(
				"Abbrechen"));
		m_jbAbbrechen.setLocation(190, 245);
		m_jbAbbrechen.setSize(170, 35);
		getContentPane().add(m_jbAbbrechen);

		setSize(new Dimension(370, 310));

		Dimension size = m_clMainFrame.getToolkit().getScreenSize();
		if (size.width > this.getSize().width) {
			// Mittig positionieren
			this.setLocation((size.width / 2) - (this.getSize().width / 2),
					(size.height / 2) - (this.getSize().height / 2));
		}

		setResizable(false);

		new LoginWaitDialog(this);
	}

	private void addListeners() {
		KeyListener kl = new KeyAdapter() {
			@Override
			public final void keyReleased(KeyEvent keyEvent) {
				// Return = ok
				if ((keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
						&& m_jbOK.isEnabled()) {
					m_jbOK.doClick();
				}
			}
		};
		m_jtfProxyHost.addKeyListener(kl);
		m_jtfProxyPort.addKeyListener(kl);

		FocusListener fl = new FocusAdapter() {
			@Override
			public final void focusGained(FocusEvent focusEvent) {
				// Selektiert den Inhalt des Textfeldes beim eintreffen
				if (focusEvent.getSource() instanceof JTextField) {
					((JTextField) focusEvent.getSource()).selectAll();
				}
			}
		};
		m_jtfProxyHost.addFocusListener(fl);
		m_jtfProxyPort.addFocusListener(fl);
		m_jtfProxyAuthName.addFocusListener(fl);
		m_jpfProxyAuthPasswort.addFocusListener(fl);

		ActionListener al = new ActionListener() {

			@Override
			public final void actionPerformed(ActionEvent actionEvent) {
				if (actionEvent.getSource().equals(m_jbOK)) {
					saveSettings();
					setVisible(false);
				} else if (actionEvent.getSource().equals(m_jchProxyAktiv)) {
					m_jtfProxyHost.setEnabled(m_jchProxyAktiv.isSelected());
					m_jtfProxyPort.setEnabled(m_jchProxyAktiv.isSelected());
					m_jchProxyAuthAktiv
							.setEnabled(m_jchProxyAktiv.isSelected());
					m_jtfProxyAuthName.setEnabled(m_jchProxyAuthAktiv
							.isEnabled() && m_jchProxyAuthAktiv.isSelected());
					m_jpfProxyAuthPasswort.setEnabled(m_jchProxyAuthAktiv
							.isEnabled() && m_jchProxyAuthAktiv.isSelected());
				} else if (actionEvent.getSource().equals(m_jchProxyAuthAktiv)) {
					m_jtfProxyAuthName.setEnabled(m_jchProxyAuthAktiv
							.isSelected());
					m_jpfProxyAuthPasswort.setEnabled(m_jchProxyAuthAktiv
							.isSelected());
				} else {
					// Beenden
					dispose();
				}
			}
		};
		m_jchProxyAktiv.addActionListener(al);
		m_jbOK.addActionListener(al);
		m_jchProxyAuthAktiv.addActionListener(al);
		m_jbAbbrechen.addActionListener(al);
	}

	/**
	 * Login versuchen
	 */
	private void saveSettings() {
		MyConnector.instance().setProxyHost(m_jtfProxyHost.getText());
		MyConnector.instance().setUseProxy(m_jchProxyAktiv.isSelected());
		MyConnector.instance().setProxyPort(m_jtfProxyPort.getText());
		MyConnector.instance().setProxyAuthentifactionNeeded(
				m_jchProxyAuthAktiv.isSelected());
		MyConnector.instance().setProxyUserName(m_jtfProxyAuthName.getText());
		MyConnector.instance().setProxyUserPWD(
				new String(m_jpfProxyAuthPasswort.getPassword()));
		MyConnector.instance().enableProxy();
		UserParameter.instance().ProxyAktiv = m_jchProxyAktiv.isSelected();
		UserParameter.instance().ProxyHost = m_jtfProxyHost.getText();
		UserParameter.instance().ProxyPort = m_jtfProxyPort.getText();
		UserParameter.instance().ProxyAuthAktiv = m_jchProxyAuthAktiv
				.isSelected();
		UserParameter.instance().ProxyAuthName = m_jtfProxyAuthName.getText();
		UserParameter.instance().ProxyAuthPassword = new String(
				m_jpfProxyAuthPasswort.getPassword());
	}
}
