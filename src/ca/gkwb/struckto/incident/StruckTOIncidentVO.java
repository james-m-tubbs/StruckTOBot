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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class StruckTOIncidentVO {

//  "INCIDENT_ID" serial NOT NULL,
	@Getter @Setter
	private Integer incidentId;
//  "INCIDENT_TWEET_ID" integer,
	@Getter @Setter
	private long tweetId;
//  "INCIDENT_SEVERITY" character varying,
	@Getter @Setter
	private String severity;
//  "INCIDENT_NEWS_URL" character varying,
	@Getter @Setter
	private String newsUrl;
//  "INCIDENT_CREATE_DATE" date NOT NULL,
	@Getter @Setter
	private Date createDate;
//  "INCIDENT_ACTIVITY_DATE" date NOT NULL,
	@Getter @Setter
	private Date activityDate;
//  "INDICDENT_LOCATION_ID" integer,\
	@Getter @Setter
	private Integer locationId;
//  "INCIDENT_VERIFIED" char[1],
	@Getter @Setter
	private boolean verified;
	
	@Getter
	public final static String SEVERITY_FATAL = "FATAL";
	@Getter
	public final static String SEVERITY_SEVERE = "SEVERE";
	@Getter
	public final static String SEVERITY_MINOR = "MINOR";	
	@Getter
	public final static String SEVERITY_UNKNOWN = "UNKNOWN";
}
