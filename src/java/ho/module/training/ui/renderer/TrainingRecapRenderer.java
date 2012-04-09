// %36155679:hoplugins.trainingExperience.ui.renderer%
package ho.module.training.ui.renderer;

import ho.core.constants.player.PlayerSkill;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.Spieler;
import ho.core.util.HelperWrapper;
import ho.module.training.ui.TrainingLegendPanel;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * Rendered for the TrainingRecap Table
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class TrainingRecapRenderer extends DefaultTableCellRenderer {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4088001127909689247L;
	private static final Color SELECTION_BG = new java.awt.Color(210, 210, 210); 
    private static final Color BIRTHDAY_BG = new java.awt.Color(255, 240, 175); 
    //~ Methods ------------------------------------------------------------------------------------

    /* (non-Javadoc)
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    @Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Reset default values
        this.setForeground(Color.BLACK);
        if (isSelected)
        	this.setBackground(SELECTION_BG);
        else
        	this.setBackground(Color.WHITE);
        
        String text = null;
        String tooltip = null;
        Icon icon = null;
        
        try {
        	String s = (String) table.getValueAt(row, column);
            int playerId = 0;
            double realPlayerAge = 0;
            
            // fetch playerId (last column) from table
        	playerId = Integer.parseInt((String)table.getValueAt(row, table.getColumnCount()-1));
        	Spieler player =HOVerwaltung.instance().getModel().getSpieler(playerId);
        	realPlayerAge = player.getAlterWithAgeDays();
        	
        	/** If there is some kind of skillup information
        	 * in the table cell (s) -> extract it
        	 * (it is in the format "SKILLTYPE SKILLLEVEL", 
        	 * e.g. "3 10" for outstanding playmaking) 
        	 */
        	
        	if (s != null && s.length() > 0) {
        		String[] skills = s.split(" "); //$NON-NLS-1$
        		int skillType = Integer.parseInt(skills[0]);
//        		Color color = Skills.getSkillColor(skillType);
        		icon = TrainingLegendPanel.getSkillupTypeIcon(skillType, 1);
        		int val = Integer.parseInt(skills[1]);
        		String skillLevelName = HelperWrapper.instance().getNameForSkill(val, true);
        		tooltip =PlayerSkill.toString(skillType)+": " + skillLevelName;
        		text = skillLevelName;
        	}
        	
            if (playerId > 0) {
            	// Check if player has birthday
            	// every row is an additional week
            	int calcPlayerAgePrevCol = (int) (realPlayerAge + column*7d/112d);
            	int calcPlayerAgeThisCol = (int) (realPlayerAge + (column+1)*7d/112d);
            	// Birthday in this week! Set BG color
            	if (calcPlayerAgePrevCol < calcPlayerAgeThisCol) {
            		String ageText =  HOVerwaltung.instance().getLanguageString("age.birthday") 
    								+ " (" + calcPlayerAgeThisCol + " "
    								+  HOVerwaltung.instance().getLanguageString("age.years") 
    								+ ")";

            		if (text == null || text.length() == 0) {
            			text = ageText;
            		} else {
            			tooltip = "<html>" + tooltip + "<br>" + ageText + "</html>";
            		}
            		this.setBackground(BIRTHDAY_BG);
            	}
            }

            if (tooltip == null)
            	tooltip = text;
    		
            this.setToolTipText(tooltip);
    		this.setText(text);
    		this.setIcon(icon);
        } catch (Exception e) {
        }
        return this;
    }
}
