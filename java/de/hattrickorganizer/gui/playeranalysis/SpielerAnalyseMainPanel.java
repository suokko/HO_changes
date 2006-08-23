// %222653727:de.hattrickorganizer.gui.playeranalysis%
package de.hattrickorganizer.gui.playeranalysis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import de.hattrickorganizer.gui.templates.ImagePanel;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class SpielerAnalyseMainPanel extends ImagePanel implements ActionListener {
    //~ Instance fields ----------------------------------------------------------------------------

    private JButton m_jbDrehen;
    private JSplitPane m_jspSpielerAnalyseSplitPane;
    private SpielerAnalysePanel m_jpSpielerAnalysePanel1;
    private SpielerAnalysePanel m_jpSpielerAnalysePanel2;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielerAnalyseMainPanel object.
     */
    public SpielerAnalyseMainPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getDividerLocation() {
        return m_jspSpielerAnalyseSplitPane.getDividerLocation();
    }

    public void saveColumnOrder(){
    	m_jpSpielerAnalysePanel1.saveColumnOrder();
    	m_jpSpielerAnalysePanel2.saveColumnOrder();
    }
    /**
     * TODO Missing Method Documentation
     *
     * @param spielerid TODO Missing Method Parameter Documentation
     */
    public final void setSpieler4Bottom(int spielerid) {
        m_jpSpielerAnalysePanel2.setAktuelleSpieler(spielerid);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param spielerid TODO Missing Method Parameter Documentation
     */
    public final void setSpieler4Top(int spielerid) {
        m_jpSpielerAnalysePanel1.setAktuelleSpieler(spielerid);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param actionEvent TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (m_jspSpielerAnalyseSplitPane.getOrientation() == JSplitPane.VERTICAL_SPLIT) {
            m_jspSpielerAnalyseSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        } else {
            m_jspSpielerAnalyseSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        }

        gui.UserParameter.instance().spieleranalyseVertikal = !gui.UserParameter.instance().spieleranalyseVertikal;
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        final JPanel panel = new ImagePanel(new BorderLayout());

        m_jbDrehen = new JButton(new ImageIcon(de.hattrickorganizer.tools.Helper.loadImage("gui/bilder/drehen.png")));
        m_jbDrehen.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                         .getProperty("tt_SpielerAnalyse_drehen"));
        m_jbDrehen.setPreferredSize(new Dimension(24, 24));
        m_jbDrehen.addActionListener(this);
        panel.add(m_jbDrehen, BorderLayout.WEST);
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        add(panel, BorderLayout.NORTH);

        m_jpSpielerAnalysePanel1 = new SpielerAnalysePanel(1);
        m_jpSpielerAnalysePanel2 = new SpielerAnalysePanel(2);
        m_jspSpielerAnalyseSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                                      m_jpSpielerAnalysePanel1,
                                                      m_jpSpielerAnalysePanel2);
        m_jspSpielerAnalyseSplitPane.setDividerLocation(gui.UserParameter.instance().spielerAnalysePanel_horizontalSplitPane);
        add(m_jspSpielerAnalyseSplitPane, BorderLayout.CENTER);

        if (!gui.UserParameter.instance().spieleranalyseVertikal) {
            m_jspSpielerAnalyseSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        } else {
            m_jspSpielerAnalyseSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        }
    }
}
