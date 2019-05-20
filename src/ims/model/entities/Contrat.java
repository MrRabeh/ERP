package ims.model.entities;

import java.io.Serializable;

public class Contrat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String numcontrat;
	private Client clt;
	private TypeContrat type;
	private double duree;
	private double nbMaxprevu;
	private double nbMaxcurative;
	private double nbMaxHotLine;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumcontrat() {
		return numcontrat;
	}
	public void setNumcontrat(String numcontrat) {
		this.numcontrat = numcontrat;
	}
	public Client getClt() {
		return clt;
	}
	public void setClt(Client clt) {
		this.clt = clt;
	}
	public TypeContrat getType() {
		return type;
	}
	public void setType(TypeContrat type) {
		this.type = type;
	}
	public double getDuree() {
		return duree;
	}
	public void setDuree(double duree) {
		this.duree = duree;
	}
	public double getNbMaxprevu() {
		return nbMaxprevu;
	}
	public void setNbMaxprevu(double nbMaxprevu) {
		this.nbMaxprevu = nbMaxprevu;
	}
	public double getNbMaxcurative() {
		return nbMaxcurative;
	}
	public void setNbMaxcurative(double nbMaxcurative) {
		this.nbMaxcurative = nbMaxcurative;
	}
	public double getNbMaxHotLine() {
		return nbMaxHotLine;
	}
	public void setNbMaxHotLine(double nbMaxHotLine) {
		this.nbMaxHotLine = nbMaxHotLine;
	}
	
	
	
	
	

}
