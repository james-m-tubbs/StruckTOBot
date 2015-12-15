package ca.gkwb.struckto.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;

import ca.gkwb.stuckto.bo.StruckTOBOImpl;
import ca.gkwb.twitter.connector.TwitterConnector;
import ca.gkwb.twitter.connector.TwitterConnectorImpl;

public class StruckTOMain {
	
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
