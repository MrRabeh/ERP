package ims.session.controller;

import ims.model.entities.AvoirClient;
import ims.model.entities.Avoir_Articles;
import ims.model.entities.BonCommande;
import ims.model.entities.BonLivraison;
import ims.model.entities.Boncommande_Article;
import ims.model.entities.Contact;
import ims.model.entities.Facture;
import ims.model.entities.Facture_Article;
import ims.model.entities.Facture_Avoir;
import ims.model.entities.ObjetPrix;
import ims.model.entities.Offre;
import ims.model.entities.PrixArticlesOffre;
import ims.model.entities.Stock_Livraison;
import ims.model.entities.TypeFacture;
import ims.model.entities.TypePaiement;
import ims.model.entities.Ville;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



@ManagedBean
@SessionScoped
public class EmailController implements Serializable{

	
	   /**
		* creator RABEH TARIK
	    **/
	
	//les Services
    @ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
    
	@ManagedProperty(value="#{ContactManager}")
	private ModelService<Contact> managercontact;
	
    @ManagedProperty(value="#{BoncommandeArticleManager}")
    private ModelService<Boncommande_Article> managerarticle;
    
	@ManagedProperty(value="#{StockLivraisonManager}")
	private ModelService<Stock_Livraison> managerstocklivraison;
	
    @ManagedProperty(value="#{TypepaiementManager}")
    private ModelService<TypePaiement> managerTypePaiement;
    
 	@ManagedProperty(value="#{TypefactManager}")
 	private ModelService<TypeFacture> managerTypefacture;
 	
    @ManagedProperty(value="#{FactArticleManage}")
    private ModelService<Facture_Article> managerFactArticle;
    
	@ManagedProperty(value="#{FactureManager}")
	private ModelService<Facture> manager;
	
    @ManagedProperty(value="#{AvoirArticlesManager}")
    private ModelService<Avoir_Articles> managerAvoirarticles;
	
    @ManagedProperty(value="#{avoirclientManager}")
    private ModelService<AvoirClient> manageravoir;
	
	
	private static final long serialVersionUID = 1L;
	private String objet="Offre de Prix";
	private String objetcommande="Bon Commande";
	private String objetlivraison="Bon Livraison";
	private String objetfacture="Facture Client ";
	private String objetavoirs="Avoir Client ";
	private String rcpt;
	private String cc;
	private String contenumessage="\n\n\n----------------------\n Bonne Réception";
	
	private List<String> filtredcontactrcpt=new ArrayList<String>();
	private List<String> filtredcontactcc=new ArrayList<String>();
	private StreamedContent fileoffre;
	private StreamedContent filecommande;
	private StreamedContent filefacture;
	private StreamedContent filelivraison;
	private StreamedContent fileavoir;
	
	// Les Objets 
	private Offre offre;
	private BonCommande commande;
	private Facture facture;
	private Facture facttempo;
	private BonLivraison livraison;
	private AvoirClient avoirclient;

	
		public EmailController(){
		}
    
    	public String mailto(){
		try {
			createPdf("D:/offres/"+offre.getRef()+".pdf");
			return "email?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			return "index?faces-redirect=true";
		} 

    }
    
    	public String mailtocommande(){
		try {
			System.out.println("commande "+commande.getNumercommande());
			createPdfbc("D:/commandes/"+commande.getNumercommande()+".pdf");
			System.out.println("------------------------createPdfbc-----------------------");
			return "email?faces-redirect=true";
		} catch (Exception e) {
			e.getStackTrace();
			return "listBoncommande?faces-redirect=true";
		} 

    }
    
    	public String mailtofacturecontrat(){
		try {
			createPdffactcontrat("D:/factures/"+facture.getRef()+".pdf");
			return "email?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			return "FactureContrat?faces-redirect=true";
		} 

    }

		public String mailtofacture(){
		try {
			createPdffact("D:/factures/"+facture.getRef()+".pdf");
			return "email?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			return "index?faces-redirect=true";
		} 

    }

		public String mailtolivraison(){
		try {
			createPdflivraison("D:/livraisons/"+livraison.getRef()+".pdf");
			return "email?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			return "index?faces-redirect=true";
		} 

    }
	
		public String mailtoAvoir(){
		try {
			createPdfAvoir("D:/avoirs/"+avoirclient.getNumero_avoir()+".pdf");
			return "email?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			return "index?faces-redirect=true";
		} 

    }

		public List<String> completercpt(String query){
    	try {
			
    	List<Contact> contacts=null;
    	filtredcontactrcpt=new ArrayList<String>();
    	try {
			contacts=managercontact.getObject();
			
			 for (int i = 0; i < contacts.size(); i++) {
		            Contact skin = contacts.get(i);
		            if(skin.getEmail().toLowerCase().startsWith(query) && skin.getEmail()!=null) {
		            	filtredcontactrcpt.add(skin.getEmail());
		            }
		        }
			
		} catch (Exception e) {
			e.getStackTrace();
			filtredcontactrcpt=null;
		}
    	return filtredcontactrcpt;
		} catch (Exception e) {
			return null;
		}
    }
    
    	public List<String> completercc(String query){
    	List<Contact> contacts=null;
    	filtredcontactcc=new ArrayList<String>();
    	try {
			contacts=managercontact.getObject();
			
			 for (int i = 0; i < contacts.size(); i++) {
		            Contact skin = contacts.get(i);
		            if(skin.getEmail().toLowerCase().startsWith(query) && skin.getEmail()!=null) {
		            	filtredcontactcc.add(skin.getEmail());
		            }
		        }
			
		} catch (Exception e) {
			e.getStackTrace();
			filtredcontactcc=null;
		}
    	return filtredcontactcc;
    }
    	
