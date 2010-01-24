package hoplugins.youthclub.ctrl;

/**
 * Simple TrustManager which accept fake and outdated SSL certificates.
 */
public class FakeTrustManager implements javax.net.ssl.X509TrustManager {

	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		return null;
	}

	public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
	}

	public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
	}
}