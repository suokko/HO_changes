package ho.module.lineup;

import ho.core.db.DBManager;
import ho.core.gui.HOMainFrame;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.MatchKurzInfo;
import ho.core.net.OnlineWorker;
import ho.core.util.HOLogger;
import ho.core.util.XMLUtils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class TestPanel extends JPanel {

	private static final long serialVersionUID = -5314050322847463180L;
	private JList matchList;

	public TestPanel() {
		initComponents();
	}

	private void initComponents() {
		MatchKurzInfo[] matches = DBManager.instance().getMatchesKurzInfo(
				HOVerwaltung.instance().getModel().getBasics().getTeamId());

		Timestamp today = new Timestamp(System.currentTimeMillis());
		List<ListElement> listElements = new ArrayList<ListElement>();
		for (MatchKurzInfo match : matches) {
			if (match.getMatchDateAsTimestamp().after(today)) {
				listElements.add(new ListElement(match));
			}
		}

		final JButton uploadButton = new JButton("upload");
		uploadButton.setEnabled(false);
		uploadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				upload();
			}
		});

		this.matchList = new JList(listElements.toArray());
		this.matchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.matchList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					uploadButton.setEnabled(matchList.getSelectedIndex() != -1);
				}
			}
		});

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		add(matchList, gbc);

		gbc.gridx = 1;
		add(uploadButton, gbc);
	}

	private void upload() {
		ListElement elem = (ListElement) this.matchList.getSelectedValue();
		int matchId = elem.getMatch().getMatchID();

		OnlineWorker ow = new OnlineWorker();
		String result = ow.uploadMatchOrder(matchId, elem.getMatch().getMatchTyp(), HOVerwaltung
				.instance().getModel().getAufstellung());

		int messageType = JOptionPane.PLAIN_MESSAGE;
		String message = null;
		try {
			Document doc = XMLUtils.createDocument(result);
			String successStr = XMLUtils.getAttributeValueFromNode(doc, "MatchData", "OrdersSet");
			if (successStr != null) {
				boolean success = Boolean.parseBoolean(successStr);
				if (success) {
					messageType = JOptionPane.PLAIN_MESSAGE;
					message = HOVerwaltung.instance().getLanguageString("lineup.upload.success");
				} else {
					messageType = JOptionPane.ERROR_MESSAGE;
					message = HOVerwaltung.instance().getLanguageString("lineup.upload.fail")
							+ XMLUtils.getTagData(doc, "Reason");
				}
			} else {
				messageType = JOptionPane.ERROR_MESSAGE;
				message = HOVerwaltung.instance().getLanguageString(
						"lineup.upload.result.parseerror");
				HOLogger.instance().log(TestPanel.class, message + "\n" + result);
			}
		} catch (SAXException e) {
			messageType = JOptionPane.ERROR_MESSAGE;
			message = HOVerwaltung.instance().getLanguageString("lineup.upload.result.parseerror");
			HOLogger.instance().log(TestPanel.class, message + "\n" + result);
			HOLogger.instance().log(TestPanel.class, e);
		}

		JOptionPane.showMessageDialog(HOMainFrame.instance(), message, HOVerwaltung.instance()
				.getLanguageString("lineup.upload.title"), messageType);
	}

	private class ListElement {
		private MatchKurzInfo match;

		public ListElement(MatchKurzInfo match) {
			this.match = match;
		}

		public MatchKurzInfo getMatch() {
			return this.match;
		}

		@Override
		public String toString() {
			return this.match.getMatchDate() + " - " + this.match.getHeimName() + " - "
					+ this.match.getGastName();
		}
	}
}
