package ca.gkwb.twitter.connector;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import com.twitter.hbc.core.event.Event;

import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

//examples here: https://github.com/twitter/hbc
public class TwitterConnectorImpl implements TwitterConnector {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	//oauth info
	private String consumerKey;
	private String consumerSecret;
	private String token;
	private String secret;
	
	private BlockingQueue<String> msgQueue;
	private BlockingQueue<Event> eventQueue;
	
	private Twitter twitter;
	
	public void connectOAuthSpring(List<Long> followings, List<String> terms)
			throws FatalException {
		connectOAuthParams(followings, terms, consumerKey, consumerSecret, token, secret);
		
	}	
	
	public void connectOAuthParams(String consumerKey, String consumerSecret) throws FatalException {
		//check input consumerKey
		if (consumerKey == null) consumerKey = this.consumerKey;
		if (consumerKey == null) throw new FatalException("Missing ConsumerKey");
		
		//check input consumersecret
		if (consumerSecret == null) consumerSecret = this.consumerSecret;
		if (consumerSecret == null) throw new FatalException("Missing ConsumerSecret");
		
		twitter = new TwitterFactory().getInstance();
		try {
			RequestToken requestToken = twitter.getOAuthRequestToken();
			AccessToken accessToken = getAccessToken(requestToken);
		} catch (TwitterException e) {
			logger.debug("Caught TwitterException: "+e.getMessage());
			if (logger.isDebugEnabled()) e.printStackTrace();
			throw new FatalException(e);
		}
				
		
	}
	
	/**
	 * Helper method to return the access token
	 * 
	 * @author tubbsj
	 * @date 2015-12-08
	 * @param requestURL
	 * @return AccessToken
	 * @throws FatalException
	 */
	private AccessToken getAccessToken(RequestToken requestToken) throws FatalException {
		int retryCount = 0;
		
		AccessToken accessToken = null;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == accessToken) {
        	if (retryCount > 10) throw new FatalException("Could not connect to twitter");
            logger.debug("Open the following URL and grant access to your account:");
            logger.debug(requestToken.getAuthorizationURL());
            try {
            	//get the requestURL
                Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
            } catch (UnsupportedOperationException ignore) {
            } catch (IOException ignore) {
            } catch (URISyntaxException e) {
                throw new AssertionError(e);
            }
            logger.debug("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
            String pin = br.readLine();
            try {
                if (pin.length() > 0) {
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                } else {
                    accessToken = twitter.getOAuthAccessToken(requestToken);
                }
            } catch (TwitterException te) {
                if (401 == te.getStatusCode()) {
                    logger.debug("Unable to get the access token.");
                } else {
                    te.printStackTrace();
                }
            }
            retryCount++;
        }
        logger.debug("Got access token.");
        logger.debug("Access token: " + accessToken.getToken());
        logger.debug("Access token secret: " + accessToken.getTokenSecret());
		
        return accessToken;
	}
	
	public List<String> getStatusByRegex(String regex) throws WarnException {
		List<String> retStr = new ArrayList<String>();
		while (!hosebirdClient.isDone()) {	
			try {
				logger.debug("Iterating through messages");
				String msg = msgQueue.take();
				logger.debug(msg);
				retStr.add(msg);
			} catch (Exception e) {
				logger.error("Error DQing msg", e);
				if (logger.isDebugEnabled()) e.printStackTrace();
				throw new WarnException();
			}
		}
		logger.debug("Dequeued Messages Size: "+retStr.size());
		return retStr;
	}
	
	public void disconnect() throws FatalException {
		// TODO Auto-generated method stub
		
	}	
		
	//**************************************************
	//** Setters
	//**************************************************

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void connect(List<Long> followings, List<String> terms)
			throws FatalException {
		// TODO Auto-generated method stub
		
	}

	public void connectOAuthParams(List<Long> followings, List<String> terms, String consumerKey, String consumerSecret,
			String token, String secret) throws FatalException {
		// TODO Auto-generated method stub
		
	}

	public void connectBasicParams(List<Long> followings, List<String> terms, String username, String password)
			throws FatalException {
		// TODO Auto-generated method stub
		
	}
}
