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
public class Secteur implements java.io.Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String secteur;
    
    private Set<Client> clients=new HashSet<Client>();
    private CategorieIISociete categorie;
    public Secteur() {
    }  
    
    public Secteur(String secteur) {
		this.secteur=secteur;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }
    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

	public CategorieIISociete getCategorie() {
		return categorie;
	}

	public void setCategorie(CategorieIISociete categorie) {
		this.categorie = categorie;
	}    
}
