/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;

import ims.model.entities.Contact;
import ims.model.entities.Fournisseur;
import ims.model.entities.Pays;
import ims.model.entities.Ville;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;



/**
 *
 * @author rabeh
 */
@ManagedBean
@ApplicationScoped
public class FournisseurController implements Serializable{


	private static final long serialVersionUID = 1L;
	
	 @ManagedProperty(value = "#{managerDataBase}")
	 private ManagerDataBase managerApplication;
	 
	 
	public ManagerDataBase getManagerApplication() {
		return managerApplication;
	}

	public void setManagerApplication(ManagerDataBase managerApplication) {
		this.managerApplication = managerApplication;
	}

	@ManagedProperty(value="#{FournisseurManager}")
    private ModelService<Fournisseur> manager;
     
	@ManagedProperty(value="#{PaysManager}")
    private ModelService<Pays> managerpays;
	
    @ManagedProperty(value="#{VilleManager}")
	private ModelService<Ville> managerV;
    
	 @ManagedProperty(value="#{ContactManager}")
	 private ModelService<Contact> contactmanager;
    
    @ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
	
    private Fournisseur fournisseur=new Fournisseur();;
    private Fournisseur objectfournisseur;
    private List<Fournisseur> fournisseurs;
    private List<Fournisseur> filteredFournisseur;
    private List<Ville> villes=new ArrayList<Ville>();
    
    
    private Contact contact;
    private List<Contact> contacts;
    private Contact contacttempo;
    
    //info de fournisseur
    private String nomsocietemodif="";
    private String adressemodif="";
    private String nomsociete="";
    private String adresse="";
    private String tel="";
    private int idpays=1;
    private int idville=1;
    private String idfournisseur="";
    private String fournisseurId="";
    
    
    //Info de Contact
    
    private String nomcontact="";
    private String prenomconatct="";
    private String emailcontact="";
    private String deptcontact="";
    private String gsmcontact="";
    private String telcontact="";
    private String faxcontact="";
    private String foncontact="";
    //------------------------------
    private String str_fournisseur;
    
    
	@PostConstruct
    public void init(){
		System.out.println("-----------INIT FOURNISSEUR----------------");
			fournisseurs=managerApplication.getFournisseurs();
			System.out.println("-------FIN INIT FOURNISSEUR----------------");
			System.out.println("------- INIT CONTACT FOURNISSEUR----------------");
			contacts=managerApplication.getContactfournisseur();
			System.out.println("------- FIN INIT CONTACT FOURNISSEUR----------------");
    }
	
	public void actualiser(){
		managerApplication.setFournisseurs(null);
	}
	
	public void actualisercontactfournisseur(){
		contacts=null;
		
	}
	
    public String preparecreer(){
    	fournisseur=new Fournisseur();
    	adresse="";
    	nomsociete="";
    	return "insert?faces-redirect=true";
    }
    
    public List<Ville> getVilles() {
    	villes=managerV.getObject();
		return villes;
	}

	public void setVilles(List<Ville> villes) {
		this.villes = villes;
	}
    
    public ModelService<Ville> getManagerV() {
		return managerV;
	}

	public void setManagerV(ModelService<Ville> managerV) {
		this.managerV = managerV;
	}

	/**************************************/
    public FournisseurController() {
    }
    
