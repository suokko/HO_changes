package hoplugins.flagsplugin;
/**
 * FlagCollection.java
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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import plugins.IMatchKurzInfo;
import plugins.ISpielePanel;

public class FlagCollection extends JPanel implements MouseListener {
	private static final long serialVersionUID = 5517146402168122463L;
	private JList lista;
//	private JPanel grid;
    private TreeSet banderas;
    private HashMap teams;
    private String fileName;
    private int type;
    public static final int T_AWAY = 1;
    public static final int T_HOME = 2;
    private FlagsPlugin fp = null;

    public FlagCollection(FlagsPlugin fp, String fn, HashMap hm, int t) {
        super(new GridLayout(0,FlagsPlugin.FLAGS_PER_ROW,3,6));
        this.fp = fp;
        banderas = new TreeSet();
        setOpaque(false);
        fileName = fn;
        teams = hm;
        type = t;
    }

    /**
     * Get the cumulated coolness score of some flags
     * @param flags TreeSet with FlagObjects
     * @return cumulated coolness score of those flags
     */
    private double getCoolnessScore(TreeSet flags) {
    	double ret = 0;
    	if (flags != null) {
    		for (Iterator i=flags.iterator(); i.hasNext(); ) {
    			FlagObject fo = (FlagObject)i.next();
    			if (fo != null) {
    				ret += fo.getCoolness();
    			}
    		}
    	}
    	return ret;
    }

    public void setLista(JList l) {
        lista = l;
    }

    protected FlagLabel createMyFlag(FlagObject fo) {
        FlagLabel jl = createFlag(fo);
        jl.addMouseListener(this);
        return jl;
    }
    public static FlagLabel createFlag(FlagObject flag) {
        FlagLabel jl = new FlagLabel(flag);
        if (FlagsPlugin.HOM!=null) {
            try {
                //jl = new JLabel(new ImageIcon(FlagsPlugin.HOM.getHelper().loadImage("gui/bilder/flaggen/" + idx + "flag.png")));
                jl.setIcon(FlagsPlugin.HOM.getHelper().getImageIcon4Country(flag.getId()));
            } catch(Exception npe) {
                jl.setIcon(new ImageIcon(FlagsPlugin.HOM.getHelper().loadImage("flags/Unknownflag.png")));
            }
        }
        else jl.setIcon(new ImageIcon("flags/" + flag.getId() + "flag.png"));

        jl.setToolTipText(flag.getName()+" (Coolness rating " + FlagRenderer.numberFormat.format(flag.getCoolness()) + ")");
        //if (pais.equals("\u65e5\u672c")) jl.setToolTipText("Nippon ");   // NIHONGO
        if (flag.getId() == FlagsPlugin.HOM.getBasics().getLand()) jl.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLineBorder(Color.BLUE,1)));
        else jl.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLineBorder(Color.BLACK,1)));
        return jl;
    }
    public void refreshFlags() {
        intRefreshFlags();
        refreshTitle();
        revalidate();
        repaint();
    }
    protected void intRefreshFlags() {
        removeAll();
        Iterator it = banderas.iterator();
        while (it.hasNext()) {
            try {
                add(createMyFlag((FlagObject)it.next()));
            } catch (ClassCastException cce) { it.remove(); }
        }
    }
    public void refreshTitle() {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"(" + Integer.toString(banderas.size()) + " flags)"));
        if (fp != null) fp.setCoolnessSum(type, getCoolnessScore(banderas));
    }

    public boolean addFlag(FlagObject fo) {
        boolean added = banderas.add(fo);
        if (added) {
            refreshFlags();
        }
        return added;
    }
    public boolean removeFlag(FlagObject fo) {
        boolean res = banderas.remove(fo);
        if (res) refreshTitle();
        return res;
    }

    public boolean saveFlags() {
        String nombreArch = new String(fileName);
        File dataDir = new File(FlagsPlugin.DATA_DIR);
        File arch = null;
        /* save it to disk */
        try {
            if (!dataDir.exists()) dataDir.mkdir();
            arch = new File(dataDir, nombreArch);
        } catch(SecurityException e) { return false;
        } catch(NullPointerException e) { return false; }
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos = new FileOutputStream(arch);
            oos = new ObjectOutputStream(new BufferedOutputStream(fos));
            oos.writeObject(banderas);
            oos.close();
            fos.close();
        } catch(IOException e) { return false; }
        return true;
    }
    public boolean loadFlags() {
        String nombreArch = FlagsPlugin.DATA_DIR + fileName;
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            fis = new FileInputStream(nombreArch);
            ois = new ObjectInputStream(new BufferedInputStream(fis));
            banderas = (TreeSet)ois.readObject();
            ois.close();
            fis.close();
            intRefreshFlags();
            return true;
        } catch(FileNotFoundException e) {
            banderas = new TreeSet();
        } catch(IOException e) {
            banderas = new TreeSet();
        } catch(ClassNotFoundException e) {
            banderas = new TreeSet();
        }
        return false;
    }

    public boolean contains(Object pais) {
        return banderas.contains(pais);
    }
    public boolean addAll(Collection c) {
        boolean added = banderas.addAll(c);
        if (added) {
            refreshFlags();
        }
        return added;
    }
    public boolean removeAll(Collection c) {
        boolean removed = false;
        Iterator it = c.iterator();
        while (it.hasNext()) {
            removed = banderas.remove(it.next()) || removed;
        }
        if (removed) refreshFlags();
        return removed;
    }

    public Collection getMissing(Collection c) {
        Iterator it = c.iterator();
        ArrayList missing = new ArrayList();
        while (it.hasNext()) {
            Object flag = it.next();
            if (!banderas.contains(flag)) missing.add(flag);
        }
        return missing;
    }
    public Collection getSurplus(Collection c) {
        Iterator it = banderas.iterator();
        ArrayList surplus = new ArrayList();
        while (it.hasNext()) {
            Object flag = it.next();
            if (!c.contains(flag)) surplus.add(flag);
        }
        return surplus;
    }

    protected JComponent createTripsPanel(int country, int minWidth) {
        boolean own_country = (country == FlagsPlugin.HOM.getBasics().getLand());
        int myTeamId = FlagsPlugin.HOM.getBasics().getTeamId();
        IMatchKurzInfo [] partidos = FlagsPlugin.HOM.getMatchesKurzInfo(myTeamId, ISpielePanel.NUR_EIGENE_FREUNDSCHAFTSSPIELE, true);
        Box box = new Box(BoxLayout.Y_AXIS);
        for (int i=0; i<partidos.length; i++) {
            if (partidos[i].getMatchStatus() != IMatchKurzInfo.FINISHED) continue;
            int home = partidos[i].getHeimID();
            int visitor = partidos[i].getGastID();
            boolean national = (partidos[i].getMatchTyp() == 4) || (partidos[i].getMatchTyp() == 5);
            if (national && !own_country) continue;
            if (type == T_HOME) {  // hosted countries
                if (home != myTeamId) continue;
                if (!national) {
                    if (!teams.containsKey(new Integer(visitor))) continue;
                    if ( ((Integer) teams.get(new Integer(visitor)) ).intValue() != country) continue;
                }
                //JPanel tripInfo = new JPanel(new FlowLayout(FlowLayout.LEADING));
                Box tripInfo = new Box(BoxLayout.X_AXIS);
                tripInfo.add(createDateLabel(partidos[i].getMatchDateAsTimestamp()));
                tripInfo.add(Box.createHorizontalStrut(8));
                tripInfo.add(new JLabel( partidos[i].getGastName() ));
                tripInfo.add(Box.createHorizontalStrut(10));
                tripInfo.add(Box.createHorizontalGlue());
                tripInfo.add(createScoreLabel(partidos[i].getHeimTore(), partidos[i].getGastTore()));
                box.add(tripInfo);
            }
            else {  // default: visited countries
                if (visitor != myTeamId) continue;
                if (!national) {
                    if (!teams.containsKey(new Integer(home))) continue;
                    if ( ((Integer) teams.get(new Integer(home)) ).intValue() != country) continue;
                }
                //JPanel tripInfo = new JPanel(new FlowLayout(FlowLayout.LEADING));
                Box tripInfo = new Box(BoxLayout.X_AXIS);
                tripInfo.add(createDateLabel(partidos[i].getMatchDateAsTimestamp()));
                tripInfo.add(Box.createHorizontalStrut(8));
                tripInfo.add(new JLabel( partidos[i].getHeimName() ));
                tripInfo.add(Box.createHorizontalStrut(10));
                tripInfo.add(Box.createHorizontalGlue());
                tripInfo.add(createScoreLabel(partidos[i].getGastTore(), partidos[i].getHeimTore()));
                box.add(tripInfo);
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

    protected JComponent createDateLabel(Date timestamp) {
        String date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(timestamp);
        JLabel label = new JLabel(date);
        label.setForeground(Color.DARK_GRAY);
        return label;
    }

    protected JComponent createScoreLabel(int myGoals, int oppoGoals) {
        String score = Integer.toString(myGoals) + " - " + Integer.toString(oppoGoals);
        JLabel label = new JLabel(score);
        if (myGoals > oppoGoals) label.setForeground(Color.BLUE);
        else if (myGoals < oppoGoals) label.setForeground(Color.RED);
        return label;
    }

    /* *********************** */
    /* Interface MOUSELISTENER */
    public void mouseClicked(MouseEvent e) {
        if (lista==null) return;

        if (e.getButton() == MouseEvent.BUTTON3) {
            if (e.getClickCount() > 1) return;
            String flagname = ((FlagLabel)e.getSource()).getFlagName();
            String title = "Trips to " + flagname;
            if (type == T_HOME) title = "Visits from " + flagname;
            int id = ((FlagLabel)e.getSource()).getFlagId();

            JDialog JDPlayer = new JDialog(FlagsPlugin.HOM.getGUI().getOwner4Dialog(), title, false);
            //JDialog.setDefaultLookAndFeelDecorated(true);
            JDPlayer.setUndecorated(true);
            JDPlayer.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
            JDPlayer.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            JDPlayer.getContentPane().setLayout(new FlowLayout());
            JComponent jcomp = createTripsPanel(id, (int) (new JLabel(title)).getPreferredSize().getWidth() );
            JDPlayer.getContentPane().add(jcomp);
            JDPlayer.pack();
            JDPlayer.setLocationRelativeTo((FlagLabel)e.getSource());
            JDPlayer.setResizable(false);
            JDPlayer.setVisible(true);
            return;
        }
        else if (e.getButton() == MouseEvent.BUTTON1) {
            if ((FlagsPlugin.DOUBLECLICK_SELECTION == true) && (e.getClickCount() != 2)) return;

            // Add flag
            if (e.getSource() == lista) {
                FlagObject fo = (FlagObject)lista.getSelectedValue();
                addFlag(fo);
                lista.repaint();
            }
            // Remove flag
            else if (e.getSource().getClass().getName().equals("hoplugins.flagsplugin.FlagLabel")) {
                FlagObject fobj = ((FlagLabel)e.getSource()).getFlagObject();
                if (removeFlag(fobj)) {
                    remove((FlagLabel)e.getSource());
                    revalidate();
                    repaint();
                    lista.repaint();
                }
            }
        }
    }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }

}
