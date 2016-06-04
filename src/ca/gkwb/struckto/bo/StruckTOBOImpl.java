package ca.gkwb.struckto.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import ca.gkwb.struckto.dao.StruckTOIncidentDAO;
import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;
import ca.gkwb.struckto.vo.StruckTOIncidentVO;
import ca.gkwb.struckto.vo.StruckTOLocationVO;
import ca.gkwb.twitter.connector.TwitterConnector;
import lombok.Setter;
import twitter4j.Status;

public class StruckTOBOImpl implements StruckTOBO {
	
	@Setter
	private TwitterConnector tConn;
	@Setter
	private StruckTOIncidentDAO stiDAO;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	public int queryAndProcess(String targetAcct, int size, List<String> hashTags) throws FatalException {
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
					logger.debug("Not a previous retweet. Sending new retweet");
					try {
						processIncident(s);
						tConn.retweet(s.getId());
						processed++;
					} catch (WarnException e) {
						//if (logger.isDebugEnabled()) e.printStackTrace();
						logger.debug("Couldn't Retweet "+s.getId());
					}
				} else {
					logger.debug("Already retweeted, igonring. "+s.getId());
				}
			}
//			if (processed > 0) {
//				try {
//					String htags = buildHashtagString(hashTags);
//					tConn.sendStatusUpdate("New Incidents Reported by @"+targetAcct+" since last run: "+processed+" "+htags);
//				} catch (WarnException e) {
//				}
//			}
			return processed;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) e.printStackTrace();
			logger.error(e);
			return processed;
		}
	}
	
	public String buildHashtagString(List<String> htags) {
		String retStr = "";
		for (int i=0;i<htags.size();i++) {
			if (i>0) retStr = retStr + " ";
			retStr = retStr + "#"+htags.get(i);
		}
		logger.debug("generated hashtag string: "+retStr);
		return retStr;
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
		//logger.debug("Retweet UserID: "+status.getCurrentUserRetweetId());
		return status.isRetweetedByMe();
	}
	
	public StruckTOLocationVO getLocationVOForIncident(StruckTOIncidentVO stVO)
			throws WarnException, FatalException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<String> parseHashtags(String hashtagString) {
		String[] htArray = hashtagString.split(",");
		return new ArrayList<String>(Arrays.asList(htArray));
	}	
	
	//**********************************************************
	//** Helper Methods
	//**********************************************************
	
	private void processIncident(Status s) {
		//create incidentVO from status
		//TODO
		StruckTOIncidentVO stVO = new StruckTOIncidentVO(
				);
	}
}
