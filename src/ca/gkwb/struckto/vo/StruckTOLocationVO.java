package ca.gkwb.struckto.vo;

import java.sql.Date;

public class StruckTOLocationVO {
	
	private String location;
	private float xCoords;
	private float yCoords;
	private String city;
	private String province;
	private String country;
	private Date createDate;
	private String user;

	public StruckTOLocationVO(String location, float xCoords, float yCoords,
			String city, String province, String country, Date createDate,
			String user) {
		super();
		this.location = location;
		this.xCoords = xCoords;
		this.yCoords = yCoords;
		this.city = city;
		this.province = province;
		this.country = country;
		this.createDate = createDate;
		this.user = user;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getxCoords() {
		return xCoords;
	}

	public void setxCoords(float xCoords) {
		this.xCoords = xCoords;
	}

	public float getyCoords() {
		return yCoords;
	}

	public void setyCoords(float yCoords) {
		this.yCoords = yCoords;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
