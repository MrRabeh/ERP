/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;
 

import ims.model.entities.Article;
import ims.model.entities.AvoirClient;
import ims.model.entities.Avoir_Articles;
import ims.model.entities.CategorieArticle;
import ims.model.entities.Client;
import ims.model.entities.Facture;
import ims.model.entities.Facture_Article;
import ims.model.entities.Facture_Avoir;
import ims.model.entities.TypePaiement;
import ims.model.entities.Ville;
import ims.service.ModelService;
 



import ims.service.ModelServiceJDBC;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.RowEditEvent;

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
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

 

/**
 *
 * @author rabeh
 */
@ManagedBean
@SessionScoped
public class AvoirClientControlleur implements Serializable{
 
	
    /**
		 * creator RABEH TARIK
		 */
	private static final long serialVersionUID = 1L;
	 
	/**
     * Creates a new instance of AvoirClientControlleur
     */
   
    @ManagedProperty(value="#{avoirclientManager}")
    private ModelService<AvoirClient> manager;
    
	@ManagedProperty(value="#{FactureManager}")
	private ModelService<Facture> managerfact;
    
 	@ManagedProperty(value="#{ArticleManager}")
 	private ModelService<Article> managerObjectArticle;
 	
    @ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
    
	@ManagedProperty(value="#{ClientManager}")
	private ModelService<Client> managerclient;
	
	@ManagedProperty(value="#{CategorieArticleManager}")
    private ModelService<CategorieArticle> managercatArticle;
	
    @ManagedProperty(value="#{TypepaiementManager}")
    private ModelService<TypePaiement> managerTypePaiement;
    
    
    @ManagedProperty(value="#{AvoirArticlesManager}")
    private ModelService<Avoir_Articles> managerAvoirarticles;
    
    @ManagedProperty(value="#{TypepaiementManager}")
    private ModelService<TypePaiement> typepaiementmanager;
    
    @ManagedProperty(value="#{FactArticleManage}")
    private ModelService<Facture_Article> managerFactArticle;
    
	private AvoirClient avoirclient=new AvoirClient();
    private List<AvoirClient> avoirclients; 
	
	private List<Avoir_Articles> avoir_articles=new ArrayList<Avoir_Articles>();
	private Avoir_Articles avoirarticle=new Avoir_Articles();
	private Avoir_Articles suppavoirarticle=new Avoir_Articles();
	private Avoir_Articles avoirarticletempo;
	//attribut de controlleur
	
    private int idclient;
	private int idfacture;
	private Facture facttempo;
	private int idarticle;
	private int idcatarticle;
	private int idtypepaiement;
	
	private String designation;
	private double pu;
	private double pt;
	private String nvqte="";
	private String nvpu="";
	private double qte;
	private double TVA=0;
	private double totalHT=0;
	private double totalTTC=0;
	private boolean checkforfait;
	
	
	public AvoirClientControlleur() {
		super();
	}
	
	public String prepareCreate(){
		idfacture=0;
		avoirclient=new AvoirClient();
		Calendar cal = Calendar.getInstance();
    	String chaine=""+cal.get(Calendar.YEAR);
    	String[] tab=chaine.split("0");
    	int lastnumbre=managerjdbc.getLastNumbre("AvoirClient","numero_avoir",chaine);
    	lastnumbre++;
    	String nombre=String.format("%03d",lastnumbre);
		avoirclient.setNumero_avoir(tab[1]+"AV"+nombre);
		avoir_articles=new ArrayList<Avoir_Articles>();
		totalHT=0;
		totalTTC=0;
		TVA=0;
		return "insertavoirclient?faces-redirect=true";
	}
	
	public void actualiser(){
		avoirclients=null;
	}
	
	public String annuler(){
		idarticle=0;
		idcatarticle=0;
		idclient=0;
		idfacture=0;
		idtypepaiement=0;
		return "index?faces-redirect=true";
	}
	
