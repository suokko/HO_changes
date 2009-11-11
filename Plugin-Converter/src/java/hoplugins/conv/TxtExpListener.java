/*
 * Created on 16.05.2004
 *
 */
package hoplugins.conv;




import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;




/**
 * @author Thorsten Dietz
 *  
 */
public final class TxtExpListener implements ActionListener {


	public void actionPerformed(ActionEvent e) {
		String comand = e.getActionCommand();
		TxtExpDialog dialog = (TxtExpDialog)((JButton)e.getSource()).getTopLevelAncestor();
		if (comand.equals(RSC.ACT_CANCEL)) {
		    dialog.dispose();
		}
		if (comand.equals(RSC.PROP_ADD)) {
		    try {
		    	if(dialog.getList().getSelectedValues().length==0){
					JOptionPane.showMessageDialog(null,	"You have to select min. one column"
							,RSC.NAME,JOptionPane.ERROR_MESSAGE);
					return;
		    	}
		    	dialog.dispose();
		    	JFileChooser saver = new JFileChooser();
		    	int returnSave = saver.showSaveDialog(RSC.MINIMODEL.getGUI()
						.getOwner4Dialog());
		    	if (returnSave == JFileChooser.APPROVE_OPTION) {
		    		File target = saver.getSelectedFile();
		    		dialog.writeFile(target);
		    	}
			} catch (Exception e1) {
				RSC.handleException(e1,"ASCII");
			}
		}
	}



}