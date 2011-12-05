// %1614414392:de.hattrickorganizer.gui.templates%
package de.hattrickorganizer.gui.templates;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.hattrickorganizer.gui.model.SpielerTableRenderer;


public class DoppelLabel extends JPanel {
	
	private static final long serialVersionUID = 4801107348466403035L;
	
	private JLabel m_jlLinks;
    private JLabel m_jlRechts;

    public DoppelLabel(Color color) {
        this();
        m_jlLinks = new JLabel();
        m_jlLinks.setBackground(color);
        m_jlRechts = new JLabel();
        m_jlRechts.setBackground(color);
    }

    public DoppelLabel(JLabel links, JLabel rechts) {
        this();
        m_jlLinks = links;
        m_jlRechts = rechts;
    }

    public DoppelLabel() {
        setLayout(new GridLayout(1, 2));
        setOpaque(true);
        setBackground(SpielerTableRenderer.SELECTION_BG);
    }


    public final void setLabels(JLabel links, JLabel rechts) {
        this.removeAll();

        m_jlLinks = links;
        m_jlRechts = rechts;
        add(links);
        add(rechts);
    }

    public final JLabel getLinks() {
        return m_jlLinks;
    }

    public final JLabel getRechts() {
        return m_jlRechts;
    }

    public final void clear() {
        m_jlLinks.setText("");
        m_jlLinks.setIcon(null);
        m_jlRechts.setText("");
        m_jlRechts.setIcon(null);
    }
}
