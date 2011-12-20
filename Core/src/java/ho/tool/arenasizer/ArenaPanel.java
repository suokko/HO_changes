// %3426612608:de.hattrickorganizer.gui.arenasizer%
package ho.tool.arenasizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;

import de.hattrickorganizer.gui.model.SpielerTableRenderer;
import de.hattrickorganizer.gui.model.VAPTableModel;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.DoppelLabelEntry;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.gui.templates.TableEntry;
import de.hattrickorganizer.model.HOModel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.Helper;


/**
 * Panel mit JTabel für die Arena anzeige und zum Testen
 */
final class ArenaPanel extends ImagePanel {

	private static final long serialVersionUID = -3049319214078126491L;

    //~ Instance fields ----------------------------------------------------------------------------

    private ArenaSizer m_clArenaSizer = new ArenaSizer();
    private JTable m_jtArena = new JTable();

    //Teststadium
    private Stadium m_clStadium;
    private String[] UEBERSCHRIFT = {"", "", "", "", ""};
    private Stadium[] m_clStadien;
    private TableEntry[][] tabellenwerte;

    //~ Constructors -------------------------------------------------------------------------------

    ArenaPanel() {

        initComponents();
        initTabelle();
        initStadium();
    }

    //-------Refresh---------------------------------    
    public void reInit() {
        initStadium();
    }

    private void initComponents() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(4, 4, 4, 4);

        setLayout(layout);

        final JPanel panel2 = new ImagePanel();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        layout.setConstraints(panel2, constraints);
        add(panel2);

        //Table
        final JPanel panel = new ImagePanel();
        panel.setLayout(new BorderLayout());

