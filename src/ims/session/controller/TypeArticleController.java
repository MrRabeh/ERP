/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;

import ims.model.entities.TypeArticle;
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

import org.primefaces.event.RowEditEvent;

/**
 *
 * @author rabeh
 */
@ManagedBean
@SessionScoped
public class TypeArticleController implements Serializable {

	 /**
	* creator RABEH TARIK
    **/
	private static final long serialVersionUID = 1L;
	/**
     * Creates a new instance of TypeArticleController
     */
	
      @ManagedProperty(value="#{TypeArticleManager}")
      private ModelService<TypeArticle> manager;
      
      @ManagedProperty(value="#{JDBCManager}")
      private ModelServiceJDBC managerjdbc;
      
 	 @ManagedProperty(value = "#{managerDataBase}")
 	 private ManagerDataBase managerApplication;
 	 
 	 
 	public ManagerDataBase getManagerApplication() {
 		return managerApplication;
 	}

 	public void setManagerApplication(ManagerDataBase managerApplication) {
 		this.managerApplication = managerApplication;
 	}
      
      private TypeArticle typearticle;
      private List<TypeArticle> typearticles;
      private List<TypeArticle> filteredTypeArticle; 
    
      private String nouveautype="";
    
    public TypeArticleController() {
    }
    
	public void actualiser(){
		managerApplication.setTypesArticles(null);
	}
    
    public String preparecreer(){
    	typearticle=new TypeArticle();
    	return "inserttypearticle?faces-redirect=true";
    }
    
    public String prepareView(){
    	return "viewtypearticle?faces-redirect=true";
	}
    
    public void insert(){
        FacesMessage msg;
        managerApplication.setTypesArticles(null);
        typearticle=new TypeArticle();
        try {
        	typearticle.setType(nouveautype);
        	if(managerjdbc.getExisteTypeArticle(typearticle)<=0){
        		manager.insertObject(typearticle);
        		msg = new FacesMessage("Type Article Enregistrer avec Success");
        	}
        	else
        		msg = new FacesMessage("Type Article existe déja");
        	typearticle=new TypeArticle();
        	nouveautype="";
        	FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
        	msg = new FacesMessage("Exception" + e.getLocalizedMessage(),"Actualisée la page");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void annuler(){
    	  FacesMessage msg;
    	try {
    		nouveautype="";
		} catch (Exception e) {
    		msg = new FacesMessage("Exception "+e.getLocalizedMessage());
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
    
    public void update(){
        FacesMessage msg;
       managerApplication.setTypesArticles(null);
        try {
        		int op=managerjdbc.updateTypeArticle(typearticle);
        		if(op==1){
            		msg = new FacesMessage("Type Article Modifier avec Success");
        		}else{
            		msg = new FacesMessage("Le Type existe Déja");
        		}

        	       FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
        msg = new FacesMessage("Exception");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void Delete(){
        FacesMessage msg;
        managerApplication.setTypesArticles(null);
        try {
       boolean op=managerjdbc.deleteTypeArticle(typearticle);
       if(op==true)
    	   msg = new FacesMessage("Type Article est supprimer avec Success");
       else
    	   msg = new FacesMessage("Type Article n'est pas supprimer");
       
       FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            msg = new FacesMessage("ERORR de Suppression");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }

    public ModelService<TypeArticle> getManager() {
        return manager;
    }

    public void setManager(ModelService<TypeArticle> manager) {
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
     
    public TypeArticle getTypearticle() {
        return typearticle;
    }

    public void setTypearticle(TypeArticle typearticle) {
        this.typearticle = typearticle;
    }

    public List<TypeArticle> getTypearticles() {
            typearticles=managerApplication.getTypesArticles();
        return typearticles;
    }

    public void setTypearticles(List<TypeArticle> typearticles) {
        this.typearticles = typearticles;
    }

    public List<TypeArticle> getFilteredTypeArticle() {
        return filteredTypeArticle;
    }

    public void setFilteredTypeArticle(List<TypeArticle> filteredTypeArticle) {
        this.filteredTypeArticle = filteredTypeArticle;
    }

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public String getNouveautype() {
		return nouveautype;
	}

	public void setNouveautype(String nouveautype) {
		this.nouveautype = nouveautype;
	}


   
    
}
