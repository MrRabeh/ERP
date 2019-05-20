package ims.model.entities;

import java.io.Serializable;

public class ListeBoncommandeFactureOffre implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Facture facture=new Facture();
	private BonCommande commande=new BonCommande();
	private Offre offre=new Offre();
	private BonLivraison bnlivraison=new BonLivraison();
	private Client client=new Client();
	public ListeBoncommandeFactureOffre() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ListeBoncommandeFactureOffre(Facture facture, BonCommande commande,
			Offre offre) {
		super();
		this.facture = facture;
		this.commande = commande;
		this.offre = offre;
	}
	
	public ListeBoncommandeFactureOffre(int id,String numoffre,String numcommande,String numbl,String numfacture,String societeclient) {
		super();
		this.id=id;
		offre.setRef(numoffre);
		commande.setNumercommande(numcommande);
		bnlivraison.setRef(numbl);
		facture.setRef(numfacture);
		client.setSociete(societeclient);
	}
	public Facture getFacture() {
		return facture;
	}
	public void setFacture(Facture facture) {
		this.facture = facture;
	}
	public BonCommande getCommande() {
		return commande;
	}
	public void setCommande(BonCommande commande) {
		this.commande = commande;
	}
	public Offre getOffre() {
		return offre;
	}
	public void setOffre(Offre offre) {
		this.offre = offre;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BonLivraison getBnlivraison() {
		return bnlivraison;
	}
	public void setBnlivraison(BonLivraison bnlivraison) {
		this.bnlivraison = bnlivraison;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	

}
