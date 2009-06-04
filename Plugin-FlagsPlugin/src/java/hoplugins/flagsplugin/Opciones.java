package hoplugins.flagsplugin;
/**
 * Opciones.java
 *
 * @author Daniel González Fisher
 */

import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import hoplugins.FlagsPlugin;

public class Opciones implements ActionListener, Serializable {
    static final long serialVersionUID = 4166593044744396412L;

    public static final String OPT_DATA_DIR = "hoplugins/flagsplugin/";

    transient private JDialog jdOpciones;
    transient private JButton jbSave, jbCancel;
    transient private JCheckBox chkDoubleClick;
    transient private JCheckBox chkOwnFlag;
    transient private JCheckBox chkSortListByCoolness;
    transient private JSlider sldFlagsPerRow;

    protected int flagsPerRow;
    protected boolean doubleClick;
    protected boolean ownFlag;
    protected boolean sortListByCoolness;
    protected HashMap teamIdCountry;
    protected HashMap coolnessRanking;

    public Opciones() {
        flagsPerRow = FlagsPlugin.FLAGS_PER_ROW;
        doubleClick = FlagsPlugin.DOUBLECLICK_SELECTION;
        ownFlag = FlagsPlugin.OWN_FLAG;
        teamIdCountry = new HashMap();
        coolnessRanking = new HashMap();
    }

    public HashMap getTeamIdCountry() { return teamIdCountry; }
    public HashMap getCoolnessRanking() { return coolnessRanking; }
    public void setTeamIdCountry(HashMap hm) { teamIdCountry = hm; }
    public void setCoolnessRanking(HashMap hm) { coolnessRanking = hm; }

    public JDialog createDialogoOpciones() {
        //JDialog dialog = new JDialog(owner, FlagsPlugin.ISFAC.getString(IconStringFactory.S_MENU_OPTIONS), true);
        JDialog dialog = new JDialog(FlagsPlugin.HOM.getGUI().getOwner4Dialog(), FlagsPlugin.NAME + " " + FlagsPlugin.HOM.getResource().getProperty("Optionen","Options"), true);
        Container cp = dialog.getContentPane();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        cp.setLayout(gbl);

        jbSave = new JButton(FlagsPlugin.HOM.getResource().getProperty("Speichern","Save"));
        jbCancel = new JButton(FlagsPlugin.HOM.getResource().getProperty("Abbrechen","Cancel"));
        jbSave.addActionListener(this);
        jbCancel.addActionListener(this);

        chkDoubleClick = new JCheckBox("Double click selection", doubleClick);
        chkOwnFlag = new JCheckBox("Include own flag in collections", ownFlag);
        chkSortListByCoolness = new JCheckBox("Sort lists by Coolness Ranking", sortListByCoolness);
        sldFlagsPerRow = new JSlider(4, 10, flagsPerRow);
        sldFlagsPerRow.setMajorTickSpacing(1);
        //sldFlagsPerRow.setMinorTickSpacing(1);
        sldFlagsPerRow.setPaintTicks(true);
        sldFlagsPerRow.setPaintLabels(true);
        sldFlagsPerRow.setSnapToTicks(true);

        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        gc.weighty = 1.0;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.gridheight = 1;
        gc.insets = new Insets(2,2,2,2);
        gbl.setConstraints(chkDoubleClick,gc);
        cp.add(chkDoubleClick);

        gbl.setConstraints(chkOwnFlag,gc);
        cp.add(chkOwnFlag);

        gbl.setConstraints(chkSortListByCoolness,gc);
        cp.add(chkSortListByCoolness);

        gbl.setConstraints(sldFlagsPerRow,gc);
        cp.add(sldFlagsPerRow);

        gc.fill = GridBagConstraints.NONE;
        gc.gridwidth = GridBagConstraints.RELATIVE;
        gc.gridheight = GridBagConstraints.REMAINDER;
        gbl.setConstraints(jbSave,gc);
        cp.add(jbSave);

        gc.gridwidth = GridBagConstraints.REMAINDER;
        gbl.setConstraints(jbCancel,gc);
        cp.add(jbCancel);

        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        jdOpciones = dialog;
        return dialog;
    }

    public static Opciones load() {
        Opciones opc = null;
        String nombreArch = new String(Opciones.OPT_DATA_DIR + FlagsPlugin.FILENAME_CONFIG);
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            fis = new FileInputStream(nombreArch);
            ois = new ObjectInputStream(new BufferedInputStream(fis));
            opc = (Opciones)ois.readObject();
            ois.close();
            fis.close();
        } catch(FileNotFoundException e) {
            opc = new Opciones();
        } catch(IOException e) {
            opc = new Opciones();
        } catch(ClassNotFoundException e) {
            opc = new Opciones();
        } catch(Exception e) {
            opc = new Opciones();
        }
        opc.getValues();
        return opc;
    }
    public boolean save() {
        File dataDir = new File(Opciones.OPT_DATA_DIR);
        File arch = null;
        setValues();
        /* save it to disk */
        try {
            if (!dataDir.exists()) dataDir.mkdir();
            arch = new File(dataDir, FlagsPlugin.FILENAME_CONFIG);
        } catch(SecurityException e) { return false;
        } catch(NullPointerException e) { return false; }
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos = new FileOutputStream(arch);
            oos = new ObjectOutputStream(new BufferedOutputStream(fos));
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch(IOException e) { return false; }
        return true;
    }

    private void getValues() {
        FlagsPlugin.FLAGS_PER_ROW = flagsPerRow;
        FlagsPlugin.DOUBLECLICK_SELECTION = doubleClick;
        FlagsPlugin.OWN_FLAG = ownFlag;
        FlagsPlugin.SORT_LIST_BY_COOLNESS = sortListByCoolness;
        //teamIdCountry;
        //coolnessRanking;
    }
    private void setValues() {
        flagsPerRow = FlagsPlugin.FLAGS_PER_ROW;
        doubleClick = FlagsPlugin.DOUBLECLICK_SELECTION;
        ownFlag = FlagsPlugin.OWN_FLAG;
        sortListByCoolness = FlagsPlugin.SORT_LIST_BY_COOLNESS;
        //teamIdCountry;
        //coolnessRanking;
    }

    /* ***** Interface ActionListener ***** */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(jbSave)) {
            FlagsPlugin.FLAGS_PER_ROW = sldFlagsPerRow.getValue();
            FlagsPlugin.DOUBLECLICK_SELECTION = chkDoubleClick.isSelected();
            FlagsPlugin.OWN_FLAG = chkOwnFlag.isSelected();
            FlagsPlugin.SORT_LIST_BY_COOLNESS = chkSortListByCoolness.isSelected();
            if (FlagsPlugin.SORT_LIST_BY_COOLNESS != sortListByCoolness) FlagsPlugin.FLAGSPLUGIN.reSortJLists();
            save();
            jdOpciones.dispose();
            /* reload the plugin */
            FlagsPlugin.FLAGSPLUGIN.refreshGUI();
        }
        else if (e.getSource().equals(jbCancel)) {
            jdOpciones.dispose();
        }
    }
    /* ************************************ */
}
