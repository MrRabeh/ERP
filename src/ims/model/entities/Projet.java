package ims.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Projet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int idprojet;
	private String projectname;
	private float totol_heur;
	private Date datedebut;
	private Date dateFin;
	private Client client;
	private Employee emp;
	private Set<Tache> taches=new HashSet<Tache>();
	
	public int getIdprojet() {
		return idprojet;
	}
	public void setIdprojet(int idprojet) {
		this.idprojet = idprojet;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public float getTotol_heur() {
		return totol_heur;
	}
	public void setTotol_heur(float totol_heur) {
		this.totol_heur = totol_heur;
	}
	public Date getDatedebut() {
		return datedebut;
	}
	public void setDatedebut(Date datedebut) {
		this.datedebut = datedebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Employee getEmp() {
		return emp;
	}
	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	public Set<Tache> getTaches() {
		return taches;
	}
	public void setTaches(Set<Tache> taches) {
		this.taches = taches;
	}
	
	
}