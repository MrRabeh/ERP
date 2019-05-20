package ims.model.entities;

public class CoefficientFacture {

	private int id;
	private String coefficient;
	
	public CoefficientFacture() {
		super();
		
	}

	public CoefficientFacture(String coefficient) {
		super();
		this.coefficient = coefficient;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCoefficient() {
		return coefficient;
	}
	public void setCoefficient(String coefficient) {
		this.coefficient = coefficient;
	}
	
	
	
}
