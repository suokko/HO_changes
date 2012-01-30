/*
 * Created on 06.07.2004
 */
package ho.module.playerCompare;

import gui.HOColorName;
import gui.HOIconName;
import gui.UserParameter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import plugins.IXtraData;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HelperWrapper;

class MyTableCellRenderer  implements TableCellRenderer{

	private final long serialVersionUID = -6249868492929346343L;
	private Color gelb = new Color(255,255,200);
	private Color gruen = new Color (220,255,220);
	private Color dklgruen = new Color (0,200,0);
	private Color rot = new Color (255,200,200);
	private Color dklrot = new Color (200,0,0);
	private Color hellblau = new Color (235,235,255);
	private Color dunkelblau = new Color (220,220,255);
	//***** Anlegen der JLabel *****
	private JLabel label;
	private JLabel wertNeu;
	private JLabel wertAlt;
	//***** weitere Klassenvariablen *****
	private String[] name = new String[2];
	private int natWert;
	private DecimalFormat df = new DecimalFormat("#,###,##0.00");
	
	//***** Konstruktor *****
	public MyTableCellRenderer()
	{

		gelb = ThemeManager.getColor(HOColorName.PLAYER_SKILL_BG);
		gruen= ThemeManager.getColor(HOColorName.PLAYER_SKILL_SPECIAL_BG);
		hellblau = ThemeManager.getColor(HOColorName.PLAYER_SUBPOS_BG);
		dunkelblau = ThemeManager.getColor(HOColorName.PLAYER_POS_BG);
	}
	
