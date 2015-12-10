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
	

//	@Test
//	public void testRetweet() {
//		try {
//			tConn.retweet(new Long("674290293403983873").longValue());
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail(e.getMessage());
//		}
//	}
	
	@Test
	public void testQueryAllStatus() {
		try {
			List<Status> status = tConn.getStatusByRegex("TPSOperations","Pedestrian struck", 100);
			logger.debug(status.size());
			for (Status s : status) {
				logger.debug("Status: "+s.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testTweetMatch() {
		try {
			List<Status> status = tConn.getStatusByRegex("TPSOperations", tConn.PEDESTRIAN_REGEX, 100);
			logger.debug(status.size());
			for (Status s : status) {
				logger.debug("Status: "+s.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}		
	}
	
	@Test
	public void testQueryTweetById() {
		try {
			Status status = tConn.getStatusById(674413700493651968L);
			logger.debug(status);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}		
	}
	
	@Test
	public void testCheckStatusPedestrianRegexMatch() {
		try {
			Status status = tConn.getStatusById(674413700493651968L);
			logger.debug(status.getText());
			boolean res1 = tConn.checkStatusRegexMatch(status, tConn.PEDESTRIAN_REGEX);
			logger.debug("Checking regex Match: "+res1);
			Assert.assertTrue(res1);
			
			Status status2 = tConn.getStatusById(674324153101631488L);
			logger.debug(status2.getText());
			boolean res2 = tConn.checkStatusRegexMatch(status2, tConn.PEDESTRIAN_REGEX);
			logger.debug("Checking regex Match: "+res2);
			Assert.assertTrue(res2);
						
			Status status4 = tConn.getStatusById(674237303049625600L);
			logger.debug(status4.getText());
			boolean res4 = tConn.checkStatusRegexMatch(status4, tConn.PEDESTRIAN_REGEX);
			logger.debug("Checking regex Match: "+res4);
			Assert.assertFalse(res4);			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}		
	}
	
	@Test
	public void testCheckStatusCyclistRegexMatch() {
		try {
			
			Status status2 = tConn.getStatusById(674237303049625600L);
			logger.debug(status2.getText());
			boolean res2 = tConn.checkStatusRegexMatch(status2, tConn.PEDESTRIAN_REGEX);
			logger.debug("Checking regex Match: "+res2);
			Assert.assertFalse(res2);
			
			Status status3 = tConn.getStatusById(674324153101631488L);
			logger.debug(status3.getText());
			boolean res3 = tConn.checkStatusRegexMatch(status3, tConn.CYCLIST_REGEX);
			logger.debug("Checking regex Match: "+res3);
			Assert.assertFalse(res3);
			
			Status status4 = tConn.getStatusById(674237303049625600L);
			logger.debug(status4.getText());
			boolean res4 = tConn.checkStatusRegexMatch(status4, tConn.CYCLIST_REGEX);
			logger.debug("Checking regex Match: "+res4);
			Assert.assertTrue(res4);			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}		
	}		
}
