package sree.saavarni.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Planet {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String name;
    private String shortName;
    private int epheCode;
    private String type;
    private String deity;
    private int unicode;
    private String color;
    private int vimshottariPeriod;
    private int nextVimshottariLord;
    
    @Transient private double longitude;
    @Transient private double latitude;
    @Transient private PositionLords positionLords;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public int getEpheCode() {
		return epheCode;
	}
	public void setEpheCode(int epheCode) {
		this.epheCode = epheCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDeity() {
		return deity;
	}
	public void setDeity(String deity) {
		this.deity = deity;
	}	
	public int getUnicode() {
		return unicode;
	}
	public void setUnicode(int unicode) {
		this.unicode = unicode;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getVimshottariPeriod() {
		return vimshottariPeriod;
	}
	public void setVimshottariPeriod(int vimshottariPeriod) {
		this.vimshottariPeriod = vimshottariPeriod;
	}
	public int getNextVimshottariLord() {
		return nextVimshottariLord;
	}
	public void setNextVimshottariLord(int nextVimshottariLord) {
		this.nextVimshottariLord = nextVimshottariLord;
	}
	public PositionLords getPositionLords() {
		return positionLords;
	}
	public void setPositionLords(PositionLords positionLords) {
		this.positionLords = positionLords;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getSubSubArc() {
		double val = (this.longitude-this.positionLords.getStartDegree())*100/(this.positionLords.getEndDegree()-this.positionLords.getStartDegree());
		return String.format("%04.2f", val);
		
	}

}
