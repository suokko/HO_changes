// %198737965:de.hattrickorganizer.gui.menu.option%
package de.hattrickorganizer.gui.dbcleanup;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.ButtonBarBuilder2;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.model.HOVerwaltung;


/**
 * Database Cleanup Dialog
 * 
 * @author flattermann <HO@flattermann.net>
 */
public class DBCleanupDialog extends JDialog implements ActionListener, FocusListener {

	private static final long serialVersionUID = 3533368597781557223L;
	private static final int MATCHTYPE_OWN_MATCH = 0;
	private static final int MATCHTYPE_OWN_FRIENDLY = 1;
	private static final int MATCHTYPE_OTHER_MATCH = 2;
	private static final int MATCHTYPE_OTHER_FRIENDLY = 3;
	private static final int NUM_MATCHTYPES = 4;

	// Components as in array allComponents
	private static final int COMPONENT_LABEL = 0;
	private static final int COMPONENT_NONE = 1;
	private static final int COMPONENT_ALL = 2;
	private static final int COMPONENT_OLDER_THAN= 3;
	private static final int COMPONENT_WEEKS_INPUT= 4;
	private static final int NUM_COMPONENTS = 5;
	
	private DBCleanupTool cleanupTool;

	private JButton jbCleanupNow = new JButton(HOVerwaltung.instance().getLanguageString("dbcleanup.cleanupnow"));
	private JButton jbCancel = new JButton(HOVerwaltung.instance().getLanguageString("dbcleanup.cancel"));

	private JTextArea jtaIntro = new JTextArea (HOVerwaltung.instance().getLanguageString("dbcleanup.intro"));
	
	private JLabel jlMatches[] = new JLabel[NUM_MATCHTYPES];
	private JCheckBox jcbNone[] = new JCheckBox[NUM_MATCHTYPES];
	private JCheckBox jcbAll[] = new JCheckBox[NUM_MATCHTYPES];
	private JCheckBox jcbOlderThan[] = new JCheckBox[NUM_MATCHTYPES];
	private JTextField jtfWeeksInput[] = new JTextField[NUM_MATCHTYPES];
	private JLabel jlWeeks[] = new JLabel[NUM_MATCHTYPES];

	private JComponent[][] allComponents = {jlMatches, jcbNone, jcbAll, jcbOlderThan, jtfWeeksInput};
	
	private JLabel jlHrf = new JLabel (HOVerwaltung.instance().getLanguageString("dbcleanup.hrf"));
//	private WeekSelectionPanel wsp_Hrf = new WeekSelectionPanel (DBCleanupTool.REMOVE_NONE, false);

//	private JLabel labelHrfAutoremove = new JLabel (HOVerwaltung.instance().getLanguageString("dbcleanup.hrfAutoremove"));
	private JCheckBox jcbHrfAutoremove = new JCheckBox(HOVerwaltung.instance().getLanguageString("dbcleanup.hrfAutoremove"));

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new DBCleanupDialog object.
	 */
	public DBCleanupDialog(JFrame owner, DBCleanupTool cleanupTool) {
		super(owner, 
				HOVerwaltung.instance().getLanguageString("dbcleanup"),
				true);
		this.cleanupTool = cleanupTool;
		initComponents();
		initLayout();
	}

	//~ Methods ------------------------------------------------------------------------------------

