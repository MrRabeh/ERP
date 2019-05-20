/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author rabeh
 */
public class Offre implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String ref;
	private String numcotation;
	private String disponibilite;
	private String description;
	private String validiteoffre;
	private String modalitepaiment;
	private Date dateoffre;
	private Date datearreteContrat;
    private double totalHT=0;
    private double TVA=25f;
    private double TotalTTC=0;
    private String etatoffre;
    private boolean activer;
    
    private Client client;
    private TypeOffre typeoffre;
    private years years;
    private TypePaiement typepaiement;
    private Set<CotationOffre> cotations=new HashSet<CotationOffre>();
    
   
    private Set<Offre_Article> ligneArticleoffre=new HashSet<Offre_Article>();;
    private Set<Boncommande_Offre> commandesfournisseur=new HashSet<Boncommande_Offre>();
    private Set<Facture> factures=new HashSet<Facture>();
    private String civilite=""; 
    private double totalmarge=0;
    private Departement bu;
    private Employee emp;
    private boolean exoneration=false;
    
    public Offre() {
    	totalHT=0;
    	TVA=25;
    	TotalTTC=0;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public double getTotalHT() {
		return totalHT;
	}

	public void setTotalHT(double totalHT) {
		this.totalHT = totalHT;
	}

	public double getTVA() {
		return TVA;
	}

	public void setTVA(double tVA) {
		TVA = tVA;
	}

	public double getTotalTTC() {
		return TotalTTC;
	}

	public void setTotalTTC(double totalTTC) {
		TotalTTC = totalTTC;
	}

	public Set<Offre_Article> getLigneArticleoffre() {
        return ligneArticleoffre;
    }

    public void setLigneArticleoffre(Set<Offre_Article> ligneArticleoffre) {
        this.ligneArticleoffre = ligneArticleoffre;
    }
    
    public boolean AddArticleToOffre(Offre_Article article){
        if(this.ligneArticleoffre==null)
            this.ligneArticleoffre=new HashSet<Offre_Article>();
        
    this.ligneArticleoffre.add(article);
    return true;
    }


	public String getRef() {
		return ref;
	}


	public void setRef(String ref) {
		this.ref = ref;
	}


	public Date getDateoffre() {
		return dateoffre;
	}

	public void setDateoffre(Date dateoffre) {
		this.dateoffre = dateoffre;
	}
	
	public Set<Boncommande_Offre> getCommandesfournisseur() {
		return commandesfournisseur;
	}


	public void setCommandesfournisseur(Set<Boncommande_Offre> commandesfournisseur) {
		this.commandesfournisseur = commandesfournisseur;
	}


	public String getDisponibilite() {
		return disponibilite;
	}


	public void setDisponibilite(String disponibilite) {
		this.disponibilite = disponibilite;
	}


	public String getValiditeoffre() {
		return validiteoffre;
	}


	public void setValiditeoffre(String validiteoffre) {
		this.validiteoffre = validiteoffre;
	}


	public String getModalitepaiment() {
		return modalitepaiment;
	}


	public void setModalitepaiment(String modalitepaiment) {
		this.modalitepaiment = modalitepaiment;
	}


	public Set<Facture> getFactures() {
		return factures;
	}


	public void setFactures(Set<Facture> factures) {
		this.factures = factures;
	}



	public boolean getActiver() {
		return activer;
	}



	public void setActiver(boolean activer) {
		this.activer = activer;
	}

	public TypeOffre getTypeoffre() {
		return typeoffre;
	}

	public void setTypeoffre(TypeOffre typeoffre) {
		this.typeoffre = typeoffre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public years getYears() {
		return years;
	}

	public void setYears(years years) {
		this.years = years;
	}

	public TypePaiement getTypepaiement() {
		return typepaiement;
	}

	public void setTypepaiement(TypePaiement typepaiement) {
		this.typepaiement = typepaiement;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "type payement="+typepaiement.getTypepaiement()+",ref="+ref+",dateoffre="+dateoffre+",des="+description+
				",HT="+totalHT;
	}

	public String getEtatoffre() {
		return etatoffre;
	}

	public void setEtatoffre(String etatoffre) {
		this.etatoffre = etatoffre;
	}

	public Set<CotationOffre> getCotations() {
		return cotations;
	}

	public void setCotations(Set<CotationOffre> cotations) {
		this.cotations = cotations;
	}

	public String getCivilite() {
		return civilite;
	}

	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}

	public double getTotalmarge() {
		return totalmarge;
	}

	public void setTotalmarge(double totalmarge) {
		this.totalmarge = totalmarge;
	}

	public Departement getBu() {
		return bu;
	}

	public void setBu(Departement bu) {
		this.bu = bu;
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	public Date getDatearreteContrat() {
		return datearreteContrat;
	}

	public void setDatearreteContrat(Date datearreteContrat) {
		this.datearreteContrat = datearreteContrat;
	}

	public String getNumcotation() {
		return numcotation;
	}

	public void setNumcotation(String numcotation) {
		this.numcotation = numcotation;
	}

	public boolean isExoneration() {
		return exoneration;
	}

	public void setExoneration(boolean exoneration) {
		this.exoneration = exoneration;
	}
	
	
    
}
