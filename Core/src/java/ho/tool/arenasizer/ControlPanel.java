package ho.tool.arenasizer;

import gui.HOColorName;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HOLogger;

class ControlPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static final BigDecimal HUNDRED = new BigDecimal(100);
	private JButton m_jbUbernehmenGesamt = new JButton(HOVerwaltung.instance().getLanguageString("Calculate"));
	private JTextField m_jtfFans = new JTextField(7);
	private JTextField m_jtfGesamtgroesse = new JTextField(7);
	
	private JTextField vipField = new JTextField(7);
	private JTextField basicField = new JTextField(7);//Basic
	private JTextField terracesField = new JTextField(7);//Terraces
	private JTextField roofField = new JTextField(7);//Roof
	
	private JFormattedTextField vipPercentField = new JFormattedTextField( new DecimalFormat  ( "###.# %" ));
	private JFormattedTextField basicPercentField = new JFormattedTextField( new DecimalFormat  ( "###.# %" ));
	private JFormattedTextField terracesPercentField = new JFormattedTextField( new DecimalFormat  ( "###.# %" ));
	private JFormattedTextField roofPercentField = new JFormattedTextField( new DecimalFormat  ( "###.# %" ));
	
	private JFormattedTextField factorMaxField = new JFormattedTextField( new DecimalFormat  ( "##" ));
	private JFormattedTextField factorNormalField = new JFormattedTextField( new DecimalFormat  ( "##" ));
	private JFormattedTextField factorMinField = new JFormattedTextField( new DecimalFormat  ( "##" ));
    final GridBagLayout layout2 = new GridBagLayout();
    final GridBagConstraints constraints2 = new GridBagConstraints();
	
	ControlPanel(){
		initialize();
	}

	private void initialize() {
		constraints2.fill = GridBagConstraints.WEST;
        constraints2.weightx = 0.0;
        constraints2.weighty = 0.0;
        constraints2.insets = new Insets(3, 3, 3, 3);
        
        setLayout(layout2);
        JLabel label;

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Gesamtgroesse"));
        addToLayout(label,0,0);
        setFieldProperties(m_jtfGesamtgroesse);
       // m_jtfGesamtgroesse.addFocusListener(this);
        addToLayout(m_jtfGesamtgroesse, 1, 0);
        
        m_jbUbernehmenGesamt.addActionListener(this);
        addToLayout (m_jbUbernehmenGesamt,2,0);
        
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Stehplaetze"));
        addToLayout(label,3,0);
        setFieldProperties(terracesPercentField);
        addToLayout(terracesPercentField,4,0);
        
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Sitzplaetze"));
        addToLayout(label,5,0);
        setFieldProperties(basicPercentField);
        addToLayout(basicPercentField,6,0);
        
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Ueberdachteplaetze"));
        addToLayout(label,7,0);
        setFieldProperties(roofPercentField);
        addToLayout(roofPercentField,8,0);
        
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Logen"));
        addToLayout(label,9,0);
        setFieldProperties(vipPercentField);
        addToLayout(vipPercentField,10,0);
        
        
        
        terracesField.setHorizontalAlignment(SwingConstants.RIGHT);
        addToLayout(terracesField,4,1);

        basicField.setHorizontalAlignment(SwingConstants.RIGHT);
        addToLayout(basicField,6,1);

        roofField.setHorizontalAlignment(SwingConstants.RIGHT);
        addToLayout(roofField,8,1);

        vipField.setHorizontalAlignment(SwingConstants.RIGHT);
        addToLayout(vipField,10,1);


        
        
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Fans"));
        addToLayout(label,0,2);

        m_jtfFans.setHorizontalAlignment(SwingConstants.RIGHT);
        addToLayout(m_jtfFans,1,2);
        
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Maximal"));
        addToLayout(label,3,2);
        setFieldProperties(factorMaxField);
        addToLayout(factorMaxField,4,2);
        
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Durchschnitt"));
        addToLayout(label,5,2);
        setFieldProperties(factorNormalField);
        addToLayout(factorNormalField,6,2);
        
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Minimal"));
        addToLayout(label,7,2);
        setFieldProperties(factorMinField);
        addToLayout(factorMinField,8,2);
        
       
        Dimension dim = vipField.getPreferredSize();
        vipPercentField.setPreferredSize(dim);
        basicPercentField.setPreferredSize(dim);
        terracesPercentField.setPreferredSize(dim);
        roofPercentField.setPreferredSize(dim);
        
        factorMaxField.setPreferredSize(dim);
        factorNormalField.setPreferredSize(dim);
        factorMinField.setPreferredSize(dim);
        
        initStadium();
	}
	
	private void addToLayout(JComponent c, int x, int y){
		constraints2.gridx = x;
        constraints2.gridy = y;
        layout2.setConstraints(c, constraints2);
        add(c);
	}
	
	private void setFieldProperties(JTextField txt){
		txt.setHorizontalAlignment(SwingConstants.RIGHT);
		vipPercentField.setForeground(ThemeManager.getColor(HOColorName.LABEL_FG));
	}
	
    //Init aus dem HRF
    private void initStadium() {
        //Nur, wenn es eine HRFArena ist
        Stadium m_clStadium = HOVerwaltung.instance().getModel().getStadium();
        int fans = HOVerwaltung.instance().getModel().getVerein().getFans();
        
        m_jtfFans.setText( fans + "");
        terracesField.setText(m_clStadium.getStehplaetze() + "");
        basicField.setText(m_clStadium.getSitzplaetze() + "");
        roofField.setText(m_clStadium.getUeberdachteSitzplaetze() + "");
        vipField.setText(m_clStadium.getLogen() + "");
        m_jtfGesamtgroesse.setText(m_clStadium.getGesamtgroesse() + "");
        
        terracesPercentField.setValue(ArenaSizer.TERRACES_PERCENT);
        basicPercentField.setValue(ArenaSizer.BASICS_PERCENT);
        roofPercentField.setValue(ArenaSizer.ROOF_PERCENT);
        vipPercentField.setValue(ArenaSizer.VIP_PERCENT);
        
        factorMaxField.setValue(ArenaSizer.SUPPORTER_MAX);
    	factorNormalField.setValue(ArenaSizer.SUPPORTER_NORMAL);
    	factorMinField.setValue(ArenaSizer.SUPPORTER_MIN);
        
    }
    
    Stadium getStadium(){
        int fans = 0;
        int steh = 0;
        int sitz = 0;
        int ueber = 0;
        int loge = 0;

        try {
            fans = Integer.parseInt(m_jtfFans.getText());
            steh = Integer.parseInt(terracesField.getText());
            sitz = Integer.parseInt(basicField.getText());
            ueber = Integer.parseInt(roofField.getText());
            loge = Integer.parseInt(vipField.getText());
        } catch (NumberFormatException e) {
            HOLogger.instance().log(getClass(),"Fehler: keine Zahl");
        }

        final Stadium stadium = new Stadium();
        stadium.setStehplaetze(steh);
        stadium.setSitzplaetze(sitz);
        stadium.setUeberdachteSitzplaetze(ueber);
        stadium.setLogen(loge);

        m_jtfGesamtgroesse.setText(stadium.getGesamtgroesse() + "");
        return stadium;
    }
    
    int getSupporter(){
    	return Integer.parseInt(m_jtfFans.getText()); 
    }
    
    int[] getModifiedSupporter(){
    	int[] supporter = new int[3];
    	supporter[0] =  ((Number)factorMaxField.getValue()).intValue() * getSupporter();
    	supporter[1] =  ((Number)factorMaxField.getValue()).intValue() * getSupporter();
    	supporter[2] =  ((Number)factorMaxField.getValue()).intValue() * getSupporter();
    	return supporter;
    }
    
    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
            BigDecimal groesse = BigDecimal.ZERO;;

            try {
                groesse = new BigDecimal(Integer.parseInt(m_jtfGesamtgroesse.getText()));
            } catch (NumberFormatException e) {
                HOLogger.instance().log(getClass(),"Fehler: keine Zahl");
            }
            BigDecimal tPercent = new BigDecimal(terracesPercentField.getValue().toString());
            BigDecimal bPercent = new BigDecimal(basicPercentField.getValue().toString());
            BigDecimal rPercent = new BigDecimal(roofPercentField.getValue().toString());
            BigDecimal vPercent = new BigDecimal(vipPercentField.getValue().toString());
            BigDecimal sum = tPercent.add(bPercent).add(rPercent).add(vPercent);
            if(sum.multiply(HUNDRED).compareTo(HUNDRED) != 0){
            	JOptionPane.showMessageDialog(getTopLevelAncestor(), sum.multiply(HUNDRED).setScale(1)+" % <> "+HUNDRED+" %", HOVerwaltung.instance().getLanguageString("Fehler"), JOptionPane.ERROR_MESSAGE);
            	terracesPercentField.setForeground(ThemeManager.getColor(HOColorName.LABEL_ERROR_FG));
            	basicPercentField.setForeground(ThemeManager.getColor(HOColorName.LABEL_ERROR_FG));
            	roofPercentField.setForeground(ThemeManager.getColor(HOColorName.LABEL_ERROR_FG));
            	vipPercentField.setForeground(ThemeManager.getColor(HOColorName.LABEL_ERROR_FG));
            } else {
            	terracesPercentField.setForeground(ThemeManager.getColor(HOColorName.LABEL_FG));
            	basicPercentField.setForeground(ThemeManager.getColor(HOColorName.LABEL_FG));
            	roofPercentField.setForeground(ThemeManager.getColor(HOColorName.LABEL_FG));
            	vipPercentField.setForeground(ThemeManager.getColor(HOColorName.LABEL_FG));
            	ArenaSizer m_clArenaSizer = new ArenaSizer();
            
            	terracesField.setText(tPercent.multiply(groesse).intValue()+"");
            	basicField.setText(bPercent.multiply(groesse).intValue()+"");
            	roofField.setText(rPercent.multiply(groesse).intValue()+"");
            	vipField.setText(vPercent.multiply(groesse).intValue()+"");
            	//In Tabelle übernehmen
            	//m_jbUbernehmen.doClick();
            //}
        }
    }
    
}
