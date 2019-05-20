package ims.model.entities;

import java.io.Serializable;
import java.util.Date;

public class Planification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String titreplan;
	private Date startdate;
	private Date enddate;
	private Date allday;
	private Contrat contrat;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitreplan() {
		return titreplan;
	}
	public void setTitreplan(String titreplan) {
		this.titreplan = titreplan;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public Date getAllday() {
		return allday;
	}
	public void setAllday(Date allday) {
		this.allday = allday;
	}
	public Contrat getContrat() {
		return contrat;
	}
	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}
	
	
	
	
	

}
