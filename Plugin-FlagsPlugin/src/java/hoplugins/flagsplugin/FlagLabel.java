package hoplugins.flagsplugin;
/**
 * FlagLabel.java
 *
 * @author Daniel González Fisher
 */

import javax.swing.JLabel;
//import hoplugins.FlagsPlugin;

public class FlagLabel extends JLabel {
	private static final long serialVersionUID = -3019005598462570278L;
	private FlagObject flag;

    public FlagLabel(FlagObject fo) {
        super();
        flag = fo;
    }

    public FlagObject getFlagObject()  { return flag; }
    public String getFlagName()        { return flag.getName(); }
    public int getFlagId()             { return flag.getId(); }


}
