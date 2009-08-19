package hoplugins.nthrf.ui;

import hoplugins.Nthrf;
import hoplugins.nthrf.util.NthrfUtil;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import plugins.IHOMiniModel;


/**
 * Main panel of the Nthrf plugin.
 */
public class MainPanel extends JPanel implements ActionListener {
	static final long serialVersionUID = 1;
	private static MainPanel instance = null;
	private JButton btnStart = null;

	/**
     * Constructs a new instance.
     */
    private MainPanel() {
    	buildGui();
    }

    public static MainPanel getInstance() {
    	if (instance == null) {
    		instance = new MainPanel();
    	}
    	return instance;
    }

    /**
     * Build the necessary GUI components.
     */
    private void buildGui() {
    	setLayout(new BorderLayout());
    	JPanel msg = new JPanel();
    	JPanel doc = new JPanel();
    	msg.setLayout(new BorderLayout());
    	doc.setLayout(new BorderLayout());
    	doc.setBorder(new EtchedBorder());
    	msg.add(new JLabel("Hint: NEVER import any NT HRF files into your normal HO!"), BorderLayout.NORTH);
    	msg.add(new JLabel("Use a secondary installation only."), BorderLayout.CENTER);

    	JTextArea ta = new JTextArea();
    	ta.append("This plugin can be used by elected NT/U20 managers only!\n");
    	ta.append("Press 'Start' to download your national team's data.\n");
    	ta.append("\n");
    	ta.append("Contact user 'aYcon' in HT if you have any questions.");
    	ta.setEditable(false);
    	doc.add(ta, BorderLayout.CENTER);

    	doc.add(new JLabel(" "), BorderLayout.SOUTH);
    	msg.add(doc, BorderLayout.SOUTH);
    	add(msg, BorderLayout.NORTH);
    	btnStart = new JButton("Start");
    	btnStart.addActionListener(this);
    	add(btnStart, BorderLayout.SOUTH);
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnStart)) {
			debug("START " + Nthrf.PLUGIN_NAME + " Version " + Nthrf.PLUGIN_VERSION + " ...");
			btnStart.setEnabled(false);
			download();
			debug("DONE " + Nthrf.PLUGIN_NAME);
		}
	}

	/**
	 * Download current NT data and create a HRF file.
	 */
	private boolean download() {
		try {
			IHOMiniModel mm = Nthrf.getMiniModel();

			List<String[]> teams = NthrfUtil.getNtTeams(mm);
			if (teams == null || teams.size() < 1 || teams.get(0)[0] == null || teams.get(0)[0].length() < 1) {
				debug("No NT manager! Stopping...");
				if (!Nthrf.DEBUG) {
					return false;
				} else {
					teams = new ArrayList<String[]>();
					teams.add(new String[]{"526156", "Club Team"});
					teams.add(new String[]{"3216", "National Team"});
				}
			}
			final long teamId;
			if (teams.size() > 1) {
				NtTeamChooser chooser = new NtTeamChooser(teams);
				chooser.setModal(true);
				chooser.setVisible(true);
				teamId = chooser.getSelectedTeamId();
				System.out.println("Result is: " + chooser.getSelectedTeamId());
				chooser.dispose();
			} else {
				teamId = Long.parseLong(teams.get(0)[0]);
			}
			debug("Compute for team " + teamId);

			if (teamId < 0) {
				btnStart.setEnabled(true);
				return false;
			}
			if (!isAllowedTeam(teamId)) {
				debug("Wrong team (id " + teamId + "), can't continue!");
				return false;
			}
			NthrfUtil.createNthrf(teamId, mm);
			debug("finished");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean isAllowedTeam(long teamId) {
		return true; // now allow all team
	}

	private void debug(String txt) {
		System.out.println("Nthrf: " + txt);
	}
}
