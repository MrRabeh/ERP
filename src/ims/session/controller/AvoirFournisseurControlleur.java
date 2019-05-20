/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;
 
  

import ims.model.entities.AvoirFournisseur;

import ims.service.ModelService;


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
public class AvoirFournisseurControlleur implements Serializable{
 
	
    /**
		 * creator RABEH TARIK
		 */
	private static final long serialVersionUID = 1L;
 
	/**
     * Creates a new instance of AvoirFournisseur
     */
   
    @ManagedProperty(value="#{avoirfournisseurManager}")
    private ModelService<AvoirFournisseur> manager;

	private List<AvoirFournisseur> avoirfournisseurs ; 
    private List<AvoirFournisseur> filteredavoirfournisseurs; 
  //  private List<Tache> taches=new ArrayList(),taches_1;
     
    
	public AvoirFournisseurControlleur() {
   	 
    }
    
	private AvoirFournisseur avoirfournisseur=new AvoirFournisseur();
    public ModelService<AvoirFournisseur> getManager() {
		return manager;
	}





	public void setManager(ModelService<AvoirFournisseur> manager) {
		this.manager = manager;
	}





	public AvoirFournisseur getAvoirfournisseur() {
		return avoirfournisseur;
	}





	public void setAvoirfournisseur(AvoirFournisseur avoirfournisseur) {
		this.avoirfournisseur = avoirfournisseur;
	}





	public List<AvoirFournisseur> getAvoirfournisseurs() {
		return avoirfournisseurs;
	}





	public void setAvoirfournisseurs(List<AvoirFournisseur> avoirfournisseurs) {
		this.avoirfournisseurs = avoirfournisseurs;
	}





	public List<AvoirFournisseur> getFilteredavoirfournisseurs() {
		return filteredavoirfournisseurs;
	}





	public void setFilteredavoirfournisseurs(
			List<AvoirFournisseur> filteredavoirfournisseurs) {
		this.filteredavoirfournisseurs = filteredavoirfournisseurs;
	}
	

	  public void insert(){
		   
	    	System.out.println("INSERT");
	    	FacesMessage msg;
	    	try{
	    		manager.insertObject(avoirfournisseur);
	           msg = new FacesMessage("vous avez creer avec success");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        } catch (Exception e) {
	               msg = new FacesMessage("ERROR CREATION");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        }   
	    }
	 
	    public String Delete(){
	        FacesMessage msg;
	        try {
	        manager.deleteObject(avoirfournisseur);
	          this.getAvoirfournisseurs();
	       msg = new FacesMessage("  bien Supprimer");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	       return "index.xhtml";
	        } catch (Exception e) {
	            msg = new FacesMessage("ERORR de Suppression");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	       return "index.xhtml";
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
	     
	     public void onRowEdit(RowEditEvent event) {
	         FacesMessage msg;
	         try {
	            manager.updateObject(((AvoirFournisseur) event.getObject()));
	             msg = new FacesMessage("bien Modifié","");
	                FacesContext.getCurrentInstance().addMessage(null, msg);
	         } catch (Exception e) {
	            msg = new FacesMessage("mal Modifier","");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	         }
	        
	    }
		 
    
		
		   public void imprimer(){
		    	 System.out.print("imprimer");
		      }
		 
}

