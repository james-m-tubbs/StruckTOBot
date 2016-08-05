package ca.gkwb.struckto.location;

import java.util.List;

import com.google.maps.model.GeocodingResult;

import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;

public interface LocationBO {
	
	/**
	 * Returns an intersection list (two values) based on input tweet text
	 * 
	 * @author g1ng3rk1d
	 * @date 2016-06-22
	 * @param inputTweet
	 * @return List<String>
	 */
	public List<String> parseStreetLocation(String inputTweet);

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
	public List<GeocodingResult> getGeoData(List<String> intersection, String country, String city) throws WarnException;
	
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
	public LocationVO processOneTweet(String inputTweet) throws WarnException, FatalException;	

}
