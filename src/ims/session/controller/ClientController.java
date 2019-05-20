/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;

import ims.model.entities.CategorieIISociete;
import ims.model.entities.Client;
import ims.model.entities.Contact;
import ims.model.entities.GroupeClient;
import ims.model.entities.Langue;
import ims.model.entities.Pays;
import ims.model.entities.Secteur;
import ims.model.entities.Ville;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
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
public class ClientController implements Serializable {

	   /**
		* creator RABEH TARIK
	    **/
	private static final long serialVersionUID = 1L;
	
	 @ManagedProperty(value = "#{managerDataBase}")
	 private ManagerDataBase managerApplication;
	 
	 
	public ManagerDataBase getManagerApplication() {
		return managerApplication;
	}

	public void setManagerApplication(ManagerDataBase managerApplication) {
		this.managerApplication = managerApplication;
	}
	
	@ManagedProperty(value="#{SecteurManager}")
	private ModelService<Secteur> managerS;
	
	@ManagedProperty(value="#{CategorieIIManager}")
	private ModelService<CategorieIISociete> managerCat;

	@ManagedProperty(value="#{LangueManager}")
	private ModelService<Langue> managerLangue;
	
	@ManagedProperty(value="#{ClientManager}")
	private ModelService<Client> manager;
	
	@ManagedProperty(value="#{PaysManager}")
    private ModelService<Pays> managerpays;
	
	 @ManagedProperty(value="#{VilleManager}")
	 private ModelService<Ville> managerV;
	 
	 @ManagedProperty(value="#{JDBCManager}")
	 private ModelServiceJDBC managerjdbc;
	 
	 @ManagedProperty(value="#{ContactManager}")
	 private ModelService<Contact> contactmanager;
	 
	 @ManagedProperty(value="#{GroupeClientManager}")
	 private ModelService<GroupeClient> groupeclientmanager;
	 
	 
		 private int idcategorie=1;
		 private int GroupeClientID;
		 private int idsecteur;
		 private int idville=1;
		 private int idvillec=1;
		 private int idpays=1;
		 private int idpaysc=1;
		 private int idlangue=1;
		 private String str_client;
	 
	    private Client client;
	    
	    private List<Client> clients;
	    private List<Client> filteredClient;
	    
	    private List<GroupeClient> groupesclient;
	    private GroupeClient groupeclient;
		
	    private List<Contact> contacts;
	    private Contact contact;
	    private Contact contacttempo;
	    
	    private List<Secteur> secteurs=new ArrayList<Secteur>();
		private List<Ville> villes;
		
		private String nom;
		private String societe;
		private String adresse;
		private String email;
		private String codepostale;
		private String siteweb;
		private String tel;
		private String tel2;
		private String fax;
		private String fonction;
		private String gsm;
		
		private String tempoactiv="";
		
		@PostConstruct
		public void init(){
			System.out.println("---------------INIT CLIENT---------------");
				clients=managerApplication.getClients();
			System.out.println("---------------FIN INIT CLIENT---------------");
		}
		
	    public String preparecreer(){
	    	client=new Client();
	    	contact=new Contact();
	    	contacts=new ArrayList<Contact>();
	    	adresse="";
	    	societe="";
	    	return "insert?faces-redirect=true";
	    }
	    
	    public String annuler(){
	    	return "index?faces-redirect=true";
	    }
	    public void actualiser(){
	    	managerApplication.setClients(null);
	    }
	    
	    public void actualiserModifcontact(){
	    	System.out.println("Prepare contact");
	    	contacts=null;
			contacts=contactmanager.getObjects(client.getIdclient());
			for(int i=0;i<contacts.size();i++){
				if(client.getContactprincipal()!=null){
					if(contacts.get(i).getCodeclient()==client.getContactprincipal().getCodeclient())
						contacts.get(i).setPrincipal("Oui");
				}
			}
	    }
	    
	    public void actualisercontact(){
	    	//contacts
	    }
	    
