/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;

import ims.model.entities.CategorieArticle;
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
public class CategorieArticleController implements Serializable {

	   /**
		* creator RABEH TARIK
	    **/
	private static final long serialVersionUID = 1L;
	/**
     * Creates a new instance of CategorieArticleController
     */
	@ManagedProperty(value="#{CategorieArticleManager}")
    private ModelService<CategorieArticle> manager;
	
	@ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
	
    public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	private CategorieArticle categorie=new CategorieArticle();
    private List<CategorieArticle> categories;
    private List<CategorieArticle> filteredCategorie;
    
    private String nouveaucat;
    
    
    public CategorieArticleController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String preparecreer(){
		categorie=new CategorieArticle();
    	return "insertcategoriearticle?faces-redirect=true";
    }
	
	public String prepareView(){
		return "viewcategoriearticle?faces-redirect=true";
	}
    
    public void insert(){
        FacesMessage msg;
        try {
        	if(managerjdbc.getExisteCategorieArticle(categorie)<=0){
        		manager.insertObject(categorie);
               msg = new FacesMessage("Categorie est Creer avec success");
        	}
        	else
        		msg = new FacesMessage("Categorie existe déja");
        	categorie=new CategorieArticle();
        	FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
              msg = new FacesMessage("Exception");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void update(){
        FacesMessage msg;
        try {
        	managerjdbc.updateCategorieArticle(categorie);
            msg = new FacesMessage("Categorie est Modifier avec success");
            
        	FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
              msg = new FacesMessage("Exception");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void Delete(){
        FacesMessage msg;
        try {
        manager.deleteObject(categorie);
       msg = new FacesMessage("Categorie est bien Supprimer");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            msg = new FacesMessage("ERORR de Suppression");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }

    public ModelService<CategorieArticle> getManager() {
        return manager;
    }

    public void setManager(ModelService<CategorieArticle>  manager) {
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
     
     public void onRowEdit(RowEditEvent event) {
         FacesMessage msg;
         try {
        	 
        	 System.out.println("new TYPE="+nouveaucat);
        	 ((CategorieArticle) event.getObject()).setCategorie(nouveaucat);
        	 int idobject=managerjdbc.updateCategorieArticle(((CategorieArticle) event.getObject()));
        	 	
        	 System.out.println("IIIIIIIIIDDDD=>"+idobject);
             
        	 msg = new FacesMessage("Categorie est bien Modifier",""+((CategorieArticle) event.getObject()).getIdcategorie());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                nouveaucat="";
         } catch (Exception e) {
            msg = new FacesMessage("Categorie est mal Modifier",""+((CategorieArticle) event.getObject()).getIdcategorie());
        FacesContext.getCurrentInstance().addMessage(null, msg);
         }
        
    }
     
    public void onRowCancel(RowEditEvent event) {

       FacesMessage msg = new FacesMessage("Modification Annuler",""+((CategorieArticle) event.getObject()).getIdcategorie());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public CategorieArticle getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieArticle categorie) {
        this.categorie = categorie;
    }

    public List<CategorieArticle> getCategories() {
            categories=manager.getObject();
        return categories;
    }

    public void setCategories(List<CategorieArticle> categories) {
        this.categories = categories;
    }

    public List<CategorieArticle> getFilteredCategorie() {
        return filteredCategorie;
    }

    public void setFilteredCategorie(List<CategorieArticle> filteredCategorie) {
        this.filteredCategorie = filteredCategorie;
    }

	public String getNouveaucat() {
		return nouveaucat;
	}

	public void setNouveaucat(String nouveaucat) {
		this.nouveaucat = nouveaucat;
	}
    
}
