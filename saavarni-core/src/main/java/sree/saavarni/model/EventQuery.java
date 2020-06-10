package sree.saavarni.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EventQuery {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String Id;
	private String name;
	private String gender;
	private double tzOffset= 5.5;
	private double latitude;
	private double longitude;
	private Date eventDateTime;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public double getTzOffset() {
		return tzOffset;
	}
	public void setTzOffset(double tzOffset) {
		this.tzOffset = tzOffset;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Date getEventDateTime() {
		return eventDateTime;
	}
	public void setEventDateTime(Date eventDateTime) {
		this.eventDateTime = eventDateTime;
	}

}
