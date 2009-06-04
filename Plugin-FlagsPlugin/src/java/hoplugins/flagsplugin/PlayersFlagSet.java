package hoplugins.flagsplugin;
/**
 * PlayersFlagSet.java
 *
 * @author Daniel González Fisher
 */

import hoplugins.FlagsPlugin;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.Border;

import plugins.IJDBCAdapter;
import plugins.ISpieler;

public class PlayersFlagSet extends JPanel implements MouseListener {
	private static final long serialVersionUID = 5412999071036972736L;
	//private JPanel grid;
    private TreeMap banderas;
    private String title;
    protected int type;

    //static protected JDialog JDPlayer = null;
    static protected int T_CURRENT = 1;
    static protected int T_OLD = 2;

    public PlayersFlagSet(Vector v) {
        this();
        setPlayers(v);
        type= T_CURRENT;
    }
    public PlayersFlagSet() {
        super();
        GridLayout grid = new GridLayout(0,FlagsPlugin.FLAGS_PER_ROW);
        grid.setHgap(3);
        grid.setVgap(6);
        setLayout(grid);
        banderas = new TreeMap();
        setOpaque(false);
        title = null;
    }

    public void refreshFlags() {
        intRefreshFlags();
        revalidate();
        repaint();
    }
    protected void intRefreshFlags() {
        removeAll();
        Iterator it = banderas.keySet().iterator();
        while (it.hasNext()) {
            String pais = (String)it.next();
            //JLabel flag = FlagCollection.createFlag(pais);
            FlagLabel flag = FlagCollection.createFlag(new FlagObject(FlagsPlugin.getCountryId(pais), pais));
            flag.addMouseListener(this);
            int np = ((Integer)banderas.get(pais)).intValue();
            flag.setToolTipText(pais + ", " + np + " player" + ((np>1)?"s":""));
            add(flag);
        }
    }

    public boolean setPlayers(Vector v) {
        if (v==null) return false;
        banderas.clear();
        for (int i=0; i<v.size(); i++) {
            try {
                ISpieler jugador = (ISpieler)v.get(i);
                addFlag(jugador.getNationalitaet());
            } catch (ClassCastException cce) {
            } catch (ArrayIndexOutOfBoundsException aie) {
            } catch (Exception e) { return false; }
        }
        refreshFlags();
        return true;
    }

    public void removeRepetidos(PlayersFlagSet pfs) {
        boolean redibujar = false;
        Iterator it = pfs.banderas.keySet().iterator();
        while (it.hasNext()) {
            String pais = (String)it.next();
            if (banderas.containsKey(pais)) {
                banderas.remove(pais);
                redibujar = true;
            }
        }
        if (redibujar) refreshFlags();
        type = T_OLD;
    }

