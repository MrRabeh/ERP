/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.entities;

import java.util.Set;

/**
 *
 * @author rabeh
 */
public class Client implements java.io.Serializable{
    
	private static final long serialVersionUID = 1L;
	private int idclient;
    private String societe="";
    private String adresse="";
    private String tel="";
    private String tel2="";
    private String fonction="";
    private String fax="";
    private String email="";
    private Pays pays;
    private Ville ville;
    private Langue langue;
    private CategorieIISociete categorie;
    private Secteur secteur;
    private GroupeClient groupeclient;
    private Set<Offre> offres;
    private Set<Stock> stocks;
    private Set<BonLivraison> livraisons;
    private Set<Facture> factures;
    private Set<BonCommande> boncommandes;
    private Set<Contact> contacts;
    private Contact contactprincipal;
    private Set<AvoirClient> avoirs;
    
    public Client() {
    }

    public int getIdclient() {
        return idclient;
    }

    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }

	public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Langue getLangue() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    public CategorieIISociete getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieIISociete categorie) {
        
        this.categorie = categorie;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public Set<Offre> getOffres() {
        return offres;
    }

    public void setOffres(Set<Offre> offres) {
        this.offres = offres;
    }

	public String getSociete() {
		return societe;
	}

	public void setSociete(String societe) {
		this.societe = societe;
	}

	public Pays getPays() {
		return pays;
	}

	public void setPays(Pays pays) {
		this.pays = pays;
	}

	public Ville getVille() {
		return ville;
	}

	public void setVille(Ville ville) {
		this.ville = ville;
	}

	public String getFonction() {
		return fonction;
	}

	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	public Set<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(Set<Stock> stocks) {
		this.stocks = stocks;
	}

	public Set<BonLivraison> getLivraisons() {
		return livraisons;
	}

	public void setLivraisons(Set<BonLivraison> livraisons) {
		this.livraisons = livraisons;
	}

	public Set<Facture> getFactures() {
		return factures;
	}

	public void setFactures(Set<Facture> factures) {
		this.factures = factures;
	}

	public Set<BonCommande> getBoncommandes() {
		return boncommandes;
	}

	public void setBoncommandes(Set<BonCommande> boncommandes) {
		this.boncommandes = boncommandes;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public Contact getContactprincipal() {
		return contactprincipal;
	}

	public void setContactprincipal(Contact contactprincipal) {
		this.contactprincipal = contactprincipal;
	}

	public Set<AvoirClient> getAvoirs() {
		return avoirs;
	}

	public void setAvoirs(Set<AvoirClient> avoirs) {
		this.avoirs = avoirs;
	}

	public GroupeClient getGroupeclient() {
		return groupeclient;
	}

	public void setGroupeclient(GroupeClient groupeclient) {
		this.groupeclient = groupeclient;
	} 
}
