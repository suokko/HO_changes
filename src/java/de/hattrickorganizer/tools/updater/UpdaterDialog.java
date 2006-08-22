// %2840227020:de.hattrickorganizer.tools.updater%
/*
 * Created on 27.10.2004
 *
 */
package de.hattrickorganizer.tools.updater;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import plugins.IDebugWindow;
import plugins.IOfficialPlugin;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.pluginWrapper.GUIPluginWrapper;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.Helper;


/**
 * Abstract class with contains methods for all UpdaterDialogs
 *
 * @author Thorsten Dietz
 *
 * @since 1.35
 */
abstract class UpdaterDialog extends JDialog implements ActionListener {
    //~ Instance fields ----------------------------------------------------------------------------

    //	protected String PROP_NO_SERVER		= HOVerwaltung.instance().getResource().getProperty("KeinServer");

    /** TODO Missing Parameter Documentation */
    protected JTable table;

    /** TODO Missing Parameter Documentation */
    protected String ACT_SHOW_INFO = "ShowInfo";

    /** TODO Missing Parameter Documentation */
    protected String HOPLUGINS_DIRECTORY = System.getProperty("user.dir") + File.separator
                                           + "hoplugins";

    /** TODO Missing Parameter Documentation */
    protected String PROP_APPLY = HOVerwaltung.instance().getResource().getProperty("Uebernehmen");

    /** TODO Missing Parameter Documentation */
    protected String PROP_FILE_NOT_FOUND = HOVerwaltung.instance().getResource().getProperty("DateiNichtGefunden");

    /** TODO Missing Parameter Documentation */
    protected String PROP_HOMEPAGE = HOVerwaltung.instance().getResource().getProperty("Homepage");

    /** TODO Missing Parameter Documentation */
    protected String PROP_NAME = HOVerwaltung.instance().getResource().getProperty("Name");

    /** TODO Missing Parameter Documentation */
    protected String PROP_NEW_START = HOVerwaltung.instance().getResource().getProperty("NeustartErforderlich");

    /** TODO Missing Parameter Documentation */
    protected String okButtonLabel;

    /** TODO Missing Parameter Documentation */
    protected String[] columnNames;

    /** TODO Missing Parameter Documentation */
    protected Object[] object;

