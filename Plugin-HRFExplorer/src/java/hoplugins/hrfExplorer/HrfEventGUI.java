/*
 * Created on 08.07.2005
 */
package hoplugins.hrfExplorer;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import plugins.IHOMiniModel;
/**
 * @author Robbi
 */
public class HrfEventGUI
{
	private int m_anzTage = 0;
	private Hashtable m_daten = new Hashtable();
	private IHOMiniModel m_model = null;
	private JPanel m_mainPanel = new JPanel();
	
	public HrfEventGUI(IHOMiniModel model,Hashtable daten, int tage)
	{
		m_anzTage = tage;
		m_daten = daten;
		m_model = model;
		
		GridBagLayout gbLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		m_mainPanel.setLayout(gbLayout);
		
		for(int jj = 0; jj < m_anzTage; jj++)
		{
			for(int ii = 0;ii < 24; ii++)
			{
			
			}
		}
	}
	
}
