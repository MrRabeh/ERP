package ims.model.entities;

import java.io.Serializable;
import java.util.Date;

public class Timesheet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int idtimesheet;
	private String heur;
	private Date datetimesheet;
	private Jour jour;
	private Tache tache;
	private Utilisateur user;
	
	public Timesheet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdtimesheet() {
		return idtimesheet;
	}

	public void setIdtimesheet(int idtimesheet) {
		this.idtimesheet = idtimesheet;
	}

	public String getHeur() {
		return heur;
	}

	public void setHeur(String heur) {
		this.heur = heur;
	}

	public Date getDatetimesheet() {
		return datetimesheet;
	}

	public void setDatetimesheet(Date datetimesheet) {
		this.datetimesheet = datetimesheet;
	}

	public Jour getJour() {
		return jour;
	}

	public void setJour(Jour jour) {
		this.jour = jour;
	}

	public Tache getTache() {
		return tache;
	}

	public void setTache(Tache tache) {
		this.tache = tache;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	
	
	

}