package sree.saavarni.model;

import swisseph.SweConst;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Preference {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private long id;

	public double tzOffset= 5.5;
	public double latitude=17.3850;
	public double longitude=78.4867;

	public boolean lookupBackwards = false;
	public int flags = SweConst.SEFLG_SWIEPH | SweConst.SEFLG_SIDEREAL | SweConst.SEFLG_TRANSIT_LONGITUDE;

	public int planetPosCalMethod = SweConst.SEFLG_TOPOCTR;
	public int ayanamsaCalMethod = SweConst.SE_SIDM_KRISHNAMURTI;
	public int houseCalMethod = SweConst.SE_HSYS_PLACIDUS;
	public int locationId;
	
	public Preference() {	}


	public Preference(double longitude, double latitude, double tzOffset) {
		this.tzOffset = tzOffset;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public boolean isLookupBackwards() {
		return lookupBackwards;
	}
	public void setLookupBackwards(boolean lookupBackwards) {
		this.lookupBackwards = lookupBackwards;
	}
	public int getFlags() {
		return flags;
	}
	public void setFlags(int flags) {
		this.flags = flags;
	}
	public int getPlanetPosCalMethod() {
		return planetPosCalMethod;
	}
	public void setPlanetPosCalMethod(int planetPosCalMethod) {
		this.planetPosCalMethod = planetPosCalMethod;
	}
	public int getAyanamsaCalMethod() {
		return ayanamsaCalMethod;
	}
	public void setAyanamsaCalMethod(int ayanamsaCalMethod) {
		this.ayanamsaCalMethod = ayanamsaCalMethod;
	}
	public int getHouseCalMethod() {
		return houseCalMethod;
	}
	public void setHouseCalMethod(int houseCalMethod) {
		this.houseCalMethod = houseCalMethod;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	} 

}
