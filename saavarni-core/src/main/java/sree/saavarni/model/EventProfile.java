package sree.saavarni.model;

import java.util.ArrayList;
import java.util.List;

import sree.saavarni.astro.BhavaSphuta;
import sree.saavarni.astro.GrahaSphuta;

public class EventProfile extends EventQuery {
	
	private List<House> houseList = new ArrayList<House>();
	private List<Planet> planetList = new ArrayList<Planet>();
	public List<House> getHouseList() {
		return houseList;
	}
	public void setHouseList(List<House> houseList) {
		this.houseList = houseList;
	}
	public List<Planet> getPlanetList() {
		return planetList;
	}
	public void setPlanetList(List<Planet> planetList) {
		this.planetList = planetList;
	}
	
	
	

}
