/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;

import ims.model.entities.Client;
import ims.model.entities.Employee;
import ims.model.entities.LicenceApplication;
import ims.model.entities.Utilisateur;
import ims.model.entities.UtilisateurHelpDesk;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 *
 * @author rabeh
 */
@ManagedBean
@SessionScoped
public class UtilisateurControlleur implements Serializable{

	 /**
	* creator RABEH TARIK
    **/
	
	private static final long serialVersionUID = 1L;

	/**
     * Creates a new instance of UtilisateurControlleur
     */
   
    @ManagedProperty(value="#{userManager}")
    private ModelService<Utilisateur> manager;
    
	@ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
	
    @ManagedProperty(value="#{EmployeeManager}")
    private ModelService<Employee> manageremp;
    
	@ManagedProperty(value="#{ClientManager}")
	private ModelService<Client> managerclient;
	
    @ManagedProperty(value="#{LicenceAppsManager}")
    private ModelService<LicenceApplication> managerapplication;
    
    @ManagedProperty(value="#{UserHelpDeskManager}")
    private ModelService<UtilisateurHelpDesk> manageruserhelpdesk;
     
    private Utilisateur user=new Utilisateur();
    Utilisateur userconnecte;
    private Utilisateur usertempo=new Utilisateur();
    private List<Utilisateur> users;
    
    private UtilisateurHelpDesk userhelpdesk=new UtilisateurHelpDesk();
    private UtilisateurHelpDesk userhelpdesktempo=new UtilisateurHelpDesk();
    private List<UtilisateurHelpDesk> usershelpdesk;


    private String idemp="";
    private String idclient="";

    private String npassword="";
    
    private String login=FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    
    private String messagebienvenu="Bonjour "+login+"";
    
    public UtilisateurControlleur() {
       
    }

    
    @PostConstruct
    public void init(){
    	ExternalContext ec;
    	try {
    		
    		System.out.println("INIT UTILISATEUR ....");
    		System.out.println("LOGIN ==>"+login);
    		 userconnecte=manager.getByName(login);
    		System.out.println(userconnecte);
    		// LicenceAppsImplServiceLocator local=new LicenceAppsImplServiceLocator();
    		 //LicenceAppsImpl licence=local.getLicenceAppsImpl();
    		// if(licence.getlicence(userconnecte.getCle())==false){
    		if(userconnecte==null){	
    			System.out.println("Redirection.....");
    				ec = FacesContext.getCurrentInstance().getExternalContext();
				   // ec.invalidateSession();
    				System.out.print("Redirection ==>");
    				System.out.println(ec.getRequestContextPath() + "/login.xhtml");
    				ec.invalidateSession();
				    ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
    		 }
    		 
    	
		} catch (Exception e) {
			System.err.println("----------------ERROR UTILISATEUR CONTROLLER--------------");
		}
    	
  
    }
    
    public void update(){
    	System.out.println("UPDATE");
    	FacesMessage msg;
    
    	try {

    			users=null;
    			if(!idemp.equals(""))
    			 user.setEmp(manageremp.getObject(Integer.parseInt(idemp)));
            	
    			int op=managerjdbc.updateUser(user);
            	if(op==1){
        			msg = new FacesMessage("Utilisateur Modifier avec Success");
            	}
            	else
            		msg = new FacesMessage("Erreur Modification");
            	idemp="";
    		
                FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			msg = new FacesMessage("Exception");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}

    }
    
