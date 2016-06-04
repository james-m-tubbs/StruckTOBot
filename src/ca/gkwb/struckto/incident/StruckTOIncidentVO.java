/**
 * This object represents the database model for a struckTO object.
 * 
 * String id - Database key
 * 
 * Date activityDate - last date the record was updated
 * 
 * Date createDate - Date the record was created
 * 
 * String newsUrl - link to a news URL about the tweet (possibly null)
 * 
 * String tweetUrl - URL of the tweet
 * 
 * String severity - severity of the collision (this.FATAL, this.SEVERE, this.MINOR)
 * 
 * String source - Source for the entry (usually TPSOperations)
 * 
 * String user - User (source) 
 * 
 * String location - location of the collision (eg. yonge/bloor)
 * 
 * float xCoords - x coordinates 
 * 
 * float yCoords - y coordinates
 * 
 * @author gingerk1d
 * @date 2015-11-30
 * 
 */

package ca.gkwb.struckto.incident;

import java.sql.Date;

import ca.gkwb.struckto.location.StruckTOLocationVO;
import ca.gkwb.struckto.tweet.StruckTOTweetVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class StruckTOIncidentVO {
	@Getter @Setter
	private String id;
	@Getter @Setter
	private String tweetId;
	@Getter @Setter
	private Date activityDate;
	@Getter @Setter
	private Date createDate;
	@Getter @Setter
	private String newsUrl;
	@Getter @Setter
	private String tweetUrl;
	@Getter @Setter
	private String severity;
	@Getter @Setter
	private String source;
	@Getter @Setter
	private String user;
	@Getter @Setter
	private StruckTOLocationVO location;
	@Getter @Setter
	private StruckTOTweetVO tweet;
	
	@Getter
	public final static String FATAL = "FATAL";
	@Getter
	public final static String SEVERE = "SEVERE";
	@Getter
	public final static String MINOR = "MINOR";	
}
