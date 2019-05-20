package ims.session.controller;

import ims.model.entities.Cnss;
import ims.model.entities.Employee;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@SessionScoped
public class CnssController implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{CnssManager}")
    private ModelService<Cnss> manager;
	
	@ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
	
	@ManagedProperty(value="#{EmployeeManager}")
	private ModelService<Employee> manageremp;
	
	private List<Cnss> listCnss;
	private Cnss cnss=new Cnss();
	
	private int idemp;
	
	private String tempocontsat="";
	
	public String preparecreate(){
		cnss=new Cnss();
		return "createcnss?faces-redirect=true";
	}
	
	public String prepareView(){
		return "viewcnss?faces-redirect=true";
	}
	
	public void onCellEdit(){
		FacesMessage msg;
		try {
			if(tempocontsat.equals("Oui"))
				cnss.setContstater(true);
			else
				cnss.setContstater(false);
		System.out.println("Modifier="+cnss.isContstater());
		managerjdbc.updatecnss(cnss);
		msg = new FacesMessage("CNSS est bien constater");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
	          msg = new FacesMessage("Exception");
	           FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void insert(){
		FacesMessage msgs;
		try {
			cnss.setEmp(manageremp.getObject(idemp));
		    SimpleDateFormat dateFormat;
		    dateFormat = new SimpleDateFormat("YYYY");
		    System.out.println("Date naissance=>"+dateFormat.format(cnss.getDatecnss()));
		    System.out.println("date CNSS=>"+Integer.parseInt(dateFormat.format(cnss.getDatecnss())));
			cnss.setYears(managerjdbc.getYears(Integer.parseInt(dateFormat.format(cnss.getDatecnss()))));
			manager.insertObject(cnss);
			System.out.println("CNSS==>"+cnss);
			cnss=new Cnss();
	          msgs = new FacesMessage("CNSS creer avec success");
	           FacesContext.getCurrentInstance().addMessage(null, msgs);
		} catch (Exception e) {
	          msgs = new FacesMessage("Exception:",e.getMessage());
	           FacesContext.getCurrentInstance().addMessage(null, msgs);
		}
	}
	public void update(){
		FacesMessage msgs;
		try {
			cnss.setEmp(manageremp.getObject(idemp));
		    SimpleDateFormat dateFormat;
		    dateFormat = new SimpleDateFormat("YYYY");
		    System.out.println("Date naissance=>"+dateFormat.format(cnss.getDatecnss()));
		    System.out.println("date CNSS=>"+Integer.parseInt(dateFormat.format(cnss.getDatecnss())));
			cnss.setYears(managerjdbc.getYears(Integer.parseInt(dateFormat.format(cnss.getDatecnss()))));
			managerjdbc.updatecnss(cnss);
			System.out.println("CNSS==>"+cnss);
			cnss=new Cnss();
	          msgs = new FacesMessage("CNSS est Modifier avec success");
	           FacesContext.getCurrentInstance().addMessage(null, msgs);
		} catch (Exception e) {
	          msgs = new FacesMessage("Exception:",e.getMessage());
	           FacesContext.getCurrentInstance().addMessage(null, msgs);
		}
	}
	public void delete(){
		FacesMessage msg;
		try {
			managerjdbc.deleteCNSS(cnss);
			msg = new FacesMessage("regelement de cnss est Supprimer avec success");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		catch (Exception e) {
		       msg = new FacesMessage("Exception");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}



	public ModelService<Cnss> getManager() {
		return manager;
	}



	public void setManager(ModelService<Cnss> manager) {
		this.manager = manager;
	}



	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}



	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}



	public List<Cnss> getListCnss() {
			listCnss=manager.getObject();
		return listCnss;
	}



	public void setListCnss(List<Cnss> listCnss) {
		this.listCnss = listCnss;
	}



	public Cnss getCnss() {
		return cnss;
	}



	public void setCnss(Cnss cnss) {
		this.cnss = cnss;
	}

	public int getIdemp() {
		return idemp;
	}

	public void setIdemp(int idemp) {
		this.idemp = idemp;
	}

	public ModelService<Employee> getManageremp() {
		return manageremp;
	}

	public void setManageremp(ModelService<Employee> manageremp) {
		this.manageremp = manageremp;
	}

	public String getTempocontsat() {
		return tempocontsat;
	}

	public void setTempocontsat(String tempocontsat) {
		this.tempocontsat = tempocontsat;
	}
	
	
	

}
