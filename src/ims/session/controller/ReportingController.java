package ims.session.controller;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ims.Exception.ImsErpException;
import ims.model.entities.Article;
import ims.model.entities.BonCommande;
import ims.model.entities.Boncommande_Article;
import ims.model.entities.Client;
import ims.model.entities.Facture;
import ims.model.entities.FactureFournisseur;
import ims.model.entities.FactureFournisseur_articles;
import ims.model.entities.Facture_Article;
import ims.model.entities.Offre;
import ims.model.entities.Offre_Article;
import ims.model.entities.Produit;
import ims.model.entities.Stock;
import ims.model.entities.Stock_Livraison;
import ims.model.entities.years;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;


@ManagedBean
@ApplicationScoped
public class ReportingController implements Serializable {

	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
		* creator RABEH TARIK
	    **/
	
	@ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
	
	
	@ManagedProperty(value="#{yearsManager}")
	private ModelService<years> manageryears;
	
	@ManagedProperty(value="#{FactureManager}")
	private ModelService<Facture> manager;
	
    @ManagedProperty(value="#{FactFournisseurManager}")
    private ModelService<FactureFournisseur> managerFactFournisseur;
    
	@ManagedProperty(value="#{boncommandeManager}")
	private ModelService<BonCommande> managercommande;
    
	@ManagedProperty(value="#{ClientManager}")
	private ModelService<Client> managerclient;
	
    @ManagedProperty(value="#{OffreManager}")
    private ModelService<Offre> manageroffre;
    
    @ManagedProperty(value="#{OffreArticleManager}")
    private ModelService<Offre_Article> managerOffrearticle;
    
	@ManagedProperty(value="#{ArticleManager}")
    private ModelService<Article> managerArticle;
	
    @ManagedProperty(value="#{BoncommandeArticleManager}")
    private ModelService<Boncommande_Article> managerarticlebc;
    
    @ManagedProperty(value="#{FactArticleManage}")
    private ModelService<Facture_Article> managerFactArticle;
    
    @ManagedProperty(value="#{FactFournisseurArticleManager}")
    private ModelService<FactureFournisseur_articles> managerFactfournisseurArticle;
    
	@ManagedProperty(value="#{StockLivraisonManager}")
	private ModelService<Stock_Livraison> managerstocklivraison;
	
	@ManagedProperty(value="#{ProduitManager}")
	private ModelService<Produit> managerProduit;
    
	 @ManagedProperty(value = "#{managerDataBase}")
	 private ManagerDataBase managerApplication;
    
	private int produitID;
     private String  articleID;
    private String idannee="";
    private String clientID="";
    private String groupclientID;
    private String fournisseurID="";
    private String etat="";
    private String constat="";
    private String typeoffre="";
    private String table;
    
    private List<years> listeyears;
    private List<Facture> facturesclient;
    private List<FactureFournisseur> facturesfournisseur;
    private List<BonCommande> boncommandesfournisseur;
    private List<Offre> offreclients;
    private List<Offre_Article> offresArticle;
    
    private List<Boncommande_Article> bcarticles;
    private List<Facture_Article> factureArticles;
    private List<FactureFournisseur_articles> factfournisseurArticles;
    private List<Stock_Livraison> stocksList;
    
    private double totalnetMAD=0;
    private double totalnet=0;
    private double totalreste=0;
    private double totalavance=0;
    private List<Client> clients;
    private List<Stock> stocks;
    
    public void annuler(){
    	idannee="";
    	clientID="";
    	fournisseurID="";
    	etat="";
    	constat="";
    }
    
    private List<Article> articles;
    
    public void eventchangearticle(){
    	articles=managerArticle.getObjects(produitID);
    }
    
    public void eventchangegroupclient(){
    	if(groupclientID.equals(""))
    		clients=managerclient.getObject();
    	else
    		clients=managerclient.getObjects(Integer.parseInt(groupclientID));
    }
    
    public void imprimerParArticle(){
    	
    	if(table.equals("") || produitID==0){
    		FacesMessage msg;
	  		
    		if(table.equals("0"))
    			msg = new FacesMessage("Sélectionner la Table");
    		else
    			msg = new FacesMessage("Sélectionner le Produit");
    		FacesContext.getCurrentInstance().addMessage(null, msg);
    	}else{
        	String dest="D:/IMS.pdf";
            try {
            File file = new File(dest);
            file.getParentFile().mkdirs();
            if(table.equals("Devis"))
            	createPdfoffreParArticle(dest);
            else if(table.equals("boncommande"))
            	createPdfBCParArticle(dest);
            else if(table.equals("facture"))
            	createPdffactureParArticle(dest);
            else if(table.equals("facturefournisseur"))
            	createPdffacturefournisseurParArticle(dest);
            else if(table.equals("bnlivraison"))
            	createPdfbnlivraisonParArticle(dest);
    				createPdfImage();
    			} catch (DocumentException
    					| IOException | NullPointerException e) {
    				e.printStackTrace();
    			}
    		
    	}
    	produitID=0;
    	articleID="0";
    	table="0";
    }
    
    public void imprimerStock(){

        	String dest="D:/IMS.pdf";
            try {
            	File file = new File(dest);
            	file.getParentFile().mkdirs();
            	createPdfStock(dest);
    			createPdfImage();
    			} catch (DocumentException
    					| IOException | NullPointerException e) {
    				e.printStackTrace();
    			}
    	produitID=0;
    	articleID="0";
    }
    
