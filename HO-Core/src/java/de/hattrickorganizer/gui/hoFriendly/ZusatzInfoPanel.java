// %42684675:de.hattrickorganizer.gui.hoFriendly%
package de.hattrickorganizer.gui.hoFriendly;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;

import de.hattrickorganizer.gui.templates.RasenPanel;


/**
 * Zeigt den Spielstand an
 */
public class ZusatzInfoPanel extends RasenPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private ChatPanel m_jpChatPanel;
    private HOFriendlyDialog m_clHOFriendlyDialog;
    private TrainerPanel m_jpGastTrainer;
    private TrainerPanel m_jpHeimTrainer;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ZusatzInfoPanel object.
     *
     * @param hoFriendlyDialog TODO Missing Constructuor Parameter Documentation
     */
    public ZusatzInfoPanel(HOFriendlyDialog hoFriendlyDialog) {
        m_clHOFriendlyDialog = hoFriendlyDialog;

        TrainerLibrary.load();

        initComponents();

        setPreferredSize(new Dimension(300, 210));
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final TrainerPanel getGastTrainer() {
        return m_jpGastTrainer;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final TrainerPanel getHeimTrainer() {
        return m_jpHeimTrainer;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param trainer TODO Missing Method Parameter Documentation
     * @param message TODO Missing Method Parameter Documentation
     */
    public final void receiveMessage(String trainer, String message) {
        m_jpChatPanel.append(trainer, message);
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void stopAnimation() {
        m_jpHeimTrainer.stopAnimation();
        m_jpGastTrainer.stopAnimation();
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setBorder(BorderFactory.createLineBorder(Color.darkGray));
        setLayout(new BorderLayout());

        m_jpHeimTrainer = new TrainerPanel(true);
        add(m_jpHeimTrainer, BorderLayout.WEST);

        m_jpChatPanel = new ChatPanel(m_clHOFriendlyDialog);
        add(m_jpChatPanel, BorderLayout.CENTER);

        m_jpGastTrainer = new TrainerPanel(false);
        add(m_jpGastTrainer, BorderLayout.EAST);

        new Thread(m_jpHeimTrainer).start();
        new Thread(m_jpGastTrainer).start();
    }
}
