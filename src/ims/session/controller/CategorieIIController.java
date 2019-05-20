/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;

import ims.model.entities.CategorieIISociete;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
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
public class CategorieIIController implements Serializable{

	   /**
		* creator RABEH TARIK
	    **/
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{CategorieIIManager}")
    private ModelService<CategorieIISociete> manager;
	
	@ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
     
    private CategorieIISociete categorieII;
    private CategorieIISociete categorietempo=new CategorieIISociete();
    private List<CategorieIISociete> categorieIIs;
    private List<CategorieIISociete> filteredCategorieII;
    
    private String novcategorie="";
    
    public CategorieIIController() {
       
    }
    
    public void actualiser(){
    	categorieIIs=null;
    }
    public String preparecreer(){
    	categorieII=new CategorieIISociete();
    	return "insertCategorieII?faces-redirect=true";
    }
    public void annuler(){
    	FacesMessage msg;
    	try {
    			novcategorie="";
    			categorieII=new CategorieIISociete();
    	       msg = new FacesMessage("Categorie creer avec success");
    	       FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
		       msg = new FacesMessage("Categorie creer avec success");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
    public void insert(){
             FacesMessage msg;
             categorieII=new CategorieIISociete();
             categorieIIs=null;
        try {
        	categorieII.setCategorie(novcategorie);
            manager.insertObject(categorieII);
       msg = new FacesMessage("Categorie creer avec success");
       FacesContext.getCurrentInstance().addMessage(null, msg);
       novcategorie="";
       categorieII=new CategorieIISociete();
        } catch (Exception e) {
            msg = new FacesMessage("Exception");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void Delete(){
        FacesMessage msg;
        try {
        		categorieIIs=null;
        		System.out.println(categorietempo.getId());
        		boolean op=managerjdbc.deleteCategorieclient(categorietempo);
        		if(op==true)
        			msg = new FacesMessage("Categorie Supprimer avec success");
        		else
        			msg = new FacesMessage("Categorie n'est pas bien Supprimer Déja liée avec un client");
        		FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            msg = new FacesMessage("Exception");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    public void onCellEdit(){
    	 FacesMessage msg;
    	 try {
    		   categorieIIs=null;
    		   managerjdbc.updateCategorieClient(categorietempo);
    	       msg = new FacesMessage("Categorie Modifier Avec Success");
    	       FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
 	       		msg = new FacesMessage("Exception");
 	       		FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }

    public ModelService<CategorieIISociete> getManager() {
        return manager;
    }

    public void setManager(ModelService<CategorieIISociete> manager) {
        this.manager = manager;
    }
  
    public CategorieIISociete getCategorieII() {
        return categorieII;
    }

    public void setCategorieII(CategorieIISociete categorieII) {
        this.categorieII = categorieII;
    }

    public List<CategorieIISociete> getCategorieIIs() {
    	if(categorieIIs==null)
    		categorieIIs=manager.getObject();
        return categorieIIs;
    }

    public void setCategorieIIs(List<CategorieIISociete> categorieIIs) {
        this.categorieIIs = categorieIIs;
    }

    public List<CategorieIISociete> getFilteredCategorieII() {
        return filteredCategorieII;
    }

    public void setFilteredCategorieII(List<CategorieIISociete> filteredCategorieII) {
        this.filteredCategorieII = filteredCategorieII;
    }
    
	public String getNovcategorie() {
		return novcategorie;
	}
	public void setNovcategorie(String novcategorie) {
		this.novcategorie = novcategorie;
	}
	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}
	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}
	public CategorieIISociete getCategorietempo() {
		return categorietempo;
	}
	public void setCategorietempo(CategorieIISociete categorietempo) {
		this.categorietempo = categorietempo;
	}
    
    

}
