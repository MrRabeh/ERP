package ims.session.controller;

import ims.model.entities.CoordonneeBancaire;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;


@ManagedBean
@RequestScoped
public class CoordonneeBanqueController implements Serializable {

	   /**
		* creator RABEH TARIK
	    **/
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{CoordonneeBanqueManager}")
	private ModelService<CoordonneeBancaire> manager;
	
	@ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
	
	private CoordonneeBancaire banque=new CoordonneeBancaire();
	private CoordonneeBancaire banquetempo=new CoordonneeBancaire();
	private List<CoordonneeBancaire> banques;
	private List<CoordonneeBancaire> filtredBanque=new ArrayList<CoordonneeBancaire>();;
	
	
	public CoordonneeBanqueController() {
		super();
	}

	public String prepareCreate(){
   		banque=new CoordonneeBancaire();
    	return "Banque?faces-redirect=true";
    }
	
	public void update(){
		 FacesMessage msg;
		 banques=null;
		try {
			managerjdbc.updateCoordonneeBanquaire(banquetempo);
            msg = new FacesMessage("Coordonnées de Banque Modifier avec success");
           	FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void insert(){
		
        FacesMessage msg;
        banques=null;
        try {
        		manager.insertObject(banque);
               msg = new FacesMessage("Coordonnées de Banque est creer avec Success");
               	FacesContext.getCurrentInstance().addMessage(null, msg);
              banque=new CoordonneeBancaire();
        } catch (Exception e) {
       msg = new FacesMessage("Coordonnées de Banque est mal creer");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        	}
	}
	
    public String Delete(){
        FacesMessage msg;
        try {
        	banques=null;
        manager.deleteObject(banquetempo);
       msg = new FacesMessage("Coordonnées est Supprimer avec Success");
       FacesContext.getCurrentInstance().addMessage(null, msg);
       return null;
        } catch (Exception e) {
            msg = new FacesMessage("Exception");
       FacesContext.getCurrentInstance().addMessage(null, msg);
       return null;
        }
        
    }
    
	public ModelService<CoordonneeBancaire> getManager() {
		return manager;
	}
	public void setManager(ModelService<CoordonneeBancaire> manager) {
		this.manager = manager;
	}
	public CoordonneeBancaire getBanque() {
		return banque;
	}
	public void setBanque(CoordonneeBancaire banque) {
		this.banque = banque;
	}
	public List<CoordonneeBancaire> getBanques() {
		if(banques==null)
			banques=manager.getObject();
		return banques;
	}
	public void setBanques(List<CoordonneeBancaire> banques) {
		this.banques = banques;
	}
	public List<CoordonneeBancaire> getFiltredBanque() {
		return filtredBanque;
	}
	public void setFiltredBanque(List<CoordonneeBancaire> filtredBanque) {
		this.filtredBanque = filtredBanque;
	}

	public CoordonneeBancaire getBanquetempo() {
		return banquetempo;
	}

	public void setBanquetempo(CoordonneeBancaire banquetempo) {
		this.banquetempo = banquetempo;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

}
