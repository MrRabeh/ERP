package ims.model.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Facture implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int id;
	private String ref;
	private Date datefacture;
	private String conditiondepayment;
	private String NumeroBCClient;
	private String contrat;
	private String description="";
	private Date dateboncommande;
	private double coefficientfacture;
	private double remise;
	private double retenuegarantie;
	private double totalht;
	private double totalttc;
	private String etat;
	private double totalttcpaye;
	private double tva;
    private boolean annuler;
    private boolean activer;
    private String afficherannuler;
	private CoordonneeBancaire banque;
	private TypeFacture facturetype;
	private TypePaiement typepaiement;
	private Set<Facture_Article> facture_article=new HashSet<Facture_Article>();
	private Set<Facture_Livraison> lignelivraison=new HashSet<Facture_Livraison>();
	private Set<Offre> Offres=new HashSet<Offre>();
	private List<Offre> listoffres=new ArrayList<Offre>();
	private Client client;
	private years years;
	private double montantregler;
	private double montantrester;
	private String reglement="";
	private Date dateechance;
	private Date dateechancep;
	private Date datereglement;
	private Date dateconstater;
	private String obs="";
	private double prixmarge;
	private String constater;
	private Set<Facture_Avoir> avoirs;
	private String typefacture;
	
	private Departement bu;
	private boolean exoneration=false;
	
	//stat
	private int Nombreconstat;
	private int NombreNonconstat;
	//
	
	
 	private final List<String> Etats=new ArrayList<String>() {
 		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{add("Non réglée");add("réglée");}};
	
	public Facture() {
		super();
		annuler=false;
		activer=true;
		montantregler=0;
		montantrester=0;
		etat=Etats.get(0);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Date getDatefacture() {
		return datefacture;
	}

	public void setDatefacture(Date datefacture) {
		this.datefacture = datefacture;
	}

	public String getConditiondepayment() {
		return conditiondepayment;
	}

	public void setConditiondepayment(String conditiondepayment) {
		this.conditiondepayment = conditiondepayment;
	}

	public String getNumeroBCClient() {
		return NumeroBCClient;
	}

	public void setNumeroBCClient(String numeroBCClient) {
		NumeroBCClient = numeroBCClient;
	}

	public Date getDateboncommande() {
		return dateboncommande;
	}

	public void setDateboncommande(Date dateboncommande) {
		this.dateboncommande = dateboncommande;
	}

	public double getCoefficientfacture() {
		return coefficientfacture;
	}

	public void setCoefficientfacture(double coefficientfacture) {
		this.coefficientfacture = coefficientfacture;
	}

	public double getTotalht() {
		return totalht;
	}

	public void setTotalht(double totalht) {
		this.totalht = totalht;
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

	public double getTva() {
		return tva;
	}

	public void setTva(double tva) {
		this.tva = tva;
	}

	public CoordonneeBancaire getBanque() {
		return banque;
	}

	public void setBanque(CoordonneeBancaire banque) {
		this.banque = banque;
	}

	public TypeFacture getFacturetype() {
		return facturetype;
	}

	public void setFacturetype(TypeFacture facturetype) {
		this.facturetype = facturetype;
	}

	public Set<Facture_Article> getFacture_article() {
		return facture_article;
	}

	public void setFacture_article(Set<Facture_Article> facture_article) {
		this.facture_article = facture_article;
	}

	public Set<Offre> getOffres() {
		return Offres;
	}

	public void setOffres(Set<Offre> offres) {
		Offres = offres;
	}

	public double getRemise() {
		return remise;
	}

	public void setRemise(double remise) {
		this.remise = remise;
	}

	public double getRetenuegarantie() {
		return retenuegarantie;
	}

	public void setRetenuegarantie(double retenuegarantie) {
		this.retenuegarantie = retenuegarantie;
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

	public String getContrat() {
		return contrat;
	}

	public void setContrat(String contrat) {
		this.contrat = contrat;
	}
	
	public void AddOffre(Offre offre){
		Offres.add(offre);
		offre.getFactures().add(this);
	}
	
	public void removeOffre(Offre offre){
		Offres.remove(offre);
		offre.getFactures().remove(this);
	}

	public List<Offre> getListoffres() {
		listoffres=new ArrayList<Offre>();
		listoffres.addAll(Offres); 
		return listoffres;
	}

	public void setListoffres(List<Offre> listoffres) {
		this.listoffres = listoffres;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Set<Facture_Livraison> getLignelivraison() {
		return lignelivraison;
	}

	public void setLignelivraison(Set<Facture_Livraison> lignelivraison) {
		this.lignelivraison = lignelivraison;
	}

	public years getYears() {
		return years;
	}

	public void setYears(years years) {
		this.years = years;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
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

	public double getMontantregler() {
		return montantregler;
	}

	public void setMontantregler(double montantregler) {
		this.montantregler = montantregler;
	}

	public double getMontantrester() {
		return montantrester;
	}

	public void setMontantrester(double montantrester) {
		this.montantrester = montantrester;
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

	public List<String> getEtats() {
		return Etats;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public double getPrixmarge() {
		return prixmarge;
	}

	public void setPrixmarge(double prixmarge) {
		this.prixmarge = prixmarge;
	}

	public String getConstater() {
		return constater;
	}

	public void setConstater(String constater) {
		this.constater = constater;
	}

	public Date getDateechancep() {
		return dateechancep;
	}

	public void setDateechancep(Date dateechancep) {
		this.dateechancep = dateechancep;
	}

	public Date getDatereglement() {
		return datereglement;
	}

	public void setDatereglement(Date datereglement) {
		this.datereglement = datereglement;
	}

	public Date getDateconstater() {
		return dateconstater;
	}

	public void setDateconstater(Date dateconstater) {
		this.dateconstater = dateconstater;
	}

	public Set<Facture_Avoir> getAvoirs() {
		return avoirs;
	}

	public void setAvoirs(Set<Facture_Avoir> avoirs) {
		this.avoirs = avoirs;
	}

	public String getTypefacture() {
		typefacture=this.listoffres.get(0).getTypeoffre().getType();
		return typefacture;
	}

	public void setTypefacture(String typefacture) {
		this.typefacture = typefacture;
	}

	public int getNombreconstat() {
		return Nombreconstat;
	}

	public void setNombreconstat(int nombreconstat) {
		Nombreconstat = nombreconstat;
	}

	public int getNombreNonconstat() {
		return NombreNonconstat;
	}

	public void setNombreNonconstat(int nombreNonconstat) {
		NombreNonconstat = nombreNonconstat;
	}

	public Departement getBu() {
		return bu;
	}

	public void setBu(Departement bu) {
		this.bu = bu;
	}

	public boolean isExoneration() {
		return exoneration;
	}

	public void setExoneration(boolean exoneration) {
		this.exoneration = exoneration;
	}


	
	
	
	
}
