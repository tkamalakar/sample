package sree.saavarni.model;

public class House {
	private String name;
	private double longitude;
	private PositionLords positionLords;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public PositionLords getPositionLords() {
		return positionLords;
	}
	public void setPositionLords(PositionLords positionLords) {
		this.positionLords = positionLords;
	}

	public String getSubSubArc() {
		double val = (this.longitude-this.positionLords.getStartDegree())*100/(this.positionLords.getEndDegree()-this.positionLords.getStartDegree());
		return String.format("%04.2f", val);

	}
}
