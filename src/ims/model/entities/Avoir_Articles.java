package ims.model.entities;

import java.io.Serializable;

public class Avoir_Articles implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private AvoirClient avoir;
	private Article article;
	private String designation;
	private CategorieArticle categorieArticle;
	private double pu;
	private double pt;
	private double qte;
	private String chaineqte;
	
	public Avoir_Articles() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public AvoirClient getAvoir() {
		return avoir;
	}
	public void setAvoir(AvoirClient avoir) {
		this.avoir = avoir;
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

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
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

	public double getQte() {
		return qte;
	}

	public void setQte(double qte) {
		this.qte = qte;
	}

	public String getChaineqte() {
		return chaineqte;
	}

	public void setChaineqte(String chaineqte) {
		this.chaineqte = chaineqte;
	}

	
}
