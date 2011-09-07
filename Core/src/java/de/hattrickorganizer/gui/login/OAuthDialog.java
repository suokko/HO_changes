package de.hattrickorganizer.gui.login;

import gui.UserParameter;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.lineup.CopyListener;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.net.MyConnector;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.MyHelper;

public class OAuthDialog extends JDialog implements ActionListener, FocusListener, KeyListener, WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1798304851624958795L;

	private static LoginWaitDialog loginWaitDialog;

	// ~ Instance fields
	// ----------------------------------------------------------------------------


	private HOMainFrame m_clMainFrame;
	
	private JButton m_jbOK = new JButton();
	private JButton m_jbBrowse = new JButton();
	private JButton m_jbCancel = new JButton();
	private JTextField m_jtfAuthString = new JTextField();
	private JTextField m_jtfAuthURL = new JTextField();
	
	private String m_sUserCode;
	private String m_sUserURL;
	private boolean m_bUserCancel = false;
	private boolean m_bFirstTry = true;
	
	private OAuthService m_service;
	Token m_AccessToken;
	Token m_RequestToken;
	private String scopes = "";
	
	public OAuthDialog(HOMainFrame mainFrame, OAuthService service) {
		super(mainFrame, HOVerwaltung.instance().getLanguageString("oauth.Title"), true);

		this.m_clMainFrame = mainFrame;
		this.m_service = service;
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		if (MyConnector.isRequireSetMatchorder()) {
			scopes = "&scope=set_matchorder";
		}
		
		addWindowListener(this);
		obtainUserURL();
		initComponents();

	}

	
	private void obtainUserURL() {
		
       try {
    	   
    	   
    	   
    	   m_RequestToken = m_service.getRequestToken();
    	   m_sUserURL = m_service.getAuthorizationUrl(m_RequestToken);
    	   m_sUserURL += scopes;
       } catch (Exception e) {
    	   HOLogger.instance().error(getClass(), "Exception in obtainUserCode: " + e.getMessage());
    	   JOptionPane.showMessageDialog(null, e.getMessage(), "Error obtaining URL", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void doAuthorize() {
		
		Verifier verifier = new Verifier(m_jtfAuthString.getText().trim());
		
		try {
			
			m_AccessToken = m_service.getAccessToken(m_RequestToken, verifier);
			UserParameter.instance().AccessToken = MyHelper.cryptString(m_AccessToken.getToken());
			UserParameter.instance().TokenSecret = MyHelper.cryptString(m_AccessToken.getSecret());
			
		} catch (Exception e) {
			HOLogger.instance().error(getClass(), "Exception in doAuthorize: " + e.getMessage());
		}
		m_bFirstTry = false;
		this.dispose();
	}
	
	private void openUrlInBrowser() {
		boolean error = false;
		if( !Desktop.isDesktopSupported() ) {
            HOLogger.instance().debug(getClass(), "Desktop not supported.");
            CopyListener.copyToClipboard(m_sUserURL); // copy to clipboard
        	JOptionPane.showMessageDialog(null, "Open URL failed. It has been copied into your system clipboard, please paste it manually into your browsers address bar.", "Open URL", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Desktop desktop = Desktop.getDesktop();

        if( !desktop.isSupported( Desktop.Action.BROWSE ) ) {
        	HOLogger.instance().debug(getClass(), "Desktop BROWSE not supported.");
        	CopyListener.copyToClipboard(m_sUserURL); // copy to clipboard
        	JOptionPane.showMessageDialog(null, "Open URL failed. It has been copied into your system clipboard, please paste it manually into your browsers address bar.", "Open URL", JOptionPane.ERROR_MESSAGE);
        	return;
        }

        try {
        	URI uri = new URI( m_sUserURL );
        	desktop.browse( uri );
        }
        catch ( Exception e ) {
			HOLogger.instance().error(getClass(), "Open URL '" + m_sUserURL + "' failed: " + e.getMessage());
			CopyListener.copyToClipboard(m_sUserURL); // copy to clipboard
        	JOptionPane.showMessageDialog(null, "Open URL failed. It has been copied into your system clipboard, please paste it manually into your browsers address bar.", "Open URL", JOptionPane.ERROR_MESSAGE);
        }
        // XXX Give user help?
	}
	
	public boolean getUserCancel() {
		return m_bUserCancel;
	}
	
	public Token getAccessToken() {
		return m_AccessToken;
	}
	
	public final void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource().equals(m_jbOK)) {
			doAuthorize();
		} else if (actionEvent.getSource().equals(m_jbBrowse)) {
			openUrlInBrowser();
		} else if (actionEvent.getSource().equals(m_jbCancel)) {
			m_bUserCancel = true;
			this.dispose();
		}
	}

	public void windowActivated(WindowEvent arg0) {
	}

	public void windowClosed(WindowEvent arg0) {
	}

	public void windowClosing(WindowEvent arg0) {
	}

	public void windowDeactivated(WindowEvent arg0) {
	}

	public void windowDeiconified(WindowEvent arg0) {
	}

	public void windowIconified(WindowEvent arg0) {
	}

	public void windowOpened(WindowEvent arg0) {
		m_jtfAuthString.requestFocusInWindow();
	}

	public void keyPressed(KeyEvent arg0) {
	}

	public void keyReleased(KeyEvent keyEvent) {
		// Return = ok
		if ((keyEvent.getKeyCode() == KeyEvent.VK_ENTER) && m_jbOK.isEnabled()) {
			m_jbOK.doClick();
		
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// Esc = Exit
			m_jbCancel.doClick();
		
		} else {
			// Alle anderen Tasten
			// Name und Passwort vorhanden
			if (!m_jtfAuthString.getText().trim().equals("")) {
				m_jbOK.setEnabled(true);
			} else {
				m_jbOK.setEnabled(false);
			}
		}
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public void focusGained(FocusEvent focusEvent) {
		if (focusEvent.getSource() instanceof JTextField) {
			((JTextField) focusEvent.getSource()).selectAll();
		}
	}

	public void focusLost(FocusEvent arg0) {
	}
	
	@Override
	public void setVisible(boolean b) {
		if (m_bFirstTry == false) {
			JOptionPane.showMessageDialog(null, HOVerwaltung.instance().getLanguageString("oauth.FailedTry"), HOVerwaltung.instance().getLanguageString("oauth.FailedTryHeader"), JOptionPane.INFORMATION_MESSAGE);
			m_jtfAuthString.setText("");
		}
		super.setVisible(b);
	}

	private void initComponents() {
		JPanel panel;
		
		setContentPane(new ImagePanel(new FlowLayout()));
		
		
		panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new GridBagLayout());
//		panel.setPreferredSize(new Dimension(370, 470));
		panel.setSize(370, 470);
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(2, 2, 2, 2);		
		
		JLabel infoLabel = new JLabel();
		infoLabel.setText(HOVerwaltung.instance().getLanguageString("oauth.Intro"));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		panel.add(infoLabel, constraints);
		
		JPanel spacer = new JPanel();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		spacer.setOpaque(false);
		spacer.setPreferredSize(new Dimension(25, 10));
		panel.add(spacer, constraints);
		
		JLabel authLink = new JLabel();
		authLink.setText(HOVerwaltung.instance().getLanguageString("oauth.URLOrButton"));
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		panel.add(authLink, constraints);

		m_jbBrowse.setText(HOVerwaltung.instance().getLanguageString("oauth.OpenUrl"));
		m_jbBrowse.setSize(170,35);
		m_jbBrowse.addActionListener(this);
		m_jbBrowse.setEnabled(true);
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.NONE;
		panel.add(m_jbBrowse, constraints);
		
		m_jtfAuthURL.setText(m_sUserURL);
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		panel.add(m_jtfAuthURL, constraints);
		
		
		
		JLabel authInput = new JLabel();
		authInput.setText(HOVerwaltung.instance().getLanguageString("oauth.EnterCode"));
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		panel.add(authInput, constraints);

		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 2;
		panel.add(m_jtfAuthString, constraints);
		
		
		
		m_jbOK.setText(HOVerwaltung.instance().getLanguageString("oauth.Enter"));
		m_jbOK.setSize(170, 35);
		constraints.gridx = 0;
		constraints.gridy = 7;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.NONE;
		m_jbOK.addActionListener(this);
		//m_jbOK.setEnabled(false);
		panel.add(m_jbOK, constraints);
		
		
		m_jbCancel.setText(HOVerwaltung.instance().getLanguageString("oauth.Cancel"));
		m_jbCancel.addActionListener(this);
		m_jbCancel.setEnabled(true);
		constraints.gridx = 1;
		constraints.gridy = 7;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.EAST;
		m_jbCancel.setSize(170,35);
		panel.add(m_jbCancel, constraints);
		
		panel.setBorder(new javax.swing.border.EtchedBorder());

		getContentPane().add(panel);
		
		this.setSize(400, 500);
		pack();
		
		final Dimension size = m_clMainFrame.getToolkit().getScreenSize();
		if (size.width > this.getSize().width) { // open dialog in the middle of the screen
			this.setLocation((size.width / 2) - (this.getSize().width / 2), (size.height / 2) - (this.getSize().height / 2));
		}
	}
}
