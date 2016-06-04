package ca.gkwb.struckto.tweet;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class StruckTOTweetVO {
	
//	"TWEET_ID" integer NOT NULL PRIMARY KEY,
	@Getter @Setter
	private int tweetId;
//	"TWEET_URL" character varying,
	@Getter @Setter
	private String url;
//	"TWEET_ACCOUNT" character varying,
	@Getter @Setter
	private String account;
//	"TWEET_TEXT" character varying,
	@Getter @Setter
	private String text;
//	"TWEET_TIMESTAMP" date)
	private Date timestamp;

}
