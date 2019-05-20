package ims.session.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ims.model.entities.Charge;
import ims.model.entities.TypeCharge;
import ims.model.entities.years;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@SessionScoped
public class ChargeController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{ChargeManager}")
    private ModelService<Charge> manager;
	
	@ManagedProperty(value="#{TypeChargeManager}")
    private ModelService<TypeCharge> managertypecharge;
	
	@ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
	@ManagedProperty(value="#{yearsManager}")
	private ModelService<years> manageryears;
	
	private Charge charge=new Charge();
	private Charge chargetempo;
	private Date datecharge;
	private List<Charge> listcharge;
	
	private int idtypecharge;
	private String idtypechargeedit="";
	private String tempocontsat="";
	
	private String[] tabmois=new String[]{"janvier","fevrier","mars","avril","mai","juin","juillet","aout","septembre","octobre","novembre","decembre"};
	private int idannee;
	private List<years> listeyears;
	private String constat;
	private String choixmois;
	
	public String preparecreate(){
		return "createcharge?faces-redirect=true";
	}
	
	public void actualiser(){
		listcharge=null;
	}
	
	public String prepareView(){
		idtypecharge=charge.getTypecharge().getId();
		return "viewcharge?faces-redirect=true";
	}
	
	
	public void insert(){
		FacesMessage msg;
		listcharge=null;
		try {
			System.out.println(managertypecharge.getObject(idtypecharge).getTypecharge());
			charge.setTypecharge(managertypecharge.getObject(idtypecharge));
		    SimpleDateFormat dateFormat;
		    dateFormat = new SimpleDateFormat("YYYY");
			charge.setDatecharge(datecharge);
		    charge.setYears(managerjdbc.getYears(Integer.parseInt(dateFormat.format(charge.getDatecharge()))));
			manager.insertObject(charge);
            msg = new FacesMessage("la charge est creer avec success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            charge=new Charge();
            idtypecharge=0;
		} catch (Exception e) {
            msg = new FacesMessage("Exception "+e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	public void update(){
		FacesMessage msg;
		listcharge=null;
		try {
			System.out.println(managertypecharge.getObject(idtypecharge).getTypecharge());
			charge.setTypecharge(managertypecharge.getObject(idtypecharge));
		    SimpleDateFormat dateFormat;
		    dateFormat = new SimpleDateFormat("YYYY");
			charge.setYears(managerjdbc.getYears(Integer.parseInt(dateFormat.format(charge.getDatecharge()))));
		    managerjdbc.updateCharge(charge);   
			msg = new FacesMessage("la charge est Modifier avec success");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
		       msg = new FacesMessage("Exception");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	
	
	public void annulerrapport(){
		FacesMessage msg;
		try {

            msg = new FacesMessage("la charge Annuler avec success");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			msg = new FacesMessage("Exception :"+e.getMessage());
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void imprimer(){
		FacesMessage msg;
		try {

            msg = new FacesMessage("Désolé En cours de Developpement ");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			msg = new FacesMessage("Exception : "+e.getMessage());
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	
	public void annuler(){
		FacesMessage msg;
		try {
            charge=new Charge();
            idtypecharge=0;
            msg = new FacesMessage("la charge Annuler avec success");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			msg = new FacesMessage("Exception");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	
	
	public void delete(){
		FacesMessage msg;
		try {
			listcharge=null;
			managerjdbc.deleteCharge(chargetempo);
			msg = new FacesMessage("regelement de charge est Supprimer avec success");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		catch (Exception e) {
		       msg = new FacesMessage("Exception");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void onCellEdit(){
		FacesMessage msg;
		listcharge=null;
		try {
			if(!tempocontsat.equals("")){
				if(tempocontsat.equals("Oui")){
					chargetempo.setConstater(true);
					chargetempo.setDatecontsater(new Date());
				}
				else
					chargetempo.setConstater(false);
				System.out.println("Modifier="+chargetempo.isConstater());
			}
			if(!idtypechargeedit.equals("")){
				chargetempo.setTypecharge(managertypecharge.getObject(Integer.parseInt(idtypechargeedit)));
			}
		managerjdbc.updateCharge(chargetempo);
		msg = new FacesMessage("Charge est Modifier Success");
		tempocontsat="";
		idtypechargeedit="";
		FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
	          msg = new FacesMessage("Exception");
	           FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	
	public ModelService<Charge> getManager() {
		return manager;
	}
	public void setManager(ModelService<Charge> manager) {
		this.manager = manager;
	}
	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}
	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}
	public Charge getCharge() {
		return charge;
	}
	public void setCharge(Charge charge) {
		this.charge = charge;
	}
	public List<Charge> getListcharge() {
		if(listcharge==null)
			listcharge=manager.getObject();
		return listcharge;
	}
	public void setListcharge(List<Charge> listcharge) {
		this.listcharge = listcharge;
	}
	public int getIdtypecharge() {
		return idtypecharge;
	}
	public void setIdtypecharge(int idtypecharge) {
		this.idtypecharge = idtypecharge;
	}


	public ModelService<TypeCharge> getManagertypecharge() {
		return managertypecharge;
	}


	public void setManagertypecharge(ModelService<TypeCharge> managertypecharge) {
		this.managertypecharge = managertypecharge;
	}

	public String getTempocontsat() {
		return tempocontsat;
	}

	public void setTempocontsat(String tempocontsat) {
		this.tempocontsat = tempocontsat;
	}

	public Charge getChargetempo() {
		return chargetempo;
	}

	public void setChargetempo(Charge chargetempo) {
		this.chargetempo = chargetempo;
	}

	public String getIdtypechargeedit() {
		return idtypechargeedit;
	}

	public void setIdtypechargeedit(String idtypechargeedit) {
		this.idtypechargeedit = idtypechargeedit;
	}

	public Date getDatecharge() {
		return datecharge;
	}

	public void setDatecharge(Date datecharge) {
		this.datecharge = datecharge;
	}
	 public List<years> getListeyears() {
	    	if(listeyears==null)
	    		listeyears=manageryears.getObject();
			return listeyears;
		}

		public void setListeyears(List<years> listeyears) {
			this.listeyears = listeyears;
		}

		public ModelService<years> getManageryears() {
			return manageryears;
		}

		public void setManageryears(ModelService<years> manageryears) {
			this.manageryears = manageryears;
		}

		public int getIdannee() {
			return idannee;
		}

		public void setIdannee(int idannee) {
			this.idannee = idannee;
		}

		public String[] getTabmois() {
			return tabmois;
		}

		public void setTabmois(String[] tabmois) {
			this.tabmois = tabmois;
		}

		public String getConstat() {
			return constat;
		}

		public void setConstat(String constat) {
			this.constat = constat;
		}

		public String getChoixmois() {
			return choixmois;
		}

		public void setChoixmois(String choixmois) {
			this.choixmois = choixmois;
		}

	
	
	

}
