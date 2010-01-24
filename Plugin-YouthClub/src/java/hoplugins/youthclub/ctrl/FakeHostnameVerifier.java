package hoplugins.youthclub.ctrl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Simple class to accept wrong hostnames from SSL certificates.
 */
public class FakeHostnameVerifier implements HostnameVerifier {
	
	public boolean verify(String rserver, SSLSession sses) {
		if (!rserver.equals(sses.getPeerHost())) {
			//System.out.println("certificate does not match host but continuing anyway");
		}
		return true;
	}
}
