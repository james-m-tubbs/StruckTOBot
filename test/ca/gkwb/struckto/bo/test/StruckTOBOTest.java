package ca.gkwb.struckto.bo.test;

import java.util.ArrayList;
import java.util.List;

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
	List<String> peelHashtags = new ArrayList();
	List<String> toHashtags = new ArrayList<String>();
	List<String> yorkHashtags = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		tConn = new TwitterConnectorImpl();
		tConn.connect();
		stBO = new StruckTOBOImpl();
		stBO.setTConn(tConn);
		
		peelHashtags.add("bikeBrampton");
		peelHashtags.add("bikeMississauga");
		peelHashtags.add("bikeON");
		
		toHashtags.add("bikeTO");
		toHashtags.add("bikeON");
		
		yorkHashtags.add("bikeYork");
		yorkHashtags.add("bikeON");
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testQueryAndRetweet() {
		try {
			int retweets = stBO.queryAndProcess("TPSOperations",100, toHashtags);
			retweets = retweets + stBO.queryAndProcess("PeelPoliceMedia",100,peelHashtags);
			retweets = retweets + stBO.queryAndProcess("YRP",100,yorkHashtags);
			
			logger.debug("New Retweet Count: "+retweets);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGenerateHashtags() {
		try {
			logger.debug(stBO.buildHashtagString(toHashtags));
			logger.debug(stBO.buildHashtagString(peelHashtags));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
