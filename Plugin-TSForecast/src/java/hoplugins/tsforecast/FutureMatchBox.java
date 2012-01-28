package hoplugins.tsforecast;

/*
 * FutureMatchBox.java
 *
 * Created on 25.March 2006, 22:04
 *
 *Version 0.11
 *history :
 *25.03.06  Version 0.1
 *26.08.06  Version 0.11 rebuilt
 *21.02.07  Version 0.2  added tooltip
 */

/**
 *
 * @author  michael.roux
 */

import hoplugins.TSForecast;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import plugins.IHOMiniModel;
import plugins.IMatchDetails;
import plugins.IHelper;

public final class FutureMatchBox extends JPanel {

  private JRadioButton m_rbPIC = null;
  private JRadioButton m_rbNORM = null;
  private JRadioButton m_rbMOTS = null;

  public FutureMatchBox( String text, String tooltip, int iCmd, int iSelected, int iType) {
    m_rbPIC = new JRadioButton();
    m_rbPIC.setActionCommand( "P" + iCmd);
    m_rbPIC.setToolTipText( TSForecast.m_clModel.getResource().getProperty("PIC", "PIC"));
    
    m_rbNORM = new JRadioButton();
    m_rbNORM.setActionCommand( "N" + iCmd);
    m_rbNORM.setToolTipText( TSForecast.m_clModel.getResource().getProperty("Normal", "Normal"));
    
    m_rbMOTS = new JRadioButton();
    m_rbMOTS.setActionCommand( "M" + iCmd);
    m_rbMOTS.setToolTipText(TSForecast.m_clModel.getResource().getProperty("MOTS", "MOTS"));
    
    ButtonGroup buttongroup = new ButtonGroup();
    buttongroup.add(m_rbPIC);
    buttongroup.add(m_rbNORM);
    buttongroup.add(m_rbMOTS);
    setSelected( iSelected);
    
    GridBagLayout gridbaglayout = new GridBagLayout();
    GridBagConstraints gridbagconstraints = new GridBagConstraints();
    setLayout( gridbaglayout);
    gridbagconstraints.fill = GridBagConstraints.HORIZONTAL;

    gridbagconstraints.gridx = 0;
    add(m_rbPIC, gridbagconstraints);

    gridbagconstraints.gridx = 1;
    add(m_rbNORM, gridbagconstraints);

    gridbagconstraints.gridx = 2;
    add(m_rbMOTS, gridbagconstraints);

    gridbagconstraints.gridx = 3;
    JLabel lIcon = new JLabel( TSForecast.m_clModel.getHelper().getImageIcon4Spieltyp( iType));
    lIcon.setToolTipText( TSForecast.m_clModel.getHelper().getNameForMatchTyp( iType));
    add( lIcon, gridbagconstraints );

    gridbagconstraints.gridx = 4;
    JLabel lText = new JLabel( "  " + text + " ", SwingConstants.LEFT);
    lText.setToolTipText( tooltip);
    add( lText, gridbagconstraints);
  }

  public final int isSelected() {
    if(m_rbMOTS.isSelected())
      return IMatchDetails.EINSTELLUNG_MOTS;
    else if( m_rbPIC.isSelected())
      return IMatchDetails.EINSTELLUNG_PIC;
    return IMatchDetails.EINSTELLUNG_NORMAL;
  }

  public final void setSelected(int i) {
    switch(i) {
      case IMatchDetails.EINSTELLUNG_MOTS:
        m_rbMOTS.setSelected(true);
        break;
      case IMatchDetails.EINSTELLUNG_PIC: 
        m_rbPIC.setSelected(true);
        break;
      case IMatchDetails.EINSTELLUNG_NORMAL: // '\0'
      default:
        m_rbNORM.setSelected(true);
        break;
    }
  }

  public final void addActionListener(ActionListener actionlistener) {
    m_rbPIC.addActionListener( actionlistener);
    m_rbNORM.addActionListener( actionlistener);
    m_rbMOTS.addActionListener( actionlistener);
  }

}