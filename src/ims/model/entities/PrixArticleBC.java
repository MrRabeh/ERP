package ims.model.entities;

public class PrixArticleBC {

	
	private String reference;
	private String designation;
	private String chaineqantite;;
	private String PU;
	private String PT;
	
	public PrixArticleBC(String reference, String designation,
			String chaineqantite, String pU, String pT) {
		super();
		this.reference = reference;
		this.designation = designation;
		this.chaineqantite = chaineqantite;
		PU = pU;
		PT = pT;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getChaineqantite() {
		return chaineqantite;
	}
	public void setChaineqantite(String chaineqantite) {
		this.chaineqantite = chaineqantite;
	}
	public String getPU() {
		return PU;
	}
	public void setPU(String pU) {
		PU = pU;
	}
	public String getPT() {
		return PT;
	}
	public void setPT(String pT) {
		PT = pT;
	}
	
}