	public void eventchangefactID(){
		List<Facture_Article> factarticletempo=managerFactArticle.getObjects(idfacture);
		if(factarticletempo!=null){
			totalHT=0;
			avoir_articles=new ArrayList<Avoir_Articles>();
			for (int i = 0; i < factarticletempo.size(); i++) {
				Avoir_Articles tempo=new Avoir_Articles();
				tempo.setArticle(factarticletempo.get(i).getArticle());
				tempo.setCategorieArticle(factarticletempo.get(i).getCategorieArticle());
				tempo.setChaineqte(factarticletempo.get(i).getLab());
				tempo.setDesignation(factarticletempo.get(i).getDesignation());
				tempo.setPt(factarticletempo.get(i).getPt());
				tempo.setPu(factarticletempo.get(i).getMontantglobal());
				tempo.setQte(factarticletempo.get(i).getQte());
				avoir_articles.add(tempo);
				totalHT+=factarticletempo.get(i).getPt();
			}
		}
		TVA=totalHT*0.2;
		totalTTC=totalHT+TVA;
		avoirarticle=null;
		avoirarticletempo=null;
	}
	
	public String prepareView(){
		System.out.println("ID="+avoirclient.getId());
		idclient=avoirclient.getClient().getIdclient();
		idtypepaiement=avoirclient.getTypepaiement().getId();
		Iterator<Facture_Avoir> factavoir=avoirclient.getFactures().iterator();
		int i=0;
		while(factavoir.hasNext()){
			if(i==0)
			facttempo=managerfact.getObject(factavoir.next().getFacture().getId());
			i++;
		}
		if(facttempo!=null)
			idfacture=facttempo.getId();
		
		avoir_articles=managerAvoirarticles.getObjects(avoirclient.getId());
		return "viewavoirclient?faces-redirect=true";
	}
	public void eventcheckforfait(){
		if(checkforfait==true)
			qte=1;
	}
	
