package ca.gkwb.struckto.location;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class LocationVO {

	//"LOCATION_ID," +
	@Getter @Setter
	private int id;
	
   //"LOCATION_LAT," +
	@Getter @Setter
	private double lat;
	
//  "LOCATION_LONG," +
	@Getter @Setter
	private double lng;
	
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

	@Override
	public String toString() {
		return "StruckTOLocationVO [id=" + id + ", lat=" + lat + ", lng=" + lng + ", city=" + city + ", prov=" + prov
				+ ", country=" + country + ", create_date=" + create_date + ", user=" + user + "]";
	}	
}
