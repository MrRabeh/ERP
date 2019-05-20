package ims.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Tache implements Serializable{

	private static final long serialVersionUID = 1L;
	private int idtache;
	 
	private String tachename;
	private Date datedebut;
	private Date datefin;
	private String heurdebart;
	private String heureFin;
	private String commentaire;
	private String duree;
	private Projet projet=new Projet();
	private Set<Timesheet> timesheet=new HashSet<Timesheet>();
	
	
	public Tache() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getIdtache() {
		return idtache;
	}
	public void setIdtache(int idtache) {
		this.idtache = idtache;
	}
	public String getTachename() {
		return tachename;
	}
	public void setTachename(String tachename) {
		this.tachename = tachename;
	}
	public Date getDatedebut() {
		return datedebut;
	}
	public void setDatedebut(Date datedebut) {
		this.datedebut = datedebut;
	}
	public Date getDatefin() {
		return datefin;
	}
	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}
	public String getHeurdebart() {
		return heurdebart;
	}
	public void setHeurdebart(String heurdebart) {
		this.heurdebart = heurdebart;
	}
	public String getHeureFin() {
		return heureFin;
	}
	public void setHeureFin(String heureFin) {
		this.heureFin = heureFin;
	}
	public String getDuree() {
		return duree;
	}
	public void setDuree(String duree) {
		this.duree = duree;
	}
	public Projet getProjet() {
		return projet;
	}
	public void setProjet(Projet projet) {
		this.projet = projet;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public Set<Timesheet> getTimesheet() {
		return timesheet;
	}
	public void setTimesheet(Set<Timesheet> timesheet) {
		this.timesheet = timesheet;
	}
	
	
	 

}