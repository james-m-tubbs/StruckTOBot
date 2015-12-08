package ca.gkwb.struckto.twitter.connector.test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.gkwb.twitter.connector.TwitterConnector;
import ca.gkwb.twitter.connector.TwitterConnectorImpl;

public class TwitterOAuthGenerator {
	
	TwitterConnector tConn;

	@Before
	public void setUp() throws Exception {
		tConn = new TwitterConnectorImpl();
		tConn.connect();
	}

	@After
	public void tearDown() throws Exception {
		tConn.disconnect();
	}

//	@Test
//	public void testGenerateOAuthParams() {
//		try {
//			tConn.generateOAuthParams("", "");
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail(e.getMessage());
//		}
//	}
	

	@Test
	public void testRetweet() {
		try {
			tConn.retweet(new Long("666324420323995648").longValue());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

}
