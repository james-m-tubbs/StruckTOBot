package ca.gkwb.struckto.location.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import ca.gkwb.struckto.location.StruckTOLocationBOImpl;
import ca.gkwb.twitter.connector.TwitterConnector;
import ca.gkwb.twitter.connector.TwitterConnectorImpl;
import twitter4j.Status;

public class StruckTOLocationBOTest {

	TwitterConnector tConn;
	StruckTOLocationBOImpl stlBO;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Before
	public void setUp() throws Exception {
		stlBO = new StruckTOLocationBOImpl();
		tConn = new TwitterConnectorImpl();
		tConn.connect();		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testParseLocations() {
		try {
			List<Status> status = tConn.getStatusByRegex("TPSOperations", tConn.PEDESTRIAN_REGEX, 100);
			logger.debug(status.size());
			for (Status s : status) {
				List<String> intersection = stlBO.parseStreetLocation(s.getText());
				logger.debug(intersection);
				Assert.isTrue(intersection.size()==2);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.isTrue(false);
		}
	}	
}
