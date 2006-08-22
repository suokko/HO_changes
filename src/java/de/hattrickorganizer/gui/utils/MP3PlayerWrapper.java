package de.hattrickorganizer.gui.utils;

import javazoom.jl.player.Player;
import plugins.IMP3Player;
import de.hattrickorganizer.tools.HOLogger;

public class MP3PlayerWrapper extends Thread implements IMP3Player{

	//Startet den Player
	Player m_clPlayer	=	null;
	String m_sFile		=	"";
	
	public void run()
	{
		if( ! m_sFile.equals( "" ) )
		{
			try
			{
		    	final java.net.URL resource = getClass().getClassLoader().getResource( m_sFile );
		    	m_clPlayer = new Player( resource.openStream() );
		    	m_clPlayer.play();
			}
			catch( Exception e )
			{
				HOLogger.instance().log(getClass(),e);
			}
		}
		else
		{
			System.err.println( "No soundfile set" );
		}
	}
	
	public void setMP3File( String file )
	{
		m_sFile	=	file;
	}
	
	public void close()
	{
		try
		{
			if( m_clPlayer != null )
			{
				m_clPlayer.close();
			}
		}
		catch( Exception e )
		{
			HOLogger.instance().log(getClass(),e);
		}
		
	}
}
