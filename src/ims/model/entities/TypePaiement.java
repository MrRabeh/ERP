package ims.model.entities;

import java.util.HashSet;
import java.util.Set;

public class TypePaiement {

	private int id;
	private String typepaiement;
	private Set<Offre> offres=new HashSet<>();
	private Set<BonCommande> commandes=new HashSet<BonCommande>();
	private Set<Facture> factures=new HashSet<Facture>();
	
	public TypePaiement() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TypePaiement(int id) {
		this.id=id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypepaiement() {
		return typepaiement;
	}
	public void setTypepaiement(String typepaiement) {
		this.typepaiement = typepaiement;
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

	public Set<Facture> getFactures() {
		return factures;
	}

	public void setFactures(Set<Facture> factures) {
		this.factures = factures;
	}
	
	
	
}
