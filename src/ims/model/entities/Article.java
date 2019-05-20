/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.entities;
import java.util.*;
/**
 *
 * @author rabeh
 */
public class Article implements java.io.Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idArticle;
    private String ref;
    private String refupdate="";
    private String designation;
    private TypeArticle typearticle;
    private Produit typeproduit;
    private Set<Offre_Article> lignearticleOffre=new HashSet<Offre_Article>();
    private Set<Boncommande_Article> lignearticleBoncommade=new HashSet<Boncommande_Article>();
    private Set<Facture_Article> lignefactures=new HashSet<Facture_Article>();
    private Set<Stock> sotcks=new HashSet<Stock>();
    private Set<Avoir_Articles> avoir=new HashSet<Avoir_Articles>();
    
    //stat
    private int NbrAchat=0; 
    //
    public Article() {
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }
    
   

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getDesignation() {
			return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public TypeArticle getTypearticle() {
        return typearticle;
    }

    public void setTypearticle(TypeArticle typearticle) {
        this.typearticle = typearticle;
    }

    public Set<Offre_Article> getLignearticleOffre() {
        return lignearticleOffre;
    }

    public void setLignearticleOffre(Set<Offre_Article> lignearticleOffre) {
        this.lignearticleOffre = lignearticleOffre;
    }

	public Set<Boncommande_Article> getLignearticleBoncommade() {
		return lignearticleBoncommade;
	}

	public void setLignearticleBoncommade(
			Set<Boncommande_Article> lignearticleBoncommade) {
		this.lignearticleBoncommade = lignearticleBoncommade;
	}

	public Set<Facture_Article> getLignefactures() {
		return lignefactures;
	}

	public void setLignefactures(Set<Facture_Article> lignefactures) {
		this.lignefactures = lignefactures;
	}

	public Set<Stock> getSotcks() {
		return sotcks;
	}

	public void setSotcks(Set<Stock> sotcks) {
		this.sotcks = sotcks;
	}

	public Produit getTypeproduit() {
		return typeproduit;
	}

	public void setTypeproduit(Produit typeproduit) {
		this.typeproduit = typeproduit;
	}

	public Set<Avoir_Articles> getAvoir() {
		return avoir;
	}

	public void setAvoir(Set<Avoir_Articles> avoir) {
		this.avoir = avoir;
	}

	public int getNbrAchat() {
		return NbrAchat;
	}

	public void setNbrAchat(int nbrAchat) {
		NbrAchat = nbrAchat;
	}

	public String getRefupdate() {
		//if(refupdate.equals(""))
			//refupdate=ref;
		return refupdate;
	}

	public void setRefupdate(String refupdate) {
		this.refupdate = refupdate;
	}

    
}
