/**
 * 
 */
package sree.saavarni.service;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sree.saavarni.astro.BhavaSphuta;
import sree.saavarni.astro.CelestialEngine;
import sree.saavarni.astro.GrahaSphuta;
import sree.saavarni.data.*;
import sree.saavarni.model.*;
import sree.saavarni.service.SaavarniService;


/**
 * @author tkamalakar
 *
 */
@Service("saavarniService")
public class SaavarniServiceImpl implements SaavarniService {

	@Autowired
	PlanetRepo planetRepo;    
	@Autowired
	SignRepo signRepo;    
	@Autowired
	StarRepo starRepo;    
	@Autowired
	PositionLordsRepo positionLordsRepo;
	@Autowired
	LocationRepo locationRepo;
	@Autowired
	PreferenceRepo preferenceRepo;

	/** 
	 * Fetch seed data, from database
	 */
	public Iterable<Planet> findAllPlanets(){ return planetRepo.findAll(); }
	public Iterable<Sign> findAllSigns(){ return signRepo.findAll(); }
	public Iterable<Star> findAllStars(){ return starRepo.findAll(); }
	public Iterable<Location> findAllLocations(){ return locationRepo.findAll(); }
	public Location findLocationByName(String name){ 
		return locationRepo.findByName(name); 
	}
	public Iterable<PositionLords> findAllPositionLords(){ return positionLordsRepo.findAll(); }

	public PositionLords findPositionLords(double forLongitude) {
		return positionLordsRepo.findByStartDegreeLessThanAndEndDegreeGreaterThanEqual(forLongitude, forLongitude);
	};

	public Preference findPreference(){ return preferenceRepo.findById(1); }
	public Preference savePreference(Preference pref) {
		pref.setId(1);
		return preferenceRepo.save(pref);
	}


	private List<GrahaSphuta> servaGrahaSphuta(CelestialEngine ce, Date dateTime){
		List<GrahaSphuta> grahaSphutaList = new ArrayList<GrahaSphuta>();

		int[] planetList = {0,1,4,2,5,3,6,11,7,8,9};
		for(int epheCode: planetList) {

			Calendar eventCalDate = new GregorianCalendar();
			eventCalDate.setTime(dateTime);

			GrahaSphuta gs = ce.grahaSphuta(eventCalDate, epheCode);	
			grahaSphutaList.add(gs);

			//if the currentNode is rahu then derive ketu node
			if(epheCode==11) {
				gs.setName("Rahu");
				GrahaSphuta ketuNode = computeKetuNode(gs);	
				grahaSphutaList.add(ketuNode);						
			}
		}	

		return grahaSphutaList;
	}

	public List<GrahaSphuta> servaGrahaSphuta(Preference preference, Date dateTime) {

		//Planet positions for eventDate and Place
		CelestialEngine ce = new CelestialEngine(preference.getLongitude(), preference.getLatitude(),preference.getTzOffset());
		ce.setAyanamsaCalMethod(preference.getAyanamsaCalMethod());
		ce.setHouseCalMethod(preference.getHouseCalMethod());
		ce.setPlanetPosCalMethod(preference.getPlanetPosCalMethod());
		ce.flags = preference.flags;
		ce.lookupBackwards = preference.isLookupBackwards();

		return servaGrahaSphuta(ce, dateTime);
	}

	public List<GrahaSphuta> servaGrahaSphuta(Preference preference, double longitude, double latitude, double tzOffset, Date dateTime) {

		//Planet positions for eventDate and Place
		CelestialEngine ce = new CelestialEngine(longitude, latitude, tzOffset);
		ce.setAyanamsaCalMethod(preference.getAyanamsaCalMethod());
		ce.setHouseCalMethod(preference.getHouseCalMethod());
		ce.setPlanetPosCalMethod(preference.getPlanetPosCalMethod());
		ce.flags = preference.flags;
		ce.lookupBackwards = preference.isLookupBackwards();

		return servaGrahaSphuta(ce, dateTime);
	}

