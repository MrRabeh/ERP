/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;

import ims.model.entities.Departement;
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
public class DepartementController implements Serializable{

	    /**
		 * creator RABEH TARIK
		 */
		private static final long serialVersionUID = 1L;
		/**
	     * Creates a new instance of DepartementController
	     */
     
		 @ManagedProperty(value = "#{managerDataBase}")
		 private ManagerDataBase managerApplication;
        
   	 	@ManagedProperty(value="#{JDBCManager}")
   	 	private ModelServiceJDBC managerjdbc;
   	 	
   	   @ManagedProperty(value="#{DepartementManager}")
       private ModelService<Departement> manager;
   	 
   	 
   	 	private Departement dept=new Departement();
   	 	private Departement deptsupp;
   	 	private String nom="";
   	 	private List<Departement> depts;
   	 	private List<Departement> filtereddepts;
    
   	  public void actualiser(){
      	depts=null;
      }
    
        public DepartementController() {
		super();
        }

        public String preparecreer(){
        	dept=new Departement();
        	return "insertdepartement?faces-redirect=true";
        }
        
        public String prepareView(){
        	System.out.println("ID DEPT="+dept.getId());
        	return "updatedepartement?faces-redirect=true";
        }
        
        public void annuler(){
      	  FacesMessage msg;
      	try {
              msg = new FacesMessage("Enregistrement Annuler avec Success");
              FacesContext.getCurrentInstance().addMessage(null, msg);
		        dept=new Departement();
		        nom="";
  		} catch (Exception e) {
              msg = new FacesMessage("Exception");
              FacesContext.getCurrentInstance().addMessage(null, msg);
  		}
      }
        
		public void insert(){
		        FacesMessage msg;
		        
		        try {
		        	dept=new Departement();
		        	dept.setNom(nom);
		       boolean op= manager.insertObject(dept);
		       if(op==true){
		    	   managerApplication.setDepts(null);
		    	   
		               msg = new FacesMessage("Departement est creer avec success");
		                FacesContext.getCurrentInstance().addMessage(null, msg);
		                depts=managerApplication.getDepts();
		       }else{
		                msg = new FacesMessage("Departement deja existe !!");
		                FacesContext.getCurrentInstance().addMessage(null, msg);
		       }
		        } catch (NullPointerException e) {
		       
		            msg = new FacesMessage("ERROR Departement IS NULL");
		            FacesContext.getCurrentInstance().addMessage(null, msg);
		        }
		        dept=new Departement();
		        nom="";
    }
		
		public void update(){
			 FacesMessage msg;
		        try {
		        	
		        	int op= managerjdbc.updateDepartement(dept);
		        	 if(op>0){
		        		 managerApplication.setDepts(null);
		        		 managerApplication.setEmps(null);
			               msg = new FacesMessage("Departement est Modifier avec success");
			                FacesContext.getCurrentInstance().addMessage(null, msg);
			                depts=managerApplication.getDepts();
			       }else{
			                msg = new FacesMessage("Departement Existe DÈja");
			                FacesContext.getCurrentInstance().addMessage(null, msg);
			       }
				} catch (Exception e) {
				    msg = new FacesMessage("Exception:",e.getLocalizedMessage());
		            FacesContext.getCurrentInstance().addMessage(null, msg);
				}
		}
                
        public void Delete(){
		        FacesMessage msg;
		        try {
		        	managerApplication.setDepts(null);
		        	managerApplication.setEmps(null);
		        boolean op=managerjdbc.deletDepartement(deptsupp.getId());
		        if(op==true)
		        	msg = new FacesMessage("Departement est bien Supprimer");
		        else
		             msg = new FacesMessage("Departement n'est pas supprimer");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		        } catch (Exception e) {
		            msg = new FacesMessage("ERORR SUPPRESSION");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		        }
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
            manager.updateObject(((Departement) event.getObject()));
             msg = new FacesMessage("Departement est bien Modifi√©",""+((Departement) event.getObject()).getId());
                FacesContext.getCurrentInstance().addMessage(null, msg);
         } catch (Exception e) {
            msg = new FacesMessage("Departement est mal Modifier",""+((Departement) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
         }
        
    }
     
        public void onRowCancel(RowEditEvent event) {
	       FacesMessage msg = new FacesMessage("Modification Annuler",""+((Departement) event.getObject()).getId());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
        }


    public Departement getDept() {
        return dept;
    }

    public List<Departement> getDepts() {
    		depts=managerApplication.getDepts();
        return depts;
    }

    public void setDepts(List<Departement> depts) {
        this.depts = depts;
    }

    public List<Departement> getFiltereddepts() {
        return filtereddepts;
    }

    public void setFiltereddepts(List<Departement> filtereddepts) {
        this.filtereddepts = filtereddepts;
    }

    public void setDept(Departement dept) {
        this.dept = dept;
    }

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Departement getDeptsupp() {
		return deptsupp;
	}

	public void setDeptsupp(Departement deptsupp) {
		this.deptsupp = deptsupp;
	}

	public ManagerDataBase getManagerApplication() {
		return managerApplication;
	}

	public void setManagerApplication(ManagerDataBase managerApplication) {
		this.managerApplication = managerApplication;
	}

	public ModelService<Departement> getManager() {
		return manager;
	}

	public void setManager(ModelService<Departement> manager) {
		this.manager = manager;
	}

    
}
