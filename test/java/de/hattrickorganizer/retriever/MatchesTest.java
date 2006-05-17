// %3176130593:hoplugins%
package de.hattrickorganizer.retriever;



import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import de.hattrickorganizer.core.HOSetup;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.logik.MatchPlayerRetriever;
import de.hattrickorganizer.model.MatchPosition;


/**
 * Plugin regarding transfer information.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public class MatchesTest extends HOSetup {
    //~ Static fields/initializers -----------------------------------------------------------------

	public MatchesTest() {
		super();
		Vector v = DBZugriff.instance().getSpieler4Matches(46934984);
		for (Iterator iter = v.iterator(); iter.hasNext();) {
			System.out.println(iter.next());			
		}
		
		v = DBZugriff.instance().getSpieler4Matches(38594587);
		for (Iterator iter = v.iterator(); iter.hasNext();) {
			System.out.println(iter.next());			
		}
		
	}

    /**
     * @see plugins.IPlugin#start(plugins.IHOMiniModel)
     */
    public static void main(String[] args) {
    	 new MatchesTest();    	
		
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
