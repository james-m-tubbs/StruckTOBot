package ca.gkwb.twitter.connector;

import java.util.List;

import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;
import twitter4j.Status;

public interface TwitterConnector {

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

	public List<Status> getStatusByRegex(String regex, int limit) throws WarnException, FatalException;
}
