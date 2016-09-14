package ca.gkwb.struckto.interaction.bo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import ca.gkwb.struck.incident.dao.IncidentVO;
import ca.gkwb.struck.interaction.dao.InteractionDAO;
import ca.gkwb.struck.interaction.dao.InteractionVO;
import ca.gkwb.struck.location.dao.LocationVO;
import ca.gkwb.struckto.bo.StruckTOBO;
import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;
import ca.gkwb.struckto.location.LocationBO;
import ca.gkwb.twitter.connector.TwitterConnector;
import lombok.Setter;
import twitter4j.Status;

public class InteractionBOImpl implements InteractionBO {
	
	private final String RECENT = "RECENT";
	private final String LOCATION = "LOCATION";
	private final String TWEET = "TWEET";
	
	@Setter
	private TwitterConnector tConn;
	@Setter
	private InteractionDAO iDAO;
	@Setter
	private LocationBO lBO;
	@Setter
	private StruckTOBO stBO;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public int processInteractions(int batchSize) throws FatalException {
		int count = 0;
		try {
			List<Status> mentions = tConn.getStatusByMentions(batchSize);
			for (int i=0;i<mentions.size();i++) {
				InteractionVO iVO = generateInteraction(mentions.get(i));
				processOneInteraction(iVO, mentions.get(i));
				}
		} catch (WarnException e) {
			logger.debug("Couldn't query mentions", e);
		}
		return count;
	}

	@Override
	public boolean processOneInteraction(InteractionVO iVO, Status s) throws FatalException {
		//if (!doesInteractionExist(iVO.getTweetId())) {
			//iDAO.insert(iVO);
		
		//prevent infinite loops
		if (s.getUser().getName().equalsIgnoreCase("StruckTOBot")) return false;
		
		try {
			String type = findInteractionType(s);
			if (type != null) {
				logger.debug("Status : " + s.getText());
				logger.debug("Type: " + type);
				
				if (type.equalsIgnoreCase(LOCATION)) processLocationInteraction(iVO, s);
				
			//CASE statement
			//
			//
			//completeInteraction(iVO);
				}
	//		} else {
	//			logger.info("Interaction Exists - ignoring: " + iVO.getTweetId());
	//			return false;
	//		}
			return true;
		} catch (WarnException e) {
			logger.error("Could not process interaction", e);
		}
		return false;
	}

	@Override
	public void completeInteraction(InteractionVO iVO) throws FatalException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String generateReportByForId(String status) throws FatalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateRecentIncidentReprot(String status) throws FatalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateLocationReport(String status) throws FatalException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private InteractionVO generateInteraction(Status s) {
		InteractionVO iVO = new InteractionVO(s.getId(), s.getUser().getName(), null, null, -1, null, null);
		return iVO;
	}

	@Override
	public boolean doesInteractionExist(long tweetId) throws FatalException {
		try {
			InteractionVO iVO = iDAO.queryOne(tweetId);
			if (iVO == null) return false;
			return true;
		} catch (Exception e) {
			throw new FatalException(e);
		}
	}
	
	@Override
	public String findInteractionType(Status s) {
		if (doesRegexMatch(InteractionRegex.recentReport, s.getText())) return RECENT;
		if (doesRegexMatch(InteractionRegex.locationRegex, s.getText())) return LOCATION;
		if (doesRegexMatch(InteractionRegex.tweetIdRegex, s.getText())) return TWEET;
		return null;
	}
	
	private boolean doesRegexMatch(String regex, String text) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		
		List<String> results = new ArrayList<String>();
		
		while (m.find()) {
			logger.debug("Matched Regex: " + m.group(0));
			return true;
		}
		return false;
	}
	
	public boolean processLocationInteraction(InteractionVO iVO, Status s) throws WarnException, FatalException {
		List<String> streetLocation = lBO.parseStreetLocation(s.getText());
		if (streetLocation.size() != 2) {
			//sendSorryLocationTweet();
			return false;
		}
		
		String intersection = streetLocation.get(0)+ " + " + streetLocation.get(1);
		
		LocationVO locationVO = lBO.processOneTweet(s.getText());
		List<IncidentVO> incidents = stBO.getIncidentsByLocationVO(locationVO);
		
		logger.debug("Found incidents: " + incidents);
		
		int weeklyCount = 0;
		int monthlyCount = 0;
		int yearlyCount = 0;
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -23);
		Date month = new Date(cal.getTime().getTime());
		
		cal = Calendar.getInstance();
		
		for (int i=0;i<incidents.size();i++) {
			Calendar createDate = Calendar.getInstance();
			createDate.setTime(incidents.get(i).getCreateDate());
			
			if (cal.get(Calendar.YEAR) == createDate.get(Calendar.YEAR)) yearlyCount++;
			
			cal.add(Calendar.DATE, -7);
			if (createDate.after(cal)) weeklyCount++;
			
			cal.add(Calendar.DATE, -23);
			if (createDate.after(cal)) monthlyCount++;
		}
		
		sendLocationInteractionTweet(
				intersection,
				weeklyCount, 
				monthlyCount, 
				yearlyCount, 
				incidents.size(), 
				s.getUser().getScreenName());
		
		return true;
	};
	
	@Override
	public void sendLocationInteractionTweet(
			String intersection,
			int weekly, 
			int monthly, 
			int yearly, 
			int allTime, 
			String username) 
					throws WarnException, FatalException {
		String statusText = "@" + username + " " +
					"Incidents at " + intersection + ":\n" +
					"7 Days - " + weekly + "\n" +
					"30 Days - " + monthly + "\n" +
					"This Year - " + yearly + "\n" +
					"All Time - " + allTime;
		tConn.sendStatusUpdate(statusText);
	}
}