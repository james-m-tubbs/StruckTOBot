package ca.gkwb.struckto.tweet;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import ca.gkwb.struckto.exception.GenericDBException;

public class TweetDAOImpl extends JdbcDaoSupport implements TweetDAO {

	private final String INSERT_SQL = "INSERT INTO strucktodb.\"TWEET\"("+
            "\"TWEET_ID\", \"TWEET_URL\", \"TWEET_ACCOUNT\", \"TWEET_TIMESTAMP\") "+
            "VALUES (?, ?, ?, ?)";

	@Override
	public void insert(TweetVO sttVO) throws GenericDBException {
		try {
		getJdbcTemplate().update(INSERT_SQL, sttVO.getTweetId(), sttVO.getUrl(), 
				sttVO.getAccount(), sttVO.getTimestamp());
		} catch (DuplicateKeyException e) {
			logger.info("Tweet Exists in DB");
		}
	}

	@Override
	public void update(TweetVO sttVO) throws GenericDBException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void query(long tweetId) throws GenericDBException {
		// TODO Auto-generated method stub
		
	}
	


}
