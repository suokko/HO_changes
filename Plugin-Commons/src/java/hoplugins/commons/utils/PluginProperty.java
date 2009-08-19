// %2950582597:hoplugins.commons.utils%
package hoplugins.commons.utils;

import hoplugins.Commons;

import java.io.File;
import java.io.IOException;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;


/**
 * Property Manager class for HO Plugins
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato </a>
 */
public final class PluginProperty {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static Properties properties;
    private static Properties languageCodes;

    /** TODO Missing Parameter Documentation */
    private static final String DEFAULT_LANGUAGE = "English";
    private static String language = DEFAULT_LANGUAGE;
    private static int languageID = 2; // 2 = English

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Private default constuctor to prevent class instantiation.
     */
    private PluginProperty() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Convert HT language ID to Java locale
     *
     * @return Locale
     */
    public static Locale getLocale() {
        if (languageCodes == null) {
            loadLanguageCodes();
        }

        languageID = Commons.getModel().getHelper().getLanguageID();

        String code = languageCodes.getProperty(Integer.toString(languageID));
        code.trim();

        Locale locale = null;

        if (code.indexOf('_') >= 0) {
            String[] parts = code.split("_");

            switch (parts.length) {
                case 2:
                    locale = new Locale(parts[0], parts[1]);
                    break;

                case 3:
                    locale = new Locale(parts[0], parts[1], parts[2]);
                    break;

                default:
                    locale = new Locale(parts[0]);
            }
        } else {
            locale = new Locale(code);
        }

        return locale;
    }

    /**
     * Returns the property value
     *
     * @param key property key
     *
     * @return the property value, or the key itself if not found
     */
    public static String getString(String key) {
        if (properties == null) {
            init();
        }

        // Get string from the properties.
        String value = properties.getProperty(key);

        // Not found? Try HO properties.
        if (value == null) {
            value = Commons.getModel().getLanguageString(key);

            // Property still not found?
            if (value == null) {
                value = '!' + key + '!';
            }

            // Cache the value.
            properties.setProperty(key, value);
        }

        return value;
    }

    /**
     * Loads the own property file of the given plugin.
     *
     * @param plugin The package name of the plugin.
     */
    public static void loadPluginProperties(String plugin) {
        if (properties == null) {
            init();
        }

        try {
            // Get resource bundle.
            ResourceBundle bundle = ResourceBundle.getBundle("hoplugins." + plugin + ".sprache.UI",
                                                             PluginProperty.getLocale());

            // Copy bundle contents to properties.
            for (Enumeration<String> e = bundle.getKeys(); e.hasMoreElements();) {
                String s = (String) e.nextElement();

                if (!properties.contains(s)) {
                    properties.setProperty(s, bundle.getString(s));
                }
            }
        } catch (Exception e) {
            // No bundle.
            // Try to read an old fashioned language file.
            
            File languagefile = new File("hoplugins/" + plugin + "/sprache/" + language
                                         + ".properties"); //$NON-NLS-1$

            try {
                if (languagefile.exists()) {
                    properties.load(new java.io.FileInputStream(languagefile));
                } else {
                    languagefile = new File("hoplugins/" + plugin + "/sprache/" + DEFAULT_LANGUAGE
                                            + ".properties"); //$NON-NLS-1$
                    properties.load(new java.io.FileInputStream(languagefile));
                }
            } catch (IOException ioe) {
            }
        }
    }

    /**
     * Initialize the class. Sets the current language and loads common property files of the
     * plugins.
     */
    private static void init() {
        // Set language.
        language = Commons.getModel().getHelper().getLanguageName();

        // Load properties.
        properties = new Properties();

        // Load common properties.
        loadPluginCommonProperties("shared");
        loadPluginCommonProperties("TeamAnalyzer");
        loadPluginCommonProperties("TrainingExperience");
        loadPluginCommonProperties("Transfers");
        loadPluginCommonProperties("TeamPlanner");
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void loadLanguageCodes() {
        languageCodes = new Properties();

        File file = new File("hoplugins/commons/sprache/LanguageCodes.properties"); //$NON-NLS-1$

        try {
            languageCodes.load(new java.io.FileInputStream(file));
        } catch (IOException e) {
            // Ignore.
        }
    }

    /**
     * Loads the properties for a specified plugin
     *
     * @param plugin Plugin name
     */
    private static void loadPluginCommonProperties(String plugin) {
        File languagefile = new File("hoplugins/commons/sprache/" + plugin + "/" + language
                                     + ".properties"); //$NON-NLS-1$

        try {
            if (languagefile.exists()) {
                properties.load(new java.io.FileInputStream(languagefile));
            } else {
                languagefile = new File("hoplugins/commons/sprache/" + plugin + "/"
                                        + DEFAULT_LANGUAGE + ".properties"); //$NON-NLS-1$
                properties.load(new java.io.FileInputStream(languagefile));
            }
        } catch (IOException e) {
            properties = new Properties();
        }
    }
}
