// %1126721330198:hoplugins.transfers.ui.component%
package hoplugins.transfers.ui.component;

import hoplugins.Commons;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.transfers.dao.TransferSettingDAO;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * A panel that allows the user to configure the plugin
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class OptionPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private JCheckBox automatic = new JCheckBox();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructs a new instance.
     */
    public OptionPanel() {
        super();
        automatic.setSelected(TransferSettingDAO.isAutomatic());
        automatic.setOpaque(false);
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Create a new Panel
     *
     * @param string Label text
     * @param checkBox CheckBox
     *
     * @return a panel
     */
    private JPanel createPanel(String string, JCheckBox checkBox) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);

        final JPanel innerPanel = new JPanel();

        //innerPanel.setLayout(new BorderLayout());
        innerPanel.add(checkBox);
        innerPanel.add(new JLabel(string, JLabel.LEFT));
        innerPanel.setOpaque(false);
        panel.add(innerPanel, BorderLayout.WEST);
        return panel;
    }

    /**
     * Initialize listeners
     */
    private void initListeners() {
        automatic.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    TransferSettingDAO.setAutomatic(automatic.isSelected());
//                    if (!automatic.isSelected()) {
//                        JOptionPane.showMessageDialog(Commons.getModel().getGUI().getOwner4Dialog(),
//                                                      new DisablePanel(),
//                                                      PluginProperty.getString("Option.Title"), //$NON-NLS-1$
//                                                      JOptionPane.PLAIN_MESSAGE);
//                    }
                }
            });
    }

    /**
     * Initializes the state of this instance.
     */
    private void jbInit() {
        initListeners();

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 1));
        mainPanel.setOpaque(false);
        mainPanel.add(createPanel(PluginProperty.getString("Option.Auto"), automatic)); //$NON-NLS-1$

        setLayout(new BorderLayout());
        setOpaque(false);
        add(mainPanel, BorderLayout.CENTER);
    }
}
