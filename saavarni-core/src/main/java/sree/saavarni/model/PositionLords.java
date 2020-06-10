package sree.saavarni.model;

import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import sree.saavarni.model.Planet;

@Entity
public class PositionLords {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private double startDegree;
    private double endDegree;
    private String sign;
    private String star;
    private String signLord;
    private String starLord;
    private String subLord;
    private String subSubLord;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getStartDegree() {
		return startDegree;
	}
	public void setStartDegree(double startDegree) {
		this.startDegree = startDegree;
	}
	public double getEndDegree() {
		return endDegree;
	}
	public void setEndDegree(double endDegree) {
		this.endDegree = endDegree;
	}	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getSignLord() {
		return signLord;
	}
	public void setSignLord(String signLord) {
		this.signLord = signLord;
	}
	public String getStarLord() {
		return starLord;
	}
	public void setStarLord(String starLord) {
		this.starLord = starLord;
	}
	public String getSubLord() {
		return subLord;
	}
	public void setSubLord(String subLord) {
		this.subLord = subLord;
	}
	public String getSubSubLord() {
		return subSubLord;
	}
	public void setSubSubLord(String subSubLord) {
		this.subSubLord = subSubLord;
	}
    
}
