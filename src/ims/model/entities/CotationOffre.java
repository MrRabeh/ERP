package ims.model.entities;

public class CotationOffre {
	
	
	private int id;
	private Offre offre;
	private String chemin;
	
	
	public CotationOffre() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Offre getOffre() {
		return offre;
	}


	public void setOffre(Offre offre) {
		this.offre = offre;
	}


	public String getChemin() {
		return chemin;
	}


	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	
}
