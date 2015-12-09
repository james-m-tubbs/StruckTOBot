package ca.gkwb.twitter.connector;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

//examples here: https://github.com/twitter/hbc
//examples here: https://github.com/yusuke/twitter4j/tree/master/twitter4j-examples/src/main/java/twitter4j/examples
public class TwitterConnectorImpl implements TwitterConnector {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	//oauth info
	private String consumerKey;
	private String consumerSecret;
	private String token;
	private String secret;
	
	private Twitter twitter;	
	
	public void generateOAuthParams(String consumerKey, String consumerSecret) throws FatalException {
		//check input consumerKey
		if (consumerKey == null) consumerKey = this.consumerKey;
		if (consumerKey == null) throw new FatalException("Missing ConsumerKey");
		
		//check input consumersecret
		if (consumerSecret == null) consumerSecret = this.consumerSecret;
		if (consumerSecret == null) throw new FatalException("Missing ConsumerSecret");		
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	            .setOAuthConsumerKey(consumerKey)
	            .setOAuthConsumerSecret(consumerSecret)
	            .setOAuthAccessToken(null)
	            .setOAuthAccessTokenSecret(null);
	    TwitterFactory tf = new TwitterFactory(cb.build());		
	    
	    twitter = tf.getInstance();
	    
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
            try {
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
            } catch (IOException e) {
            	if (logger.isDebugEnabled()) e.printStackTrace();
            	throw new FatalException("Can't read form URL", e);
            	
            }
            retryCount++;
        }
        logger.debug("Got access token.");
        logger.debug("Access token: " + accessToken.getToken());
        logger.debug("Access token secret: " + accessToken.getTokenSecret());
		
        return accessToken;
	}
	
	public void connect() {
		TwitterFactory tf = new TwitterFactory();
		if (twitter == null) twitter = tf.getInstance();
		
	}	
	
	public List<Status> getStatusByRegex(String regex, int limit) throws WarnException, FatalException {
		List<Status> retStr = new ArrayList<Status>();
		if (regex != null) regex = regex.toLowerCase();
		try {
			Query query = new Query();
			Paging paging = new Paging(1, limit);
			List<Status> statusList = twitter.getUserTimeline("TPSOperations",paging);
			for (int i=0;i<statusList.size();i++) {
				Status status = statusList.get(i);
//				logger.debug("Read status: "+status.getText());
				//set up the regex
				if (checkStatusRegexMatch(status, regex)) {
					logger.debug("Adding status:"+status.getId());
					retStr.add(status);
				}
			}
			
		} catch (TwitterException e) {
			if (logger.isDebugEnabled()) e.printStackTrace();
			throw new WarnException(e);
		}
		return retStr;
	}
	
	public boolean checkStatusRegexMatch(Status status, String regex) throws WarnException {
		if (regex == null) throw new WarnException("Regex is null");
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(status.getText().toLowerCase());
//		logger.debug(status.getText().toLowerCase());
		if (status.getId() == 674413700493651968L) {
			logger.debug("####################### JME ###########################");
			logger.debug(status.getText());
			logger.debug("####################### JME ###########################");
		}
		if (m.find()) {
			logger.debug("Matched Status: "+status.getText().toLowerCase());	
			return true;
		}
		return false;
	}
	
	public void disconnect() throws FatalException {
		twitter = null;
	}
	
	public void retweet(long statusId) throws WarnException, FatalException {
		if (twitter == null) throw new FatalException("Not connected");
		try {
			twitter.retweetStatus(statusId);
		} catch (TwitterException e) {
			if (logger.isDebugEnabled()) e.printStackTrace();
			throw new WarnException("Can't process retweet", e);
		}
	}
	
	public Status getStatusById(long statusId) throws WarnException, FatalException {
		if (twitter == null) throw new FatalException("Not connected");
		try {
			return twitter.showStatus(statusId);
		} catch (TwitterException e) {
			if (logger.isDebugEnabled()) e.printStackTrace();
			throw new WarnException("Can't process retweet", e);
		}
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
}
