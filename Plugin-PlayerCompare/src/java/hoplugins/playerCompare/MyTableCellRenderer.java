/*
 * Created on 06.07.2004
 */
package hoplugins.playerCompare;

import hoplugins.*;
import hoplugins.playerCompare.*;
import plugins.*;
import java.awt.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import gui.UserParameter;

/**
 * @author KickMuck
 */
public class MyTableCellRenderer extends JLabel implements TableCellRenderer{
	
	//***** Klassenvariable für das HOMiniModel *****
	private IHOMiniModel m_IHOmodel;
//	***** Klassenvariable für das TableModel *****
	private PlayerTableModel plTableModel;
	private PlayerTableModel plTableModel1;
	//***** Festlegen der Farben *****
	private Color gelb = new Color(255,255,200);
	private Color gruen = new Color (220,255,220);
	private Color dklgruen = new Color (0,200,0);
	private Color rot = new Color (255,200,200);
	private Color dklrot = new Color (200,0,0);
	private Color hellblau = new Color (235,235,255);
	private Color dunkelblau = new Color (220,220,255);
	private Color grau = new Color (240,240,240);
	private Color gruppeA = new Color(50,200,50);
	private Color gruppeB = new Color(160,220,50);
	private Color gruppeC = new Color(240,240,0);
	private Color gruppeD = new Color(240,156,0);
	private Color gruppeE = new Color(230,83,0);
	//***** Anlegen der JLabel *****
	private JLabel label;
	private JLabel wertNeu;
	private JLabel wertAlt;
	//***** weitere Klassenvariablen *****
	private String[] name = new String[2];
	private int natWert;
	private Font f;
	private DecimalFormat df = new DecimalFormat("#,###,##0.00");
	private int shit = 0;
	private int zaehler = 0;
	
	//***** Konstruktor *****
	public MyTableCellRenderer(IHOMiniModel minimod,PlayerTableModel ptm)
	{
		m_IHOmodel = minimod;
		plTableModel = ptm;
	}
	
