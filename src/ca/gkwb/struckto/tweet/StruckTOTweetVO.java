package ca.gkwb.struckto.tweet;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class StruckTOTweetVO {
	
//	"TWEET_ID" integer NOT NULL PRIMARY KEY,
	@Getter @Setter
	private long tweetId;
//	"TWEET_URL" character varying,
	@Getter @Setter
	private String url;
//	"TWEET_ACCOUNT" character varying,
	@Getter @Setter
	private String account;
//	"TWEET_TIMESTAMP" date)
	@Getter @Setter
	private Date timestamp;
	@Override
	public String toString() {
		return "StruckTOTweetVO [tweetId=" + tweetId + ", url=" + url + ", account=" + account + ", timestamp="
				+ timestamp + "]";
	}
}
