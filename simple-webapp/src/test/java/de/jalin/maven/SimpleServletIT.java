package de.jalin.maven;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.catalina.startup.Tomcat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SimpleServletIT {

	private static final String workingDir = System.getProperty("user.dir") + "/target";

	private Tomcat tomcat;
	private String baseURL;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final File dir = new File(workingDir + "/tomcat");
		dir.mkdirs();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		tomcat = new Tomcat();
		tomcat.setPort(0);
		tomcat.setBaseDir(workingDir + "/tomcat");
		tomcat.getHost().setAppBase(workingDir + "/simple-webapp");
		tomcat.getHost().setAutoDeploy(true);
		tomcat.getHost().setDeployOnStartup(true);
		tomcat.addWebapp(tomcat.getHost(), "/", workingDir + "/simple-webapp");
		tomcat.start();
		final int httpPort = tomcat.getConnector().getLocalPort();
		baseURL = "http://localhost:" + httpPort + "/";
		
	}

	@After
	public void tearDown() throws Exception {
		tomcat.stop();
	}

	@Test
	public void test() {
		try {
			Thread.sleep(1000L);
			final URL url = new URL(baseURL);
			final URLConnection connection = url.openConnection();
			connection.connect();
			final InputStreamReader reader = new InputStreamReader(connection.getInputStream());
			final BufferedReader bf = new BufferedReader(reader);
			final String readLine = bf.readLine();
			Assert.assertEquals("Hello world from SimpleServlet", readLine);
		} catch (InterruptedException | IOException e) {
			fail(e.getMessage());
		}
	}

}
