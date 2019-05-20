package ims.model.entities;

public class ObjetPrixF {

	
	float totalHT;
	float TotalTTC;
	float tva;
	
	
	public ObjetPrixF(float totalHT, float totalTTC, float tva) {
		super();
		this.totalHT = totalHT;
		TotalTTC = totalTTC;
		this.tva = tva;
	}


	public float getTotalHT() {
		return totalHT;
	}


	public void setTotalHT(float totalHT) {
		this.totalHT = totalHT;
	}


	public float getTotalTTC() {
		return TotalTTC;
	}


	public void setTotalTTC(float totalTTC) {
		TotalTTC = totalTTC;
	}


	public float getTva() {
		return tva;
	}


	public void setTva(float tva) {
		this.tva = tva;
	}
	
	
}
