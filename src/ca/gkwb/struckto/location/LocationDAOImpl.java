package ca.gkwb.struckto.location;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import ca.gkwb.struckto.exception.GenericDBException;

public class LocationDAOImpl extends JdbcDaoSupport implements LocationDAO {
	
	private final String INSERT_SQL = "INSERT INTO strucktodb.\"LOCATION\"(" +
            "\"LOCATION_LAT\", \"LOCATION_LONG\"," + 
            "\"LOCATION_CITY\", \"LOCATION_PROV\", \"LOCATION_COUNTRY\"," + 
            "\"LOCATION_CREATE_DATE\", \"LOCATION_USER\") VALUES (?, ?, ?, ?, ?, ?, ? );";	
	
	public void insertStruckTOLocation(LocationVO stlVO) throws GenericDBException {
		getJdbcTemplate().update(INSERT_SQL, stlVO.getLat(), stlVO.getLng(), 
				stlVO.getCity(), stlVO.getProv(), stlVO.getCountry(),
				stlVO.getCreate_date(), stlVO.getUser()); 		
	}	
	
	private final String QUERY_SQL = "SELECT " +
			   "\"LOCATION_ID\"," +
			   "\"LOCATION_LAT\"," +  
			   "\"LOCATION_LONG\"," +  
			   "\"LOCATION_CITY\"," + 
			   "\"LOCATION_PROV\"," + 
			   "\"LOCATION_COUNTRY\"," + 
			   "\"LOCATION_CREATE_DATE\"," + 
			   "\"LOCATION_USER\" " +			
			   "FROM strucktodb.\"LOCATION\" " +
			 "WHERE \"LOCATION_ID\" = ?";
	
	public LocationVO queryOneLocation(int id) throws GenericDBException {
		
		try {
	        LocationVO result = (LocationVO)getJdbcTemplate().queryForObject(QUERY_SQL, new Object[] { id }, new LocationRowMapper());
	        
	        //should only get one result
	        return result;
       } catch (DataAccessException e) { 
    	   throw new GenericDBException(e);
       }
	};	
	
	private final String QUERY_NEW_LOCATION = "SELECT " +
			   "\"LOCATION_ID\"," +
			   "\"LOCATION_LAT\"," +  
			   "\"LOCATION_LONG\"," +  
			   "\"LOCATION_CITY\"," + 
			   "\"LOCATION_PROV\"," + 
			   "\"LOCATION_COUNTRY\"," + 
			   "\"LOCATION_CREATE_DATE\"," + 
			   "\"LOCATION_USER\" " +				
			   "FROM strucktodb.\"LOCATION\" " +
			 "WHERE \"LOCATION_ID\" = (SELECT MAX(\"LOCATION_ID\") FROM strucktodb.\"LOCATION\")";
	
	@Override
	public LocationVO queryNewestLocation() throws GenericDBException {
		try {
	        LocationVO result = (LocationVO)getJdbcTemplate().queryForObject(QUERY_NEW_LOCATION, new Object[] {}, new LocationRowMapper());
	       return result;
	        
       } catch (DataAccessException e) { 
    	   throw new GenericDBException(e);
       }		
	}	

	public void updateStruckTOLocation(LocationVO stlVO) throws GenericDBException {
		// TODO Auto-generated method stub

	}
	
	private final String QUERY_LAT_LNG_SQL = "SELECT " +
			   "\"LOCATION_ID\"," +
			   "\"LOCATION_LAT\"," +  
			   "\"LOCATION_LONG\"," +  
			   "\"LOCATION_CITY\"," + 
			   "\"LOCATION_PROV\"," + 
			   "\"LOCATION_COUNTRY\"," + 
			   "\"LOCATION_CREATE_DATE\"," + 
			   "\"LOCATION_USER\" " +				
			 "FROM strucktodb.\"LOCATION\" " +
			 "WHERE \"LOCATION_LAT\" = ? " +
			 "AND \"LOCATION_LONG\" = ? ";	
	
	@Override
	public LocationVO queryByLatLng(Double lat, Double lng) throws GenericDBException {
		try {
	        LocationVO result = (LocationVO)getJdbcTemplate().queryForObject(QUERY_LAT_LNG_SQL, new Object[] {lat, lng}, new LocationRowMapper());
	       return result;
		} catch (EmptyResultDataAccessException e) {
			logger.debug("Empty data set returned");
			return null;
		} catch (DataAccessException e) { 
    	   throw new GenericDBException(e);
		}		
	}

	public class LocationRowMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new LocationVO(
        			rs.getInt("LOCATION_ID"),	
        			rs.getFloat("LOCATION_LAT"),
        			rs.getFloat("LOCATION_LONG"),
        			rs.getString("LOCATION_CITY"),
        			rs.getString("LOCATION_PROV"),
        			rs.getString("LOCATION_COUNTRY"),
        			rs.getDate("LOCATION_CREATE_DATE"),
        			rs.getString("LOCATION_USER"));
		}
	}		
	
}
