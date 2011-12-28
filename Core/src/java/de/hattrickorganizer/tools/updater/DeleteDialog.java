// %2464279560:de.hattrickorganizer.tools.updater%
/*
 * Created on 06.04.2005
 *
 */
package de.hattrickorganizer.tools.updater;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import plugins.IPlugin;
import de.hattrickorganizer.gui.pluginWrapper.GUIPluginWrapper;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HelperWrapper;


/**
 * This is the Dialog for deleting plugins.
 *
 * @author Thorsten Dietz
 *
 * @since 1.35
 */
final class DeleteDialog extends UpdaterDialog {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 6602146630761684946L;
	private static String PROP_DELETE = HOVerwaltung.instance().getLanguageString("loeschen");

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new DeleteDialog object.
     */
    DeleteDialog() {
        super(null, PROP_DELETE);
        inizialize();

        Container contenPane = getContentPane();
        contenPane.add(createTable(), BorderLayout.CENTER);
        contenPane.add(createButtons(), BorderLayout.SOUTH);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param selected TODO Missing Method Parameter Documentation
     * @param columnNames2 TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	protected TableModel getModel(boolean selected, String[] columnNames2) {
        Object[][] value = new Object[object.length][4];
        boolean isEnabled = true;
        IPlugin tmp = null;

        for (int i = 0; i < object.length; i++) {
            tmp = ((IPlugin) object[i]);
            value[i][0] = getCheckbox(selected, isEnabled);
            value[i][1] = tmp.getName();
        }

        TableModel model = new TableModel(value, columnNames2);
        return model;
    }

    /**
     * TODO Missing Method Documentation
     */
    @Override
	protected void action() {
        boolean selected = false;

        if (table != null) {
            int i = 0;

            try {
                for (i = 0; i < table.getRowCount(); i++) {
                    selected = ((JCheckBox) table.getValueAt(i, 0)).isSelected();

                    if (selected) {
                        deletePlugin(object[i], true);
                    }
                     // selected
                }
                 // for

                GUIPluginWrapper.instance().getInfoPanel().clearLangInfo();
                JOptionPane.showMessageDialog(null, PROP_NEW_START, PROP_DELETE,
                                              JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                                              PROP_FILE_NOT_FOUND + ": "
                                              + ((HPPluginInfo) object[i]).getZipFileName(),
                                              PROP_DELETE, JOptionPane.ERROR_MESSAGE);
            }
        }

        this.dispose();
    }

    /**
     * TODO Missing Method Documentation
     */
    protected void inizialize() {
        object = HelperWrapper.instance().getPlugins().toArray();

        columnNames = new String[2];
        columnNames[0] = PROP_DELETE;
        columnNames[1] = PROP_NAME;
        okButtonLabel = PROP_DELETE;
    }
}
