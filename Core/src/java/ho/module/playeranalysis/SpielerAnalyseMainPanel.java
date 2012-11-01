// %222653727:de.hattrickorganizer.gui.playeranalysis%
package ho.module.playeranalysis;

import ho.core.gui.ApplicationClosingListener;
import ho.core.gui.HOMainFrame;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.UserParameter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class SpielerAnalyseMainPanel extends ImagePanel implements ActionListener {

	private static final long serialVersionUID = 5384638406362299060L;
	private JButton m_jbDrehen;
	private JSplitPane m_jspSpielerAnalyseSplitPane;
	private SpielerAnalysePanel m_jpSpielerAnalysePanel1;
	private SpielerAnalysePanel m_jpSpielerAnalysePanel2;

	/**
	 * Creates a new SpielerAnalyseMainPanel object.
	 */
	public SpielerAnalyseMainPanel() {
		initComponents();
		addListeners();
	}

	public final void setSpieler4Bottom(int spielerid) {
		m_jpSpielerAnalysePanel2.setAktuelleSpieler(spielerid);
	}

	public final void setSpieler4Top(int spielerid) {
		m_jpSpielerAnalysePanel1.setAktuelleSpieler(spielerid);
	}

	@Override
	public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
		if (m_jspSpielerAnalyseSplitPane.getOrientation() == JSplitPane.VERTICAL_SPLIT) {
			m_jspSpielerAnalyseSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		} else {
			m_jspSpielerAnalyseSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}

		UserParameter.instance().spieleranalyseVertikal = !UserParameter.instance().spieleranalyseVertikal;
	}

	private void addListeners() {
		HOMainFrame.instance().addApplicationClosingListener(new ApplicationClosingListener() {

			@Override
			public void applicationClosing() {
				saveSettings();
			}
		});
	}

	private void saveSettings() {
		UserParameter parameter = UserParameter.instance();
		parameter.spielerAnalysePanel_horizontalSplitPane = m_jspSpielerAnalyseSplitPane
				.getDividerLocation();
		m_jpSpielerAnalysePanel1.saveColumnOrder();
		m_jpSpielerAnalysePanel2.saveColumnOrder();
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		final JPanel panel = new ImagePanel(new BorderLayout());

		m_jbDrehen = new JButton(ThemeManager.getIcon(HOIconName.TURN));
		m_jbDrehen.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString(
				"tt_SpielerAnalyse_drehen"));
		m_jbDrehen.setPreferredSize(new Dimension(24, 24));
		m_jbDrehen.addActionListener(this);
		panel.add(m_jbDrehen, BorderLayout.WEST);
		panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

		add(panel, BorderLayout.NORTH);

		m_jpSpielerAnalysePanel1 = new SpielerAnalysePanel(1);
		m_jpSpielerAnalysePanel2 = new SpielerAnalysePanel(2);
		m_jspSpielerAnalyseSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				m_jpSpielerAnalysePanel1, m_jpSpielerAnalysePanel2);
		m_jspSpielerAnalyseSplitPane
				.setDividerLocation(UserParameter.instance().spielerAnalysePanel_horizontalSplitPane);
		add(m_jspSpielerAnalyseSplitPane, BorderLayout.CENTER);

		if (!UserParameter.instance().spieleranalyseVertikal) {
			m_jspSpielerAnalyseSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		} else {
			m_jspSpielerAnalyseSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
	}
}
