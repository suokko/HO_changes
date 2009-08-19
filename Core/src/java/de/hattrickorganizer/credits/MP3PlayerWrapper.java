package de.hattrickorganizer.credits;

import javazoom.jl.player.Player;
import de.hattrickorganizer.tools.HOLogger;

public class MP3PlayerWrapper extends Thread{

	//Startet den Player
	Player m_clPlayer	=	null;
	String m_sFile		=	"";
	
	@Override
	public void run()
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
	
	public void setMP3File( String file )
	{
		m_sFile	=	file;
	}
	
	public void close()
	{
		m_clPlayer.close();
	}
}
