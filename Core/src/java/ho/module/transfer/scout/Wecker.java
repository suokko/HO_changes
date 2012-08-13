// %2833997336:de.hattrickorganizer.gui.transferscout%
/*
 * Wecker.java
 *
 * Created on 8. April 2003, 10:14
 */
package ho.module.transfer.scout;

import ho.core.gui.HOMainFrame;

import javax.swing.JButton;
import javax.swing.JTextArea;



/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
class Wecker extends javax.swing.JFrame implements java.awt.event.ActionListener {

	private static final long serialVersionUID = -8263831429834255080L;
	
	//~ Instance fields ----------------------------------------------------------------------------
    private JButton m_jbOK = new JButton();

    //~ Constructors -------------------------------------------------------------------------------
    /**
     * Creates a new instance of Wecker
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     */
    Wecker(String text) {
        //javax.swing.JOptionPane temp    =   new javax.swing.JOptionPane( text, javax.swing.JOptionPane.INFORMATION_MESSAGE ) ;
        final JTextArea ta = new JTextArea();

        ta.setEditable(false);
        ta.setText(text);
        m_jbOK.setText("OK");
        m_jbOK.addActionListener(this);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Scout");
        this.setIconImage(HOMainFrame.instance().getIconImage());

        //this.setContentPane(temp);
        this.getContentPane().setLayout(new java.awt.BorderLayout());

        this.getContentPane().add(ta, java.awt.BorderLayout.CENTER /*new JLabel( text )*/);
        this.getContentPane().add(m_jbOK, java.awt.BorderLayout.SOUTH);
        pack();
        this.setLocation((int) ((this.getToolkit().getScreenSize().getWidth() / 2)
                         - (this.getSize().getWidth() / 2)),
                         (int) ((this.getToolkit().getScreenSize().getHeight() / 2)
                         - (this.getSize().getHeight() / 2)));
        this.setVisible(true);
        this.setResizable(false);
        this.toFront();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param actionEvent TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(m_jbOK)) {
            setVisible(false);
            this.dispose();
        }
    }
}
