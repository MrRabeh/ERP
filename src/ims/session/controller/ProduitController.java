package ims.session.controller;

import ims.model.entities.Produit;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;



@ManagedBean
@SessionScoped
public class ProduitController implements Serializable{

	 /**
	* creator RABEH TARIK
    **/
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{ProduitManager}")
	private ModelService<Produit> manager;
	
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

	private Produit produit;
	private List<Produit> produits;
	private List<Produit> filteredproduits;
	private String typeproduit;
	
	
	public void actualiser(){
		managerApplication.setProduits(null);
	}
	
	  public String preparecreer(){
		  produit=new Produit();
	    	return "insertproduit?faces-redirect=true";
	    }
	  
	  public String prepareView(){
		  return "viewproduit?faces-redirect=true";
		}
	 
	  
	  public void delete(){
		  FacesMessage msg;
		  managerApplication.setProduits(null);
		  try {
			  managerjdbc.deleteProduit(produit);
			msg = new FacesMessage("Produit Supprimer Avec Success");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			msg = new FacesMessage("Exception "+e.getLocalizedMessage(),"Actualisée la page");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	  }
	  
	public void insert(){
		FacesMessage msg;
		managerApplication.setProduits(null);
		produit=new Produit();
		try {
    		produit.setTypeproduit(typeproduit);
    		System.out.println(produit.getTypeproduit());
    		if(managerjdbc.getExisteProduit(produit)<=0){
	    		manager.insertObject(produit);
	    		msg = new FacesMessage("Produit creer avec success");
    		}
    		else
    			msg = new FacesMessage("Produit existe déja");
    		
    		FacesContext.getCurrentInstance().addMessage(null, msg);
    		produit=new Produit();
    		typeproduit="";
		}catch (Exception e) {
			msg = new FacesMessage("Exception" + e.getLocalizedMessage(),"Actualisée la page");
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void update(){
		FacesMessage msg;
		managerApplication.setProduits(null);
		try {
    		
    		System.out.println(produit.getTypeproduit());
	    	managerjdbc.updateProduit(produit);
	    	msg = new FacesMessage("Produit modifier avec success");
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		}catch (Exception e) {
    		msg = new FacesMessage("Exception");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void onCellEdit(){
		FacesMessage msg;
		managerApplication.setProduits(null);
		try {
			managerjdbc.updateProduit(produit);
	    	msg = new FacesMessage("Produit modifier avec success");
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
	    	msg = new FacesMessage("Exception");
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public ModelService<Produit> getManager() {
		return manager;
	}

	public void setManager(ModelService<Produit> manager) {
		this.manager = manager;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public List<Produit> getProduits() {
			produits=managerApplication.getProduits();
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}

	public List<Produit> getFilteredproduits() {
		return filteredproduits;
	}

	public void setFilteredproduits(List<Produit> filteredproduits) {
		this.filteredproduits = filteredproduits;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public String getTypeproduit() {
		return typeproduit;
	}

	public void setTypeproduit(String typeproduit) {
		this.typeproduit = typeproduit;
	}
	
	
}
