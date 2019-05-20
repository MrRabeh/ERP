package ims.model.entities;


public class Facture_Livraison {

	private int id;
	private Facture facture;
	private BonLivraison livraison;
	public Facture_Livraison() {
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
	public BonLivraison getLivraison() {
		return livraison;
	}
	public void setLivraison(BonLivraison livraison) {
		this.livraison = livraison;
	}
	
	
	
	
}
