package ims.session.controller;


import java.io.Serializable;
import java.util.List;

import ims.model.entities.TypeCharge;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@SessionScoped
public class TypeChargeController implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{TypeChargeManager}")
    private ModelService<TypeCharge> manager;
	
	@ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
	
	private TypeCharge typecharge=new TypeCharge();
	private TypeCharge typechargetempo=new TypeCharge();
	private List<TypeCharge> typescharges;
	
	
	public String preparecreate(){
		typecharge=new TypeCharge();
		return "createTypeCharge?faces-redirect=true";
	}
	
	public void actualiser(){
		typescharges=null;
	}
	
	public String prepareView(){
		return "viewTypeCharge?faces-redirect=true";
	}
	
	public void insert(){
		FacesMessage msg;
		typescharges=null;
		try {
			manager.insertObject(typecharge);
            msg = new FacesMessage("Type charge est creer avec sucess");   
            FacesContext.getCurrentInstance().addMessage(null, msg);
            typecharge=new TypeCharge();
		} catch (Exception e) {
            msg = new FacesMessage("Exception");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void annuler(){
		FacesMessage msg;
		typescharges=null;
		try {
			typecharge=new TypeCharge();
            msg = new FacesMessage("Type charge Annuler avec sucess");   
            FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
            msg = new FacesMessage("Exception");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void update(){
		FacesMessage msg;
		typescharges=null;
		try {
			managerjdbc.updateTypeCharge(typechargetempo);
            msg = new FacesMessage("Type charge est creer avec sucess");
       FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
            msg = new FacesMessage("Exception");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public ModelService<TypeCharge> getManager() {
		return manager;
	}

	public void setManager(ModelService<TypeCharge> manager) {
		this.manager = manager;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public TypeCharge getTypecharge() {
		return typecharge;
	}

	public void setTypecharge(TypeCharge typecharge) {
		this.typecharge = typecharge;
	}

	public List<TypeCharge> getTypescharges() {
		if(typescharges==null)
			typescharges=manager.getObject();
		return typescharges;
	}

	public void setTypescharges(List<TypeCharge> typescharges) {
		this.typescharges = typescharges;
	}

	public TypeCharge getTypechargetempo() {
		return typechargetempo;
	}

	public void setTypechargetempo(TypeCharge typechargetempo) {
		this.typechargetempo = typechargetempo;
	}
	
	
	
	
	
	
	
	

}
