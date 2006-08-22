// %1127326956556:plugins%
/*
 * IPlugin.java
 *
 * Created on 26. März 2004, 08:12
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
/**
 * This is the interface all hoplugins need to implement All implementations need a std construtor
 * without params!
 */
public interface IPlugin {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** PluginInterface Version used by this HO-Version */
    public static final double VERSION = 1.14d;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Name of Plugin
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getName();

    /**
     * method called from ho to start plugin
     *
     * @param model TODO Missing Constructuor Parameter Documentation
     */
    public void start(IHOMiniModel model);
}
