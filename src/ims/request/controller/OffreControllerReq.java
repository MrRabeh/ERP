package ims.request.controller;


import ims.model.dao.SessionIMS;
import ims.model.entities.Article;
import ims.model.entities.Client;
import ims.model.entities.Offre;
import ims.model.entities.Offre_Article;
import ims.model.entities.TypeOffre;
import ims.model.entities.years;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.RowEditEvent;

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

/**
 *
 * @author rabeh
 */
@ManagedBean
@RequestScoped
public class OffreControllerReq implements Serializable{

    
   private static final long serialVersionUID = 1L;
        
    /**
     * Creates a new instance of OffreController
     */
    @ManagedProperty(value="#{OffreManager}")
    private ModelService<Offre> manager;
    
    @ManagedProperty(value="#{TypeOffreManager}")
    private ModelService<TypeOffre> managertype;
     
    @ManagedProperty(value="#{OffreArticleManager}")
    private ModelService<Offre_Article> managerarticle;
     
    @ManagedProperty(value="#{ArticleManager}")
    private ModelService<Article> managerObjetarticle;
    
    @ManagedProperty(value="#{ClientManager}")
    private ModelService<Client> managerclient;
    
    @ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
    
    private Offre calcule=new Offre();
    private List<Offre> calcules;
    private List<Offre> filtredcalcules;
    private int idtypeoffre;
    private List<TypeOffre> typeoffres;
    private Offre_Article ligne=new Offre_Article();
    private List<Offre_Article> lignes=new ArrayList<Offre_Article>();
    private List<Offre_Article> lignesView;
    private int idarticle;
    private int idclient;
    private boolean checkforfait;
    ///////////////////////////////////////////
    private double marget;
    private Article article=new Article();
    private double qte=1;
    private int nbrper=1;
    private double pu;
    private int marge=25;
    private double TVA=0;
    private double totalHT;
    private double totalTTC;
    /////////////////////////////////////////////////
    
    /////////////////////////////////////////////////
    private String ref;
    private String description;
    private String designation;
    private String diponibilite;
    private String validiteoffre;
    private String modalitepaiment="30 jours à réception de la facture";
    private Date dateoffre;
    private Client client=new Client();
    /////////////////////////////////////////////////
    
    public OffreControllerReq() {
    	modalitepaiment="30 jours à réception de la facture";
    }
    
    @PostConstruct
    public void init() {
    	calcules=null;
        calcules=manager.getObject();
        lignes=new ArrayList<Offre_Article>();
        modalitepaiment="30 jours à réception de la facture";
    }
  
    public void menu(){
    	calcules=null;
        calcules=manager.getObject();
        
    }
    
