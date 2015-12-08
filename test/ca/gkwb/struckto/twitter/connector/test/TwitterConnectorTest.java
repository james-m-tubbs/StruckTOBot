package ca.gkwb.struckto.twitter.connector.test;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.gkwb.twitter.connector.TwitterConnector;
import ca.gkwb.twitter.connector.TwitterConnectorImpl;
import twitter4j.Status;

public class TwitterConnectorTest {
	
	TwitterConnector tConn;
	private Logger logger = Logger.getLogger(this.getClass().getName());

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
			tConn.retweet(new Long("674290293403983873").longValue());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testQueryAllStatus() {
		try {
			List<Status> status = tConn.getStatusByRegex("Pedestrian struck", 100);
			logger.debug(status.size());
			for (Status s : status) {
				logger.debug("Status: "+s.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
