package de.hattrickorganizer;

//DOM
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import de.hattrickorganizer.logik.xml.XMLNewsParser;
import de.hattrickorganizer.model.News;
import de.hattrickorganizer.net.NewsPanel;

/**
 * Main XML Parser Test Classes
 * 
 * @author draghetto
 */
public class NewsTest {
	//~ Constructors
	// -------------------------------------------------------------------------------

	//~ Methods
	// ------------------------------------------------------------------------------------

	public static void main(String[] args) {
		testNews();
	}

	private static void testNews() {
		final News news = new XMLNewsParser().parseNews(new File("news.xml"));
		showNews(news);
	}

	private static void showNews(News news) {
		JFrame f = new JFrame();
		f.setVisible(true);
		switch (news.getType()) {

			case News.PLUGIN :
				{
					JOptionPane.showMessageDialog(f, new NewsPanel(news), "Plugin News", JOptionPane.INFORMATION_MESSAGE);
					break;
				}
			case News.MESSAGE :
				{
					JOptionPane.showMessageDialog(f, new NewsPanel(news), "HO News", JOptionPane.INFORMATION_MESSAGE);
					break;
				}
			default :
				{
					JOptionPane.showMessageDialog(f, new JLabel("Unsupported"), "Error", JOptionPane.INFORMATION_MESSAGE);
				}
		}

	}
}