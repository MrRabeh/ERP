package ims.session.controller;

import java.util.List;

import ims.model.entities.TypeFacture;
import ims.service.ModelService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class TypeFactureController {
	
	
	 /**
	* creator RABEH TARIK
    **/
	
    @ManagedProperty(value="#{TypefactManager}")
    private ModelService<TypeFacture> manager;
    
    private TypeFacture type;
    private List<TypeFacture> types;


	public TypeFacture getType() {
		return type;
	}

	public void setType(TypeFacture type) {
		this.type = type;
	}

	public ModelService<TypeFacture> getManager() {
		return manager;
	}

	public void setManager(ModelService<TypeFacture> manager) {
		this.manager = manager;
	}

	public List<TypeFacture> getTypes() {
		types=manager.getObject();
		return types;
	}

	public void setTypes(List<TypeFacture> types) {
		this.types = types;
	}
    

}
