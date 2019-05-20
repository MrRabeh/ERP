package ims.session.controller;


import ims.model.entities.Ville;
import ims.model.entities.Pays;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class VilleController implements Serializable {

	 /**
	* creator RABEH TARIK
    **/
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{VilleManager}")
	private ModelService<Ville> manager;
	
	@ManagedProperty(value="#{PaysManager}")
	private ModelService<Pays> managerpays;
	
	@ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
    
   private Ville ville=new Ville();
   private Ville villeinsert=new Ville();
   private List<Ville> villes;
   private List<Ville> filteredVille=new ArrayList<Ville>();;
   private Pays country =new Pays();
   private List<Pays> countries=new ArrayList<Pays>();; 
   private String idpays="";
   
    
   
   
   public VilleController() {
   	
      
   }
   
   public void insert(){
       FacesMessage msg;
       villes=null;
       try {
    	   		villeinsert.setPays(managerpays.getObject(Integer.parseInt(idpays)));
    	   		manager.insertObject(villeinsert);
    	   		msg = new FacesMessage("Ville Créer avec Success");
              	FacesContext.getCurrentInstance().addMessage(null, msg);
              	idpays="";
              	villeinsert=new Ville();
       } catch (Exception e) {
                      msg = new FacesMessage("Ville est mal Ajouter");
                      FacesContext.getCurrentInstance().addMessage(null, msg);
       }
   }
   public void update(){
	     FacesMessage msg;
	     villes=null;
	       try {
	    	   if(!idpays.equals("")){
	    		   ville.setPays(managerpays.getObject(Integer.parseInt(idpays)));
	    	   }
	       managerjdbc.updateVille(ville);
	              msg = new FacesMessage("Ville Modifier avec Success");
	      FacesContext.getCurrentInstance().addMessage(null, msg);
	      idpays="";
	       } catch (Exception e) {
	                      msg = new FacesMessage("Exception");
	                      FacesContext.getCurrentInstance().addMessage(null, msg);
	       }
   }
   
   public void Delete(){
       FacesMessage msg;
       villes=null;
       try {
       boolean op=managerjdbc.deleteVille(ville);
       if(op==true)
    	   msg = new FacesMessage("ville est bien Supprimer");
       else
    	   msg = new FacesMessage("ville est Déja liée");
       
      FacesContext.getCurrentInstance().addMessage(null, msg);
       } catch (Exception e) {
           msg = new FacesMessage("ERORR de Suppression");
      FacesContext.getCurrentInstance().addMessage(null, msg);
       }
       
   }
   
   public String prepareView(){
   	return "ViewVille?faces-redirect=true";
   } 
   
public Pays getCountry() {
	return country;
}

public void setCountry(Pays country) {
	this.country = country;
}

public List<Pays> getCountries() {
	return countries;
}

public void setCountries(List<Pays> countries) {
	this.countries = countries;
}

public ModelService<Ville> getManager() {
       return manager;
   }

   public void setManager(ModelService<Ville> manager) {
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


   public Ville getVille() {
       return ville;
   }

   public void setVille(Ville ville) {
       this.ville = ville;
   }
   
 
   public List<Ville> getVilles() {
	   if(villes==null)
		   villes=manager.getObject();
       return villes;
   }

   public void setVilles(List<Ville> villes) {
       this.villes = villes;
   }

   public List<Ville> getFilteredVille() {
       return filteredVille;
   }

   public void setFilteredVille(List<Ville> filteredVille) {
       this.filteredVille = filteredVille;
   }

public ModelService<Pays> getManagerpays() {
	return managerpays;
}

public void setManagerpays(ModelService<Pays> managerpays) {
	this.managerpays = managerpays;
}

public String getIdpays() {
	return idpays;
}

public void setIdpays(String idpays) {
	this.idpays = idpays;
}

public ModelServiceJDBC getManagerjdbc() {
	return managerjdbc;
}

public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
	this.managerjdbc = managerjdbc;
}

public Ville getVilleinsert() {
	return villeinsert;
}

public void setVilleinsert(Ville villeinsert) {
	this.villeinsert = villeinsert;
}


   
}
