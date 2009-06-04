package plugins;


public interface IMP3Player {
		
		/**
		 * Start playing the MP3 File, that was set with setMP3File( String )
		 *
		 */
		public void run();
		
		/**
		 * Set the filepath and filename to the mp3 file 
		 * @param file the path to the file
		 */
		public void setMP3File( String file );
		
		/**
		 * Ends playing the mp3 file.
		 *
		 */
		public void close();
}
