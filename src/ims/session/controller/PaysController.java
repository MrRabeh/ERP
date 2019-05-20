package ims.session.controller;
 
import ims.model.entities.Pays;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

@ManagedBean
@RequestScoped
public class PaysController implements Serializable{

	   /**
		* creator RABEH TARIK
	    **/
		private static final long serialVersionUID = 1L;
		/**
	     * Creates a new instance of PaysController
	     */
	     
	       
		@ManagedProperty(value="#{PaysManager}")
	    private ModelService<Pays> manager;
		
		@ManagedProperty(value="#{JDBCManager}")
	    private ModelServiceJDBC managerjdbc;
	  
	    //private List<Pays> countries=new ArrayList<Pays>();
	    
	    
	    private Pays country=new Pays();
	    private Pays countrytempo=new Pays();
	    private List<Pays> countries;
	    private List<Pays> filteredPays;
	    private String npays;
	    
	    
	    public PaysController() {
	    }

	    
	    public void insert(){
	        FacesMessage msg;
	        countries=null;
	        try {
	        		manager.insertObject(country);
	               msg = new FacesMessage("Pays créer avec Success");
	               FacesContext.getCurrentInstance().addMessage(null, msg);
	               country=new Pays();
	        } catch (Exception e) {
	                       msg = new FacesMessage("Pays est mal Ajouter");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
	    }
	    
	    public void Delete(){
	        FacesMessage msg;
	        try {
	        	countries=null;
	        boolean op=managerjdbc.deletePays(countrytempo);
	        if(op==true)
	        	msg = new FacesMessage("Pays est Supprimer avec Success");
	        else
	        	msg = new FacesMessage("Pays est Déja liée");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        } catch (Exception e) {
	            msg = new FacesMessage("ERORR de Suppression");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
	        
	    }
	    
	     public void update() {
	         FacesMessage msg;
	         countries=null;
	         try {
	        	 	managerjdbc.updatePays(countrytempo);
	        	 	msg = new FacesMessage("Pays Modifier avec Success");
	                FacesContext.getCurrentInstance().addMessage(null, msg);
	         } catch (Exception e) {
	            msg = new FacesMessage("Exception");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	         }
	        
	    }

	     public List<Pays> getFilteredPays() {
			return filteredPays;
		}




		public void setFilteredPays(List<Pays> filteredPays) {
			this.filteredPays = filteredPays;
		}




		public ModelService<Pays> getManager() {
	        return manager;
	    }

	    public void setManager(ModelService<Pays> manager) {
	        this.manager = manager;
	    }

	    public Pays getCountry() {
	        return country;
	    }

	    public List<Pays> getCountries() {
	    	if(countries==null)
	    		countries=manager.getObject();
	        return countries;
	    }

	    public void setCountries(List<Pays> countries) {
	        this.countries = countries;
	    }

	    
	    public void setCountry(Pays country) {
	        this.country = country;
	    }


		public String getNpays() {
			return npays;
		}


		public void setNpays(String npays) {
			this.npays = npays;
		}


		public ModelServiceJDBC getManagerjdbc() {
			return managerjdbc;
		}


		public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
			this.managerjdbc = managerjdbc;
		}


		public Pays getCountrytempo() {
			return countrytempo;
		}


		public void setCountrytempo(Pays countrytempo) {
			this.countrytempo = countrytempo;
		} 
	    
	    
	}
