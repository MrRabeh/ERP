package ims.model.entities;

import ims.model.dao.Impl.DataIms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.classic.Session;

public class BonCommande implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String numercommande;
	private String numeroOffreFournisseur="";
	private String echeance="";
	private Date datecommande;
	private Date dateoffre;
	private double totalht;
	private double tva;
	private double totalttc;
	private double totalnet;
    private boolean annuler;
    private String afficherannuler;
    private boolean activer;
	private Fournisseur fournisseur;
	private Client client;
	private years years;
	private Set<Boncommande_Article> lignecommandearticles=new HashSet<Boncommande_Article>();
	private Set<Boncommande_Offre> offreclients=new HashSet<Boncommande_Offre>();
	private List<Offre> listeoffres=new ArrayList<Offre>();
	private double fraitransport;
	private TypePaiement typepaiement;
	private String etat="En commande";
	private String reglement="";
	private Date dateechance;
	private String obs="";
	private String constater="";
	private Set<FactureFournisseur> facturesfournisseur=new HashSet<FactureFournisseur>();
	
 	private final List<String> Etats=new ArrayList<String>() {
 		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{ add("");add("Non réglée");add("réglée");}};
	
	public BonCommande() {
		super();

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumercommande() {
		return numercommande;
	}

	public void setNumercommande(String numercommande) {
		this.numercommande = numercommande;
	}

	public String getEcheance() {
		return echeance;
	}

	public void setEcheance(String echeance) {
		this.echeance = echeance;
	}

	public Fournisseur getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}


	public Date getDatecommande() {
		return datecommande;
	}

	public void setDatecommande(Date datecommande) {
		this.datecommande = datecommande;
	}

	public Set<Boncommande_Article> getLignecommandearticles() {
		return lignecommandearticles;
	}

	public void setLignecommandearticles(
			Set<Boncommande_Article> lignecommandearticles) {
		this.lignecommandearticles = lignecommandearticles;
	}

	public String getNumeroOffreFournisseur() {
		return numeroOffreFournisseur;
	}

	public void setNumeroOffreFournisseur(String numeroOffreFournisseur) {
		this.numeroOffreFournisseur = numeroOffreFournisseur;
	}

	public Date getDateoffre() {
		return dateoffre;
	}

	public void setDateoffre(Date dateoffre) {
		this.dateoffre = dateoffre;
	}

	public Set<Boncommande_Offre> getOffreclients() {
		return offreclients;
	}

	public void setOffreclients(Set<Boncommande_Offre> offreclients) {
		this.offreclients = offreclients;
	}
	
	public List<Offre> getListeoffres() {
		listeoffres=null;
		listeoffres=new ArrayList<Offre>();
		if(id>0){
		List<Boncommande_Offre> lignes=null;
		try {
			Session session=DataIms.sessionFactory.getCurrentSession();
	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class Boncommande_Offre where obj.commande.id=:id");
	        q.setParameter("id",id);
	        lignes=((List<Boncommande_Offre>)q.list());
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i=0;i<lignes.size();i++)
			listeoffres.add(lignes.get(i).getOffre());
		}
		return listeoffres;
	}
	
	

	public void setListeoffres(List<Offre> listeoffres) {
		this.listeoffres = listeoffres;
	}

	public boolean isAnnuler() {
		return annuler;
	}

	public void setAnnuler(boolean annuler) {
		this.annuler = annuler;
	}

	public boolean isActiver() {
		return activer;
	}

	public void setActiver(boolean activer) {
		this.activer = activer;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}


	public years getYears() {
		return years;
	}

	public void setYears(years years) {
		this.years = years;
	}

	public String getAfficherannuler() {
		if(annuler==true)
			afficherannuler="Oui";
		else
			afficherannuler="Non";
		
		return afficherannuler;
	}

	public void setAfficherannuler(String afficherannuler) {
		this.afficherannuler = afficherannuler;
	}

	public TypePaiement getTypepaiement() {
		return typepaiement;
	}

	public void setTypepaiement(TypePaiement typepaiement) {
		this.typepaiement = typepaiement;
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

	public double getTotalnet() {
		return totalnet;
	}

	public void setTotalnet(double totalnet) {
		this.totalnet = totalnet;
	}

	public double getFraitransport() {
		return fraitransport;
	}

	public void setFraitransport(double fraitransport) {
		this.fraitransport = fraitransport;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getReglement() {
		return reglement;
	}

	public void setReglement(String reglement) {
		this.reglement = reglement;
	}

	public Date getDateechance() {
		return dateechance;
	}

	public void setDateechance(Date dateechance) {
		this.dateechance = dateechance;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public List<String> getEtats() {
		return Etats;
	}

	public String getConstater() {
		return constater;
	}

	public void setConstater(String constater) {
		this.constater = constater;
	}

	public Set<FactureFournisseur> getFacturesfournisseur() {
		return facturesfournisseur;
	}

	public void setFacturesfournisseur(Set<FactureFournisseur> facturesfournisseur) {
		this.facturesfournisseur = facturesfournisseur;
	}	
	
}
