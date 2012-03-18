// %1287661405:de.hattrickorganizer.gui.model%
package ho.core.gui.model;

import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.comp.entry.SpielerLabelEntry;
import ho.core.gui.comp.renderer.HODefaultTableCellRenderer;
import ho.core.model.HOVerwaltung;

import java.awt.Component;

import javax.swing.JList;


public class SpielerCBItem  implements Comparable<SpielerCBItem>{
    //~ Static fields/initializers -----------------------------------------------------------------

    public static javax.swing.JLabel m_jlLeer = new javax.swing.JLabel(" ");

    //~ Instance fields ----------------------------------------------------------------------------
    public SpielerLabelEntry m_clEntry = new SpielerLabelEntry(null, null, 0f, true, true);
    private plugins.ISpieler m_clSpieler;
    private String m_sText;
    private float m_fPositionsBewertung;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielerCBItem object.
     *
     */
    public SpielerCBItem(String text, float poswert, plugins.ISpieler spieler) {
        m_sText = text;
        m_clSpieler = spieler;
        m_fPositionsBewertung = poswert;
        m_clEntry = new SpielerLabelEntry(null, null, 0f, true, true);
    }
    
    public SpielerCBItem(String text, float poswert, plugins.ISpieler spieler, boolean useCustomText) {
        m_sText = text;
        m_clSpieler = spieler;
        m_fPositionsBewertung = poswert;
        if (useCustomText == true) {
            m_clEntry = new SpielerLabelEntry(null, null, 0f, true, true, true, text);
        } else {
        	m_clEntry = new SpielerLabelEntry(null, null, 0f, true, true);
        }
    }
    //~ Methods ------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------
    //------------------------------------CellRenderer-------------------------------------------    
    public final Component getListCellRendererComponent(JList jList, int index,boolean isSelected) {
            final plugins.ISpieler spieler = getSpieler();

            //Kann für Tempspieler < 0 sein && spieler.getSpielerID () > 0 )
            if (spieler != null) {
                m_clEntry.updateComponent(spieler,
                                          HOVerwaltung.instance().getModel().getAufstellung()
                                                                                 .getPositionBySpielerId(spieler
                                                                                 .getSpielerID()),
                                                                                 getPositionsBewertung(), m_sText);

                return m_clEntry.getComponent(isSelected);
            } else {
                m_jlLeer.setOpaque(true);
                m_jlLeer.setBackground(isSelected?HODefaultTableCellRenderer.SELECTION_BG:ColorLabelEntry.BG_STANDARD);
                return m_jlLeer;
            }
    }

    public final void setPositionsBewertung(float m_sPositionsBewertung) {
        this.m_fPositionsBewertung = m_sPositionsBewertung;
    }


    public final float getPositionsBewertung() {
        return m_fPositionsBewertung;
    }


    public final void setSpieler(plugins.ISpieler spieler) {
        m_clSpieler = spieler;
    }


    public final plugins.ISpieler getSpieler() {
        return m_clSpieler;
    }

    public final void setText(String text) {
        m_sText = text;
    }

    public final String getText() {
        return m_sText;
    }

    public final void setValues(String text, float poswert, plugins.ISpieler spieler) {
        m_sText = text;
        m_clSpieler = spieler;
        m_fPositionsBewertung = poswert;
    }

    public final int compareTo(SpielerCBItem obj) {
        
        final SpielerCBItem cbitem =  obj;

        if ((cbitem.getSpieler() != null) && (getSpieler() != null)) {
            if (getPositionsBewertung() > cbitem.getPositionsBewertung()) {
                return -1;
            } else if (getPositionsBewertung() < cbitem.getPositionsBewertung()) {
                return 1;
            } else {
                return getSpieler().getName().compareTo(cbitem.getSpieler().getName());
            }

            //return getSpieler().getName ().compareTo ( cbitem.getSpieler ().getName () );
        } else if (cbitem.getSpieler() == null) {
            return -1;
        } else {
            return 1;
        }
 
    }

    @Override
	public final boolean equals(Object obj) {
        if (obj instanceof SpielerCBItem) {
            final SpielerCBItem temp = (SpielerCBItem) obj;

            if ((this.getSpieler() != null) && (temp.getSpieler() != null)) {
                if (this.getSpieler().getSpielerID() == temp.getSpieler().getSpielerID()) {
                    return true;
                }
            } else if ((this.getSpieler() == null) && (temp.getSpieler() == null)) {
                return true;
            }
        }

        return false;
    }

    @Override
	public final String toString() {
        return m_sText;
    }
}
