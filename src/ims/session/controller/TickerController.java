package ims.session.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import ims.model.entities.Client;
import ims.model.entities.ImagesbyTicker;
import ims.model.entities.SuiviTicker;
import ims.model.entities.Ticker;
import ims.model.entities.TypePriorite;
import ims.model.entities.TypeTicker;
import ims.model.entities.Utilisateur;
import ims.model.entities.UtilisateurHelpDesk;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class TickerController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{JDBCManager}")
	private ModelServiceJDBC managerjdbc;
	
	@ManagedProperty(value="#{ClientManager}")
	private ModelService<Client> managerclient;
	
    @ManagedProperty(value="#{UserHelpDeskManager}")
    private ModelService<UtilisateurHelpDesk> manageruserhelpdesk;
    
    @ManagedProperty(value="#{userManager}")
    private ModelService<Utilisateur> manageruser;
    
    @ManagedProperty(value="#{TickerManager}")
    private ModelService<Ticker> manager;
    
    @ManagedProperty(value="#{SuiviTickerManager}")
    private ModelService<SuiviTicker> managersuivi;
    
    @ManagedProperty(value="#{TypeTickerManager}")
    private ModelService<TypeTicker> managertypeTicker;
    
    @ManagedProperty(value="#{TypePrioriteManager}")
    private ModelService<TypePriorite> managertypePriorite;
    
    @ManagedProperty(value="#{ImagesTickerManager}")
    private ModelService<ImagesbyTicker> managerimagesticker;
    
    private Ticker ticker=new Ticker();
    private Ticker tickertempo;
    private Ticker tickerdetail;
    private List<Ticker> listeTickers;
    
    private List<ImagesbyTicker> listeimages;
    private ImagesbyTicker imageticker;
    private StreamedContent content;
    
    private SuiviTicker suiviTicker=new SuiviTicker();
    private SuiviTicker suiviTickertempo;
    private List<SuiviTicker> listeSuiviTickers;

    private TypePriorite typepriorite=new TypePriorite();
    private TypePriorite typeprioriteTempo=new TypePriorite();
    private List<TypePriorite> listTypePriorite;
    
    private TypeTicker typeTicker=new TypeTicker();
    private TypeTicker typetickertempo=new TypeTicker();
    
    private List<TypeTicker> listTypeTicker;
    
    private String idtypeticker="";
    private String idpriorite="0";
    private String idprioriteedit="0";
    private String idclient="";
    private String iduserhelpdesk="";
    private String numticker="";
    
    private String numsuiviticker="";
	final String username="RABEH";
	final String pass="Pa$$w0rd";
	
	 private String destination="D:\\xampp\\htdocs\\HelpDesk\\ticker\\";
	private UploadedFile file;
    
    private List<UtilisateurHelpDesk> usershelpdesk;
    
    private String login=FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    
    
	public TickerController() {
		super();
	}
	
	
	public void DeleteTypeticker(){
		FacesMessage msg;
		listTypeTicker=null;
		try {
			boolean op=managerjdbc.deleteTypeTicker(typetickertempo);
			if(op==true){
				msg = new FacesMessage("Type Ticker Supprimer Avec Success");
		 		FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
				msg = new FacesMessage("Le type Déja liée à une Ticker");
		 		FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} catch (Exception e) {
			msg = new FacesMessage("Exception contacter Administration");
	 		FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	
	public void DeleteTypeProrite(){
		FacesMessage msg;
		listTypePriorite=null;
		try {
			boolean op=managerjdbc.deleteTypeProriter(typeprioriteTempo);
			if(op==true){
				msg = new FacesMessage("Type Prorité Supprimer Avec Success");
		 		FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
				msg = new FacesMessage("Le type Déja liée à une Ticker");
		 		FacesContext.getCurrentInstance().addMessage(null, msg);
			}
	 		
		} catch (Exception e) {
			msg = new FacesMessage("Exception contacter Administration");
	 		FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void onCelltypeticker(){
		FacesMessage msg;
		try {
			listTypeTicker=null;
			managerjdbc.update(typetickertempo);
			System.out.println("onCelltypeticker");
			msg = new FacesMessage("Type Ticker Modifier Avec Success");
	 		FacesContext.getCurrentInstance().addMessage(null, msg);
	 		
		} catch (Exception e) {
			
			msg = new FacesMessage("Exception contacter Administration");
		 		FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
    public void onCelltypepriorite(){
    	FacesMessage msg;
		try {
			listTypePriorite=null;
			managerjdbc.update(typeprioriteTempo);
			System.out.println("onCelltypepriorite");
			msg = new FacesMessage("Priorité Modifier Avec Success");
		 		FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			msg = new FacesMessage("Exception contacter Administration");
		 		FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
    public void joinfile(){
    	FacesMessage msg;
    	try {
    		System.out.println("join file");
        	ImagesbyTicker imagetick=new ImagesbyTicker();
        	System.out.println(getFile().getFileName());
        	imagetick.setCheminimage(getFile().getFileName());
        	imagetick.setTicker(tickertempo);
        	boolean op=managerimagesticker.insertObject(imagetick);
        	System.out.println("join file success");
        	if(op==true){
        		System.out.println("join file true");
        	if(getFile()!=null){  
    	        try {
    	            copyFile(getFile().getFileName(), getFile().getInputstream());
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }
    			}
        	listeimages=managerimagesticker.getObjects(tickertempo.getId());
   		 		msg = new FacesMessage("Image join avec success");
   		 		FacesContext.getCurrentInstance().addMessage(null, msg);
        	}
        	else{
   		 		msg = new FacesMessage("le Nom de fichier exsite deja modifier le nom de fichier");
   		 		FacesContext.getCurrentInstance().addMessage(null, msg);
        	}
		} catch (Exception e) {}
    }
    
    public void copyFile(String fileName, InputStream in) {
        try {
           
        	//System.out.println("New file !");
             
             OutputStream out = new FileOutputStream(new File(destination+ fileName));
           
             int read = 0;
             byte[] bytes = new byte[(int) getFile().getSize()];
           
             while ((read = in.read(bytes)) != -1) {
                 out.write(bytes, 0, read);
             }
           
             in.close();
             out.flush();
             out.close();
           
             //System.out.println("New file created!");
             } catch (IOException e) {
             System.out.println(e.getMessage());
             }
 }
	
	public void actualiser(){
		listeTickers=null;
		getNumticker();
		listeTickers=manager.getObject();
		for(int i=0;i<listeTickers.size();i++)
			System.out.println(listeTickers.get(i).getRef());
			
	}
	public String preparelist(){
		return "index?faces-redirect=true";
	}
	
	public String preparedetail(){
		listeimages=managerimagesticker.getObjects(tickertempo.getId());
		return "modifticker?faces-redirect=true";
	}
	
	public void preparedetaildialog(){

	}
	
	public void changeclientID(){
		System.out.println("test");
		usershelpdesk=manageruserhelpdesk.getObjects(Integer.parseInt(idclient));
		if(usershelpdesk!=null){
		for(int i=0;i<usershelpdesk.size();i++)
			System.out.println(usershelpdesk.get(i).getNom());
		}
	}
	
	public void preparelistdialog(){
		System.out.println("liste des suivi Ticker");
		listeSuiviTickers=null;
	}
	
	public void onCellEdit(){
		FacesMessage msg;
		try {
			listeTickers=null;
			if(tickertempo.isFermerticker()==false){
				if(tickertempo.getStatut().equals("Fermer") || tickertempo.getStatut().equals("Résolu")){
					tickertempo.setDateresolution(new Date());
				}
				else
					tickertempo.setDateresolution(null);
				if(!idprioriteedit.equals("0"))
					tickertempo.setPriorite(managertypePriorite.getObject(Integer.parseInt(idprioriteedit)));
				
				managerjdbc.updateTicker(tickertempo);
	            
	              envoimail("Application IMS Technology Helpdesk",
	             
	            		"Le Support IMS technology vous informer que le ticker "+tickertempo.getRef()+" est "+tickertempo.getStatut(),
	            		tickertempo.getUserhelpdesk().getEmail(),"Support_IMS@ims-technology.ma");
	             	
				msg = new FacesMessage("le ticker "+tickertempo.getRef()+" est "+tickertempo.getStatut());
				//msg = new FacesMessage("Ticker est modifier");
				FacesContext.getCurrentInstance().addMessage(null, msg);
	            //tickertempo=null;
			}
			else{
				msg = new FacesMessage("Ticker est Déja Fermer");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} catch (Exception e) {
			  msg = new FacesMessage("Exception Ticker "+e.getLocalizedMessage());
	            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
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
					return new PasswordAuthentication(username, pass);
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
            msg = new FacesMessage("Exception MAIL");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	
	}
	
	public void fermerTicker(){
		FacesMessage msg;
		listeTickers=null;
		try {
			if(tickertempo.isFermerticker()==false){
				boolean bool=managerjdbc.FermerTicker(tickertempo);
				if(bool==true){
					envoimail("Application IMS Technology Helpdesk (Fermeture de Ticker)", 
							"Le Support IMS Technology vous informer que le Ticker "+tickertempo.getRef()+"est Fermer Définitivement ", tickertempo.getUserhelpdesk().getEmail(),"Support_IMS@ims-technology.ma");
				}
				
	            msg = new FacesMessage("Ticker Fermer avec success");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
				   msg = new FacesMessage("Ticker est Déja Fermer");
		            FacesContext.getCurrentInstance().addMessage(null, msg);
			}
				

		} catch (Exception e) {
            msg = new FacesMessage("Exception");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void creerSuiviTicker(){
		FacesMessage msg;
		try {
			suiviTicker.setRefsuivi(numsuiviticker);
			suiviTicker.setTicker(tickertempo);
			suiviTicker.setUser(manageruser.getByName(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName()));
            if(suiviTicker.getStatu().equals("Résolu") || suiviTicker.getStatu().equals("Fermer"))
            	suiviTicker.setDatefinsuivi(new Date());
            managersuivi.insertObject(suiviTicker);
			msg = new FacesMessage("Suivi Ticker créer avec success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            suiviTicker=new SuiviTicker();
		} catch (Exception e) {
            msg = new FacesMessage("Exception");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void creeTypeTicker(){
		FacesMessage msgs;
		listTypeTicker=null;
		try {
			   System.out.println(typeTicker.getTypeticker());
			   managertypeTicker.insertObject(typeTicker);
               msgs = new FacesMessage("Type Ticker créer avec success");
               FacesContext.getCurrentInstance().addMessage(null, msgs);
               typeTicker=new TypeTicker();
		} catch (Exception e) {
			  msgs = new FacesMessage("Exception");
	            FacesContext.getCurrentInstance().addMessage(null, msgs);
		}
	}
	
	public void creerTypePriorite(){
		FacesMessage msg;
		listTypePriorite=null;
		try {
			managertypePriorite.insertObject(typepriorite);
            msg = new FacesMessage("Type Priorite créer avec success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            typepriorite=new TypePriorite();
		} catch (Exception e) {
            msg = new FacesMessage("Exception");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	public void creerTicker(){
		FacesMessage msg;
		listeTickers=null;
		try {
			System.out.println("Ticker "+ numticker);
			ticker.setRef(numticker);
			ticker.setTypeticker(managertypeTicker.getObject(Integer.parseInt(idtypeticker)));
			ticker.setStatut("en cours de traitement");
			ticker.setUserhelpdesk(manageruserhelpdesk.getObject(Integer.parseInt(iduserhelpdesk)));
			ticker.setUser(manageruser.getByName(login));
			ticker.setPriorite(managertypePriorite.getObject(Integer.parseInt(idpriorite)));
			boolean op=manager.insertObject(ticker);
		    if(op==true){
		    	envoimail("Application IMS Technology Helpdesk Création de Ticker", 
						"Le Support IMS Technology vous informer que le Ticker "+ticker.getRef()+" est créer Avec Success ", ticker.getUserhelpdesk().getEmail(),"Support_IMS@ims-technology.ma");
	            msg = new FacesMessage("Ticker créer avec success");
	            FacesContext.getCurrentInstance().addMessage(null, msg);	
		    }
		    else{
	            msg = new FacesMessage("Probleme de Création de Ticker la reference déja existe");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
		    }
            ticker=new Ticker();
            idclient="0";
            idpriorite="0";
            idtypeticker="0";
            iduserhelpdesk="0";
            
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

	public ModelService<Client> getManagerclient() {
		return managerclient;
	}

	public void setManagerclient(ModelService<Client> managerclient) {
		this.managerclient = managerclient;
	}

	public ModelService<UtilisateurHelpDesk> getManageruserhelpdesk() {
		return manageruserhelpdesk;
	}

	public void setManageruserhelpdesk(
			ModelService<UtilisateurHelpDesk> manageruserhelpdesk) {
		this.manageruserhelpdesk = manageruserhelpdesk;
	}

	public ModelService<Utilisateur> getManageruser() {
		return manageruser;
	}

	public void setManageruser(ModelService<Utilisateur> manageruser) {
		this.manageruser = manageruser;
	}

	public ModelService<Ticker> getManager() {
		return manager;
	}

	public void setManager(ModelService<Ticker> manager) {
		this.manager = manager;
	}

	public ModelService<SuiviTicker> getManagersuivi() {
		return managersuivi;
	}

	public void setManagersuivi(ModelService<SuiviTicker> managersuivi) {
		this.managersuivi = managersuivi;
	}

	public ModelService<TypeTicker> getManagertypeTicker() {
		return managertypeTicker;
	}

	public void setManagertypeTicker(ModelService<TypeTicker> managertypeTicker) {
		this.managertypeTicker = managertypeTicker;
	}

	public ModelService<TypePriorite> getManagertypePriorite() {
		return managertypePriorite;
	}

	public void setManagertypePriorite(
			ModelService<TypePriorite> managertypePriorite) {
		this.managertypePriorite = managertypePriorite;
	}

	public Ticker getTicker() {
		return ticker;
	}

	public void setTicker(Ticker ticker) {
		this.ticker = ticker;
	}

	public Ticker getTickertempo() {
		return tickertempo;
	}

	public void setTickertempo(Ticker tickertempo) {
		this.tickertempo = tickertempo;
	}

	public List<Ticker> getListeTickers() {
		if(listeTickers==null)
			listeTickers=manager.getObject();
		return listeTickers;
	}

	public void setListeTickers(List<Ticker> listeTickers) {
		this.listeTickers = listeTickers;
	}

	public SuiviTicker getSuiviTicker() {
		return suiviTicker;
	}

	public void setSuiviTicker(SuiviTicker suiviTicker) {
		this.suiviTicker = suiviTicker;
	}

	public SuiviTicker getSuiviTickertempo() {
		return suiviTickertempo;
	}

	public void setSuiviTickertempo(SuiviTicker suiviTickertempo) {
		this.suiviTickertempo = suiviTickertempo;
	}

	public List<SuiviTicker> getListeSuiviTickers() {
		if(listeSuiviTickers==null){
			if(tickertempo!=null){
				System.out.println("liste des suivi Ticker de Ticker "+tickertempo.getId());
				listeSuiviTickers=managersuivi.getObjects(tickertempo.getId());
			}

		}
			
		return listeSuiviTickers;
	}

	public void setListeSuiviTickers(List<SuiviTicker> listeSuiviTickers) {
		this.listeSuiviTickers = listeSuiviTickers;
	}

	public TypePriorite getTypepriorite() {
		return typepriorite;
	}

	public void setTypepriorite(TypePriorite typepriorite) {
		this.typepriorite = typepriorite;
	}

	public TypePriorite getTypeprioriteTempo() {
		return typeprioriteTempo;
	}

	public void setTypeprioriteTempo(TypePriorite typeprioriteTempo) {
		this.typeprioriteTempo = typeprioriteTempo;
	}

	public List<TypePriorite> getListTypePriorite() {
		if(listTypePriorite==null)
			listTypePriorite=managertypePriorite.getObject();
		return listTypePriorite;
	}

	public void setListTypePriorite(List<TypePriorite> listTypePriorite) {
		this.listTypePriorite = listTypePriorite;
	}

	public TypeTicker getTypeTicker() {
		return typeTicker;
	}

	public void setTypeTicker(TypeTicker typeTicker) {
		this.typeTicker = typeTicker;
	}

	public TypeTicker getTypetickertempo() {
		return typetickertempo;
	}

	public void setTypetickertempo(TypeTicker typetickertempo) {
		this.typetickertempo = typetickertempo;
	}

	public List<TypeTicker> getListTypeTicker() {
		if(listTypeTicker==null)
			listTypeTicker=managertypeTicker.getObject();
		return listTypeTicker;
	}

	public void setListTypeTicker(List<TypeTicker> listTypeTicker) {
		this.listTypeTicker = listTypeTicker;
	}

	public String getIdtypeticker() {
		return idtypeticker;
	}

	public void setIdtypeticker(String idtypeticker) {
		this.idtypeticker = idtypeticker;
	}

	public String getIdpriorite() {
		return idpriorite;
	}

	public void setIdpriorite(String idpriorite) {
		this.idpriorite = idpriorite;
	}

	public String getIdclient() {
		return idclient;
	}

	public void setIdclient(String idclient) {
		this.idclient = idclient;
	}

	public String getIduserhelpdesk() {
		return iduserhelpdesk;
	}

	public void setIduserhelpdesk(String iduserhelpdesk) {
		this.iduserhelpdesk = iduserhelpdesk;
	}

	public String getNumticker() {
    	Calendar cal = Calendar.getInstance();
    	String chaine=""+cal.get(Calendar.YEAR);
    	int lastnumbre=managerjdbc.getLastNumbre("Ticker","ref",chaine);
    	lastnumbre++;
    	String nombre=String.format("%05d",lastnumbre);
    	numticker="ticker"+nombre;
		return numticker;
	}

	public void setNumticker(String numticker) {
		this.numticker = numticker;
	}

	public List<UtilisateurHelpDesk> getUsershelpdesk() {
		return usershelpdesk;
	}

	public void setUsershelpdesk(List<UtilisateurHelpDesk> usershelpdesk) {
		this.usershelpdesk = usershelpdesk;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getIdprioriteedit() {
		return idprioriteedit;
	}

	public void setIdprioriteedit(String idprioriteedit) {
		this.idprioriteedit = idprioriteedit;
	}

	public String getNumsuiviticker() {
		try {
			Calendar cal = Calendar.getInstance();
	    	String chaine=""+cal.get(Calendar.YEAR);
	    	int lastnumbre=managerjdbc.getLastNumbre("SuiviTicker","refsuivi",chaine);
	    	
	    	lastnumbre++;
	    	String nombre=String.format("%05d",lastnumbre);
	    	numsuiviticker="suivi"+nombre;
		} catch (Exception e) {
			numsuiviticker="suivi00001";
		}

		return numsuiviticker;
	}

	public void setNumsuiviticker(String numsuiviticker) {
		this.numsuiviticker = numsuiviticker;
	}

	public Ticker getTickerdetail() {
		return tickerdetail;
	}

	public void setTickerdetail(Ticker tickerdetail) {
		this.tickerdetail = tickerdetail;
	}

	public ModelService<ImagesbyTicker> getManagerimagesticker() {
		return managerimagesticker;
	}

	public void setManagerimagesticker(
			ModelService<ImagesbyTicker> managerimagesticker) {
		this.managerimagesticker = managerimagesticker;
	}

	public List<ImagesbyTicker> getListeimages() {
		return listeimages;
	}

	public void setListeimages(List<ImagesbyTicker> listeimages) {
		this.listeimages = listeimages;
	}

	public ImagesbyTicker getImageticker() {
		return imageticker;
	}

	public void setImageticker(ImagesbyTicker imageticker) {
		this.imageticker = imageticker;
	}

	public StreamedContent getContent() {
		try {
			

			try {
				if(!imageticker.getCheminimage().equals(""))
					content = new DefaultStreamedContent(new FileInputStream(new File("D:\\xampp\\htdocs\\HelpDesk\\ticker\\"+imageticker.getCheminimage())),"application/xls",imageticker.getCheminimage());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return content;
			} catch (Exception e) {
				return null;
			}
	}

	public void setContent(StreamedContent content) {
		this.content = content;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getUsername() {
		return username;
	}

	public String getPass() {
		return pass;
	}
    
    

}
