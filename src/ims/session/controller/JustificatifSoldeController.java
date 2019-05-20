package ims.session.controller;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ims.Exception.ImsErpException;
import ims.model.entities.Facture;
import ims.model.entities.FactureFournisseur;
import ims.model.entities.years;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
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
@RequestScoped
public class JustificatifSoldeController implements Serializable {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
	
	
	@ManagedProperty(value="#{yearsManager}")
	private ModelService<years> manageryears;
	
	@ManagedProperty(value="#{FactureManager}")
	private ModelService<Facture> manager;
	
    @ManagedProperty(value="#{FactFournisseurManager}")
    private ModelService<FactureFournisseur> managerFactFournisseur;
    
    private List<years> listeyears;
   

	private List<Facture> factures;
    private List<FactureFournisseur> facturesFournisseur;
    private String idannee;
    private String typejustif;
    
    public void annuler(){
    	 FacesMessage msg;
    	 try {
    		 	typejustif="";
    		 	idannee="";
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    public void imprimer() throws FileNotFoundException,ImsErpException{
    	String dest="C:/IMS.pdf";
    	 FacesMessage msg;
        try {
        	if(!typejustif.equals("") && !idannee.equals("0")){
        		File file = new File(dest);
        		file.getParentFile().mkdirs();	
        		createPdf(dest);
				createPdfImage();
        	}else{
        		msg = new FacesMessage("Selectionner le Type Justificatif et aussi l'année");
     	       FacesContext.getCurrentInstance().addMessage(null, msg);
        	}

			} catch (DocumentException
					| IOException | NullPointerException e) {
				e.printStackTrace();
			}
        }
    
 	public void createPdf(String dest){
		   System.out.println("Imprimer*************** SUCCES");
		   SimpleDateFormat ft= new SimpleDateFormat("dd/MM/yy");
		   String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
	   		Paragraph saute=new Paragraph("\n");
	       Document document = new Document(PageSize.A4);
	       HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	       
	       try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
	    	   document.open();
	    	   response.setContentType("application/pdf");
	           response.setHeader("Content-Disposition", "inline; filename=IMSJustificationSolde.pdf");
	           System.out.println("setHeader Success");
	          
	           document.setMargins(36, 36, 108, 180);
	           System.out.println("OPEN SUCESS");
	           document.add(saute);
	           
	           Font ftextgra=new Font(Font.FontFamily.HELVETICA,15,Font.BOLD,new BaseColor(Color.black));
	           Font ftextToutaux=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
	           Font ftext=new Font(Font.FontFamily.HELVETICA,8,Font.NORMAL,new BaseColor(Color.black));
	           Paragraph titre=new Paragraph("Justificatif de solde général",ftextgra);
	           titre.setAlignment(Element.ALIGN_CENTER);
	           document.add(titre);
	           document.add(saute);
	           titre=new Paragraph();
	           years y=manageryears.getObject(Integer.parseInt(idannee));
	           System.out.println(y.getAnnee());
	           Phrase ph=new Phrase("Période du : 1/1/"+y.getAnnee(),ftext);
	           titre.add(ph);
	           titre.add(saute);
	           ph=new Phrase("au : 1/12/"+y.getAnnee(),ftext);
	           titre.add(ph);
	           titre.setAlignment(Element.ALIGN_RIGHT);
	            document.add(titre);
	            titre=new Paragraph("Date de Tirage :"+ft.format(new Date()),ftextgra);
	            titre.setAlignment(Element.ALIGN_CENTER);
	            document.add(titre);
	            
	           
	           
	           document.add(saute);
	          
	           
	           System.out.println("------------------ Debut Table -----------------");
	           PdfPTable table;
	        	   table = new PdfPTable(7);
		           table.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.setWidthPercentage(100);
		           table.setWidths(new float[]{40,40,20,150,40,40,50});
	           
	           
		       PdfPCell c1 = new PdfPCell(new Phrase("Date",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("C.J",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("N°",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);

	           c1 = new PdfPCell(new Phrase("Libelle",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	     
	           c1 = new PdfPCell(new Phrase("Debit",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Credit",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Solde",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           table.setHeaderRows(1);
	           
	           if(typejustif.equals("service") || typejustif.equals("aquisition") || typejustif.equals("prestation")){
	        	   System.out.println("------------------ IF -----------------");
			        	   String typefacture="";
			        	   String typefacturesimple="";
			        	   if(typejustif.equals("prestation")){
			        		   typefacture="Contrat";
			        		   typefacturesimple="";
			        	   } 
			        	   else if(typejustif.equals("aquisition")){
			        		   typefacture="Simple";
			        		   typefacturesimple="Acquisition";
			        	   }else{
			        		   typefacture="Simple";
			        		   typefacturesimple="service";
			        	   }
			        		   
			        	   System.out.println("------------------ TYPE FACTURE SUCCESS -----------------");
			        	   double cumule=0;
			        	   System.out.println("------------------ PREPARE getByNames -----------------");
			        	   factures=manager.getByNames("stat",typefacture,typefacturesimple,idannee);
			        	   System.out.println("------------------GET  -----------------");
			        	   System.out.println(factures.size());
			        	   DecimalFormat df = new DecimalFormat("0.##");
				           if(factures!=null){  
					           for(int i=0;i<factures.size();i++)
					           {
					        	   	if(factures.get(i).getDatefacture()!=null)
					        	   		c1 = new PdfPCell(new Phrase(ft.format(factures.get(i).getDatefacture()),ftext));
					        	   	else
					        	   		c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase("VENTE",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+factures.get(i).getId(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           if(factures.get(i).getClient().getSociete()!=null && factures.get(i).getRef()!=null)
						        	   c1 = new PdfPCell(new Phrase(factures.get(i).getClient().getSociete()+"/"+factures.get(i).getRef(),ftext));
						           else
						        	   c1 = new PdfPCell(new Phrase(factures.get(i).getClient().getSociete()+"/"+factures.get(i).getRef(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(""+factures.get(i).getTotalht(),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
						           
						           c1 = new PdfPCell(new Phrase(" ",ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
						           table.addCell(c1);
						           cumule+=factures.get(i).getTotalht();
						           
						           c1 = new PdfPCell(new Phrase(""+df.format(cumule),ftext));
						           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						           table.addCell(c1);
					           }
					           c1 = new PdfPCell(new Phrase("Totaux",ftextToutaux));
					           c1.setColspan(4);
					           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(c1);
					           c1 = new PdfPCell(new Phrase(df.format(cumule),ftextToutaux));
					           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(c1);
					           c1 = new PdfPCell(new Phrase(" "));
					           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
					           table.addCell(c1);
					           c1 = new PdfPCell(new Phrase(df.format(cumule),ftextToutaux));
					           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           table.addCell(c1);
				           }
				           
	           }else{
	        	   
	        	   System.out.println("RECUP FACTURE Fournisseur ...");
	        	   facturesFournisseur=managerFactFournisseur.getByNames("stat",idannee);
	        	   System.out.println("RECUP FACTURE Fournisseur ...SUCCESS");
		           if(facturesFournisseur!=null){
		        	   double cumulef=0;
		        	   System.out.println("size forunisseur=>"+facturesFournisseur.size());
		        	   DecimalFormat df = new DecimalFormat("0.##");
			           for(int i=0;i<facturesFournisseur.size();i++)
			           {
			        	   	
			        	   if(facturesFournisseur.get(i).getDatefacture()!=null)
			        		   c1 = new PdfPCell(new Phrase(ft.format(facturesFournisseur.get(i).getDatefacture()),ftext));
			        	   else
			        		   c1 = new PdfPCell(new Phrase(" ",ftext));
				           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				           table.addCell(c1);
				           
				           c1 = new PdfPCell(new Phrase("ACHAT",ftext));
				           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				           table.addCell(c1);
				           
				           c1 = new PdfPCell(new Phrase(""+facturesFournisseur.get(i).getId(),ftext));
				           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				           table.addCell(c1);
				           if(facturesFournisseur.get(i).getCommandefournissuer()!=null && facturesFournisseur.get(i).getNum_facture()!=null)
				        	   c1 = new PdfPCell(new Phrase(facturesFournisseur.get(i).getCommandefournissuer().getFournisseur().getNomsociete()+"/"+facturesFournisseur.get(i).getNum_facture(),ftext));
				           else
				        	   c1 = new PdfPCell(new Phrase(" ",ftext));
				           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				           table.addCell(c1);
				           
				           c1 = new PdfPCell(new Phrase(""+facturesFournisseur.get(i).getTotalht(),ftext));
				           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				           table.addCell(c1);
				           System.out.println("total "+facturesFournisseur.get(i).getTotalht());
				           c1 = new PdfPCell(new Phrase(" ",ftext));
				           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				           table.addCell(c1);
				           cumulef=cumulef+facturesFournisseur.get(i).getTotalht();
				           
				           c1 = new PdfPCell(new Phrase(""+df.format(cumulef),ftext));
				           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				           table.addCell(c1);
			           }
			           c1 = new PdfPCell(new Phrase("Totaux",ftextToutaux));
			           c1.setColspan(4);
			           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			           table.addCell(c1);
			           c1 = new PdfPCell(new Phrase(df.format(cumulef),ftextToutaux));
			           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			           table.addCell(c1);
			           c1 = new PdfPCell(new Phrase(" "));
			           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			           table.addCell(c1);
			           c1 = new PdfPCell(new Phrase(df.format(cumulef),ftextToutaux));
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
	       } catch (DocumentException | FileNotFoundException | NullPointerException de) {
				FacesMessage msg = new FacesMessage("Probleme de generation de PDF","contacter l'administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
	           System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF "+de.getMessage());
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
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
    
    
	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
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

	

	public List<Facture> getFactures() {
		return factures;
	}

	public void setFactures(List<Facture> factures) {
		this.factures = factures;
	}

	public List<FactureFournisseur> getFacturesFournisseur() {
		return facturesFournisseur;
	}

	public void setFacturesFournisseur(List<FactureFournisseur> facturesFournisseur) {
		this.facturesFournisseur = facturesFournisseur;
	}

	public String getIdannee() {
		return idannee;
	}

	public void setIdannee(String idannee) {
		this.idannee = idannee;
	}

	public String getTypejustif() {
		return typejustif;
	}

	public void setTypejustif(String typejustif) {
		this.typejustif = typejustif;
	}
	
	 public List<years> getListeyears() {
	    	if(listeyears==null)
	    		listeyears=manageryears.getObject();
			return listeyears;
		}

		public void setListeyears(List<years> listeyears) {
			this.listeyears = listeyears;
		}

	public ModelService<years> getManageryears() {
		return manageryears;
	}

	public void setManageryears(ModelService<years> manageryears) {
		this.manageryears = manageryears;
	}
    
    
  
}
