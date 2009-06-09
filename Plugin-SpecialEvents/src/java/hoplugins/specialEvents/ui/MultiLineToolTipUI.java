package hoplugins.specialEvents.ui;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalToolTipUI;

class MultiLineToolTipUI extends MetalToolTipUI
{

    private String strs[];
/*    private int maxWidth;

    MultiLineToolTipUI()
    {
        maxWidth = 0;
    }*/

    @Override
	public void paint(Graphics g, JComponent c)
    {
        FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(g.getFont());
        Dimension size = c.getSize();
        g.setColor(c.getBackground());
        g.fillRect(0, 0, size.width, size.height);
        g.setColor(c.getForeground());
        if(strs != null)
        {
            for(int i = 0; i < strs.length; i++)
            {
                g.drawString(strs[i], 3, metrics.getHeight() * (i + 1));
            }

        }
    }

    @Override
	public Dimension getPreferredSize(JComponent c)
    {
        FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(c.getFont());
        String tipText = ((JToolTip)c).getTipText();
        if(tipText == null)
        {
            tipText = "";
        }
        BufferedReader br = new BufferedReader(new StringReader(tipText));
        int maxWidth = 0;
        Vector<String> v = new Vector<String>();
        String line;
        try
        {
            while((line = br.readLine()) != null) 
            {
                int width = SwingUtilities.computeStringWidth(metrics, line);
                maxWidth = maxWidth >= width ? maxWidth : width;
                v.addElement(line);
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        int lines = v.size();
        if(lines < 1)
        {
            strs = null;
            lines = 1;
        } else
        {
            strs = new String[lines];
            int i = 0;
            for(Enumeration<String> e = v.elements(); e.hasMoreElements();)
            {
                strs[i] = e.nextElement();
                i++;
            }

        }
        int height = metrics.getHeight() * lines;
        //this.maxWidth = maxWidth;
        return new Dimension(maxWidth + 6, height + 4);
    }
}
