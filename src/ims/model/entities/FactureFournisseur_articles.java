package ims.model.entities;

import java.io.Serializable;

public class FactureFournisseur_articles implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String designationfournisseur;
	private double pu;
	private double qte;
	private double tva;
	private double pt;
	private Article article;
	private FactureFournisseur facture;
	
	
	
	public FactureFournisseur_articles() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FactureFournisseur_articles(Article article2, String designation,
			double pu2, double qte2, double pt2) {
		this.article=article2;
		this.designationfournisseur=designation;
		this.pu=pu2;
		this.qte=qte2;
		this.pt=pt2;
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
	public String getDesignationfournisseur() {
		return designationfournisseur;
	}
	public void setDesignationfournisseur(String designationfournisseur) {
		this.designationfournisseur = designationfournisseur;
	}
	public double getPu() {
		return pu;
	}
	public void setPu(double pu) {
		this.pu = pu;
	}
	public double getQte() {
		return qte;
	}
	public void setQte(double qte) {
		this.qte = qte;
	}
	public double getTva() {
		return tva;
	}
	public void setTva(double tva) {
		this.tva = tva;
	}
	public double getPt() {
		return pt;
	}
	public void setPt(double pt) {
		this.pt = pt;
	}
	public FactureFournisseur getFacture() {
		return facture;
	}
	public void setFacture(FactureFournisseur facture) {
		this.facture = facture;
	}
	
	
	
	

}
