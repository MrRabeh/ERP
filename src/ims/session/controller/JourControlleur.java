/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;
 
 
import ims.model.entities.Jour;

import ims.service.ModelService;
import java.io.Serializable; 
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
/**
 *
 * @author rabeh
 */
@ManagedBean
@RequestScoped
public class JourControlleur implements Serializable{
 
	
	   /**
		* creator RABEH TARIK
	    **/
	private static final long serialVersionUID = 1L;
 

	/**
     * Creates a new instance of JourControlleur
     */
   
    @ManagedProperty(value="#{jourManager}")
    private ModelService<Jour> manager;
     
    
	private Jour jour=new Jour();
    private List<Jour> jours,jours2; 
    private List<Jour> filteredjours; 
    private String total;
  
   
   	
   	 
   	public Jour getJour() {
		return jour;
	}




	public void setJour(Jour jour) {
		this.jour = jour;
	}




	public List<Jour> getJours() {
		jours=manager.getObject();
		return jours;
	}




	public void setJours(List<Jour> jours) {
		this.jours = jours;
	}




	public List<Jour> getJours2() {
		return jours2;
	}




	public void setJours2(List<Jour> jours2) {
		this.jours2 = jours2;
	}




	public List<Jour> getFilteredjours() {
		return filteredjours;
	}




	public void setFilteredjours(List<Jour> filteredjours) {
		this.filteredjours = filteredjours;
	}




	public String getTotal() {
		return total;
	}




	public void setTotal(String total) {
		this.total = total;
	}




	public JourControlleur() {
       	 
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
   	     
   	     
   		public ModelService<Jour> getManager() {
   			return manager;
   		}
   		public void setManager(ModelService<Jour> manager) {
   			this.manager = manager;
   		}
   		 
   		
   		
   		 
 
   		
    
   }

