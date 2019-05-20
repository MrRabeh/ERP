/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.entities;

import java.io.Serializable;

/**
 *
 * @author rabeh
 */
public class Offre_Article implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int nombrepersonne;
    private double qtef;
    private String lbqte;
    private double pu;
    private String str_pu;
    private String str_pt;
    private double pt;
    private double puDH_fournisseur;
    private double ptDH_fournisseur;
    private String designation;
    private double marge=25;
    private boolean margetotal;
    private String Strmargetotal;
    private double prixmarge;
    private double pu_calculer;
    private double pt_calculer;
    private Offre offre=null;
    private Article article=null;
    private CategorieArticle categorieArticle=new CategorieArticle();
    private Rubrique rubrique;
    private double taux;
    private double retenu;
    private boolean prixfournisseurisDH;
    private String etat;
    private boolean optionnel;
    private boolean gratuite;
    
    private String stroption;
    private String optionmodif;
    
    public Offre_Article() {
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNombrepersonne() {
		return nombrepersonne;
	}

	public void setNombrepersonne(int nombrepersonne) {
		this.nombrepersonne = nombrepersonne;
	}

	public double getQtef() {
		return qtef;
	}

	public void setQtef(double qtef) {
		this.qtef = qtef;
	}

	public String getLbqte() {
		return lbqte;
	}

	public void setLbqte(String lbqte) {
		this.lbqte = lbqte;
	}

	public double getPu() {
		return pu;
	}

	public void setPu(double pu) {
		this.pu = pu;
	}

	public double getPt() {
		return pt;
	}

	public void setPt(double pt) {
		this.pt = pt;
	}

	public double getPuDH_fournisseur() {
		return puDH_fournisseur;
	}

	public void setPuDH_fournisseur(double puDH_fournisseur) {
		this.puDH_fournisseur = puDH_fournisseur;
	}

	public double getPtDH_fournisseur() {
		return ptDH_fournisseur;
	}

	public void setPtDH_fournisseur(double ptDH_fournisseur) {
		this.ptDH_fournisseur = ptDH_fournisseur;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public double getMarge() {
		return marge;
	}

	public void setMarge(double marge) {
		this.marge = marge;
	}

	public boolean isMargetotal() {
		return margetotal;
	}

	public void setMargetotal(boolean margetotal) {
		this.margetotal = margetotal;
	}

	public double getPrixmarge() {
		return prixmarge;
	}

	public void setPrixmarge(double prixmarge) {
		this.prixmarge = prixmarge;
	}

	public double getPu_calculer() {
		return pu_calculer;
	}

	public void setPu_calculer(double pu_calculer) {
		this.pu_calculer = pu_calculer;
	}

	public double getPt_calculer() {
		return pt_calculer;
	}

	public void setPt_calculer(double pt_calculer) {
		this.pt_calculer = pt_calculer;
	}

	public Offre getOffre() {
		return offre;
	}

	public void setOffre(Offre offre) {
		this.offre = offre;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public CategorieArticle getCategorieArticle() {
		return categorieArticle;
	}

	public void setCategorieArticle(CategorieArticle categorieArticle) {
		this.categorieArticle = categorieArticle;
	}

	public Rubrique getRubrique() {
		return rubrique;
	}

	public void setRubrique(Rubrique rubrique) {
		this.rubrique = rubrique;
	}

	public double getTaux() {
		return taux;
	}

	public void setTaux(double taux) {
		this.taux = taux;
	}

	public boolean isPrixfournisseurisDH() {
		return prixfournisseurisDH;
	}

	public void setPrixfournisseurisDH(boolean prixfournisseurisDH) {
		this.prixfournisseurisDH = prixfournisseurisDH;
	}

	public String getStr_pu() {
		if(prixfournisseurisDH==false)
			str_pu=pu+" €";
		else
			str_pu=pu+" DH";
		return str_pu;
	}

	public void setStr_pu(String str_pu) {
		this.str_pu = str_pu;
	}

	public String getStr_pt() {
		if(prixfournisseurisDH==false)
			str_pt=pt+" €";
		else
			str_pt=pt+" DH";
		return str_pt;
	}

	public void setStr_pt(String str_pt) {
		this.str_pt = str_pt;
	}

	public double getRetenu() {
		return retenu;
	}

	public void setRetenu(double retenu) {
		this.retenu = retenu;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public boolean isOptionnel() {
		return optionnel;
	}

	public void setOptionnel(boolean optionnel) {
		this.optionnel = optionnel;
	}

	public boolean isGratuite() {
		return gratuite;
	}

	public void setGratuite(boolean gratuite) {
		this.gratuite = gratuite;
	}

	public String getStroption() {
		if(optionnel==true)
			stroption="Oui";
		else
			stroption="Non";
		return stroption;
	}

	public void setStroption(String stroption) {
		this.stroption = stroption;
	}

	public String getOptionmodif() {
		return optionmodif;
	}

	public void setOptionmodif(String optionmodif) {
		this.optionmodif = optionmodif;
	}

	public String getStrmargetotal() {
		if(margetotal==true)
			Strmargetotal="Oui";
		else
			Strmargetotal="Non";
		return Strmargetotal;
	}

	public void setStrmargetotal(String strmargetotal) {
		Strmargetotal = strmargetotal;
	}

	

    

}
