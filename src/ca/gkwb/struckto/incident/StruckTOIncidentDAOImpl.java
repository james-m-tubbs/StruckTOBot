package ca.gkwb.struckto.incident;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import ca.gkwb.struckto.exception.GenericDBException;
import ca.gkwb.struckto.exception.NoRowFoundException;

public class StruckTOIncidentDAOImpl extends JdbcDaoSupport implements StruckTOIncidentDAO {
//	
	private final String INSERT_SQL = "INSERT INTO strucktodb.\"INCIDENT\"(" +
            "\"INCIDENT_TWEET_ID\", \"INCIDENT_SEVERITY\", \"INCIDENT_NEWS_URL\"," + 
            "\"INCIDENT_CREATE_DATE\", \"INCIDENT_ACTIVITY_DATE\", \"INCIDENT_LOCATION_ID\"," + 
            "\"INCIDENT_VERIFIED\") VALUES (?, ?, ?, ?, ?, ?, ? );";
	
	private final String QUERY_SQL = "SELECT * FROM strucktodb.\"INCIDENT\" WHERE "
			+ "\"INCIDENT_ID\" = ?";
	
	public StruckTOIncidentVO queryById(int id) throws GenericDBException {
		return (StruckTOIncidentVO)getJdbcTemplate().queryForObject(QUERY_SQL, new Object[]{id}, new StruckTOIncidentRowMapper());
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
	
	public class StruckTOIncidentRowMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			boolean verified = false;
			if (rs.getString("INCIDENT_VERIFIED").equalsIgnoreCase("Y")) verified = true;
			StruckTOIncidentVO res = new StruckTOIncidentVO(
					rs.getInt("INCIDENT_ID"),
					rs.getLong("INCIDENT_TWEET_ID"),
					rs.getString("INCIDENT_SEVERITY"),
					rs.getString("INCIDENT_NEWS_URL"),
				    rs.getDate("INCIDENT_CREATE_DATE"),
				    rs.getDate("INCIDENT_ACTIVITY_DATE"),
				    rs.getInt("INCIDENT_LOCATION_ID"),
				    verified
					);
			return res;
		}
	}	

}
