package ims.model.entities;

import java.io.Serializable;

public class Stock_Livraison implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Stock stock;
	private BonLivraison livraison;
	private float qte;
	private String designationclient;
	private String numeroserielivre;
	
	public Stock_Livraison() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	public BonLivraison getLivraison() {
		return livraison;
	}
	public void setLivraison(BonLivraison livraison) {
		this.livraison = livraison;
	}
	public float getQte() {
		return qte;
	}
	public void setQte(float qte) {
		this.qte = qte;
	}
	public String getDesignationclient() {
		return designationclient;
	}
	public void setDesignationclient(String designationclient) {
		this.designationclient = designationclient;
	}
	public String getNumeroserielivre() {
		return numeroserielivre;
	}
	public void setNumeroserielivre(String numeroserielivre) {
		this.numeroserielivre = numeroserielivre;
	}
	
	
	
	
}
