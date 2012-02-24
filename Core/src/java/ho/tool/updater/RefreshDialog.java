// %2112127676:de.hattrickorganizer.tools.updater%
/*
 * Created on 06.04.2005
 *
 */
package ho.tool.updater;

import gui.HOIconName;
import ho.core.file.ZipHelper;
import ho.core.gui.HOMainFrame;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.plugins.GUIPluginWrapper;
import ho.core.plugins.PluginManager;
import ho.core.util.HelperWrapper;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import org.w3c.dom.Document;

import plugins.IOfficialPlugin;


/**
 * DOCUMENT ME!
 *
 * @author Thorsten Dietz
 */
final class RefreshDialog extends UpdaterDialog {
	private static final long serialVersionUID = 7661955953578080637L;
    protected Hashtable<String, String> infos = null;
    protected String PROP_ADD = HOVerwaltung.instance().getLanguageString("Hinzufuegen");
    protected String WEB_PLUGINS = UpdateController.PLUGINS_HOMEPAGE + "/downloads/";

    ArrayList<?> others = new ArrayList<Object>();
    private String ADDPLUGIN = HOVerwaltung.instance().getLanguageString("addPlugin");
    private String CORRUPTEDPLUGIN = HOVerwaltung.instance().getLanguageString("corruptedPlugin");
    private String INFO = HOVerwaltung.instance().getLanguageString("Info");
    private String INSTALLATIONCANCELLED = HOVerwaltung.instance().getLanguageString("installationCancelled");
    private String INSTALLATIONCANCELLEDTEXT = HOVerwaltung.instance().getLanguageString("installationCancelledText");
    private String PROP_NOTE = HOVerwaltung.instance().getLanguageString("Notizen");
    private String REQUIREDPLUGIN = HOVerwaltung.instance().getLanguageString("requiredPlugin");
    private String UNKNOWNPLUGIN = HOVerwaltung.instance().getLanguageString("unknownPlugin");
    private final static DecimalFormat versionFormat = new DecimalFormat("#0.0####");

    //~ Constructors -------------------------------------------------------------------------------

    protected RefreshDialog(Object data) {
        super(data, HOVerwaltung.instance().getLanguageString("Plugins"));
        object = ((ArrayList<?>) data).toArray();
        inizialize();

        Container contenPane = getContentPane();
        contenPane.add(createTable(), BorderLayout.CENTER);
        contenPane.add(createButtons(), BorderLayout.SOUTH);
    }

	protected ArrayList<XMLPLuginInfo> getAllPlugins() {
		ArrayList<XMLPLuginInfo> plugins = new ArrayList<XMLPLuginInfo>();
		File hopluginsDir = new File(PluginManager.HOPLUGINS_DIRECTORY);
		File[] filesAfter = hopluginsDir.listFiles();

		for (int i = 0; i < filesAfter.length; i++) {
			if (filesAfter[i].isDirectory()) {
				File[] files = filesAfter[i].listFiles();

				for (int j = 0; j < files.length; j++) {
					if (files[j].getName().equalsIgnoreCase("plugin.xml")) {
						try {
							final Document doc = UpdateHelper.instance().getDocument(files[j]);
							XMLPLuginInfo tmp = XMLPLuginInfo.getInstance(doc.getDocumentElement().getChildNodes(), files[j].getName());
							plugins.add(tmp);
							break;
						} catch (Exception e) {
						}
					}// if
				}// for
			}// if
		}// for
		return plugins;
	}

