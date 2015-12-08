package ca.gkwb.stuckto.bo;

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
	
	public boolean queryAndProcess(int size) throws FatalException {
		try {
			boolean processed = false;
			List<Status> status = tConn.getStatusByRegex("cyclist|pedestrian", size);
			logger.debug(status.size());
			for (Status s : status) {
				logger.debug("Status: "+s.getText());
				//TODO processOneStatus
				tConn.retweet(s.getId());
				if (!processed) processed = true;
			}
			return processed;
		} catch (WarnException e) {
			if (logger.isDebugEnabled()) e.printStackTrace();
			logger.error(e);
			return false;
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
