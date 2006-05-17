// %1912333325:de.hattrickorganizer.credits%
package de.hattrickorganizer;

import de.hattrickorganizer.core.HOSetup;
import de.hattrickorganizer.tools.extension.ExtensionListener;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
class ListenerTest extends HOSetup  {

	public static void main(String[] args) {
		new ListenerTest();
	}

	public ListenerTest() {
		new ExtensionListener().run();
	}
}
