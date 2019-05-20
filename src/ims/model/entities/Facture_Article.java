package ims.model.entities;

import java.io.Serializable;

public class Facture_Article implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int nombreinstance=0;
	private int id;
	private Article article;
	private Facture facture;
	private double montantglobal;
	private double pt;
	private String designation;
	private double Coefficient;
	private double Mensualite;
	private double qte;
	private String lab;
	private CategorieArticle categorieArticle;
	private double prixmarge;
	private double marge;
	private Offre_Article objetOffreArticle;
	
	public Facture_Article() {
		super();
		// TODO Auto-generated constructor stub
		nombreinstance++;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public Facture getFacture() {
		return facture;
	}
	public void setFacture(Facture facture) {
		this.facture = facture;
	}
	public double getMontantglobal() {
		return montantglobal;
	}
	public void setMontantglobal(double montantglobal) {
		this.montantglobal = montantglobal;
	}
	public double getPt() {
		return pt;
	}
	public void setPt(double pt) {
		this.pt = pt;
	}
	public double getCoefficient() {
		return Coefficient;
	}
	public void setCoefficient(double coeff) {
		Coefficient = coeff;
	}
	public double getMensualite() {
		return Mensualite;
	}
	public void setMensualite(double mensualite2) {
		Mensualite = mensualite2;
	}
	public double getQte() {
		return qte;
	}
	public void setQte(double qte) {
		this.qte = qte;
	}
	public String getLab() {
		return lab;
	}
	public void setLab(String lab) {
		this.lab = lab;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public CategorieArticle getCategorieArticle() {
		return categorieArticle;
	}
	public void setCategorieArticle(CategorieArticle categorieArticle) {
		this.categorieArticle = categorieArticle;
	}
	public double getPrixmarge() {
		return prixmarge;
	}
	public void setPrixmarge(double prixmarge) {
		this.prixmarge = prixmarge;
	}
	public double getMarge() {
		return marge;
	}
	public void setMarge(double marge) {
		this.marge = marge;
	}
	public Offre_Article getObjetOffreArticle() {
		return objetOffreArticle;
	}
	public void setObjetOffreArticle(Offre_Article objetOffreArticle) {
		this.objetOffreArticle = objetOffreArticle;
	}
	public static int getNombreinstance() {
		return nombreinstance;
	}
	public static void setNombreinstance(int nombreinstance) {
		Facture_Article.nombreinstance = nombreinstance;
	}
	
	
	

}
