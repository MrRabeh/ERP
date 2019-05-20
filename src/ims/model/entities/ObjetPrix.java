package ims.model.entities;

public class ObjetPrix {

	
	String totalHT;
	String TotalTTC;
	String tva;
	
	
	public ObjetPrix(String totalHT, String totalTTC, String tva) {
		super();
		this.totalHT = totalHT;
		TotalTTC = totalTTC;
		this.tva = tva;
	}
	public String getTotalHT() {
		return totalHT;
	}
	public void setTotalHT(String totalHT) {
		this.totalHT = totalHT;
	}
	public String getTotalTTC() {
		return TotalTTC;
	}
	public void setTotalTTC(String totalTTC) {
		TotalTTC = totalTTC;
	}
	public String getTva() {
		return tva;
	}
	public void setTva(String tva) {
		this.tva = tva;
	}
	
	
}
