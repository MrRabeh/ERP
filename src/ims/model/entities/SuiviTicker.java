package ims.model.entities;

import java.io.Serializable;
import java.util.Date;

public class SuiviTicker implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String refsuivi;
	private String statu;
	private Date Datesuivi;
	private Date Datefinsuivi;
	private double duree;
	private String remarque;
	private Utilisateur user;
	private Ticker ticker;
	
	public SuiviTicker() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRefsuivi() {
		return refsuivi;
	}
	public void setRefsuivi(String refsuivi) {
		this.refsuivi = refsuivi;
	}
	public String getStatu() {
		return statu;
	}
	public void setStatu(String statu) {
		this.statu = statu;
	}
	public String getRemarque() {
		return remarque;
	}
	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}
	public Utilisateur getUser() {
		return user;
	}
	public void setUser(Utilisateur user) {
		this.user = user;
	}
	public Date getDatesuivi() {
		return Datesuivi;
	}
	public void setDatesuivi(Date datesuivi) {
		Datesuivi = datesuivi;
	}
	public Date getDatefinsuivi() {
		return Datefinsuivi;
	}
	public void setDatefinsuivi(Date datefinsuivi) {
		Datefinsuivi = datefinsuivi;
	}
	public double getDuree() {
		return duree;
	}
	public void setDuree(double duree) {
		this.duree = duree;
	}
	public Ticker getTicker() {
		return ticker;
	}
	public void setTicker(Ticker ticker) {
		this.ticker = ticker;
	}
	
	

}
