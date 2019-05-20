package ims.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;


public class AvoirClient implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private int id;
	
	private String numero_avoir="";
	private Date dateavoir;
	private Client client;
	private Set<Facture_Avoir> factures;
	private Set<Avoir_Articles> lignesArticle;
	
	private TypePaiement typepaiement;
	private String reglement=" ";
	private double totalht=0;
	private double tva=0;;
	private double totalttc=0;
	private double totalttcpaye=0;
	private Facture fact;
	private boolean activer;
	
	public AvoirClient() {
	    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateavoir() {
		return dateavoir;
	}

	public void setDateavoir(Date dateavoir) {
		this.dateavoir = dateavoir;
	}

	public String getNumero_avoir() {
		return numero_avoir;
	}

	public void setNumero_avoir(String numero_avoir) {
		this.numero_avoir = numero_avoir;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Set<Facture_Avoir> getFactures() {
		return factures;
	}

	public void setFactures(Set<Facture_Avoir> factures) {
		this.factures = factures;
	}

	public Set<Avoir_Articles> getLignesArticle() {
		return lignesArticle;
	}

	public void setLignesArticle(Set<Avoir_Articles> lignesArticle) {
		this.lignesArticle = lignesArticle;
	}

	public double getTotalht() {
		return totalht;
	}

	public void setTotalht(double totalht) {
		this.totalht = totalht;
	}

	public double getTva() {
		return tva;
	}

	public void setTva(double tva) {
		this.tva = tva;
	}

	public double getTotalttc() {
		return totalttc;
	}

	public void setTotalttc(double totalttc) {
		this.totalttc = totalttc;
	}

	public double getTotalttcpaye() {
		return totalttcpaye;
	}

	public void setTotalttcpaye(double totalttcpaye) {
		this.totalttcpaye = totalttcpaye;
	}

	public TypePaiement getTypepaiement() {
		return typepaiement;
	}

	public void setTypepaiement(TypePaiement typepaiement) {
		this.typepaiement = typepaiement;
	}

	public String getReglement() {
		return reglement;
	}

	public void setReglement(String reglement) {
		this.reglement = reglement;
	}

	public Facture getFact() {
		Iterator<Facture_Avoir> iteratorfac=this.factures.iterator();
		if(iteratorfac.hasNext())
			fact=iteratorfac.next().getFacture();
		return fact;
	}

	public void setFact(Facture fact) {
		this.fact = fact;
	}

	public boolean isActiver() {
		return activer;
	}

	public void setActiver(boolean activer) {
		this.activer = activer;
	}

	
	
}