    @Override
	protected TableModel getModel(boolean selected, String[] columnNames2) {
		Object[][] value = new Object[object.length][5];
		boolean isEnabled = true;

		for (int i = 0; i < object.length; i++) {
			isEnabled = true;

			IOfficialPlugin tmp = ((HPPluginInfo) object[i]).getOfficialPlugin();
			String oldVersion = "-";

			if (tmp != null) {
				oldVersion = versionFormat.format(tmp.getVersion());

				if (!(((HPPluginInfo) object[i]).getVersion() > tmp.getVersion())) {
					isEnabled = false;
				}
			}

			// wenn pluginVersion höher dann rot
			if (HOMainFrame.VERSION < ((HPPluginInfo) object[i]).getHoversionAsDouble()) {
				value[i][3] = +((HPPluginInfo) object[i]).getVersion() + " (HO " + ((HPPluginInfo) object[i]).getHoversion() + " )";
				isEnabled = false;
			}

			boolean isInfo = (((HPPluginInfo) object[i]).getUpdateText().length() > 0);

			value[i][0] = getCheckbox(selected, isEnabled);
			value[i][1] = getLabel(isEnabled, ((HPPluginInfo) object[i]).getName());
			value[i][2] = getLabel(isEnabled, oldVersion);
			value[i][3] = getLabel(isEnabled, ((HPPluginInfo) object[i]).getVersion() + "");
			value[i][4] = getButton(isInfo, isEnabled, ((HPPluginInfo) object[i]));
		}

		TableModel model = new TableModel(value, columnNames2);
		return model;
	}

    /**
     * TODO Missing Method Documentation
     *
     * @param list TODO Missing Method Parameter Documentation
     */
    protected void setOtherPlugins(ArrayList<?> list) {
        others = list;
    }

