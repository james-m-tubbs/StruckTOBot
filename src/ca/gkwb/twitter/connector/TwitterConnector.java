package ca.gkwb.twitter.connector;

import java.util.List;

import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;

public interface TwitterConnector {
	
	/**
	 * Returns a list of twitter statuses by input regex. 
	 * 
	 * @author tubbsj
	 * @date 2015-12-05
	 * @param regex
	 * @return List<>
	 * @throws WarnException
	 */
	public List<String> getStatusByRegex(String regex) throws WarnException;

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

}
