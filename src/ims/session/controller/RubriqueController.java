package ims.session.controller;

import ims.model.entities.Offre_Article;
import ims.model.entities.Rubrique;
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
public class RubriqueController implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{RubriqueManager}")
    private ModelService<Rubrique> manager;
	
	@ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
	
    @ManagedProperty(value="#{OffreArticleManager}")
    private ModelService<Offre_Article> managerarticle;
    
    private List<Rubrique> rubriques;
    private Rubrique rubrique=new Rubrique();
    private Rubrique rtempo;
    private String ref="";
    private String des="";
    

	public RubriqueController() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	
	public void actualiser(){
		rubriques=null;
	}
	
    public void insert(){
    	FacesMessage msg;
    	rubriques=null;
    	try {
    		rubrique.setRef(ref);
    		rubrique.setDescription(des);
    		boolean op=manager.insertObject(rubrique);
    		managerjdbc.updateclassementRubrique(rubrique.getId());
    		rubrique=new Rubrique();
			des="";
			ref="";
			if(op==true)
	          msg = new FacesMessage("Rubrique est creer avec success");
			else
				 msg = new FacesMessage("Rubrique existe déja");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			ref="";
			des="";
	        msg = new FacesMessage("Exception "+e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
    
    public void oncellrubrique(){
    	FacesMessage msg;
    	try {
    			System.out.println("test "+rubrique.getDescription());
    			managerjdbc.updateRubrique(rubrique);
	          	msg = new FacesMessage("Rubrique Modifier avec success");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
	        msg = new FacesMessage("Exception "+e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }

	public List<Rubrique> getRubriques() {
		if(rubriques==null)
			rubriques=manager.getObject();
		return rubriques;
	}

	public void setRubriques(List<Rubrique> rubriques) {
		this.rubriques = rubriques;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public ModelService<Rubrique> getManager() {
		return manager;
	}

	public void setManager(ModelService<Rubrique> manager) {
		this.manager = manager;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public ModelService<Offre_Article> getManagerarticle() {
		return managerarticle;
	}

	public void setManagerarticle(ModelService<Offre_Article> managerarticle) {
		this.managerarticle = managerarticle;
	}


	public Rubrique getRubrique() {
		return rubrique;
	}


	public void setRubrique(Rubrique rubrique) {
		this.rubrique = rubrique;
	}


	public Rubrique getRtempo() {
		return rtempo;
	}


	public void setRtempo(Rubrique rtempo) {
		this.rtempo = rtempo;
	}
	
}
