/*
 * Created on 5-nov-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.hattrickorganizer.net;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.hattrickorganizer.model.HOMiniModel;
import de.hattrickorganizer.model.News;

/**
 * @author Mirtillo
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NewsPanel extends JPanel {
	
	private JLabel header = new JLabel("",JLabel.LEFT); 
	private JButton b3 = new JButton("");
	private boolean linkEnabled = false;

	public NewsPanel(News news) {
		super();
		jbInit(news);
	}

	private void jbInit(News news) {
		linkEnabled = false;
		if ((news.getLink() != null) && (news.getLink().length() > 1)) {
			linkEnabled = true;
		}
		
		switch (news.getType()) {
			case News.PLUGIN :
				{
					header.setText("Plugin Update");					
					break;
				}
			case News.MESSAGE :
				{
					header.setText("News from HO-Team");
					break;
				}
		}
		
		int dim = 1 + news.getMessages().size();
		if (linkEnabled) {
			dim++;
		}		
		setLayout(new GridLayout(dim, 1));
		add(header);
		for (int i = 0; i < news.getMessages().size(); i++) {
			JLabel l = new JLabel(""+news.getMessages().get(i),JLabel.LEFT);
			add(l);
		}
		if (linkEnabled) {
			b3.setText(news.getLink());
			add(b3);			
		}
		
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (linkEnabled) {
					HOMiniModel.instance().getHelper().openUrlInUserBRowser(b3.getText());
				}
			}
		});
	}
}
