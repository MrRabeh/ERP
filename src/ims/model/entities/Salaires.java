package ims.model.entities;

import java.io.Serializable;
import java.util.Date;

public class Salaires implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private double montant=0;
	private Date datesalaire;
	private String moisordre;
	private years years;
	private Employee emp;
	private OrdreVirement ordre;
	
	public Salaires() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public Date getDatesalaire() {
		return datesalaire;
	}
	public void setDatesalaire(Date datesalaire) {
		this.datesalaire = datesalaire;
	}
	
	public years getYears() {
		return years;
	}
	public void setYears(years years) {
		this.years = years;
	}
	public Employee getEmp() {
		return emp;
	}
	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	public String getMoisordre() {
		return moisordre;
	}
	public void setMoisordre(String moisordre) {
		this.moisordre = moisordre;
	}
	public OrdreVirement getOrdre() {
		return ordre;
	}
	public void setOrdre(OrdreVirement ordre) {
		this.ordre = ordre;
	}
	
}
