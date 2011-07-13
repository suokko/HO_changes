// %2760322819:de.hattrickorganizer.gui.hoFriendly%
package de.hattrickorganizer.gui.hoFriendly;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.hattrickorganizer.gui.templates.RasenPanel;
import de.hattrickorganizer.gui.theme.ImageUtilities;
import de.hattrickorganizer.gui.theme.ThemeManager;


/**
 * Zeigt den Spielstand an
 */
public class ChatPanel extends RasenPanel implements ActionListener, KeyListener {

 	private static final long serialVersionUID = 1624019111726212359L;
	private ChatMessagePanel m_clChatMessagePanel = new ChatMessagePanel();
    private HOFriendlyDialog m_clChat;
    private JButton m_jbSenden = new JButton(new ImageIcon(ImageUtilities.makeColorTransparent(ThemeManager.loadImage("gui/bilder/senden.png"),
                                                                                 Color.red)));
    private JTextField m_jtfChatMessage = new JTextField();

 
    public ChatPanel(HOFriendlyDialog chat) {
        m_clChat = chat;

        setLayout(new BorderLayout());

        final JPanel panel = new RasenPanel();
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        panel.setLayout(layout);

        m_jtfChatMessage.setBackground(new Color(230, 240, 240));
        m_jtfChatMessage.setFont(m_jtfChatMessage.getFont().deriveFont(Font.BOLD, 12f));
        m_jtfChatMessage.addKeyListener(this);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 0;
        layout.setConstraints(m_jtfChatMessage, constraints);
        panel.add(m_jtfChatMessage);

        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        constraints.weighty = 1.0;
        constraints.gridx = 2;
        constraints.gridy = 0;
        m_jbSenden.addActionListener(this);
        layout.setConstraints(m_jbSenden, constraints);
        panel.add(m_jbSenden);

        add(panel, BorderLayout.NORTH);

        add(m_clChatMessagePanel, BorderLayout.CENTER);
    }

    public final void actionPerformed(ActionEvent e) {
        m_clChat.sendMsg(m_jtfChatMessage.getText());
        m_jtfChatMessage.setText("");
    }

    public final void append(String trainer, String message) {
        m_clChatMessagePanel.append(trainer, message);
    }

    public void keyPressed(KeyEvent e) {
    }

    public final void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            m_jbSenden.doClick();
        }
    }
 
    public void keyTyped(KeyEvent e) {
    }
}