	public List<BhavaSphuta> bhavaSphuta(Preference preference, double longitude, double latitude, double tzOffset, Date dateTime) {

		List<BhavaSphuta> bhavaSphutaList = new ArrayList<BhavaSphuta>();

		//Planet positions for eventDate and Place
		CelestialEngine ce = new CelestialEngine(longitude, latitude, tzOffset);
		ce.setAyanamsaCalMethod(preference.getAyanamsaCalMethod());
		ce.setHouseCalMethod(preference.getHouseCalMethod());
		ce.setPlanetPosCalMethod(preference.getPlanetPosCalMethod());
		ce.flags = preference.flags;
		ce.lookupBackwards = preference.isLookupBackwards();

		Calendar calDateTime = new GregorianCalendar();
		calDateTime.setTime(dateTime);

		double[] sphutaList = ce.bhavaSphuta(calDateTime);
		String[] romans = new String[] { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII"};

		for(int i=1; i<sphutaList.length; i++) {
			BhavaSphuta bs = new BhavaSphuta();
			bs.setName(romans[i-1]);
			bs.setLongitude(sphutaList[i]);

			bhavaSphutaList.add(bs);
		}
		return bhavaSphutaList;		
	}

	public EventProfile getEventProfile(Preference preference, double longitude, double latitude, double tzOffset, Date dateTime) {
System.out.println("***** serviceImpl date:" + dateTime.toString());		
		EventProfile eProfile = new EventProfile();
		List<GrahaSphuta> grahaSphutaList = this.servaGrahaSphuta(preference, longitude, latitude, tzOffset, dateTime);
		for(GrahaSphuta grahaSphuta: grahaSphutaList) {
			Planet planet = planetRepo.findByName(grahaSphuta.getName());
			planet.setLongitude(grahaSphuta.getLongitude());
			planet.setLatitude(grahaSphuta.getLatitude());
			planet.setPositionLords(this.positionLordsRepo.findByStartDegreeLessThanAndEndDegreeGreaterThanEqual(grahaSphuta.getLongitude(),grahaSphuta.getLongitude()));
			
			eProfile.getPlanetList().add(planet);			
		}
		
		List<BhavaSphuta> bhavaSphutaList = this.bhavaSphuta(preference, longitude, latitude, tzOffset, dateTime);
		for(BhavaSphuta bhavaSphuta: bhavaSphutaList) {
			House house = new House();
			
			house.setLongitude(bhavaSphuta.getLongitude());
			house.setName(bhavaSphuta.getName());
			house.setPositionLords(this.positionLordsRepo.findByStartDegreeLessThanAndEndDegreeGreaterThanEqual(bhavaSphuta.getLongitude(),bhavaSphuta.getLongitude()));
			
			eProfile.getHouseList().add(house);			
		}
		
		return eProfile;

	
	}

	
	private GrahaSphuta computeKetuNode(GrahaSphuta rahuNode) {
		GrahaSphuta ketuNode = new GrahaSphuta();

		ketuNode.setDateTime(rahuNode.getDateTime());
		ketuNode.setAyanamsaId(rahuNode.getAyanamsaId());
		ketuNode.setAyanamsa(rahuNode.getAyanamsa());
		ketuNode.setTzOffset(rahuNode.getTzOffset());
		ketuNode.setName("Ketu");		
		ketuNode.setLatitude(rahuNode.getLatitude());
		ketuNode.setDistance(rahuNode.getDistance());
		ketuNode.setLongitudeSpeed(rahuNode.getLongitudeSpeed());
		ketuNode.setLatitudeSpeed(rahuNode.getLatitudeSpeed());
		ketuNode.setDistanceSpeed(rahuNode.getDistanceSpeed());

		if(rahuNode.getLongitude()>180) {
			ketuNode.setLongitude(rahuNode.getLongitude()-180);
		} else {
			ketuNode.setLongitude(rahuNode.getLongitude()+180);
		}

		return ketuNode;
	}

	/**
	 * Read preferences from system - properties file
	 */
	Preference preference =null;
	public Preference getPreference() {
		if(this.preference==null) {
			this.preference = loadSystemPreference();
		}
		return this.preference;
	}
	public Preference loadSystemPreference() { 

		InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");	    

		Properties p=new Properties(); 

		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		double latitude= Double.parseDouble(p.getProperty("LATITUDE"));
		double longitude= Double.parseDouble(p.getProperty("LONGITUDE"));
		double tzOffset= Double.parseDouble(p.getProperty("TZ_OFFSET"));
		int planetPosCalMethod = Integer.parseInt(p.getProperty("PLANET_POS_CAL_METHOD"));
		int ayanamsaCalMethod = Integer.parseInt(p.getProperty("AYANAMSA_CAL_METHOD"));
		int houseCalMethod = Integer.parseInt(p.getProperty("HOUSE_CAL_METHOD"));

		Preference systemPref = new Preference(longitude,latitude,tzOffset);
		systemPref.setPlanetPosCalMethod(planetPosCalMethod);
		systemPref.setAyanamsaCalMethod(ayanamsaCalMethod);
		systemPref.setHouseCalMethod(houseCalMethod);

		return systemPref;
	}
	
	public List<VimshottariDivision> calculateVimshottariDasa(double moonPosition, Date eventDate) {
		PositionLords pl = this.findPositionLords(moonPosition);
//System.out.println("***** PositionLord star/starLord:"+ pl.getStar() + " - " + pl.getStarLord());		
		
		double moonPositionMins = moonPosition*60;
		
		int completedStars = (int) moonPositionMins/800 ;
		double dasaCovered = moonPositionMins%800 ;
		double dasaRemaining = (800-dasaCovered)%800;
//System.out.println("***** starsPassed:"+ completedStars + " dasaCovered (%):" + dasaCovered + " (" + (dasaCovered/8) +"%) currentPosition:" + toDMS(moonPosition));
//System.out.println("***** dasaRemaining(%):"+ dasaRemaining + " ( " + (dasaRemaining/8) + "%)");

		int noDaysInYear = 365;
		int totalVimshottariDays = 120 * noDaysInYear;

		Planet lord = planetRepo.findByName(pl.getStarLord());
		int totalDaysForCurrentLordDasa = lord.getVimshottariPeriod()*noDaysInYear;
		int daysLapsedForCurrentLord = (int) ((dasaCovered/8) * totalDaysForCurrentLordDasa/100);
		int daysRemainingForCurrentLord = (int) ((dasaRemaining/8) * totalDaysForCurrentLordDasa/100);
//System.out.println("***** lord:" + lord.getName() + " lord Period:" + lord.getVimshottariPeriod() + " nextLord:" + lord.getNextVimshottariLord());
//System.out.println("***** totalDaysForCurrentLordDasa:" + totalDaysForCurrentLordDasa + " daysLapsedForCurrentLord:" + daysLapsedForCurrentLord + " daysRemainingForCurrentLord:" +daysRemainingForCurrentLord);

		//Date dasaCalStartDate = currentDate.;
		Calendar calStartDate = Calendar.getInstance(); 
		calStartDate.setTime(eventDate);
		
		calStartDate.add(Calendar.DAY_OF_MONTH, (-1)*daysLapsedForCurrentLord);
		
		Calendar calEndDate = Calendar.getInstance();
		calEndDate.setTime(calStartDate.getTime());
		calEndDate.add(Calendar.YEAR, lord.getVimshottariPeriod());
//System.out.println("***** Janma dasa - Lord:" + lord.getName() + "; dasa start:"+ calStartDate.getTime() + "; end:" + calEndDate.getTime());


Calendar calEndDate2 = Calendar.getInstance();
calEndDate2.setTime(eventDate);
calEndDate2.add(Calendar.DAY_OF_MONTH, daysRemainingForCurrentLord);
//System.out.println("***** Janma dasa2 - Lord:" + lord.getName() + "; dasa start:"+ calStartDate.getTime() + "; end:" + calEndDate2.getTime());

		List<VimshottariDivision> vimList = calculateVimshottariDasa(lord.getId(), calStartDate.getTime(), (120*365*24), VimshottariDivision.DIVISION_TYPE_DASA);
	
		return vimList;
	}
	
	private List<VimshottariDivision> calculateVimshottariDasa(long startLordId, Date startDate, double vimshottariPeriod, String divisionType) {
		//int vimshottariPeriod = 120; //in years
		Calendar calStartDate = Calendar.getInstance();
		Calendar calEndDate = Calendar.getInstance();

		List<VimshottariDivision> vimList = new ArrayList<VimshottariDivision>();
		long nextLord = startLordId;
		calStartDate.setTime(startDate);
		calEndDate.setTime(calStartDate.getTime());
		
		while(true) {

			Planet lord = planetRepo.findById(nextLord);

			double lordPeriod = (lord.getVimshottariPeriod()/120.0)*vimshottariPeriod;
			nextLord = lord.getNextVimshottariLord();
			calStartDate.setTime(calEndDate.getTime());			
			//calEndDate.add(Calendar.YEAR, (int) lordPeriod);
			//calEndDate.add(Calendar.DAY_OF_MONTH, (int) lordPeriod);
			//calEndDate.add(Calendar.HOUR_OF_DAY, (int) (lordPeriod));
			calEndDate.add(Calendar.HOUR_OF_DAY, (int) (lordPeriod));

System.out.println("***** Division Type::" + divisionType + " lord:" + lord.getName() + " vimsottariPeriod * currentLordPeriod = divPeriod:" + vimshottariPeriod +" * " + lord.getVimshottariPeriod() + " = " + lordPeriod + " startDate:" + calStartDate.getTime()  + " endDate:" +  calEndDate.getTime()) ;			
			VimshottariDivision vDiv = new VimshottariDivision(divisionType, lord.getName(), calStartDate.getTime(), calEndDate.getTime());
			
			
			if(divisionType.equalsIgnoreCase(VimshottariDivision.DIVISION_TYPE_DASA)) {
				vDiv.setSubDivisionList( calculateVimshottariDasa(lord.getId(), calStartDate.getTime(), lordPeriod, VimshottariDivision.DIVISION_TYPE_BHUKTI));				
			} else if(divisionType.equalsIgnoreCase(VimshottariDivision.DIVISION_TYPE_BHUKTI)) {
				vDiv.setSubDivisionList( calculateVimshottariDasa(lord.getId(), calStartDate.getTime(), lordPeriod, VimshottariDivision.DIVISION_TYPE_ANTRA));				
			} /*else if(divisionType.equalsIgnoreCase(VimshottariDivision.DIVISION_TYPE_ANTRA)) {
				vDiv.setSubDivisionList( calculateVimshottariDasa(lord.getId(), calStartDate.getTime(), lordPeriod, VimshottariDivision.DIVISION_TYPE_SOOKSHMA));				
			} else if(divisionType.equalsIgnoreCase(VimshottariDivision.DIVISION_TYPE_SOOKSHMA)) {
				vDiv.setSubDivisionList( calculateVimshottariDasa(lord.getId(), calStartDate.getTime(), lordPeriod, VimshottariDivision.DIVISION_TYPE_PRANA));				
			} else if(divisionType.equalsIgnoreCase(VimshottariDivision.DIVISION_TYPE_PRANA)) {
				vDiv.setSubDivisionList( calculateVimshottariDasa(lord.getId(), calStartDate.getTime(), lordPeriod, VimshottariDivision.DIVISION_TYPE_DEHA));				
			}*/
			
			vimList.add(vDiv);
			if(startLordId == nextLord) break;
		}
		
		return vimList;		
	}
		
} 

//Date input = new Date();
//LocalDate date = LocalDate.ofInstant(input.toInstant(), ZoneId.systemDefault());


/*
import java.util.*;  
import java.io.*;  
public class Test {  
public static void main(String[] args)throws Exception{  

Properties p=new Properties();  
p.setProperty("name","Sonoo Jaiswal");  
p.setProperty("email","sonoojaiswal@javatpoint.com");  

p.store(new FileWriter("info.properties"),"Javatpoint Properties Example");  

}  
}  
 */