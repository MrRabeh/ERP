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
public class CategorieIISociete implements java.io.Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String categorie;

    private Set<Client> clients=new HashSet<Client>();
    
    private Set<Secteur> secteurs=new HashSet<Secteur>();
    
    public CategorieIISociete() {
    }

    public CategorieIISociete(String categrorie) {
		this.categorie=categrorie;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

	public Set<Secteur> getSecteurs() {
		return secteurs;
	}

	public void setSecteurs(Set<Secteur> secteurs) {
		this.secteurs = secteurs;
	}    
}
