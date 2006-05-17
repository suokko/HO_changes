// %3176130593:hoplugins%
package de.hattrickorganizer.retriever;



import java.util.Iterator;
import java.util.List;

import de.hattrickorganizer.core.HOSetup;
import de.hattrickorganizer.logik.MatchPlayerRetriever;
import de.hattrickorganizer.model.MatchPosition;


/**
 * Plugin regarding transfer information.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public class MatchRetriever extends HOSetup {
    //~ Static fields/initializers -----------------------------------------------------------------

	public MatchRetriever() {
		super();
		testMatch(56186292);
		//testMatch(50197446);
	}

    /**
     * @see plugins.IPlugin#start(plugins.IHOMiniModel)
     */
    public static void main(String[] args) {
    	 new MatchRetriever();    	
		
    }

	private void testMatch(int id) {
		System.out.println(MODEL);
		MatchPlayerRetriever mpp = new MatchPlayerRetriever(MODEL);
		List l = mpp.getMatchData(id);
		
		for (Iterator iter = l.iterator(); iter.hasNext();) {
		    MatchPosition element = (MatchPosition) iter.next();
		    System.out.println(element.getName() + " " + element.getPosition());
		}		       
		MatchUpdaterFake.updateMatch(MODEL,id);
	}
}
