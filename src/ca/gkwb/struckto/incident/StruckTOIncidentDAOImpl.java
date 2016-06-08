package ca.gkwb.struckto.incident;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import ca.gkwb.struckto.exception.GenericDBException;
import ca.gkwb.struckto.exception.NoRowFoundException;

public class StruckTOIncidentDAOImpl implements StruckTOIncidentDAO {

	private JdbcTemplate conn;
	
	private final String INSERT_SQL = "INSERT INTO INCIDENT (INCIDENT_TWEET_ID, INCIDENT_SEVERITY, INCIDENT_NEWS_URL" +
			"INCIDENT_CREATE_DATE, INCIDENT_ACTIVITY_DATE, INDICDENT_LOCATION_ID, INCIDENT_VERIFIED)" +
			"values " +
		"?, ?, ?, ?, ?, ?, ?, ?";	
	
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
		conn.update(INSERT_SQL, stVO.getTweetId(), stVO.getSeverity(), stVO.getNewsUrl(), 
				stVO.getCreateDate(), stVO.getActivityDate(), stVO.getLocationId(), stVO.isVerified());
	}

	public List<StruckTOIncidentVO> queryAll(StruckTOIncidentVO stVO) throws NoRowFoundException, GenericDBException {
		// TODO Auto-generated method stub
		return null;
	}

}
