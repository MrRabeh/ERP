package ims.model.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class years {
	
	private int id;
	private Date years;
	private String annee;
	private Set<Offre> offres=new HashSet<Offre>();
	private Set<BonCommande> commandes=new HashSet<BonCommande>();
	private Set<Facture> factures=new HashSet<Facture>();
	private Set<BonLivraison> livraisons=new HashSet<BonLivraison>();
	private Set<Salaires> salaires=new HashSet<Salaires>();
	private Set<Cnss> listcnss=new HashSet<Cnss>();
	private Set<Charge> charges=new HashSet<Charge>();
	private SimpleDateFormat ft= new SimpleDateFormat("yyyy");
	
	public years() {
		super();
		// TODO Auto-generated constructor stub
	}

	public years(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getYears() {
		return years;
	}
	public void setYears(Date years) {
		this.years = years;
	}
	public Set<Facture> getFactures() {
		return factures;
	}
	public void setFactures(Set<Facture> factures) {
		this.factures = factures;
	}

	public Set<Offre> getOffres() {
		return offres;
	}

	public void setOffres(Set<Offre> offres) {
		this.offres = offres;
	}

	public Set<BonCommande> getCommandes() {
		return commandes;
	}

	public void setCommandes(Set<BonCommande> commandes) {
		this.commandes = commandes;
	}

	public Set<BonLivraison> getLivraisons() {
		return livraisons;
	}

	public void setLivraisons(Set<BonLivraison> livraisons) {
		this.livraisons = livraisons;
	}

	public Set<Salaires> getSalaires() {
		return salaires;
	}

	public void setSalaires(Set<Salaires> salaires) {
		this.salaires = salaires;
	}

	public Set<Cnss> getListcnss() {
		return listcnss;
	}

	public void setListcnss(Set<Cnss> listcnss) {
		this.listcnss = listcnss;
	}

	public Set<Charge> getCharges() {
		return charges;
	}

	public void setCharges(Set<Charge> charges) {
		this.charges = charges;
	}

	public String getAnnee() {
		annee=ft.format(this.years);
		return annee;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
	}
	
	

}
