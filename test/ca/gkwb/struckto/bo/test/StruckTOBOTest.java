package ca.gkwb.struckto.bo.test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import ca.gkwb.struck.incident.dao.IncidentVO;
import ca.gkwb.struckto.bo.StruckTOBO;
import ca.gkwb.twitter.connector.TwitterConnector;

public class StruckTOBOTest {

	StruckTOBO stBO;
	TwitterConnector tConn;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	List<String> peelHashtags = new ArrayList();
	List<String> toHashtags = new ArrayList<String>();
	List<String> yorkHashtags = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		stBO = (StruckTOBO)context.getBean("StruckTOBO");

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
			int retweets = stBO.queryAndProcess("TPSOperations", 100, toHashtags);
			// retweets = retweets +
			// stBO.queryAndProcess("PeelPoliceMedia",100,peelHashtags);
			// retweets = retweets +
			// stBO.queryAndProcess("YRP",100,yorkHashtags);

			logger.debug("New Retweet Count: " + retweets);
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

	@Test
	public void testGenerateStruckTOIncidentVO() {
		Calendar cal = Calendar.getInstance();
		Date now = new Date(cal.getTime().getTime());
		long tweetId = new Long("740033293530374144");
		int locationId = 2;

		IncidentVO sttVO = stBO.generateIncidentVO(tweetId, now, locationId);

		Assert.isTrue(tweetId == sttVO.getTweetId());
		Assert.isTrue(sttVO.getLocationId() == locationId);
		Assert.isTrue(now.equals(sttVO.getCreateDate()));
		Assert.isTrue(now.equals(sttVO.getActivityDate()));
		Assert.isTrue(sttVO.getSeverity().equalsIgnoreCase(IncidentVO.SEVERITY_UNKNOWN));
	}
}
