/*
 * Created on 06.07.2004
 */
package hoplugins.playerCompare;

import gui.HOColorName;
import gui.HOIconName;

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

import plugins.IHOMiniModel;
import plugins.IXtraData;

/**
 * @author KickMuck
 */
public class MyTableCellRenderer  implements TableCellRenderer{

	private static final long serialVersionUID = -6249868492929346343L;
	private IHOMiniModel m_IHOmodel;
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
	public MyTableCellRenderer(IHOMiniModel minimod)
	{
		m_IHOmodel = minimod;
		gelb = minimod.getGUI().getColor(HOColorName.PLAYER_SKILL_BG);
		gruen= minimod.getGUI().getColor(HOColorName.PLAYER_SKILL_SPECIAL_BG);
		hellblau = minimod.getGUI().getColor(HOColorName.PLAYER_SUBPOS_BG);
		dunkelblau = minimod.getGUI().getColor(HOColorName.PLAYER_POS_BG);
	}
	
	public Component getTableCellRendererComponent(JTable table,
			Object value,
			boolean isSelected,
			boolean hasFocus,
            int row,
            int column)
	{
		label = new JLabel();

		if(table.getColumnName(column).equals(m_IHOmodel.getLanguageString("Name"))) {	
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
			
			Icon ic = m_IHOmodel.getHelper().getImageIcon4Spezialitaet(spezWert);
			label.setLayout(new BorderLayout());
			label.setText(name[0]);
			JLabel l2 = new JLabel(ic,SwingConstants.LEFT);
			label.add(l2,BorderLayout.EAST);
			label.setBackground(table.getBackground());
			label.validate();
		}
		else if(column == 2) {
			natWert = ((Integer)table.getValueAt(row,column)).intValue();
			label.setIcon(m_IHOmodel.getHelper().getImageIcon4Country(natWert));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBackground(table.getBackground());
		}
		else if(table.getColumnName(column).equals(m_IHOmodel.getLanguageString("Gruppe")))	{

			
			String group = ((String)table.getValueAt(row,column)).toString();
			if(group != null && group.length() > 3)
				label.setIcon(m_IHOmodel.getHelper().getImageIcon(group));
			label.setBackground(table.getBackground());
		} else if(table.getColumnName(column).equals(m_IHOmodel.getLanguageString("BestePosition")))		{
			byte tmpPos = ((Float)table.getValueAt(row,column)).byteValue();
			float tmpFloat = m_IHOmodel.getHelper().round((((Float)table.getValueAt(row,column)).floatValue() - tmpPos)*100,m_IHOmodel.getUserSettings().anzahlNachkommastellen);
			label.setText(m_IHOmodel.getHelper().getNameForPosition(tmpPos) + " ("+ tmpFloat +")");
			label.setBackground(table.getBackground());
		} else if(table.getColumnName(column).equals(m_IHOmodel.getLanguageString("MC"))) {
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
				label.setIcon(m_IHOmodel.getHelper().getImageIcon(HOIconName.HOMEGROWN));
				label.setHorizontalAlignment(SwingConstants.CENTER);
			}
			if (changeWert < 0)
				label.setBackground(Color.RED);
			else if (changeWert > 0)
				label.setBackground(Color.GREEN);
			else
				label.setBackground(table.getBackground());
		}
		else if(table.getColumnName(column).equals(m_IHOmodel.getLanguageString("ER"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("FUE"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("FO"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("KO"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("TW"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("VE"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("SA"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("PS"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("FL"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("TS"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("ST"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("LOY"))
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
			
			Icon ii = m_IHOmodel.getHelper().getImageIcon4Veraenderung(changeWert);
			label = new JLabel(""+skillWertNew,ii,SwingConstants.CENTER);
			label.setHorizontalTextPosition(SwingConstants.LEADING);
			if(table.getColumnName(column).equals(m_IHOmodel.getLanguageString("ER"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("FUE"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("FO"))
				)
			{
				label.setBackground(gruen);
			}
			else
			{
				label.setBackground(gelb);
			}
			
		}
		else if(table.getColumnName(column).equals(m_IHOmodel.getLanguageString("IVA"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("IVO"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("AVI"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("AVD"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("AVO"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("MITA"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("MITD"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("MITO"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("FLGI"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("FLGO"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("FLGD"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("STUD"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("TOR"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("IV"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("AV"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("MIT"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("FLG"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("STU"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("STUA"))
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
				changeValue = m_IHOmodel.getHelper().round(Float.parseFloat(skill[1]),m_IHOmodel.getUserSettings().anzahlNachkommastellen);
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
			
			if(table.getColumnName(column).equals(m_IHOmodel.getLanguageString("TOR"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("IV"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("AV"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("MIT"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("FLG"))
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("STU"))
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
		else if(table.getColumnName(column).equals(m_IHOmodel.getLanguageString("Gehalt")))	{
			IXtraData extraData = m_IHOmodel.getXtraDaten();
			String curr = "" + df.format(Double.parseDouble(value.toString())) + " " + extraData.getCurrencyName();
			label.setText(curr);
			
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setBackground(table.getBackground());
		}
		else if(table.getColumnName(column).equals("TSI")
				|| table.getColumnName(column).equals(m_IHOmodel.getLanguageString("ID"))){
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
