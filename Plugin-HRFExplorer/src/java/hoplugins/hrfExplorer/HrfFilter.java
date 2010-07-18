/*
 * Created on 15.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package hoplugins.hrfExplorer;

import java.io.*;

/**
 * @author KickMuck
 */
public class HrfFilter extends javax.swing.filechooser.FileFilter
{
	public boolean accept(File f)
	{
		boolean accept = f.isDirectory();
		if( ! accept)
		{
			String suffix = getSuffix(f);
			if(suffix != null)
			{
				accept = suffix.equals("hrf");
			}
		}
		return accept;
	}
	
	public String getDescription()
	{
		return "HattrickRessourceFiles (*.hrf)";
	}
	
	private String getSuffix(File f)
	{
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf(".");
		
		if(i > 0 && i < s.length() -1)
		{
			suffix = s.substring(i + 1).toLowerCase();
		}
		return suffix;
	}
}