    public void onCellContact(){
     	FacesMessage msg;
     	try {
     		
     		if(manager.getByName(contacttempo.getFournisseur().getNomsociete())!=null){
	     			contacttempo.setFournisseur(manager.getByName(contacttempo.getFournisseur().getNomsociete()));
     		}
     		
     		managerjdbc.updateContactFournisseur(contacttempo);
     		//contacts=null;
            msg = new FacesMessage("Contact Modifier avec success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
           
            System.out.println("onCellContact FIN");
		} catch (Exception e) {
            msg = new FacesMessage("Exception "+e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
    
    public void deletecontact(){
     	FacesMessage msg;
     	contacts=null;
    	try {
			managerjdbc.deleteContact(contacttempo);
            msg = new FacesMessage("Contact Supprimer avec success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
            msg = new FacesMessage("Exception " + e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
    
    public void insertcontact(){
    	FacesMessage msg;
    	contacts=null;
    	contact=new Contact();
    	try {
    		if(!idfournisseur.equals("")){
    			Fournisseur f=manager.getObject(Integer.parseInt(idfournisseur));
    	    	contact.setGsm1(gsmcontact);
    	    	contact.setNom(nomcontact);
    	    	contact.setPrenom(prenomconatct);
    	    	contact.setEmail(emailcontact);
    	    	contact.setDept(deptcontact);
    	    	contact.setTel(telcontact);
    	    	contact.setFaxclient(faxcontact);
        		contact.setFournisseur(f);
        		contact.setVille(f.getVille());
        		contact.setPays(f.getPays());
    			contactmanager.insertObject(contact);
                msg = new FacesMessage("Contact est Créer avec success");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                nomcontact="";
                prenomconatct="";
                emailcontact="";
                deptcontact="";
                gsmcontact="";
                telcontact="";
                faxcontact="";
                foncontact="";
    		}
		} catch (Exception e) {
            msg = new FacesMessage("Exception "+e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            nomcontact="";
            prenomconatct="";
            emailcontact="";
            deptcontact="";
            gsmcontact="";
            telcontact="";
            faxcontact="";
            foncontact="";
		}
    }
     
    public void onCellfournisseur(){
    	
    	 FacesMessage msg;
    	 managerApplication.setFournisseurs(null);
    	 try {
    		 if(managerpays.getByName(fournisseur.getPays().getPays())!=null)
    			 fournisseur.setPays(managerpays.getByName(fournisseur.getPays().getPays()));
    		 if(managerV.getByName(fournisseur.getVille().getVille())!=null)
    			 fournisseur.setVille(managerV.getByName(fournisseur.getVille().getVille()));
    		 
    		 manager.updateObject(fournisseur);
             msg = new FacesMessage("Fournisseur est Modifier avec success");
             FacesContext.getCurrentInstance().addMessage(null, msg);
       		 nomsocietemodif="";
    		 adressemodif="";
		} catch (Exception e) {
            msg = new FacesMessage("Exception");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
    
    public String prepareView(){
    	idpays=fournisseur.getPays().getIdpays();
    	idville=fournisseur.getVille().getIdville();
		return "Viewfournisseur?faces-redirect=true";
	}
    
	public List<String> completerVille(String query){
    	try {
     List<String> filtredville=new ArrayList<String>();
    	try {
    		List<Ville> listeobjVille=managerV.getObject();
			
			 for (int i = 0; i < listeobjVille.size(); i++) {
		            Ville skin = listeobjVille.get(i);
		            if(skin.getVille().toLowerCase().startsWith(query) && skin.getVille()!=null) {
		            	filtredville.add(skin.getVille());
		            }
		        }
			
		} catch (Exception e) {
			e.getStackTrace();
			filtredville=null;
		}
    	return filtredville;
		} catch (Exception e) {
			return null;
		}

	}
	
	public  List<String> completerFournisseur(String query){
    	try {
    		List<String> filtredfournisseur=new ArrayList<String>();
	    	try{
				List<Fournisseur> listFournisseur=manager.getObject();
				 for(int i = 0; i < listFournisseur.size(); i++){
					 Fournisseur skin = listFournisseur.get(i);
			            if(skin.getNomsociete().toLowerCase().startsWith(query) && skin.getNomsociete()!=null) {
			            	filtredfournisseur.add(skin.getNomsociete());
			            }
			        }
			}catch (Exception e) {
				e.getStackTrace();
				filtredfournisseur=null;
			}
	    	return filtredfournisseur;
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<String> completerPays(String query){
    	try {
    		List<String> filtredpays=new ArrayList<String>();
	    	try{
				List<Pays> listPays=managerpays.getObject();
				 for(int i = 0; i < listPays.size(); i++){
			            Pays skin = listPays.get(i);
			            if(skin.getPays().toLowerCase().startsWith(query) && skin.getPays()!=null) {
			            	filtredpays.add(skin.getPays());
			            }
			        }
			}catch (Exception e) {
				e.getStackTrace();
				filtredpays=null;
			}
	    	return filtredpays;
		} catch (Exception e) {
			return null;
		}

	}
	
    public void annuler(){
        FacesMessage msg;
        	try{
        		vider();
          		msg = new FacesMessage("Fournisseur Annuler avec Suceess");
                FacesContext.getCurrentInstance().addMessage(null, msg);
        	}catch (NullPointerException e) {
            msg = new FacesMessage("Exception => "+e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void insert(){
        FacesMessage msg;
        try {
        	managerApplication.setFournisseurs(null);
        	if(managerjdbc.getExisteFournisseur(nomsociete)<=0){
        	fournisseur.setPays(managerpays.getObject(idpays));
        	fournisseur.setVille(managerV.getObject(idville));
        	fournisseur.setAdresse(adresse);
        	fournisseur.setNomsociete(nomsociete);
        	fournisseur.setTel(tel);
        	vider();
       boolean op= manager.insertObject(fournisseur);
       if(op==true){
               msg = new FacesMessage("Fournisseur est creer avec success");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                vider();
       }else{
                msg = new FacesMessage("Exception "+"--> insert Object");
                FacesContext.getCurrentInstance().addMessage(null, msg);
       }
       
        	}else{
        		msg = new FacesMessage("Societe existe deja");
                FacesContext.getCurrentInstance().addMessage(null, msg);
        	}
        } catch (NullPointerException e) {
       
            msg = new FacesMessage("Exception"+e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    private void vider() {
		nomsociete="";
		adresse="";
		tel="";
		idpays=0;
		idville=0;
	}

	public void Delete(){
        FacesMessage msg;
        try {
        	managerApplication.setFournisseurs(null);
        boolean op=managerjdbc.delete(fournisseur);
        if(op==true)
        	msg = new FacesMessage("Fournisseur est bien Supprimer");
        else
        	msg = new FacesMessage("Fournisseur déja Utiliser");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            msg = new FacesMessage("ERORR de Suppression");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }



    public ModelService<Fournisseur> getManager() {
        return manager;
    }

    public void setManager(ModelService<Fournisseur> manager) {
        this.manager = manager;
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

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public List<Fournisseur> getFournisseurs() {
    		   fournisseurs=managerApplication.getFournisseurs();
        return fournisseurs;  
    }

    public void setFournisseurs(List<Fournisseur> fournisseurs) {
        this.fournisseurs = fournisseurs;
    }

    public List<Fournisseur> getFilteredFournisseur() {
        return filteredFournisseur;
    }

    public void setFilteredFournisseur(List<Fournisseur> filteredFournisseur) {
        this.filteredFournisseur = filteredFournisseur;
    }

	public ModelService<Pays> getManagerpays() {
		return managerpays;
	}

	public void setManagerpays(ModelService<Pays> managerpays) {
		this.managerpays = managerpays;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getIdpays() {
		return idpays;
	}

	public void setIdpays(int idpays) {
		this.idpays = idpays;
	}

	public int getIdville() {
		return idville;
	}

	public void setIdville(int idville) {
		this.idville = idville;
	}

	public String getNomsociete() {
		return nomsociete;
	}

	public void setNomsociete(String nomsociete) {
		this.nomsociete = nomsociete;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public Fournisseur getObjectfournisseur() {
		return objectfournisseur;
	}

	public void setObjectfournisseur(Fournisseur objectfournisseur) {
		this.objectfournisseur = objectfournisseur;
	}

	public String getNomsocietemodif() {
		nomsocietemodif=fournisseur.getNomsociete();
		return nomsocietemodif;
	}

	public void setNomsocietemodif(String nomsocietemodif) {
		this.nomsocietemodif = nomsocietemodif;
	}

	public String getAdressemodif() {
		adressemodif=fournisseur.getAdresse();
		return adressemodif;
	}

	public void setAdressemodif(String adressemodif) {
		this.adressemodif = adressemodif;
	}

	public String getIdfournisseur() {
		return idfournisseur;
	}

	public void setIdfournisseur(String idfournisseur) {
		this.idfournisseur = idfournisseur;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public List<Contact> getContacts() {
		if(contacts==null)
			contacts=contactmanager.getByNames("fournisseur");
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public Contact getContacttempo() {
		return contacttempo;
	}

	public void setContacttempo(Contact contacttempo) {
		this.contacttempo = contacttempo;
	}

	public ModelService<Contact> getContactmanager() {
		return contactmanager;
	}

	public void setContactmanager(ModelService<Contact> contactmanager) {
		this.contactmanager = contactmanager;
	}

	public String getNomcontact() {
		return nomcontact;
	}

	public void setNomcontact(String nomcontact) {
		this.nomcontact = nomcontact;
	}

	public String getPrenomconatct() {
		return prenomconatct;
	}

	public void setPrenomconatct(String prenomconatct) {
		this.prenomconatct = prenomconatct;
	}

	public String getEmailcontact() {
		return emailcontact;
	}

	public void setEmailcontact(String emailcontact) {
		this.emailcontact = emailcontact;
	}

	public String getDeptcontact() {
		return deptcontact;
	}

	public void setDeptcontact(String deptcontact) {
		this.deptcontact = deptcontact;
	}

	public String getGsmcontact() {
		return gsmcontact;
	}

	public void setGsmcontact(String gsmcontact) {
		this.gsmcontact = gsmcontact;
	}

	public String getTelcontact() {
		return telcontact;
	}

	public void setTelcontact(String telcontact) {
		this.telcontact = telcontact;
	}

	public String getFaxcontact() {
		return faxcontact;
	}

	public void setFaxcontact(String faxcontact) {
		this.faxcontact = faxcontact;
	}

	public String getFoncontact() {
		return foncontact;
	}

	public void setFoncontact(String foncontact) {
		this.foncontact = foncontact;
	}

	public String getFournisseurId() {
		return fournisseurId;
	}

	public void setFournisseurId(String fournisseurId) {
		this.fournisseurId = fournisseurId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getStr_fournisseur() {
			str_fournisseur="Fournisseur";
		return str_fournisseur;
	}

	public void setStr_fournisseur(String str_fournisseur) {
		this.str_fournisseur = str_fournisseur;
	}
	
	
}