	private void createPdfStock(String dest) {
		 System.out.println("Imprimer Stock    SUCCES");
		   SimpleDateFormat ft= new SimpleDateFormat("dd/MM/yy");
	   		Paragraph saute=new Paragraph("\n");
	       Document document = new Document(PageSize.A4);
	       document.setMargins(80, 20, 0, 0);
	       HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	       
	       try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
	    	   document.open();
	    	   response.setContentType("application/pdf");
	           response.setHeader("Content-Disposition", "inline; filename=IMSStockParArticle.pdf");

	           document.add(saute);
	           
	           Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
	           Font ftext=new Font(Font.FontFamily.HELVETICA,8,Font.NORMAL,new BaseColor(Color.black));
	           String str_Titre="Relevé Stock Pour un Toutes les Articles";
	           if(articleID!=null){
	        	   Article art;
	        	   if(!articleID.equals("0")){
	        		   art=managerArticle.getObject(Integer.parseInt(articleID));
	        		   str_Titre="Relevé Stock Pour un l'article de Reference"+art.getRef();
	        	   }	
	           }
	           
	           System.out.println("Stock ..................");
	          
	           Paragraph titre=new Paragraph(str_Titre,ftextgra);
	           titre.setAlignment(Element.ALIGN_CENTER);
	           document.add(titre);
	           document.add(saute);
	            titre=new Paragraph("Date d'édition :"+ft.format(new Date()),ftextgra);
	            titre.setAlignment(Element.ALIGN_CENTER);
	            document.add(titre);
	           document.add(saute);
	           
	           		if(produitID!=0){
	           			titre=new Paragraph("Produit : "+managerProduit.getObject(produitID).getTypeproduit(),ftextgra);
		  	            document.add(titre);
	           		}
	           		document.add(saute);
	  	          	PdfPTable table;
		        	table = new PdfPTable(8);
			        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
			        table.setWidthPercentage(106);
			        table.setWidths(new float[]{50,60,40,30,30,50,50,50});
	           
		       PdfPCell c1 = new PdfPCell(new Phrase("Ref. Article",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Numero Series",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Date Stock",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Qte Initial",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("le reste de Qte",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Fournisseur",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Client",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Client Final",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           table.setHeaderRows(1);
	           
			        	   System.out.println("RECUP Stocks ...");
			        	   managerApplication.setStocks(null);
			        	   stocks=managerApplication.getStocks();
			        	   System.out.println("RECUP Stock ...SUCCESS");
				           if(stocks!=null){  
				        	   System.out.println("SIZE==>"+stocks.size());
				        	   int qte;
					           for(int i=0;i<stocks.size();i++){
					        	   
					        	   c1 = new PdfPCell(new Phrase(""+stocks.get(i).getArticle().getRef(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           String numserie="";
						           
						           /////////////////////////////////////////////////////////
						           if(!stocks.get(i).getNumserie().equals("")){
							           String [] tablenumeroserie=stocks.get(i).getNumserie().split(",");
							           System.out.println("table pret =>"+tablenumeroserie.length);
							           
							           for(int j = 0; j < tablenumeroserie.length / 2; j++)
							           {
							               String temp = tablenumeroserie[j];
							               tablenumeroserie[j] = tablenumeroserie[tablenumeroserie.length - i - 1];
							               tablenumeroserie[tablenumeroserie.length - j - 1] = temp;
							           }
							           System.out.println(".... table est reverser ....");
							           qte=(int)stocks.get(i).getQte() ;
							           if(qte==tablenumeroserie.length){
								           System.out.println(qte);
								           for(int j=0;j<qte;j++)
								        	   numserie+=tablenumeroserie[j]+'\n';
							           }

							           //////////////////////////////////////////////////////////// 
						           }
			
						           
						          
						           c1 = new PdfPCell(new Phrase(numserie,ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+stocks.get(i).getDatestock(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+stocks.get(i).getQteinital(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+stocks.get(i).getQte(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+stocks.get(i).getFournisseur().getNomsociete(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+stocks.get(i).getClient().getSociete(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+stocks.get(i).getClientFinal().getSociete(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
					           }
				           }else{
				        	   System.out.println("STOCK ======NULL");
				           }

	          
	           
	           document.add(table);
	           
	           System.out.println("Imprimer***************FIN SUCCES");
	           document.addAuthor("IMS Technology");
	           document.addTitle("Facture");
	           document.addCreator("RABEH");
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	    	typeoffre="";
	       } catch (DocumentException | FileNotFoundException | NullPointerException de) {
				FacesMessage msg = new FacesMessage("Probleme de generation de PDF","contacter l'administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
	           System.out.println("ERROR REPORTING STOCK PDF "+de.getMessage());
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	       }
		
		
	}
    
    
    private void createPdfbnlivraisonParArticle(String dest) {
    	
    	System.out.println("Imprimer*************** SUCCES");
		SimpleDateFormat ft= new SimpleDateFormat("dd/MM/yy");
   		Paragraph saute=new Paragraph("\n");
   		Document document = new Document(PageSize.A4);
   		document.setMargins(80, 20, 0, 0);
   		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
       
   		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
			document.open();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline; filename=LivraisonParArticle.pdf");

			document.add(saute);		
           
			Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
			Font ftext=new Font(Font.FontFamily.HELVETICA,8,Font.NORMAL,new BaseColor(Color.black));
			String str_Titre="Relevé Bon Livraison Pour Toutes les Articles";
			if(articleID!=null){
        	   Article art;
        	   if(!articleID.equals("0")){
        		   art=managerArticle.getObject(Integer.parseInt(articleID));
        		   str_Titre="Relevé Bon Livraison Pour l'article de Reference "+art.getRef();
        	   }	
           }
          
             
           
			Paragraph titre=new Paragraph(str_Titre,ftextgra);
           	titre.setAlignment(Element.ALIGN_CENTER);
           	document.add(titre);
           	document.add(saute);
            titre=new Paragraph("Date d'édition :"+ft.format(new Date()),ftextgra);
            titre.setAlignment(Element.ALIGN_CENTER);
            document.add(titre);
            document.add(saute);
            
	           System.out.println("ID Produit");
	           System.out.println(produitID);
	           
	            	titre=new Paragraph("Produit : "+managerProduit.getObject(produitID).getTypeproduit(),ftextgra);
	  	            document.add(titre);
	  	          document.add(saute);
           PdfPTable table;
        	   table = new PdfPTable(5);
	           table.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           table.setWidthPercentage(106);
	           table.setWidths(new float[]{50,50,50,50,50});
           
           
	       PdfPCell c1 = new PdfPCell(new Phrase("N°BL",ftextgra));
           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
           table.addCell(c1);
           
           c1 = new PdfPCell(new Phrase("Date",ftextgra));
           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
           table.addCell(c1);
           
           c1 = new PdfPCell(new Phrase("Client",ftextgra));
           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
           table.addCell(c1);
           
           c1 = new PdfPCell(new Phrase("Qte",ftextgra));
           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
           table.addCell(c1);
           
           c1 = new PdfPCell(new Phrase("Numero Serie",ftextgra));
           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
           table.addCell(c1);
           
           table.setHeaderRows(1);
           
		        	   System.out.println("RECUP BL ...");
		        	   stocksList=managerstocklivraison.getByNames("stat",idannee,articleID);
		        	   System.out.println("RECUP BL ...SUCCESS");
		        	   System.out.println("SIZE==>"+stocksList.size());
			           if(stocksList!=null){  
				           for(int i=0;i<stocksList.size();i++)
				           {
				        	   if(stocksList.get(i).getLivraison()!=null)
				        	   c1 = new PdfPCell(new Phrase(stocksList.get(i).getLivraison().getRef(),ftext));
				        	   else
				        		   c1 = new PdfPCell(new Phrase(" ",ftext));
					           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
					           table.addCell(c1);
					           
					           if(stocksList.get(i).getLivraison()!=null)
					        	   c1 = new PdfPCell(new Phrase(ft.format(stocksList.get(i).getLivraison().getDatebl()),ftext));
					           else
					        	   c1 = new PdfPCell(new Phrase(" ",ftext));
					           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
					           table.addCell(c1);
					           
					           if(stocksList.get(i).getLivraison()!=null){
					        	   if(stocksList.get(i).getLivraison().getClient()!=null)
					        		   c1 = new PdfPCell(new Phrase(stocksList.get(i).getLivraison().getClient().getSociete(),ftext));
					        	   else
					        		   c1 = new PdfPCell(new Phrase("",ftext));
					           }else
					        	   c1 = new PdfPCell(new Phrase(" ",ftext));
					           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
					           table.addCell(c1);
					           
					           
					           c1 = new PdfPCell(new Phrase(""+stocksList.get(i).getQte(),ftext));
					           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(c1);
					           
					           c1 = new PdfPCell(new Phrase(""+stocksList.get(i).getNumeroserielivre(),ftext));
					           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(c1);
					           
					      
					           
					          
				           }
			           }

          
           
           document.add(table);
           
           System.out.println("Imprimer***************FIN SUCCES");
           document.addAuthor("IMS Technology");
           document.addTitle("Facture");
           document.addCreator("RABEH");
           document.close();
           FacesContext.getCurrentInstance().responseComplete();
       	idannee="";
    	clientID="";
    	fournisseurID="";
    	etat="";
    	constat="";
    	typeoffre="";
       } catch (DocumentException | FileNotFoundException | NullPointerException de) {
			FacesMessage msg = new FacesMessage("Probleme de generation de PDF","contacter l'administration");
			FacesContext.getCurrentInstance().addMessage(null, msg);
           System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF "+de.getMessage());
           document.close();
           FacesContext.getCurrentInstance().responseComplete();
       	idannee="";
    	clientID="";
    	fournisseurID="";
    	etat="";
    	constat="";
       }
	
		
	}

	private void createPdffacturefournisseurParArticle(String dest) {
		 
			System.out.println("Imprimer*************** SUCCES");
			SimpleDateFormat ft= new SimpleDateFormat("dd/MM/yy");
	   		Paragraph saute=new Paragraph("\n");
	   		Document document = new Document(PageSize.A4);
	   		document.setMargins(80, 20, 0, 0);
	   		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	       
	   		try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
				document.open();
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "inline; filename=FactureFournisseurParArticle.pdf");

				document.add(saute);		
	           
				Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
				Font ftext=new Font(Font.FontFamily.HELVETICA,8,Font.NORMAL,new BaseColor(Color.black));
				String str_Titre="Relevé Facture Fournisseur Pour un Toutes les Articles";
				if(articleID!=null){
	        	   Article art;
	        	   if(!articleID.equals("0")){
	        		   art=managerArticle.getObject(Integer.parseInt(articleID));
	        		   str_Titre="Relevé Facture Fournisseur Pour un l'article de Reference "+art.getRef();
	        	   }	
	           }
	          
	             
	           
				Paragraph titre=new Paragraph(str_Titre,ftextgra);
	           	titre.setAlignment(Element.ALIGN_CENTER);
	           	document.add(titre);
	           	document.add(saute);
	            titre=new Paragraph("Date d'édition :"+ft.format(new Date()),ftextgra);
	            titre.setAlignment(Element.ALIGN_CENTER);
	            document.add(titre);
	            document.add(saute);
	            
		           System.out.println("ID Produit");
		           System.out.println(produitID);
		           
		            	titre=new Paragraph("Produit : "+managerProduit.getObject(produitID).getTypeproduit(),ftextgra);
		  	            document.add(titre);
		  	          document.add(saute);
	           PdfPTable table;
	        	   table = new PdfPTable(6);
		           table.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.setWidthPercentage(106);
		           table.setWidths(new float[]{50,50,50,50,50,50});
	           
	           
		       PdfPCell c1 = new PdfPCell(new Phrase("N°Facture",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Date",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Fournisseur",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("HT",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("PU ",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Qte",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	          
	           
	           table.setHeaderRows(1);
	           
			        	   System.out.println("RECUP Fact ...");
			        	   factfournisseurArticles=managerFactfournisseurArticle.getByNames("stat",idannee,articleID);
			        	   System.out.println("RECUP fact ...SUCCESS");
			        	   System.out.println("SIZE==>"+factfournisseurArticles.size());
				           if(factfournisseurArticles!=null){  
					           for(int i=0;i<factfournisseurArticles.size();i++)
					           {
					        	   if(factfournisseurArticles.get(i).getFacture()!=null)
					        	   c1 = new PdfPCell(new Phrase(factfournisseurArticles.get(i).getFacture().getNum_facture(),ftext));
					        	   else
					        		   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(factfournisseurArticles.get(i).getFacture()!=null)
						        	   c1 = new PdfPCell(new Phrase(ft.format(factfournisseurArticles.get(i).getFacture().getDatefacture()),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(factfournisseurArticles.get(i).getFacture()!=null){
						        	   if(factfournisseurArticles.get(i).getFacture().getCommandefournissuer()!=null){
						        		   if(factfournisseurArticles.get(i).getFacture().getCommandefournissuer().getFournisseur()!=null)
						        			   c1 = new PdfPCell(new Phrase(factfournisseurArticles.get(i).getFacture().getCommandefournissuer().getFournisseur().getNomsociete(),ftext));
						        		   else
						        			   c1 = new PdfPCell(new Phrase(" ",ftext));
						        	   }else
						        		   c1 = new PdfPCell(new Phrase(" ",ftext));
						           }
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(factfournisseurArticles.get(i).getFacture()!=null)
						        	   c1 = new PdfPCell(new Phrase(factfournisseurArticles.get(i).getFacture().getTotalht()+"",ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+factfournisseurArticles.get(i).getPu(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+factfournisseurArticles.get(i).getQte(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						      
						           
						          
					           }
				           }

	          
	           
	           document.add(table);
	           
	           System.out.println("Imprimer***************FIN SUCCES");
	           document.addAuthor("IMS Technology");
	           document.addTitle("Facture");
	           document.addCreator("RABEH");
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	    	typeoffre="";
	       } catch (DocumentException | FileNotFoundException | NullPointerException de) {
				FacesMessage msg = new FacesMessage("Probleme de generation de PDF","contacter l'administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
	           System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF "+de.getMessage());
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	       }
		
	}

	private void createPdffactureParArticle(String dest) {
		 System.out.println("Imprimer*************** SUCCES");
		   SimpleDateFormat ft= new SimpleDateFormat("dd/MM/yy");
	   		Paragraph saute=new Paragraph("\n");
	       Document document = new Document(PageSize.A4);
	       document.setMargins(80, 20, 0, 0);
	       HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	       
	       try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
	    	   document.open();
	    	   response.setContentType("application/pdf");
	           response.setHeader("Content-Disposition", "inline; filename=IMSFactureParArticle.pdf");

	           document.add(saute);
	           
	           Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
	           Font ftext=new Font(Font.FontFamily.HELVETICA,8,Font.NORMAL,new BaseColor(Color.black));
	           String str_Titre="Relevé Facture Pour un Toutes les Articles";
	           if(articleID!=null){
	        	   Article art;
	        	   if(!articleID.equals("0")){
	        		   art=managerArticle.getObject(Integer.parseInt(articleID));
	        		   str_Titre="Relevé Facture Pour un l'article de Reference "+art.getRef();
	        	   }	
	           }
	          
	             
	           
	           Paragraph titre=new Paragraph(str_Titre,ftextgra);
	           titre.setAlignment(Element.ALIGN_CENTER);
	           document.add(titre);
	           document.add(saute);
	            titre=new Paragraph("Date d'édition :"+ft.format(new Date()),ftextgra);
	            titre.setAlignment(Element.ALIGN_CENTER);
	            document.add(titre);
	           document.add(saute);
	           
	           System.out.println("ID Produit");
	           System.out.println(produitID);
	           
	            	titre=new Paragraph("Produit : "+managerProduit.getObject(produitID).getTypeproduit(),ftextgra);
	  	            document.add(titre);
	  	          document.add(saute);
	           PdfPTable table;
	        	   table = new PdfPTable(7);
		           table.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.setWidthPercentage(106);
		           table.setWidths(new float[]{50,50,50,50,50,50,50});
	           
	           
		       PdfPCell c1 = new PdfPCell(new Phrase("N°Facture",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Date",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Client",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("HT",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("PU ",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Qte",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Prix Marge",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           table.setHeaderRows(1);
	           
			        	   System.out.println("RECUP Fact ...");
			        	   factureArticles=managerFactArticle.getByNames("stat",idannee,articleID);
			        	   System.out.println("RECUP fact ...SUCCESS");
			        	   System.out.println("SIZE==>"+factureArticles.size());
				           if(factureArticles!=null){  
					           for(int i=0;i<factureArticles.size();i++)
					           {
					        	   if(factureArticles.get(i).getFacture()!=null)
					        	   c1 = new PdfPCell(new Phrase(factureArticles.get(i).getFacture().getRef(),ftext));
					        	   else
					        		   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(factureArticles.get(i).getFacture()!=null)
						        	   c1 = new PdfPCell(new Phrase(ft.format(factureArticles.get(i).getFacture().getDatefacture()),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(factureArticles.get(i).getFacture()!=null){
						        	   if(factureArticles.get(i).getFacture().getClient()!=null)
						        		   c1 = new PdfPCell(new Phrase(factureArticles.get(i).getFacture().getClient().getSociete(),ftext));
						        	   else
						        		   c1 = new PdfPCell(new Phrase(" ",ftext));
						           }
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(factureArticles.get(i).getFacture()!=null)
						        	   c1 = new PdfPCell(new Phrase(factureArticles.get(i).getFacture().getTotalht()+"",ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+factureArticles.get(i).getMensualite(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+factureArticles.get(i).getQte(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+factureArticles.get(i).getPrixmarge(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						          
					           }
				           }

	          
	           
	           document.add(table);
	           
	           System.out.println("Imprimer***************FIN SUCCES");
	           document.addAuthor("IMS Technology");
	           document.addTitle("Facture");
	           document.addCreator("RABEH");
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	    	typeoffre="";
	       } catch (DocumentException | FileNotFoundException | NullPointerException de) {
				FacesMessage msg = new FacesMessage("Probleme de generation de PDF","contacter l'administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
	           System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF "+de.getMessage());
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	       }
		
		
	}

	public void createPdfBCParArticle(String dest) {
   	 System.out.println("Imprimer*************** SUCCES");
		   SimpleDateFormat ft= new SimpleDateFormat("dd/MM/yy");
	   		Paragraph saute=new Paragraph("\n");
	       Document document = new Document(PageSize.A4);
	       document.setMargins(80, 20, 0, 0);
	       HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	       
	       try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
	    	   document.open();
	    	   response.setContentType("application/pdf");
	           response.setHeader("Content-Disposition", "inline; filename=IMSBCParArticle.pdf");

	           document.add(saute);
	           
	           Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
	           Font ftext=new Font(Font.FontFamily.HELVETICA,8,Font.NORMAL,new BaseColor(Color.black));
	           String str_Titre="Relevé BC Pour un Toutes les Articles";
	           if(articleID!=null){
	        	   Article art;
	        	   if(!articleID.equals("0")){
	        		   art=managerArticle.getObject(Integer.parseInt(articleID));
	        		   str_Titre="Relevé BC Pour un l'article de Reference "+art.getRef();
	        	   }	
	           }
	          
	             
	           
	           Paragraph titre=new Paragraph(str_Titre,ftextgra);
	           titre.setAlignment(Element.ALIGN_CENTER);
	           document.add(titre);
	           document.add(saute);
	            titre=new Paragraph("Date d'édition :"+ft.format(new Date()),ftextgra);
	            titre.setAlignment(Element.ALIGN_CENTER);
	            document.add(titre);
	           document.add(saute);
	           
	           System.out.println("ID Produit");
	           System.out.println(produitID);
	           
	            	titre=new Paragraph("Produit : "+managerProduit.getObject(produitID).getTypeproduit(),ftextgra);
	  	            document.add(titre);
	  	          document.add(saute);
	           PdfPTable table;
	        	   table = new PdfPTable(6);
		           table.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.setWidthPercentage(106);
		           table.setWidths(new float[]{50,50,50,50,50,50});
	           
	           
		       PdfPCell c1 = new PdfPCell(new Phrase("N°BC",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Date",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Founisseur",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("HT",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("PU F.",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Client",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           table.setHeaderRows(1);
	           
			        	   System.out.println("RECUP BC ...");
			        	   bcarticles=managerarticlebc.getByNames("stat",idannee,articleID);
			        	   System.out.println("RECUP BC ...SUCCESS");
			        	   System.out.println("SIZE==>"+bcarticles.size());
				           if(bcarticles!=null){  
					           for(int i=0;i<bcarticles.size();i++)
					           {
					        	   if(bcarticles.get(i).getCommande()!=null)
					        	   c1 = new PdfPCell(new Phrase(bcarticles.get(i).getCommande().getNumercommande(),ftext));
					        	   else
					        		   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(bcarticles.get(i).getCommande()!=null)
						        	   c1 = new PdfPCell(new Phrase(ft.format(bcarticles.get(i).getCommande().getDatecommande()),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(bcarticles.get(i).getCommande()!=null){
						        	   if(bcarticles.get(i).getCommande().getFournisseur()!=null)
						        		   c1 = new PdfPCell(new Phrase(bcarticles.get(i).getCommande().getFournisseur().getNomsociete(),ftext));
						        	   else
						        		   c1 = new PdfPCell(new Phrase(" ",ftext));
						           }
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(bcarticles.get(i).getCommande()!=null)
						        	   c1 = new PdfPCell(new Phrase(bcarticles.get(i).getCommande().getTotalht()+"",ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+bcarticles.get(i).getPu(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           if(bcarticles.get(i).getCommande()!=null){
						        	   if(bcarticles.get(i).getCommande().getClient()!=null)
						        		   c1 = new PdfPCell(new Phrase(bcarticles.get(i).getCommande().getClient().getSociete(),ftext));
						        	   else
						        		   c1 = new PdfPCell(new Phrase(" ",ftext));
						           }
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
					           }
				           }

	          
	           
	           document.add(table);
	           
	           System.out.println("Imprimer***************FIN SUCCES");
	           document.addAuthor("IMS Technology");
	           document.addTitle("Offre");
	           document.addCreator("RABEH");
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	    	typeoffre="";
	       } catch (DocumentException | FileNotFoundException | NullPointerException de) {
				FacesMessage msg = new FacesMessage("Probleme de generation de PDF","contacter l'administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
	           System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF "+de.getMessage());
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	       }
		
	}
    
	public void imprimeroffreclients(){
    	String dest="D:/IMS.pdf";
        try {
        File file = new File(dest);
        file.getParentFile().mkdirs();	
        createPdfoffreclient(dest);
				createPdfImage();
			} catch (DocumentException
					| IOException | NullPointerException e) {
				e.printStackTrace();
			}
    }
	
    public void createPdfoffreParArticle(String dest) {


    	 System.out.println("Imprimer*************** SUCCES");
		   SimpleDateFormat ft= new SimpleDateFormat("dd/MM/yy");
	   		Paragraph saute=new Paragraph("\n");
	       Document document = new Document(PageSize.A4);
	       document.setMargins(80, 20, 0, 0);
	       HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	       
	       try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
	    	   document.open();
	    	   
	    	   response.setContentType("application/pdf");
	           response.setHeader("Content-Disposition", "inline; filename=IMSOffreParArticle.pdf");

	           document.add(saute);
	           
	           Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
	           Font ftext=new Font(Font.FontFamily.HELVETICA,8,Font.NORMAL,new BaseColor(Color.black));
	           String str_Titre="Relevé Devis Pour un Toutes les Articles";
	           if(articleID!=null){
	        	   Article art;
	        	   if(!articleID.equals("0")){
	        		   art=managerArticle.getObject(Integer.parseInt(articleID));
	        		   str_Titre="Relevé Devis Pour un l'article de Reference "+art.getRef();
	        	   }	
	           }
	           Paragraph titre=new Paragraph(str_Titre,ftextgra);
	           titre.setAlignment(Element.ALIGN_CENTER);
	           document.add(titre);
	           document.add(saute);
	            titre=new Paragraph("Date d'édition :"+ft.format(new Date()),ftextgra);
	            titre.setAlignment(Element.ALIGN_CENTER);
	            document.add(titre);
	           System.out.println("ID Produit");
	           System.out.println(produitID);
	           
	            	titre=new Paragraph("Produit : "+managerProduit.getObject(produitID).getTypeproduit(),ftextgra);
	  	            document.add(titre);
	           document.add(saute);
	           
	           PdfPTable table;
	        	   table = new PdfPTable(8);
		           table.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.setWidthPercentage(106);
		           table.setWidths(new float[]{50,50,50,50,50,50,50,50});
	           
	           
		       PdfPCell c1 = new PdfPCell(new Phrase("N° Devis",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Date",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Societe",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("HT",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("PU F.",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Marge",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);

	           c1 = new PdfPCell(new Phrase("Prix Marge",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("PU Client",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           
	           
	           table.setHeaderRows(1);
	           
			        	   System.out.println("RECUP Offre ...");
			        	   offresArticle=managerOffrearticle.getByNames("stat",idannee,articleID);
			        	   System.out.println("RECUP Offres ...SUCCESS");
			        	   System.out.println("SIZE==>"+offresArticle.size());
				           if(offresArticle!=null){  
					           for(int i=0;i<offresArticle.size();i++)
					           {
					        	   if(offresArticle.get(i).getOffre()!=null)
					        	   c1 = new PdfPCell(new Phrase(offresArticle.get(i).getOffre().getRef(),ftext));
					        	   else
					        		   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(offresArticle.get(i).getOffre()!=null)
						        	   c1 = new PdfPCell(new Phrase(ft.format(offresArticle.get(i).getOffre().getDateoffre()),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(offresArticle.get(i).getOffre()!=null){
						        	   if(offresArticle.get(i).getOffre().getClient()!=null)
						        		   c1 = new PdfPCell(new Phrase(offresArticle.get(i).getOffre().getClient().getSociete(),ftext));
						        	   else
						        		   c1 = new PdfPCell(new Phrase(" ",ftext));
						           }
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(offresArticle.get(i).getOffre()!=null)
						        	   c1 = new PdfPCell(new Phrase(offresArticle.get(i).getOffre().getTotalHT()+"",ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+offresArticle.get(i).getPu(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+offresArticle.get(i).getMarge(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+offresArticle.get(i).getPrixmarge(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+offresArticle.get(i).getPu_calculer(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
					           }
				           }

	          
	           
	           document.add(table);
	           
	           System.out.println("Imprimer***************FIN SUCCES");
	           document.addAuthor("IMS Technology");
	           document.addTitle("Offre");
	           document.addCreator("RABEH");
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	    	typeoffre="";
	       } catch (DocumentException | FileNotFoundException | NullPointerException de) {
				FacesMessage msg = new FacesMessage("Probleme de generation de PDF","contacter l'administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
	           System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF "+de.getMessage());
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	       }
		
	}
    
    public void createPdfoffreclient(String dest){
		   System.out.println("Imprimer*************** SUCCES");
		   SimpleDateFormat ft= new SimpleDateFormat("dd/MM/yy");
	   		Paragraph saute=new Paragraph("\n");
	       Document document = new Document(PageSize.A4);
	       document.setMargins(80, 20, 0, 0);
	       HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	       
	       try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
	    	   document.open();
	    	   response.setContentType("application/pdf");
	           response.setHeader("Content-Disposition", "inline; filename=IMSBCFournisseur.pdf");

	           document.add(saute);
	           
	           Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
	           Font ftext=new Font(Font.FontFamily.HELVETICA,8,Font.NORMAL,new BaseColor(Color.black));
	           Paragraph titre=new Paragraph("Relevé Offre Client",ftextgra);
	           titre.setAlignment(Element.ALIGN_CENTER);
	           document.add(titre);
	           document.add(saute);
	            titre=new Paragraph("Date d'édition :"+ft.format(new Date()),ftextgra);
	            titre.setAlignment(Element.ALIGN_CENTER);
	            document.add(titre);
	           document.add(saute);
	           PdfPTable table;
	        	   table = new PdfPTable(7);
		           table.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.setWidthPercentage(106);
		           table.setWidths(new float[]{70,60,65,50,50,50,50});
	           
	           
		       PdfPCell c1 = new PdfPCell(new Phrase("Réfrence",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Client",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Date",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Type",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);

	           c1 = new PdfPCell(new Phrase("Total HT",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Total TTC",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Etat",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           table.setHeaderRows(1);
	           
			        	   System.out.println("RECUP Offre ...");
			        	   offreclients=manageroffre.getByNames("stat",idannee,clientID,etat,typeoffre);
			        	   System.out.println("RECUP Offre ...SUCCESS");
			        	   System.out.println("SIZE==>"+offreclients.size());
				           if(offreclients!=null){  
					           for(int i=0;i<offreclients.size();i++)
					           {
					        	   	
					        	   c1 = new PdfPCell(new Phrase(offreclients.get(i).getRef(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(offreclients.get(i).getClient()!=null)
						        	   c1 = new PdfPCell(new Phrase(offreclients.get(i).getClient().getSociete(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(offreclients.get(i).getDateoffre()!=null)
						        	   c1 = new PdfPCell(new Phrase(ft.format(offreclients.get(i).getDateoffre()),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(offreclients.get(i).getTypeoffre()!=null)
						        	   c1 = new PdfPCell(new Phrase(offreclients.get(i).getTypeoffre().getType(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+offreclients.get(i).getTotalHT(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+offreclients.get(i).getTotalTTC(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+offreclients.get(i).getEtatoffre(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
					           }
				           }

	          
	           
	           document.add(table);
	           
	           System.out.println("Imprimer***************FIN SUCCES");
	           document.addAuthor("IMS Technology");
	           document.addTitle("Offre");
	           document.addCreator("RABEH");
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	    	typeoffre="";
	       } catch (DocumentException | FileNotFoundException | NullPointerException de) {
				FacesMessage msg = new FacesMessage("Probleme de generation de PDF","contacter l'administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
	           System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF "+de.getMessage());
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	       }
	   }
    
    public void imprimerbcfournisseur() throws FileNotFoundException,ImsErpException{
    	String dest="D:/IMS.pdf";
        try {
        File file = new File(dest);
        file.getParentFile().mkdirs();	
        createPdfbcfournisseur(dest);
				createPdfImage();
			} catch (DocumentException
					| IOException | NullPointerException e) {
				e.printStackTrace();
			}
        }
    
    public void createPdfbcfournisseur(String dest){
		   System.out.println("Imprimer*************** SUCCES");
		   SimpleDateFormat ft= new SimpleDateFormat("dd/MM/yy");
		 
	   		Paragraph saute=new Paragraph("\n");
	       Document document = new Document(PageSize.A4);
	       document.setMargins(80, 20, 0, 0);
	       HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	       
	       try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
	    	   document.open();
	    	   response.setContentType("application/pdf");
	           response.setHeader("Content-Disposition", "inline; filename=IMSBCFournisseur.pdf");
	           System.out.println("setHeader Success");
	          
	           System.out.println("OPEN SUCESS");
	           document.add(saute);
	           
	           Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
	           Font ftext=new Font(Font.FontFamily.HELVETICA,8,Font.NORMAL,new BaseColor(Color.black));
	           Paragraph titre=new Paragraph("Relevé Bon Commande Fournisseur",ftextgra);
	           titre.setAlignment(Element.ALIGN_CENTER);
	           document.add(titre);
	           document.add(saute);
	            titre=new Paragraph("Date d'édition :"+ft.format(new Date()),ftextgra);
	            titre.setAlignment(Element.ALIGN_CENTER);
	            document.add(titre);
	           document.add(saute);
	           PdfPTable table;
	        	   table = new PdfPTable(10);
		           table.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.setWidthPercentage(106);
		           table.setWidths(new float[]{50,50,65,50,50,50,50,50,50,50});
	           
	           
		       PdfPCell c1 = new PdfPCell(new Phrase("N°BC",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Date",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Fournisseur",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Client final",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);

	           c1 = new PdfPCell(new Phrase("NET",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Echéance",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Date Echéance",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Etat",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Rég.",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           
	           c1 = new PdfPCell(new Phrase("Devise",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           table.setHeaderRows(1);
	           
			        	   System.out.println("RECUP BonCommande ...");
			        	   boncommandesfournisseur=managercommande.getByNames("stat",idannee,fournisseurID,etat,constat);
			        	   System.out.println("RECUP BonCommande ...SUCCESS");
			        	   System.out.println("SIZE==>"+boncommandesfournisseur.size());
				           if(boncommandesfournisseur!=null){  
					           for(int i=0;i<boncommandesfournisseur.size();i++)
					           {
					        	   	
					        	   c1 = new PdfPCell(new Phrase(boncommandesfournisseur.get(i).getNumercommande(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(boncommandesfournisseur.get(i).getDatecommande()!=null)
						        	   c1 = new PdfPCell(new Phrase(ft.format(boncommandesfournisseur.get(i).getDatecommande()),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(boncommandesfournisseur.get(i).getFournisseur()!=null)
						        	   c1 = new PdfPCell(new Phrase(boncommandesfournisseur.get(i).getFournisseur().getNomsociete(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(boncommandesfournisseur.get(i).getClient()!=null)
						        	   c1 = new PdfPCell(new Phrase(boncommandesfournisseur.get(i).getClient().getSociete(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+boncommandesfournisseur.get(i).getTotalnet(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           if(boncommandesfournisseur.get(i).getEcheance()!=null)
						        	   c1 = new PdfPCell(new Phrase(boncommandesfournisseur.get(i).getEcheance(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(boncommandesfournisseur.get(i).getDateechance()!=null)
						            c1 = new PdfPCell(new Phrase(ft.format(boncommandesfournisseur.get(i).getDateechance()),ftext));
						           else
						            c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(boncommandesfournisseur.get(i).getEtat()!=null)
						        	   c1 = new PdfPCell(new Phrase(""+boncommandesfournisseur.get(i).getEtat(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           if(boncommandesfournisseur.get(i).getReglement()!=null)
						        	   c1 = new PdfPCell(new Phrase(""+boncommandesfournisseur.get(i).getReglement(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           
						           if(boncommandesfournisseur.get(i).getTypepaiement()!=null)
						        	   c1 = new PdfPCell(new Phrase(""+boncommandesfournisseur.get(i).getTypepaiement().getTypepaiement(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
					           }
				           }

	          
	           
	           document.add(table);
	           
	           System.out.println("Imprimer***************FIN SUCCES");
	           document.addAuthor("IMS Technology");
	           document.addTitle("Jusitificatif");
	           document.addCreator("RABEH");
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	       } catch (DocumentException | FileNotFoundException | NullPointerException de) {
				FacesMessage msg = new FacesMessage("Probleme de generation de PDF","contacter l'administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
	           System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF "+de.getMessage());
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	       }
	   }
      
    public void imprimerfactfournisseur() throws FileNotFoundException,ImsErpException{
    	String dest="D:/IMS.pdf";
        try {
        File file = new File(dest);
        file.getParentFile().mkdirs();	
        createPdffactfournisseur(dest);
				createPdfImage();
			} catch (DocumentException
					| IOException | NullPointerException e) {
				e.printStackTrace();
			}
        }
    
 	public void createPdffactfournisseur(String dest){
		   
 		
 			System.out.println("Imprimer*************** SUCCES");
 			SimpleDateFormat ft= new SimpleDateFormat("dd/MM/yy");
	   		Paragraph saute=new Paragraph("\n");
	   		Document document = new Document(PageSize.A4);
	   	    document.setMargins(80, 20, 0, 0);
	   		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	       
	       try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
	    	   document.open();
	    	   response.setContentType("application/pdf");
	           response.setHeader("Content-Disposition", "inline; filename=IMSFactureFournisseur.pdf");
	           System.out.println("setHeader Success");
	          
	           System.out.println("OPEN SUCESS");
	           document.add(saute);
	           
	           Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
	           Font ftext=new Font(Font.FontFamily.HELVETICA,8,Font.NORMAL,new BaseColor(Color.black));
	           Paragraph titre=new Paragraph("Relevé Facture Fournisseur",ftextgra);
	           titre.setAlignment(Element.ALIGN_CENTER);
	           document.add(titre);
	           document.add(saute);
	            titre=new Paragraph("Date d'édition :"+ft.format(new Date()),ftextgra);
	            titre.setAlignment(Element.ALIGN_CENTER);
	            document.add(titre);
	           document.add(saute);
	           PdfPTable table;
	        	   table = new PdfPTable(12);
		           table.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.setWidthPercentage(106);
		           
		           table.setWidths(new float[]{60,50,45,65,50,50,45,60,50,50,50,50});
		           System.out.println("------------------------LEFT MARGIN----------------------------");
		          
		           System.out.println( document.leftMargin());
		           System.out.println("----------------------------------------------------");
	          
		       PdfPCell c1 = new PdfPCell(new Phrase("N°facture",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("BC.F",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Date",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Societe",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Client Final",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);

	           c1 = new PdfPCell(new Phrase("NET",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	     
	           c1 = new PdfPCell(new Phrase("Date Echéance",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Etat",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Rég",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Avance",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Reste",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Devise",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           table.setHeaderRows(1);
	           
			        	   System.out.println("RECUP FACTURE ...");
			        	   facturesfournisseur=managerFactFournisseur.getByNames("stat",idannee,fournisseurID,etat,constat);
			        	   System.out.println("RECUP FACTURE ...SUCCESS");
			        	   System.out.println("SIZE==>"+facturesfournisseur.size());
			        	   
			        	   totalnet=0;
			        	   totalavance=0;
			        	   totalreste=0;
			        	   totalnetMAD=0;
				           
			        	   if(facturesfournisseur!=null){  
					           for(int i=0;i<facturesfournisseur.size();i++)
					           {
					        	   	
					        	   c1 = new PdfPCell(new Phrase(facturesfournisseur.get(i).getNum_facture(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(facturesfournisseur.get(i).getCommandefournissuer()!=null)
						        	   c1 = new PdfPCell(new Phrase(facturesfournisseur.get(i).getCommandefournissuer().getNumercommande(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(facturesfournisseur.get(i).getDatefacture()!=null)
						        	   c1 = new PdfPCell(new Phrase(ft.format(facturesfournisseur.get(i).getDatefacture()),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(facturesfournisseur.get(i).getCommandefournissuer()!=null)
						        	   c1 = new PdfPCell(new Phrase(facturesfournisseur.get(i).getCommandefournissuer().getFournisseur().getNomsociete(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(facturesfournisseur.get(i).getCommandefournissuer()!=null){
						        	   if(facturesfournisseur.get(i).getCommandefournissuer().getClient()!=null)
						        	   c1 = new PdfPCell(new Phrase(facturesfournisseur.get(i).getCommandefournissuer().getClient().getSociete(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
					                }
					           			else
					           				c1 = new PdfPCell(new Phrase(" ",ftext));
						           
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+facturesfournisseur.get(i).getTotalNet(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           if(facturesfournisseur.get(i).getDateechance()!=null)
						        	   c1 = new PdfPCell(new Phrase(ft.format(facturesfournisseur.get(i).getDateechance()),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(facturesfournisseur.get(i).getEtat()!=null)
						        	   c1 = new PdfPCell(new Phrase(""+facturesfournisseur.get(i).getEtat(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           if(facturesfournisseur.get(i).getReglement()!=null)
						        	   c1 = new PdfPCell(new Phrase(""+facturesfournisseur.get(i).getReglement(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+facturesfournisseur.get(i).getMontantregler(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+facturesfournisseur.get(i).getMontantrester(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           if(facturesfournisseur.get(i).getTypepaiement()!=null)
						        	   c1 = new PdfPCell(new Phrase(""+facturesfournisseur.get(i).getTypepaiement().getTypepaiement(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           totalnet+=facturesfournisseur.get(i).getTotalNet();
						           totalavance+=facturesfournisseur.get(i).getMontantregler();
						           totalreste+=facturesfournisseur.get(i).getMontantrester();
					           }
					           
					           c1 = new PdfPCell(new Phrase("Total :",ftext));
					           c1.setColspan(5);
					           table.addCell(c1);
					           
					           c1 = new PdfPCell(new Phrase(""+totalnet,ftext));
					           //c1.setColspan();
					           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(c1);
					           
					           c1 = new PdfPCell(new Phrase("",ftext));
					           c1.setColspan(3);
					           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(c1);
					           
					           c1 = new PdfPCell(new Phrase(""+totalavance,ftext));
					           //c1.setColspan(4);
					           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(c1);
					           
					           c1 = new PdfPCell(new Phrase(""+totalreste,ftext));
					           //c1.setColspan(2);
					           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(c1);
					           
					           c1 = new PdfPCell(new Phrase("",ftext));
					           //c1.setColspan(2);
					           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(c1);
					           
				           }

	          
	           
	           document.add(table);
	           
	           System.out.println("Imprimer***************FIN SUCCES");
	           document.addAuthor("IMS Technology");
	           document.addTitle("Jusitificatif");
	           document.addCreator("RABEH");
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	       } catch (DocumentException | FileNotFoundException | NullPointerException de) {
				FacesMessage msg = new FacesMessage("Probleme de generation de PDF","contacter l'administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
	           System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF "+de.getMessage());
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	       }
	   }
       
    public void imprimerfactclients() throws FileNotFoundException,ImsErpException{
    	String dest="D:/IMS.pdf";
        try {
        File file = new File(dest);
        file.getParentFile().mkdirs();	
        createPdffactclient(dest);
				createPdfImage();
			} catch (DocumentException
					| IOException | NullPointerException e) {
				e.printStackTrace();
			}
        }
  	
    public void createPdffactclient(String dest){
		   SimpleDateFormat ft= new SimpleDateFormat("dd/MM/yy");
	   		Paragraph saute=new Paragraph("\n");
	       Document document = new Document(PageSize.A4);
	       document.setMargins(80, 20, 0, 0);
	       HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	       
	       try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
	    	   document.open();
	    	   response.setContentType("application/pdf");
	           response.setHeader("Content-Disposition", "inline; filename=IMSFacturesClient.pdf");
	           document.add(saute);
	           
	           Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
	           Font ftext=new Font(Font.FontFamily.HELVETICA,8,Font.NORMAL,new BaseColor(Color.black));
	           Paragraph titre=new Paragraph("Relevé Facture Client",ftextgra);
	           titre.setAlignment(Element.ALIGN_CENTER);
	           document.add(titre);
	           document.add(saute);
	            titre=new Paragraph("Date d'édition :"+ft.format(new Date()),ftextgra);
	            titre.setAlignment(Element.ALIGN_CENTER);
	            document.add(titre);
	           document.add(saute);
	          
	           PdfPTable table;
	        	   table = new PdfPTable(11);
		           table.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.setWidthPercentage(106);
		           table.setWidths(new float[]{50,60,60,50,50,70,50,60,50,50,50});
	           
	           
		       PdfPCell c1 = new PdfPCell(new Phrase("N°facture",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Date",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Societe",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);

	           c1 = new PdfPCell(new Phrase("NET",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	     
	           c1 = new PdfPCell(new Phrase("Echéance",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Date Echéance",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Etat",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Rég",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Avance",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Reste",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Devise",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           table.setHeaderRows(1);
	           
			        	   System.out.println("RECUP FACTURE ...");
			        	   facturesclient=manager.getByNames("stat",idannee,clientID,etat,constat,groupclientID);
			        	   System.out.println("RECUP FACTURE ...SUCCESS");
			        	   System.out.println("SIZE==>"+facturesclient.size());
			        	   
			        	   totalnet=0;
			        	   totalavance=0;
			        	   totalreste=0;
			        	   totalnetMAD=0;
			        	   
				           if(facturesclient!=null){  
					           for(int i=0;i<facturesclient.size();i++)
					           {
					        	   	
					        	   c1 = new PdfPCell(new Phrase(facturesclient.get(i).getRef(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(facturesclient.get(i).getDatefacture()!=null)
						        	   c1 = new PdfPCell(new Phrase(ft.format(facturesclient.get(i).getDatefacture()),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(facturesclient.get(i).getClient()!=null)
						        	   c1 = new PdfPCell(new Phrase(facturesclient.get(i).getClient().getSociete(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           
						           c1 = new PdfPCell(new Phrase(""+facturesclient.get(i).getTotalttcpaye(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
		
						           if(facturesclient.get(i).getConditiondepayment()!=null)
						        	   c1 = new PdfPCell(new Phrase(facturesclient.get(i).getConditiondepayment(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           if(facturesclient.get(i).getDateechance()!=null)
						        	   c1 = new PdfPCell(new Phrase(ft.format(facturesclient.get(i).getDateechance()),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						     
						           if(facturesclient.get(i).getEtat()!=null)
						        	   c1 = new PdfPCell(new Phrase(""+facturesclient.get(i).getEtat(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           if(facturesclient.get(i).getReglement()!=null)
						        	   c1 = new PdfPCell(new Phrase(""+facturesclient.get(i).getReglement(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           
						           c1 = new PdfPCell(new Phrase(""+facturesclient.get(i).getMontantregler(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           facturesclient.get(i).setMontantrester((double) Math.round(facturesclient.get(i).getMontantrester() * 100) / 100);
						           c1 = new PdfPCell(new Phrase(""+facturesclient.get(i).getMontantrester(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           if(facturesclient.get(i).getTypepaiement()!=null)
						        	   c1 = new PdfPCell(new Phrase(""+facturesclient.get(i).getTypepaiement().getTypepaiement(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           totalnet+=facturesclient.get(i).getTotalttcpaye();
						           totalavance+=facturesclient.get(i).getMontantregler();
						           totalreste+=facturesclient.get(i).getMontantrester();
					           }
					           
					           c1 = new PdfPCell(new Phrase("Total :",ftext));
					           c1.setColspan(3);
					           table.addCell(c1);
					           
					           c1 = new PdfPCell(new Phrase(""+totalnet,ftext));
					           //c1.setColspan();
					           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(c1);
					           
					           c1 = new PdfPCell(new Phrase("",ftext));
					           c1.setColspan(4);
					           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(c1);
					           
					           c1 = new PdfPCell(new Phrase(""+totalavance,ftext));
					           //c1.setColspan(4);
					           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(c1);
					           
					           c1 = new PdfPCell(new Phrase(""+totalreste,ftext));
					           //c1.setColspan(2);
					           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(c1);
					           
					           c1 = new PdfPCell(new Phrase("",ftext));
					           //c1.setColspan(2);
					           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(c1);
					           
				           }

	          
	           
	           document.add(table);
	           
	           System.out.println("Imprimer***************FIN SUCCES");
	           document.addAuthor("IMS Technology");
	           document.addTitle("Jusitificatif");
	           document.addCreator("RABEH");
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	       } catch (DocumentException | FileNotFoundException | NullPointerException de) {
				FacesMessage msg = new FacesMessage("Probleme de generation de PDF","contacter l'administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
	           System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF "+de.getMessage());
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       	idannee="";
	    	clientID="";
	    	fournisseurID="";
	    	etat="";
	    	constat="";
	       }
	   }
    
    public void createPdfImage() throws FileNotFoundException, DocumentException, IOException ,NullPointerException{

        PdfReader reader;			
        reader = new PdfReader("D:/IMS.pdf");

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
    
	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public ModelService<years> getManageryears() {
		return manageryears;
	}

	public void setManageryears(ModelService<years> manageryears) {
		this.manageryears = manageryears;
	}

	public ModelService<Facture> getManager() {
		return manager;
	}

	public void setManager(ModelService<Facture> manager) {
		this.manager = manager;
	}

	public ModelService<FactureFournisseur> getManagerFactFournisseur() {
		return managerFactFournisseur;
	}

	public void setManagerFactFournisseur(
			ModelService<FactureFournisseur> managerFactFournisseur) {
		this.managerFactFournisseur = managerFactFournisseur;
	}

	public String getIdannee() {
		return idannee;
	}

	public void setIdannee(String idannee) {
		this.idannee = idannee;
	}

	public List<years> getListeyears() {
		if(listeyears==null)
			listeyears=manageryears.getObject();
		return listeyears;
	}

	public void setListeyears(List<years> listeyears) {
		this.listeyears = listeyears;
	}

	public ModelService<Client> getManagerclient() {
		return managerclient;
	}

	public void setManagerclient(ModelService<Client> managerclient) {
		this.managerclient = managerclient;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getConstat() {
		return constat;
	}

	public void setConstat(String constat) {
		this.constat = constat;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Facture> getFacturesclient() {
		return facturesclient;
	}

	public void setFacturesclient(List<Facture> facturesclient) {
		this.facturesclient = facturesclient;
	}

	public List<FactureFournisseur> getFacturesfournisseur() {
		return facturesfournisseur;
	}

	public void setFacturesfournisseur(List<FactureFournisseur> facturesfournisseur) {
		this.facturesfournisseur = facturesfournisseur;
	}

	public List<BonCommande> getBoncommandesfournisseur() {
		return boncommandesfournisseur;
	}

	public void setBoncommandesfournisseur(List<BonCommande> boncommandesfournisseur) {
		this.boncommandesfournisseur = boncommandesfournisseur;
	}

	public String getFournisseurID() {
		return fournisseurID;
	}

	public void setFournisseurID(String fournisseurID) {
		this.fournisseurID = fournisseurID;
	}

	public ModelService<BonCommande> getManagercommande() {
		return managercommande;
	}

	public void setManagercommande(ModelService<BonCommande> managercommande) {
		this.managercommande = managercommande;
	}

	public ModelService<Offre> getManageroffre() {
		return manageroffre;
	}

	public void setManageroffre(ModelService<Offre> manageroffre) {
		this.manageroffre = manageroffre;
	}

	public String getTypeoffre() {
		return typeoffre;
	}

	public void setTypeoffre(String typeoffre) {
		this.typeoffre = typeoffre;
	}

	public List<Offre> getOffreclients() {
		return offreclients;
	}

	public void setOffreclients(List<Offre> offreclients) {
		this.offreclients = offreclients;
	}

	public ModelService<Offre_Article> getManagerOffrearticle() {
		return managerOffrearticle;
	}

	public void setManagerOffrearticle(
			ModelService<Offre_Article> managerOffrearticle) {
		this.managerOffrearticle = managerOffrearticle;
	}

	public ModelService<Article> getManagerArticle() {
		return managerArticle;
	}

	public void setManagerArticle(ModelService<Article> managerArticle) {
		this.managerArticle = managerArticle;
	}

	public List<Offre_Article> getOffresArticle() {
		return offresArticle;
	}

	public void setOffresArticle(List<Offre_Article> offresArticle) {
		this.offresArticle = offresArticle;
	}

	public String getArticleID() {
		return articleID;
	}

	public void setArticleID(String articleID) {
		this.articleID = articleID;
	}
	
	public double getTotalnetMAD() {
		return totalnetMAD;
	}

	public void setTotalnetMAD(double totalnetMAD) {
		this.totalnetMAD = totalnetMAD;
	}

	public double getTotalnet() {
		return totalnet;
	}

	public void setTotalnet(double totalnet) {
		this.totalnet = totalnet;
	}

	public double getTotalreste() {
		return totalreste;
	}

	public void setTotalreste(double totalreste) {
		this.totalreste = totalreste;
	}

	public double getTotalavance() {
		return totalavance;
	}

	public void setTotalavance(double totalavance) {
		this.totalavance = totalavance;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public List<Boncommande_Article> getBcarticles() {
		return bcarticles;
	}

	public void setBcarticles(List<Boncommande_Article> bcarticles) {
		this.bcarticles = bcarticles;
	}

	public List<Facture_Article> getFactureArticles() {
		return factureArticles;
	}

	public void setFactureArticles(List<Facture_Article> factureArticles) {
		this.factureArticles = factureArticles;
	}

	public List<FactureFournisseur_articles> getFactfournisseurArticles() {
		return factfournisseurArticles;
	}

	public void setFactfournisseurArticles(
			List<FactureFournisseur_articles> factfournisseurArticles) {
		this.factfournisseurArticles = factfournisseurArticles;
	}

	public ModelService<Boncommande_Article> getManagerarticlebc() {
		return managerarticlebc;
	}

	public void setManagerarticlebc(
			ModelService<Boncommande_Article> managerarticlebc) {
		this.managerarticlebc = managerarticlebc;
	}

	public ModelService<Facture_Article> getManagerFactArticle() {
		return managerFactArticle;
	}

	public void setManagerFactArticle(
			ModelService<Facture_Article> managerFactArticle) {
		this.managerFactArticle = managerFactArticle;
	}

	public ModelService<FactureFournisseur_articles> getManagerFactfournisseurArticle() {
		return managerFactfournisseurArticle;
	}

	public void setManagerFactfournisseurArticle(
			ModelService<FactureFournisseur_articles> managerFactfournisseurArticle) {
		this.managerFactfournisseurArticle = managerFactfournisseurArticle;
	}

	public ModelService<Stock_Livraison> getManagerstocklivraison() {
		return managerstocklivraison;
	}

	public void setManagerstocklivraison(
			ModelService<Stock_Livraison> managerstocklivraison) {
		this.managerstocklivraison = managerstocklivraison;
	}

	public int getProduitID() {
		return produitID;
	}

	public void setProduitID(int produitID) {
		this.produitID = produitID;
	}

	public List<Stock_Livraison> getStocksList() {
		return stocksList;
	}

	public void setStocksList(List<Stock_Livraison> stocksList) {
		this.stocksList = stocksList;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public ModelService<Produit> getManagerProduit() {
		return managerProduit;
	}

	public void setManagerProduit(ModelService<Produit> managerProduit) {
		this.managerProduit = managerProduit;
	}


	public String getGroupclientID() {
		return groupclientID;
	}

	public void setGroupclientID(String groupclientID) {
		this.groupclientID = groupclientID;
	}

	public List<Client> getClients() {
		if(clients==null)
			clients=managerclient.getObject();
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public ManagerDataBase getManagerApplication() {
		return managerApplication;
	}

	public void setManagerApplication(ManagerDataBase managerApplication) {
		this.managerApplication = managerApplication;
	}

	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}
	
	

}