	private void initLayout() {
		FormLayout layout = new FormLayout("p, 10dlu, p, 10dlu, p, 10dlu, p, 2dlu, p, 2dlu, p", // cols 
									""); // dynamic rows
		
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.setDefaultDialogBorder();
		builder.setRowGroupingEnabled(true);
		CellConstraints cc = new CellConstraints();

		builder.appendTitle(HOVerwaltung.instance().getLanguageString("dbcleanup"));

		builder.appendRow("p");
		builder.nextLine();
		builder.add (jtaIntro, cc.xyw(builder.getColumn(), builder.getRow(), builder.getColumnCount()));
		builder.nextLine();
		
		builder.appendSeparator("Cleanup matches");//HOVerwaltung.instance().getLanguageString("dbcleanup.matches"));
		
		for (int matchType=0; matchType<NUM_MATCHTYPES; matchType++) {
			builder.append(jlMatches[matchType]);
			builder.append(jcbNone[matchType]);
			builder.append(jcbAll[matchType]);
			builder.append(jcbOlderThan[matchType]);
			builder.append(jtfWeeksInput[matchType]);
			builder.append(jlWeeks[matchType]);
			builder.nextLine();
		}

		builder.appendSeparator(HOVerwaltung.instance().getLanguageString("dbcleanup.hrf"));

		builder.appendRow("p");
		builder.nextLine();
		builder.add (jcbHrfAutoremove, cc.xyw(builder.getColumn(), builder.getRow(), builder.getColumnCount()));
		
		builder.appendRow("p");
		builder.nextLine();
		
		/* Create button bar */
		ButtonBarBuilder2 buttonBarBuilder = new ButtonBarBuilder2();
		buttonBarBuilder.addGlue();
		buttonBarBuilder.addButton(jbCleanupNow);
		buttonBarBuilder.addRelatedGap();
		buttonBarBuilder.addButton(jbCancel);

		builder.add(buttonBarBuilder.getPanel(), cc.xyw(builder.getColumn(), builder.getRow(), builder.getColumnCount()));

		getContentPane().add(builder.getPanel());
		
		pack();
		
        final Dimension size = HOMainFrame.instance().getToolkit().getScreenSize();

        if (size.width > this.getSize().width) {
            //Mittig positionieren
            this.setLocation((size.width / 2) - (this.getSize().width / 2),
                             (size.height / 2) - (this.getSize().height / 2));
        }
        setVisible(true);
}
	
	private void initComponents() {
		for (int matchType=0; matchType < NUM_MATCHTYPES; matchType++) {
			jcbNone[matchType] = new JCheckBox (HOVerwaltung.instance().getLanguageString("dbcleanup.none"));
			jcbAll[matchType] = new JCheckBox (HOVerwaltung.instance().getLanguageString("dbcleanup.all"));
			jcbOlderThan[matchType] = new JCheckBox (HOVerwaltung.instance().getLanguageString("dbcleanup.removeOlderThan"));
			jtfWeeksInput[matchType] = new JTextField("0", 3);
			jlWeeks[matchType] = new JLabel (HOVerwaltung.instance().getLanguageString("dbcleanup.weeks"));

			jcbNone[matchType].addActionListener(this);
			jcbAll[matchType].addActionListener(this);
			jcbOlderThan[matchType].addActionListener(this);
			jtfWeeksInput[matchType].addFocusListener(this);
		}

		jlMatches[MATCHTYPE_OWN_MATCH] = new JLabel (HOVerwaltung.instance().getLanguageString("dbcleanup.ownMatches"));
		jlMatches[MATCHTYPE_OWN_FRIENDLY] = new JLabel (HOVerwaltung.instance().getLanguageString("dbcleanup.ownFriendlies"));
		jlMatches[MATCHTYPE_OTHER_MATCH] = new JLabel (HOVerwaltung.instance().getLanguageString("dbcleanup.otherMatches"));
		jlMatches[MATCHTYPE_OTHER_FRIENDLY] = new JLabel (HOVerwaltung.instance().getLanguageString("dbcleanup.otherFriendlies"));

		Font boldFont = jlMatches[MATCHTYPE_OWN_MATCH].getFont().deriveFont(Font.BOLD);
		jlMatches[MATCHTYPE_OWN_MATCH].setFont(boldFont);
		jlMatches[MATCHTYPE_OWN_FRIENDLY].setFont(boldFont);
		jlMatches[MATCHTYPE_OTHER_MATCH].setFont(boldFont);
		jlMatches[MATCHTYPE_OTHER_FRIENDLY].setFont(boldFont);

		jbCleanupNow.addActionListener(this);
		jbCancel.addActionListener(this);

		jtaIntro.setEditable(false);
		jtaIntro.setWrapStyleWord(true);
		jtaIntro.setLineWrap(true);

		jlHrf.setFont(jlHrf.getFont().deriveFont(Font.BOLD));

		// Set defaults
		jcbNone[MATCHTYPE_OWN_MATCH].setSelected(true);
		jcbNone[MATCHTYPE_OWN_FRIENDLY].setSelected(true);
		jcbOlderThan[MATCHTYPE_OTHER_MATCH].setSelected(true);
		jtfWeeksInput[MATCHTYPE_OTHER_MATCH].setText("16");
		jcbOlderThan[MATCHTYPE_OTHER_FRIENDLY].setSelected(true);
		jtfWeeksInput[MATCHTYPE_OTHER_FRIENDLY].setText("8");
		jcbHrfAutoremove.setSelected(true);
	}