		public void sendmailfact(){
			
		FacesMessage msg;
		final String username = "rabeh";
		final String password = "Pa$$w0rd";
		Properties props = new Properties();
		
		props.put("mail.smtp.auth", "false");
		//props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "192.168.30.251");
		props.put("mail.smtp.port", "25");
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
 
		try {
			msg = new FacesMessage("attend SVP...");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("guerrandou@ims-technology.ma"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(rcpt));
			message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(cc));
			message.setSubject(objetfacture);
			
			/*
			 * -----------------------
			 */
			File file=null;
			Multipart multipart = new MimeMultipart();
			
			BodyPart fileBodyPart = new MimeBodyPart();
			if(facture!=null){
				  file = new File("D:/factures/"+facture.getRef()+".pdf");
				  fileBodyPart.setFileName(facture.getRef()+".pdf");
			}
			BodyPart contenubody=new MimeBodyPart();
			
			DataSource source = new FileDataSource(file);
			fileBodyPart.setDataHandler(new DataHandler(source));
			
			
			contenubody.setText(contenumessage);
			
			multipart.addBodyPart(contenubody);
			multipart.addBodyPart(fileBodyPart);
			
			message.setContent(multipart);
			
			Transport.send(message);
 
			System.out.println("Message envoyer");
	        msg = new FacesMessage("Email est Bien Envoyer");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (MessagingException e) {
			System.out.println("Probleme =>"+e.getMessage());
	        msg = new FacesMessage("Exception Outlook=>"+e.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
    	
		public void sendmailbl(){
			
		FacesMessage msg;
		final String username = "rabeh";
		final String password = "Pa$$w0rd";
		Properties props = new Properties();
		
		props.put("mail.smtp.auth", "false");
		//props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "192.168.30.251");
		props.put("mail.smtp.port", "25");
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
 
		try {
			msg = new FacesMessage("attend SVP...");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("guerrandou@ims-technology.ma"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(rcpt));
			message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(cc));
			message.setSubject(objetlivraison);
			
			/*
			 * -----------------------
			 */
			File file=null;
			Multipart multipart = new MimeMultipart();
			
			BodyPart fileBodyPart = new MimeBodyPart();
			if(livraison!=null){
				  file = new File("D:/livraisons/"+livraison.getRef()+".pdf");
				  fileBodyPart.setFileName(livraison.getRef()+".pdf");
			}
			BodyPart contenubody=new MimeBodyPart();
			
			DataSource source = new FileDataSource(file);
			fileBodyPart.setDataHandler(new DataHandler(source));
			
			
			contenubody.setText(contenumessage);
			
			multipart.addBodyPart(contenubody);
			multipart.addBodyPart(fileBodyPart);
			
			message.setContent(multipart);
			
			Transport.send(message);
 
			System.out.println("Message envoyer");
	        msg = new FacesMessage("Email est Bien Envoyer");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (MessagingException e) {
			System.out.println("Probleme =>"+e.getMessage());
	        msg = new FacesMessage("Exception Outlook=>"+e.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
    	
    	
		public void sendmailbc(){
			
		FacesMessage msg;
		final String username = "rabeh";
		final String password = "Pa$$w0rd";
		Properties props = new Properties();
		
		props.put("mail.smtp.auth", "false");
		//props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "192.168.30.251");
		props.put("mail.smtp.port", "25");
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
 
		try {
			msg = new FacesMessage("attend SVP...");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("guerrandou@ims-technology.ma"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(rcpt));
			message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(cc));
			message.setSubject(objet);
			
			/*
			 * -----------------------
			 */
			File file=null;
			Multipart multipart = new MimeMultipart();
			
			BodyPart fileBodyPart = new MimeBodyPart();
			if(commande!=null){
				  file = new File("D:/commandes/"+commande.getNumercommande()+".pdf");
				  fileBodyPart.setFileName(commande.getNumercommande()+".pdf");
			}
			BodyPart contenubody=new MimeBodyPart();
			
			DataSource source = new FileDataSource(file);
			fileBodyPart.setDataHandler(new DataHandler(source));
			
			
			contenubody.setText(contenumessage);
			
			multipart.addBodyPart(contenubody);
			multipart.addBodyPart(fileBodyPart);
			
			message.setContent(multipart);
			
			Transport.send(message);
 
			System.out.println("Message envoyer");
	        msg = new FacesMessage("Email est Bien Envoyer");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (MessagingException e) {
			System.out.println("Probleme =>"+e.getMessage());
	        msg = new FacesMessage("Exception Outlook=>"+e.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
    	
	
		public void sendmail(){
		System.out.println("sendmail");
		System.out.println(rcpt);
		System.err.println(cc);
		FacesMessage msg;
		final String username = "rabeh";
		final String password = "Pa$$w0rd";
		Properties props = new Properties();
		
		props.put("mail.smtp.auth", "false");
		//props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "192.168.30.251");
		props.put("mail.smtp.port", "25");
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
 
		try {
			msg = new FacesMessage("attend SVP...");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("guerrandou@ims-technology.ma"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(rcpt));
			message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(cc));
			message.setSubject(objet);
			
			/*
			 * -----------------------
			 */
			File file=null;
			Multipart multipart = new MimeMultipart();
			
			BodyPart fileBodyPart = new MimeBodyPart();
			if(offre!=null){
				  file = new File("D:/offres/"+offre.getRef()+".pdf");
				  fileBodyPart.setFileName(offre.getRef()+".pdf");
			}
			BodyPart contenubody=new MimeBodyPart();
			
			DataSource source = new FileDataSource(file);
			fileBodyPart.setDataHandler(new DataHandler(source));
			
			
			contenubody.setText(contenumessage);
			
			multipart.addBodyPart(contenubody);
			multipart.addBodyPart(fileBodyPart);
			
			message.setContent(multipart);
			
			Transport.send(message);
 
			System.out.println("Message envoyer");
	        msg = new FacesMessage("Email est Bien Envoyer");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (MessagingException e) {
			System.out.println("Probleme =>"+e.getMessage());
	        msg = new FacesMessage("Exception Outlook=>"+e.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	 private void createPdflivraison(String dest) {
		   	
		 	DateFormat format=new SimpleDateFormat("dd/MM/yy"); 
		   	Paragraph saute=new Paragraph("\n");
	   
		   	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
		   	Document document = new Document(PageSize.A4);
	    try {
	    	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
	        document.open();
	        
        	Image img = Image.getInstance(webroot+"\\fondlogoancien.png"); 
            img.scaleAbsoluteHeight(PageSize.A4.getHeight());
            img.scaleAbsoluteWidth(PageSize.A4.getWidth());
            img.setAbsolutePosition(0, 0);
            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(img);
	        
	        Font ftext=new Font(Font.FontFamily.HELVETICA,8,Font.NORMAL,new BaseColor(Color.black));
		 	Font ftext10=new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL,new BaseColor(Color.black));
	        Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
	        document.setMargins(36, 36, 80, 20);
	        document.add(saute);
	        document.add(saute);
	        document.add(saute);
	        document.add(saute);
	        document.add(saute);
	        PdfPTable tableinfo = new PdfPTable(1);
	        PdfPCell l = new PdfPCell(new Phrase("Date BL:",ftextgra));
	        l.setHorizontalAlignment(Element.ALIGN_LEFT);
	        l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	        l.setBorder(Rectangle.NO_BORDER);
	        tableinfo.addCell(l);
	        PdfPCell lval = new PdfPCell(new Phrase(format.format(livraison.getDatebl()),ftext));
	        lval.setHorizontalAlignment(Element.ALIGN_CENTER);
	        tableinfo.addCell(lval);
	        
	        l = new PdfPCell(new Phrase("Client:",ftextgra));
	        l.setHorizontalAlignment(Element.ALIGN_LEFT);
	        l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	        l.setBorder(Rectangle.NO_BORDER);
	        tableinfo.addCell(l);
	        String societe="";
	        if(livraison.getClient().getSociete()!=null){
	        	societe=livraison.getClient().getSociete();
	        }
	        String adresse="";
	        if(livraison.getClient().getAdresse()!=null)
	        	adresse=livraison.getClient().getAdresse();
	        if(livraison.getClient().getVille().getVille()!=null)
	        	adresse+="\n"+livraison.getClient().getVille().getVille();
	        lval = new PdfPCell(new Phrase(societe,ftext));
	        lval.setHorizontalAlignment(Element.ALIGN_CENTER);
	        tableinfo.addCell(lval);
	        
	        lval = new PdfPCell(new Phrase(adresse,ftext));
	        lval.setHorizontalAlignment(Element.ALIGN_CENTER);
	        tableinfo.addCell(lval);
	        tableinfo.setHorizontalAlignment(tableinfo.ALIGN_RIGHT);
	        tableinfo.setWidthPercentage(30);
	        document.add(tableinfo);
	        Font fnumbl=new Font(Font.FontFamily.COURIER,15,Font.BOLD,new BaseColor(Color.black));
	        document.add(saute);
	        Paragraph Numerocommande=new Paragraph("Bon de Livraison N° : "+livraison.getRef(),fnumbl);
	        Numerocommande.setIndentationLeft(53);
	        document.add(Numerocommande);
	        document.add(saute);
	        PdfPCell c1;
	        PdfPTable table = new PdfPTable(3);
	        table.setWidths(new float[]{50,200,20});
	        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        table.setWidthPercentage(90);
	        c1 = new PdfPCell(new Phrase("Réference",ftextgra));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	        
	        table.addCell(c1);
	        
	        c1 = new PdfPCell(new Phrase("Désignation",ftextgra));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	        table.addCell(c1);
	        
	        c1 = new PdfPCell(new Phrase("Qté",ftextgra));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        c1.setVerticalAlignment(Element.ALIGN_CENTER);
	        c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	        table.addCell(c1);
	        table.setHeaderRows(1);
	        
	       
			List<Stock_Livraison> sotcklivraison=managerstocklivraison.getObjects(livraison.getId());
	        int taillebl=sotcklivraison.size();
	        for(int i=0;i<sotcklivraison.size();i++)
	        {
	        	
	        	if(i==20){
	        		 for(int j=0;j<writer.getPageNumber();j++)
	                	canvas.addImage(img);
	        	}
	        	
	        	c1 = new PdfPCell((new Phrase(sotcklivraison.get(i).getStock().getArticle().getRef(),ftext)));
	            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	            if(taillebl<10)
	            	c1.setPadding(7);
	            table.addCell(c1);
	            String numseri="";
	            if(sotcklivraison.get(i).getStock().getNumserie().equals(""))
	            	c1=new PdfPCell(new Phrase(sotcklivraison.get(i).getDesignationclient(),ftext));
	            else
	            {
	            	numseri="\nNuméro Serie : \n";
	            	String[] serie=sotcklivraison.get(i).getStock().getNumserie().split(",");
	            	for(int iserie=0;iserie<serie.length;iserie++){
	            		numseri+=serie[iserie]+"\n";
	            	}
	            	 c1=new PdfPCell(new Phrase(sotcklivraison.get(i).getDesignationclient()+"\n"+numseri,ftext));
	            	}
	            
	            if(taillebl<10){
	            	c1.setPaddingTop(7);
	            	c1.setPaddingBottom(7);
	            }
	            table.addCell(c1);
	            
	            c1 = new PdfPCell(new Phrase(sotcklivraison.get(i).getQte()+"",ftext));
	            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	            if(taillebl<10){
	            	c1.setPaddingTop(7);
	            	c1.setPaddingBottom(7);
	            }
	            table.addCell(c1);
	        }
	        document.add(table);

       
       	
	        Font ftest10gr=new Font(Font.FontFamily.HELVETICA,10,Font.BOLD,new BaseColor(Color.black));
	        Paragraph livrer=new Paragraph("Livré par :                                                                                 Receptioné par :",ftest10gr);
	        livrer.setAlignment(Element.ALIGN_LEFT);
	        livrer.setIndentationLeft(53);
	        document.add(livrer);
	        Paragraph livrerres=new Paragraph(livraison.getLivrepar()+"                                                                                                    "+livraison.getReceptionpar(),ftext10);
	        livrerres.setAlignment(Element.ALIGN_LEFT);
	        livrerres.setIndentationLeft(53);
	        document.add(livrerres);
	        
			 for(int j=0;j<writer.getPageNumber();j++)
	               	canvas.addImage(img);
			 
	        System.out.println("Imprimer***************FIN SUCCES");
	        document.addAuthor("IMS Technology");
	        document.addTitle("Bon Commande");
	        document.addSubject("Bon de livraison N°"+livraison.getRef());
	        document.addCreator("RABEH");
	        document.close();
	        FacesContext.getCurrentInstance().responseComplete();
	    } catch (DocumentException | IOException de) {
	        System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF");
	        document.close();
	        FacesContext.getCurrentInstance().responseComplete();
	    }
		}
	
	 public StreamedContent getFilelivraison() {
			try {
				filelivraison = new DefaultStreamedContent(new FileInputStream(new File("D:\\livraisons\\"+livraison.getRef()+".pdf")),"application/pdf",livraison.getRef()+".pdf");
					return filelivraison;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
	
	 public void createPdfbc(String dest){
    	

    	DateFormat format=new SimpleDateFormat("dd/MM/yyyy");
    	
    	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
        System.out.println("webroot");
    	System.out.println(webroot); 
    	System.out.println("webroot FIN");
    	Phrase saute=new Phrase("\n");
        Document document = new Document(PageSize.A4);
        try {
        	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();
            
        	Image img = Image.getInstance(webroot+"\\fondlogoancien.png"); 
            img.scaleAbsoluteHeight(PageSize.A4.getHeight());
            img.scaleAbsoluteWidth(PageSize.A4.getWidth());
            img.setAbsolutePosition(0, 0);
            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(img);
            
            Font ftext=new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL,new BaseColor(Color.black));
            Font ftextgra=new Font(Font.FontFamily.HELVETICA,10,Font.BOLD,new BaseColor(Color.black));
            document.setMargins(36, 36, 108, 180);
            document.add(saute);
            document.add(saute);
            document.add(saute);
            document.add(saute);
            document.add(saute);
            document.add(saute);
            
            PdfPTable tableinfo = new PdfPTable(1);
            PdfPCell l = new PdfPCell(new Phrase("Date BC:",ftextgra));
            l.setHorizontalAlignment(Element.ALIGN_LEFT);
            l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            tableinfo.addCell(l);
            PdfPCell lval;
            if(commande.getDatecommande()!=null){
            lval = new PdfPCell(new Phrase(""+format.format(commande.getDatecommande()),ftext));
            lval.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableinfo.addCell(lval);
            }
            l = new PdfPCell(new Phrase("Fournisseur:",ftextgra));
            l.setHorizontalAlignment(Element.ALIGN_LEFT);
            l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            tableinfo.addCell(l);
            if(commande.getFournisseur().getNomsociete()==null)
         	   commande.getFournisseur().setNomsociete(" ");
            lval = new PdfPCell(new Phrase(commande.getFournisseur().getNomsociete()+"\n"+commande.getFournisseur().getVille().getVille(),ftext));
            lval.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableinfo.addCell(lval);
            
            tableinfo.setHorizontalAlignment(tableinfo.ALIGN_RIGHT);
            tableinfo.setWidthPercentage(30);
            document.add(tableinfo);
            
            document.add(saute);
            Paragraph Numerocommande=new Paragraph("Bon de Commande N° : "+commande.getNumercommande(),new Font(Font.FontFamily.COURIER,15,Font.BOLD,new BaseColor(Color.black)));
            Numerocommande.setAlignment(Element.ALIGN_LEFT);
            Numerocommande.setIndentationLeft(70);
            document.add(Numerocommande);
            document.add(saute);
            
            PdfPTable tableoffre = new PdfPTable(3);
            tableoffre.setHeaderRows(1);
            tableoffre.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tableoffre.setWidthPercentage(90);
            PdfPCell c1 = new PdfPCell(new Phrase("Date OP",ftextgra));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            tableoffre.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("N°OP",ftextgra));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            tableoffre.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("Echeance",ftextgra));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            tableoffre.addCell(c1);

            if(commande.getDateoffre()!=null)
            c1 = new PdfPCell(new Phrase(format.format(commande.getDateoffre()),ftext));
            else
         	   c1 = new PdfPCell(new Phrase(" ")); 
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableoffre.addCell(c1);
            
            c1 = new PdfPCell(new Phrase(commande.getNumeroOffreFournisseur(),ftext));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableoffre.addCell(c1);
            
            c1 = new PdfPCell(new Phrase(commande.getEcheance(),ftext));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableoffre.addCell(c1);
            
            document.add(tableoffre);
            document.add(saute);
            PdfPTable table = new PdfPTable(5);
            table.setWidths(new float[]{30,90,20,30,30});
            table.setHeaderRows(1);
            table.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.setWidthPercentage(90);
            c1 = new PdfPCell(new Phrase("Référence",ftextgra));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("Désignation",ftextgra));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("Qté",ftextgra));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("P.U HT",ftextgra));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("P.T HT",ftextgra));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            table.addCell(c1);
            table.setHeaderRows(1);
            System.out.println("managerarticle prepare");
            List<Boncommande_Article> offreArticle=managerarticle.getObjects(commande.getId());
            System.out.println("managerarticle execute");
            DecimalFormat df = new DecimalFormat();
            df.setDecimalSeparatorAlwaysShown(true);
            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
            System.out.println("Bon commande Article :"+offreArticle.size());
            
            for(int i=0;i<writer.getPageNumber();i++)
            	canvas.addImage(img);
            
            for(int i=0;i<offreArticle.size();i++)
            {
         	PdfPCell cart = new PdfPCell(new Phrase(offreArticle.get(i).getArticle().getRef(),ftext));
       
         	cart.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cart.setVerticalAlignment(Element.ALIGN_CENTER);
         	table.addCell(cart);
         	cart = new PdfPCell(new Phrase(offreArticle.get(i).getDesignation(),ftext));
         	table.addCell(cart);
         	cart = new PdfPCell(new Phrase(""+offreArticle.get(i).getQte(),ftext));
         	cart.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cart.setVerticalAlignment(Element.ALIGN_CENTER);
         	table.addCell(cart);
         	cart = new PdfPCell(new Phrase(df.format(offreArticle.get(i).getPu()),ftext));
         	cart.setHorizontalAlignment(Element.ALIGN_RIGHT);
         	table.addCell(cart);
         	cart = new PdfPCell(new Phrase(df.format(offreArticle.get(i).getPt()),ftext));
         	cart.setHorizontalAlignment(Element.ALIGN_RIGHT);
         	table.addCell(cart);
         	if(i==10){
         		 for(int j=0;j<writer.getPageNumber();j++)
                 	canvas.addImage(img);
         	}
         	
        	if(i==20){
        		 for(int j=0;j<writer.getPageNumber();j++)
                	canvas.addImage(img);
        	}
         	
            }
            ObjetPrix cmdprix=managerjdbc.gettotalPrix(commande);
            if(commande.getTypepaiement().getTypepaiement().equals("MAD")){
 		           PdfPCell totalHT = new PdfPCell(new Phrase("Total HT",ftextgra));
 		           totalHT.setColspan(4);
 		           totalHT.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
 		           table.addCell(totalHT);
 		           
 		           PdfPCell restotalHT = new PdfPCell(new Phrase(df.format(Double.parseDouble(cmdprix.getTotalHT())),ftextgra));
 		           restotalHT.setHorizontalAlignment(Element.ALIGN_RIGHT);
 		           table.addCell(restotalHT);
 		           
 		           PdfPCell tva = new PdfPCell(new Phrase("TVA 20%",ftextgra));
 		           tva.setColspan(4);
 		           tva.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
 		           table.addCell(tva);
 		           
 		           PdfPCell restva = new PdfPCell(new Phrase(df.format(Double.parseDouble(cmdprix.getTva()))+"",ftextgra));
 		           restva.setHorizontalAlignment(Element.ALIGN_RIGHT);
 		           table.addCell(restva);
 		           PdfPCell totalTTC = new PdfPCell(new Phrase("Total TTC",ftextgra));
 		           totalTTC.setColspan(4);
 		           totalTTC.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
 		           table.addCell(totalTTC);
 		          
 		           cmdprix.setTotalTTC(cmdprix.getTotalTTC().replace(".", ","));
 		           
 		           String[] reel=cmdprix.getTotalTTC().split(",");

 		           PdfPCell restotalTTC;
 		           if(reel.length>1){
 		        	   char[] caracteres=reel[1].toCharArray();
 		        	   if(caracteres.length>1)
 		        		   restotalTTC = new PdfPCell(new Phrase(df.format(Double.parseDouble(reel[0])).split(",")[0]+","+caracteres[0]+""+caracteres[1],ftextgra));
 		        	   else
 		        		   restotalTTC = new PdfPCell(new Phrase(df.format(Double.parseDouble(reel[0])).split(",")[0]+","+caracteres[0]+"0",ftextgra));
 		           }
 		        	 
 		           else
 		        	  restotalTTC = new PdfPCell(new Phrase(df.format(Double.parseDouble(cmdprix.getTotalTTC())),ftextgra));
 		           restotalTTC.setHorizontalAlignment(Element.ALIGN_RIGHT);
 		           table.addCell(restotalTTC);
 		           if(commande.getFraitransport()>0){
 			           PdfPCell fraitranspot = new PdfPCell(new Phrase("Frais d'enregistrement de transit",ftextgra));
 			           fraitranspot.setColspan(4);
 			           fraitranspot.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
 			           table.addCell(fraitranspot);
 			           PdfPCell resfrai = new PdfPCell(new Phrase(df.format(commande.getFraitransport())+"",ftextgra));
 			           resfrai.setHorizontalAlignment(Element.ALIGN_RIGHT);
 			           table.addCell(resfrai);
 			           PdfPCell totalnet = new PdfPCell(new Phrase("TOTAL NET 1",ftextgra));
 			           totalnet.setColspan(4);
 			           totalnet.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
 			           table.addCell(totalnet);
 			           PdfPCell restotalNET = new PdfPCell(new Phrase(df.format(commande.getTotalnet())+"",ftextgra));
 			           restotalNET.setHorizontalAlignment(Element.ALIGN_RIGHT);
 			           table.addCell(restotalNET);
 		           	}
            }else{
 	           PdfPCell totalHT = new PdfPCell(new Phrase("Total en "+commande.getTypepaiement().getTypepaiement().toLowerCase(),ftextgra));
 	           totalHT.setColspan(4);
 	           totalHT.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
 	           table.addCell(totalHT);
 	           
 	           PdfPCell restotalHT = new PdfPCell(new Phrase(df.format(commande.getTotalht()),ftextgra));
 	           restotalHT.setHorizontalAlignment(Element.ALIGN_RIGHT);
 	           table.addCell(restotalHT);
 	           if(commande.getFraitransport()>0){
 		           PdfPCell fraitranspot = new PdfPCell(new Phrase("Frais d'enregistrement de transit et de transport ",ftextgra));
 		           fraitranspot.setColspan(4);
 		           fraitranspot.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
 		           table.addCell(fraitranspot);
 		           PdfPCell resfrai = new PdfPCell(new Phrase(df.format(commande.getFraitransport())+"",ftextgra));
 		           resfrai.setHorizontalAlignment(Element.ALIGN_RIGHT);
 		           table.addCell(resfrai);
 		           PdfPCell totalnet = new PdfPCell(new Phrase("TOTAL NET 1 en "+commande.getTypepaiement().getTypepaiement().toLowerCase(),ftextgra));
 		           totalnet.setColspan(4);
 		           totalnet.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
 		           table.addCell(totalnet);
 		           PdfPCell restotalNET = new PdfPCell(new Phrase(df.format(commande.getTotalnet())+"",ftextgra));
 		           restotalNET.setHorizontalAlignment(Element.ALIGN_RIGHT);
 		           table.addCell(restotalNET);
 	           	}
            }
            
            document.add(table);
            for(int i=0;i<writer.getPageNumber();i++)
            	canvas.addImage(img);
            System.out.println("List Offre");
            if(commande.getListeoffres()!=null){
         	   if(commande.getListeoffres().size()>0){
 		           Paragraph pie=new Paragraph();
 		           
 		           
 		           pie.add(new Phrase("Client Final :",ftextgra));
 		           pie.add(new Phrase(commande.getClient().getSociete()));
 		           if(commande.getClient().getContactprincipal()!=null)
 		           {
 		        	   pie.add(new Phrase("\nContact       :",ftextgra));
 		        	   pie.add(new Phrase(commande.getClient().getContactprincipal().getNom()+" "+commande.getClient().getContactprincipal().getPrenom()));
 		           }
 		           if(commande.getClient().getTel()!=null)
 		           {
 		        	   pie.add(new Phrase("\nTel               :",ftextgra));
 		        	   pie.add(new Phrase(commande.getClient().getTel()));
 		           }
 		           if(commande.getClient().getContactprincipal()!=null)
 		           {
 		        	   pie.add(new Phrase("\nmail             :",ftextgra));
 		        	   pie.add(new Phrase(commande.getClient().getContactprincipal().getEmail()));
 		           }
 		           
 		           pie.add(new Phrase("\nAdresse      :",ftextgra));
 		           pie.add(new Phrase(commande.getClient().getAdresse()));
 		           pie.setIndentationLeft(53);
 		           document.add(saute);
 		           document.add(saute);
 		           document.add(pie);
         	   }
            }
            System.out.println("Imprimer***************FIN SUCCES");
            document.close();

        } catch (DocumentException | IOException de) {
        	System.out.println("----------------DocumentException | IOException EXCEPTION----------------------------");
        	de.getStackTrace();
            document.close();
        }
      }
	
	 public StreamedContent getFilecommande() {
		
		try {
			filecommande = new DefaultStreamedContent(new FileInputStream(new File("D:\\commandes\\"+commande.getNumercommande()+".pdf")),"application/pdf",commande.getNumercommande()+".pdf");
        return filecommande;
		} catch (FileNotFoundException e) {
			System.out.println("----------------StreamedContent EXCEPTION----------------------------");
			e.printStackTrace();
			return null;
		}
	}

 	 public void createPdf(String dest){
    	
    	System.out.println("Imprimer*************** SUCCES");
    	DateFormat format=new SimpleDateFormat("dd/MM/yyyy");
    	
    	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
        System.out.println("webroot");
    	System.out.println(webroot); 
    	System.out.println("webroot FIN");
    	Phrase saute=new Phrase("\n");
        Document document = new Document(PageSize.A4);


        
        try {
        	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();
        	System.out.println("ID===>"+offre.getId());
            
        	Image img = Image.getInstance(webroot+"\\fondOffre.png"); 
            img.scaleAbsoluteHeight(PageSize.A4.getHeight());
            img.scaleAbsoluteWidth(PageSize.A4.getWidth());
            img.setAbsolutePosition(0, 0);
            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(img);
            System.out.println("Nombre apres le chargement ======>"+writer.getPageNumber());
            
            Font ftextpetite=new Font(Font.FontFamily.HELVETICA,7,Font.NORMAL,new BaseColor(Color.black));
            Font ftext=new Font(Font.FontFamily.HELVETICA,9,Font.NORMAL,new BaseColor(Color.black));
            Font ftextgra=new Font(Font.FontFamily.HELVETICA,10,Font.BOLD,new BaseColor(Color.black));
            Font forange=new Font(Font.FontFamily.HELVETICA,12,Font.UNDERLINE,new BaseColor(247, 150, 70));
            //document.setMargins(36, 72, 108, 180);
            document.setMargins(36, 36, 108, 180);
            System.out.println("Imprimer***************2 SUCCES");
            document.add(saute);
            document.add(saute);
            document.add(saute);
            document.add(saute);
            Paragraph villedate=new Paragraph("Casablanca, le "+format.format(offre.getDateoffre()),ftext);
            villedate.setIndentationLeft(300);
            document.add(villedate);
            document.add(saute);
            document.add(saute);
            document.add(saute);
            Paragraph client=new Paragraph(""+offre.getClient().getSociete(),ftextgra);
            client.setIndentationLeft(300);
            document.add(client);
            document.add(saute);
            Phrase textref=new Phrase(offre.getRef(),ftextgra);
            Phrase lbref=new Phrase  ("N/Réf     : ",ftext);
            Paragraph ref=new Paragraph(lbref);
            ref.add(textref);
            ref.setIndentationLeft(53);
            document.add(ref);
            Phrase textobjet=new Phrase("Proposition Financière",ftextgra);
            Phrase lbobjet=new Phrase("Objet     : ",ftext);
            Paragraph objet=new Paragraph(lbobjet);
            objet.add(textobjet);
            objet.setIndentationLeft(53);
            document.add(objet);
            document.add(saute);
            Paragraph presentation;
            System.out.println(offre.getTypeoffre().getType().toLowerCase());
            if(offre.getTypeoffre().getType().toLowerCase().equals("prestation")){
            	presentation=new Paragraph("Monsieur,\n\nFaisant suite à  votre demande nous vous communiquons notre meilleure offre de prix pour la prestation des services suivants:",ftext);
            }
            else if(offre.getTypeoffre().getType().toLowerCase().equals("renouvellement")){
            	presentation=new Paragraph("Monsieur,\n\nFaisant suite à votre demande nous vous communiquons notre meilleure offre de prix pour le renouvellement des produits suivants :",ftext);
            }
            else if(offre.getTypeoffre().getType().toLowerCase().equals("formation")){
            	presentation=new Paragraph("Monsieur,\n\nNous avons le plaisir de vous faire parvenir notre offre de prix concernant la formation suivante :",ftext);
            }
            else if(offre.getTypeoffre().getType().toLowerCase().equals("certification")){
            	presentation=new Paragraph("Monsieur,\n\nNous avons le plaisir de vous faire parvenir notre offre de prix pour les certifications suivantes :",ftext);
            }
            else
            	presentation=new Paragraph("Monsieur,\n\nFaisant suite à  votre demande nous vous communiquons notre meilleure offre de prix pour l'acquisition des produits suivants:",ftext);
            
            presentation.setIndentationLeft(53);
            document.add(presentation);
            document.add(saute);
            System.out.println("Imprimer***************3 SUCCES");
            
            
            DecimalFormat df = new DecimalFormat();
            df.setDecimalSeparatorAlwaysShown(true);
            PdfPTable table;
            	table= new PdfPTable(5);
            	table.setWidths(new float[]{55,180,25,40,50});
            
            table.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        table.setWidthPercentage(90);
	        
            String titre="";
            titre="Référence";
            PdfPCell c1 = new PdfPCell(new Phrase(titre,ftextgra));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            table.addCell(c1);
            
            titre="Désignation";
            
            c1 = new PdfPCell(new Phrase(titre,ftextgra));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            table.addCell(c1);
            

            titre="Qté";
            c1 = new PdfPCell(new Phrase(titre,ftextgra));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("P.U HT",ftextgra));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("P.T HT",ftextgra));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            table.addCell(c1);
            
            table.setHeaderRows(1);
            
            System.out.println(" Table");
            
            if(offre.getDescription()!=null){
            	if(!offre.getDescription().equals("")){
		            c1 = new PdfPCell(new Phrase(offre.getDescription(),ftext));
		            c1.setColspan(5);
		            c1.setBackgroundColor(new BaseColor(Color.decode("#D9D9D9")));
		            table.addCell(c1);
            	}
            }
               //List<Offre_Article> offreArticle=managerarticle.getObjects(offre.getId());
               List<PrixArticlesOffre> str_Sring=managerjdbc.getArticleOffre(offre);
            if(str_Sring!=null){
	            	canvas.addImage(img);
            	int nbrtest= str_Sring.size();
	               for(int i=0;i<str_Sring.size();i++)
	            {
	   	           
	   	            for(int j=0;j<writer.getPageNumber();j++)
		            	canvas.addImage(img);
	            	
	            	System.out.println("offreArticle");
	            		if(!str_Sring.get(i).getTypeoffre().equals("contrat")){
		            		
		            		
		    
		            		c1 = new PdfPCell(new Phrase(str_Sring.get(i).getReference(),ftext));
		                    
		                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		                    c1.setVerticalAlignment(100);
		                    if(nbrtest<10)
				            	c1.setPadding(15);
		                    
				            table.addCell(c1);
				            
				            if(str_Sring.get(i).getDesignation()==null)
				            	str_Sring.get(i).setDesignation(" ");
				            Phrase para;
				            if(str_Sring.get(i).getDesignation().length()>1000)
				            		para=new Phrase(str_Sring.get(i).getDesignation(),ftextpetite);
				            	else
				            		para=new Phrase(str_Sring.get(i).getDesignation(),ftext);
				      
				            	c1 = new PdfPCell(para);
				            	if(nbrtest<10)
				            	c1.setPadding(15);
				            table.addCell(c1);
				            System.out.println("Forfait ....");
				            c1 = new PdfPCell(new Phrase(str_Sring.get(i).getChaineqantite(),ftext));
				            
				            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
					           if(nbrtest<10){
					        	   c1.setPaddingTop(15);
					            	c1.setPaddingBottom(15);
					           }
				            table.addCell(c1);
				            
				        	String chaine=str_Sring.get(i).getPU_HT2().replace(".", ",");
			            	String[] nombre=chaine.split(",");
			            	if(nombre[1]!=null){
			            		char[] virgule=nombre[1].toCharArray();
			            		if(virgule.length>2){
			            			int n=Integer.parseInt(""+virgule[2]);
			            			if(n<5)
			            				c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+""+virgule[1],ftext));
			            			else{
			            				int v2=Integer.parseInt(""+virgule[1]);
			            				v2++;
			            				if(v2==10){
			            					int reste=Integer.parseInt(virgule[0]+""+virgule[1]);
			            					reste++;
			            					c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+reste,ftext));
			            				}else
			            					c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+""+v2,ftext));
			            			}
			            		}
			            		else if(virgule.length==2)
			            			c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+""+virgule[1],ftext));
			            		else
			            			c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+"0",ftext));
			            	}
			            	else{
			            		 c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(chaine)),ftext));
			            	}
				       	    
				            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           if(nbrtest<10){
					        	   c1.setPaddingTop(15);
					            	c1.setPaddingBottom(15);
					           }
	
				            table.addCell(c1);
				            
				             chaine=str_Sring.get(i).getPT_HT2().replace(".", ",");
				             nombre=chaine.split(",");
			            	if(nombre[1]!=null){
			            		char[] virgule=nombre[1].toCharArray();
			            		if(virgule.length>2){
			            			int n=Integer.parseInt(""+virgule[2]);
			            			if(n<5)
			            				c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+""+virgule[1],ftext));
			            			else{
			            				int v2=Integer.parseInt(""+virgule[1]);
			            				v2++;
			            				c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+""+v2,ftext));
			            			}
			            		}
			            		else if(virgule.length==2)
			            			c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+""+virgule[1],ftext));
			            		else
			            			c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+"0",ftext));
			            	}
			            	else{
			            		 c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(chaine)),ftext));
			            	}
				            
				            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           if(nbrtest<10){
					        	   c1.setPaddingTop(15);
					            	c1.setPaddingBottom(15);
					           }
				            table.addCell(c1);
	            	}
	            }
            }
            for(int i=0;i<writer.getPageNumber();i++)
            	canvas.addImage(img);
            
            if(offre.getTypepaiement().getTypepaiement().equals("MAD")){
            	
            	  ObjetPrix objettotal=managerjdbc.gettotalPrix(offre);
            	
                PdfPCell totalHT = new PdfPCell(new Phrase("Total HT",ftextgra));
                	totalHT.setColspan(4);
                
                totalHT.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
                table.addCell(totalHT);
                
                String chaine=objettotal.getTotalHT().replace(".", ",");
                String[] nombre = chaine.split(",");
                if(nombre[1]!=null){
                	char[] virgule=nombre[1].toCharArray();
            		if(virgule.length>2){
            			int n=Integer.parseInt(""+virgule[2]);
            			if(n<5)
            				c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+""+virgule[1],ftextgra));
            			else{
            				int v2=Integer.parseInt(""+virgule[1]);
            				v2++;
            				c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+""+v2,ftextgra));
            			}
            		}
            		else if(virgule.length==2)
            			c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+""+virgule[1],ftextgra));
                	else
                		c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+"0",ftextgra));
                }
                else
                	c1 = new PdfPCell(new Phrase(chaine+",00",ftextgra));

    	        
                c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(c1);
                
                PdfPCell tva = new PdfPCell(new Phrase("TVA 20%",ftextgra));
                	tva.setColspan(4);
                tva.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
                table.addCell(tva); 
                
                 chaine=objettotal.getTva().replace(".", ",");
                 nombre=chaine.split(",");
                if(nombre[1]!=null){
                	char[] virgule=nombre[1].toCharArray();
                	if(virgule.length>1)
                		c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+""+virgule[1],ftextgra));
                	else
                		c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+"0",ftextgra));
                }
                else
                	c1 = new PdfPCell(new Phrase(chaine+",00",ftextgra));

    	        
                c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                c1.setVerticalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                 
                PdfPCell totalTTC = new PdfPCell(new Phrase("Total DH TTC",ftextgra));
                
                	totalTTC.setColspan(4);
                
                totalTTC.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
                table.addCell(totalTTC);
                
              
                chaine=objettotal.getTotalTTC().replace(".", ",");
                nombre=chaine.split(",");
                if(nombre[1]!=null){
                	char[] virgule=nombre[1].toCharArray();
                	if(virgule.length>1)
                		c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+""+virgule[1],ftextgra));
                	else
                		c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+"0",ftextgra));
                }
                else
                	c1 = new PdfPCell(new Phrase(chaine+",00",ftextgra));
                c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                c1.setVerticalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
            }else{
                PdfPCell totalHT = new PdfPCell(new Phrase("Total en "+offre.getTypepaiement().getTypepaiement().toLowerCase(),ftextgra));
                	totalHT.setColspan(4);
                
                totalHT.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
                table.addCell(totalHT);

    	        	c1 = new PdfPCell(new Phrase(df.format(offre.getTotalHT()),ftextgra));

    	        
                c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(c1);
            }
            
            document.add(table);
            document.add(saute);

            Paragraph pie=new Paragraph();
            
            for(int i=0;i<writer.getPageNumber();i++)
            	canvas.addImage(img);
            
            if(offre.getDisponibilite()!=null){
            	if(!offre.getDisponibilite().equals("")){
		            pie.add(new Paragraph("Disponibilité",forange));
		            pie.add(new Paragraph(offre.getDisponibilite(),ftext));
            	}
            }
            String[] tab;
            if(offre.getValiditeoffre()!=null){
            	if(!offre.getValiditeoffre().equals("")){
            	pie.add(new Paragraph("Validité de l'offre",forange));
	            tab=offre.getValiditeoffre().split("\n");
	            for(int i=0;i<tab.length;i++)
	            	pie.add(new Paragraph(tab[i],ftext));
            	}
            }
            for(int i=0;i<writer.getPageNumber();i++)
            	canvas.addImage(img);
            if(offre.getModalitepaiment()!=null){
            	if(!offre.getModalitepaiment().equals("")){
	            	   pie.add(new Paragraph("Modalité de paiement",forange));
	   	            tab=offre.getModalitepaiment().split("\n");
	   	            for(int i=0;i<tab.length;i++)
	   	            	pie.add(new Paragraph(""+tab[i],ftext));
            	}
            }
            pie.add(saute);
            for(int i=0;i<writer.getPageNumber();i++)
            	canvas.addImage(img);
            pie.add(new Phrase("Nous restons à votre entière disposition pour toute information complémentaire.\nDans l’attente, veuillez accepter, Monsieur, nos cordiales salutations",ftext));
            pie.setIndentationLeft(53);
            document.add(pie);
            System.out.println("-----------------------------------------------------------");
            System.out.println("Nombre des page:"+writer.getPageNumber());
            for(int i=0;i<writer.getPageNumber();i++)
            	canvas.addImage(img);

            System.out.println("-----------------------------------------------------------");
     
            System.out.println("Imprimer***************FIN SUCCES");
            document.addAuthor("IMS Technology");
            document.addTitle("Bon Commande");
            document.addSubject("Offre N°"+offre.getRef());
            document.addCreator("RABEH");
            

            document.close();

        } catch (DocumentException | IOException de) {
            System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF");
            document.close();
        }
      }
 	
 	 public StreamedContent getFileoffre() {
			try {
				fileoffre = new DefaultStreamedContent(new FileInputStream(new File("D:\\offres\\"+offre.getRef()+".pdf")),"application/pdf",offre.getRef()+".pdf");
				return fileoffre;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
	}
	
 	 private void createPdffact(String dest) {
 		
    	DateFormat format=new SimpleDateFormat("dd/MM/yyyy");
    	
    	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
       
    	Phrase saute=new Phrase("\n");
        Document document = new Document(PageSize.A4);
        Font ftext=new Font(Font.FontFamily.HELVETICA,9,Font.NORMAL,new BaseColor(Color.black));
 	   Font ftext10=new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL,new BaseColor(Color.black));
         Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
         Font forange=new Font(Font.FontFamily.HELVETICA,10,Font.UNDERLINE,new BaseColor(Color.black));
        

         try {
        	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();
        	
        	Image img = Image.getInstance(webroot+"\\fondlogoancien.png"); 
            img.scaleAbsoluteHeight(PageSize.A4.getHeight());
            img.scaleAbsoluteWidth(PageSize.A4.getWidth());
            img.setAbsolutePosition(0, 0);
            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(img);
            
            document.setMargins(36, 36, 108, 180);
	           document.add(saute);
	  
	           
	           
	           DecimalFormat df = new DecimalFormat();
	           df.setDecimalSeparatorAlwaysShown(true);
	           df.setMaximumFractionDigits(2);
	           df.setMinimumFractionDigits(2);
	           facture=manager.getObject(facture.getId());
	           
	           if(facture.getClient().getSociete()==null)
	        	   facture.getClient().setSociete("");
	           
	           if(facture.getClient().getAdresse()==null)
	        	   facture.getClient().setAdresse("");
	           
	           if(facture.getClient().getVille()==null)
	        	   {
	        	   facture.getClient().setVille(new Ville());
	        	   facture.getClient().getVille().setVille("");
	        	   }
	         
	           PdfPTable tableinfo = new PdfPTable(1);
	           
	           PdfPCell l = new PdfPCell(new Phrase("Date:",ftext));
	           l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           l.setBorder(Rectangle.NO_BORDER);
	           tableinfo.addCell(l);
	           
	           l = new PdfPCell(new Phrase(format.format(facture.getDatefacture()),ftext));
	           l.setHorizontalAlignment(Element.ALIGN_CENTER);
	           tableinfo.addCell(l);
	           
	           l = new PdfPCell(new Phrase("Client:"));
	           l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           l.setBorder(Rectangle.NO_BORDER);
	           tableinfo.addCell(l);
	          
	           l = new PdfPCell(new Phrase(""+facture.getClient().getSociete(),ftext));
	           l.setHorizontalAlignment(Element.ALIGN_CENTER);
	           tableinfo.addCell(l);
	           
	           l = new PdfPCell(new Phrase(""+facture.getClient().getAdresse(),ftext));
	           l.setHorizontalAlignment(Element.ALIGN_CENTER);
	           tableinfo.addCell(l);
	           
	           l = new PdfPCell(new Phrase(""+facture.getClient().getVille().getVille(),ftext));
	           l.setHorizontalAlignment(Element.ALIGN_CENTER);
	           tableinfo.addCell(l);
	          
	           tableinfo.setHorizontalAlignment(tableinfo.ALIGN_RIGHT);
	           tableinfo.setWidthPercentage(30);
	           document.add(tableinfo);
	           document.add(saute);
	           Font ftextgrafact=new Font(Font.FontFamily.HELVETICA,15,Font.BOLD,new BaseColor(Color.black));
	           Paragraph Numerocommande=new Paragraph("Facture N° : "+facture.getRef(),ftextgrafact);
	           Numerocommande.setAlignment(Element.ALIGN_LEFT);
	           Numerocommande.setAlignment(Element.ALIGN_TOP);
	           Numerocommande.setIndentationLeft(53);
	           document.add(Numerocommande);
	           
	           document.add(saute);
	          
	           PdfPTable tableoffre = new PdfPTable(3);
	           tableoffre.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           tableoffre.setWidthPercentage(90);
	           PdfPCell c1 = new PdfPCell(new Phrase("Date BC",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           tableoffre.addCell(c1);
	           c1 = new PdfPCell(new Phrase("Numéro BC",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           tableoffre.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Mode de Réglement",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           tableoffre.addCell(c1);

	           	if(facture.getNumeroBCClient()==null)
	           		facture.setNumeroBCClient(" ");
	           	if(facture.getConditiondepayment()==null)
	           		facture.setNumeroBCClient(" ");
	           	if(facture.getDateboncommande()!=null)
	           		c1 = new PdfPCell(new Phrase(""+format.format(facture.getDateboncommande()),ftext));
	           	else
	           		c1 = new PdfPCell(new Phrase("-",ftext));
	           	
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           tableoffre.addCell(c1);
		           
		           c1 = new PdfPCell(new Phrase(""+facture.getNumeroBCClient(),ftext));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           tableoffre.addCell(c1);
		           
		           c1 = new PdfPCell(new Phrase(""+facture.getConditiondepayment(),ftext));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           tableoffre.addCell(c1);
	           document.add(tableoffre);
	           document.add(saute);
	           List<Facture_Article> offreArticle=managerFactArticle.getObjects(facture.getId());
	           int nbrtest=offreArticle.size();
	           boolean trouve=false;
	           if(offreArticle!=null){
	        	  for(int i=0;i<offreArticle.size();i++)
	        	  {
	        		  if(offreArticle.get(i).getCoefficient()!=1)
	        			  trouve=true;
	        	  }
	           }

	           PdfPTable table;
	           if(trouve==true){
	        	   table = new PdfPTable(6);
		           table.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.setWidthPercentage(90);
		           table.setWidths(new float[]{50,120,20,40,20,40});
	           }else{
	           table = new PdfPTable(5);
	           table.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           table.setWidthPercentage(90);
	           table.setWidths(new float[]{50,120,20,40,40});
	           }
	           
	           c1 = new PdfPCell(new Phrase("Référence",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Désignation",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Qté",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);

	           c1 = new PdfPCell(new Phrase("P.U HT",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           if(trouve==true){
	        	   c1 = new PdfPCell(new Phrase("Coefficient",ftextgra));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(c1);
	           }

	           c1 = new PdfPCell(new Phrase("P.T HT",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           table.setHeaderRows(1);
	           
	           if(offreArticle!=null){
	        	 
	        	   /*
	        	   if(facture.getDescription()!=null){
	        		   
		       	    	if(!facture.getDescription().equals("")){
				        	   c1 = new PdfPCell(new Phrase(facture.getDescription(),ftext));
					           c1.setBackgroundColor(new BaseColor(Color.decode("#D9D9D9")));
					           if(trouve==true){
					        	   c1.setColspan(6);
					           }else
					        	   c1.setColspan(5);
					           table.addCell(c1);
		       	    	}
	        	   }
	        	   */
	        	  
	           for(int i=0;i<offreArticle.size();i++)
	           {
	        	   if(offreArticle.get(i).getArticle()==null){
	        		   table.addCell(new Phrase("Article n'existe pas",ftext));
	        		   table.addCell(new Phrase(""));
			           table.addCell(new Phrase(""));
			           table.addCell(new Phrase(""));
			           table.addCell(new Phrase(""));
	        	   }
	        	   else{
	        		   if(offreArticle.get(i).getArticle().getRef()==null)
	        			   offreArticle.get(i).getArticle().setRef(" ");
	        		  if(offreArticle.get(i).getArticle().getDesignation()==null)
	        			  offreArticle.get(i).getArticle().setDesignation(" ");
		           
	        		  
	        	   
	        	   c1 = new PdfPCell(new Phrase(offreArticle.get(i).getArticle().getRef(),ftext));
	        	   c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           if(nbrtest<10)
		            	c1.setPadding(6);
		           table.addCell(c1);
		           Paragraph para=new Paragraph(offreArticle.get(i).getDesignation(),ftext);
		           c1 = new PdfPCell(para);
		           if(nbrtest<10)
		            	c1.setPadding(6);
		           table.addCell(c1);
		           
		           c1 = new PdfPCell(new Phrase(""+offreArticle.get(i).getLab(),ftext));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           c1.setVerticalAlignment(Element.ALIGN_CENTER);
		           if(nbrtest<10){
		            	c1.setPaddingBottom(6);
		            	c1.setPaddingTop(6);
		           }
		           table.addCell(c1);
		           
		           
		           
		           c1 = new PdfPCell(new Phrase(df.format(offreArticle.get(i).getMontantglobal()),ftext));
		           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           if(nbrtest<10){
		            	c1.setPaddingBottom(6);
		            	c1.setPaddingTop(6);
		           }
		           table.addCell(c1);
		           
		           if(trouve==true){
		        	   c1 = new PdfPCell(new Phrase(""+offreArticle.get(i).getCoefficient(),ftext));
			           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			           c1.setVerticalAlignment(Element.ALIGN_CENTER);
			           if(nbrtest<10){
			            	c1.setPaddingBottom(6);
			            	c1.setPaddingTop(6);
			           }
			           table.addCell(c1);
		           }
		           
		           if(trouve==true)
		        	   c1 = new PdfPCell(new Phrase(""+df.format(offreArticle.get(i).getMensualite()),ftext));
		           else
		        	   c1 = new PdfPCell(new Phrase(""+df.format(offreArticle.get(i).getPt()),ftext));
		           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           if(nbrtest<10){
		            	c1.setPaddingBottom(6);
		            	c1.setPaddingTop(6);
		           }
		           table.addCell(c1);
	        	   
	        	   }
	        	   
		           	if(i==20){
		        		 for(int j=0;j<writer.getPageNumber();j++)
		                	canvas.addImage(img);
		        	}
	           }
	           
	          
	           }
	           if(facture.getTypepaiement().getTypepaiement().equals("MAD")){
	        	   
				           //Total HT
				           PdfPCell totalHT = new PdfPCell(new Phrase("Total HT",ftextgra));
				           if(trouve==true)
				        	   totalHT.setColspan(5);
				           else
				        	   totalHT.setColspan(4);
				           totalHT.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
				           table.addCell(totalHT);
					        PdfPCell cfinal;
					        
					        	cfinal = new PdfPCell(new Phrase(df.format(facture.getTotalht()),ftextgra));
				           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
				           table.addCell(cfinal);
				           
				           //---TVA
				           PdfPCell tva = new PdfPCell(new Phrase("TVA 20%",ftextgra));
				           if(trouve==true)
				        	   tva.setColspan(5);
				           else
				        	   tva.setColspan(4);
				           tva.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
				           table.addCell(tva);
				           
					        cfinal = new PdfPCell(new Phrase(df.format(facture.getTva()),ftextgra));
	
					        
				           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
				           table.addCell(cfinal);
				           
				         
				           //---TOTAL
				           PdfPCell totalTTC = new PdfPCell(new Phrase("Total DH TTC",ftextgra));
				           if(trouve==true)
				        	   totalTTC.setColspan(5);
				           else
				        	   totalTTC.setColspan(4);
				           totalTTC.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
				           table.addCell(totalTTC);
				
					       cfinal = new PdfPCell(new Phrase(df.format(facture.getTotalttc()),ftextgra));
	
					        
				           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
				           table.addCell(cfinal);
			           
				           if(facture.getRemise()!=0){
					           PdfPCell coeffcell = new PdfPCell(new Phrase("Retenu de garantie",ftextgra));
					           if(trouve==true)
					        	   coeffcell.setColspan(5);
					           else
					        	   coeffcell.setColspan(4);
					           coeffcell.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
					           table.addCell(coeffcell);
					         
					           cfinal=new PdfPCell(new Phrase(df.format(facture.getCoefficientfacture())+"%",ftextgra));
					           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(cfinal);
					           
					           PdfPCell payettccell = new PdfPCell(new Phrase("Net à Payer TTC",ftextgra));
					           if(trouve==true)
					        	   payettccell.setColspan(5);
					           else
					        	   payettccell.setColspan(4);
					           payettccell.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
					           table.addCell(payettccell);
	
						        	cfinal = new PdfPCell(new Phrase(df.format(facture.getTotalttcpaye()),ftextgra));
	
						        
					           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(cfinal);
					           
				           }
	        	   
	           }else{
	        	 //Total HT
		           PdfPCell totalHT = new PdfPCell(new Phrase("Total en "+facture.getTypepaiement().getTypepaiement(),ftextgra));
		           if(trouve==true)
		        	   totalHT.setColspan(5);
		           else
		        	   totalHT.setColspan(4);
		           totalHT.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(totalHT);
			        PdfPCell cfinal;
			        	cfinal = new PdfPCell(new Phrase(df.format(facture.getTotalht()),ftextgra));
		           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.addCell(cfinal);
		           
		           if(facture.getRemise()!=0){
			           PdfPCell coeffcell = new PdfPCell(new Phrase("Retenu de garantie"));
			           if(trouve==true)
			        	   coeffcell.setColspan(5);
			           else
			        	   coeffcell.setColspan(4);
			           coeffcell.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
			           table.addCell(coeffcell);
			         
			           cfinal=new PdfPCell(new Phrase(df.format(facture.getCoefficientfacture())+"%",ftextgra));
			           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			           table.addCell(cfinal);
			           
			           PdfPCell payettccell = new PdfPCell(new Phrase("Net à Payer en "+facture.getTypepaiement().getTypepaiement()));
			           if(trouve==true)
			        	   payettccell.setColspan(5);
			           else
			        	   payettccell.setColspan(4);
			           payettccell.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
			           table.addCell(payettccell);

				        	cfinal = new PdfPCell(new Phrase(df.format(facture.getTotalttcpaye()),ftextgra));

				        
			           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			           table.addCell(cfinal);
			           
		           }
	           }
	           
	           document.add(table);
	           
	
	        		 for(int j=0;j<writer.getPageNumber();j++)
	                	canvas.addImage(img);
	  
	           Paragraph pie=new Paragraph();
	           pie.add(new Phrase("Arrêtée la présente facture à la somme de :\n",forange));
	           
	           NumberFormat formatter = new RuleBasedNumberFormat(Locale.FRANCE, RuleBasedNumberFormat.SPELLOUT);
	           try {
	        	   
	        	  
	        	   String[]var;
	        	   String chaine;
	        	   if(facture.getTypepaiement().getTypepaiement().equals("MAD")){
	        		  chaine=""+facture.getTotalttcpaye();
	        		   var=chaine.replace(".", ",").split(",");
	        	   }
	        	   else{
	        		   chaine=""+facture.getTotalht();
	        		   var=chaine.replace(".", ",").split(",");
	        	   }
	        	   double nombre=Double.parseDouble(var[0]);
	        	   double virgule=Double.parseDouble(var[1]);
	        	   if(var[1].length()>1){
	        			   String chainenombre=formatter.format(nombre).replace("onze cents","mille cents");
	        			   
	        		  if(facture.getTypepaiement().getTypepaiement().equals("MAD")) 
	        		   pie.add(new Phrase(chainenombre+" Dirhams et "+formatter.format(virgule)+ " centimes Toutes Taxes Comprises",ftextgra));
	        		  else
	        			  pie.add(new Phrase(chainenombre+" "+facture.getTypepaiement().getTypepaiement().toLowerCase()+" et "+formatter.format(virgule)+ " hors Taxes Comprises",ftextgra));
	        			  
	        	   }
			       
	        	   else{
	        		   
	        		   virgule=virgule*10;
	        		   
	        		   String chainenombre=formatter.format(nombre).replace("onze cents","mille cents");
	        		   if(facture.getTypepaiement().getTypepaiement().equals("MAD")) 
	        			   pie.add(new Phrase(chainenombre+" Dirhams et "+formatter.format(virgule)+ " centimes Toutes Taxes Comprises",ftextgra));
	        		   else
	        			   pie.add(new Phrase(chainenombre+" "+facture.getTypepaiement().getTypepaiement().toLowerCase()+" et "+formatter.format(virgule)+ " hors Taxes Comprises",ftextgra));
	        			   
	        	   }
	        		   
	           } catch (Exception e) {
	        	   System.out.println("-----------Exception----------");
	        	   if(facture.getTypepaiement().getTypepaiement().equals("MAD"))
	        		   pie.add(new Phrase(formatter.format(facture.getTotalttcpaye())+" Dirhams et zero centimes Toutes Taxes Comprises",ftextgra));
	        	   else
	        		   pie.add(new Phrase(formatter.format(facture.getTotalht())+" Dirhams et zero centimes Toutes Taxes Comprises",ftextgra));
	           }
	           pie.add(saute);
	           pie.add(new Paragraph("Coordonnées Bancaire :",forange));
	           pie.add(new Paragraph(facture.getBanque().getNumerobancaire(),ftext10));
	           pie.add(new Paragraph(facture.getBanque().getBanque(),ftext10));
	           pie.add(new Paragraph("Agence "+facture.getBanque().getAgence(),ftext10));
	           pie.setIndentationLeft(53);
	           document.add(pie);
	           
	           document.addAuthor("IMS Technology");
	           document.addTitle("Facture");
	           document.addSubject("Facture N°"+facture.getRef());
	           document.addCreator("RABEH");
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       } catch (DocumentException | IOException | NullPointerException de) {
				FacesMessage msg = new FacesMessage("Probleme de generation de PDF","contacter l'administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
	           System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF"+de.getMessage());
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       }
 	 }
	
	 
 	 //Facture Contrat
 	 
 	 
 	 public void createPdffactcontrat(String dest) {

 		 DateFormat format=new SimpleDateFormat("dd/MM/yyyy");
    	
    	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
       
    	Phrase saute=new Phrase("\n");
        Document document = new Document(PageSize.A4);
       
         try {
        	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));         
        	document.open();
        	 System.out.println("------------document.open()------------");
        	Image img = Image.getInstance(webroot+"\\fondlogoancien.png"); 
            img.scaleAbsoluteHeight(PageSize.A4.getHeight());
            img.scaleAbsoluteWidth(PageSize.A4.getWidth());
            img.setAbsolutePosition(0, 0);
            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(img);
            
        	  document.setMargins(36, 36, 108, 180);
	           document.add(saute);
	           document.add(saute);
	           document.add(saute);
	           Font ftext=new Font(Font.FontFamily.HELVETICA,8,Font.NORMAL,new BaseColor(Color.black));
	    	   Font ftext10=new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL,new BaseColor(Color.black));
	            Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
	            Font forange=new Font(Font.FontFamily.HELVETICA,10,Font.UNDERLINE,new BaseColor(Color.black));
	           
	            DecimalFormat df = new DecimalFormat();
	            df.setMinimumFractionDigits(2);
	            df.setMaximumFractionDigits(2);
	           
	           facture=manager.getObject(facture.getId());
	           
	           PdfPTable tableinfo = new PdfPTable(1);
	           PdfPCell l = new PdfPCell(new Phrase("Date:",ftext));
	           l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           l.setBorder(Rectangle.NO_BORDER);
	           tableinfo.addCell(l);
	           
	           l = new PdfPCell(new Phrase(format.format(facture.getDatefacture()),ftext));
	           l.setHorizontalAlignment(Element.ALIGN_CENTER);
	           tableinfo.addCell(l);
	           
	           l = new PdfPCell(new Phrase("Client:",ftext));
	           l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           l.setBorder(Rectangle.NO_BORDER);
	           tableinfo.addCell(l);
	          
	           l = new PdfPCell(new Phrase(""+facture.getClient().getSociete(),ftextgra));
	           l.setHorizontalAlignment(Element.ALIGN_CENTER);
	           tableinfo.addCell(l);
	           
	           l = new PdfPCell(new Phrase(""+facture.getClient().getAdresse(),ftext));
	           l.setHorizontalAlignment(Element.ALIGN_CENTER);
	           l.setPadding(5);
	           tableinfo.addCell(l);
	           
	           l = new PdfPCell(new Phrase(""+facture.getClient().getVille().getVille(),ftext));
	           l.setHorizontalAlignment(Element.ALIGN_CENTER);
	           tableinfo.addCell(l);
	          
	           tableinfo.setHorizontalAlignment(tableinfo.ALIGN_RIGHT);
	           tableinfo.setWidthPercentage(30);
	           document.add(tableinfo);
	           document.add(saute);
	           Paragraph Numerocommande=new Paragraph("Facture N° : "+facture.getRef(),new Font(Font.FontFamily.COURIER,15,Font.BOLD,new BaseColor(Color.black)));
	           Numerocommande.setAlignment(Element.ALIGN_LEFT);
	           Numerocommande.setIndentationLeft(70);
	           document.add(Numerocommande);
	           document.add(saute);
	           
	           PdfPTable tableoffre = new PdfPTable(3);
	           tableoffre.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           tableoffre.setWidthPercentage(90);
	           PdfPCell c1 = new PdfPCell(new Phrase("Date BC",ftext));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           tableoffre.addCell(c1);
	           c1 = new PdfPCell(new Phrase("Numéro BC",ftext));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           tableoffre.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Mode de Réglement",ftext));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           tableoffre.addCell(c1);
               
	           	if(facture.getNumeroBCClient()==null)
	           		facture.setNumeroBCClient(" ");
	           	if(facture.getConditiondepayment()==null)
	           		facture.setNumeroBCClient(" ");
	           		if(facture.getDateboncommande()!=null)
	           			c1 = new PdfPCell(new Phrase(""+format.format(facture.getDateboncommande()),ftext));
	           		else
	           			c1 = new PdfPCell(new Phrase("-",ftext));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           tableoffre.addCell(c1);
		           
		           c1 = new PdfPCell(new Phrase(""+facture.getNumeroBCClient(),ftext));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           tableoffre.addCell(c1);
		           
		           c1 = new PdfPCell(new Phrase(""+facture.getConditiondepayment(),ftext));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           tableoffre.addCell(c1);
	           document.add(tableoffre);
	           document.add(saute);
	           
	           PdfPTable table;
	           
	           List<Facture_Article> factArticle=managerFactArticle.getObjects(facture.getId());
	           int taillearticle=0;
	           if(factArticle.size()>0){
	        	   while (taillearticle<factArticle.size() && factArticle.get(taillearticle).getCoefficient()==1)
	        		   taillearticle++;
	           }
	           System.err.println(taillearticle+"==="+factArticle.size());
	           if(taillearticle==factArticle.size()){
	        	   
	        	   table= new PdfPTable(3);
		           table.setWidths(new float[]{130,40,40});
		           table.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.setWidthPercentage(90);
		           
		           c1 = new PdfPCell(new Phrase("Désignation de service",ftextgra));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(c1);
		           
		           c1 = new PdfPCell(new Phrase("Qantité",ftextgra));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(c1);
		           
		           c1 = new PdfPCell(new Phrase("P.T(HT)",ftextgra));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(c1);
		           table.setHeaderRows(1);
		           System.err.println("------------------------TITIRE COMPLETE----------------");
		           if(factArticle!=null){
		        	   
				        	   int nbrtest=factArticle.size();
				        	   
				        	   if(facture.getDescription()!=null){
				        		   
					       	    	if(!facture.getDescription().equals("")){
							        	   c1 = new PdfPCell(new Phrase(facture.getDescription(),ftext));
								           c1.setBackgroundColor(new BaseColor(Color.decode("#D9D9D9")));
								           c1.setColspan(3);
								           table.addCell(c1);
					       	    	}
				        	   }
				        	   
				           for(int i=0;i<factArticle.size();i++)
				           {
				        	   if(factArticle.get(i).getArticle()==null){
				        		   table.addCell(new Phrase("Article n'existe pas"));
				        		   table.addCell(new Phrase(""));
						           table.addCell(new Phrase(""));
				        	   }
				        	   else{
				        		  if(factArticle.get(i).getDesignation()==null)
				        			  factArticle.get(i).setDesignation(" ");
		                       
				        		  
				        			  c1 = new PdfPCell(new Phrase(""+factArticle.get(i).getDesignation(),ftext));
				        		  if(nbrtest<10)
						            	c1.setPadding(15);
				        		  table.addCell(c1);
					           
					           
					        	   c1 = new PdfPCell(new Phrase(factArticle.get(i).getLab(),ftext));
					           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
					           if(nbrtest<10){
					            	c1.setPaddingTop(15);
					            	c1.setPaddingBottom(15);
					           }
					           table.addCell(c1);
					           
					        	   c1 = new PdfPCell(new Phrase(""+df.format(factArticle.get(i).getMensualite()),ftext));
					           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           if(nbrtest<10){
					            	c1.setPaddingTop(15);
					            	c1.setPaddingBottom(15);
					           }
					           table.addCell(c1);
				        	   }
				           }
	           }
		           
		           System.err.println("--------------table final sucess-------------------");
		           //Total HT
		           PdfPCell totalHT = new PdfPCell(new Phrase("Total HT",ftextgra));
		           totalHT.setColspan(2);
		           totalHT.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(totalHT);
			        
		           PdfPCell cfinal=new PdfPCell(new Phrase(df.format(facture.getTotalht()),ftextgra));
		           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           cfinal.setVerticalAlignment(Element.ALIGN_CENTER);
		           table.addCell(cfinal);
		          
		           //---TVA
		           PdfPCell tva = new PdfPCell(new Phrase("TVA 20%",ftextgra));
		           tva.setColspan(2);
		           tva.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(tva);
			       
		           cfinal=new PdfPCell(new Phrase(df.format(facture.getTva()),ftextgra));
		           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           cfinal.setVerticalAlignment(Element.ALIGN_CENTER);
		           table.addCell(cfinal);
			        
		           //---TOTAL
		           PdfPCell totalTTC = new PdfPCell(new Phrase("Total DH TTC",ftextgra));
		           totalTTC.setColspan(2);
		           totalTTC.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(totalTTC);
		           
		           cfinal=new PdfPCell(new Phrase(df.format(facture.getTotalttc()),ftextgra));
		           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           cfinal.setVerticalAlignment(Element.ALIGN_CENTER);
		           table.addCell(cfinal);
		           
		           if(facture.getCoefficientfacture()>0 && facture.getCoefficientfacture()<1){
			           PdfPCell coeffcell = new PdfPCell(new Phrase("Coefficient"));
			           coeffcell.setColspan(2);
			           coeffcell.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
			           table.addCell(coeffcell);
			        
			           cfinal=new PdfPCell(new Phrase(df.format(facture.getCoefficientfacture())+"",ftextgra));
			           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			           cfinal.setVerticalAlignment(Element.ALIGN_CENTER);
			           table.addCell(cfinal);
			           
			           PdfPCell payettccell = new PdfPCell(new Phrase("Net à Payer TTC"));
			           payettccell.setColspan(2);
			           payettccell.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
			           table.addCell(payettccell);
				        
			           cfinal=new PdfPCell(new Phrase(df.format(facture.getTotalttcpaye()),ftextgra));
			           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			           cfinal.setVerticalAlignment(Element.ALIGN_CENTER);
			           table.addCell(cfinal);
			           
		           }
	        	   
	        	   
	           }else{
	        	   /*
	        	    * Pour la coefftion superieur à 1
	        	    */
	        	   table= new PdfPTable(4);
		           table.setWidths(new float[]{130,40,40,40});
		           table.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.setWidthPercentage(90);
		           
		           c1 = new PdfPCell(new Phrase("Désignation",ftextgra));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(c1);
		           
		           c1 = new PdfPCell(new Phrase("Montant Global",ftextgra));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(c1);

		           c1 = new PdfPCell(new Phrase("Coefficient",ftextgra));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(c1);

		           c1 = new PdfPCell(new Phrase("Mensualité(HT)",ftextgra));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(c1);
		           table.setHeaderRows(1);
		           

		           if(factArticle!=null){
		        	   
			        	   int nbrtest=factArticle.size();
			        	   if(facture.getDescription()!=null){
			        		   
				       	    	if(!facture.getDescription().equals("")){
						        	   c1 = new PdfPCell(new Phrase(facture.getDescription(),ftext));
							           c1.setBackgroundColor(new BaseColor(Color.decode("#D9D9D9")));
							           c1.setColspan(4);
							           table.addCell(c1);
				       	    	}
			        	   }
			           for(int i=0;i<factArticle.size();i++)
			           {
			        	   if(factArticle.get(i).getArticle()==null){
			        		   table.addCell(new Phrase("Article n'existe pas"));
			        		   table.addCell(new Phrase(""));
					           table.addCell(new Phrase(""));
					           table.addCell(new Phrase(""));
					           table.addCell(new Phrase(""));
			        	   }
			        	   else{
			        		  if(factArticle.get(i).getDesignation()==null)
			        			  factArticle.get(i).setDesignation(" ");
	                       
			        		  
			        			  c1 = new PdfPCell(new Phrase(""+factArticle.get(i).getDesignation(),ftext));
			        		  if(nbrtest<10)
					            	c1.setPadding(15);
			        		  table.addCell(c1);
				           
			        			  c1 = new PdfPCell(new Phrase(""+df.format(factArticle.get(i).getMontantglobal()),ftext));
				           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				           if(nbrtest<10){
				            	c1.setPaddingTop(15);
				            	c1.setPaddingBottom(15);
				           }
				           table.addCell(c1);
				           
				        	   c1 = new PdfPCell(new Phrase(factArticle.get(i).getLab(),ftext));
				           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				           if(nbrtest<10){
				            	c1.setPaddingTop(15);
				            	c1.setPaddingBottom(15);
				           }
				           table.addCell(c1);
				           
				        	   c1 = new PdfPCell(new Phrase(""+df.format(factArticle.get(i).getMensualite()),ftext));
				           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				           if(nbrtest<10){
				            	c1.setPaddingTop(15);
				            	c1.setPaddingBottom(15);
				           }
				           table.addCell(c1);
			        	   }
			           }
		           }
		           //Total HT
		           PdfPCell totalHT = new PdfPCell(new Phrase("Total HT",ftextgra));
		           totalHT.setColspan(3);
		           totalHT.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(totalHT);
			        
		           PdfPCell cfinal=new PdfPCell(new Phrase(df.format(facture.getTotalht()),ftextgra));
		           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           cfinal.setVerticalAlignment(Element.ALIGN_CENTER);
		           table.addCell(cfinal);
		          
		           //---TVA
		           PdfPCell tva = new PdfPCell(new Phrase("TVA 20%",ftextgra));
		           tva.setColspan(3);
		           tva.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(tva);
			       
		           cfinal=new PdfPCell(new Phrase(df.format(facture.getTva()),ftextgra));
		           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           cfinal.setVerticalAlignment(Element.ALIGN_CENTER);
		           table.addCell(cfinal);
		           
		           //---TOTAL
		           PdfPCell totalTTC = new PdfPCell(new Phrase("Total DH TTC",ftextgra));
		           totalTTC.setColspan(3);
		           totalTTC.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(totalTTC);
		           
		           cfinal=new PdfPCell(new Phrase(df.format(facture.getTotalttc()),ftextgra));
		           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           cfinal.setVerticalAlignment(Element.ALIGN_CENTER);
		           table.addCell(cfinal);
		           
		           if(facture.getCoefficientfacture()>0 && facture.getCoefficientfacture()<1){
			           PdfPCell coeffcell = new PdfPCell(new Phrase("Coefficient"));
			           coeffcell.setColspan(3);
			           coeffcell.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
			           table.addCell(coeffcell);
			        
			           cfinal=new PdfPCell(new Phrase(df.format(facture.getCoefficientfacture())+"",ftextgra));
			           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			           cfinal.setVerticalAlignment(Element.ALIGN_CENTER);
			           table.addCell(cfinal);
			           
			           PdfPCell payettccell = new PdfPCell(new Phrase("Net à Payer TTC"));
			           payettccell.setColspan(3);
			           payettccell.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
			           table.addCell(payettccell);
				        
			           cfinal=new PdfPCell(new Phrase(df.format(facture.getTotalttcpaye()),ftextgra));
			           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
			           cfinal.setVerticalAlignment(Element.ALIGN_CENTER);
			           table.addCell(cfinal);
			           
		           }
		           
	           }
	           
	          
	           document.add(table);
	           document.add(saute);
	           Paragraph pie=new Paragraph();
	           pie.add(new Phrase("Arrêtée la présente facture à la somme de :\n",forange));
	           NumberFormat formatter = new RuleBasedNumberFormat(RuleBasedNumberFormat.SPELLOUT);
	    	   String[]var;
       	   String chaine; 
	           chaine=""+facture.getTotalttcpaye();
       	   var=chaine.replace(".", ",").split(",");
       	   int nombre=Integer.parseInt(var[0]);
       	   int virgule=Integer.parseInt(var[1]);
       	   if(var[1].length()>1){
       		   if(facture.getTypepaiement().getTypepaiement().equals("MAD"))
       		   pie.add(new Phrase(formatter.format(nombre)+" Dirhams et "+formatter.format(virgule)+ " centimes Toutes Taxes Comprises",ftextgra));
       		   else
       			   pie.add(new Phrase(formatter.format(nombre)+" "+facture.getTypepaiement().getTypepaiement()+" et "+formatter.format(virgule),ftextgra));
       	   }
		      
       	   else{
       		   virgule=virgule*10;
       		   if(facture.getTypepaiement().getTypepaiement().equals("MAD"))
       		   pie.add(new Phrase(formatter.format(nombre)+" Dirhams et "+formatter.format(virgule)+ " centimes Toutes Taxes Comprises",ftextgra));
       		   else
       			   pie.add(new Phrase(formatter.format(nombre)+" "+facture.getTypepaiement().getTypepaiement()+" et "+formatter.format(virgule),ftextgra));
       	   }
	           
	           pie.add(saute);
	           pie.add(new Paragraph("Coordonnées Bancaire :",forange));
	           pie.add(new Paragraph(facture.getBanque().getNumerobancaire(),ftext10));
	           pie.add(new Paragraph(facture.getBanque().getBanque(),ftext10));
	           pie.add(new Paragraph("Agence "+facture.getBanque().getAgence(),ftext10));
	           pie.add(saute);
	           pie.setIndentationLeft(53);
	           document.add(pie);
	           pie.add(saute);
	           document.addAuthor("IMS Technology");
	           document.addTitle("Facture");
	           document.addCreator("RABEH");
	           document.close();
	           System.out.println("FIN GENRATION PDF");
	       } catch (DocumentException | IOException de) {
	           System.out.println("-----------DocumentException | IOException------");
	           de.getStackTrace();
	           document.close();
	       }
        	
        	
 	}
 	 
 	 public StreamedContent getFilefacture() {
		try {
			filefacture = new DefaultStreamedContent(new FileInputStream(new File("D:\\factures\\"+facture.getRef()+".pdf")),"application/pdf",facture.getRef()+".pdf");
			return filefacture;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}


 	public void createPdfAvoir(String dest) {
 		
 		 DateFormat format=new SimpleDateFormat("dd/MM/yyyy"); 
         DecimalFormat df = new DecimalFormat();
         df.setDecimalSeparatorAlwaysShown(true);
         df.setMaximumFractionDigits(2);
         df.setMinimumFractionDigits(2);
         
 		 String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
 		 Phrase saute=new Phrase("\n");
         Document document = new Document(PageSize.A4);
        
          try {
        	  
        	  PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));         
        	  document.open();
        	  
        	  Font ftext=new Font(Font.FontFamily.HELVETICA,9,Font.NORMAL,new BaseColor(Color.black));
	            Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
	            Font forange=new Font(Font.FontFamily.HELVETICA,10,Font.UNDERLINE,new BaseColor(Color.black));
	    	   
        	  Image img = Image.getInstance(webroot+"\\fondlogoancien.png"); 
              img.scaleAbsoluteHeight(PageSize.A4.getHeight());
              img.scaleAbsoluteWidth(PageSize.A4.getWidth());
              img.setAbsolutePosition(0, 0);
              PdfContentByte canvas = writer.getDirectContentUnder();
              canvas.addImage(img);
              
        	 
         	
         	 document.setMargins(36, 36, 108, 180);
	           document.add(saute);

	           avoirclient=manageravoir.getObject(avoirclient.getId());
	           
	           if(avoirclient.getClient().getSociete()==null)
	        	   avoirclient.getClient().setSociete("");
	           
	           if(avoirclient.getClient().getAdresse()==null)
	        	   avoirclient.getClient().setAdresse("");
	           
	           if(avoirclient.getClient().getVille()==null)
	        	   {
	        	   avoirclient.getClient().setVille(new Ville());
	        	   avoirclient.getClient().getVille().setVille("");
	        	   }
	         
	           PdfPTable tableinfo = new PdfPTable(1);
	           
	           PdfPCell l = new PdfPCell(new Phrase("Date:",ftext));
	           l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           l.setBorder(Rectangle.NO_BORDER);
	           tableinfo.addCell(l);
	           
	           l = new PdfPCell(new Phrase(format.format(avoirclient.getDateavoir()),ftext));
	           l.setHorizontalAlignment(Element.ALIGN_CENTER);
	           tableinfo.addCell(l);
	           
	           l = new PdfPCell(new Phrase("Client:"));
	           l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           l.setBorder(Rectangle.NO_BORDER);
	           tableinfo.addCell(l);
	          
	           l = new PdfPCell(new Phrase(""+avoirclient.getClient().getSociete(),ftext));
	           l.setHorizontalAlignment(Element.ALIGN_CENTER);
	           tableinfo.addCell(l);
	           
	           l = new PdfPCell(new Phrase(""+avoirclient.getClient().getAdresse(),ftext));
	           l.setHorizontalAlignment(Element.ALIGN_CENTER);
	           tableinfo.addCell(l);
	           
	           l = new PdfPCell(new Phrase(""+avoirclient.getClient().getVille().getVille(),ftext));
	           l.setHorizontalAlignment(Element.ALIGN_CENTER);
	           tableinfo.addCell(l);
	          
	           tableinfo.setHorizontalAlignment(tableinfo.ALIGN_RIGHT);
	           tableinfo.setWidthPercentage(30);
	           document.add(tableinfo);
	           document.add(saute);
	           Font ftextgrafact=new Font(Font.FontFamily.HELVETICA,15,Font.BOLD,new BaseColor(Color.black));
	           Paragraph Numerocommande=new Paragraph("AVOIR N° : "+avoirclient.getNumero_avoir(),ftextgrafact);
	           Numerocommande.setAlignment(Element.ALIGN_LEFT);
	           Numerocommande.setAlignment(Element.ALIGN_TOP);
	           Numerocommande.setIndentationLeft(53);
	           document.add(Numerocommande);
	           
	           document.add(saute);

	           System.out.println("---------------avoirclient---------------");
	           PdfPTable tableoffre = new PdfPTable(3);
	           tableoffre.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           tableoffre.setWidthPercentage(90);
	           PdfPCell c1 = new PdfPCell(new Phrase("Date fact",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           tableoffre.addCell(c1);
	           c1 = new PdfPCell(new Phrase("N°facture",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           tableoffre.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Réglement",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           tableoffre.addCell(c1);
	           
		   		Iterator<Facture_Avoir> factavoir=avoirclient.getFactures().iterator();
				int j=0;
				while(factavoir.hasNext()){
					if(j==0)
					facttempo=manager.getByID(factavoir.next().getFacture().getId());
					j++;
				}
				if(facttempo!=null)
	           	   c1 = new PdfPCell(new Phrase(format.format(facttempo.getDatefacture()) ,ftext));
				else
					c1 = new PdfPCell(new Phrase("-",ftext));
				
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           tableoffre.addCell(c1);
		           
		           if(facttempo!=null)
		        	   c1 = new PdfPCell(new Phrase(facttempo.getRef(),ftext));
		           else
		        	   c1 = new PdfPCell(new Phrase("-",ftext));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           tableoffre.addCell(c1);
		           
		           c1 = new PdfPCell(new Phrase(avoirclient.getReglement(),ftext));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           tableoffre.addCell(c1);
		      
	           document.add(tableoffre);
	           document.add(saute);
	           List<Avoir_Articles> avoirarticle=managerAvoirarticles.getObjects(avoirclient.getId());

	           PdfPTable table;
	
	           table = new PdfPTable(5);
	           table.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           table.setWidthPercentage(90);
	           table.setWidths(new float[]{50,120,20,40,40});
	           
	           c1 = new PdfPCell(new Phrase("Référence",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Désignation",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Qté",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);

	           c1 = new PdfPCell(new Phrase("P.U HT",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           

	           c1 = new PdfPCell(new Phrase("P.T HT",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           table.setHeaderRows(1);
	           
	           if(avoirarticle!=null){
	        	   
	           for(int i=0;i<avoirarticle.size();i++)
	           {
	        	   if(avoirarticle.get(i).getArticle()==null){
	        		   table.addCell(new Phrase("Article n'existe pas",ftext));
	        		   table.addCell(new Phrase(""));
			           table.addCell(new Phrase(""));
			           table.addCell(new Phrase(""));
			           table.addCell(new Phrase(""));
	        	   }
	        	   else{
	        		   if(avoirarticle.get(i).getArticle().getRef()==null)
	        			   avoirarticle.get(i).getArticle().setRef(" ");
	        		  if(avoirarticle.get(i).getArticle().getDesignation()==null)
	        			  avoirarticle.get(i).getArticle().setDesignation(" ");
		           
	        		  
	        	   
	        	   c1 = new PdfPCell(new Phrase(avoirarticle.get(i).getArticle().getRef(),ftext));
	        	   c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	c1.setPadding(3);
		           table.addCell(c1);
		           Paragraph para=new Paragraph(avoirarticle.get(i).getDesignation(),ftext);
		           c1 = new PdfPCell(para);
		            	c1.setPadding(3);
		           table.addCell(c1);
		           
		           c1 = new PdfPCell(new Phrase(avoirarticle.get(i).getChaineqte(),ftext));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           c1.setVerticalAlignment(Element.ALIGN_CENTER);
		           c1.setPadding(3);
		           table.addCell(c1);
		           
		           
		           
		           c1 = new PdfPCell(new Phrase(df.format(avoirarticle.get(i).getPu()),ftext));
		           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           c1.setPadding(3);
		           table.addCell(c1);
		           
		           c1 = new PdfPCell(new Phrase(""+df.format(avoirarticle.get(i).getPt()),ftext));
		           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.addCell(c1);
	        	   
	        	   }
	           }
	          
	           }
	           if(avoirclient.getTypepaiement().getTypepaiement().equals("MAD")){
	        	   
				           //Total HT
				           PdfPCell totalHT = new PdfPCell(new Phrase("Total HT",ftextgra));
				           totalHT.setColspan(4);
				           totalHT.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
				           table.addCell(totalHT);
					        PdfPCell cfinal;
					       
					        cfinal = new PdfPCell(new Phrase(df.format(avoirclient.getTotalht()),ftextgra));
				           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
				           table.addCell(cfinal);
				           
				           //---TVA
				           PdfPCell tva = new PdfPCell(new Phrase("TVA 20%",ftextgra));
				         tva.setColspan(4);
				           tva.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
				           table.addCell(tva);
				           
					        cfinal = new PdfPCell(new Phrase(df.format(avoirclient.getTva()),ftextgra));
	
					        
				           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
				           table.addCell(cfinal);
				           
				         
				           //---TOTAL
				           PdfPCell totalTTC = new PdfPCell(new Phrase("Net à Payer TTC",ftextgra));
				           totalTTC.setColspan(4);
				           totalTTC.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
				           table.addCell(totalTTC);
				
					       cfinal = new PdfPCell(new Phrase(df.format(avoirclient.getTotalttc()),ftextgra));
	
					        
				           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
				           table.addCell(cfinal);
	        	   
	           }else{
	        	 //Total HT
		           PdfPCell totalHT = new PdfPCell(new Phrase("Net à Payer en "+avoirclient.getTypepaiement().getTypepaiement(),ftextgra));
		        	   totalHT.setColspan(4);
		           totalHT.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(totalHT);
			        PdfPCell cfinal;
			        	cfinal = new PdfPCell(new Phrase(df.format(avoirclient.getTotalht()),ftextgra));
		           cfinal.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.addCell(cfinal);
	           }
	           
	           document.add(table);
	           
	           Paragraph pie=new Paragraph();
	           pie.add(new Phrase("Arrêté le présent Avoir à la somme de :\n",forange));
	           
	           NumberFormat formatter = new RuleBasedNumberFormat(Locale.FRANCE, RuleBasedNumberFormat.SPELLOUT);
	           try {
	        	   
	        	  
	        	   String[]var;
	        	   String chaine;
	        	   if(avoirclient.getTypepaiement().getTypepaiement().equals("MAD")){
	        		  chaine=""+avoirclient.getTotalttcpaye();
	        		   var=chaine.replace(".", ",").split(",");
	        	   }
	        	   else{
	        		   chaine=""+avoirclient.getTotalht();
	        		   var=chaine.replace(".", ",").split(",");
	        	   }
	        	   System.out.println("converion"+var[0]);
	        	   System.out.println("converion"+var[1]);
	        	   double nombre=Double.parseDouble(var[0]);
	        	   double virgule=Double.parseDouble(var[1]);
	        	   if(var[1].length()>1){
	        			   String chainenombre=formatter.format(nombre).replace("onze cents","mille cents");
	        			   
	        		  if(avoirclient.getTypepaiement().getTypepaiement().equals("MAD")) 
	        		   pie.add(new Phrase(chainenombre+" Dirhams et "+formatter.format(virgule)+ " centimes Toutes Taxes Comprises",ftextgra));
	        		  else
	        			  pie.add(new Phrase(chainenombre+" "+avoirclient.getTypepaiement().getTypepaiement().toLowerCase()+" et "+formatter.format(virgule)+ " hors Taxes Comprises",ftextgra));
	        			  
	        	   }
			       
	        	   else{
	        		   virgule=virgule*10;
	        		   System.out.println(formatter.format(nombre));
	        		   String chainenombre=formatter.format(nombre).replace("onze cents","mille cents");
	        		   if(avoirclient.getTypepaiement().getTypepaiement().equals("MAD")) 
	        			   pie.add(new Phrase(chainenombre+" Dirhams et "+formatter.format(virgule)+ " centimes Toutes Taxes Comprises",ftextgra));
	        		   else
	        			   pie.add(new Phrase(chainenombre+" "+avoirclient.getTypepaiement().getTypepaiement().toLowerCase()+" et "+formatter.format(virgule)+ " hors Taxes Comprises",ftextgra));
	        			   
	        	   }
	        		   
	           } catch (Exception e) {
	        	   System.out.println("-----------Exception CHAINE----------");
	        	   if(avoirclient.getTypepaiement().getTypepaiement().equals("MAD"))
	        		   pie.add(new Phrase(formatter.format(avoirclient.getTotalttcpaye())+" Dirhams et zero centimes Toutes Taxes Comprises",ftextgra));
	        	   else
	        		   pie.add(new Phrase(formatter.format(avoirclient.getTotalht())+" Dirhams et zero centimes Toutes Taxes Comprises",ftextgra));
	           }
	           pie.add(saute);
	           pie.setIndentationLeft(53);
	           document.add(pie);
	           document.addAuthor("IMS Technology");
	           document.addTitle("Avoir Client");
	           document.close();
	           System.out.println("-----------------FIN IMPRESSION--------------");
	       } catch (DocumentException | IOException | NullPointerException de) {
				FacesMessage msg = new FacesMessage("Probleme de generation de PDF","contacter l'administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				  System.out.println("-----------DocumentException | IOException | NullPointerException----------");
				  de.getStackTrace();
	           document.close();
	       }
		
	}
 	
	
 	public StreamedContent getFileavoir() {
 		try {
			fileavoir = new DefaultStreamedContent(new FileInputStream(new File("D:\\avoirs\\"+avoirclient.getNumero_avoir()+".pdf")),"application/pdf",avoirclient.getNumero_avoir()+".pdf");
			return fileavoir;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
 	 * FIN GETTER DE StreamedContent
 	 */
	
	
	public String getObjet() {
		return objet;
	}

	public void setObjet(String objet) {
		this.objet = objet;
	}
	
	public String getRcpt() {
		return rcpt;
	}

	public void setRcpt(String rcpt) {
		this.rcpt = rcpt;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public Offre getOffre() {
		return offre;
	}

	public void setOffre(Offre offre) {
		this.offre = offre;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public String getContenumessage() {
		return contenumessage;
	}

	public void setContenumessage(String contenumessage) {
		this.contenumessage = contenumessage;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}
		public ModelService<Contact> getManagercontact() {
			return managercontact;
		}

		public void setManagercontact(ModelService<Contact> managercontact) {
			this.managercontact = managercontact;
		}

		public List<String> getFiltredcontactcc() {
			return filtredcontactcc;
		}

		public void setFiltredcontactcc(List<String> filtredcontactcc) {
			this.filtredcontactcc = filtredcontactcc;
		}

		public void setFileoffre(StreamedContent fileoffre) {
			this.fileoffre = fileoffre;
		}

		public String getObjetcommande() {
			return objetcommande;
		}

		public void setObjetcommande(String objetcommande) {
			this.objetcommande = objetcommande;
		}

		public String getObjetlivraison() {
			return objetlivraison;
		}

		public void setObjetlivraison(String objetlivraison) {
			this.objetlivraison = objetlivraison;
		}

		public String getObjetfacture() {
			return objetfacture;
		}

		public void setObjetfacture(String objetfacture) {
			this.objetfacture = objetfacture;
		}

		public String getObjetavoirs() {
			return objetavoirs;
		}

		public void setObjetavoirs(String objetavoirs) {
			this.objetavoirs = objetavoirs;
		}

		public void setFilecommande(StreamedContent filecommande) {
			this.filecommande = filecommande;
		}


		public void setFilefacture(StreamedContent filefacture) {
			this.filefacture = filefacture;
		}

		public void setFilelivraison(StreamedContent filelivraison) {
			this.filelivraison = filelivraison;
		}



		public void setFileavoir(StreamedContent fileavoir) {
			this.fileavoir = fileavoir;
		}

		public BonCommande getCommande() {
			return commande;
		}

		public void setCommande(BonCommande commande) {
			this.commande = commande;
		}

		public Facture getFacture() {
			return facture;
		}

		public void setFacture(Facture facture) {
			this.facture = facture;
		}

		public BonLivraison getLivraison() {
			return livraison;
		}

		public void setLivraison(BonLivraison livraison) {
			this.livraison = livraison;
		}

		public AvoirClient getAvoirclient() {
			return avoirclient;
		}

		public void setAvoirclient(AvoirClient avoirclient) {
			this.avoirclient = avoirclient;
		}

		public ModelService<Boncommande_Article> getManagerarticle() {
			return managerarticle;
		}

		public void setManagerarticle(ModelService<Boncommande_Article> managerarticle) {
			this.managerarticle = managerarticle;
		}

		public ModelService<Stock_Livraison> getManagerstocklivraison() {
			return managerstocklivraison;
		}

		public void setManagerstocklivraison(
				ModelService<Stock_Livraison> managerstocklivraison) {
			this.managerstocklivraison = managerstocklivraison;
		}

		public ModelService<TypePaiement> getManagerTypePaiement() {
			return managerTypePaiement;
		}

		public void setManagerTypePaiement(
				ModelService<TypePaiement> managerTypePaiement) {
			this.managerTypePaiement = managerTypePaiement;
		}

		public ModelService<TypeFacture> getManagerTypefacture() {
			return managerTypefacture;
		}

		public void setManagerTypefacture(ModelService<TypeFacture> managerTypefacture) {
			this.managerTypefacture = managerTypefacture;
		}

		public ModelService<Facture_Article> getManagerFactArticle() {
			return managerFactArticle;
		}

		public void setManagerFactArticle(
				ModelService<Facture_Article> managerFactArticle) {
			this.managerFactArticle = managerFactArticle;
		}

		public ModelService<Facture> getManager() {
			return manager;
		}

		public void setManager(ModelService<Facture> manager) {
			this.manager = manager;
		}

		public ModelService<Avoir_Articles> getManagerAvoirarticles() {
			return managerAvoirarticles;
		}

		public void setManagerAvoirarticles(
				ModelService<Avoir_Articles> managerAvoirarticles) {
			this.managerAvoirarticles = managerAvoirarticles;
		}

		public ModelService<AvoirClient> getManageravoir() {
			return manageravoir;
		}

		public void setManageravoir(ModelService<AvoirClient> manageravoir) {
			this.manageravoir = manageravoir;
		}

		public Facture getFacttempo() {
			return facttempo;
		}

		public void setFacttempo(Facture facttempo) {
			this.facttempo = facttempo;
		}

		public List<String> getFiltredcontactrcpt() {
			return filtredcontactrcpt;
		}

		public void setFiltredcontactrcpt(List<String> filtredcontactrcpt) {
			this.filtredcontactrcpt = filtredcontactrcpt;
		} 
		
		
}
