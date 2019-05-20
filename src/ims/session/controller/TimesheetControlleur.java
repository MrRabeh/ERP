/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;
 
 
import ims.model.entities.Jour;
 
import ims.model.entities.Tache;
import ims.model.entities.Utilisateur;
 
import ims.model.entities.Timesheet;
 
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
 

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

 
 
/**
 *
 * @author rabeh
 */
@ManagedBean
@SessionScoped
public class TimesheetControlleur implements Serializable{
 
	
	 /**
	* creator RABEH TARIK
    **/
	private static final long serialVersionUID = 1L;
 

	/**
     * Creates a new instance of TimesheetControlleur
     */
	 @ManagedProperty(value="#{jourManager}")
	 private ModelService<Jour> managerjour;
	 
	@ManagedProperty(value="#{timesheetManager}")
    private ModelService<Timesheet> manager;

    @ManagedProperty(value="#{userManager}")
    private ModelService<Utilisateur> managerU;
    
    @ManagedProperty(value="#{tacheManager}")
    private ModelService<Tache> managerTache;
    
    @ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
    
    
	private Timesheet timesheet;
    private List<Timesheet> timesheets;
    private Date datetimesheet;
    private String idjour="0";
    private String idtache="0";
    private String heur="";
	
	
	 public TimesheetControlleur() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	 public void actualiser(){
		 timesheets=null;
	 }
	 
	 public void oncellEdit(){
	    	String nameuser=FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
	    	FacesMessage msg;
	    	timesheets=null;
	    	try {
	    		if(idtache!=null){
	    			if(!idtache.equals("0"))
	    				timesheet.setTache(managerTache.getObject(Integer.parseInt(idtache)));
	    		}
	    		if(idjour!=null){
	    			if(!idjour.equals("0"))
	    				timesheet.setJour(managerjour.getObject(Integer.parseInt(idjour)));
	    		
	    		}
	    		timesheet.setUser(managerU.getByName(nameuser));
				managerjdbc.updateTimesheet(timesheet);
	    		msg = new FacesMessage("vous avez creer avec success");
	    		FacesContext.getCurrentInstance().addMessage(null, msg);
	    		idjour="0";
	    		idtache="0";
			} catch (Exception e) {
				 msg = new FacesMessage("Exception "+e.getLocalizedMessage());
	       FacesContext.getCurrentInstance().addMessage(null, msg);
			}
	 }

	public void insert(){
    	String nameuser=FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    	FacesMessage msg;
    	timesheet=new Timesheet();
    	timesheets=null;
	    	try{
	    		if(idtache!=null){
	    			if(!idtache.equals("0"))
	    				timesheet.setTache(managerTache.getObject(Integer.parseInt(idtache)));
	    		}
	    		if(idjour!=null){
	    			if(!idjour.equals("0"))
	    				timesheet.setJour(managerjour.getObject(Integer.parseInt(idjour)));
	    		
	    		}
	    		if(datetimesheet!=null)
	    			timesheet.setDatetimesheet(datetimesheet);
	    		else
	    			timesheet.setDatetimesheet(new Date());
	    		timesheet.setHeur(heur+"h");
	    		timesheet.setUser(managerU.getByName(nameuser));
	    		manager.insertObject(timesheet);
	    		idjour="0";
	    		idtache="0";
	    		msg = new FacesMessage("céer avec success");
	    		FacesContext.getCurrentInstance().addMessage(null, msg);
	        } catch (Exception e) {
	               msg = new FacesMessage("Exception "+e.getLocalizedMessage());
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        }   
	    }
	 
	    public void Delete(){
	        FacesMessage msg;
	        try {
	        manager.deleteObject(timesheet);
	       msg = new FacesMessage("Supprimer avec Success");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        } catch (Exception e) {
	        	 msg = new FacesMessage("Exception "+e.getLocalizedMessage());
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
	        
	    }

		public ModelService<Jour> getManagerjour() {
			return managerjour;
		}

		public void setManagerjour(ModelService<Jour> managerjour) {
			this.managerjour = managerjour;
		}

		public ModelService<Timesheet> getManager() {
			return manager;
		}

		public void setManager(ModelService<Timesheet> manager) {
			this.manager = manager;
		}

		public ModelService<Utilisateur> getManagerU() {
			return managerU;
		}

		public void setManagerU(ModelService<Utilisateur> managerU) {
			this.managerU = managerU;
		}

		public ModelService<Tache> getManagerTache() {
			return managerTache;
		}

		public void setManagerTache(ModelService<Tache> managerTache) {
			this.managerTache = managerTache;
		}

		public Timesheet getTimesheet() {
			return timesheet;
		}

		public void setTimesheet(Timesheet timesheet) {
			this.timesheet = timesheet;
		}

		public List<Timesheet> getTimesheets() {
			if(timesheets==null)
				timesheets=manager.getObject();
			return timesheets;
		}

		public void setTimesheets(List<Timesheet> timesheets) {
			this.timesheets = timesheets;
		}

		public Date getDatetimesheet() {
			return datetimesheet;
		}

		public void setDatetimesheet(Date datetimesheet) {
			this.datetimesheet = datetimesheet;
		}

		
		public String getIdjour() {
			return idjour;
		}

		public void setIdjour(String idjour) {
			this.idjour = idjour;
		}

		public String getIdtache() {
			return idtache;
		}

		public void setIdtache(String idtache) {
			this.idtache = idtache;
		}

		public ModelServiceJDBC getManagerjdbc() {
			return managerjdbc;
		}

		public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
			this.managerjdbc = managerjdbc;
		}

		public String getHeur() {
			return heur;
		}

		public void setHeur(String heur) {
			this.heur = heur;
		}
	     
		
 
}

