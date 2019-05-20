package ims.model.entities;

import java.io.Serializable;

public class Facture_Avoir implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Facture facture;
	private AvoirClient avoir;
	public Facture_Avoir() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Facture getFacture() {
		return facture;
	}
	public void setFacture(Facture facture) {
		this.facture = facture;
	}
	public AvoirClient getAvoir() {
		return avoir;
	}
	public void setAvoir(AvoirClient avoir) {
		this.avoir = avoir;
	}
	
	
	
	

}
