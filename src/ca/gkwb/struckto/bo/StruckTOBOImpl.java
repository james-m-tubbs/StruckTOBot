package ca.gkwb.struckto.bo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.GenericDBException;
import ca.gkwb.struckto.exception.WarnException;
import ca.gkwb.struckto.incident.StruckTOIncidentDAO;
import ca.gkwb.struckto.incident.StruckTOIncidentVO;
import ca.gkwb.struckto.location.StruckTOLocationDAO;
import ca.gkwb.struckto.location.StruckTOLocationVO;
import ca.gkwb.struckto.tweet.StruckTOTweetDAO;
import ca.gkwb.struckto.tweet.StruckTOTweetVO;
import ca.gkwb.twitter.connector.TwitterConnector;
import lombok.Setter;
import twitter4j.Status;

public class StruckTOBOImpl implements StruckTOBO {
	
	@Setter
	private TwitterConnector tConn;
	@Setter
	private StruckTOIncidentDAO stiDAO;
	@Setter
	private StruckTOTweetDAO sttDAO;
	@Setter
	private StruckTOLocationDAO stlDAO;
	
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
	
	@Override
	public boolean processIncident(Status s) throws FatalException {
		logger.debug("Processing Incident : " + s.toString());
		Calendar cal = Calendar.getInstance();
		Date now = new Date(cal.getTime().getTime());
		
		StruckTOTweetVO sttVO = generateTweetVO(s, now);
		StruckTOIncidentVO stiVO = generateIncidentVO(sttVO.getTweetId(), now, null);
			
		try {
			sttDAO.insert(sttVO);
			stiDAO.insert(stiVO);
		} catch (GenericDBException e) {
			throw new FatalException("Error writing to DB -> ", e);
		}
		return true;
	}
	
	@Override
	public StruckTOTweetVO generateTweetVO(Status s, Date d) {
		String tweetUrl = "http://twitter.com/" + s.getUser().getScreenName() + "/status/" + s.getId();
		
		StruckTOTweetVO sttVO = new StruckTOTweetVO(s.getId(), tweetUrl, s.getUser().getName(), d);	
		return sttVO;			
	}
	
	@Override
	public StruckTOIncidentVO generateIncidentVO(long tweetId, Date date, Integer locationId) {
		
		//create incidentVO from status
		StruckTOIncidentVO stVO = new StruckTOIncidentVO(-1, tweetId, StruckTOIncidentVO.SEVERITY_UNKNOWN,
				null, date, date, locationId, true
			);	
		
		logger.debug("Generated IncidentVO " + stVO.toString());
		return stVO;
		
	}
}
