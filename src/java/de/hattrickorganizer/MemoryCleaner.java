package de.hattrickorganizer;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class MemoryCleaner implements Runnable {
	//~ Instance fields ----------------------------------------------------------------------------

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new instance of HoServerWorker
	 *
	 * @param server TODO Missing Constructuor Parameter Documentation
	 */
	public MemoryCleaner() {
		run();
	}

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 */
	public final void run() {
		while(true) {
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.gc();
		}
	}

	/**
	 * TODO Missing Method Documentation
	 */
	@Override
	protected final void finalize() {
	}

}
