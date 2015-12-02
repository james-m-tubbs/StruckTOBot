package ca.gkwb.stuckto.bo;

import java.util.List;

import ca.gkwb.google.connector.maps.GoogleMapsConnector;
import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;
import ca.gkwb.struckto.vo.StruckTOIncidentVO;
import ca.gkwb.struckto.vo.StruckTOLocationVO;
import ca.gkwb.twitter.connector.TwitterConnector;

public class StruckTOBOImpl implements StruckTOBO {
	
	private TwitterConnector tconn;
	private GoogleMapsConnector gconn;
	
	public boolean queryAndProcess(int size) throws FatalException {
		// TODO Auto-generated method stub
		return false;
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
	
	public void setTconn(TwitterConnector tconn) {
		this.tconn = tconn;
	}
	public void setTconn(GoogleMapsConnector gconn) {
		this.gconn = gconn;
	}
}
