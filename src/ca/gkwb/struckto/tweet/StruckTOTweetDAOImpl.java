package ca.gkwb.struckto.tweet;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import ca.gkwb.struckto.exception.GenericDBException;
import ca.gkwb.struckto.location.StruckTOLocationDAO;
import ca.gkwb.struckto.location.StruckTOLocationVO;

public class StruckTOTweetDAOImpl extends JdbcDaoSupport implements StruckTOTweetDAO {

	private final String INSERT_SQL = "INSERT INTO strucktodb.\"TWEET\"("+
            "\"TWEET_ID\", \"TWEET_URL\", \"TWEET_ACCOUNT\", \"TWEET_TIMESTAMP\") "+
            "VALUES (?, ?, ?, ?)";

	@Override
	public void insert(StruckTOTweetVO sttVO) throws GenericDBException {
		getJdbcTemplate().update(INSERT_SQL, sttVO.getTweetId(), sttVO.getUrl(), 
				sttVO.getAccount(), sttVO.getTimestamp()); 
	}

	@Override
	public void update(StruckTOTweetVO sttVO) throws GenericDBException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void query(long tweetId) throws GenericDBException {
		// TODO Auto-generated method stub
		
	}
	


}
