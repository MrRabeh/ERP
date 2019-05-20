package ims.model.entities;

import java.io.Serializable;
import java.util.Date;

public class Intervention implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String numintervention;
	private String nomclient;
	private String prenomclient;
	private String fonction;
	private String site_intervention;
	
	private Date datedebut;
	private Date datefin;
	private double nbredemijournee;
	private String objet;
	private String detail;
	private String observation;
	
	private Date dateconsultant;
	private Date dateclient;
	
	//pour afficher le N°BC
	private Offre Offre;
	private Client client;
	private Employee emp;
	
	public Intervention() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public String getNomclient() {
		return nomclient;
	}
	public void setNomclient(String nomclient) {
		this.nomclient = nomclient;
	}
	public String getPrenomclient() {
		return prenomclient;
	}
	public void setPrenomclient(String prenomclient) {
		this.prenomclient = prenomclient;
	}
	public String getFonction() {
		return fonction;
	}
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}
	public String getSite_intervention() {
		return site_intervention;
	}
	public void setSite_intervention(String site_intervention) {
		this.site_intervention = site_intervention;
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
	public double getNbredemijournee() {
		return nbredemijournee;
	}
	public void setNbredemijournee(double nbredemijournee) {
		this.nbredemijournee = nbredemijournee;
	}
	public Employee getEmp() {
		return emp;
	}
	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	public String getObjet() {
		return objet;
	}
	public void setObjet(String objet) {
		this.objet = objet;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public Date getDateconsultant() {
		return dateconsultant;
	}
	public void setDateconsultant(Date dateconsultant) {
		this.dateconsultant = dateconsultant;
	}
	public Date getDateclient() {
		return dateclient;
	}
	public void setDateclient(Date dateclient) {
		this.dateclient = dateclient;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumintervention() {
		return numintervention;
	}
	public void setNumintervention(String numintervention) {
		this.numintervention = numintervention;
	}
	public Offre getOffre() {
		return Offre;
	}
	public void setOffre(Offre offre) {
		Offre = offre;
	}
	
	
	
}
