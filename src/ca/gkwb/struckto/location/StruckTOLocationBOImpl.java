package ca.gkwb.struckto.location;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import ca.gkwb.struckto.exception.FatalException;
import ca.gkwb.struckto.exception.WarnException;
import lombok.Setter;

public class StruckTOLocationBOImpl implements StruckTOLocationBO {

	private String intersectionRegex = "([A-Z]\\w+|[A-Z]\\w+ [A-Z]\\w+) (Ave*|Blvd|Rd|Sq|Cr+|St) *[NSEW]*";
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
    private	GeoApiContext context;
    @Setter
	private String apiKey;
	
	public StruckTOLocationBOImpl() {
		context = new GeoApiContext().setApiKey(apiKey);
	}
	
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

	@Override
	public void processOneTweet(String inputTweet) throws WarnException, FatalException {
		List<String> intersection = parseStreetLocation(inputTweet);

		
	}
	
	@Override
	public List<GeocodingResult> getGeoData(List<String> intersection, String country, String city ) throws WarnException {
		String address = "";
		for (int i=0; i<intersection.size(); i++) {
			if (address.length() > 0) address = address + " and ";
			address = address + intersection.get(i);
		}
		
		logger.debug("Searching for Address: " + address);
		
		try {		
			GeocodingResult[] results =  GeocodingApi.newRequest(context)
//				.components(
//					ComponentFilter.country(country),
//					ComponentFilter.administrativeArea(city))
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

}
