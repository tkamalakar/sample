/**
 * 
 */
package sree.saavarni.service;

import java.util.Date;
import java.util.List;

import sree.saavarni.astro.BhavaSphuta;
import sree.saavarni.astro.GrahaSphuta;
import sree.saavarni.model.EventProfile;
import sree.saavarni.model.Location;
import sree.saavarni.model.Planet;
import sree.saavarni.model.PositionLords;
import sree.saavarni.model.Preference;
import sree.saavarni.model.Sign;
import sree.saavarni.model.Star;
import sree.saavarni.model.VimshottariDivision;

/**
 * @author tkamalakar
 *
 */
public interface SaavarniService {
	public Iterable<Planet> findAllPlanets();
	public Iterable<Sign> findAllSigns();
	public Iterable<Star> findAllStars();
	public Iterable<Location> findAllLocations();
	public Location findLocationByName(String name);
	public Iterable<PositionLords> findAllPositionLords();
	public Preference findPreference();
	public Preference savePreference(Preference pref);
	public PositionLords findPositionLords(double forLongitude);

	public List<VimshottariDivision> calculateVimshottariDasa(double moonPosition, Date eventDate);
	
	//public Preference loadSystemPreference();
	//public Preference getPreference() ;	
	public List<GrahaSphuta> servaGrahaSphuta(Preference preference, Date dateTime);
	public List<GrahaSphuta> servaGrahaSphuta(Preference preference, double longitude, double latitude, double tzOffset, Date dateTime);
	public List<BhavaSphuta> bhavaSphuta(Preference preference, double longitude, double latitude, double tzOffset, Date dateTime);
	public EventProfile getEventProfile(Preference preference, double longitude, double latitude, double tzOffset, Date dateTime);	
}
