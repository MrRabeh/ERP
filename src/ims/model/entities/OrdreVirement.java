package ims.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class OrdreVirement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private double montantGlobal;
	private Date datevirement;
	private String moisordre;
	private years years;
	private Set<Salaires> salaires;
	private CoordonneeBancaire banque;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getMontantGlobal() {
		return montantGlobal;
	}
	public void setMontantGlobal(double montantGlobal) {
		this.montantGlobal = montantGlobal;
	}
	public Date getDatevirement() {
		return datevirement;
	}
	public void setDatevirement(Date datevirement) {
		this.datevirement = datevirement;
	}
	public String getMoisordre() {
		return moisordre;
	}
	public void setMoisordre(String moisordre) {
		this.moisordre = moisordre;
	}
	public years getYears() {
		return years;
	}
	public void setYears(years years) {
		this.years = years;
	}
	public Set<Salaires> getSalaires() {
		return salaires;
	}
	public void setSalaires(Set<Salaires> salaires) {
		this.salaires = salaires;
	}
	public CoordonneeBancaire getBanque() {
		return banque;
	}
	public void setBanque(CoordonneeBancaire banque) {
		this.banque = banque;
	}
	

}
