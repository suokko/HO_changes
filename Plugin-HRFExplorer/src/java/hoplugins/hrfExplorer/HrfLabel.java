/*
 * Created on 05.06.2005
 */
package hoplugins.hrfExplorer;

import plugins.IHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author KickMuck
 */

public class HrfLabel extends JLabel
{
	private int m_width;
	private int m_height;
	private Color m_bgColor;
	private Color m_fgColor;
	private Color gruen = new Color(220,255,220);
	
	public HrfLabel(ImageIcon bild,IHelper helper)
	{
		Image tmp = helper.makeColorTransparent(bild.getImage(),gruen);
		setIcon(new ImageIcon(tmp));
	}
	
	public HrfLabel(String text, int hoehe)
	{
		super(text);
		setHeight(hoehe);
		setOpaque(true);
	}
	
	public HrfLabel(String text, int breite, int hoehe)
	{
		super(text,JLabel.CENTER);
		setWidth(breite);
		setHeight(hoehe);
		setOpaque(true);
	}
	
	public HrfLabel(String text, int breite, int hoehe, int position)
	{
		super(text,position);
		setWidth(breite);
		setHeight(hoehe);
		setOpaque(true);
	}
	
	public HrfLabel(String text, int breite, int hoehe, int position, Color bg)
	{
		super(text,position);
		setWidth(breite);
		setHeight(hoehe);
		setBackground(bg);
		setOpaque(true);
	}
	
	public HrfLabel(String text, ImageIcon bild, int position, Color bg, int anordnung)
	{
		super(text,bild,position);
		setBackground(bg);
		setHorizontalTextPosition(anordnung);
		setOpaque(true);
	}
	
	public Dimension getPreferredSize()
    {
        return new Dimension(getWidth(),getHeight());
    }
	
	public Insets getInsets()
	{
		return new Insets(0,8,0,8);
	}
	
	/**
	 * @return Returns the m_bgColor.
	 */
	public Color getBgColor()
	{
		return m_bgColor;
	}
	
	/**
	 * @return Returns the m_fgColor.
	 */
	public Color getFgColor()
	{
		return m_fgColor;
	}
	/**
	 * @return Returns the m_height.
	 */
	public int getHeight()
	{
		return m_height;
	}
	/**
	 * @return Returns the m_width.
	 */
	public int getWidth()
	{
		return m_width;
	}
	/**
	 * @param color The m_bgColor to set.
	 */
	public void setBgColor(Color color)
	{
		m_bgColor = color;
	}
	/**
	 * @param color The m_fgColor to set.
	 */
	public void setFgColor(Color color)
	{
		m_fgColor = color;
	}
	/**
	 * @param m_height The m_height to set.
	 */
	public void setHeight(int m_height)
	{
		this.m_height = m_height;
	}
	/**
	 * @param m_width The m_width to set.
	 */
	public void setWidth(int m_width)
	{
		this.m_width = m_width;
	}
}
