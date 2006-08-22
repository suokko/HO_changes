// %1287661405:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import de.hattrickorganizer.gui.templates.SpielerLabelEntry;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class SpielerCBItem implements plugins.ISpielerComboboxItem {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static javax.swing.JLabel m_jlLeer = new javax.swing.JLabel(" ");

    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public SpielerLabelEntry m_clEntry = new SpielerLabelEntry(null, null, 0f, true, true);
    private plugins.ISpieler m_clSpieler;
    private String m_sText;
    private float m_fPositionsBewertung;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielerCBItem object.
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     * @param poswert TODO Missing Constructuor Parameter Documentation
     * @param spieler TODO Missing Constructuor Parameter Documentation
     */
    public SpielerCBItem(String text, float poswert, plugins.ISpieler spieler) {
        m_sText = text;
        m_clSpieler = spieler;
        m_fPositionsBewertung = poswert;
    }

    //~ Methods ------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------
    //------------------------------------CellRenderer-------------------------------------------    
    public final java.awt.Component getListCellRendererComponent(javax.swing.JList jList,
                                                                 Object obj, int index,
                                                                 boolean isSelected,
                                                                 boolean cellHasFocus) {
        if (obj instanceof SpielerCBItem) {
            final plugins.ISpieler spieler = ((SpielerCBItem) obj).getSpieler();

            //Kann für Tempspieler < 0 sein && spieler.getSpielerID () > 0 )
            if (spieler != null) {
                //m_clEntry = new SpielerLabelEntry( spieler, model.HOVerwaltung.instance ().getModel().getAufstellung ().getPositionBySpielerId ( spieler.getSpielerID () ), ( (SpielerCBItem)obj ).getPositionsBewertung (), true, true );
                m_clEntry.updateComponent(spieler,
                                          de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                 .getModel()
                                                                                 .getAufstellung()
                                                                                 .getPositionBySpielerId(spieler
                                                                                                         .getSpielerID()),
                                          ((SpielerCBItem) obj).getPositionsBewertung());

                return m_clEntry.getComponent(isSelected);
            } else {
                m_jlLeer.setOpaque(true);

                if (isSelected) {
                    m_jlLeer.setBackground(de.hattrickorganizer.gui.model.SpielerTableRenderer.SELECTION_BG);
                } else {
                    m_jlLeer.setBackground(java.awt.Color.white);
                }

                return m_jlLeer;
            }
        } else {
            m_jlLeer.setOpaque(true);

            if (isSelected) {
                m_jlLeer.setBackground(de.hattrickorganizer.gui.model.SpielerTableRenderer.SELECTION_BG);
            } else {
                m_jlLeer.setBackground(java.awt.Color.white);
            }

            return m_jlLeer;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param m_sPositionsBewertung TODO Missing Method Parameter Documentation
     */
    public final void setPositionsBewertung(float m_sPositionsBewertung) {
        this.m_fPositionsBewertung = m_sPositionsBewertung;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final float getPositionsBewertung() {
        return m_fPositionsBewertung;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param spieler TODO Missing Method Parameter Documentation
     */
    public final void setSpieler(plugins.ISpieler spieler) {
        m_clSpieler = spieler;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final plugins.ISpieler getSpieler() {
        return m_clSpieler;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    public final void setText(String text) {
        m_sText = text;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getText() {
        return m_sText;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     * @param poswert TODO Missing Method Parameter Documentation
     * @param spieler TODO Missing Method Parameter Documentation
     */
    public final void setValues(String text, float poswert, plugins.ISpieler spieler) {
        m_sText = text;
        m_clSpieler = spieler;
        m_fPositionsBewertung = poswert;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int compareTo(Object obj) {
        if (obj instanceof SpielerCBItem) {
            final SpielerCBItem cbitem = (SpielerCBItem) obj;

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
        } else {
            return -1;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
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

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String toString() {
        return m_sText;
    }
}
