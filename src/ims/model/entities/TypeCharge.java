package ims.model.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TypeCharge implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String typecharge;
	private String description;
	private Set<Charge> charges=new HashSet<Charge>();
	
	public TypeCharge() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypecharge() {
		return typecharge;
	}
	public void setTypecharge(String typecharge) {
		this.typecharge = typecharge;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<Charge> getCharges() {
		return charges;
	}
	public void setCharges(Set<Charge> charges) {
		this.charges = charges;
	}
	
	

}
