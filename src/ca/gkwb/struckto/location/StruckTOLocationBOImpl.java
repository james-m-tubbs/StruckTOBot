package ca.gkwb.struckto.location;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class StruckTOLocationBOImpl implements StruckTOLocationBO {

	private String intersectionRegex = "([A-Z]\\w+|[A-Z]\\w+ [A-Z]\\w+) (Ave*|Blvd|Rd|Sq|Cr+|St) *[NSEW]*";
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
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

}
