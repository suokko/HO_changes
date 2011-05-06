package de.hattrickorganizer.gui.login;

import gui.UserParameter;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;

import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.HOVerwaltung;
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

	String REQUEST_TOKEN_ENDPOINT_URL = "https://chpp.hattrick.org/oauth/request_token.ashx";
	String AUTHORIZE_WEBSITE_URL = "https://chpp.hattrick.org/oauth/authorize.aspx";
	String ACCESS_TOKEN_ENDPOINT_URL = "https://chpp.hattrick.org/oauth/access_token.ashx";

	private HOMainFrame m_clMainFrame;
	
	private JButton m_jbOK = new JButton();
	private JButton m_jbBrowse = new JButton();
	private JButton m_jbCancel = new JButton();
	private JTextField m_jtfAuthString = new JTextField();
	private JTextField m_jtfAuthURL = new JTextField();
	
	private String m_sUserCode;
	private String m_sUserURL;
	private boolean m_bUserCancel = false;
	
	private OAuthConsumer m_consumer;
	OAuthProvider m_provider = new DefaultOAuthProvider(
			REQUEST_TOKEN_ENDPOINT_URL, ACCESS_TOKEN_ENDPOINT_URL,
			AUTHORIZE_WEBSITE_URL);

	
	public OAuthDialog(HOMainFrame mainFrame, OAuthConsumer consumer) {
		super(mainFrame, HOVerwaltung.instance().getLanguageString("Login"), true);

		this.m_clMainFrame = mainFrame;
		this.m_consumer = consumer;
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		obtainUserURL();
		initComponents();

	}

	
	private void obtainUserURL() {
		
       try {
    	   m_sUserURL = m_provider.retrieveRequestToken(m_consumer, OAuth.OUT_OF_BAND);
       } catch (Exception e){
			HOLogger.instance().debug(getClass(), "Exception in obtainUserCode");
			
			// XXX Do More?
		}
	}
	private void doAuthorize() {
		
		m_sUserCode = new String(m_jtfAuthString.getText().trim());
		
		try {
			m_provider.retrieveAccessToken(m_consumer, m_sUserCode);
		} catch (Exception e) {
			HOLogger.instance().debug(getClass(), "Exception in doAuthorize");
		}
		
		UserParameter.instance().AccessToken = MyHelper.cryptString(m_consumer.getToken());
		UserParameter.instance().TokenSecret = MyHelper.cryptString(m_consumer.getTokenSecret());
		
		this.dispose();
	}
	
	private void openUrlInBrowser() {
		boolean error = false;
		if( !java.awt.Desktop.isDesktopSupported() ) {
            HOLogger.instance().debug(getClass(), "Desktop not supported.");
            JOptionPane.showMessageDialog(null, "Open URL failed.", "Open URL", JOptionPane.ERROR_MESSAGE);
            return;
        }

        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

        if( !desktop.isSupported( java.awt.Desktop.Action.BROWSE ) ) {
        	HOLogger.instance().debug(getClass(), "Desktop not supported.");
        	JOptionPane.showMessageDialog(null, "Open URL failed.", "Open URL", JOptionPane.ERROR_MESSAGE);
        	return;
        }

        try {

        	java.net.URI uri = new java.net.URI( m_sUserURL );
        	desktop.browse( uri );
        }
        catch ( Exception e ) {
        	HOLogger.instance().debug(getClass(), "Open URL failed.");
        	JOptionPane.showMessageDialog(null, "Open URL failed.", "Open URL", JOptionPane.ERROR_MESSAGE);
        }
        
        // XXX Give user help?
	}
	
	public boolean getUserCancel() {
		return m_bUserCancel;
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

	private void initComponents() {
		JPanel panel;
		
		setContentPane(new ImagePanel());
		getContentPane().setLayout(null);
		
		
		// User Daten
		panel = new ImagePanel();
		panel.setLayout(new GridLayout(0,2));
		panel.setSize(355, 290);
		
		JLabel infoLabel = new JLabel();
		infoLabel.setText("Hi welcome, do your job, authorize, paste, be happy, whatever");
		panel.add(infoLabel);
		// Fill out the first line
		panel.add(new JPanel());
		
		JLabel authLink = new JLabel();
		authLink.setText("This is the url for authorization");
		panel.add(authLink);
		m_jtfAuthURL.setText(m_sUserURL);
		panel.add(m_jtfAuthURL);
		
		JLabel authInput = new JLabel();
		authInput.setText("Enter your authorization obtained from Hattrick here");
		panel.add(authInput);
		panel.add(m_jtfAuthString);
		
		
		
		getContentPane().add(panel);
		
		// Buttons
		m_jbOK.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Login_Anmelden"));
		m_jbOK.setText(HOVerwaltung.instance().getLanguageString("Anmelden"));
		m_jbOK.setSize(170, 35);
		m_jbOK.addActionListener(this);
		
		//m_jbOK.setEnabled(false);
		panel.add(m_jbOK);
		
		m_jbBrowse.setText("Open URL");
		m_jbBrowse.setSize(170,35);
		m_jbBrowse.addActionListener(this);
		m_jbBrowse.setEnabled(true);
		panel.add(m_jbBrowse);
		
		m_jbCancel.setText("Cancel");
		m_jbCancel.setSize(170,35);
		m_jbCancel.addActionListener(this);
		m_jbCancel.setEnabled(true);
		panel.add(m_jbCancel);
		
		final Dimension size = m_clMainFrame.getToolkit().getScreenSize();
		this.setSize(400, 500);
		
		if (size.width > this.getSize().width) { // open dialog in the middle of the screen
			this.setLocation((size.width / 2) - (this.getSize().width / 2), (size.height / 2) - (this.getSize().height / 2));
		}
		
		//setResizable(false);
		
		//loginWaitDialog = new LoginWaitDialog(this);
	}
}
