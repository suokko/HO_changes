package hoplugins.specialEvents;

import hoplugins.SpecialEvents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import plugins.IDebugWindow;
import plugins.IHOMiniModel;

public class FilterPanel extends JPanel
    implements ActionListener
{

	private static final long serialVersionUID = 3213362575191990865L;

	private class OptionFieldActionListener
        implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            SpecialEvents.newTableModel();
        }
    }

    public static final int SAISONALL = 1;
    public static final int SAISONLAST2 = 2;
    public static final int SAISONACT = 3;
    IHOMiniModel miniModel;
    Properties props;
    Object matches[][];
    private static ButtonGroup saisonGroup = new ButtonGroup();
    private static JRadioButton saisonAct;
    private static JRadioButton saisonLastTwo;
    private static JRadioButton saisonAll;
    private static ButtonGroup gameTypGroup = new ButtonGroup();
    private static JRadioButton gameTypAll;
    private static JRadioButton gameTypSE;
    private static JCheckBox friendlies;
    private static JCheckBox specialtySE;
    private static JCheckBox weatherSE;
    private static JCheckBox counter;
    private static JCheckBox freekick;
    private static JCheckBox penalty;
    private static JCheckBox ifk;
    private static JCheckBox longshot;

    public FilterPanel(IHOMiniModel arg0, Properties props)
    {
        this.props = null;
        initialize(arg0, props);
    }

    public static int getSaisonTyp()
    {
        int saison = SAISONALL;

        if(saisonAct.isSelected())
            saison = SAISONACT;
        else if(saisonAll.isSelected())
            saison = SAISONALL;
        else if(saisonLastTwo.isSelected())
            saison = SAISONLAST2;

        return saison;
    }

    public void initialize(IHOMiniModel arg0, Properties props)
    {
        this.props = props;
        try
        {
            miniModel = arg0;
            gameTypSE = new JRadioButton(props.getProperty("SpieleMitSEs"));
            gameTypSE.addActionListener(new OptionFieldActionListener());
            gameTypAll = new JRadioButton(props.getProperty("AlleSpiele"));
            gameTypAll.addActionListener(new OptionFieldActionListener());
            gameTypGroup.add(gameTypSE);
            gameTypGroup.add(gameTypAll);
            gameTypSE.setSelected(true);
            saisonAct = new JRadioButton(props.getProperty("AktSaison"));
            saisonAct.addActionListener(new OptionFieldActionListener());
            saisonLastTwo = new JRadioButton(props.getProperty("2Saison"));
            saisonLastTwo.addActionListener(new OptionFieldActionListener());
            saisonAll = new JRadioButton(props.getProperty("AllSaison"));
            saisonAll.addActionListener(new OptionFieldActionListener());
            saisonGroup.add(saisonAct);
            saisonGroup.add(saisonLastTwo);
            saisonGroup.add(saisonAll);
            saisonLastTwo.setSelected(true);
            friendlies = new JCheckBox(props.getProperty("FRIENDLIES"));
            friendlies.setSelected(true);
            friendlies.addActionListener(this);
            specialtySE = new JCheckBox(props.getProperty("SPECIALTYSE"));
            specialtySE.setSelected(true);
            specialtySE.addActionListener(this);
            weatherSE = new JCheckBox(props.getProperty("WEATHERSE"));
            weatherSE.setSelected(true);
            weatherSE.addActionListener(this);
            counter = new JCheckBox(props.getProperty("COUNTER"));
            counter.setSelected(true);
            counter.addActionListener(this);
            freekick = new JCheckBox(props.getProperty("FREEKICK"));
            freekick.setSelected(true);
            freekick.addActionListener(this);
            penalty = new JCheckBox(props.getProperty("PENALTY"));
            penalty.setSelected(true);
            penalty.addActionListener(this);
            ifk = new JCheckBox(props.getProperty("IFK"));
            ifk.setSelected(true);
            ifk.addActionListener(this);
            longshot = new JCheckBox(props.getProperty("LONGSHOT"));
            longshot.setSelected(true);
            longshot.addActionListener(this);

        	setLayout(new BorderLayout());
        	
        	JPanel filterTop = new JPanel();
        	
            filterTop.add(gameTypSE);
            filterTop.add(gameTypAll);
            filterTop.add(new JLabel("                           "));
            filterTop.add(saisonAct);
            filterTop.add(saisonLastTwo);
            filterTop.add(saisonAll);
            filterTop.add(new JLabel("                           "));
            filterTop.add(friendlies);

        	JPanel filterBottom = new JPanel();

        	filterBottom.add(specialtySE);
        	filterBottom.add(weatherSE);
        	filterBottom.add(counter);
        	filterBottom.add(freekick);
        	filterBottom.add(penalty);
        	filterBottom.add(ifk);
        	filterBottom.add(longshot);
        	
        	add (BorderLayout.NORTH, filterTop);
        	add (BorderLayout.SOUTH, filterBottom);
        }
        catch(Exception exr)
        {
//        	exr.printStackTrace();
            IDebugWindow debugWindow = miniModel.getGUI().createDebugWindow(new Point(100, 200), new Dimension(700, 400));
            debugWindow.setVisible(true);
            debugWindow.append(exr);
        }
    }

    public static JRadioButton getGameTypAll()
    {
        return gameTypAll;
    }

    public static JRadioButton getGameTypSE()
    {
        return gameTypSE;
    }

    public static JRadioButton getSaisonAct()
    {
        return saisonAct;
    }

    public static JRadioButton getSaisonLastTwo()
    {
        return saisonLastTwo;
    }

    public static JRadioButton getSaisonAll()
    {
        return saisonAll;
    }

//    public static JCheckBox getFriendlies()
//    {
//        return friendlies;
//    }
//
//    public static JCheckBox getSpecialtySE()
//    {
//        return specialtySE;
//    }
//
//    public static JCheckBox getWeatherSE()
//    {
//        return weatherSE;
//    }
//
//    public static JCheckBox getCounter()
//    {
//        return counter;
//    }

    public static boolean showFriendlies()
    {
        return friendlies.isSelected();
    }

    public static boolean showSpecialtySE()
    {
        return specialtySE.isSelected();
    }

    public static boolean showWeatherSE()
    {
        return weatherSE.isSelected();
    }

    public static boolean showCounter()
    {
        return counter.isSelected();
    }
    
    public static boolean showFreekick()
    {
        return freekick.isSelected();
    }
    
    public static boolean showPenalty()
    {
        return penalty.isSelected();
    }
    
    public static boolean showIFK()
    {
        return ifk.isSelected();
    }
    
    public static boolean showLongShot()
    {
        return longshot.isSelected();
    }    
    
    public void actionPerformed(ActionEvent actionevent)
    {
        SpecialEvents.newTableModel();
    }

}
