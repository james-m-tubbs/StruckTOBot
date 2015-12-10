package ca.gkwb.stuckto.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ca.gkwb.google.connector.maps.GoogleMapsConnector;
import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;
import ca.gkwb.struckto.vo.StruckTOIncidentVO;
import ca.gkwb.struckto.vo.StruckTOLocationVO;
import ca.gkwb.twitter.connector.TwitterConnector;
import twitter4j.Status;

public class StruckTOBOImpl implements StruckTOBO {
	
	private TwitterConnector tConn;
	private GoogleMapsConnector gconn;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	public int queryAndProcess(String targetAcct, int size) throws FatalException {
		int processed = 0;
		try {
			List<Status> status = new ArrayList<Status>();
			status.addAll(tConn.getStatusByRegex(targetAcct, tConn.CYCLIST_REGEX, size));
			logger.debug("Added Cyclist Regex: "+status.size());
			
			status.addAll(tConn.getStatusByRegex(targetAcct, tConn.PEDESTRIAN_REGEX, size));
			logger.debug("Added Pedestrian Regex: "+status.size());
			
			logger.debug(status.size());
			for (Status s : status) {
				//logger.debug("Status: "+s.getText());
				//TODO processOneStatus
				if (!checkRetweeted(s, "StruckTOBot")) {
					logger.debug("Not a previous retweet. Sending new");
					try {
						tConn.retweet(s.getId());
						processed++;
					} catch (WarnException e) {
						//if (logger.isDebugEnabled()) e.printStackTrace();
						logger.debug("Couldn't Retweet "+s.getId());
					}
				}
			}
			return processed;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) e.printStackTrace();
			logger.error(e);
			return processed;
		}
	}
	public List<StruckTOIncidentVO> getNewIncidents() throws FatalException,
			WarnException {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean processIncident(StruckTOIncidentVO stVO)
			throws WarnException, FatalException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean checkRetweeted(Status status, String username) throws WarnException, FatalException {
		logger.debug("Retweet UserID: "+status.getCurrentUserRetweetId());
		//TODO resolve this
		return false;
	}
	
	public StruckTOLocationVO getLocationVOForIncident(StruckTOIncidentVO stVO)
			throws WarnException, FatalException {
		// TODO Auto-generated method stub
		return null;
	}
	
	//**********************************************************
	//** Getters and Setters
	//**********************************************************
	
	public void setTConn(TwitterConnector tconn) {
		this.tConn = tconn;
	}
	public void setGConn(GoogleMapsConnector gconn) {
		this.gconn = gconn;
	}
}
