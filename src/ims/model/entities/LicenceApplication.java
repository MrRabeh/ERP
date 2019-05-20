package ims.model.entities;

import java.util.Date;
import java.util.Set;

public class LicenceApplication {
	
	private int id;
	private String cle="";
	private Date dateexpiration;
	private String codegeneration="";
	private Date dategenerer;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCle() {
		return cle;
	}
	public void setCle(String cle) {
		this.cle = cle;
	}
	public Date getDateexpiration() {
		return dateexpiration;
	}
	public void setDateexpiration(Date dateexpiration) {
		this.dateexpiration = dateexpiration;
	}
	public String getCodegeneration() {
		return codegeneration;
	}
	public void setCodegeneration(String codegeneration) {
		this.codegeneration = codegeneration;
	}
	public Date getDategenerer() {
		return dategenerer;
	}
	public void setDategenerer(Date dategenerer) {
		this.dategenerer = dategenerer;
	}

	

}
