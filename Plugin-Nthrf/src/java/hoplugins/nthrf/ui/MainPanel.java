package hoplugins.nthrf.ui;

import hoplugins.Nthrf;
import hoplugins.nthrf.util.NthrfUtil;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    	ta.append("After a successful download you'll find the created HRF file\n");
    	ta.append("in your root folder, e.g. C:\\nt_1234_20090525_221000.hrf\n");
    	ta.append("\n");
    	ta.append("Contact user 'aYcon' in HT if you have any questions.");
    	ta.setEditable(false);
    	doc.add(ta, BorderLayout.CENTER);

    	doc.add(new JLabel(" "), BorderLayout.SOUTH);
//    	doc.setMinimumSize(new Dimension(250, 50));
//    	doc.setPreferredSize(new Dimension(250, 60));
    	msg.add(doc, BorderLayout.SOUTH);
//    	msg.setMinimumSize(new Dimension(300, 80));
//    	msg.setPreferredSize(new Dimension(300, 100));
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

			long[] teamIds = NthrfUtil.getNtTeamIds(mm);
			if (teamIds == null || teamIds.length<1) {
				debug("No NT manager! Stopping...");
				if (!Nthrf.DEBUG) {
					return false;
				} else {
					teamIds = new long[]{3216}; // 526156 - 3216
				}
			}
			long teamId = teamIds[0]; // TODO: handle more than one NT team for a manager
			debug("Compute for team " + teamId);

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
		if (true) return true; // now allow all team

		return (teamId == 3002 || teamId == 3043 // Germany NT + U20
				|| teamId == 3245 || teamId == 3246 // Qatar NT + U20
				|| teamId == 3216 || teamId == 3217 // Iraq NT + U20
				|| teamId == 3024 || teamId == 3065 // Brasil NT + U20
				|| teamId == 3036 || teamId == 3077 // Singapore
				|| teamId == 526156); // Debug
	}

	private void debug(String txt) {
		System.out.println("Nthrf: " + txt);
	}
}