        m_jtArena.setDefaultRenderer(java.lang.Object.class, new SpielerTableRenderer());
        panel.add(m_jtArena);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.anchor = GridBagConstraints.NORTH;
        layout.setConstraints(panel, constraints);
        add(panel);
    }

    //Init aus dem HRF
    private void initStadium() {
        
    	HOModel model = HOVerwaltung.instance().getModel(); 
        m_clStadium = model.getStadium();
        m_clStadien = m_clArenaSizer.calcDifferentLevelArenas(m_clStadium,model.getVerein().getFans());

        //Entrys mit Werten füllen
        reinitTable();
    }

    private void initTabelle() {
        //Tablewerte setzen
        tabellenwerte = new TableEntry[10][5];
        HOVerwaltung hoV = HOVerwaltung.instance();
        //Spalten
        tabellenwerte[0][0] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE,
                                                  SwingConstants.CENTER);
        tabellenwerte[0][1] = new ColorLabelEntry(hoV.getLanguageString("Aktuell"),
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE,
                                                  SwingConstants.CENTER);
        tabellenwerte[0][2] = new ColorLabelEntry(hoV.getLanguageString("Maximal"),
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE,
                                                  SwingConstants.CENTER);
        tabellenwerte[0][3] = new ColorLabelEntry(hoV.getLanguageString("Durchschnitt"),
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE,
                                                  SwingConstants.CENTER);
        tabellenwerte[0][4] = new ColorLabelEntry(hoV.getLanguageString("Minimal"),
                                                  ColorLabelEntry.FG_STANDARD,
                                                  ColorLabelEntry.BG_SPIELERPOSITONSWERTE,
                                                  SwingConstants.CENTER);
 
        String[] columnText = {"Stehplaetze","Sitzplaetze","Ueberdachteplaetze","Logen","Gesamt","Einnahmen","Unterhalt","Gewinn","Baukosten"};
        for (int i = 0; i < columnText.length; i++) {
        	tabellenwerte[i+1][0] = new ColorLabelEntry(hoV.getLanguageString(columnText[i]),
                    ColorLabelEntry.FG_STANDARD,  ColorLabelEntry.BG_SPIELERPOSITONSWERTE, SwingConstants.LEFT);
		}
 

        //Platzwerte
        for (int i = 1; i < 10; i++) {
        	for (int j = 1; j < 5; j++) {
        		if(i<5)
        			tabellenwerte[i][j] = createDoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
        		else if(i == 5)
        			tabellenwerte[i][j] = createDoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
        		else if(i > 5)
        			 tabellenwerte[i][j] = createDoppelLabelEntry(ColorLabelEntry.BG_SPIELEREINZELWERTE);
			}
			
		}

        m_jtArena.setModel(new VAPTableModel(UEBERSCHRIFT, tabellenwerte));

        final TableColumnModel columnModel = m_jtArena.getColumnModel();
        columnModel.getColumn(0).setMinWidth(Helper.calcCellWidth(150));
        columnModel.getColumn(1).setMinWidth(Helper.calcCellWidth(160));
        columnModel.getColumn(2).setMinWidth(Helper.calcCellWidth(160));
        columnModel.getColumn(3).setMinWidth(Helper.calcCellWidth(160));
        columnModel.getColumn(4).setMinWidth(Helper.calcCellWidth(160));
    }

    /**
     * create a new DoppelLabelEntry with default values
     * @param background
     * @return
     */
    private DoppelLabelEntry createDoppelLabelEntry(Color background){
    	return new DoppelLabelEntry(new ColorLabelEntry("",
                						ColorLabelEntry.FG_STANDARD,
                						background, SwingConstants.RIGHT),
                	               new ColorLabelEntry("",
                	            		   ColorLabelEntry.FG_STANDARD,
                	            		   background, SwingConstants.RIGHT));
    }
    
    void reinitArena(Stadium currentArena, int maxSupporter, int normalSupporter, int minSupporter) {
        m_clStadium = currentArena;
        m_clStadien = m_clArenaSizer.calcConstructionArenas(currentArena, maxSupporter, normalSupporter, minSupporter);

        //Entrys mit Werten füllen
        reinitTable();
    }

    private void reinitTable() {
        final Stadium stadium = HOVerwaltung.instance().getModel().getStadium();
        if (m_clStadium != null) {
                ((DoppelLabelEntry) tabellenwerte[1][1]).getLinks().setText(m_clStadium.getStehplaetze() + "");
                ((DoppelLabelEntry) tabellenwerte[1][1]).getRechts().setSpezialNumber(m_clStadium.getStehplaetze() - stadium.getStehplaetze(),false);
                ((DoppelLabelEntry) tabellenwerte[2][1]).getLinks().setText(m_clStadium.getSitzplaetze() + "");
                ((DoppelLabelEntry) tabellenwerte[2][1]).getRechts().setSpezialNumber(m_clStadium.getSitzplaetze() - stadium.getSitzplaetze(),false);
                ((DoppelLabelEntry) tabellenwerte[3][1]).getLinks().setText(m_clStadium.getUeberdachteSitzplaetze()+ "");
                ((DoppelLabelEntry) tabellenwerte[3][1]).getRechts().setSpezialNumber(m_clStadium.getUeberdachteSitzplaetze() - stadium.getUeberdachteSitzplaetze(),false);
                ((DoppelLabelEntry) tabellenwerte[4][1]).getLinks().setText(m_clStadium.getLogen()+ "");
                ((DoppelLabelEntry) tabellenwerte[4][1]).getRechts().setSpezialNumber(m_clStadium.getLogen() - stadium.getLogen(), false);
                ((DoppelLabelEntry) tabellenwerte[5][1]).getLinks().setText(m_clStadium.getGesamtgroesse()+ "");
                ((DoppelLabelEntry) tabellenwerte[5][1]).getRechts().setSpezialNumber(m_clStadium.getGesamtgroesse() - stadium.getGesamtgroesse(), false);
                ((DoppelLabelEntry) tabellenwerte[6][1]).getLinks().setSpezialNumber(m_clArenaSizer.calcMaxIncome(m_clStadium),true);
                ((DoppelLabelEntry) tabellenwerte[6][1]).getRechts().setSpezialNumber(m_clArenaSizer.calcMaxIncome(m_clStadium) - m_clArenaSizer.calcMaxIncome(stadium),true);
                ((DoppelLabelEntry) tabellenwerte[7][1]).getLinks().setSpezialNumber(-m_clArenaSizer.calcMaintenance(m_clStadium), true);
                ((DoppelLabelEntry) tabellenwerte[7][1]).getRechts().setSpezialNumber(-(m_clArenaSizer.calcMaintenance(m_clStadium) - m_clArenaSizer.calcMaintenance(stadium)),true);
                ((DoppelLabelEntry) tabellenwerte[8][1]).getLinks().setSpezialNumber(m_clArenaSizer.calcMaxIncome(m_clStadium) - m_clArenaSizer.calcMaintenance(m_clStadium),true);
                ((DoppelLabelEntry) tabellenwerte[8][1]).getRechts().setSpezialNumber((m_clArenaSizer.calcMaxIncome(m_clStadium) - m_clArenaSizer.calcMaintenance(m_clStadium))- (m_clArenaSizer.calcMaxIncome(stadium)
                                                                                      - m_clArenaSizer.calcMaintenance(stadium)), true);
                ((DoppelLabelEntry) tabellenwerte[9][1]).getLinks().setSpezialNumber(-m_clArenaSizer.calcConstructionCosts(m_clStadium.getStehplaetze()- stadium.getStehplaetze(),
                                                                                                     m_clStadium.getSitzplaetze()- stadium.getSitzplaetze(),
                                                                                                     m_clStadium.getUeberdachteSitzplaetze()- stadium.getUeberdachteSitzplaetze(),
                                                                                                     m_clStadium.getLogen()- stadium.getLogen()),true);
                ((DoppelLabelEntry) tabellenwerte[9][1]).getRechts().setText("");

                for (int i = 2; i < 5; i++) {
                    ((DoppelLabelEntry) tabellenwerte[1][i]).getLinks().setText(m_clStadien[i - 2].getStehplaetze()+ "");
                    ((DoppelLabelEntry) tabellenwerte[1][i]).getRechts().setSpezialNumber(m_clStadien[i- 2].getStehplaetze()- m_clStadium.getStehplaetze(),false);
                    ((DoppelLabelEntry) tabellenwerte[2][i]).getLinks().setText(m_clStadien[i - 2].getSitzplaetze()+ "");
                    ((DoppelLabelEntry) tabellenwerte[2][i]).getRechts().setSpezialNumber(m_clStadien[i- 2].getSitzplaetze()- m_clStadium.getSitzplaetze(),false);
                    ((DoppelLabelEntry) tabellenwerte[3][i]).getLinks().setText(m_clStadien[i - 2].getUeberdachteSitzplaetze()+ "");
                    ((DoppelLabelEntry) tabellenwerte[3][i]).getRechts().setSpezialNumber(m_clStadien[i- 2].getUeberdachteSitzplaetze()- m_clStadium.getUeberdachteSitzplaetze(),false);
                    ((DoppelLabelEntry) tabellenwerte[4][i]).getLinks().setText(m_clStadien[i - 2].getLogen() + "");
                    ((DoppelLabelEntry) tabellenwerte[4][i]).getRechts().setSpezialNumber(m_clStadien[i- 2].getLogen()- m_clStadium.getLogen(),false);
                    ((DoppelLabelEntry) tabellenwerte[5][i]).getLinks().setText(m_clStadien[i - 2].getGesamtgroesse()+ "");
                    ((DoppelLabelEntry) tabellenwerte[5][i]).getRechts().setSpezialNumber(m_clStadien[i- 2].getGesamtgroesse()- m_clStadium.getGesamtgroesse(),false);
                    ((DoppelLabelEntry) tabellenwerte[6][i]).getLinks().setSpezialNumber(m_clArenaSizer.calcMaxIncome(m_clStadien[i- 2]),true);
                    ((DoppelLabelEntry) tabellenwerte[6][i]).getRechts().setSpezialNumber(m_clArenaSizer.calcMaxIncome(m_clStadien[i- 2])- m_clArenaSizer.calcMaxIncome(m_clStadium),true);
                    ((DoppelLabelEntry) tabellenwerte[7][i]).getLinks().setSpezialNumber(-m_clArenaSizer.calcMaintenance(m_clStadien[i- 2]),true);
                    ((DoppelLabelEntry) tabellenwerte[7][i]).getRechts().setSpezialNumber(-(m_clArenaSizer.calcMaintenance(m_clStadien[i- 2])
                                                                                          - m_clArenaSizer.calcMaintenance(m_clStadium)),true);
                    ((DoppelLabelEntry) tabellenwerte[8][i]).getLinks().setSpezialNumber(m_clArenaSizer.calcMaxIncome(m_clStadien[i- 2])- m_clArenaSizer.calcMaintenance(m_clStadien[i- 2]),true);
                    ((DoppelLabelEntry) tabellenwerte[8][i]).getRechts().setSpezialNumber((m_clArenaSizer.calcMaxIncome(m_clStadien[i- 2])- m_clArenaSizer.calcMaintenance(m_clStadien[i- 2]))
                                                                                          - (m_clArenaSizer.calcMaxIncome(m_clStadium)- m_clArenaSizer.calcMaintenance(m_clStadium)),true);
                    ((DoppelLabelEntry) tabellenwerte[9][i]).getLinks().setSpezialNumber(-m_clStadien[i- 2].getAusbauKosten(),true);
                }

                m_jtArena.setModel(new VAPTableModel(UEBERSCHRIFT, tabellenwerte));
                m_jtArena.getColumnModel().getColumn(0).setMinWidth(Helper.calcCellWidth(150));
                m_jtArena.getColumnModel().getColumn(1).setMinWidth(Helper.calcCellWidth(160));
                m_jtArena.getColumnModel().getColumn(2).setMinWidth(Helper.calcCellWidth(160));
                m_jtArena.getColumnModel().getColumn(3).setMinWidth(Helper.calcCellWidth(160));
                m_jtArena.getColumnModel().getColumn(4).setMinWidth(Helper.calcCellWidth(160));
        }
    }
}
