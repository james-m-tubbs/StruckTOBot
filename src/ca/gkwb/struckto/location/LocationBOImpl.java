package ca.gkwb.struckto.location;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.GeocodingResult;

import ca.gkwb.struck.exception.db.GenericDBException;
import ca.gkwb.struck.location.dao.LocationDAO;
import ca.gkwb.struck.location.dao.LocationVO;
import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;
import lombok.Setter;

public class LocationBOImpl implements LocationBO {

	private String intersectionRegex = "([A-Z]\\w+|[A-Z]\\w+ [A-Z]\\w+) ([Aa]ve*|Bl|Blvd|Rd|Sq|Cr+|St|Tr|Dr|Road) *[NSEW]*";
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
    private	GeoApiContext context;
    private String country;
    private String city;
    
    @Setter
    private LocationDAO stlDAO;
    
    @Setter
    private boolean dbFlag = true;
    
    @Setter
    private boolean mapFlag = true;
	
	public LocationBOImpl(String apiKey, String city, String country) {
		context = new GeoApiContext().setApiKey(apiKey);
		this.city = city;
		this.country = country;
	}
	
	/**
	 * Returns an intersection list (two values) based on input tweet text
	 * 
	 * @author g1ng3rk1d
	 * @date 2016-06-22
	 * @param inputTweet
	 * @return List<String>
	 */	
	@Override
	public List<String> parseStreetLocation(String inputTweet) {
		logger.debug("Parsing Location from input tweet:");
		logger.debug(inputTweet);
		
		Pattern p = Pattern.compile(intersectionRegex);
		Matcher m = p.matcher(inputTweet);
		
		List<String> results = new ArrayList<String>();
		
		while (m.find()) {
			logger.debug("Matched Regex: " + m.group(0));
			results.add(m.group(0));
		}
		return results;
	}
	
	/**
	 * Returns a list of GeocodingResults based on input intersection String (ex. List = {"Yonge St","Sheppard Ave"},
	 * input country, and input city
	 * 
	 * @author g1ng3rk1d
	 * @date 2016-06-22
	 * @param intersection
	 * @param country
	 * @param city
	 * @return
	 * @throws WarnException
	 */	
	@Override
	public List<GeocodingResult> getGeoData(List<String> intersection, String city, String country ) throws WarnException {
		//rebuild address into single string
		String address = "";
		for (int i=0; i<intersection.size(); i++) {
			if (address.length() > 0) address = address + " and ";
			address = address + intersection.get(i);
		}
		
		logger.debug("Searching for Address: " + address);
		
		try {		
			GeocodingResult[] results =  GeocodingApi.newRequest(context)
				.components(
					ComponentFilter.country(country),
					ComponentFilter.administrativeArea(city))
			.address(address).await();
			
			//add results to list
			List<GeocodingResult> resultList = new ArrayList<GeocodingResult>();
			for (int i=0;i<results.length;i++) {
				resultList.add(results[i]);
			}
			logger.debug("Created list: " + resultList);
			return resultList;
		} catch (Exception e) {
			logger.debug("Error with Google Maps Query", e);
			throw new WarnException(e);
		}
	}
	
	/**
	 * Processes one record based on input tweet text. Depending on enabled features, will
	 * parse the location LAT+LNG, save to DB, drop a pin on the associated map
	 * 
	 * @author g1ng3rk1d
	 * @date 2016-06-22
	 * @param inputTweet
	 * @throws WarnException
	 * @throws FatalException
	 */	
	@Override
	public LocationVO processOneTweet(String tweetText) throws WarnException, FatalException {
		List<String> intersection = parseStreetLocation(tweetText);
		List<GeocodingResult> res = getGeoData(intersection, city, country);
		logger.debug("Geocode Results: " + res);
		GeocodingResult topRes = null;
		if (res.size() > 0)  topRes = res.get(0);
		LocationVO stlVO = null;
		//create geolocation object
		if (dbFlag && topRes != null) {	
			Calendar cal = Calendar.getInstance();
			Date sqlDate = new Date(cal.getTime().getTime());
			stlVO = new LocationVO(
					-1,
					topRes.geometry.location.lat,
					topRes.geometry.location.lng,
					city,
					"ON",
					country,
					sqlDate,
					"StruckTOBot"
					);
			logger.debug("Created stlVO: " + stlVO);
			try {
				LocationVO checkLocationExists = stlDAO.queryByLatLng(stlVO.getLat(), stlVO.getLng());
				if (checkLocationExists != null) {
					logger.debug("Found existing loc: " + checkLocationExists);
					return checkLocationExists;
				}
				
				//if doesn't exist, insert and return newest
				stlDAO.insertStruckTOLocation(stlVO);
				stlVO = stlDAO.queryNewestLocation();
			} catch (GenericDBException e) {
				logger.error("Couldn't insert stlVO", e);
				throw new FatalException(e);
			}
		}
		return stlVO;		
	}	

}
