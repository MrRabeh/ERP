package ims.model.entities;

import java.io.Serializable;

public class Contact implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int codeclient;
	private String nom="";
	private String prenom="";
	private String adr="";
	private String fonction="";
	private String dept="";
	private String tel="";
	private String faxclient="";
	private String gsm1="";
	private String gsm2="";
	private String email="";
	private String email2="";
	private String profil="";
	private Client client;
	private Fournisseur fournisseur;
	private Pays pays;
	private Ville ville;
	private String principal="Non";
	
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getCodeclient() {
		return codeclient;
	}

	public void setCodeclient(int codeclient) {
		this.codeclient = codeclient;
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

	public String getAdr() {
		return adr;
	}

	public void setAdr(String adr) {
		this.adr = adr;
	}

	public String getFonction() {
		return fonction;
	}

	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFaxclient() {
		return faxclient;
	}

	public void setFaxclient(String faxclient) {
		this.faxclient = faxclient;
	}

	public String getGsm1() {
		return gsm1;
	}

	public void setGsm1(String gsm1) {
		this.gsm1 = gsm1;
	}

	public String getGsm2() {
		return gsm2;
	}

	public void setGsm2(String gsm2) {
		this.gsm2 = gsm2;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getProfil() {
		return profil;
	}

	public void setProfil(String profil) {
		this.profil = profil;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public Fournisseur getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}
	
	

}
