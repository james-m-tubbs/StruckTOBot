package ca.gkwb.struckto.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class StruckTOLocationVO {
	
	@Getter @Setter
	private String location;
	@Getter @Setter
	private float xCoords;
	@Getter @Setter
	private float yCoords;
	@Getter @Setter
	private String city;
	@Getter @Setter
	private String province;
	@Getter @Setter
	private String country;
	@Getter @Setter
	private Date createDate;
	@Getter @Setter
	private String user;
}
