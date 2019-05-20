/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;


import ims.model.entities.Pointage;
import ims.model.entities.Utilisateur;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author rabeh
 */
@ManagedBean
@SessionScoped
public class PointageController implements Serializable{

	 /**
	* creator RABEH TARIK
    **/
	
	private static final long serialVersionUID = 1L;

	/**
     * Creates a new instance of pointageControlleur
     */
   
    @ManagedProperty(value="#{pointageManager}")
    private ModelService<Pointage> manager;
    
    @ManagedProperty(value="#{userManager}")
    private ModelService<Utilisateur> manageruser;
    
    @ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
 
    
    private Pointage pointage;
	private List<Pointage> pointages;
	
	private SimpleDateFormat ftheurseulement= new SimpleDateFormat("HH:mm:ss");
    
    private Date datepointage;
    private Date entree;
    private Date sortie;
    private String selectperiode;
    
    
    public void actualiser(){
    	pointages=null;
    }
    
    public void insert(){

    	FacesMessage msg;
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	pointage=new Pointage(); 
    	pointages=null;
    	try{
    		 pointage.setPeriode(selectperiode);
    		 pointage.setDatePointage(datepointage);
    		 pointage.setNom(user.getUsername());
    		 pointage.setEntree(ftheurseulement.format(entree));
    		 pointage.setSortie(ftheurseulement.format(sortie));
    		 System.out.println("PREPARE INSERT");
    		 manager.insertObject(pointage);
    		 
           msg = new FacesMessage("vous avez pointé avec success");
           FacesContext.getCurrentInstance().addMessage(null, msg);
           datepointage=null;
           entree=null;
           sortie=null;
           selectperiode="";
           pointage=null;
        } catch (Exception e) {
               msg = new FacesMessage("Probleme de creation","Contacter Administration");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }   
    }
 
    public void oncellEdit(){
        FacesMessage msg;
        pointages=null;
        try {
        	managerjdbc.updatePointage(pointage);
        	 msg = new FacesMessage("vous avez pointé avec success");
 	       FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
		       msg = new FacesMessage("pointage est bien Supprimer");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
    
    public void Delete(){
        FacesMessage msg;
        pointages=null;
        try {
        	
        	manager.deleteObject(pointage);
	       msg = new FacesMessage("pointage est bien Supprimer");
	       FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Exception e) {
            msg = new FacesMessage("ERORR de Suppression");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }

    public Pointage getPointage() {
        return pointage;
    }

    public void setPointage(Pointage pointage) {
        this.pointage = pointage;
    }

    public List<Pointage> getPointages() {
    	
    	if(pointages==null)
    		pointages=manager.getObject();
    	
        return pointages;
    }

    public void setPointages(List<Pointage> pointages) {
        this.pointages = pointages;
    }

    public ModelService<Pointage> getManager() {
        return manager;
    }

    public void setManager(ModelService<Pointage> manager) {
        this.manager = manager;
    }
    
     @SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean filterByPrice(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if(filterText == null||filterText.equals("")) {
            return true;
        }
         
        if(value == null) {
            return false;
        }
         
        return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
    }
	public ModelService<Utilisateur> getManageruser() {
		return manageruser;
	}

	public void setManageruser(ModelService<Utilisateur> manageruser) {
		this.manageruser = manageruser;
	}

	public Date getDatepointage() {
		return datepointage;
	}

	public void setDatepointage(Date datepointage) {
		this.datepointage = datepointage;
	}

	public String getSelectperiode() {
		return selectperiode;
	}

	public void setSelectperiode(String selectperiode) {
		this.selectperiode = selectperiode;
	}

	public Date getEntree() {
		return entree;
	}

	public void setEntree(Date entree) {
		this.entree = entree;
	}

	public Date getSortie() {
		return sortie;
	}

	public void setSortie(Date sortie) {
		this.sortie = sortie;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public SimpleDateFormat getFtheurseulement() {
		return ftheurseulement;
	}

	public void setFtheurseulement(SimpleDateFormat ftheurseulement) {
		this.ftheurseulement = ftheurseulement;
	}

	
    
    
}
