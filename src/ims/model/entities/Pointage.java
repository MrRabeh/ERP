package ims.model.entities;

import java.util.Date;

 
public class Pointage implements java.io.Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idpointage;
	private String nom;
	private String periode;
	private Date datePointage;
	private String entree;
	private String sortie;
	
	public Pointage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdpointage() {
		return idpointage;
	}

	public void setIdpointage(int idpointage) {
		this.idpointage = idpointage;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}

	public Date getDatePointage() {
		return datePointage;
	}

	public void setDatePointage(Date datePointage) {
		this.datePointage = datePointage;
	}

	public String getEntree() {
		return entree;
	}

	public void setEntree(String entree) {
		this.entree = entree;
	}

	public String getSortie() {
		return sortie;
	}

	public void setSortie(String sortie) {
		this.sortie = sortie;
	}



}
