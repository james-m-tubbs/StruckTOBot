package ca.gkwb.struckto.location;

import java.util.List;

import com.google.maps.model.GeocodingResult;

import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;

public interface StruckTOLocationBO {
	
	public List<String> parseStreetLocation(String inputTweet);
	
	public void processOneTweet(String inputTweet) throws WarnException, FatalException;

	public List<GeocodingResult> getGeoData(List<String> intersection, String country, String city) throws WarnException;

}
