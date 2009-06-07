package hoplugins;

import hoplugins.specialEvents.FilterPanel;
import hoplugins.specialEvents.SpecialEventsPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import plugins.IDebugWindow;
import plugins.IHOMiniModel;
import plugins.IOfficialPlugin;
import plugins.IPlugin;
import plugins.IRefreshable;

public class SpecialEvents
    implements IPlugin, IRefreshable, IOfficialPlugin
{

    private final String PLUGIN_NAME = "Special Events";
    private final double PLUGIN_VERSION = 1.22D;
    private final int PLUGIN_ID = 33;
    private JPanel mainPanel;
    public static IHOMiniModel miniModel = null;
    public static Properties props;
    private static SpecialEventsPanel specialEventsTable;

    public SpecialEvents()
    {
        mainPanel = null;
    }

    public void start(IHOMiniModel hOMiniModel)
    {
        mpInit(hOMiniModel);
    }

    private void mpInit(IHOMiniModel hOMiniModel)
    {
        String sTabName = "Special Events";
        miniModel = hOMiniModel;
        mainPanel = hOMiniModel.getGUI().createImagePanel();
        mainPanel.setLayout(new BorderLayout());
        props = getProperties();
        JPanel filterPanel = new FilterPanel(miniModel, props);
        specialEventsTable = new SpecialEventsPanel(miniModel, props);
        specialEventsTable.setTableModel(specialEventsTable.getSEModel(miniModel, props));
        JScrollPane matchArea = new JScrollPane(specialEventsTable);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, filterPanel, matchArea);
        splitPane.setDividerLocation(60);
        splitPane.setDividerSize(5);
        splitPane.setContinuousLayout(true);
        mainPanel.add(splitPane);
        miniModel.getGUI().addTab(sTabName, mainPanel);
        miniModel.getGUI().registerRefreshable(this);
    }

    private Properties getProperties()
    {
        Properties props = null;
        try
        {
            File languagefile = new File("hoplugins/specialEvents/sprache/" + miniModel.getHelper().getLanguageName() + ".properties");
            if(languagefile.exists())
            {
                props = new Properties();
                props.load(new FileInputStream(languagefile));
            } else
            {
                languagefile = new File("hoplugins/specialEvents/sprache/English.properties");
                props = new Properties();
                props.load(new FileInputStream(languagefile));
            }
        }
        catch(Exception ex)
        {
            IDebugWindow debugWindow = miniModel.getGUI().createDebugWindow(new Point(100, 200), new Dimension(700, 400));
            debugWindow.setVisible(true);
            debugWindow.append(ex);
        }
        return props;
    }

    public void refresh()
    {
        newTableModel();
    }

    public static void newTableModel()
    {
        specialEventsTable.removeAll();
        specialEventsTable.setTableModel(specialEventsTable.getSEModel(miniModel, props));
    }

    public double getVersion()
    {
        return PLUGIN_VERSION;
    }

    public int getPluginID()
    {
        return PLUGIN_ID;
    }

    public String getPluginName()
    {
        return PLUGIN_NAME;
    }

    public String getName()
    {
        return getPluginName() + " " + getVersion();
    }

    public File[] getUnquenchableFiles()
    {
        return new File[0];
    }

}
