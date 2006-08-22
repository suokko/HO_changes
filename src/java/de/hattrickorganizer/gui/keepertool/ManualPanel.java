// %362946196:de.hattrickorganizer.gui.keepertool%
package de.hattrickorganizer.gui.keepertool;

import de.hattrickorganizer.gui.pluginWrapper.GUIPluginWrapper;
import de.hattrickorganizer.model.HOMiniModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * Panel for manual editing of keeper data
 *
 * @author draghetto
 */
public class ManualPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private JComboBox form = new JComboBox();
    private JTextField tsi = new JTextField(10);
    private ResultPanel target;
    private int formValue;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ManualPanel object.
     *
     * @param panel the panel where to show results
     */
    public ManualPanel(ResultPanel panel) {
        target = panel;
        init();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Reset the panel to default data
     */
    public final void reset() {
        form.setSelectedIndex(0);
        tsi.setText("");
        formValue = 0;
    }

    /**
     * Initialize the GUI components
     */
    private void init() {
        setLayout(new BorderLayout());
        setOpaque(false);

        final JPanel buttonPanel = GUIPluginWrapper.instance().createImagePanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(6, 2));
        buttonPanel.add(label(""));
        buttonPanel.add(label(""));
        buttonPanel.add(label("TSI"));
        buttonPanel.add(tsi);
        buttonPanel.add(label(""));
        buttonPanel.add(label(""));
        buttonPanel.add(label(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                     .getProperty("Form")));
        buttonPanel.add(form);
        buttonPanel.add(label(""));
        buttonPanel.add(label(""));
        buttonPanel.add(label(""));

        for (int i = 1; i < 9; i++) {
            form.addItem(HOMiniModel.instance().getHelper().getNameForSkill(i, false));
        }

        form.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    formValue = form.getSelectedIndex() + 1;
                }
            });

        final JButton b = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                             .getResource()
                                                                             .getProperty("SubskillsBerechnen"));
        b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    final int tsiValue = Integer.parseInt(tsi.getText());
                    target.setPlayer(formValue, tsiValue, 0, "");
                }
            });
        buttonPanel.add(b);
        add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Create a configured label
     *
     * @param string the label text
     *
     * @return the built component
     */
    private Component label(String string) {
        final JLabel label = new JLabel(string, JLabel.CENTER);
        label.setOpaque(false);
        return label;
    }
}
