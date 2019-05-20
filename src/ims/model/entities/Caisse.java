package ims.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Caisse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Date datedebutcaisse;
	private Date datefincaisse;
	private String moiscaisse="";
	private double entreetotal=0;
	private double sortietotal=0;
	private double restetotal=0;
	private years objyear=new years();
	
	private Set<LigneCaisse> lignes=new HashSet<LigneCaisse>();

	public Caisse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatedebutcaisse() {
		return datedebutcaisse;
	}

	public void setDatedebutcaisse(Date datedebutcaisse) {
		this.datedebutcaisse = datedebutcaisse;
	}

	public Date getDatefincaisse() {
		return datefincaisse;
	}

	public void setDatefincaisse(Date datefincaisse) {
		this.datefincaisse = datefincaisse;
	}

	public String getMoiscaisse() {
		return moiscaisse;
	}

	public void setMoiscaisse(String moiscaisse) {
		this.moiscaisse = moiscaisse;
	}

	public double getEntreetotal() {
		return entreetotal;
	}

	public void setEntreetotal(double entreetotal) {
		this.entreetotal = entreetotal;
	}

	public double getSortietotal() {
		return sortietotal;
	}

	public void setSortietotal(double sortietotal) {
		this.sortietotal = sortietotal;
	}

	public double getRestetotal() {
		return restetotal;
	}

	public void setRestetotal(double restetotal) {
		this.restetotal = restetotal;
	}

	public Set<LigneCaisse> getLignes() {
		return lignes;
	}

	public void setLignes(Set<LigneCaisse> lignes) {
		this.lignes = lignes;
	}

	public years getObjyear() {
		return objyear;
	}

	public void setObjyear(years objyear) {
		this.objyear = objyear;
	}
	
	
	

}
