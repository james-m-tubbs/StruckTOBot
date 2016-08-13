package ca.gkwb.struckto.bo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import ca.gkwb.struck.exception.db.GenericDBException;
import ca.gkwb.struck.incident.dao.IncidentDAO;
import ca.gkwb.struck.incident.dao.IncidentVO;
import ca.gkwb.struck.location.dao.LocationVO;
import ca.gkwb.struck.tweet.dao.TweetDAO;
import ca.gkwb.struck.tweet.dao.TweetVO;
import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;
import ca.gkwb.struckto.location.LocationBO;
import ca.gkwb.twitter.connector.TwitterConnector;
import lombok.Setter;
import twitter4j.Status;

public class StruckTOBOImpl implements StruckTOBO {
	
	@Setter
	private TwitterConnector tConn;
	@Setter
	private IncidentDAO stiDAO;
	@Setter
	private TweetDAO sttDAO;
	@Setter
	private LocationBO stlBO;
	
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
	
	public List<IncidentVO> getNewIncidents() throws FatalException,
			WarnException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean checkRetweeted(Status status, String username) throws WarnException, FatalException {
		//logger.debug("Retweet UserID: "+status.getCurrentUserRetweetId());
		return status.isRetweetedByMe();
	}
	
	public LocationVO getLocationVOForIncident(IncidentVO stVO)
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
		
		LocationVO stlVO = generateLocationVO(s);
		TweetVO sttVO = generateTweetVO(s, now);
		Integer locationId = null;
		if (stlVO != null) locationId = stlVO.getId();
		IncidentVO stiVO = generateIncidentVO(sttVO.getTweetId(), now, locationId);
			
		try {
			sttDAO.insert(sttVO);
			stiDAO.insert(stiVO);
		} catch (GenericDBException e) {
			throw new FatalException("Error writing to DB -> ", e);
		}
		return true;
	}
	
	@Override
	public TweetVO generateTweetVO(Status s, Date d) {
		String tweetUrl = "http://twitter.com/" + s.getUser().getScreenName() + "/status/" + s.getId();
		
		TweetVO sttVO = new TweetVO(s.getId(), tweetUrl, s.getUser().getName(), d);	
		logger.debug("Generated TweetVO: " + sttVO);
		return sttVO;			
	}
	
	@Override
	public IncidentVO generateIncidentVO(long tweetId, Date date, Integer locationId) {
		
		//create incidentVO from status
		IncidentVO stVO = new IncidentVO(-1, tweetId, IncidentVO.SEVERITY_UNKNOWN,
				null, date, date, locationId, true
			);	
		
		logger.debug("Generated IncidentVO " + stVO.toString());
		return stVO;
		
	}

	@Override
	public LocationVO generateLocationVO(Status s) throws FatalException {
		LocationVO stlVO = null;
		logger.debug("Status to parse: " + s);
		try {
			stlVO = stlBO.processOneTweet(s.getText());
		} catch (WarnException e) {
			logger.error("WarnException on parsing tweet location", e);
		}
		logger.debug("Generated LocationVO : " + stlVO);
		return stlVO;
	}
}
