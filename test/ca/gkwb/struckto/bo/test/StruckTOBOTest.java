package ca.gkwb.struckto.bo.test;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.gkwb.stuckto.bo.StruckTOBOImpl;
import ca.gkwb.twitter.connector.TwitterConnector;
import ca.gkwb.twitter.connector.TwitterConnectorImpl;

public class StruckTOBOTest {
	
	StruckTOBOImpl stBO;
	TwitterConnector tConn;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Before
	public void setUp() throws Exception {
		tConn = new TwitterConnectorImpl();
		tConn.connect();
		stBO = new StruckTOBOImpl();
		stBO.setTConn(tConn);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testQueryAndRetweet() {
		try {
			int retweets = stBO.queryAndProcess("TPSOperations",100);
			retweets = retweets + stBO.queryAndProcess("PeelPoliceMedia",100);
			logger.debug("New Retweet Count: "+retweets);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
