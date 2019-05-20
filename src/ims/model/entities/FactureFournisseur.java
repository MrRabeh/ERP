package ims.model.entities;

import java.io.Serializable;
import java.util.Date;

public class FactureFournisseur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String num_facture="";
	private Date datereception;
	private Date datefacture;
	private Date dateechance;
	private String reglement="";
	private double totalht=0;
	private double tva=0;
	private double totalttc=0;
	private double fraitransport=0;
	private double totalNet=0;
	
	private String obs="";
	private Date datepevisionnel;
	private Date dateconstater;
	private double montantregler=0;
	private double montantrester=0;
	private String constater="";
	private String etat="Non réglée";
    private boolean annuler;
    private boolean activer;
	private TypePaiement typepaiement;
	private BonCommande commandefournissuer;
	private years objetyears;
	private String numAvoir;
	private double montantdavoire;
	
	
	public FactureFournisseur() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNum_facture() {
		return num_facture;
	}
	public void setNum_facture(String num_facture) {
		this.num_facture = num_facture;
	}
	public Date getDatefacture() {
		return datefacture;
	}
	public void setDatefacture(Date datefacture) {
		this.datefacture = datefacture;
	}
	public Date getDateechance() {
		return dateechance;
	}
	public void setDateechance(Date dateechance) {
		this.dateechance = dateechance;
	}
	public String getReglement() {
		return reglement;
	}
	public void setReglement(String reglement) {
		this.reglement = reglement;
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
	public BonCommande getCommandefournissuer() {
		return commandefournissuer;
	}
	public void setCommandefournissuer(BonCommande commandefournissuer) {
		this.commandefournissuer = commandefournissuer;
	}
	public double getFraitransport() {
		return fraitransport;
	}
	public void setFraitransport(double fraitransport) {
		this.fraitransport = fraitransport;
	}
	public double getTotalNet() {
		return totalNet;
	}
	public void setTotalNet(double totalNet) {
		this.totalNet = totalNet;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public Date getDatepevisionnel() {
		return datepevisionnel;
	}
	public void setDatepevisionnel(Date datepevisionnel) {
		this.datepevisionnel = datepevisionnel;
	}
	public Date getDateconstater() {
		return dateconstater;
	}
	public void setDateconstater(Date dateconstater) {
		this.dateconstater = dateconstater;
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
	public String getConstater() {
		return constater;
	}
	public void setConstater(String constater) {
		this.constater = constater;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
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
	public TypePaiement getTypepaiement() {
		return typepaiement;
	}
	public void setTypepaiement(TypePaiement typepaiement) {
		this.typepaiement = typepaiement;
	}
	public years getObjetyears() {
		return objetyears;
	}
	public void setObjetyears(years objetyears) {
		this.objetyears = objetyears;
	}
	public String getNumAvoir() {
		return numAvoir;
	}
	public void setNumAvoir(String numAvoir) {
		this.numAvoir = numAvoir;
	}
	public double getMontantdavoire() {
		return montantdavoire;
	}
	public void setMontantdavoire(double montantdavoire) {
		this.montantdavoire = montantdavoire;
	}
	public Date getDatereception() {
		return datereception;
	}
	public void setDatereception(Date datereception) {
		this.datereception = datereception;
	}

	
	
}
