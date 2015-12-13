package ca.gkwb.twitter.connector;

import java.util.List;

import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;
import twitter4j.Status;

public interface TwitterConnector {

	public final static String CYCLIST_REGEX = ".?(C|c)yclist";
	public final static String PEDESTRIAN_REGEX = ".?(P|p)edestrian";
	
	/**
	 * Connects using oAuthParams
	 * 
	 * @param consumerKey
	 * @param consumerSecret
	 * @throws FatalException
	 */
	public void generateOAuthParams(String consumerKey, String consumerSecret) throws FatalException;
	
	public void connect() throws FatalException;
	
	public void disconnect() throws FatalException;
	
	public void retweet(long statusId) throws WarnException, FatalException;

	public List<Status> getStatusByRegex(String targetAccount, String regex, int limit) throws WarnException, FatalException;
	
	public Status getStatusById(long statusId) throws WarnException, FatalException;

	public boolean checkStatusRegexMatch(Status status, String regex) throws WarnException;
	
	public long sendStatusUpdate(String status) throws FatalException, WarnException;
}
