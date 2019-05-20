package ims.model.entities;

import java.io.Serializable;
import java.util.Set;

public class CoordonneeBancaire implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String banque;
	private String numerobancaire;
	private String agence;
	private Set<Facture> factures;
	
	public CoordonneeBancaire() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBanque() {
		return banque;
	}
	public void setBanque(String banque) {
		this.banque = banque;
	}
	public String getNumerobancaire() {
		return numerobancaire;
	}
	public void setNumerobancaire(String numerobancaire) {
		this.numerobancaire = numerobancaire;
	}
	public String getAgence() {
		return agence;
	}
	public void setAgence(String agence) {
		this.agence = agence;
	}
	public Set<Facture> getFactures() {
		return factures;
	}
	public void setFactures(Set<Facture> factures) {
		this.factures = factures;
	}
	
	
	
}
