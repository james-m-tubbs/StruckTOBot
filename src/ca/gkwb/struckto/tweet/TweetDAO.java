package ca.gkwb.struckto.tweet;

import ca.gkwb.struckto.exception.GenericDBException;

public interface TweetDAO {
	
	public void insert(TweetVO sttVO) throws GenericDBException;
	
	public void update(TweetVO sttVO) throws GenericDBException;
	
	public void query(long tweetId) throws GenericDBException;

}
