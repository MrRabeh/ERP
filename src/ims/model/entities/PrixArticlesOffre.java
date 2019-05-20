package ims.model.entities;

public class PrixArticlesOffre {

	
	private String reference;
	private String designation;
	private String chaineqantite;;
	private String PU_HT2;
	private String PT_HT2;
	private String typeoffre;
	private String rubrique;
	private int optionnel;
	private int gratuite;
	
	public PrixArticlesOffre() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PrixArticlesOffre(String reference, String designation,
			String chaineqantite, String pU_HT2, String pT_HT2,String typeoffre,String rubrique,int optionel,int gratuite) {
		super();
		this.reference = reference;
		this.designation = designation;
		this.chaineqantite = chaineqantite;
		PU_HT2 = pU_HT2;
		PT_HT2 = pT_HT2;
		this.typeoffre=typeoffre;
		this.rubrique=rubrique;
		this.optionnel=optionel;
		this.gratuite=gratuite;
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
	public String getPU_HT2() {
		return PU_HT2;
	}
	public void setPU_HT2(String pU_HT2) {
		PU_HT2 = pU_HT2;
	}
	public String getPT_HT2() {
		return PT_HT2;
	}
	public void setPT_HT2(String pT_HT2) {
		PT_HT2 = pT_HT2;
	}

	public String getTypeoffre() {
		return typeoffre;
	}

	public void setTypeoffre(String typeoffre) {
		this.typeoffre = typeoffre;
	}

	public String getRubrique() {
		return rubrique;
	}

	public void setRubrique(String rubrique) {
		this.rubrique = rubrique;
	}

	public int getOptionnel() {
		return optionnel;
	}

	public void setOptionnel(int optionnel) {
		this.optionnel = optionnel;
	}

	public int getGratuite() {
		return gratuite;
	}

	public void setGratuite(int gratuite) {
		this.gratuite = gratuite;
	}

	
	
}
