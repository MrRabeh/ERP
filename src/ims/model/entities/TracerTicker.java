package ims.model.entities;

import java.util.Date;

public class TracerTicker {
	
	private int id;
	private Date datetracer;
	private Ticker ticker;
	private String etat;
	private String actions="";
	
	
	public TracerTicker() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDatetracer() {
		return datetracer;
	}
	public void setDatetracer(Date datetracer) {
		this.datetracer = datetracer;
	}
	public Ticker getTicker() {
		return ticker;
	}
	public void setTicker(Ticker ticker) {
		this.ticker = ticker;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public String getActions() {
		return actions;
	}
	public void setActions(String actions) {
		this.actions = actions;
	}
	
	

}
