// %2679287538:hoplugins.teamAnalyzer.ui.component%
package hoplugins.teamAnalyzer.ui.component;

import hoplugins.Commons;

import hoplugins.commons.ui.InfoPanel;


/**
 * A joke panel :)
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class JokePanel extends InfoPanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 3188758998536694991L;
	private static String[] messages = new String[4];

    static {
        if (Commons.getModel().getHelper().getLanguageName().equalsIgnoreCase("Italiano")) {
            messages[0] = "Mi dispiace ma utilizzare il presente software ";
            messages[1] = "per analizzare la squadra del creatore è considerato ";
            messages[2] = "atto di pirateria e non può essere processato :) ";
            messages[3] = "eh eh eh eh";
        } else {
            messages[0] = "I am sorry but use this software ";
            messages[1] = "to analyze the creator's team has to be considered ";
            messages[2] = "act of piracy and therefore cannot be processed :) ";
            messages[3] = "eh eh eh eh";
        }
    }

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructs a new instance.
     */
    public JokePanel() {
        super(messages);
    }
}
