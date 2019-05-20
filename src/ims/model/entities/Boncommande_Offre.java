package ims.model.entities;

public class Boncommande_Offre {
	
	private int idboncommandeoffre;
	private Offre offre;
	private BonCommande commande;
	
	public Boncommande_Offre() {
		super();
	}
	public Offre getOffre() {
		return offre;
	}
	public void setOffre(Offre offre) {
		this.offre = offre;
	}
	public BonCommande getCommande() {
		return commande;
	}
	public void setCommande(BonCommande commande) {
		this.commande = commande;
	}
	public int getIdboncommandeoffre() {
		return idboncommandeoffre;
	}
	public void setIdboncommandeoffre(int idboncommandeoffre) {
		this.idboncommandeoffre = idboncommandeoffre;
	}

	

}
