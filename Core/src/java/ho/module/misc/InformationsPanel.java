// %1451261274:de.hattrickorganizer.gui.info%
package ho.module.misc;

import ho.core.gui.CursorToolkit;
import ho.core.gui.RefreshManager;
import ho.core.gui.Refreshable;
import ho.core.gui.comp.panel.ImagePanel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Zeigt die allgemeinen Informationen
 */
public class InformationsPanel extends ImagePanel implements Refreshable {

	private static final long serialVersionUID = 1218148161116371590L;
	private TeamPanel m_jpBasics;
	private FinanzenPanel m_jpAktuelleFinanzen;
	private FinanzenPanel m_jpVorwochenFinanzen;
	private MiscPanel m_jpSonstiges;
	private StaffPanel m_jpTrainerStab;
	private boolean initialized = false;
	private boolean needsRefresh = false;

	/**
	 * Creates a new InformationsPanel object.
	 */
	public InformationsPanel() {
		addHierarchyListener(new HierarchyListener() {

			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				if ((HierarchyEvent.SHOWING_CHANGED == (e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) && isShowing())) {
					if (!initialized) {
						CursorToolkit.startWaitCursor(InformationsPanel.this);
						try {
							initialize();
						} finally {
							CursorToolkit.stopWaitCursor(InformationsPanel.this);
						}
					}
					if (needsRefresh) {
						update();
					}
				}

			}
		});
	}

	@Override
	public final void reInit() {
		if (isShowing()) {
			update();
		} else {
			this.needsRefresh = true;
		}
	}

	@Override
	public final void refresh() {
		if (isShowing()) {
			update();
		} else {
			this.needsRefresh = true;
		}
	}

	private void initialize() {
		initComponents();
		RefreshManager.instance().registerRefreshable(this);
		update();
		this.initialized = true;
	}

	private void update() {
		CursorToolkit.startWaitCursor(this);
		try {
			m_jpBasics.setLabels();
			m_jpAktuelleFinanzen.setLabels();
			m_jpVorwochenFinanzen.setLabels();
			m_jpSonstiges.setLabels();
			m_jpTrainerStab.setLabels();
		} finally {
			CursorToolkit.stopWaitCursor(this);
		}
		this.needsRefresh = false;
	}

	private void initComponents() {
		final GridBagLayout layout = new GridBagLayout();
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.insets = new Insets(4, 4, 4, 4);

		setLayout(new BorderLayout());

		final JPanel mainPanel = new ImagePanel();
		mainPanel.setLayout(new GridLayout(2, 1, 4, 4));

		JPanel panel = new ImagePanel();
		panel.setLayout(layout);

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		m_jpBasics = new TeamPanel();
		layout.setConstraints(m_jpBasics, constraints);
		panel.add(m_jpBasics);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		m_jpTrainerStab = new StaffPanel();
		layout.setConstraints(m_jpTrainerStab, constraints);
		panel.add(m_jpTrainerStab);

		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		m_jpSonstiges = new MiscPanel();
		layout.setConstraints(m_jpSonstiges, constraints);
		panel.add(m_jpSonstiges);

		mainPanel.add(panel);

		panel = new ImagePanel();
		panel.setLayout(layout);

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		m_jpAktuelleFinanzen = new FinanzenPanel(true);
		layout.setConstraints(m_jpAktuelleFinanzen, constraints);
		panel.add(m_jpAktuelleFinanzen);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		m_jpVorwochenFinanzen = new FinanzenPanel(false);
		layout.setConstraints(m_jpVorwochenFinanzen, constraints);
		panel.add(m_jpVorwochenFinanzen);

		mainPanel.add(panel);

		final JScrollPane scrollpane = new JScrollPane(mainPanel);
		add(scrollpane);

	}

}
