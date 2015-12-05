package ca.gkwb.twitter.connector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.BasicAuth;
import com.twitter.hbc.httpclient.auth.OAuth1;

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
	
//	private Hosts hosebirdHosts;
//	private StatusesFilterEndpoint hosebirdEndpoint;
//	private Authentication hosebirdAuth;
	private Client hosebirdClient;
	
	public void connectOAuthSpring(List<Long> followings, List<String> terms)
			throws FatalException {
		connectOAuthParams(followings, terms, consumerKey, consumerSecret, token, secret);
		
	}	
	
	public void connectOAuthParams(List<Long> followings, List<String> terms,
			String consumerKey, String consumerSecret, String token,
			String secret) throws FatalException {
		/** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
		msgQueue = new LinkedBlockingQueue<String>(100000);
		eventQueue = new LinkedBlockingQueue<Event>(1000);

		/** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

		// Optional: set up some followings and track terms
		hosebirdEndpoint.followings(followings);
		hosebirdEndpoint.trackTerms(terms);

		// These secrets should be read from a config file
		Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);
		
		ClientBuilder builder = new ClientBuilder()
		  .name("StruckTOBotClient-01-OAuth")                              // optional: mainly for the logs
		  .hosts(hosebirdHosts)
		  .authentication(hosebirdAuth)
		  .endpoint(hosebirdEndpoint)
		  .processor(new StringDelimitedProcessor(msgQueue))
		  .eventMessageQueue(eventQueue);                          // optional: use this if you want to process client events

		hosebirdClient = builder.build();
		// Attempts to establish a connection.
		hosebirdClient.connect();		
		
	}
	
	public void connectBasicParams(List<Long> followings, List<String> terms,
			String username, String password) throws FatalException {
		/** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
		msgQueue = new LinkedBlockingQueue<String>(100000);
		eventQueue = new LinkedBlockingQueue<Event>(1000);

		/** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

		// Optional: set up some followings and track terms
		if (followings != null && followings.size() > 0) hosebirdEndpoint.followings(followings);
		if (terms != null && terms.size() > 0) hosebirdEndpoint.trackTerms(terms);

		// These secrets should be read from a config file
		logger.debug("Creating Twitter Auth - Basic");
		//Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);
		Authentication hosebirdAuth = new BasicAuth(username, password);
		
		logger.debug("Building Client - Basic");
		ClientBuilder builder = new ClientBuilder()
		  .name("StruckTOBotClient-01-Basic")                              // optional: mainly for the logs
		  .hosts(hosebirdHosts)
		  .authentication(hosebirdAuth)
		  .endpoint(hosebirdEndpoint)
		  .processor(new StringDelimitedProcessor(msgQueue))
		  .eventMessageQueue(eventQueue);                          // optional: use this if you want to process client events

		logger.debug("Building Client - Basic");
		hosebirdClient = builder.build();
		
		// Attempts to establish a connection.
		logger.debug("Connecting - Basic");
		hosebirdClient.connect();		
		
	}	
	
	public void disconnect() throws FatalException {
		hosebirdClient.stop();
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
}
