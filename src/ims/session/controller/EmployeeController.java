/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;

import ims.model.entities.Departement;
import ims.model.entities.Employee;
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


/**
 *
 * @author rabeh
 */
@ManagedBean
@SessionScoped
public class EmployeeController implements Serializable{

	   /**
		* creator RABEH TARIK
	    **/
	private static final long serialVersionUID = 1L;

	/**
     * Creates a new instance of EmployeeController
     */
    
	 @ManagedProperty(value = "#{managerDataBase}")
	 private ManagerDataBase managerApplication;
	 
    @ManagedProperty(value="#{EmployeeManager}")
    private ModelService<Employee> manager;
    
    @ManagedProperty(value="#{DepartementManager}")
    private ModelService<Departement> managerdept;
    
  	@ManagedProperty(value="#{JDBCManager}")
  	private ModelServiceJDBC managerjdbc;
    
    private Employee employee=new Employee() ;
    private Employee objectemployee=new Employee();
    private Employee employeesupp;
    
    private List<Employee> employees;
    private List<Employee> filteredEmploye;
    private int idept;
    private String ideptedit="";
    

    public EmployeeController() {
		super();
	}
    
    public void actualiser(){
    	employees=null;
    }
    
    public void annuler(){
    	  FacesMessage msg;
    	try {
            msg = new FacesMessage("Enregistrement Annuler avec Success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            employee=new Employee();
            idept=0;
		} catch (Exception e) {
            msg = new FacesMessage("Exception");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
        
    public String preparecreer(){
        	employee=new Employee();
        	return "insert?faces-redirect=true";
        }
    
    public String prepareView(){
    	try {
        	System.out.println("ID Emp="+employee.getId());
        	return "updateEmp?faces-redirect=true";
		} catch (Exception e) {
			return "index?faces-redirect=true";
		}

    }

    public void insert(){
        FacesMessage msg;
        managerApplication.setEmps(null);
        try {
        		employee.setDepartement(managerdept.getObject(idept));
        		boolean op=manager.insertObject(employee);
        		if(op==true){
                    msg = new FacesMessage("Employee est creer avec success");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
        		}else{
                    msg = new FacesMessage("Nom d'employee existe Déja");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
        		}
        		employees=manager.getObject();
        } catch (NullPointerException e) {
            msg = new FacesMessage("Exception d'insertion",e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        employee=new Employee();
        idept=0;
    }
    
    public void update(){
    	FacesMessage msg;
    	 managerApplication.setEmps(null);
        try {
        		if(!ideptedit.equals(""))
        			objectemployee.setDepartement(managerdept.getObject(Integer.parseInt(ideptedit)));
        		
        		managerjdbc.updateEmployee(objectemployee);
        		msg = new FacesMessage("Employee est Modifier avec success");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                employees=manager.getObject();
        } catch (NullPointerException e) {
            msg = new FacesMessage("Exception",e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
        
    public String Delete(){
        FacesMessage msg;
        managerApplication.setEmps(null);
        try {
        boolean op=managerjdbc.deleteEmployee(employeesupp.getId());
        if(op==true){
            msg = new FacesMessage("Employee est bien Supprimer");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }else{
            msg = new FacesMessage("Employee est liée à la caisse ou a l'utilisateur");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

       return null;
        } catch (Exception e) {
            msg = new FacesMessage("ERORR SUPPRESSION");
       FacesContext.getCurrentInstance().addMessage(null, msg);
       return null;
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
 
    public ModelService<Employee> getManager() {
        return manager;
    }

    public void setManager(ModelService<Employee> manager) {
        this.manager = manager;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Employee> getEmployees() {
    		employees=managerApplication.getEmps();
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> getFilteredEmploye() {
        return filteredEmploye;
    }

    public void setFilteredEmploye(List<Employee> filteredEmploye) {
        this.filteredEmploye = filteredEmploye;
    }

	public int getIdept() {
		return idept;
	}

	public void setIdept(int idept) {
		this.idept = idept;
	}

	public ModelService<Departement> getManagerdept() {
		return managerdept;
	}

	public void setManagerdept(ModelService<Departement> managerdept) {
		this.managerdept = managerdept;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public Employee getObjectemployee() {
		return objectemployee;
	}

	public void setObjectemployee(Employee objectemployee) {
		this.objectemployee = objectemployee;
	}

	public Employee getEmployeesupp() {
		return employeesupp;
	}

	public void setEmployeesupp(Employee employeesupp) {
		this.employeesupp = employeesupp;
	}

	public String getIdeptedit() {
		return ideptedit;
	}

	public void setIdeptedit(String ideptedit) {
		this.ideptedit = ideptedit;
	}

	public ManagerDataBase getManagerApplication() {
		return managerApplication;
	}

	public void setManagerApplication(ManagerDataBase managerApplication) {
		this.managerApplication = managerApplication;
	}

    
}
