// %2980931775:de.hattrickorganizer.gui.skillupdate%
package de.hattrickorganizer.gui.skillupdate;

import de.hattrickorganizer.gui.templates.ImagePanel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * Panel mit JTabel für die Arena anzeige und zum Testen
 */
public class SkillAenderungsPanel extends de.hattrickorganizer.gui.templates.ImagePanel
    implements ActionListener
{
	private static final long serialVersionUID = -2695740054393344382L;
	
    //~ Instance fields ----------------------------------------------------------------------------

    private JButton m_bBerechnen = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Tabelle_berechnen"));
    private SkillAenderungsTable m_jtTable;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SkillAenderungsPanel object.
     */
    public SkillAenderungsPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param actionEvent TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        //Tabelle aktualisieren
        m_jtTable.init();
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        m_jtTable = new SkillAenderungsTable();
        add(new JScrollPane(m_jtTable), BorderLayout.CENTER);

        final JPanel panel = new ImagePanel();
        m_bBerechnen.addActionListener(this);
        panel.add(m_bBerechnen);
        add(panel, BorderLayout.NORTH);
    }
}
