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
	 * @param followings
	 * @param terms
	 * @param consumerKey
	 * @param consumerSecret
	 * @param token
	 * @param secret
	 * @throws FatalException
	 */
	public void connectOAuthParams(List<Long> followings,  List<String> terms, String consumerKey, String consumerSecret, 
			String token, String secret) throws FatalException;	
	
	public void connectBasicParams(List<Long> followings, List<String> terms,
			String username, String password) throws FatalException;	
	
	public void connectOAuthSpring(List<Long> followings, 
			List<String> terms) throws FatalException;
	
	public void disconnect() throws FatalException;

}
