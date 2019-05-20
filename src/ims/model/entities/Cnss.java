package ims.model.entities;

import java.io.Serializable;
import java.util.Date;

public class Cnss implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private double montant;
	private Date datecnss;
	private Date dateconstat;
	private boolean contstater;
	private String libelleconstater;
	private years years;
	private Employee emp;
	public Cnss() {
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
	public Date getDatecnss() {
		return datecnss;
	}
	public void setDatecnss(Date datecnss) {
		this.datecnss = datecnss;
	}
	public Date getDateconstat() {
		return dateconstat;
	}
	public void setDateconstat(Date dateconstat) {
		this.dateconstat = dateconstat;
	}
	public boolean isContstater() {
		return contstater;
	}
	public void setContstater(boolean contstater) {
		this.contstater = contstater;
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
	
	public String getLibelleconstater() {
		if(this.contstater==true)
			libelleconstater="Oui";
		else
			libelleconstater="Non";
		return libelleconstater;
	}
	
	
	

}