    public void activer(){
    	FacesMessage msg;
    	System.out.println("ID => "+calcule.getId());
    	managerjdbc.desactiverOffre(calcule.getId());
        msg = new FacesMessage("Offre est desactiver");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void annuler(){
    	FacesMessage msg;
    	System.out.println("ID => "+calcule.getId());
    	managerjdbc.AnnulerOffre(calcule.getId(),1);
        msg = new FacesMessage("Offre est desactiver");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void ajouterligne(){
    	try {
    		FacesMessage msg;
        	System.out.println("ADD LIGNE");
        	ligne=new Offre_Article();
        	
        	ligne.setPu(pu);
        	ligne.setQtef(qte);
        	double m=marge;
        	ligne.setMarge(m);
        	Article article=managerObjetarticle.getObject(idarticle);
        	ligne.setArticle(article);
        	if(checkforfait==true){
        		ligne.setLbqte("F");
        		ligne.setQtef(1);
        		nbrper=0;
        	}
        	else{
        	if(ligne.getArticle().getTypearticle().getType().toLowerCase().equals("prestation"))
        		{
        		ligne.setLbqte(qte+" J");
        		nbrper=0;
        		}
        	else if(ligne.getArticle().getTypearticle().getType().toLowerCase().equals("formation"))
        		ligne.setLbqte(qte+" P");
        	else if(ligne.getArticle().getTypearticle().getType().toLowerCase().equals("contrat"))
        		{
        		ligne.setLbqte("contrat");
        		ligne.setQtef(1);
        		nbrper=0;
        		}
        	else
        		ligne.setLbqte(qte+"");
        	}
        	ligne.setNombrepersonne(nbrper);
        	ligne.setPt(ligne.getPu()*ligne.getQtef());
            marget=((ligne.getPu()*ligne.getMarge())/100);
            ligne.setPu_calculer(ligne.getPu()+marget);
            System.out.println("Qte="+ligne.getQtef());
            System.out.println("ligne.getPu_calculer="+ligne.getPu_calculer());
            System.out.println("ligne.getPt_calculer="+ligne.getPt_calculer());
            ligne.setPt_calculer(ligne.getPu_calculer()*ligne.getQtef());
            System.out.println("ligne.getPt_calculer="+ligne.getPt_calculer());
            ligne.setPrixmarge(ligne.getPt_calculer()-ligne.getPt());
            if(designation.equals(""))
            	ligne.setDesignation(article.getDesignation());
            else
            	ligne.setDesignation(designation);
            
            lignes.add(ligne);
            totalHT=totalHT+ligne.getPt_calculer();
            
            TVA=(totalHT*0.2f);

            totalTTC=totalHT+TVA;

            pu=0;
            qte=1;
            designation="";
            msg = new FacesMessage("Article ajouter avec success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
    	} catch (Exception e) {
    		 System.out.println("ERROOOOORRRRRRRRRR");
		}
    }
    
    public void DeleteArticle(){
        FacesMessage msg;
        try {
        	
        	System.out.println("designation Objet"+ligne.getDesignation());
        		marget=((ligne.getPu()*ligne.getMarge())/100);
        		totalHT=totalHT-ligne.getPt_calculer()-marget;
        		TVA=(totalHT*0.2f);
        		totalTTC=totalHT+TVA;
        		lignes.remove(ligne);  
        		if(totalTTC<0)
        		{
        			totalTTC=0;
        			TVA=0;
        			totalHT=0;	
        		}
        		msg = new FacesMessage("Article est supprimer de l'offre");
              FacesContext.getCurrentInstance().addMessage(null, msg);

            } catch (Exception e) {
                msg = new FacesMessage("ERROR DATA");
           FacesContext.getCurrentInstance().addMessage(null, msg);
            }   
    }
    
    public void ajouterligne_modif(){
    	try {
    		FacesMessage msg;
        	System.out.println("ADD LIGNE");
        	ligne=new Offre_Article();
        	article=managerObjetarticle.getObject(idarticle);
        	ligne.setArticle(article);
        	ligne.setPu(pu);
        	ligne.setQtef(qte);
            
        	if(checkforfait==true){
        		ligne.setLbqte("F");
        		ligne.setQtef(1);
        		nbrper=0;
        	}
        	else{
        	if(ligne.getArticle().getTypearticle().getType().toLowerCase().equals("prestation"))
        		{
        		ligne.setLbqte(qte+" J");
        		nbrper=0;
        		}
        	else if(ligne.getArticle().getTypearticle().getType().toLowerCase().equals("formation"))
        		ligne.setLbqte(qte+" P");
        	else if(ligne.getArticle().getTypearticle().getType().toLowerCase().equals("contrat"))
        		{
        		ligne.setLbqte("contrat");
        		ligne.setQtef(1);
        		nbrper=0;
        		}
        	else
        		ligne.setLbqte(qte+"");
        	}
        	
        	ligne.setMarge(marge);
        	
        	ligne.setPt(ligne.getPu()*ligne.getQtef());
        	
        	
            marget=((ligne.getPu()*ligne.getMarge())/100);
            
            ligne.setPu_calculer(ligne.getPu()+marget);
            ligne.setPt_calculer(ligne.getPu_calculer()*ligne.getQtef());
        	System.out.println("ADD LIGNE SUCCESS YYYYYYYYYYYYYYYY");
        	ligne.setPrixmarge(ligne.getPt_calculer()-ligne.getPt());
            if(designation.equals(""))
            	{
            	System.out.println("---------------------------------------------------------");
            	System.out.println("KHHAWI");
            	ligne.setDesignation(article.getDesignation());
            	}
            else{
            	System.out.println("---------------------------------------------------------");
            	System.out.println("3AAAMR");
            	ligne.setDesignation(designation);
            }
            	
        	lignes.add(ligne);
            System.out.println("SIZE==============> "+lignes.size());
            calcule.setTotalHT(calcule.getTotalHT()+ligne.getPt_calculer());
            calcule.setTVA(calcule.getTotalHT()*0.2f);
            calcule.setTotalTTC(+calcule.getTotalHT()+calcule.getTVA());
            pu=0;
            qte=1;
            designation="";
            	ligne.setOffre(calcule);
                 boolean op=managerarticle.insertObject(ligne);
                 if(op==true){
                	 managerjdbc.updateOffreforAddArticle(calcule);
                	 	msg = new FacesMessage("Article ajouter avec success");
                	 	}
                 else
                	 msg = new FacesMessage("ERROR d'insertion de base de données");
            FacesContext.getCurrentInstance().addMessage(null, msg);
    	} catch (Exception e) {
    		 System.out.println("ERROOOOORRRRRRRRRR");
		}
    }
   
    public void DeleteArticle_Modif(){
        FacesMessage msg;
        try {
        		lignes.remove(ligne);
        		calcule.getLigneArticleoffre().remove(ligne);
        		marget=((ligne.getPu()*ligne.getMarge())/100);
        		calcule.setTotalHT(calcule.getTotalHT()-ligne.getPu_calculer());
        		calcule.setTVA(calcule.getTotalHT()*0.2f);
        		calcule.setTotalTTC(calcule.getTotalHT()+calcule.getTVA());
        		if(calcule.getTotalTTC()<=0)
        		{
            		calcule.setTotalHT(0);
            		calcule.setTVA(0);
            		calcule.setTotalTTC(0);
        		}
        		boolean op=managerarticle.deleteObject(ligne);
        		if(op==true){
        			 managerjdbc.updateOffreforAddArticle(calcule);
        			msg = new FacesMessage("offre est modifier");
        		}
        			
        		else
        			msg = new FacesMessage("Offre n'est pas bien Modfier");	
        		FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception e) {
                msg = new FacesMessage("ERORR DATA");
           FacesContext.getCurrentInstance().addMessage(null, msg);
            }   
    }

    public void insert(){
    	FacesMessage msg;
    	try{
    				
    			 calcule=new Offre();
            	 calcule.setTVA(TVA);
            	 calcule.setTotalHT(totalHT);
            	 calcule.setTotalTTC(totalTTC);
            	 client=managerclient.getObject(idclient);
            	 calcule.setClient(client);
            	 calcule.setDateoffre(dateoffre);
            	 calcule.setRef(ref);
            	 calcule.setDescription(description);
            	 calcule.setDisponibilite(diponibilite);
            	 calcule.setValiditeoffre(validiteoffre);
            	 calcule.setModalitepaiment(modalitepaiment);
            	 calcule.setTypeoffre(managertype.getObject(idtypeoffre));
            	 calcule.setYears(new years(3));
            	 calcule.setActiver(true);
            	 boolean op=manager.insertObject(calcule);
            	 if(op==true){
	            	 for(int i=0;i<lignes.size();i++){
	            		 lignes.get(i).setOffre(calcule);
	                     managerarticle.insertObject(lignes.get(i));
	                 }
	            	 msg = new FacesMessage("L'offre est creer avec success");
            	 }
            	 else
            		 msg = new FacesMessage("ERROR insertion");
                FacesContext.getCurrentInstance().addMessage(null, msg);
    	}catch(Exception e){
    		msg = new FacesMessage("ERROR insertion");
            FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
    	vider();
    }
    
    public void update() {
        FacesMessage msg;
        try {
        	calcule.setTypeoffre(managertype.getObject(idtypeoffre));
        	calcule.setClient(managerclient.getObject(idclient));
          	managerjdbc.UpdateOffre(calcule);
                msg = new FacesMessage("Offre est bien Modifier","");
                FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch ( NullPointerException e) {
           msg = new FacesMessage("ERROR DATA BASE","");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
   }
    
    public void Delete(){
        FacesMessage msg;
        try {
        	System.out.println("DELETE OFFRE");
            List<Offre_Article> offreArticle=managerarticle.getObjects(calcule.getId());
            for(int i=0;i<offreArticle.size();i++)
                managerarticle.deleteObject(offreArticle.get(i));
            	
            	boolean op=manager.deleteObject(calcule);
            	if(op==true)
            		msg = new FacesMessage("l'Offre est bien Supprimer");
            	else
            		msg = new FacesMessage("l'Offre est deja liée a une Facture");
            	FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception e) {
                msg = new FacesMessage("ERORR SUPPRESSION");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }   
    }

    public String preparecreer(){
    	try {
        	calcule=new Offre();
        	lignes=null;
        	lignes=new ArrayList<Offre_Article>();
        	totalHT=0;
        	totalTTC=0;
        	TVA=0;
        	modalitepaiment="30 jours à réception de la facture";
        	validiteoffre="Cette offre est valide jusqu’au ";
        	return "OffreArticle?faces-redirect=true";
		} catch (Exception e) {
			return prepareCreate();
		}

    }
    
    public void vider(){
        qte=1;
        pu=0;
        marge=20;
        TVA=0;
        totalHT=0;
        totalTTC=0;
        ref="";
        diponibilite="";
        validiteoffre="";
        modalitepaiment="";
        dateoffre=null;
        lignes=null;
        ligne=null;
        designation="";
    }
    
    public String prepareView(){
    	try {
        	lignes=null;
        	lignes=managerarticle.getObjects(calcule.getId());
        	return "Viewoffre?faces-redirect=true";
		} catch (Exception e) {
			return prepareCreate();
		}

    }
    
    public String prepareCreate(){
    	lignes=null;
    	lignes=new ArrayList<Offre_Article>();
    	vider();
    	return "index?faces-redirect=true";
    } 
    
    public void updateColumns() {
    	FacesMessage msg;
        msg = new FacesMessage("Table bien modifier");
        FacesContext.getCurrentInstance().addMessage(null, msg);
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

        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        
        try {
        	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();
        	System.out.println("ID===>"+calcule.getId());
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename="+calcule.getRef()+".pdf");
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
            Paragraph villedate=new Paragraph("Casablanca, le "+format.format(calcule.getDateoffre()),ftext);
            villedate.setIndentationLeft(300);
            document.add(villedate);
            document.add(saute);
            document.add(saute);
            document.add(saute);
            Paragraph client=new Paragraph(""+calcule.getClient().getSociete(),ftextgra);
            client.setIndentationLeft(300);
            document.add(client);
            document.add(saute);
            Phrase textref=new Phrase(calcule.getRef(),ftextgra);
            Phrase lbref=new Phrase  ("N/Réf     : ",ftext);
            Paragraph ref=new Paragraph(lbref);
            ref.add(textref);
            ref.setIndentationLeft(53);
            document.add(ref);
            Phrase textobjet=new Phrase("Proposition Financiere",ftextgra);
            Phrase lbobjet=new Phrase("Objet     : ",ftext);
            Paragraph objet=new Paragraph(lbobjet);
            objet.add(textobjet);
            objet.setIndentationLeft(53);
            document.add(objet);
            document.add(saute);
            Paragraph presentation;
            System.out.println(calcule.getTypeoffre().getType().toLowerCase());
            if(calcule.getTypeoffre().getType().toLowerCase().equals("prestation")){
            	presentation=new Paragraph("Monsieur,\n\nFaisant suite a  votre demande nous vous communiquons notre meilleure offre de prix pour la prestation des services suivants:",ftext);
            }
            else if(calcule.getTypeoffre().getType().toLowerCase().equals("renouvellement")){
            	presentation=new Paragraph("Monsieur,\n\nFaisant suite à votre demande nous vous communiquons notre meilleure offre de prix pour le renouvellement des produits suivants :",ftext);
            }
            else if(calcule.getTypeoffre().getType().toLowerCase().equals("formation")){
            	presentation=new Paragraph("Monsieur,\n\nNous avons le plaisir de vous faire parvenir notre offre de prix concernant la formation suivante :",ftext);
            }
            else if(calcule.getTypeoffre().getType().toLowerCase().equals("certification")){
            	presentation=new Paragraph("Monsieur,\n\nNous avons le plaisir de vous faire parvenir notre offre de prix pour les certifications suivantes :",ftext);
            }
            else
            	presentation=new Paragraph("Monsieur,\n\nFaisant suite a  votre demande nous vous communiquons notre meilleure offre de prix pour l'acquisition des produits suivants:",ftext);
            
            presentation.setIndentationLeft(53);
            document.add(presentation);
            document.add(saute);
            System.out.println("Imprimer***************3 SUCCES");
            
            
            DecimalFormat df = new DecimalFormat ( ) ;
            df.setMaximumFractionDigits(2) ; //arrondi à 2 chiffres apres la virgules
            df.setMinimumFractionDigits(2) ;
            df.setDecimalSeparatorAlwaysShown (true);
            
            DecimalFormat dfr = new DecimalFormat ( ) ;
	           dfr.setDecimalSeparatorAlwaysShown (true);
            
            PdfPTable table;
            if(calcule.getTypeoffre().getType().equals("formation"))
            	{
            	table= new PdfPTable(6);
            	table.setWidths(new float[]{50,180,20,30,40,50});
            	}
            else
            	{
            	table= new PdfPTable(5);
            	table.setWidths(new float[]{50,180,20,40,50});
            	}
            
            table.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        table.setWidthPercentage(90);
	        
            String titre="";
            if(calcule.getTypeoffre().getType().equals("formation"))
            	titre="N°cours";
            else
            	titre="Référence";
            PdfPCell c1 = new PdfPCell(new Phrase(titre,ftextgra));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            table.addCell(c1);
            
            if(calcule.getTypeoffre().getType().equals("formation"))
            	titre="cours";
            else
            	titre="Désignation";
            
            c1 = new PdfPCell(new Phrase(titre,ftextgra));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            table.addCell(c1);
            
            if(calcule.getTypeoffre().getType().equals("formation")){
                c1 = new PdfPCell(new Phrase("Nbre Pers",ftextgra));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
                table.addCell(c1);
            }
            if(calcule.getTypeoffre().getType().equals("formation"))
            	titre="Nbre de Jours";
            else
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
            if(calcule.getDescription()!=null){
            	if(!calcule.getDescription().equals("")){
		            c1 = new PdfPCell(new Phrase(calcule.getDescription(),ftext));
		            c1.setColspan(5);
		            c1.setBackgroundColor(new BaseColor(Color.decode("#D9D9D9")));
		            table.addCell(c1);
            	}
            }
               List<Offre_Article> offreArticle=managerarticle.getObjects(calcule.getId());
               double prixtva=0f;
           	double prixht=0;
            if(offreArticle!=null){
            	
            	int nbrtest= offreArticle.size();
	               for(int i=0;i<offreArticle.size();i++)
	            {
	            	
	            	System.out.println("offreArticle");
	            	if(offreArticle.get(i).getArticle()!=null){
	            		if(!offreArticle.get(i).getArticle().getTypearticle().getType().toLowerCase().equals("contrat")){
		            		
	            			if(offreArticle.get(i).getArticle().getRef()==null)
		            			offreArticle.get(i).getArticle().setRef(" ");
		            		
		    
		            		c1 = new PdfPCell(new Phrase(offreArticle.get(i).getArticle().getRef(),ftext));
		                    
		                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		                    c1.setVerticalAlignment(100);
		                    if(nbrtest<10)
				            	c1.setPadding(15);
				            table.addCell(c1);
				            
				            if(offreArticle.get(i).getDesignation()==null)
		            			offreArticle.get(i).setDesignation(" ");
				            
				            Phrase para=new Phrase(offreArticle.get(i).getDesignation(),ftext);
				      
				            	c1 = new PdfPCell(para);
				            	if(nbrtest<10)
				            	c1.setPadding(15);
				            table.addCell(c1);
				            System.out.println("Forfait ....");
				            if(calcule.getTypeoffre().getType().equals("formation")){

					            c1 = new PdfPCell(new Phrase(offreArticle.get(i).getNombrepersonne()+"",ftext));
					            
					        	c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           if(nbrtest<10){
						            	c1.setPaddingTop(15);
						            	c1.setPaddingBottom(15);
						           }
					            table.addCell(c1);
				            }
				            c1 = new PdfPCell(new Phrase(offreArticle.get(i).getLbqte(),ftext));
				            
				            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
					           if(nbrtest<10){
					        	   c1.setPaddingTop(15);
					            	c1.setPaddingBottom(15);
					           }
				            table.addCell(c1);
				            
				            c1 = new PdfPCell(new Phrase(df.format(offreArticle.get(i).getPu_calculer()),ftext));
				       	    
				            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           if(nbrtest<10){
					        	   c1.setPaddingTop(15);
					            	c1.setPaddingBottom(15);
					           }
	
				            table.addCell(c1);
				            
				            c1 = new PdfPCell(new Phrase(df.format(offreArticle.get(i).getPt_calculer()),ftext));
				            
				            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           if(nbrtest<10){
					        	   c1.setPaddingTop(15);
					            	c1.setPaddingBottom(15);
					           }
				            table.addCell(c1);
				            
				            prixht=(prixht+offreArticle.get(i).getPt_calculer());
	            	}
	            	}
	            }
            }
            
            PdfPCell totalHT = new PdfPCell(new Phrase("Total HT",ftextgra));
            
            if(calcule.getTypeoffre().getType().equals("formation"))
            	totalHT.setColspan(5);
            else
            	totalHT.setColspan(4);
            
            totalHT.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            table.addCell(totalHT);
            
            double d =prixht;
	        int nombre = (int)Math.floor(d);
	        double j = d - nombre;
	        double k = j*100;
	        int virgule=(int)k;
	        
	        if(virgule==0)
	        	c1 = new PdfPCell(new Phrase(dfr.format(nombre)+"00",ftextgra));
	        else
	        	c1 = new PdfPCell(new Phrase(dfr.format(nombre)+virgule+"",ftextgra));
	        
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);
            
            PdfPCell tva = new PdfPCell(new Phrase("TVA 20%",ftextgra));
            if(calcule.getTypeoffre().getType().equals("formation"))
            	tva.setColspan(5);
            else
            	tva.setColspan(4);
            tva.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            table.addCell(tva); 
            prixtva=prixht*0.2;
            
            d =prixtva;
	        nombre = (int)Math.floor(d);
	        j = d - nombre;
	        k = j*100;
	        virgule=(int)k;
	        
	        if(virgule==0)
	        	c1 = new PdfPCell(new Phrase(dfr.format(nombre)+"00",ftextgra));
	        else
	        	c1 = new PdfPCell(new Phrase(dfr.format(nombre)+virgule+"",ftextgra));
	        
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
             
            PdfPCell totalTTC = new PdfPCell(new Phrase("Total DH TTC",ftextgra));
            
            if(calcule.getTypeoffre().getType().equals("formation"))
            	totalTTC.setColspan(5);
            else
            	totalTTC.setColspan(4);
            
            totalTTC.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
            table.addCell(totalTTC);
            
            d =calcule.getTotalTTC();
	        nombre = (int)Math.floor(d);
	        j = d - nombre;
	        k = j*100;
	        virgule=(int)k;
	        
	        if(virgule==0)
	        	c1 = new PdfPCell(new Phrase(dfr.format(nombre)+"00",ftextgra));
	        else
	        	c1 = new PdfPCell(new Phrase(dfr.format(nombre)+virgule+"",ftextgra));
	        
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            
            document.add(table);
            document.add(saute);
            //******************Contrat
            /*
            if(managerjdbc.getExisteArticleContratOrServiceInOffre(calcule.getId())>0){
            	
                Paragraph contratsupport=new Paragraph("Contrat de support",forange);
                contratsupport.setIndentationLeft(53);
                document.add(contratsupport);
                document.add(saute);
                PdfPTable tablecontrat = new PdfPTable(3);
                tablecontrat.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tablecontrat.setWidthPercentage(90);
                tablecontrat.setWidths(new float[]{200,40,40});
                PdfPCell cellcontrat = new PdfPCell(new Phrase("Descriptions des Services",ftextgra));
                cellcontrat.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellcontrat.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
                tablecontrat.addCell(cellcontrat);
                cellcontrat = new PdfPCell(new Phrase("Prix en DHS (HT)",ftextgra));
                cellcontrat.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellcontrat.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
                tablecontrat.addCell(cellcontrat);
                cellcontrat = new PdfPCell(new Phrase("Prix en DHS (TTC)",ftextgra));
                cellcontrat.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellcontrat.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
                tablecontrat.addCell(cellcontrat);
                
                if(offreArticle!=null){
                	int nbrtest= offreArticle.size();
    	               for(int i=0;i<offreArticle.size();i++)
    	            {
    	            	
    	            	System.out.println("offreArticle");
    	            	if(offreArticle.get(i).getArticle()!=null){
    	            		if(offreArticle.get(i).getArticle().getTypearticle().getType().toLowerCase().equals("contrat")){
    	            			
    	            			if(offreArticle.get(i).getArticle().getDesignation()==null)
    		            			offreArticle.get(i).getArticle().setDesignation(" ");
    				            
    				            	c1 = new PdfPCell(new Phrase(offreArticle.get(i).getArticle().getDesignation(),ftext));
    				            	if(nbrtest<5)
    				            	c1.setPadding(30);
    				            	tablecontrat.addCell(c1);
    				            	
    				            	c1 = new PdfPCell(new Phrase(df.format(offreArticle.get(i).getPt_calculer()),ftext));
    				            	c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
    						           if(nbrtest<10){
    						            	c1.setPaddingTop(30);
    						            	c1.setPaddingBottom(30);
    						           }
    				            	tablecontrat.addCell(c1);
    				            	
    				            	float TVAcontrat=offreArticle.get(i).getPt_calculer()*0.2f;
    				            	float prixTTC=TVAcontrat+offreArticle.get(i).getPt_calculer();
    				            	c1 = new PdfPCell(new Phrase(df.format(prixTTC),ftext)); 
    				            	c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
    				            	if(nbrtest<10){
						            	c1.setPaddingTop(30);
						            	c1.setPaddingBottom(30);
						           }
    				            	tablecontrat.addCell(c1);
    	            		}
    	            	}
    	            }
                }
                document.add(tablecontrat);
                Paragraph clausecontrat=new Paragraph("Les clauses du contrat vous seront envoyées prochainement.",ftextgra);
                clausecontrat.setIndentationLeft(53);
                document.add(clausecontrat);
            }
            */
            //******************Fin
            Paragraph pie=new Paragraph();
            
            
            if(calcule.getDisponibilite()!=null){
            	if(!calcule.getDisponibilite().equals("")){
		            pie.add(new Paragraph("Disponibilité",forange));
		            pie.add(new Paragraph(calcule.getDisponibilite(),ftext));
            	}
            }
            String[] tab;
            if(calcule.getValiditeoffre()!=null){
            	if(!calcule.getValiditeoffre().equals("")){
            	pie.add(new Paragraph("Validité de l'offre",forange));
	            tab=calcule.getValiditeoffre().split("\n");
	            for(int i=0;i<tab.length;i++)
	            	pie.add(new Paragraph(tab[i],ftext));
            	}
            }
            if(calcule.getModalitepaiment()!=null){
            	if(!calcule.getModalitepaiment().equals("")){
	            	   pie.add(new Paragraph("Modalité de paiement",forange));
	   	            tab=calcule.getModalitepaiment().split("\n");
	   	            for(int i=0;i<tab.length;i++)
	   	            	pie.add(new Paragraph(""+tab[i],ftext));
            	}
            }
            pie.add(saute);
            pie.add(new Phrase("Nous restons à votre entière disposition pour toute information complémentaire.\nDans l’attente, veuillez accepter, Monsieur, nos cordiales salutations",ftext));
            pie.setIndentationLeft(53);
            document.add(pie);
            System.out.println("Imprimer***************FIN SUCCES");
            document.addAuthor("IMS Technology");
            document.addTitle("Bon Commande");
            document.addSubject("Offre N°"+calcule.getRef());
            document.addCreator("RABEH");
            document.close();
            FacesContext.getCurrentInstance().responseComplete();
            SessionIMS.session.clear();
            SessionIMS.session.close();
        } catch (DocumentException | IOException de) {
            System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF");
            document.close();
            FacesContext.getCurrentInstance().responseComplete();
        }
      }
    
    /****************PDF************************/
	 
    public void imprimer(){
    String dest="C:/IMS.pdf";
    File file = new File(dest);
    file.getParentFile().mkdirs();
    try { 	
    	createPdf(dest);
    	createPdfImage();
    	 
    	
    } catch (IOException | DocumentException e) {
    	// TODO Auto-generated catch block
    	e.printStackTrace();
    }

    }		 


    public void createPdfImage() throws FileNotFoundException, DocumentException, IOException{

    PdfReader reader;			
    reader = new PdfReader("C:/IMS.pdf");

    int n = reader.getNumberOfPages(); 
    HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
    PdfStamper stamp = new PdfStamper(reader,response.getOutputStream()); 
    	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
    int i = 1; 
    com.itextpdf.text.pdf.PdfContentByte under; 
    com.itextpdf.text.pdf.PdfContentByte over; 
    Image img = Image.getInstance(webroot+"\\fondOffre.png"); 
    img.scaleAbsoluteHeight(PageSize.A4.getHeight());
    img.scaleAbsoluteWidth(PageSize.A4.getWidth());
    img.setAbsolutePosition(0, 0);
    BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI, BaseFont.EMBEDDED); 


    for(i=1;i<=n;i++){ 
    // img under the existing page 
    under = stamp.getUnderContent(i); 
    under.addImage(img);
    // Text over the existing page 
    over = stamp.getOverContent(i); 
    over.beginText(); 
    over.setFontAndSize(bf, 18);
    over.endText(); 
    } 
    stamp.close();	

    }
          
    public Offre getCalcule() {
        return calcule;
    }

    public void setCalcule(Offre calcule) {
        this.calcule = calcule;
    }

    public List<Offre> getCalcules() {
    	calcules=null;
        calcules=manager.getObject();
        for(int i=0;i<calcules.size();i++)
        	{
        	
        	System.out.println("-----Active==>-----------"+calcules.get(i).getActiver()+"-----<===--Activer-----------------");
        	}
        return calcules;
    }

    public void setCalcules(List<Offre> calcules) {
        this.calcules = calcules;
    }

    public List<Offre> getFiltredcalcules() {
        return filtredcalcules;
    }

    public void setFiltredcalcules(List<Offre> filtredcalcules) {
        this.filtredcalcules = filtredcalcules;
    }

    public ModelService<Offre> getManager() {
        return manager;
    }

    public void setManager(ModelService<Offre> manager) {
        this.manager = manager;
    }
    
    public Offre_Article getLigne() {
		return ligne;
	}

	public void setLigne(Offre_Article ligne) {
		this.ligne = ligne;
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
            manager.updateObject(((Offre) event.getObject()));
             msg = new FacesMessage("Offre est bien Modifier",""+((Offre) event.getObject()).getId());
                FacesContext.getCurrentInstance().addMessage(null, msg);
         } catch (Exception e) {
            msg = new FacesMessage("Offre est mal Modifier",""+((Offre) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
         }
        
    }
     
    public void onRowCancel(RowEditEvent event) {

       FacesMessage msg = new FacesMessage("Modification ete Annuler",""+((Offre) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<Offre_Article> getLignes() {
      return lignes;
    }

    public void setLignes(List<Offre_Article> lignes) {
        this.lignes = lignes;
    }

    public List<Offre_Article> getLignesView() {
        return lignesView;
    }

    public void setLignesView(List<Offre_Article> lignesView) {
        this.lignesView = lignesView;
    }

    public ModelService<Offre_Article> getManagerarticle() {
        return managerarticle;
    }

    public void setManagerarticle(ModelService<Offre_Article> managerarticle) {
        this.managerarticle = managerarticle;
    }

	

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}





	public int getMarge() {
		return marge;
	}

	public void setMarge(int marge) {
		this.marge = marge;
	}

	
	public double getMarget() {
		return marget;
	}

	public void setMarget(double marget) {
		this.marget = marget;
	}

	public double getQte() {
		return qte;
	}

	public void setQte(double qte) {
		this.qte = qte;
	}

	public double getPu() {
		return pu;
	}

	public void setPu(double pu) {
		this.pu = pu;
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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getRef() {
    	Calendar cal = Calendar.getInstance();
    	String chaine=""+cal.get(Calendar.YEAR);
    	String[] tab=chaine.split("0");
    	int lastnumbre=managerjdbc.getLastNumbre("Offre","Numero_offre",chaine);
    	lastnumbre++;
    	String nombre=String.format("%04d",lastnumbre);
    	ref=tab[1]+"OP"+nombre;
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Date getDateoffre() {
    	Calendar cal = Calendar.getInstance();
    	dateoffre=cal.getTime();
		return dateoffre;
	}

	public void setDateoffre(Date dateoffre) {
		this.dateoffre = dateoffre;
	}

	public int getIdarticle() {
		return idarticle;
	}

	public void setIdarticle(int idarticle) {
		this.idarticle = idarticle;
	}

	public int getIdclient() {
		return idclient;
	}

	public void setIdclient(int idclient) {
		this.idclient = idclient;
	}

	public ModelService<Article> getManagerObjetarticle() {
		return managerObjetarticle;
	}

	public void setManagerObjetarticle(ModelService<Article> managerObjetarticle) {
		this.managerObjetarticle = managerObjetarticle;
	}

	public ModelService<Client> getManagerclient() {
		return managerclient;
	}

	public void setManagerclient(ModelService<Client> managerclient) {
		this.managerclient = managerclient;
	}

	public String getDiponibilite() {
		return diponibilite;
	}

	public void setDiponibilite(String diponibilite) {
		this.diponibilite = diponibilite;
	}

	public String getValiditeoffre() {
		return validiteoffre;
	}

	public void setValiditeoffre(String validiteoffre) {
		this.validiteoffre = validiteoffre;
	}

	public String getModalitepaiment() {
		return modalitepaiment;
	}

	public void setModalitepaiment(String modalitepaiment) {
		this.modalitepaiment = modalitepaiment;
	}

	public ModelService<TypeOffre> getManagertype() {
		return managertype;
	}

	public void setManagertype(ModelService<TypeOffre> managertype) {
		this.managertype = managertype;
	}

	public List<TypeOffre> getTypeoffres() {
		typeoffres=managertype.getObject();
		return typeoffres;
	}

	public void setTypeoffres(List<TypeOffre> typeoffres) {
		this.typeoffres = typeoffres;
	}

	public int getIdtypeoffre() {
		return idtypeoffre;
	}

	public void setIdtypeoffre(int idtypeoffre) {
		this.idtypeoffre = idtypeoffre;
	}

	public boolean isCheckforfait() {
		return checkforfait;
	}

	public void setCheckforfait(boolean checkforfait) {
		this.checkforfait = checkforfait;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public int getNbrper() {
		return nbrper;
	}

	public void setNbrper(int nbrper) {
		this.nbrper = nbrper;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
    
}
