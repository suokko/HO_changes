// %3943495873:hoplugins.teamAnalyzer.ui.component%
package ho.module.teamAnalyzer.ui.component;

import ho.core.db.DBManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.Matchdetails;
import ho.core.util.HelperWrapper;
import ho.module.teamAnalyzer.ui.NumberTextField;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * A panel that allows the user to download a new match from HO
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class DownloadPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -3212179990708350342L;

	/** Download Button */
    JButton downloadButton = new JButton(HOVerwaltung.instance().getLanguageString("Download"));

    /** Description label */
    JLabel jLabel1 = new JLabel();

    /** Status label */
    JLabel status = new JLabel();

    /** The matchid text field */
    NumberTextField matchId = new NumberTextField(10);

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructs a new instance.
     */
    public DownloadPanel() {
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Initializes the state of this instance.
     */
    private void jbInit() {
        jLabel1.setText(HOVerwaltung.instance().getLanguageString("GameID"));
        setLayout(new BorderLayout());
        add(jLabel1, BorderLayout.NORTH);
        add(downloadButton, BorderLayout.CENTER);
        add(matchId, BorderLayout.WEST);
        add(status, BorderLayout.SOUTH);

        downloadButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int id = matchId.getValue();

                    if (id == 0) {
                        status.setText(HOVerwaltung.instance().getLanguageString("ImportError"));

                        return;
                    }

                    if (HelperWrapper.instance().downloadMatchData(id)) {
	
                    	Matchdetails md = DBManager.instance().getMatchDetails(id);
	
	                    if (md.getFetchDatum() != null) {
	                        status.setText(HOVerwaltung.instance().getLanguageString("ImportOK"));
	                        matchId.setText("");
	                    } else {
	                        status.setText(HOVerwaltung.instance().getLanguageString("ImportError"));
	                    }
                    }
                }
            });
    }
}
