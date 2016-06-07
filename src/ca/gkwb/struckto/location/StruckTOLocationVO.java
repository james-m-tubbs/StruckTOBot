package ca.gkwb.struckto.location;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class StruckTOLocationVO {

	//"LOCATION_ID," +
	@Getter @Setter
	private int id;
	
   //"LOCATION_LAT," +
	@Getter @Setter
	private float lat;
	
//  "LOCATION_LONG," +
	@Getter @Setter
	private float lng;
	
//  "LOCATION_CITY," +
	@Getter @Setter
	private String city;
	
//  "LOCATION_PROV," +
	@Getter @Setter
	private String prov;
	
//  "LOCATION_COUNTRY," +
	@Getter @Setter
	private String country;
	
//  "LOCATION_CREATE_DATE," +
	@Getter @Setter
	private Date create_date;
	
//  "LOCATION_USER,"
	@Getter @Setter
	private String user;	
}
