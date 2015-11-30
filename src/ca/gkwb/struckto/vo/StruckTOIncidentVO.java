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

package ca.gkwb.struckto.vo;

import java.sql.Date;

public class StruckTOIncidentVO {
	
	private String id;
	private Date activityDate;
	private Date createDate;
	private String newsUrl;
	private String tweetUrl;
	private String severity;
	private String source;
	private String user;
	private StruckTOLocationVO location;
	
	public final static String FATAL = "FATAL";
	public final static String SEVERE = "SEVERE";
	public final static String MINOR = "MINOR";	

	public StruckTOIncidentVO(String id, Date activityDate, Date createDate,
			String newsUrl, String tweetUrl, String severity, String source,
			String user, StruckTOLocationVO location) {
		super();
		this.id = id;
		this.activityDate = activityDate;
		this.createDate = createDate;
		this.newsUrl = newsUrl;
		this.tweetUrl = tweetUrl;
		this.severity = severity;
		this.source = source;
		this.user = user;
		this.location = location;
	}

	public StruckTOLocationVO getLocation() {
		return location;
	}

	public void setLocation(StruckTOLocationVO location) {
		this.location = location;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}
	public String getNewsUrl() {
		return newsUrl;
	}
	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}
	public String getTweetUrl() {
		return tweetUrl;
	}
	public void setTweetUrl(String tweetUrl) {
		this.tweetUrl = tweetUrl;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
