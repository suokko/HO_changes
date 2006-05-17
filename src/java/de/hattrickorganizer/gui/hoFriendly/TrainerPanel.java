// %1945446148:de.hattrickorganizer.gui.hoFriendly%
package de.hattrickorganizer.gui.hoFriendly;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import de.hattrickorganizer.gui.model.ImageSequenzItem;
import de.hattrickorganizer.gui.model.TrainerSequenz;
import de.hattrickorganizer.tools.HOLogger;


/**
 * Zeigt den Trainer an
 */
public class TrainerPanel extends JPanel implements Runnable {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
     * Der aktuelle Thread, damit er interupted werden kann, wenn ein andere Sequenz gespielt
     * werden soll
     */
    public Thread m_clWaitThread;

    /** TODO Missing Parameter Documentation */
    public boolean m_bShowAnimation = true;
    private ImageSequenzItem m_clAktuellesImageSequenzItem;

    //Aktuelle Sequenz
    private TrainerSequenz m_clAktuelleTrainerSequenz;

    //Wird immer gezeigt, wenn keine andere Sequenz aktiv ist
    private TrainerSequenz m_clDefaultTrainerSequenz;

    //Nächste Sequenz
    private TrainerSequenz m_clNaechsteTrainerSequenz;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TrainerPanel object.
     *
     * @param heim TODO Missing Constructuor Parameter Documentation
     */
    public TrainerPanel(boolean heim) {
        setPreferredSize(new Dimension(260, 210));
        setOpaque(true);

        //Default setzen
        if (heim) {
            m_clDefaultTrainerSequenz = TrainerLibrary.Trainer1_Langeweile.duplicate();
        } else {
            m_clDefaultTrainerSequenz = TrainerLibrary.Trainer2_Langeweile.duplicate();
        }

        this.setDoubleBuffered(false);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     */
    public final void paint(Graphics g) {
        render();
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void run() {
        m_clWaitThread = Thread.currentThread();

        while (m_bShowAnimation) {
            prepareRender();

            //Je nach Bildsequenz warte
            try {
                m_clWaitThread.sleep(m_clAktuellesImageSequenzItem.getWait());
            } catch (Exception e) {
                //Nix
            }
        }
    }

    /**
     * Neue Sequenz setzen
     *
     * @param sequenz TODO Missing Constructuor Parameter Documentation
     */
    public final void showSequence(TrainerSequenz sequenz) {
        //ändern der Sequenz
        m_clNaechsteTrainerSequenz = sequenz.duplicate();
        m_clNaechsteTrainerSequenz.resetCounters();

        m_clWaitThread.interrupt();
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void stopAnimation() {
        m_bShowAnimation = false;
    }

    /**
     * TODO Missing Method Documentation
     */
    private void prepareRender() {
        //Aktuelle vorhanden und noch keine NachfolgeSequenz vorhanden und noch Bilder in der Aktuelle Sequenz
        if ((m_clAktuelleTrainerSequenz != null)
            && (m_clNaechsteTrainerSequenz == null)
            && m_clAktuelleTrainerSequenz.hasMoreImageSequenzItems()) {
            m_clAktuellesImageSequenzItem = m_clAktuelleTrainerSequenz.getNextImageSequenzItem();
        }
        //Ein Nachfolger steht bereit
        else if (m_clNaechsteTrainerSequenz != null) {
            m_clAktuelleTrainerSequenz = m_clNaechsteTrainerSequenz;

            //##Geht das?
            m_clNaechsteTrainerSequenz = null;
            m_clAktuellesImageSequenzItem = m_clAktuelleTrainerSequenz.getNextImageSequenzItem();
        }
        //Keine Sequenz mehr vorhanden
        else {
            m_clDefaultTrainerSequenz.resetCounters();
            m_clAktuelleTrainerSequenz = m_clDefaultTrainerSequenz;
            m_clAktuellesImageSequenzItem = m_clAktuelleTrainerSequenz.getNextImageSequenzItem();
        }

        //Bild zeichnen
        render();

        //this.paintImmediately ( 0, 0, getWidth(), getHeight() );
    }

    //-----------Zeichnen    
    private void render() {
        if ((this.getGraphics() != null) && (m_clAktuellesImageSequenzItem != null)) {
            //HOLogger.instance().log(getClass(), "Render Trainer success" );
            final Graphics2D g2d = (Graphics2D) this.getGraphics();

            final java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(250, 200,
                                                                                        java.awt.image.BufferedImage.TYPE_INT_ARGB);

            final Graphics gImage = image.getGraphics();

            //TODO Hintergrund Image
            //Image ist auf jeden Fall schon vorhanden!
            gImage.drawImage(de.hattrickorganizer.gui.templates.RasenPanel.background, 5, -5, this);

            gImage.drawImage(m_clAktuellesImageSequenzItem.getImage(),
                             m_clAktuellesImageSequenzItem.getOffsetX(),
                             m_clAktuellesImageSequenzItem.getOffsetY(), this);

            g2d.drawImage(image, 5, 5, this);
        } else {
            HOLogger.instance().log(getClass(),"Render Trainer FAILED");

            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }

            ;

            //this.paintImmediately ( 0, 0, 250, 200 );
            render();
        }
    }
}