    /**
     * TODO Missing Method Documentation
     */
    @Override
	protected void action() {
        if (table != null) {
            int i = 0;

			try {
				for (i = 0; i < table.getRowCount(); i++) {
					boolean selected = ((JCheckBox) table.getValueAt(i, 0)).isSelected();

					if (selected) {
						IOfficialPlugin officialPlugin = ((HPPluginInfo) object[i]).getOfficialPlugin();
						String zipname = ((HPPluginInfo) object[i]).getZipFileName();

						if (!UpdateHelper.instance().download(WEB_PLUGINS + zipname, UpdateController.getLocalZipFile())) {
							continue;
						}

						// Wenn schon installiert, dann lösche vorher
						if (officialPlugin != null) {
							PluginManager.deletePlugin(officialPlugin, false);
						}

						ZipHelper.unzip(UpdateController.getLocalZipFile(), new File(PluginManager.HOPLUGINS_DIRECTORY));
                    }// selected
                }// for

				checkPlugins(0);
				GUIPluginWrapper.instance().getInfoPanel().clearLangInfo();

				JOptionPane.showMessageDialog(null, PROP_NEW_START, "", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(null, PROP_FILE_NOT_FOUND + ": " + ((HPPluginInfo) object[i]).getZipFileName(), "",
						JOptionPane.ERROR_MESSAGE);
			}
		}

        this.dispose();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param count TODO Missing Method Parameter Documentation
     */
    protected void checkPlugins(int count) {
        boolean ok = false;

        if (count > 1) {
            show(INSTALLATIONCANCELLEDTEXT + "\n" + INSTALLATIONCANCELLED);
            return;
        }

        try {
            ArrayList<XMLPLuginInfo> plugins = getAllPlugins();

            for (int i = 0; i < plugins.size(); i++) {
                XMLPLuginInfo item = plugins.get(i);
                HPPluginInfo currentplugin = getHppPluginInfo(item.getPluginID());
                ArrayList<?> ids = item.getDependPluginIDs();

                for (int j = 0; j < ids.size(); j++) {
                    int tmpid = ((Integer) ids.get(j)).intValue();
                    HPPluginInfo pluginInfo = getHppPluginInfo(tmpid);
                    int result = checkDependencies(plugins, tmpid);

                    if (result == -1) {
						if (pluginInfo == null) {
							show(UNKNOWNPLUGIN.replaceAll("xxx", currentplugin.getName()) + "\n" + CORRUPTEDPLUGIN);
							continue;
						}

						String txt = REQUIREDPLUGIN.replaceAll("xxx", currentplugin.getName()).replaceAll("yyy", pluginInfo.getName());
						int choice = JOptionPane.showConfirmDialog(null, txt + "\n" + ADDPLUGIN.replaceAll("yyy", pluginInfo.getName()),
								INFO, JOptionPane.YES_NO_OPTION);

						if (choice == JOptionPane.NO_OPTION) {
							continue;
						}

						if (!UpdateHelper.instance().download(WEB_PLUGINS + pluginInfo.getZipFileName(), //
								UpdateController.getLocalZipFile())) {
							continue;
						}

						ZipHelper.unzip(UpdateController.getLocalZipFile(), new File(PluginManager.HOPLUGINS_DIRECTORY));
				
                        ok = true;
                    } else {
                        // überprüfen, ob die aktuelleste Version und wenn nicht
                        // installieren
                        Object[] runplugins = HelperWrapper.instance().getPlugins().toArray();

						for (int k = 0; k < runplugins.length; k++) {
							if (runplugins[k] instanceof IOfficialPlugin) {
								if (((IOfficialPlugin) runplugins[k]).getPluginID() == result) {
									if (((IOfficialPlugin) runplugins[k]).getVersion() < pluginInfo.getVersion()) {
										if (!UpdateHelper.instance().download(WEB_PLUGINS + pluginInfo.getZipFileName(),
												UpdateController.getLocalZipFile())) {
											continue;
										}

										PluginManager.deletePlugin(runplugins[k], false);
										ZipHelper.unzip(UpdateController.getLocalZipFile(), new File(PluginManager.HOPLUGINS_DIRECTORY));
									}
								}
                            }// instanceof
                        }// for k
                    }// else
                }// for j
            }

            if (ok) {
                checkPlugins(count + 1);
            }
        } catch (Exception e) {
            showException(e, "");
        }
    }

    /**
     * Initialize the columns.
     */
    protected void inizialize() {
        infos = new Hashtable<String, String>();

        columnNames = new String[5];
        columnNames[0] = PROP_ADD;
        columnNames[1] = PROP_NAME;
        columnNames[2] = "HO!";
        columnNames[3] = PROP_HOMEPAGE;
        columnNames[4] = PROP_NOTE;
        okButtonLabel = PROP_APPLY;
    }

    protected void showInfo(String key) {
        JOptionPane.showMessageDialog(null, infos.get(key));
    }

    /**
     * Get the info button for the plugin.
     */
	private JButton getButton(boolean isInfo, boolean isEnabled, HPPluginInfo pluggi) {
		JButton tmp = new JButton(ThemeManager.getIcon(isInfo?HOIconName.INFO:HOIconName.EMPTY));

		if (isInfo) {
			String name = pluggi.getName();
			tmp.setName(name);
			infos.put(name, pluggi.getUpdateText());
			tmp.addActionListener(this);
			tmp.setActionCommand(ACT_SHOW_INFO);
		} else {
			isEnabled = false;
		}
		
		tmp.setOpaque(false);
		tmp.setEnabled(isEnabled);
		return tmp;
	}

    /**
     * TODO Missing Method Documentation
     *
     * @param pluginID TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
	private HPPluginInfo getHppPluginInfo(int pluginID) {
		// gerade angezeigte Plugins
		for (int i = 0; i < object.length; i++) {
			if (((HPPluginInfo) object[i]).getPluginId() == pluginID) {
				return (HPPluginInfo) object[i];
			}
		}

		// die anderen nicht angezeigten Plugins
		for (int i = 0; i < others.size(); i++) {
			if (((HPPluginInfo) others.get(i)).getPluginId() == pluginID) {
				return (HPPluginInfo) others.get(i);
			}
		}
		return null;
	}

    /**
     * DOCUMENT ME!
     *
     * @param plugins
     * @param currentID
     *
     * @return TODO Missing Return Method Documentation
     */
	private int checkDependencies(ArrayList<XMLPLuginInfo> plugins, int currentID) {
		for (int j = 0; j < plugins.size(); j++) {
			XMLPLuginInfo tmp = plugins.get(j);

			if (tmp.getPluginID() == currentID) {
				return currentID;
			}
		}
		return -1;
	}
}