    /** TODO Missing Parameter Documentation */
    protected boolean defaultSelected;
    private String ACT_CANCEL = "CANCEL";
    private String ACT_FIND = "FIND";
    private String ACT_SET_ALL = "SET_ALL";
    private String ACT_SET_NONE = "SET_NONE";

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new UpdaterDialog object.
     *
     * @param data TODO Missing Constructuor Parameter Documentation
     * @param title TODO Missing Constructuor Parameter Documentation
     */
    protected UpdaterDialog(Object data, String title) {
        super(GUIPluginWrapper.instance().getOwner4Dialog(), title);

        int dialogWidth = 480;
        int dialogHeight = 320;

        int with = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()
                                            .getWidth();
        int height = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()
                                              .getHeight();
        setLocation((with - dialogWidth) / 2, (height - dialogHeight) / 2);
        setSize(dialogWidth, dialogHeight);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public void actionPerformed(ActionEvent e) {
        String comand = e.getActionCommand();
        UpdaterDialog dialog = (UpdaterDialog) ((JButton) e.getSource()).getTopLevelAncestor();

        if (comand.equals(ACT_CANCEL)) {
            dialog.dispose();
        }

        if (comand.equals(ACT_SET_NONE)) {
            dialog.setAll(false);
        }

        if (comand.equals(ACT_SET_ALL)) {
            dialog.setAll(true);
        }

        if (comand.equals(ACT_FIND)) {
            dialog.action();
        }

        if (comand.equals(ACT_SHOW_INFO)) {
            ((RefreshDialog) dialog).showInfo(((JButton) e.getSource()).getName());
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param selected TODO Missing Method Parameter Documentation
     * @param columnNames2 TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected abstract TableModel getModel(boolean selected, String[] columnNames2);

    /**
     * TODO Missing Method Documentation
     */
    protected abstract void action();

    /**
     * TODO Missing Method Documentation
     *
     * @param isSelected TODO Missing Method Parameter Documentation
     * @param isEnabled TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected JCheckBox getCheckbox(boolean isSelected, boolean isEnabled) {
        JCheckBox tmp = new JCheckBox();
        tmp.setEnabled(isEnabled);
        tmp.setBackground(Color.WHITE);
        tmp.setSelected(isSelected);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        return tmp;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param isEnabled TODO Missing Method Parameter Documentation
     * @param txt TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected JLabel getLabel(boolean isEnabled, String txt) {
        JLabel tmp = new JLabel(txt);
        tmp.setEnabled(isEnabled);
        tmp.setBackground(Color.lightGray);
        return tmp;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param file TODO Missing Method Parameter Documentation
     * @param files TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected boolean isUnquenchable(File file, File[] files) {
        if (files == null) {
            return false;
        }

        for (int i = 0; i < files.length; i++) {
            if (files[i].exists() && file.getName().equals(files[i].getName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param path TODO Missing Method Parameter Documentation
     * @param unquenchablesFiles TODO Missing Method Parameter Documentation
     */
    protected void clearDirectory(String path, File[] unquenchablesFiles) {
        File dir = new File(path);

        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();

            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    clearDirectory(files[i].getAbsolutePath(), unquenchablesFiles);
                }

                if (!isUnquenchable(files[i], unquenchablesFiles)) {
                    files[i].delete();
                }
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected JPanel createButtons() {
        JPanel buttonPanel = GUIPluginWrapper.instance().createImagePanel();
        ((FlowLayout) buttonPanel.getLayout()).setAlignment(FlowLayout.RIGHT);

        JButton okButton = new JButton(okButtonLabel);
        okButton.setActionCommand(ACT_FIND);
        okButton.addActionListener(this);

        JButton cancelButton = new JButton(HOVerwaltung.instance().getResource().getProperty("Abbrechen"));
        cancelButton.setActionCommand(ACT_CANCEL);
        cancelButton.addActionListener(this);

        JButton selectAllButton = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/CheckBoxSelected.gif")));
        selectAllButton.setBackground(Color.WHITE);
        selectAllButton.setPreferredSize(new Dimension(23, 23));
        selectAllButton.setActionCommand(ACT_SET_ALL);
        selectAllButton.addActionListener(this);

        JButton selectNoneButton = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/CheckBoxNotSelected.gif")));

        selectNoneButton.setBackground(Color.WHITE);
        selectNoneButton.setPreferredSize(new Dimension(23, 23));
        selectNoneButton.setActionCommand(ACT_SET_NONE);
        selectNoneButton.addActionListener(this);

        buttonPanel.add(selectAllButton);
        buttonPanel.add(selectNoneButton);
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        return buttonPanel;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected JScrollPane createTable() {
        table = new JTable(getModel(defaultSelected, columnNames));
        table.setDefaultRenderer(Object.class, new UpdaterCellRenderer());
        table.getTableHeader().setReorderingAllowed(false);

        if (table.getColumnCount() == 5) {
            table.getColumn(HOVerwaltung.instance().getResource().getProperty("Notizen"))
                 .setCellEditor(new TableEditor());
        }

        table.getColumn(columnNames[0]).setCellEditor(new TableEditor());

        JScrollPane scroll = new JScrollPane(table);
        return scroll;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param plugin TODO Missing Method Parameter Documentation
     * @param withTables TODO Missing Method Parameter Documentation
     */
    protected void deletePlugin(Object plugin, boolean withTables) {
        File[] unquenchableFiles = new File[0];
        String pluginName = plugin.getClass().getName();
        pluginName = pluginName.substring(pluginName.indexOf(".") + 1);

        // nur beim richtiges Löschen und nicht beim Update
        if (withTables) {
            deletePluginTables(pluginName);
        }

        File classFile = new File(HOPLUGINS_DIRECTORY + File.separator + pluginName + ".class");

        if (classFile.exists()) {
            classFile.delete();

            if (plugin instanceof IOfficialPlugin) {
                unquenchableFiles = ((IOfficialPlugin) plugin).getUnquenchableFiles();
            }

            clearDirectory(HOPLUGINS_DIRECTORY + File.separator + pluginName, unquenchableFiles);
            classFile = new File(HOPLUGINS_DIRECTORY + File.separator + pluginName);

            classFile.delete();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param pluginname TODO Missing Method Parameter Documentation
     */
    protected void deletePluginTables(String pluginname) {
        try {
            ArrayList droptables = new ArrayList();
            Object [] tables = DBZugriff.instance().getAdapter().getDBInfo().getAllTablesNames();
    
            for (int i = 0; i < tables.length; i++) {
				if(tables[i].toString().toUpperCase().startsWith(pluginname.toUpperCase())) {
                    droptables.add(tables[i].toString());
                }
			}
    
            for (int i = 0; i < droptables.size(); i++) {
                DBZugriff.instance().getAdapter().executeUpdate("DROP TABLE " + droptables.get(i));
            }
        } catch (Exception e) {
            handleException(e, "");
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     * @param txt TODO Missing Method Parameter Documentation
     */
    protected void handleException(Exception e, String txt) {
        //	    JOptionPane.showMessageDialog(null,	txt
        //				,RSC.NAME,JOptionPane.ERROR_MESSAGE);
        //		if(e!=null)
        //			writeErrorToLog(e);
        showException(e, txt);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param key TODO Missing Method Parameter Documentation
     */
    protected void show(String key) {
        JOptionPane.showMessageDialog(null, key);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param ex TODO Missing Method Parameter Documentation
     * @param itxt TODO Missing Method Parameter Documentation
     */
    protected void showException(Exception ex, String itxt) {
        IDebugWindow debugWindow = GUIPluginWrapper.instance().createDebugWindow(new Point(100, 200),
                                                                                 new Dimension(700,
                                                                                               400));
        debugWindow.setVisible(true);
        debugWindow.append(itxt);
        debugWindow.append(ex);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    private void setAll(boolean value) {
        for (int i = 0; i < table.getRowCount(); i++) {
            JCheckBox tmp = (JCheckBox) table.getValueAt(i, 0);

            if (tmp.isEnabled()) {
                tmp.setSelected(value);
            }
        }

        table.repaint();
    }
}