    public void addFlag(int idx) {
        String pais = FlagsPlugin.getCountryName(idx);
        if (pais==null) return;
        Integer I = new Integer(0);
        if (banderas.containsKey(pais)) {
            I = (Integer)banderas.get(pais);
        }
        banderas.put(pais,new Integer(I.intValue()+1));
    }
    public boolean contains(String pais) {
        return banderas.containsKey(pais);
    }
    public void setTitle(String t) {
        title = t;
        refreshTitle();
    }
    public void refreshTitle() {
        if (title == null) return;
        Border btitulo = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),title);
        Border bcontador = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"(" + Integer.toString(banderas.size()) + " flags)");
        //setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),title + "\n(" + Integer.toString(banderas.size()) + " flags)"));
        setBorder(BorderFactory.createCompoundBorder(btitulo,bcontador));
    }

    protected JComponent createPlayerPanel(int country, int minWidth) {
        Box box = new Box(BoxLayout.Y_AXIS);
        if (type == T_OLD) {
            //res = db.executeQuery("SELECT name, MAX(datum) FROM spieler WHERE land='" + country + "' GROUP BY name HAVING MAX(datum)<'" + FlagsPlugin.HOM.getBasics().getDatum().toString() + "'");
            Vector vp = FlagsPlugin.HOM.getAllOldSpieler();
            Iterator it = vp.iterator();
            while (it.hasNext()) {
                ISpieler sp = (ISpieler)it.next();
                if (sp.getNationalitaet() == country) box.add(new JLabel(sp.getName()));
            }
        }
        else {
            IJDBCAdapter db = FlagsPlugin.HOM.getAdapter();
            ResultSet res = null;
            res = db.executeQuery("SELECT name FROM spieler WHERE land='" + country + "' AND datum='" + FlagsPlugin.HOM.getBasics().getDatum().toString() + "' GROUP BY name");
            try {
                while (res.next()) {
                    box.add(new JLabel(res.getString(1)));
                }
                res.close();
            } catch (java.sql.SQLException sqlex) {
                FlagsPlugin.HOM.getGUI().getInfoPanel().setLangInfoText("ERROR! " + sqlex.toString(), Color.RED);
            }
        }

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel jp = new JPanel(gbl);
        Component horStrut = Box.createHorizontalStrut(minWidth + 24);
        JLabel flagIcon = new JLabel(FlagsPlugin.HOM.getHelper().getImageIcon4Country(country));

        gbc.gridheight = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbl.setConstraints(horStrut, gbc);
        jp.add(horStrut);

        gbc.insets = new Insets(3,4,3,4);
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 1;
        gbl.setConstraints(flagIcon, gbc);
        jp.add(flagIcon);

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbl.setConstraints(box, gbc);
        jp.add(box);

        return jp;
    }

    /* *********************** */
    /* Interface MOUSELISTENER */
    public void mouseClicked(MouseEvent e) {
        //testDB();
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (e.getClickCount() > 1) return;
            String flagname = ((FlagLabel)e.getSource()).getFlagName();
            String title = "players from " + flagname;
            if (type == T_OLD) title = "Old " + title;
            else title = "Current " + title;
            int id = ((FlagLabel)e.getSource()).getFlagId();

            JDialog JDPlayer = new JDialog(FlagsPlugin.HOM.getGUI().getOwner4Dialog(), title, false);
            //JDialog.setDefaultLookAndFeelDecorated(true);
            JDPlayer.setUndecorated(true);
            JDPlayer.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
            JDPlayer.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            JDPlayer.getContentPane().setLayout(new FlowLayout());
            JComponent jcomp = createPlayerPanel(id, (int) (new JLabel(title)).getPreferredSize().getWidth() );
            JDPlayer.getContentPane().add(jcomp);
            JDPlayer.pack();
            JDPlayer.setLocationRelativeTo((FlagLabel)e.getSource());
            JDPlayer.setResizable(false);
            JDPlayer.setVisible(true);
            return;
        }
    }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }




//     protected void testDB() {
//         String fecha = FlagsPlugin.HOM.getBasics().getDatum().toString();
//         // SELECT name, MAX(datum) FROM spieler WHERE land='3' group by name having max(datum)<'2005-12-15 18:49:23.0'
//         //String inputValue = JOptionPane.showInputDialog("Please input a value", "SELECT name FROM spieler WHERE land='3' GROUP BY name");
//         String inputValue = JOptionPane.showInputDialog("Please input a value", "SELECT name FROM spieler WHERE land='3' AND datum='" + fecha + "' GROUP BY name");

//         JDPlayer = new JDialog(FlagsPlugin.HOM.getGUI().getOwner4Dialog(), "Players from X", false);
//         JDPlayer.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

//         Box box = new Box(BoxLayout.Y_AXIS);
//         IJDBCAdapter db = FlagsPlugin.HOM.getAdapter();
//         ResultSet res = db.executeQuery(inputValue);

//         try {
//             while (res.next()) {
//                 box.add(new JLabel(res.getString(1)));
//             }
//             res.close();
//         } catch (java.sql.SQLException sqlex) {
//             FlagsPlugin.HOM.getGUI().getInfoPanel().setLangInfoText("ERROR! " + sqlex.toString(), Color.RED);
//         }
//         JDPlayer.getContentPane().add(box);

//         JDPlayer.pack();
//         JDPlayer.setVisible(true);
//     }

}