	private int getMatchType (Object o) {
		try {
			JComponent curComponent = (JComponent)o;
			for (int compType=0; compType < NUM_COMPONENTS; compType++) {
				for (int matchType=0; matchType < NUM_MATCHTYPES; matchType++) {
					if (curComponent.equals(allComponents[compType][matchType])) {
						return matchType;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return -1;
	}
	
	private int getComponentType (Object o) {
		try {
			JComponent curComponent = (JComponent)o;
			for (int compType=0; compType < NUM_COMPONENTS; compType++) {
				for (int matchType=0; matchType < NUM_MATCHTYPES; matchType++) {
					if (curComponent.equals(allComponents[compType][matchType])) {
						return compType;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return -1;
	}

	public void actionPerformed(ActionEvent e) {
		int compType = getComponentType(e.getSource());
		int matchType = getMatchType(e.getSource());
		if (compType == COMPONENT_NONE) {
			if (jcbNone[matchType].isSelected()) {
				jcbAll[matchType].setSelected(false);
				jcbOlderThan[matchType].setSelected(false);
			} else {
				jcbNone[matchType].setSelected(true);
			}
		} else if (compType == COMPONENT_ALL) {
			if (jcbAll[matchType].isSelected()) {
				jcbNone[matchType].setSelected(false);
				jcbOlderThan[matchType].setSelected(false);
			} else {
				jcbNone[matchType].setSelected(true);
			}
		} else if (compType == COMPONENT_OLDER_THAN) {
			if (jcbOlderThan[matchType].isSelected()) {
				jcbAll[matchType].setSelected(false);
				jcbNone[matchType].setSelected(false);
			} else {
				jcbNone[matchType].setSelected(true);
			}
		} else if (e.getSource().equals(jbCleanupNow)) {
			cleanupTool.cleanupMatches (getRemoveWeeks(MATCHTYPE_OWN_MATCH), getRemoveWeeks(MATCHTYPE_OWN_FRIENDLY),
					getRemoveWeeks(MATCHTYPE_OTHER_MATCH), getRemoveWeeks(MATCHTYPE_OTHER_FRIENDLY));
			cleanupTool.cleanupHRFs (DBCleanupTool.REMOVE_NONE, jcbHrfAutoremove.isSelected());
			setVisible(false);
		} else if (e.getSource().equals(jbCancel)) {
			setVisible(false);
		}
	}

	private int getRemoveWeeks (int matchType) {
		if (jcbNone[matchType].isSelected()) {
			return DBCleanupTool.REMOVE_NONE;			
		} else if (jcbAll[matchType].isSelected()) {
			return DBCleanupTool.REMOVE_ALL;
		} else if (jcbOlderThan[matchType].isSelected()){
			int weeks = DBCleanupTool.REMOVE_NONE;
			try {
				weeks = Integer.parseInt(jtfWeeksInput[matchType].getText());
			} catch (Exception e) {
				// be silent
			}
			if (weeks > 0) {
				return weeks;
			}
		}
		return DBCleanupTool.REMOVE_NONE;
	}
	
	public void focusGained(FocusEvent e) {
		int compType = getComponentType(e.getSource());
		int matchType = getMatchType(e.getSource());
		if (compType == COMPONENT_WEEKS_INPUT) {
			jcbAll[matchType].setSelected(false);
			jcbNone[matchType].setSelected(false);
			jcbOlderThan[matchType].setSelected(true);
		}
	}

	public void focusLost(FocusEvent arg0) {
		// do nothing
	}
}