    public void updateHelpdesk(){
    	//FacesContext fc
    	System.out.println("UPDATE");
    	FacesMessage msg;
    	users=null;
    	try {
    			if(!idclient.equals(""))
    			 userhelpdesktempo.setClient(managerclient.getObject(Integer.parseInt(idclient)));
            	
    			boolean op=managerjdbc.updateUserHelpDesk(userhelpdesktempo);
            	if(op==true){
            		String contenu="Bonjour "+userhelpdesktempo.getNom()+", \n";
        			contenu+="Nous avons Modifier Votre compte sur l'applciation HelpDesk pour la gestion des ticker veuillez consulter http://www.ims-technology.ma/helpdesk les information son les suivant \n";
        			contenu+="LOGIN : "+userhelpdesktempo.getLogin()+" \n";
        			contenu+="PASSWORD : "+userhelpdesktempo.getPassword()+" \n";
        			contenu+="coodrialeemnt";
        			String to=userhelpdesktempo.getEmail();
        			envoimail("Modification de Compte utilisateur IMS technology HelpDesk",contenu, to, "");
        			msg = new FacesMessage("Utilisateur HelpDesk Modifier avec Success");
            	}
            		
            	else
            		msg = new FacesMessage("Erreur modification");
            	idemp="";
                FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			msg = new FacesMessage("Exception");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}

    }
    
