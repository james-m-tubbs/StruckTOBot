package ca.gkwb.struckto.location;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import ca.gkwb.struckto.exception.GenericDBException;

public class StruckTOLocationDAOImpl extends JdbcDaoSupport implements StruckTOLocationDAO {
	
	private JdbcTemplate conn;
	private final String INSERT_SQL = "";
	
//	private final String INSERT_SQL = "INSERT INTO strucktodb.\"INCIDENT\"(" +
//            "\"INCIDENT_TWEET_ID\", \"INCIDENT_SEVERITY\", \"INCIDENT_NEWS_URL\"," + 
//            "\"INCIDENT_CREATE_DATE\", \"INCIDENT_ACTIVITY_DATE\", \"INDICDENT_LOCATION_ID\"," + 
//            "\"INCIDENT_VERIFIED\") VALUES (?, ?, ?, ?, ?, ?, ? );";	
	
	private final String UPDATE_SQL = "";
	private final String QUERY_SQL = "SELECT " +
			   "LOCATION_ID," +
			   "LOCATION_LAT," +  
			   "LOCATION_LONG," +  
			   "LOCATION_CITY," + 
			   "LOCATION_PROV," + 
			   "LOCATION_COUNTRY," + 
			   "LOCATION_CREATE_DATE," + 
			   "LOCATION_USER," +			
			 "FROM LOCATION " +
			 "WHERE LOCATION_LAT = ? ";
	
	public StruckTOLocationDAOImpl(JdbcTemplate conn) {
		this.conn = conn;
	}
	
	public void insertStruckTOLocation(StruckTOLocationVO stlVO) throws GenericDBException {
	}

	public void updateStruckTOLocation(StruckTOLocationVO stlVO) throws GenericDBException {
		// TODO Auto-generated method stub

	}

	public StruckTOLocationVO queryOneLocation(int id) throws GenericDBException {
		
		try {
	        List<StruckTOLocationVO> result = conn.query(QUERY_SQL, new Object[] { id },
	        		(rs, rowNum) -> new StruckTOLocationVO(
	        			rs.getInt("LOCATION_ID"),	
	        			rs.getFloat("LOCATION_LAT"),
	        			rs.getFloat("LOCATION_LONG"),
	        			rs.getString("LOCATION_CITY"),
	        			rs.getString("LOCATION_PROV"),
	        			rs.getString("LOCATION_COUNTRY"),
	        			rs.getDate("LOCATION_CREATE_DATE"),
	        			rs.getString("LOCATION_USER")
	        		));
	        
	        //should only get one result
	        if (result.size() > 1) throw new GenericDBException("More than one result for " + id);
	        if (result.size() < 1) return null;
	        return result.get(0);
	        
       } catch (DataAccessException e) { 
    	   throw new GenericDBException(e);
       }
	};

}
