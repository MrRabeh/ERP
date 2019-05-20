/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;

import ims.model.entities.Langue;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

/**
 *
 * @author rabeh
 */
@ManagedBean
@RequestScoped
public class LangueController implements Serializable{

	   /**
		* creator RABEH TARIK
	    **/
	private static final long serialVersionUID = 1L;

	/**
     * Creates a new instance of LangueController
     */
    @ManagedProperty(value="#{LangueManager}")
    private ModelService<Langue> managerlangue;
    
    @ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
    
    private Langue vlangue=new Langue();
    private List<Langue> vlangues;
    private List<Langue> filteredLangue;
    
   
    
    public LangueController() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String preparecreer(){
     	vlangue=new Langue();
    	return "insertlangue?faces-redirect=true";
    }
	
    public void insert(){
        FacesMessage msg;
        vlangues=null;
        try {
        		managerlangue.insertObject(vlangue);
        		vlangue=new Langue();
               msg = new FacesMessage("langue créer avec success");
               FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
                   msg = new FacesMessage("Exception");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
       
       public void Delete(){
        FacesMessage msg;
        vlangues=null;
        try {
        managerlangue.deleteObject(vlangue);
       msg = new FacesMessage("langue Supprimer avec Success");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            msg = new FacesMessage("Exception");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
       
       public void update(){
           FacesMessage msg;
           vlangues=null;
           try {
           managerjdbc.updateLangue(vlangue);
          msg = new FacesMessage("langue Modifier avec Success");
          FacesContext.getCurrentInstance().addMessage(null, msg);
           } catch (Exception e) {
               msg = new FacesMessage("Exception");
          FacesContext.getCurrentInstance().addMessage(null, msg);
           }
           
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
     
    
    public ModelService<Langue> getManagerlangue() {
        return managerlangue;
    }

    public void setManagerlangue(ModelService<Langue> managerlangue) {
        this.managerlangue = managerlangue;
    }

    public Langue getVlangue() {
        return vlangue;
    }

    public void setVlangue(Langue vlangue) {
        this.vlangue = vlangue;
    }

    public List<Langue> getVlangues() {
    	if(vlangues==null)
    		vlangues=managerlangue.getObject();
        return vlangues;
    }

    public void setVlangues(List<Langue> vlangues) {
        this.vlangues = vlangues;
    }

    public List<Langue> getFilteredLangue() {
        return filteredLangue;
    }

    public void setFilteredLangue(List<Langue> filteredLangue) {
        this.filteredLangue = filteredLangue;
    }
	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}
	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

  
       
    
}
