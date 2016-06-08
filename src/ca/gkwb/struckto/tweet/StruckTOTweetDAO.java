package ca.gkwb.struckto.tweet;

import ca.gkwb.struckto.exception.GenericDBException;

public interface StruckTOTweetDAO {
	
	public void insert(StruckTOTweetVO sttVO) throws GenericDBException;
	
	public void update(StruckTOTweetVO sttVO) throws GenericDBException;
	
	public void query(long tweetId) throws GenericDBException;

}
