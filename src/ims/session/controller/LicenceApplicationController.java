package ims.session.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ims.model.entities.LicenceApplication;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@RequestScoped
public class LicenceApplicationController implements Serializable {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
    
    @ManagedProperty(value="#{LicenceAppsManager}")
    private ModelService<LicenceApplication> managerapplication;
    
    private LicenceApplication licenceapps=new LicenceApplication();
    private LicenceApplication licenceappstempo=new LicenceApplication();
    private List<LicenceApplication> listLicenceApps;
	public LicenceApplicationController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void generer(){
		  FacesMessage msg;
		  String cle="";
		try {
			Random rand=new Random();
			
			char [] tableau = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
			 char mp = ' ';
			for (int i = 0; i < 25; i++){

                // sélectionne au hasard un indice dans le tableau
                int indiceChiffre = rand.nextInt(tableau.length);
                //System.out.print("-cara : "+indiceChiffre+" => " );
                mp = tableau[indiceChiffre];
                //System.out.println(mp);
                cle+=mp;
                if(i%5==0){
                	if(i!=0)
                		cle+="-";
                }
                	
             }
			
			licenceapps.setCle(cle);
	            msg = new FacesMessage("la clé est générer avec Success");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
	           msg = new FacesMessage("Exception répeter SVP");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
    
    public void insert(){
    	   FacesMessage msg;
    	   listLicenceApps=null;
    	try {
			licenceapps.setDategenerer(new Date());
			licenceapps.setCodegeneration("Aletoire");
			managerapplication.insertObject(licenceapps);
            msg = new FacesMessage("Licence Application Créer avec Success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
            msg = new FacesMessage("Exception");
     FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
    
    public void Delete(){
    	   FacesMessage msg;
    	try {
			listLicenceApps=null;
			managerjdbc.deleteLiceneApps(licenceappstempo);
            msg = new FacesMessage("Licence Application Créer avec Success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
            msg = new FacesMessage("Exception");
     FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public ModelService<LicenceApplication> getManagerapplication() {
		return managerapplication;
	}

	public void setManagerapplication(
			ModelService<LicenceApplication> managerapplication) {
		this.managerapplication = managerapplication;
	}

	public LicenceApplication getLicenceapps() {
		return licenceapps;
	}

	public void setLicenceapps(LicenceApplication licenceapps) {
		this.licenceapps = licenceapps;
	}

	public List<LicenceApplication> getListLicenceApps() {
		if(listLicenceApps==null)
			listLicenceApps=managerapplication.getObject();
		return listLicenceApps;
	}

	public void setListLicenceApps(List<LicenceApplication> listLicenceApps) {
		this.listLicenceApps = listLicenceApps;
	}

	public LicenceApplication getLicenceappstempo() {
		return licenceappstempo;
	}

	public void setLicenceappstempo(LicenceApplication licenceappstempo) {
		this.licenceappstempo = licenceappstempo;
	}
    
    
    
}
