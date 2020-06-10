package sree.saavarni.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VimshottariDivision {

	public static String DIVISION_TYPE_DASA = "Dasa";
	public static String DIVISION_TYPE_BHUKTI = "Bhukti";
	public static String DIVISION_TYPE_ANTRA = "Anthara";
	public static String DIVISION_TYPE_SOOKSHMA = "Sookshma";
	public static String DIVISION_TYPE_PRANA = "Prana";
	public static String DIVISION_TYPE_DEHA = "Deha";

	private String division_type;
	private String divisionLord;
	private Date periodStartDate;
	private Date periodEndDate;

	private List<VimshottariDivision> subDivisionList = new ArrayList<VimshottariDivision>();

	public VimshottariDivision(String division_type, String divisionLord, Date periodStartDate, Date periodEndDate) {
		super();
		this.division_type = division_type;
		this.divisionLord = divisionLord;
		this.periodStartDate = periodStartDate;
		this.periodEndDate = periodEndDate;
	}

	public String getDivision_type() { return division_type; }
	public Date getPeriodStartDate() { return periodStartDate; }
	public Date getPeriodEndDate() { return periodEndDate; }
	public String getDivisionLord() { return divisionLord; }
	
	public void addSubDivision(VimshottariDivision subDivision) {
		this.subDivisionList.add(subDivision);
	}
	public List<VimshottariDivision> getSubDivisionList(){
		return this.subDivisionList;
	}
	public void setSubDivisionList(List<VimshottariDivision> subList) {
		this.subDivisionList = subList;
	}
	public void printdasa() {
		System.out.println("***** Dasa Type:" + this.division_type + " \tdivision Lord:" + divisionLord + " \tstartDate:" + periodStartDate.toString() + " \tendDate:" + periodEndDate.toString());
		
		subDivisionList.forEach((VimshottariDivision vdiv) -> {
			vdiv.printdasa();
		});
		
	}
}