	public void addart(){
		FacesMessage msg;
		try {
			Avoir_Articles tempo=new Avoir_Articles();
			tempo.setArticle(managerObjectArticle.getObject(idarticle));
			if(!designation.equals(""))
				tempo.setDesignation(designation);
			else
				tempo.setDesignation(tempo.getArticle().getDesignation());
			
			tempo.setCategorieArticle(managercatArticle.getObject(idcatarticle));
			if(checkforfait==true){
				tempo.setChaineqte("F");
				tempo.setQte(1);
				tempo.setPu(pu);
				tempo.setPt(pu*tempo.getQte());
			}else{
				tempo.setChaineqte(qte+"");
				tempo.setQte(qte);
				tempo.setPu(pu);
				tempo.setPt(pu*tempo.getQte());
			}

			totalHT=totalHT+tempo.getPt();
			TVA=totalHT*0.2;
			totalTTC=totalHT+TVA;
			avoir_articles.add(tempo);
		   msg = new FacesMessage("Article ajouter avec success");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	       designation="";
	       pu=0;
	       qte=0;
	       checkforfait=false;
	       pt=0;
	       idarticle=0;
	       idcatarticle=0;
	       avoirarticletempo=null;
		} catch (Exception e2) {
	           msg = new FacesMessage("Exception :",e2.getMessage());
	       FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	public void addartmodif(){
		FacesMessage msg;
		try {
			Avoir_Articles tempo=new Avoir_Articles();
			tempo.setArticle(managerObjectArticle.getObject(idarticle));
			if(!designation.equals(""))
				tempo.setDesignation(designation);
			else
				tempo.setDesignation(tempo.getArticle().getDesignation());
			
			tempo.setCategorieArticle(managercatArticle.getObject(idcatarticle));
			if(checkforfait==true){
				tempo.setChaineqte("F");
				tempo.setQte(1);
				tempo.setPu(pu);
				tempo.setPt(pu*qte);
			}else{
				tempo.setChaineqte(qte+"");
				tempo.setQte(qte);
				tempo.setPu(pu);
				tempo.setPt(pu*qte);
			}

			totalHT=totalHT+tempo.getPt();
			TVA=totalHT*0.2;
			totalTTC=totalHT+TVA;
			avoir_articles.add(tempo);
			
			tempo.setAvoir(avoirclient);
			managerAvoirarticles.insertObject(tempo);
		    managerjdbc.updatePrixAvoirClient(avoirclient);
		    avoirclient=manager.getByID(avoirclient.getId());
			msg = new FacesMessage("Article ajouter avec success");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	       msg = new FacesMessage("Avoir Client est Modfier avec success");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	       designation="";
	       pu=0;
	       qte=0;
	       checkforfait=false;
	       pt=0;
	       avoirarticletempo=null;
		} catch (Exception e2) {
	           msg = new FacesMessage("Exception :",e2.getMessage());
	       FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	public void removeart(){
		System.out.println("remove article");
		FacesMessage msg;
    	try{
    		avoir_articles.remove(suppavoirarticle);
    		totalHT=totalHT-suppavoirarticle.getPt();
    		TVA=totalHT*0.2;
    		totalTTC=totalHT+TVA;
    	       msg = new FacesMessage("Article est supprimer avec success");
               FacesContext.getCurrentInstance().addMessage(null, msg);
               suppavoirarticle=null;
               avoirarticle=null;
               avoirarticletempo=null;
    	}catch(Exception e){
            msg = new FacesMessage("Exception"+e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
	}
	
	public void deleteArticlesmodif(){
		System.out.println("remove article");
		FacesMessage msg;
    	try{
    		avoir_articles.remove(avoirarticle);
    		managerjdbc.deleteAvoirArticle(avoirarticle);
    		avoirclient.setTotalttcpaye(avoirclient.getTotalttc());
    		managerjdbc.updatePrixAvoirClient(avoirclient);
    		avoirclient=manager.getByID(avoirclient.getId());
    	       msg = new FacesMessage("Article est supprimer avec success");
               FacesContext.getCurrentInstance().addMessage(null, msg);
    	}catch(Exception e){
            msg = new FacesMessage("Exception"+e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
	}

	public void onCellEdit(){
		avoirclients=null;
		System.out.println("edition article");
		FacesMessage msg;
		try{
			avoirarticletempo.setChaineqte(""+avoirarticletempo.getQte());
			avoirarticletempo.setPt(avoirarticletempo.getPu()*avoirarticletempo.getQte());
			totalHT=0;
			for(int i=0;i<avoir_articles.size();i++)
				totalHT+=avoir_articles.get(i).getPt();
			TVA=totalHT*0.2;
			totalTTC=totalHT+TVA;
    	    msg = new FacesMessage("Article est Editer avec success");
              FacesContext.getCurrentInstance().addMessage(null, msg);
              nvpu="";
              nvqte="";
              avoirarticletempo=null;
    	}catch(Exception e){
            msg = new FacesMessage("Exception"+e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
	}
	
	public void onCellEditview(){
		avoirclients=null;
		System.out.println("edition article");
		FacesMessage msg;
		try{
			
			if(nvqte!="")
				{
					if(nvqte.equals("F") || nvqte.equals("f")){
						avoirarticle.setQte(1);
						avoirarticle.setChaineqte(nvqte);
						avoirarticle.setPt(avoirarticle.getPu()*avoirarticle.getQte());
					}else{
						avoirarticle.setQte(Double.parseDouble(nvqte));
						avoirarticle.setChaineqte(nvqte);
						avoirarticle.setPt(avoirarticle.getPu()*avoirarticle.getQte());
					}
				}
			if(nvpu!=""){
				avoirarticle.setPu(Double.parseDouble(nvpu));
				avoirarticle.setPt(avoirarticle.getPu()*avoirarticle.getQte());
			}
			avoirclient.setTotalht(0);
			for(int i=0;i<avoir_articles.size();i++)
				avoirclient.setTotalht(avoirclient.getTotalht()+avoir_articles.get(i).getPt());
			
			avoirclient.setTva(avoirclient.getTotalht()*0.2);
			avoirclient.setTotalttc(avoirclient.getTotalht()+avoirclient.getTva());
			managerjdbc.updateAvoir_Article(avoirarticle);
			managerjdbc.updatePrixAvoirClient(avoirclient);
			avoirclient.setTotalht(0);
			for(int i=0;i<avoir_articles.size();i++)
				avoirclient.setTotalht(avoirclient.getTotalht()+avoir_articles.get(i).getPt());
			
			avoirclient.setTva(avoirclient.getTotalht()*0.2);
			avoirclient.setTotalttc(avoirclient.getTotalht()+avoirclient.getTva());
    	    avoirclient.setTotalttcpaye(avoirclient.getTotalttc());
			msg = new FacesMessage("Article est Editer avec success");
              FacesContext.getCurrentInstance().addMessage(null, msg);
              nvpu="";
              nvqte="";
    	}catch(Exception e){
            msg = new FacesMessage("Exception"+e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
	}
 	
	public void insert(){
		   avoirclients=null;
	    	System.out.println("INSERT");
	    	FacesMessage msg;
	    	try{
	    		avoirclient.setClient(managerclient.getObject(idclient));
	    		avoirclient.setTypepaiement(typepaiementmanager.getObject(idtypepaiement));
	    		avoirclient.setTotalht(totalHT);
	    		avoirclient.setTva(TVA);
	    		avoirclient.setTotalttc(totalTTC);
	    		avoirclient.setTotalttcpaye(totalTTC);
	    		avoirclient.setActiver(true);
	    		manager.insertObject(avoirclient);
	    		for(int i=0;i<avoir_articles.size();i++){
	    			avoir_articles.get(i).setAvoir(avoirclient);
	    			managerAvoirarticles.insertObject(avoir_articles.get(i));
	    		}
	    		managerjdbc.insertFactureAvoir(idfacture,avoirclient.getId());
	    		managerjdbc.Annuler(managerfact.getObject(idfacture));
	           msg = new FacesMessage("vous avez creer une avoir Client avec success");
	           avoir_articles=new ArrayList<Avoir_Articles>();
	           totalHT=0;
	           totalTTC=0;
	           TVA=0;
	           avoirclient=new AvoirClient();
			Calendar cal = Calendar.getInstance();
	    	String chaine=""+cal.get(Calendar.YEAR);
	    	String[] tab=chaine.split("0");
	    	int lastnumbre=managerjdbc.getLastNumbre("AvoirClient","numero_avoir",chaine);
	    	lastnumbre++;
	    	String nombre=String.format("%03d",lastnumbre);
			avoirclient.setNumero_avoir(tab[1]+"AV"+nombre);
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        } catch (Exception e) {
	               msg = new FacesMessage("Exception"+e.getMessage());
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        }   
	}
 	
 	public void update(){
 		FacesMessage msg;
 		avoirclients=null;
 		try {
 	 		Facture_Avoir objettempo=new Facture_Avoir();
 	 		objettempo.setAvoir(avoirclient);
 	 		objettempo.setFacture(facttempo);
 	 		managerjdbc.deleteFactureAvoir(objettempo);
 	 		managerjdbc.confirmer(facttempo);
 	 		managerjdbc.insertFactureAvoir(idfacture,avoirclient.getId());
 	 		managerjdbc.Annuler(managerfact.getObject(idfacture));
 	 		managerjdbc.updateAvoirClient(avoirclient);
 	       msg = new FacesMessage("Avoir Client est Modifier avec success");
 	       FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
		    msg = new FacesMessage("Exception"+e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}

 	}
	 
 	public void delete(){
	        FacesMessage msg;
	        avoirclients=null;
	        try {
	        managerjdbc.deleteAvoir(avoirclient);
	       msg = new FacesMessage("avoir bien Supprimer");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        } catch (Exception e) {
	            msg = new FacesMessage("Exception"+e.getMessage());
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	        }   
	  }
	
	public void imprimerfont(){
        String dest="C:/IMS.pdf";
        try {
        File file = new File(dest);
        file.getParentFile().mkdirs();	
        		createPdf(dest);
        		createPdfImageavecfond();
			} catch (DocumentException
					| IOException | NullPointerException e) {
				
				e.printStackTrace();
			}
	}
	
	public void imprimer(){
        String dest="C:/IMS.pdf";
        try {
        File file = new File(dest);
        file.getParentFile().mkdirs();	
        	createPdf(dest);
				createPdfImage();
			} catch (DocumentException
					| IOException | NullPointerException e) {
				e.printStackTrace();
			}
	}
	
    public void createPdfImage() throws FileNotFoundException, DocumentException, IOException ,NullPointerException{

        PdfReader reader;			
        reader = new PdfReader("C:/IMS.pdf");

        int n = reader.getNumberOfPages(); 
        // Create a stamper that will copy the document to a new file 
        //PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("C:/boncmd.pdf")); 
        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        PdfStamper stamp = new PdfStamper(reader,response.getOutputStream()); 
        	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
        int i = 1; 
        com.itextpdf.text.pdf.PdfContentByte under; 
        com.itextpdf.text.pdf.PdfContentByte over; 
        //Image img = Image.getInstance(webroot+"\\fondfacture.png");
        Image img = Image.getInstance(webroot+"\\fondlogoancien.png"); 
        img.scaleAbsoluteHeight(PageSize.A4.getHeight());
        img.scaleAbsoluteWidth(PageSize.A4.getWidth());
        img.setAbsolutePosition(0, 0);
        BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI, BaseFont.EMBEDDED); 


        for(i=1;i<=n;i++){
        under = stamp.getUnderContent(i); 
        //under.addImage(img);
        over = stamp.getOverContent(i); 
        over.beginText(); 
        over.setFontAndSize(bf, 18);
        over.endText(); 
        } 
        stamp.close();	

        }
    
    public void createPdfImageavecfond() throws FileNotFoundException, DocumentException, IOException ,NullPointerException{

        PdfReader reader;			
        reader = new PdfReader("C:/IMS.pdf");

        int n = reader.getNumberOfPages(); 
        // Create a stamper that will copy the document to a new file 
        //PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("C:/boncmd.pdf")); 
        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        PdfStamper stamp = new PdfStamper(reader,response.getOutputStream()); 
        	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
        int i = 1; 
        com.itextpdf.text.pdf.PdfContentByte under; 
        com.itextpdf.text.pdf.PdfContentByte over; 
        //Image img = Image.getInstance(webroot+"\\fondfacture.png");
        Image img = Image.getInstance(webroot+"\\fondlogoancien.png"); 
        img.scaleAbsoluteHeight(PageSize.A4.getHeight());
        img.scaleAbsoluteWidth(PageSize.A4.getWidth());
        img.setAbsolutePosition(0, 0);
        BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI, BaseFont.EMBEDDED); 


        for(i=1;i<=n;i++){
        under = stamp.getUnderContent(i); 
        under.addImage(img);
        over = stamp.getOverContent(i); 
        over.beginText(); 
        over.setFontAndSize(bf, 18);
        over.endText(); 
        } 
        stamp.close();	

        }
	
   	public void createPdf(String dest){
		   System.out.println("Imprimer......");
		   DateFormat format=new SimpleDateFormat("dd/MM/yy");
		   System.out.println("Imprimer*************** SUCCES");
		   String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
	       System.out.println("webroot");
	   		System.out.println(webroot); 
	   		System.out.println("webroot FIN");
	   		Paragraph saute=new Paragraph("\n");
	       Document document = new Document(PageSize.A4);
	       HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	       try {
	    	   Font ftext=new Font(Font.FontFamily.HELVETICA,9,Font.NORMAL,new BaseColor(Color.black));
	    	   Font ftext10=new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL,new BaseColor(Color.black));
	            Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
	            Font forange=new Font(Font.FontFamily.HELVETICA,10,Font.UNDERLINE,new BaseColor(Color.black));
	    	   PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
	    	   System.out.println("ID===>"+avoirclient.getId());
	           response.setContentType("application/pdf");
	           response.setHeader("Content-Disposition", "inline; filename="+avoirclient.getNumero_avoir()+".pdf");
	           System.out.println("application 1");
	           document.open();
	           document.setMargins(36, 36, 108, 180);
	           System.out.println("application 2");
	           document.add(saute);
	           document.add(saute);
	           document.add(saute);
	           DecimalFormat df = new DecimalFormat();
	           df.setDecimalSeparatorAlwaysShown(true);
	           df.setMaximumFractionDigits(2);
	           df.setMinimumFractionDigits(2);
	           avoirclient=manager.getObject(avoirclient.getId());
	           
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
					facttempo=managerfact.getByID(factavoir.next().getFacture().getId());
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
					        System.out.println("============================================================================");
					        System.out.println("=====avoirclient.getTotalttc()============"+avoirclient.getTotalht()+"=============");
					        System.out.println("============================================================================");
					        System.out.println("=====avoirclient.getTotalttc() df============"+df.format(avoirclient.getTotalht())+"=============");
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
	        	   System.out.println("-----------Exception----------");
	        	   if(avoirclient.getTypepaiement().getTypepaiement().equals("MAD"))
	        		   pie.add(new Phrase(formatter.format(avoirclient.getTotalttcpaye())+" Dirhams et zero centimes Toutes Taxes Comprises",ftextgra));
	        	   else
	        		   pie.add(new Phrase(formatter.format(avoirclient.getTotalht())+" Dirhams et zero centimes Toutes Taxes Comprises",ftextgra));
	           }
	           pie.add(saute);
	           pie.setIndentationLeft(53);
	           document.add(pie);
	           System.out.println("Imprimer***************FIN SUCCES");
	           document.addAuthor("IMS Technology");
	           document.addTitle("Avoir");
	           document.addSubject("Facture N°"+avoirclient.getNumero_avoir());
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
	     
	     public void onRowEdit(RowEditEvent event) {
	         FacesMessage msg;
	         try {
	          
		            msg = new FacesMessage("Modifier");
			        FacesContext.getCurrentInstance().addMessage(null, msg);
	         } catch (Exception e) {
	            msg = new FacesMessage("Exception");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	         }
	        
	    }

		public ModelService<AvoirClient> getManager() {
			return manager;
		}

		public void setManager(ModelService<AvoirClient> manager) {
			this.manager = manager;
		}

		public AvoirClient getAvoirclient() {
			return avoirclient;
		}

		public void setAvoirclient(AvoirClient avoirclient) {
			this.avoirclient = avoirclient;
		}

		public List<AvoirClient> getAvoirclients() {
			if(avoirclients==null)
				avoirclients=manager.getObject();
			return avoirclients;
		}

		public void setAvoirclients(List<AvoirClient> avoirclients) {
			this.avoirclients = avoirclients;
		}

		public int getIdclient() {
			return idclient;
		}

		public void setIdclient(int idclient) {
			this.idclient = idclient;
		}

		public int getIdfacture() {
			return idfacture;
		}

		public void setIdfacture(int idfacture) {
			this.idfacture = idfacture;
		}

		public List<Avoir_Articles> getAvoir_articles() {
			return avoir_articles;
		}

		public void setAvoir_articles(List<Avoir_Articles> avoir_articles) {
			this.avoir_articles = avoir_articles;
		}

		public Avoir_Articles getAvoirarticle() {
			return avoirarticle;
		}

		public void setAvoirarticle(Avoir_Articles avoirarticle) {
			this.avoirarticle = avoirarticle;
		}

		public int getIdarticle() {
			return idarticle;
		}

		public void setIdarticle(int idarticle) {
			this.idarticle = idarticle;
		}

		public double getTVA() {
			return TVA;
		}

		public void setTVA(double tVA) {
			TVA = tVA;
		}

		public double getTotalHT() {
			return totalHT;
		}

		public void setTotalHT(double totalHT) {
			this.totalHT = totalHT;
		}

		public double getTotalTTC() {
			return totalTTC;
		}

		public void setTotalTTC(double totalTTC) {
			this.totalTTC = totalTTC;
		}

		public int getIdcatarticle() {
			return idcatarticle;
		}

		public void setIdcatarticle(int idcatarticle) {
			this.idcatarticle = idcatarticle;
		}

		public boolean isCheckforfait() {
			return checkforfait;
		}

		public void setCheckforfait(boolean checkforfait) {
			this.checkforfait = checkforfait;
		}

		public double getPu() {
			return pu;
		}

		public void setPu(double pu) {
			this.pu = pu;
		}

		public double getPt() {
			return pt;
		}

		public void setPt(double pt) {
			this.pt = pt;
		}


		public double getQte() {
			return qte;
		}

		public void setQte(double qte) {
			this.qte = qte;
		}

		public String getDesignation() {
			return designation;
		}

		public void setDesignation(String designation) {
			this.designation = designation;
		}

		public ModelService<Article> getManagerObjectArticle() {
			return managerObjectArticle;
		}

		public void setManagerObjectArticle(ModelService<Article> managerObjectArticle) {
			this.managerObjectArticle = managerObjectArticle;
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

		public ModelService<CategorieArticle> getManagercatArticle() {
			return managercatArticle;
		}

		public void setManagercatArticle(
				ModelService<CategorieArticle> managercatArticle) {
			this.managercatArticle = managercatArticle;
		}

		public ModelService<TypePaiement> getManagerTypePaiement() {
			return managerTypePaiement;
		}

		public void setManagerTypePaiement(
				ModelService<TypePaiement> managerTypePaiement) {
			this.managerTypePaiement = managerTypePaiement;
		}

		public ModelService<Facture> getManagerfact() {
			return managerfact;
		}

		public void setManagerfact(ModelService<Facture> managerfact) {
			this.managerfact = managerfact;
		}

		public ModelService<Avoir_Articles> getManagerAvoirarticles() {
			return managerAvoirarticles;
		}

		public void setManagerAvoirarticles(
				ModelService<Avoir_Articles> managerAvoirarticles) {
			this.managerAvoirarticles = managerAvoirarticles;
		}

		public int getIdtypepaiement() {
			return idtypepaiement;
		}

		public void setIdtypepaiement(int idtypepaiement) {
			this.idtypepaiement = idtypepaiement;
		}

		public ModelService<TypePaiement> getTypepaiementmanager() {
			return typepaiementmanager;
		}

		public void setTypepaiementmanager(
				ModelService<TypePaiement> typepaiementmanager) {
			this.typepaiementmanager = typepaiementmanager;
		}

		public Facture getFacttempo() {
			return facttempo;
		}

		public void setFacttempo(Facture facttempo) {
			this.facttempo = facttempo;
		}

		public String getNvqte() {
			return nvqte;
		}

		public void setNvqte(String nvqte) {
			this.nvqte = nvqte;
		}

		public String getNvpu() {
			return nvpu;
		}

		public void setNvpu(String nvpu) {
			this.nvpu = nvpu;
		}

		public ModelService<Facture_Article> getManagerFactArticle() {
			return managerFactArticle;
		}

		public void setManagerFactArticle(
				ModelService<Facture_Article> managerFactArticle) {
			this.managerFactArticle = managerFactArticle;
		}

		public Avoir_Articles getSuppavoirarticle() {
			return suppavoirarticle;
		}

		public void setSuppavoirarticle(Avoir_Articles suppavoirarticle) {
			this.suppavoirarticle = suppavoirarticle;
		}

		public Avoir_Articles getAvoirarticletempo() {
			return avoirarticletempo;
		}

		public void setAvoirarticletempo(Avoir_Articles avoirarticletempo) {
			this.avoirarticletempo = avoirarticletempo;
		}
	     
	     
 
}

