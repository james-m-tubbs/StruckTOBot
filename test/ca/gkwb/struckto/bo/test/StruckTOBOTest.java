package ca.gkwb.struckto.bo.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.gkwb.stuckto.bo.StruckTOBOImpl;
import ca.gkwb.twitter.connector.TwitterConnector;
import ca.gkwb.twitter.connector.TwitterConnectorImpl;

public class StruckTOBOTest {
	
	StruckTOBOImpl stBO;
	TwitterConnector tConn;

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
	public void test() {
		try {
			stBO.queryAndProcess(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