	public Component getTableCellRendererComponent(JTable table,
			Object value,
			boolean isSelected,
			boolean hasFocus,
            int row,
            int column)
	{
		label = new JLabel();

		if(table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("Name"))) {	
			int i = 0;
			int spezWert = 0;
			StringTokenizer tk = new StringTokenizer(table.getValueAt(row,column).toString(),";");
			while (tk.hasMoreTokens()) {
		         name[i]=tk.nextToken();
		         i++;
		    }
			try	{
				spezWert = Integer.parseInt(name[1]);
			}
			catch(Exception e){}
			
			Icon ic = ThemeManager.getIcon(HOIconName.SPECIAL[spezWert]);
			label.setLayout(new BorderLayout());
			label.setText(name[0]);
			JLabel l2 = new JLabel(ic,SwingConstants.LEFT);
			label.add(l2,BorderLayout.EAST);
			label.setBackground(table.getBackground());
			label.validate();
		}
		else if(column == 2) {
			natWert = ((Integer)table.getValueAt(row,column)).intValue();
			label.setIcon(HelperWrapper.instance().getImageIcon4Country(natWert));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBackground(table.getBackground());
		}
		else if(table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("Gruppe")))	{

			
			String group = ((String)table.getValueAt(row,column)).toString();
			if(group != null && group.length() > 3)
				label.setIcon(HelperWrapper.instance().getImageIcon(group));
			label.setBackground(table.getBackground());
		} else if(table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("BestePosition")))		{
			byte tmpPos = ((Float)table.getValueAt(row,column)).byteValue();
			float tmpFloat =HelperWrapper.instance().round((((Float)table.getValueAt(row,column)).floatValue() - tmpPos)*100,UserParameter.instance().anzahlNachkommastellen);
			label.setText(HelperWrapper.instance().getNameForPosition(tmpPos) + " ("+ tmpFloat +")");
			label.setBackground(table.getBackground());
		} else if(table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("MC"))) {
			double skillwert = 0;
			String skillwertS = "";
			try	{
				skillwertS = (table.getValueAt(row,column)).toString();
				skillwert = Double.parseDouble(skillwertS);

			} catch(Exception e){}
			
			int skillWertNew = new Double(skillwert).intValue();
			int skillWertOld = new Double((skillwert - skillWertNew) * 100 + 0.1).intValue();
			int changeWert = skillWertNew - skillWertOld;
			if (skillwert == 2 || skillWertNew == 2 || skillWertOld == 2)
			{
				label.setIcon(HelperWrapper.instance().getImageIcon(HOIconName.HOMEGROWN));
				label.setHorizontalAlignment(SwingConstants.CENTER);
			}
			if (changeWert < 0)
				label.setBackground(Color.RED);
			else if (changeWert > 0)
				label.setBackground(Color.GREEN);
			else
				label.setBackground(table.getBackground());
		}
		else if(table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("ER"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("FUE"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("FO"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("KO"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("TW"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("VE"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("SA"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("PS"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("FL"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("TS"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("ST"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("LOY"))
				)
		{
			double skillwert = 0;
			String skillwertS = "";
			try
			{
				skillwertS = (table.getValueAt(row,column)).toString();
				skillwert = Double.parseDouble(skillwertS);
			}
			catch(Exception e){}
			
			int skillWertNew = new Double(skillwert).intValue();
			int skillWertOld = new Double((skillwert - skillWertNew) * 100 + 0.1).intValue();
			int changeWert = skillWertNew - skillWertOld;
			
			Icon ii = HelperWrapper.instance().getImageIcon4Veraenderung(changeWert);
			label = new JLabel(""+skillWertNew,ii,SwingConstants.CENTER);
			label.setHorizontalTextPosition(SwingConstants.LEADING);
			if(table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("ER"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("FUE"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("FO"))
				)
			{
				label.setBackground(gruen);
			}
			else
			{
				label.setBackground(gelb);
			}
			
		}
		else if(table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("IVA"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("IVO"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("AVI"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("AVD"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("AVO"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("MITA"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("MITD"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("MITO"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("FLGI"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("FLGO"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("FLGD"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("STUD"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("TOR"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("IV"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("AV"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("MIT"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("FLG"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("STU"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("STUA"))
				
				)
		{
			int i = 0;
			float neuerWert = 0;
			String []skill = new String[2];
			float changeValue = 0;
			String chValue = "";
			
			StringTokenizer tk = new StringTokenizer(table.getValueAt(row,column).toString(),";");
			while (tk.hasMoreTokens()) {
		         skill[i]=tk.nextToken();
		         i++;
		     }
			try
			{
				neuerWert = Float.parseFloat(skill[0]);
				changeValue = HelperWrapper.instance().round(Float.parseFloat(skill[1]),UserParameter.instance().anzahlNachkommastellen);
			}
			catch(Exception exc){}
			
			wertAlt = new JLabel();
			
			if(changeValue > 0)
			{
				chValue += "+" + changeValue;
				wertAlt.setForeground(dklgruen);
				wertAlt.setText(chValue);
			}
			else if(changeValue == 0){
				wertAlt.setText("-");
			}
			else {
				chValue += Float.toString(changeValue);
				wertAlt.setForeground(dklrot);
				wertAlt.setText("" + changeValue);
			}
			
			wertNeu = new JLabel("" + neuerWert);
			wertNeu.setHorizontalAlignment(SwingConstants.RIGHT);
			wertAlt.setHorizontalAlignment(SwingConstants.CENTER);
			
			label.setLayout(new GridLayout());
			label.add(wertNeu);
			label.add(wertAlt);
			
			if(table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("TOR"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("IV"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("AV"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("MIT"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("FLG"))
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("STU"))
				)
			{
				label.setBackground(dunkelblau);
				wertNeu.setBackground(dunkelblau);
				wertAlt.setBackground(dunkelblau);
			}
			else
			{
				label.setBackground(hellblau);
				wertNeu.setBackground(hellblau);
				wertAlt.setBackground(hellblau);
			}
			
			label.validate();
		}
		else if(table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("Gehalt")))	{
			IXtraData extraData = HOVerwaltung.instance().getModel().getXtraDaten();
			String curr = "" + df.format(Double.parseDouble(value.toString())) + " " + extraData.getCurrencyName();
			label.setText(curr);
			
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setBackground(table.getBackground());
		}
		else if(table.getColumnName(column).equals("TSI")
				|| table.getColumnName(column).equals(HOVerwaltung.instance().getLanguageString("ID"))){
			label.setText(value.toString());
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setBackground(table.getBackground());
		}
		else if((table.getValueAt(0,column) instanceof Boolean) == false) {
			label.setText(value.toString());
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBackground(table.getBackground());
		}
		label.setOpaque(true);
		label.setFont(table.getFont());
		label.setForeground(table.getForeground());

		
		if(isSelected){
			label.setBackground(table.getSelectionBackground());
			label.setForeground(table.getSelectionForeground());
		} 
		if(table.getValueAt(row,0) == Boolean.TRUE)
		{
			label.setBackground(rot);
		}
			return label;
	}
}
