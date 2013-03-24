package org.shirdrn.tinyframework.server.jetty;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestHttpServer {

	JettyHttpServer server;

	@Before
	public void setUp() {
		String name = "";
		String bindAddress = "";
		int port = 9999;
		boolean findPort = false;
		try {
			server = new JettyHttpServer(name, bindAddress, port, findPort);
			server.addServlet("echo", "/echo", EchoServlet.class);
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testServlet() {
		try {
			URL baseUrl = new URL("http://localhost:9999");
			 assertEquals("a:b\nc:d\n", 
			         readOutput(new URL(baseUrl, "/echo?a=b&c=d")));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String readOutput(URL url) throws IOException {
		StringBuilder out = new StringBuilder();
		InputStream in = url.openConnection().getInputStream();
		byte[] buffer = new byte[64 * 1024];
		int len = in.read(buffer);
		while (len > 0) {
			out.append(new String(buffer, 0, len));
			len = in.read(buffer);
		}
		return out.toString();
	}

	@After
	public void tearDown() {
		try {
			server.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
