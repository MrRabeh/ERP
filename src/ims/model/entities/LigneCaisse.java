package ims.model.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class LigneCaisse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Date dateligne;
	private String mois;
	private int annee;
	private String strdate;
	private double entree;
	private double sortie;
	private String details="";
	private Employee emp;
	private Caisse caisse;
	private String reglement="";
	private boolean debutjour;
	private Caisse caissePresedant;
	
	private String[] tabmois=new String[]{"janvier","février","mars","avril","mai","juin","juillet","août","septembre","octobre","novembre","décembre"};
	
	public LigneCaisse() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Date getDateligne() {
		return dateligne;
	}


	public void setDateligne(Date dateligne) {
		this.dateligne = dateligne;
	}


	public double getEntree() {
		return entree;
	}


	public void setEntree(double entree) {
		this.entree = entree;
	}


	public double getSortie() {
		return sortie;
	}


	public void setSortie(double sortie) {
		this.sortie = sortie;
	}


	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}


	public Employee getEmp() {
		return emp;
	}


	public void setEmp(Employee emp) {
		this.emp = emp;
	}


	public Caisse getCaisse() {
		return caisse;
	}


	public void setCaisse(Caisse caisse) {
		this.caisse = caisse;
	}


	public String getReglement() {
		return reglement;
	}


	public void setReglement(String reglement) {
		this.reglement = reglement;
	}


	public boolean isDebutjour() {
		return debutjour;
	}


	public void setDebutjour(boolean debutjour) {
		this.debutjour = debutjour;
	}


	public Caisse getCaissePresedant() {
		return caissePresedant;
	}


	public void setCaissePresedant(Caisse caissePresedant) {
		this.caissePresedant = caissePresedant;
	}


	public String getMois() {
		GregorianCalendar calendar = new GregorianCalendar(); 
		calendar.setTime(this.dateligne); 
		int month=calendar.get(GregorianCalendar.MONTH);
		mois=tabmois[month];
		return mois;
	}


	public void setMois(String mois) {
		this.mois = mois;
	}


	public int getAnnee() {
		GregorianCalendar calendar = new GregorianCalendar(); 
		calendar.setTime(this.dateligne); 
		annee=calendar.get(GregorianCalendar.YEAR);
		return annee;
	}


	public void setAnnee(int annee) {
		this.annee = annee;
	}


	public String getStrdate() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		strdate=df.format(dateligne);
		return strdate;
	}


	public void setStrdate(String strdate) {
		this.strdate = strdate;
	}


	
	
	

}
