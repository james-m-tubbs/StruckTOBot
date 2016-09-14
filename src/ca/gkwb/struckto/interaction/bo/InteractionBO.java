package ca.gkwb.struckto.interaction.bo;

import ca.gkwb.struck.interaction.dao.InteractionVO;
import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;
import twitter4j.Status;

public interface InteractionBO {
	
	public int processInteractions(int batchSize) throws FatalException;
	
	public void completeInteraction(InteractionVO iVO) throws FatalException;
	
	public String generateReportByForId(String status) throws FatalException;
	
	public String generateRecentIncidentReprot(String status) throws FatalException;
	
	public String generateLocationReport(String status) throws FatalException;
	
	public boolean doesInteractionExist(long tweetId) throws FatalException;

	public boolean processOneInteraction(InteractionVO iVO, Status s) throws FatalException;

	public String findInteractionType(Status s);

	public void sendLocationInteractionTweet(int weekly, int monthly, int yearly, int allTime, String username)
			throws WarnException, FatalException;

}
