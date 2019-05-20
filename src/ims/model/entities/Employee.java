/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author rabeh
 */
public class Employee implements Serializable {
    
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
    private String nom="";
    private String prenom="";
    private String cnss="";
    private String comptebancaire="";
    private double salaire=0.0;
    private String cin="";
    private String profil="";
    private Departement departement;
    private Set<Salaires> salaires=new HashSet<Salaires>();
    //private Set<Cnss> listcnss=new HashSet<Cnss>();
    
    private Set<LigneCaisse> lignescaisse=new HashSet<LigneCaisse>();
    
    public Employee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public double getSalaire() {
		return salaire;
	}

	public void setSalaire(double salaire) {
		this.salaire = salaire;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

	public String getComptebancaire() {
		return comptebancaire;
	}

	public void setComptebancaire(String comptebancaire) {
		this.comptebancaire = comptebancaire;
	}

	public Set<LigneCaisse> getLignescaisse() {
		return lignescaisse;
	}

	public void setLignescaisse(Set<LigneCaisse> lignescaisse) {
		this.lignescaisse = lignescaisse;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getCnss() {
		return cnss;
	}

	public void setCnss(String cnss) {
		this.cnss = cnss;
	}

	public Set<Salaires> getSalaires() {
		return salaires;
	}

	public void setSalaires(Set<Salaires> salaires) {
		this.salaires = salaires;
	}

    
}
