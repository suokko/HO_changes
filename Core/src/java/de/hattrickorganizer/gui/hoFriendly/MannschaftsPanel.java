// %3025410748:de.hattrickorganizer.gui.hoFriendly%
package de.hattrickorganizer.gui.hoFriendly;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import de.hattrickorganizer.gui.templates.ImagePanel;


/**
 * Zeigt eine Mannschaft an
 */
public class MannschaftsPanel extends ImagePanel {
    //~ Constructors -------------------------------------------------------------------------------

    //private ServerTeam          m_clTeam            =   null;

    /**
     * Creates a new MannschaftsPanel object.
     */
    public MannschaftsPanel() {
        initComponents();

        setPreferredSize(new Dimension(300, 300));
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        //setBorder( BorderFactory.createTitledBorder ( m_clTeam.getManagerName () ) );
        setLayout(new BorderLayout());

        final JPanel panel = new ImagePanel();
        panel.setLayout(new GridLayout(16, 3, 4, 4));

        /*
           m_clEntries = new TableEntry[16][3];
        
           Vector positionen = m_clTeam.getPositionen ();
           for ( int i = 0; i < 16; i++ )
           {
               SpielerPosition pos = (SpielerPosition)positionen.get ( i );
               if ( pos != null )
               {
                   m_clEntries[i][0] = new ColorLabelEntry( m_clTeam.getSpielerById ( pos.getSpielerId () ).getName (), tools.Helper.getImage4Position ( pos.getId () ), ColorLabelEntry.FG_STANDARD, ColorLabelEntry.BG_STANDARD, JLabel.LEFT );
                   m_clEntries[i][1] = new SpielerStatusLabelEntry()
               }
               else
               {
                   m
               }
           }
        
           JScrollPane pane = new JScrollPanel( panel );
           add( pane, BorderLayout.CENTER );
         */
    }

    //---------------------Hilfsmethoden----------------------
}
