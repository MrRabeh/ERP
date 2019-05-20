package ims.session.controller;

import java.util.List;

import ims.model.entities.TypePaiement;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@RequestScoped
public class TypePaiementController {
	
	 /**
	* creator RABEH TARIK
    **/
	
	
    @ManagedProperty(value="#{TypepaiementManager}")
    private ModelService<TypePaiement> manager;
    
    @ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
    
    private List<TypePaiement> listePaiement;
    private TypePaiement typepaiement=new TypePaiement();
    private TypePaiement typepaiementtempo;
    private TypePaiement typepaiementsupp;
    
    
    public void insert(){
    	FacesMessage msg;
    	listePaiement=null;
		try {
			manager.insertObject(typepaiement);
			typepaiement=new TypePaiement();
		    msg = new FacesMessage("devise est creer avec success");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			
		    msg = new FacesMessage("Exception");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
    
    public void actualiser(){
    	listePaiement=null;
    }
    
    public void annuler(){
    	FacesMessage msg;
		try {

			typepaiement=new TypePaiement();
		    msg = new FacesMessage("devise Annuler avec success");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
		    msg = new FacesMessage("Exception");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
    
    public void delete(){
    	FacesMessage msg;
    	listePaiement=null;
		try {
			boolean op=managerjdbc.deleteTypePaiement(typepaiementtempo);
			if(op==true){
		    msg = new FacesMessage("devise est Supprimer avec success");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
			    msg = new FacesMessage("devise n'est pas Supprimer");
			    FacesContext.getCurrentInstance().addMessage(null, msg);
			}
				
		} catch (Exception e) {
			
		    msg = new FacesMessage("Exception");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
    
    public void update(){
    	FacesMessage msg;
    	listePaiement=null;
		try {
			managerjdbc.updateTypePaiement(typepaiementtempo);
			typepaiement=new TypePaiement();
		    msg = new FacesMessage("devise est Modifier avec success");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			
		    msg = new FacesMessage("Exception");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
    
	public ModelService<TypePaiement> getManager() {
		return manager;
	}
	public void setManager(ModelService<TypePaiement> manager) {
		this.manager = manager;
	}
	public List<TypePaiement> getListePaiement() {
		if(listePaiement==null)
		listePaiement=manager.getObject();
		return listePaiement;
	}
	public void setListePaiement(List<TypePaiement> listePaiement) {
		this.listePaiement = listePaiement;
	}
	public TypePaiement getTypepaiement() {
		return typepaiement;
	}
	public void setTypepaiement(TypePaiement typepaiement) {
		this.typepaiement = typepaiement;
	}
	public TypePaiement getTypepaiementtempo() {
		return typepaiementtempo;
	}
	public void setTypepaiementtempo(TypePaiement typepaiementtempo) {
		this.typepaiementtempo = typepaiementtempo;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public TypePaiement getTypepaiementsupp() {
		return typepaiementsupp;
	}

	public void setTypepaiementsupp(TypePaiement typepaiementsupp) {
		this.typepaiementsupp = typepaiementsupp;
	}
    
    
    

}
