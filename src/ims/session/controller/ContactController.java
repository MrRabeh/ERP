/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;

import ims.model.entities.Client;
import ims.model.entities.Contact;
import ims.model.entities.Pays;
import ims.model.entities.Ville;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.util.ArrayList;
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
public class ContactController implements Serializable {

	   /**
		* creator RABEH TARIK
	    **/
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{ContactManager}")
	private ModelService<Contact> manager;
	
	@ManagedProperty(value="#{PaysManager}")
    private ModelService<Pays> managerpays;
	
	 @ManagedProperty(value="#{VilleManager}")
	 private ModelService<Ville> managerV;
	 
	 @ManagedProperty(value="#{JDBCManager}")
	 private ModelServiceJDBC managerjdbc;
	 
	 @ManagedProperty(value="#{ClientManager}")
	 private ModelService<Client> managerclient;
	 
	 
	 private int idville=1;
	 private int idpays=1;
	 private List<Ville> villes;
	 private int idclient=0;
	 
	 private Contact contact=new Contact();
	 private List<Contact> contacts=new ArrayList<Contact>();
	 private List<Contact> fcontacts=new ArrayList<Contact>();
	 
	 private List<Contact> filteredContact=new ArrayList<Contact>();

		
	    public ContactController() {
	        
	    }
		
	    public String preparecreate(){
	    	contact=new Contact();
	    	return "insertContact?faces-redirect=true";
	    }
	    
	public String prepareView(){
		idclient=contact.getClient().getIdclient();
		idville=contact.getVille().getIdville();
		idpays=contact.getPays().getIdpays();
		return "ViewContact?faces-redirect=true";
	}
	
    public void insert(){
        FacesMessage msgs;
        try {
        	contacts=null;
        	contact.setClient(managerclient.getObject(idclient));
        	contact.setPays(managerpays.getObject(idpays));
        	contact.setVille(managerV.getObject(idville));
        	manager.insertObject(contact);
			System.out.println("contact est bien Enregistrer");
			msgs = new FacesMessage("contact est bien Enregistrer");
			FacesContext.getCurrentInstance().addMessage(null, msgs);
	        contact=new Contact();
	        contacts=manager.getObject();
        } catch (Exception e) {
        	msgs = new FacesMessage("Probleme d'insertion");
       FacesContext.getCurrentInstance().addMessage(null, msgs);
        }   
    }

    public void update(){
        FacesMessage msg;
        try {
        	contacts=null;
        	contact.setVille(managerV.getObject(idville));
        	contact.setPays(managerpays.getObject(idpays));
        	contact.setClient(managerclient.getObject(idclient));
        	managerjdbc.updateContact(contact);
        	contacts=manager.getObject();
           msg = new FacesMessage("Contact Modifier avec success");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        	
        } catch (Exception e) {
               msg = new FacesMessage("ERROR CREATION");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }   
    }
    
    public void Delete(){
        FacesMessage msg;
        try {
        manager.deleteObject(contact);
       msg = new FacesMessage("contact est bien Supprimer");
       FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Exception e) {
            msg = new FacesMessage("ERORR de Suppression");
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

	public ModelService<Contact> getManager() {
		return manager;
	}

	public void setManager(ModelService<Contact> manager) {
		this.manager = manager;
	}

	public ModelService<Pays> getManagerpays() {
		return managerpays;
	}

	public void setManagerpays(ModelService<Pays> managerpays) {
		this.managerpays = managerpays;
	}

	public ModelService<Ville> getManagerV() {
		return managerV;
	}

	public void setManagerV(ModelService<Ville> managerV) {
		this.managerV = managerV;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
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

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public List<Contact> getContacts() {
		if(contact!=null)
		contacts=manager.getObject();
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public List<Contact> getFilteredContact() {
		return filteredContact;
	}

	public void setFilteredContact(List<Contact> filteredContact) {
		this.filteredContact = filteredContact;
	}

	public List<Ville> getVilles() {
		return villes;
	}

	public void setVilles(List<Ville> villes) {
		this.villes = villes;
	}

	public int getIdclient() {
		return idclient;
	}

	public void setIdclient(int idclient) {
		this.idclient = idclient;
	}

	public ModelService<Client> getManagerclient() {
		return managerclient;
	}

	public void setManagerclient(ModelService<Client> managerclient) {
		this.managerclient = managerclient;
	}

	public List<Contact> getFcontacts() {
		return fcontacts;
	}

	public void setFcontacts(List<Contact> fcontacts) {
		this.fcontacts = fcontacts;
	}
	

    
}
