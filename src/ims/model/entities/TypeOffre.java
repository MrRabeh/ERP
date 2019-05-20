package ims.model.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TypeOffre implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String type;
	private String cotenutype;
	private Set<Offre> offres=new HashSet<Offre>();
	
	public TypeOffre() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCotenutype() {
		return cotenutype;
	}

	public void setCotenutype(String cotenutype) {
		this.cotenutype = cotenutype;
	}

	public Set<Offre> getOffres() {
		return offres;
	}

	public void setOffres(Set<Offre> offres) {
		this.offres = offres;
	}
	
	
	

}