	    public void onCellEditclient(){
	    	FacesMessage msg;
	    	System.out.println("onCellEditclient");
	    	try {
		    	if(!tempoactiv.equals("")){
		    		if(tempoactiv.equals("Oui")){
		    			System.out.println("Code Client "+contacttempo.getCodeclient());
		    		client.setContactprincipal(contacttempo);
			    	managerjdbc.updateClient(client);
			        
			    	contacts=contactmanager.getObjects(client.getIdclient());
					for(int i=0;i<contacts.size();i++){
						if(client.getContactprincipal()!=null){
							if(contacts.get(i).getCodeclient()==client.getContactprincipal().getCodeclient())
								contacts.get(i).setPrincipal("Oui");
							managerjdbc.updateContactPrincipalForClient(contacttempo);
						}
					}
		    		}
		    	}
		    	managerjdbc.updateContact(contacttempo);
		    	tempoactiv="";
		    	msg = new FacesMessage("contact est modifier avec success");
		        FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (Exception e) {
				tempoactiv="";
		        msg = new FacesMessage("Exception");
		        FacesContext.getCurrentInstance().addMessage(null, msg);
			}

	    }
	    
	    public void onCellEdit(){
	    	FacesMessage msg;
	    	System.out.println("onCellEdit");
	    	try {
		    	if(!tempoactiv.equals("")){
		    		contacttempo.setPrincipal(tempoactiv);
		    		if(tempoactiv.equals("Oui")){
		    			for (int i = 0; i < contacts.size(); i++) {
							if(contacttempo.getCodeclient()!=contacts.get(i).getCodeclient())
								contacts.get(i).setPrincipal("Non");
						}
		    		}
		    	}
		    	tempoactiv="";
		    	msg = new FacesMessage("contact est modifier avec success");
		        FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (Exception e) {
				tempoactiv="";
		        msg = new FacesMessage("Exception",e.getLocalizedMessage());
		        FacesContext.getCurrentInstance().addMessage(null, msg);
			}

	    }
	    
	    public void suppcontact(){
	    FacesMessage msg;
        try {
		contacts.remove(contacttempo);
        msg = new FacesMessage("contact supprimer avec success");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        contacttempo=null;
        }
		catch (Exception e) {
            msg = new FacesMessage("ERROR CREATION");
            FacesContext.getCurrentInstance().addMessage(null, msg);
     } 
	}
	
	    public void suppcontactupdate(){
	    FacesMessage msg;
        try {
		contacts.remove(contacttempo);
		managerjdbc.deleteContact(contacttempo);
        msg = new FacesMessage("contact supprimer avec success");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
		catch (Exception e) {
            msg = new FacesMessage("ERROR CREATION");
            FacesContext.getCurrentInstance().addMessage(null, msg);
     } 
	}

	    public String getFonction() {
			return fonction;
		}

		public void setFonction(String fonction) {
			this.fonction = fonction;
		}
	 
	public ModelService<Secteur> getManagerS() {
		return managerS;
	}

	public void setManagerS(ModelService<Secteur> managerS) {
		this.managerS = managerS;
	}
	
	public List<Ville> getVilles() {
		villes=managerV.getObject();
		return villes;
	}

	public void setVilles(List<Ville> villes) {
		this.villes = villes;
	}

	public String prepareView(){
		try {
			contact=new Contact();
			System.out.println("Prepare Client");
			idpays=client.getPays().getIdpays();
			idville=client.getVille().getIdville();
			if(client.getCategorie()!=null)
				idcategorie=client.getCategorie().getId();
			if(client.getSecteur()!=null)
				idsecteur=client.getSecteur().getId();
			if(client.getGroupeclient()!=null)
				GroupeClientID=client.getGroupeclient().getId();
			else
				GroupeClientID=0;
			System.out.println("FIN Prepare Client");
			
			System.out.println("Prepare contact");
			contacts=contactmanager.getObjects(client.getIdclient());
			for(int i=0;i<contacts.size();i++){
				if(client.getContactprincipal()!=null){
					if(contacts.get(i).getCodeclient()==client.getContactprincipal().getCodeclient())
						contacts.get(i).setPrincipal("Oui");
				}
			}
			System.out.println("fin Prepare contact");
		} catch (Exception e) {
			System.out.println("-------Exception---------");
			e.getStackTrace();
		}
		
		
		return "ViewClient?faces-redirect=true";
	}
	
	public List<Secteur> getSecteurs() {
		secteurs=managerS.getObject();
		return secteurs;
	}

	public void setSecteurs(List<Secteur> secteurs) {
		this.secteurs = secteurs;
	}
    
    
    public ClientController() {
       
    }
    
    public void insertcontact(){
    	   FacesMessage msg;
           try {
        	   contact.setCodeclient(contacts.size()+1);
        	   contacts.add(contact);
        	   contact=new Contact();
           }catch (Exception e) {
               msg = new FacesMessage("ERROR CREATION");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }   
    }
    
    public void insertcontactModif(){
 	   FacesMessage msg;
        try {
        	contact.setClient(client);
     	   contactmanager.insertObject(contact);
           contacts.add(contact);
           contact=new Contact();
        }catch (Exception e) {
            msg = new FacesMessage("ERROR CREATION");
    FacesContext.getCurrentInstance().addMessage(null, msg);
     }   
 }

    public void insert(){
        FacesMessage msg;
        managerApplication.setClients(null);
        try {
        	if(managerjdbc.getExicteClient(societe)<=0){
        		if(GroupeClientID!=0){
        			client.setGroupeclient(groupeclientmanager.getObject(GroupeClientID));
        		}else{
        			GroupeClient newgroupe=new GroupeClient();
        			newgroupe.setNomGroupe(societe);
        			groupeclientmanager.insertObject(newgroupe);
        			client.setGroupeclient(newgroupe);
        			groupesclient=null;
        		}
        		client.setTel(tel);
        		client.setFax(fax);
        		client.setEmail(email);
	        	client.setCategorie(managerCat.getObject(idcategorie));
	        	client.setSecteur(managerS.getObject(idsecteur));
	        	client.setLangue(managerLangue.getObject(idlangue));
	        	client.setPays(managerpays.getObject(idpays));
	        	client.setVille(managerV.getObject(idville));
	        	client.setAdresse(adresse);
	        	client.setSociete(societe);
	        	manager.insertObject(client);
	        	for (int i = 0; i < contacts.size(); i++) {
					contacts.get(i).setClient(client);
					contacts.get(i).setVille(client.getVille());
					contacts.get(i).setPays(client.getPays());
					contactmanager.insertObject(contacts.get(i));
					if(contacts.get(i).getPrincipal().equals("Oui"))
						client.setContactprincipal(contacts.get(i));
				}
	        	
	        	managerjdbc.updateClient(client);
	        	contact=new Contact();
	        	vider();
	        	msg = new FacesMessage("Client creer avec success");
	        	FacesContext.getCurrentInstance().addMessage(null, msg);
        	}else{
        		  	msg = new FacesMessage("Societe existe deja");
        		  	FacesContext.getCurrentInstance().addMessage(null, msg);
        	}
        } catch (Exception e) {
               msg = new FacesMessage("ERROR CREATION");
               FacesContext.getCurrentInstance().addMessage(null, msg);
        }   
    }

    public void update(){
        FacesMessage msg;
        try {
    		if(GroupeClientID!=0){
    			client.setGroupeclient(groupeclientmanager.getObject(GroupeClientID));
    		}else{
    			GroupeClient newgroupe=new GroupeClient();
    			newgroupe.setNomGroupe(client.getSociete());
    			groupeclientmanager.insertObject(newgroupe);
    			client.setGroupeclient(newgroupe);
    			groupesclient=null;
    		}
        	client.setCategorie(managerCat.getObject(idcategorie));
        	client.setSecteur(managerS.getObject(idsecteur));
        	client.setLangue(managerLangue.getObject(idlangue));
        	client.setPays(managerpays.getObject(idpays));
        	client.setVille(managerV.getObject(idville));
        	managerjdbc.updateClient(client);
        	contact=new Contact();
        	vider();
           msg = new FacesMessage("Client Modifier avec success");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        	
        } catch (Exception e) {
               msg = new FacesMessage("ERROR CREATION");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }   
    }
    
    public String Delete(){
        FacesMessage msg;
        try {
        manager.deleteObject(client);
       msg = new FacesMessage("Client est bien Supprimer");
       FacesContext.getCurrentInstance().addMessage(null, msg);
       return "index.xhtml";
        } catch (Exception e) {
            msg = new FacesMessage("ERORR de Suppression");
       FacesContext.getCurrentInstance().addMessage(null, msg);
       return "index.xhtml";
        }
    }

    public ModelService<Client> getManager() {
        return manager;
    }

    public void setManager(ModelService<Client> manager) {
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Client> getClients() {
    		clients=managerApplication.getClients();
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Client> getFilteredClient() {
        return filteredClient;
    }

    public void setFilteredClient(List<Client> filteredClient) {
        this.filteredClient = filteredClient;
    }

	public ModelService<Ville> getManagerV() {
		return managerV;
	}

	public void setManagerV(ModelService<Ville> managerV) {
		this.managerV = managerV;
	}

	public ModelService<Pays> getManagerpays() {
		return managerpays;
	}

	public void setManagerpays(ModelService<Pays> managerpays) {
		this.managerpays = managerpays;
	}

	public ModelService<CategorieIISociete> getManagerCat() {
		return managerCat;
	}

	public void setManagerCat(ModelService<CategorieIISociete> managerCat) {
		this.managerCat = managerCat;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getSociete() {
		return societe;
	}

	public void setSociete(String societe) {
		this.societe = societe;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getCodepostale() {
		return codepostale;
	}

	public void setCodepostale(String codepostale) {
		this.codepostale = codepostale;
	}

	public String getSiteweb() {
		return siteweb;
	}

	public void setSiteweb(String siteweb) {
		this.siteweb = siteweb;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private void vider(){
    	adresse="";
    	codepostale="";
    	email="";
    	fax="";
    	nom="";
    	siteweb="";
    	societe="";
    	tel="";
    	tel2="";
    	email="";
    }

	public int getIdville() {
		return idville;
	}

	public void setIdville(int idville) {
		this.idville = idville;
	}

	public int getIdpays() {
		return idpays;
	}

	public void setIdpays(int idpays) {
		this.idpays = idpays;
	}

	public int getIdsecteur() {
		return idsecteur;
	}

	public void setIdsecteur(int idsecteur) {
		this.idsecteur = idsecteur;
	}

	public int getIdcategorie() {
		return idcategorie;
	}

	public void setIdcategorie(int idcategorie) {
		this.idcategorie = idcategorie;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public int getIdlangue() {
		return idlangue;
	}

	public void setIdlangue(int idlangue) {
		this.idlangue = idlangue;
	}

	public ModelService<Langue> getManagerLangue() {
		return managerLangue;
	}

	public void setManagerLangue(ModelService<Langue> managerLangue) {
		this.managerLangue = managerLangue;
	}
    
	 public String getGsm() {
			return gsm;
		}

		public void setGsm(String gsm) {
			this.gsm = gsm;
		}

		public List<Contact> getContacts() {
			return contacts;
		}

		public void setContacts(List<Contact> contacts) {
			this.contacts = contacts;
		}

		public Contact getContact() {
			return contact;
		}

		public void setContact(Contact contact) {
			this.contact = contact;
		}

		public Contact getContacttempo() {
			return contacttempo;
		}

		public void setContacttempo(Contact contacttempo) {
			this.contacttempo = contacttempo;
		}

		public int getIdvillec() {
			return idvillec;
		}

		public void setIdvillec(int idvillec) {
			this.idvillec = idvillec;
		}

		public int getIdpaysc() {
			return idpaysc;
		}

		public void setIdpaysc(int idpaysc) {
			this.idpaysc = idpaysc;
		}

		public ModelService<Contact> getContactmanager() {
			return contactmanager;
		}

		public void setContactmanager(ModelService<Contact> contactmanager) {
			this.contactmanager = contactmanager;
		}

		public String getTempoactiv() {
			return tempoactiv;
		}

		public void setTempoactiv(String tempoactiv) {
			this.tempoactiv = tempoactiv;
		}

		public ModelService<GroupeClient> getGroupeclientmanager() {
			return groupeclientmanager;
		}

		public void setGroupeclientmanager(
				ModelService<GroupeClient> groupeclientmanager) {
			this.groupeclientmanager = groupeclientmanager;
		}

		public List<GroupeClient> getGroupesclient() {
			if(groupesclient==null){
				System.out.println("GET CONTROLLER GROUPE");
				groupesclient=groupeclientmanager.getObject();
			}
				
			return groupesclient;
		}

		public void setGroupesclient(List<GroupeClient> groupesclient) {
			this.groupesclient = groupesclient;
		}

		public GroupeClient getGroupeclient() {
			return groupeclient;
		}

		public void setGroupeclient(GroupeClient groupeclient) {
			this.groupeclient = groupeclient;
		}

		public int getGroupeClientID() {
			return GroupeClientID;
		}

		public void setGroupeClientID(int groupeClientID) {
			GroupeClientID = groupeClientID;
		}

		public String getStr_client() {
			str_client="Client";
			return str_client;
		}

		public void setStr_client(String str_client) {
			this.str_client = str_client;
		}
    
		
}
