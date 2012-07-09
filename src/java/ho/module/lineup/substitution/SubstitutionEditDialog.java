package ho.module.lineup.substitution;

import ho.core.model.HOVerwaltung;
import ho.core.util.GUIUtils;
import ho.module.lineup.substitution.model.MatchOrderType;
import ho.module.lineup.substitution.model.Substitution;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class SubstitutionEditDialog extends JDialog {

	private static final long serialVersionUID = 1875761460780943159L;
	private MatchOrderType orderType;
	private SubstitutionEditView behaviourView;
	private boolean canceled = true;

	public SubstitutionEditDialog(Dialog parent, MatchOrderType orderType) {
		super(parent, true);
		this.orderType = orderType;
		initDialog();
	}

	public SubstitutionEditDialog(Frame parent, MatchOrderType orderType) {
		super(parent, true);
		this.orderType = orderType;
		initDialog();
	}

	public void init(Substitution sub) {
		this.orderType = sub.getOrderType();
		setDlgTitle();
		this.behaviourView.init(sub);
	}

	public boolean isCanceled() {
		return this.canceled;
	}

	public Substitution getSubstitution() {
		return this.behaviourView.getSubstitution();
	}

	private void initDialog() {
		setDlgTitle();
		initComponents();
		pack();
	}

	private void setDlgTitle() {
		String dlgTitleKey = null;
		switch (this.orderType) {
		case NEW_BEHAVIOUR:
			dlgTitleKey = "subs.TypeOrder";
			break;
		case SUBSTITUTION:
			dlgTitleKey = "subs.TypeSub";
			break;
		case POSITION_SWAP:
			dlgTitleKey = "subs.TypeSwap";
			break;
		}
		setTitle(HOVerwaltung.instance().getLanguageString(dlgTitleKey));
	}

	private void initComponents() {
		getContentPane().setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel(new GridBagLayout());
		JButton okButton = new JButton(HOVerwaltung.instance().getLanguageString("subs.Ok"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(12, 8, 8, 2);
		buttonPanel.add(okButton, gbc);

		JButton cancelButton = new JButton(HOVerwaltung.instance().getLanguageString("subs.Cancel"));
		gbc.gridx = 1;
		gbc.weightx = 0.0;
		gbc.insets = new Insets(12, 2, 8, 8);
		buttonPanel.add(cancelButton, gbc);

		this.behaviourView = new SubstitutionEditView(this.orderType);
		getContentPane().add(this.behaviourView, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canceled = false;
				dispose();
			}
		});

		Action cancelAction = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				canceled = true;
				dispose();
			}

		};
		cancelAction.putValue(Action.NAME, HOVerwaltung.instance().getLanguageString("subs.Cancel"));
		cancelButton.setAction(cancelAction);
		GUIUtils.decorateWithActionOnESC(this, cancelAction);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				canceled = true;
			}
		});

		GUIUtils.equalizeComponentSizes(okButton, cancelButton);
	}
}
