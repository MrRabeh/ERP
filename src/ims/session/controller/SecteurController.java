package ims.session.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ims.model.entities.CategorieIISociete;
import ims.model.entities.Secteur;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

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
public class SecteurController implements Serializable {

	 /**
	* creator RABEH TARIK
    **/
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{SecteurManager}")
    private ModelService<Secteur> manager;
	
	@ManagedProperty(value="#{CategorieIIManager}")
    private ModelService<CategorieIISociete> managercat;
	
	@ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
     
    private Secteur secteur=new Secteur();
    private Secteur secteurtempo=new Secteur();
    
    private List<Secteur> secteurs;
    private List<Secteur> filteredSecteur;
    private CategorieIISociete Cat =new CategorieIISociete();
    private List<CategorieIISociete> cats; 
    
    private String novsecteur="";
    private String idcat="0";
    private String idcatmodif="";
    
    public SecteurController(){}
    
    public String preparecreer(){
    	secteur=new Secteur();
    	return "insertsecteur?faces-redirect=true";
    }
    
    public void actualiser(){
    	secteurs=null;
    }
    
    public void annuler(){
		FacesMessage msg;
			secteurs=null;
			try {
	    	novsecteur="";
	    	idcat="0";
	    	secteur=new Secteur();
	    	msg = new FacesMessage("Secteur Annuler avec success");
	    	FacesContext.getCurrentInstance().addMessage(null, msg);
		    } catch (Exception e) {
		        msg = new FacesMessage("Exception");
		        FacesContext.getCurrentInstance().addMessage(null, msg);    
		    }
     }
    
    public void insert(){
    		FacesMessage msg;
    		secteurs=null;
        try {
        	secteur.setCategorie(managercat.getObject(Integer.parseInt(idcat)));
        	secteur.setSecteur(novsecteur);
        	manager.insertObject(secteur);
        	msg = new FacesMessage("Secteur est bien Ajouter");
        	FacesContext.getCurrentInstance().addMessage(null, msg);
        	novsecteur="";
        	idcat="0";
        	secteur=new Secteur();
        } catch (Exception e) {
            msg = new FacesMessage("Exception");
            FacesContext.getCurrentInstance().addMessage(null, msg);    
        }
    }
    
    public void onCellEdit(){
    	
        	FacesMessage msg;
        	secteurs=null;
        try {
        	if(!idcatmodif.equals(""))
        		secteurtempo.setCategorie(managercat.getObject(Integer.parseInt(idcatmodif)));
        
        	managerjdbc.updateSecteur(secteurtempo);
        	idcatmodif="";
        	msg = new FacesMessage("Secteur Modifier Avec Success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
            msg = new FacesMessage("Exception");
            FacesContext.getCurrentInstance().addMessage(null, msg);   
		}
    }

    public void Delete(){
        FacesMessage msg;
        secteurs=null;
        try {
        	System.out.println("ID SECTEUR "+secteurtempo.getId());
        	managerjdbc.delete(secteurtempo);
        	
        	msg = new FacesMessage("Secteur est bien Supprimer");
        	FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            msg = new FacesMessage("Exception");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }

    public ModelService<Secteur> getManager() {
        return manager;
    }

    public void setManager(ModelService<Secteur> manager) {
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
     
    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }
    
    public List<Secteur> getSecteurs() {
    	if(secteurs==null)
    		secteurs=manager.getObject();
        return secteurs;
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public List<Secteur> getFilteredSecteur() {
        return filteredSecteur;
    }

    public void setFilteredSecteur(List<Secteur> filteredSecteur) {
        this.filteredSecteur = filteredSecteur;
    }

	public CategorieIISociete getCat() {
		return Cat;
	}

	public void setCat(CategorieIISociete cat) {
		Cat = cat;
	}

	public List<CategorieIISociete> getCats() {
		return cats;
	}

	public void setCats(List<CategorieIISociete> cats) {
		this.cats = cats;
	}

	public String getNovsecteur() {
		return novsecteur;
	}

	public void setNovsecteur(String novsecteur) {
		this.novsecteur = novsecteur;
	}

	public ModelService<CategorieIISociete> getManagercat() {
		return managercat;
	}

	public void setManagercat(ModelService<CategorieIISociete> managercat) {
		this.managercat = managercat;
	}

	public String getIdcat() {
		return idcat;
	}

	public void setIdcat(String idcat) {
		this.idcat = idcat;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public String getIdcatmodif() {
		return idcatmodif;
	}

	public void setIdcatmodif(String idcatmodif) {
		this.idcatmodif = idcatmodif;
	}

	public Secteur getSecteurtempo() {
		return secteurtempo;
	}

	public void setSecteurtempo(Secteur secteurtempo) {
		this.secteurtempo = secteurtempo;
	}



    
}