    public void inserthelpdesk(){
    	FacesMessage msg;
    		try {
    			usershelpdesk=null;
    			
    			if(!idclient.equals(""))
    				userhelpdesk.setClient(managerclient.getObject(Integer.parseInt(idclient)));
        		
    			boolean op=manageruserhelpdesk.insertObject(userhelpdesk);
        		if(op==true){
        			String contenu="Bonjour "+userhelpdesk.getNom()+",\n";
        			contenu+="Nous avons créer votre compte sur l'applciation IMS Technology HelpDesk pour la gestion des ticker veuillez consulter http://www.ims-technology.ma/helpdesk les information son les suivant \n";
        			contenu+="LOGIN : "+userhelpdesk.getLogin()+" \n";
        			contenu+="PASSWORD :"+userhelpdesk.getPassword()+" \n";
        			contenu+="coodrialeemnt";
        			String to=userhelpdesk.getEmail();
        			envoimail("Création de Compte utilisateur sur IMS technology HelpDesk", contenu, to, "");
        			msg = new FacesMessage("Utilisateur HelpDesk est créer avec success");
        		}else
        			msg = new FacesMessage("Utilisateur HelpDesk n'est pas créer");
        			
        		idclient="";
        		userhelpdesk=new UtilisateurHelpDesk();
        		FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (Exception e) {
				msg = new FacesMessage("Exception");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
    		
    		
    }
    
    public void insert(){
    	FacesMessage msg;
    	try {
    		if(managerjdbc.getUsercle(user.getCle())==false){
    		users=null;
    		System.out.println("INSERT USER");
    		user.setEmp(manageremp.getObject(Integer.parseInt(idemp)));
    		boolean op=manager.insertObject(user);
    		if(op==true)
    			msg = new FacesMessage("Utilisateur est creer avec success");
    		else
    			msg = new FacesMessage("Utilisateur est Déja Existe Modifier login ou et la licence");
    	idemp="";
    	user=new Utilisateur();
		
	}else
		msg = new FacesMessage("Licence Déja Utiliser");
    		
        FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			msg = new FacesMessage("Exception");
			FacesContext.getCurrentInstance().addMessage(null, msg);
	    	idemp="";
	    	user=new Utilisateur();
		}
    	
    	
    }

    public void Delete(){
        FacesMessage msg;
        users=null;
        try {
        	System.out.println(user.getIduser());
        	managerjdbc.deleteUser(user);
      
       msg = new FacesMessage("Utilisateur est bien Supprimer");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            msg = new FacesMessage("Utilisateur est déja connecter");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
    
    
    public void Deletehelpdesk(){
        FacesMessage msg;
        usershelpdesk=null;
        try {
        	System.out.println(userhelpdesktempo.getId());
        	boolean bool=managerjdbc.deleteUserHelpdesk(userhelpdesktempo);
      if(bool==true){
    	  String contenu="Bonjour "+userhelpdesktempo.getNom()+",\n";
			contenu+="Nous avons Supprimer Votre compte sur l'applciation HelpDesk pour la gestion des ticker veuillez contacter le Support \n";
			contenu+="coodrialeemnt";
			String to=userhelpdesktempo.getEmail();
			envoimail("Supression de Compte utilisateur IMS technology HelpDesk", contenu, to, "");
			msg = new FacesMessage("Utilisateur HelpDesk Supprimer avec Success");
      }else
    	  msg = new FacesMessage("Utilisateur HelpDesk n'est pas Supprimer");
      
       FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            msg = new FacesMessage("Utilisateur est déja connecter");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
    

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public List<Utilisateur> getUsers() {
        if(users==null)
        	users=manager.getObject();
        return users;
    }

    public void setUsers(List<Utilisateur> users) {
        this.users = users;
    }

    public ModelService<Utilisateur> getManager() {
        return manager;
    }

    public void setManager(ModelService<Utilisateur> manager) {
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


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getMessagebienvenu() {
		return messagebienvenu;
	}


	public void setMessagebienvenu(String messagebienvenu) {
		this.messagebienvenu = messagebienvenu;
	}


	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}


	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}


	public String getNpassword() {
		return npassword;
	}


	public void setNpassword(String npassword) {
		this.npassword = npassword;
	}


	public ModelService<Employee> getManageremp() {
		return manageremp;
	}


	public void setManageremp(ModelService<Employee> manageremp) {
		this.manageremp = manageremp;
	}


	public String getIdemp() {
		return idemp;
	}


	public void setIdemp(String idemp) {
		this.idemp = idemp;
	}

	public UtilisateurHelpDesk getUserhelpdesk() {
		return userhelpdesk;
	}


	public void setUserhelpdesk(UtilisateurHelpDesk userhelpdesk) {
		this.userhelpdesk = userhelpdesk;
	}


	public List<UtilisateurHelpDesk> getUsershelpdesk() {
		if(usershelpdesk==null)
			usershelpdesk=manageruserhelpdesk.getObject();
		return usershelpdesk;
	}


	public void setUsershelpdesk(List<UtilisateurHelpDesk> usershelpdesk) {
		this.usershelpdesk = usershelpdesk;
	}


	public ModelService<UtilisateurHelpDesk> getManageruserhelpdesk() {
		return manageruserhelpdesk;
	}


	public void setManageruserhelpdesk(
			ModelService<UtilisateurHelpDesk> manageruserhelpdesk) {
		this.manageruserhelpdesk = manageruserhelpdesk;
	}


	public String getIdclient() {
		return idclient;
	}


	public void setIdclient(String idclient) {
		this.idclient = idclient;
	}


	public Utilisateur getUsertempo() {
		return usertempo;
	}


	public void setUsertempo(Utilisateur usertempo) {
		this.usertempo = usertempo;
	}


	public UtilisateurHelpDesk getUserhelpdesktempo() {
		return userhelpdesktempo;
	}


	public void setUserhelpdesktempo(UtilisateurHelpDesk userhelpdesktempo) {
		this.userhelpdesktempo = userhelpdesktempo;
	}


	public ModelService<Client> getManagerclient() {
		return managerclient;
	}


	public void setManagerclient(ModelService<Client> managerclient) {
		this.managerclient = managerclient;
	}


	public ModelService<LicenceApplication> getManagerapplication() {
		return managerapplication;
	}


	public void setManagerapplication(
			ModelService<LicenceApplication> managerapplication) {
		this.managerapplication = managerapplication;
	}


	public Utilisateur getUserconnecte() {
		return userconnecte;
	}


	public void setUserconnecte(Utilisateur userconnecte) {
		this.userconnecte = userconnecte;
	}
	
	
	public void envoimail(String objet,String contenu,String to,String cc){
		FacesMessage msg;
		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "false");
			props.put("mail.smtp.host", "192.168.30.251");
			props.put("mail.smtp.port", "25");
			
			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("", "");
				}
			  });
	 
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("HelpDesk@ims-technology.ma"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
				if(!cc.equals("")){
				message.setRecipients(Message.RecipientType.BCC,
						InternetAddress.parse(cc));
				}
				message.setSubject(objet);
				message.setText(contenu);
				Transport.send(message);
				System.out.println("TO "+to);
				System.out.println("CC "+cc);
			//
		} catch (Exception e) {
            msg = new FacesMessage("Exception MAIL "+e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	
	}
	
	
}

