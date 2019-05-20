package ims.model.entities;

import java.io.Serializable;
import java.util.Date;

public class Charge implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private double montant=0;
	private Date datecharge;
	private Date datecontsater;
	private boolean constater;
	private String libelleconstater;
	private years years;
	private TypeCharge typecharge;
	private String reglement="CH/";
	
	public Charge() {
		super();
		// TODO Auto-generated constructor stub
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


	public Date getDatecharge() {
		return datecharge;
	}


	public void setDatecharge(Date datecharge) {
		this.datecharge = datecharge;
	}


	public Date getDatecontsater() {
		return datecontsater;
	}


	public void setDatecontsater(Date datecontsater) {
		this.datecontsater = datecontsater;
	}


	public boolean isConstater() {
		return constater;
	}


	public void setConstater(boolean constater) {
		this.constater = constater;
	}


	public years getYears() {
		return years;
	}


	public void setYears(years years) {
		this.years = years;
	}


	public TypeCharge getTypecharge() {
		return typecharge;
	}


	public void setTypecharge(TypeCharge typecharge) {
		this.typecharge = typecharge;
	}


	public String getLibelleconstater() {
		if(this.constater==true)
			libelleconstater="Oui";
		else
			libelleconstater="Non";
		return libelleconstater;
	}


	public String getReglement() {
		return reglement;
	}


	public void setReglement(String reglement) {
		this.reglement = reglement;
	}
	
	

	
}
