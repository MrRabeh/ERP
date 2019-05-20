package ims.model.entities;

import java.util.HashSet;
import java.util.Set;

public class TypeFacture {

	private int id;
	private String typefacture;
	private Set<Facture> factures=new HashSet<Facture>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypefacture() {
		return typefacture;
	}
	public void setTypefacture(String typefacture) {
		this.typefacture = typefacture;
	}
	public Set<Facture> getFactures() {
		return factures;
	}
	public void setFactures(Set<Facture> factures) {
		this.factures = factures;
	}
	
}
