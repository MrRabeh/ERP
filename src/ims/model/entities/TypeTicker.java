package ims.model.entities;

import java.io.Serializable;

public class TypeTicker implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String typeticker="";
	
	
	public TypeTicker() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeticker() {
		return typeticker;
	}
	public void setTypeticker(String typeticker) {
		this.typeticker = typeticker;
	}
	
	

}
