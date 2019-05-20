/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.entities;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author rabeh
 */
public class CategorieArticle implements java.io.Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idcategorie=1;
    private String categorie="";
    private Set<Offre_Article> offresArticles=new HashSet<Offre_Article>();
    private Set<Facture_Article> factureArticles=new HashSet<Facture_Article>();
    private Set<Avoir_Articles> avoirArticles=new HashSet<Avoir_Articles>();
    
    public CategorieArticle() {
    }

    public int getIdcategorie() {
        return idcategorie;
    }

    public void setIdcategorie(int idcategorie) {
        this.idcategorie = idcategorie;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

	public Set<Offre_Article> getOffresArticles() {
		return offresArticles;
	}

	public void setOffresArticles(Set<Offre_Article> offresArticles) {
		this.offresArticles = offresArticles;
	}

	public Set<Facture_Article> getFactureArticles() {
		return factureArticles;
	}

	public void setFactureArticles(Set<Facture_Article> factureArticles) {
		this.factureArticles = factureArticles;
	}

	public Set<Avoir_Articles> getAvoirArticles() {
		return avoirArticles;
	}

	public void setAvoirArticles(Set<Avoir_Articles> avoirArticles) {
		this.avoirArticles = avoirArticles;
	}
    
    
    
}
