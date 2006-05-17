// %1945446148:de.hattrickorganizer.gui.hoFriendly%
package de.hattrickorganizer;

import java.awt.BorderLayout;

import javax.swing.JDialog;

import de.hattrickorganizer.gui.hoFriendly.TrainerLibrary;
import de.hattrickorganizer.gui.hoFriendly.TrainerPanel;


/**
 * Zeigt den Trainer an
 */
public class TestTrainerPanel {

    public static void main(String[] args) {
        de.hattrickorganizer.gui.HOMainFrame.main(args);

        TrainerLibrary.load();

        final JDialog dialog = new JDialog(de.hattrickorganizer.gui.HOMainFrame.instance(), "Test");
        dialog.getContentPane().setLayout(new BorderLayout());

        final TrainerPanel panel = new TrainerPanel(true);
        dialog.getContentPane().add(panel, BorderLayout.CENTER);
        dialog.setSize(250, 200);
        new Thread(panel).start();

        dialog.setVisible(true);
    }

}
