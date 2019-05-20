package ims.model.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Jour implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int idjour;
    
	private String journame;
	 
	private Set<Timesheet> timesheets=new HashSet<Timesheet>();
	 

	public int getIdjour() {
		return idjour;
	}



	public void setIdjour(int idjour) {
		this.idjour = idjour;
	}



	public Jour() {
	    }


 


	 

 


	public String getJourname() {
		return journame;
	}



	public void setJourname(String journame) {
		this.journame = journame;
	}


  


	public Jour(int idjour,    
			String journame) {
		super();
		this.idjour = idjour;
	 
		 
		this.journame = journame;
	 
	}



	public Set<Timesheet> getTimesheets() {
		return timesheets;
	}



	public void setTimesheets(Set<Timesheet> timesheets) {
		this.timesheets = timesheets;
	}

 
 
 

 
	 

}