	public Component getTableCellRendererComponent(JTable table,
			Object value,
			boolean isSelected,
			boolean hasFocus,
            int row,
            int column)
	{
		//***** Festlegen des Fonts für die Gruppenanzeige
		f = new Font(table.getFont().getFontName(), Font.PLAIN, (table.getFont().getSize()) + 2);
		
		//***** Grafische Darstellung der Spalten ***** 
		if(table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("Name")))
		{	
			label = new JLabel();
			
			int i = 0;
			int spezWert = 0;
			StringTokenizer tk = new StringTokenizer(table.getValueAt(row,column).toString(),";");
			while (tk.hasMoreTokens()) {
		         name[i]=tk.nextToken();
		         i++;
		    }
			try
			{
				spezWert = Integer.parseInt(name[1]);
			}
			catch(Exception e){}
			
			Icon ic = m_IHOmodel.getHelper().getImageIcon4Spezialitaet(spezWert);
			label.setLayout(new BorderLayout());
			JLabel l1 = new JLabel(name[0]);
			JLabel l2 = new JLabel(ic,JLabel.LEFT);
			label.add(l1,BorderLayout.CENTER);
			label.add(l2,BorderLayout.EAST);
			label.setBackground(table.getBackground());
			label.validate();
		}
		else if(table.getColumnName(column).equals(PlayerCompare.getPCProperties("original")))
		{
			//PlayerCompare.appendText("Spalte: " + table.getColumnName(column));
			label = new JLabel();
			label.setText(value.toString());
			label.setHorizontalAlignment(CENTER);
			label.setBackground(dunkelblau);
		}
		else if(table.getColumnName(column).equals(PlayerCompare.getPCProperties("position")))
		{
			//PlayerCompare.appendText("Spalte: " + table.getColumnName(column));
			label = new JLabel();
			label.setText(value.toString());
			label.setHorizontalAlignment(CENTER);
			label.setBackground(hellblau);
		}
		else if(table.getColumnName(column).equals(PlayerCompare.getPCProperties("geaendert")))
		{
			//PlayerCompare.appendText("Spalte: " + table.getColumnName(column));
			label = new JLabel();
			label.setText(value.toString());
			label.setHorizontalAlignment(CENTER);
			label.setBackground(dunkelblau);
		}
		else if(column == 2)
		{
			label = new JLabel();
			
			natWert = ((Integer)table.getValueAt(row,column)).intValue();
			label.setIcon(m_IHOmodel.getHelper().getImageIcon4Country(natWert));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBackground(table.getBackground());
		}
		else if(table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("Gruppe")))
		{
			label = new JLabel();
			
			String group = ((String)table.getValueAt(row,column)).toString();
			if(group.equals("A-Team.png"))
			{
				label.setLayout(new BorderLayout());
				label.setBackground(table.getBackground());
				label.setHorizontalAlignment(SwingConstants.CENTER);
				
				JLabel aLabel = new JLabel("A",SwingConstants.CENTER);
				aLabel.setPreferredSize(new Dimension(15,15));
				aLabel.setBackground(gruppeA);
				aLabel.setOpaque(true);
				aLabel.setBorder(new LineBorder(Color.BLACK,1));
				aLabel.setFont(f);
				label.add(aLabel,BorderLayout.WEST);
			}
			else if(group.equals("B-Team.png"))
			{
				label.setLayout(new BorderLayout());
				label.setBackground(table.getBackground());
				label.setHorizontalAlignment(SwingConstants.CENTER);
				
				JLabel aLabel = new JLabel("B",SwingConstants.CENTER);
				aLabel.setPreferredSize(new Dimension(15,15));
				aLabel.setBackground(gruppeB);
				aLabel.setOpaque(true);
				aLabel.setBorder(new LineBorder(Color.BLACK,1));
				aLabel.setFont(f);
				label.add(aLabel,BorderLayout.WEST);
			}
			else if(group.equals("C-Team.png"))
			{
				label.setLayout(new BorderLayout());
				label.setBackground(table.getBackground());
				label.setHorizontalAlignment(SwingConstants.CENTER);
				
				JLabel aLabel = new JLabel("C",SwingConstants.CENTER);
				aLabel.setPreferredSize(new Dimension(15,15));
				aLabel.setBackground(gruppeC);
				aLabel.setOpaque(true);
				aLabel.setBorder(new LineBorder(Color.BLACK,1));
				aLabel.setFont(f);
				label.add(aLabel,BorderLayout.WEST);
			}
			else if(group.equals("D-Team.png"))
			{
				label.setLayout(new BorderLayout());
				label.setBackground(table.getBackground());
				label.setHorizontalAlignment(SwingConstants.CENTER);
				
				JLabel aLabel = new JLabel("D",SwingConstants.CENTER);
				aLabel.setPreferredSize(new Dimension(15,15));
				aLabel.setBackground(gruppeD);
				aLabel.setOpaque(true);
				aLabel.setBorder(new LineBorder(Color.BLACK,1));
				aLabel.setFont(f);
				label.add(aLabel,BorderLayout.WEST);
			}
			else if(group.equals("E-Team.png"))
			{
				label.setLayout(new BorderLayout());
				label.setBackground(table.getBackground());
				label.setHorizontalAlignment(SwingConstants.CENTER);
				
				JLabel aLabel = new JLabel("E",SwingConstants.CENTER);
				aLabel.setPreferredSize(new Dimension(15,15));
				aLabel.setBackground(gruppeE);
				aLabel.setOpaque(true);
				aLabel.setBorder(new LineBorder(Color.BLACK,1));
				aLabel.setFont(f);
				label.add(aLabel,BorderLayout.WEST);
			}
			else
			{
				label.setBackground(table.getBackground());
			}
			
		}
		else if(table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("BestePosition")))
		{
			label = new JLabel();
			
			byte tmpPos = ((Float)table.getValueAt(row,column)).byteValue();
			float tmpFloat = m_IHOmodel.getHelper().round((((Float)table.getValueAt(row,column)).floatValue() - tmpPos)*100,m_IHOmodel.getUserSettings().anzahlNachkommastellen);
			label.setText(m_IHOmodel.getHelper().getNameForPosition(tmpPos) + " ("+ tmpFloat +")");
			label.setBackground(table.getBackground());
		}
		
		else if(table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("ER"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("FUE"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("FO"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("KO"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("TW"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("VE"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("SA"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("PS"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("FL"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("TS"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("ST"))
				)
		{
			int i = 0;
			double skillwert = 0;
			String skillwertS = "";
			try
			{
				skillwertS = (table.getValueAt(row,column)).toString();
			}
			catch(Exception e){}
			
			try
			{
				skillwert = Double.parseDouble(skillwertS);
			}
			catch(Exception e){}
			
			
			int skillWertNew = new Double(skillwert).intValue();
			int skillWertOld = new Double((skillwert - skillWertNew) * 100 + 0.1).intValue();
			int changeWert = skillWertNew - skillWertOld;
			
			Icon ii = m_IHOmodel.getHelper().getImageIcon4Veraenderung(changeWert);
			label = new JLabel(""+skillWertNew,ii,JLabel.CENTER);
			label.setHorizontalTextPosition(JLabel.LEADING);
			if(table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("ER"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("FUE"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("FO"))
				)
			{
				label.setBackground(gruen);
			}
			else
			{
				label.setBackground(gelb);
			}
			
		}
		else if(table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("IVA"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("IVO"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("AVI"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("AVD"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("AVO"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("MITA"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("MITD"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("MITO"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("FLGI"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("FLGO"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("FLGD"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("STUD"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("TOR"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("IV"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("AV"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("MIT"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("FLG"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("STU"))
				)
		{
			int i = 0;
			float neuerWert = 0;
			float alterWert = 0;
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
			else if(changeValue == 0)
			{
				wertAlt.setText("-");
			}
			else
			{
				chValue += Float.toString(changeValue);
				wertAlt.setForeground(dklrot);
				wertAlt.setText("" + changeValue);
			}
			
			wertNeu = new JLabel("" + neuerWert);
			wertNeu.setHorizontalAlignment(SwingConstants.RIGHT);
			wertAlt.setHorizontalAlignment(SwingConstants.CENTER);
			
			label = new JLabel();
			label.setLayout(new GridLayout());
			label.add(wertNeu);
			label.add(wertAlt);
			
			if(table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("TOR"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("IV"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("AV"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("MIT"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("FLG"))
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("STU"))
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
		else if(table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("Gehalt")))
		{
			label = new JLabel();
			IXtraData extraData = m_IHOmodel.getXtraDaten();
			String curr = "" + df.format(Double.parseDouble(value.toString())) + " " + extraData.getCurrencyName();
			label.setText(curr);
			
			label.setHorizontalAlignment(JLabel.RIGHT);
			label.setBackground(table.getBackground());
		}
		else if(table.getColumnName(column).equals("TSI")
				|| table.getColumnName(column).equals(m_IHOmodel.getResource().getProperty("ID"))
				)
		{
			label = new JLabel();
			label.setText(value.toString());
			label.setHorizontalAlignment(JLabel.RIGHT);
			label.setBackground(table.getBackground());
		}
		else if((table.getValueAt(0,column) instanceof Boolean) == false)
		//else
		{
			label = new JLabel();
			label.setText(value.toString());
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setBackground(table.getBackground());
		}
		label.setOpaque(true);
		label.setFont(table.getFont());
		label.setForeground(table.getForeground());
		
		
		if(isSelected)
		{
			label.setBackground(grau);
		}
		else
		{
			
		}
		if(table.getValueAt(row,0) == Boolean.TRUE)
		{
			label.setBackground(rot);
		}
			return label;
	}
}
