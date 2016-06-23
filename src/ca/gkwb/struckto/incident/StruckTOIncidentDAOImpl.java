package ca.gkwb.struckto.incident;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import ca.gkwb.struckto.exception.GenericDBException;
import ca.gkwb.struckto.exception.NoRowFoundException;

public class StruckTOIncidentDAOImpl extends JdbcDaoSupport implements StruckTOIncidentDAO {
//	
	private final String INSERT_SQL = "INSERT INTO strucktodb.\"INCIDENT\"(" +
            "\"INCIDENT_TWEET_ID\", \"INCIDENT_SEVERITY\", \"INCIDENT_NEWS_URL\"," + 
            "\"INCIDENT_CREATE_DATE\", \"INCIDENT_ACTIVITY_DATE\", \"INDICDENT_LOCATION_ID\"," + 
            "\"INCIDENT_VERIFIED\") VALUES (?, ?, ?, ?, ?, ?, ? );";
	
	public StruckTOIncidentVO queryById(String id) throws GenericDBException {
		// TODO Auto-generated method stub
		return null;
	}

	public StruckTOIncidentVO queryByTwitterId(String id) throws NoRowFoundException, GenericDBException {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(StruckTOIncidentVO stVO) throws GenericDBException {
		// TODO Auto-generated method stub

	}

	public void insert(StruckTOIncidentVO stVO) throws GenericDBException {
		logger.debug("Inserting StruckTOIncidentVO : " + stVO);
		char verified = 'N';
		if (stVO.isVerified()) verified = 'Y';
		
		getJdbcTemplate().update(INSERT_SQL, stVO.getTweetId(), stVO.getSeverity(), stVO.getNewsUrl(), 
				stVO.getCreateDate(), stVO.getActivityDate(), stVO.getLocationId(), verified);
	}

	public List<StruckTOIncidentVO> queryAll(StruckTOIncidentVO stVO) throws NoRowFoundException, GenericDBException {
		// TODO Auto-generated method stub
		return null;
	}

}
