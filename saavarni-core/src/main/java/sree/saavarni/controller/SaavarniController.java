package sree.saavarni.controller;

import sree.saavarni.astro.GrahaSphuta;
import sree.saavarni.model.Planet;
import sree.saavarni.model.PositionLords;
import sree.saavarni.model.Preference;
import sree.saavarni.model.Sign;
import sree.saavarni.model.Star;
import sree.saavarni.model.VimshottariDivision;
import sree.saavarni.service.SaavarniService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaavarniController {

	@Autowired
	SaavarniService saavarniService;
	
	@GetMapping("/")
	//	public GrahaSphuta greeting(@RequestParam(value = "name", defaultValue = "World") String name, @RequestParam("dob") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dob) {
	public String greeting123(@RequestParam(value = "name", defaultValue = "World") String name) {	
		return "Hello, docker deployment test is success.";
	}

	@GetMapping("/greeting")
	//	public GrahaSphuta greeting(@RequestParam(value = "name", defaultValue = "World") String name, @RequestParam("dob") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dob) {
	public List<GrahaSphuta> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {	
		List<GrahaSphuta> gSphutaList = saavarniService.servaGrahaSphuta(new Preference(78.4867, 17.3850, 5.5), new Date());
		saavarniService.findPreference();
		return gSphutaList;
	}
	
	
	public static void main(String[] args) {
		
	}

	@GetMapping("/vim")
	//	public GrahaSphuta greeting(@RequestParam(value = "name", defaultValue = "World") String name, @RequestParam("dob") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dob) {
	public List<VimshottariDivision> vimDasa(@RequestParam(value = "name", defaultValue = "325.0") String name) {
		
		return saavarniService.calculateVimshottariDasa(Double.parseDouble(name), new Date());
		//return "done";	
	}
	
	@GetMapping("/planets")
	public Iterable<Planet> planets() {
		return saavarniService.findAllPlanets();
	}
	
	@GetMapping("/signs")
	public Iterable<Sign> signs() {
		return saavarniService.findAllSigns();
	}

	@GetMapping("/stars")
	public Iterable<Star> stars() {
		return saavarniService.findAllStars();

	}
	@GetMapping("/lords")
	public Iterable<PositionLords> lords() {
		return saavarniService.findAllPositionLords();

	}
}