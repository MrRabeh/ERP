package ims.session.controller;

import ims.model.entities.Caisse;
import ims.model.entities.Employee;
import ims.model.entities.LigneCaisse;
import ims.model.entities.years;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.primefaces.event.CellEditEvent;





import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
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
@SessionScoped
public class CaisseController implements Serializable{

	   /**
		* creator RABEH TARIK
	    **/
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{CaisseManager}")
	private ModelService<Caisse> manager;
	
	@ManagedProperty(value="#{LigneCaisseManager}")
	private ModelService<LigneCaisse> managerlignecaisse;
	
    @ManagedProperty(value="#{EmployeeManager}")
    private ModelService<Employee> manageremp;
	
	@ManagedProperty(value="#{JDBCManager}")
	private ModelServiceJDBC managerjdbc;
	
	@ManagedProperty(value="#{yearsManager}")
	private ModelService<years> manageryears;
	
    private List<years> listeyears;
	
	private Caisse caisse=new Caisse();
	private List<Caisse> listecaisse;
	private List<Caisse> filtredlistecaisse=new ArrayList<Caisse>();
	private LigneCaisse ligneparcaisse=new LigneCaisse();
	private LigneCaisse lignetempo;
	private List<LigneCaisse> lignesparcaisse;
	private List<LigneCaisse> filtredlignesparcaisse=new ArrayList<LigneCaisse>();
	
	private Date datedebutcaisse;
	private Date datefincaisse;
	private Date jour;
	private boolean checkdjr;
	private double entree;
	private String reg="";
	private double sortie;
	private String detail="";
	private int idemp;
	private int idempmodfi=-1;
	private List<Employee> listemp;
	
	private Date   tempodate=null;
	private String tempoentree="";
	private String temposortie="";
	private String tempodetail="";
	private String temporeg="";
	
	private Date tempodatefin=null;
	private Date tempodatedebut=null;
	
    private SimpleDateFormat ft= new SimpleDateFormat("yyyy");
    private SimpleDateFormat ftletter= new SimpleDateFormat("dd MMMMM YYYY");
    
    private String[] tabmois=new String[]{"janvier","fevrier","mars","avril","mai","juin","juillet","aout","septembre","octobre","novembre","decembre"};
	
    private int idannee;
    private String choixmois;
    private double sommeentrer=0;
    private double sommesortie=0;
    private double reste=0;
	
	@PostConstruct
    public void init(){
		try {
			System.out.println("INIT Caisse Controller");
			sommeentrer=managerjdbc.getSommeCaisseEntrer();
			sommesortie=managerjdbc.getSommeCaisseSortie();
			reste=sommeentrer-sommesortie;
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    }
	
	 public List<years> getListeyears() {
	    	if(listeyears==null)
	    		listeyears=manageryears.getObject();
			return listeyears;
		}

		public void setListeyears(List<years> listeyears) {
			this.listeyears = listeyears;
		}
	
	public void actualiser(){
		listecaisse=null;
	}
	
	public CaisseController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String preparecreate(){
		caisse=new Caisse();
		return "createcaisse?faces-redirect=true";
	}
	
	public String decaisser(){
		lignesparcaisse=null;
		if(caisse==null)
			return "caisses?faces-redirect=true";
		else{
		Calendar cal = Calendar.getInstance();
		jour=cal.getTime();
		System.out.println("ID caisse=>"+caisse.getId());
		return "createlignescaisse?faces-redirect=true";
		}
	}
	
	public void annuler(){
		FacesMessage msg;
		try {
			datedebutcaisse=null;
			datefincaisse=null;
		    msg = new FacesMessage("la caisse Annuler avec success");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
		    msg = new FacesMessage("Exception");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void imprimerPDF(){/*
		System.out.println("Caisse ID =>"+caisse.getId());
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
				*/
		String dest="C:/IMS.pdf";
        try {
        	System.out.println("-----  Imprimer PDF ---------");
        File file = new File(dest);
        file.getParentFile().mkdirs();	
        	createPdf(dest);
				createPdfImage();
			} catch (DocumentException
					| IOException | NullPointerException e) {
				e.printStackTrace();
			}
	}
	
	private void createPdfImage() throws IOException, DocumentException {
		  
			PdfReader reader;			
	        reader = new PdfReader("C:/IMS.pdf");

	        int n = reader.getNumberOfPages(); 
	        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	        PdfStamper stamp = new PdfStamper(reader,response.getOutputStream()); 
	        	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
	        int i = 1; 
	        com.itextpdf.text.pdf.PdfContentByte under; 
	        com.itextpdf.text.pdf.PdfContentByte over; 
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

	private void createPdf(String dest) {
		 
		DateFormat format=new SimpleDateFormat("dd/MM/yy");
		   String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
	   		Paragraph saute=new Paragraph("\n");
	       Document document = new Document(PageSize.A4);
	       HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	       try {
	    	   Font ftext=new Font(Font.FontFamily.HELVETICA,9,Font.NORMAL,new BaseColor(Color.black));
	            Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
	    	   PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
	    	 
	           response.setContentType("application/pdf");
	           //response.setHeader("Content-Disposition", "inline; filename=caisse_"+caisse.getMoiscaisse()+".pdf");
	           response.setHeader("Content-Disposition", "inline; filename=caisse.pdf");
		          
	           document.open();
	           document.setMargins(36, 36, 108, 180);
	           document.add(saute);
	  
	           
	           
	           DecimalFormat df = new DecimalFormat();
	           df.setDecimalSeparatorAlwaysShown(true);
	           df.setMaximumFractionDigits(2);
	           df.setMinimumFractionDigits(2);
	          
	        
	           document.add(saute);
	           Font ftextgrafact=new Font(Font.FontFamily.HELVETICA,15,Font.BOLD,new BaseColor(Color.black));
	           
	           years y=manageryears.getObject(idannee);
	   			GregorianCalendar calendar = new GregorianCalendar(); 
	   			calendar.setTime(y.getYears()); 
	   			int year=calendar.get(GregorianCalendar.YEAR);
	   			Paragraph titrecaisse;;
	   			if(choixmois.equals("0"))
	   				 titrecaisse=new Paragraph("Caisse de l'Année "+year,ftextgrafact);
	   			else
	   				titrecaisse=new Paragraph("Caisse de Mois "+choixmois +" Année "+year,ftextgrafact);
	   			
	   			titrecaisse.setAlignment(Element.ALIGN_LEFT);
	   			titrecaisse.setAlignment(Element.ALIGN_TOP);
	   			titrecaisse.setIndentationLeft(53);
	           document.add(titrecaisse);
	           
	           document.add(saute);
	          
	           document.add(saute);
	           
	           System.out.println("-----  CAISSE ---------");
	           PdfPTable table= new PdfPTable(4);
	           table.setWidths(new float[]{80,50,50,150});
	           
	           PdfPCell c1 = new PdfPCell(new Phrase("Date",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Entree",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("Sortie",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);

	           c1 = new PdfPCell(new Phrase("Detail",ftextgra));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(c1);
	           boolean trouve=false;
	           for(int i=0;i<tabmois.length;i++){
	        	   if(tabmois[i].equals(choixmois)){
	        		   int m=i;
	        		   m++;
	        		   if(m<10)
	        			   choixmois="0"+m;
	        		   else
	        			   choixmois=""+m;
	        		   trouve=true;
	        	   }
	        	   if(trouve==true)
	        		   break;
	           }
	           System.out.println("-----  GET LIGNE CAISSE ---------");
	           List<LigneCaisse> lignecaisse= managerjdbc.getLigneCaisse(choixmois,year);
	           double sommeentree=0;
	           double sommesortie=0;
	           double sommeReste=0;
	           if(lignecaisse!=null){
	        	  
		           for(int i=0;i<lignecaisse.size();i++)
		           {
	
		        		   table.addCell(new Phrase(""+ftletter.format(lignecaisse.get(i).getDateligne()),ftext));
		        		   table.addCell(new Phrase(""+lignecaisse.get(i).getEntree(),ftext));
				           table.addCell(new Phrase(""+lignecaisse.get(i).getSortie(),ftext));
				           table.addCell(new Phrase(""+lignecaisse.get(i).getDetails(),ftext));
				           sommeentree+=lignecaisse.get(i).getEntree();
				           sommesortie+=lignecaisse.get(i).getSortie();
		           }
		           sommeReste=sommeentree-sommesortie;
		           document.add(table);
	           }
	           System.out.println("----- FIN  GET LIGNE CAISSE ---------");
	           Paragraph presultat=new Paragraph();
	           presultat.setAlignment(Element.ALIGN_LEFT);
	           presultat.setAlignment(Element.ALIGN_TOP);
	           presultat.setIndentationLeft(53);
	           Phrase Resultat=new Phrase("Total Entrer : "+sommeentree+"\n",ftext);
	           presultat.add(Resultat);
	           Resultat=new Phrase("Total Sortie : "+sommesortie+"\n",ftext);
	           presultat.add(Resultat);
	           Resultat=new Phrase("Total Reste : "+sommeReste+"\n",ftext);
	           presultat.add(Resultat);
	           document.add(saute);
	           document.add(presultat);
	           
	           document.addAuthor("IMS Technology");
	           document.addTitle("Caisse");
	           document.addSubject("Caisse");
	           document.addCreator("RABEH");
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       } catch (DocumentException | IOException | NullPointerException de) {
				FacesMessage msg = new FacesMessage("Probleme de generation de PDF","contacter l'administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
	           System.out.println("ERRROR PDF"+de.getMessage());
	           document.close();
	           FacesContext.getCurrentInstance().responseComplete();
	       }
		
	}

	public void imprimer(){
		
		 HSSFWorkbook wb = new HSSFWorkbook();
		 HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		  
		  OutputStream out;
		try {
			out = response.getOutputStream();

	      response.setContentType("application/vnd.ms-excel");

	      response.setHeader("Content-disposition", "inline; filename=Caisse_Mois_"+caisse.getMoiscaisse()+".xls");

	        HSSFSheet sheet = wb.createSheet("ma feuille");
	        String Mois[]={"Mois","Janvier","Fevrier","Mars","Avril","Mai","juin","Juillet","aout","Septembre","Octobre","Novembre","Décembre"};
		    HSSFRow row = sheet.createRow(0);
		    HSSFCell cell = null;
		    HSSFCellStyle cellStyle = null;
		    
		    cell = row.createCell((short) 0);
		    cell.setCellValue("Caisse");
		    cellStyle = wb.createCellStyle();
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		    cell.setCellStyle(cellStyle);
		    
		    row = sheet.createRow(1);
		    cell = row.createCell((short) 2);
		    cell.setCellValue("Mois "+Mois[Integer.parseInt(caisse.getMoiscaisse())]+" "+ft.format(caisse.getObjyear().getYears()));
		    sheet.addMergedRegion(new Region(0,(short)0,0,(short)4));
		    
		    row = sheet.createRow(3);
		    cell = row.createCell((short) 0);
		    cell.setCellValue("Date");
		    cellStyle = wb.createCellStyle();
		    cellStyle.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cell.setCellStyle(cellStyle); 
		    
		    cell = row.createCell((short) 1);
		    cell.setCellValue("Entres");
		    cellStyle = wb.createCellStyle();
		    cellStyle.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cell.setCellStyle(cellStyle); 
		    
		    cell = row.createCell((short) 2);
		    cell.setCellValue("Sorties");
		    cellStyle = wb.createCellStyle();
		    cellStyle.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cell.setCellStyle(cellStyle);
		    
		    cell = row.createCell((short) 3);
		    cell.setCellValue("Detail");
		    cellStyle = wb.createCellStyle();
		    cellStyle.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    cell.setCellStyle(cellStyle);
		    
		    int rowid=4;
		    List<LigneCaisse> lignecaisse= managerlignecaisse.getObjects(caisse.getId());
		    for(int i=0;i<lignecaisse.size();i++){
		    	 row = sheet.createRow(rowid);
				    cell = row.createCell((short) 0);
				    cell.setCellValue(ftletter.format(lignecaisse.get(i).getDateligne()));
				    cellStyle = wb.createCellStyle();
				    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				    cell.setCellStyle(cellStyle); 
				    
				    cell = row.createCell((short) 1);
				    cell.setCellValue(lignecaisse.get(i).getEntree());
				    cellStyle = wb.createCellStyle();
				    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				    cell.setCellStyle(cellStyle); 
				    
				    cell = row.createCell((short) 2);
				    cell.setCellValue(lignecaisse.get(i).getSortie());
				    cellStyle = wb.createCellStyle();
				    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				    cell.setCellStyle(cellStyle);
				    
				    cell = row.createCell((short) 3);
				    cell.setCellValue(lignecaisse.get(i).getDetails());
				    cellStyle = wb.createCellStyle();
				    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				    cell.setCellStyle(cellStyle);
				    
				    rowid++;
		    }
		    rowid++;
	    	 row = sheet.createRow(rowid);
	    	 sheet.addMergedRegion(new Region(0,(short)0,0,(short)4));
			    cell = row.createCell((short) 0);
			    cell.setCellValue("Total Entrer");
			    cellStyle = wb.createCellStyle();
			    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			    cell.setCellStyle(cellStyle); 
			    
			    cell = row.createCell((short) 1);
			    cell.setCellValue(caisse.getEntreetotal());
			    cellStyle = wb.createCellStyle();
			    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			    cell.setCellStyle(cellStyle); 
			    rowid++;
		    	 row = sheet.createRow(rowid);
		    	 sheet.addMergedRegion(new Region(0,(short)0,0,(short)4));
				    cell = row.createCell((short) 0);
				    cell.setCellValue("Total Sortie");
				    cellStyle = wb.createCellStyle();
				    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				    cell.setCellStyle(cellStyle); 
				    
				    cell = row.createCell((short) 1);
				    cell.setCellValue(caisse.getSortietotal());
				    cellStyle = wb.createCellStyle();
				    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				    cell.setCellStyle(cellStyle); 
				    rowid++;
			    	 row = sheet.createRow(rowid);
			    	 sheet.addMergedRegion(new Region(0,(short)0,0,(short)4));
					    cell = row.createCell((short) 0);
					    cell.setCellValue("Le Reste");
					    cellStyle = wb.createCellStyle();
					    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					    cell.setCellStyle(cellStyle); 
					    
					    cell = row.createCell((short) 1);
					    cell.setCellValue(caisse.getRestetotal());
					    cellStyle = wb.createCellStyle();
					    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					    cell.setCellStyle(cellStyle); 
			    
		    wb.write(out);
		      out.flush();
		      out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Delete(){
		
		FacesMessage msg;
		try {
			lignesparcaisse=null;
			System.out.println("ID ligneparcaisse=>"+ligneparcaisse.getId());
			managerlignecaisse.deleteObject(ligneparcaisse);
		    msg = new FacesMessage("la decaissement est supprimer avec success");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			   msg = new FacesMessage("Exception: ",e.getLocalizedMessage());
			    FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}
	
	public void onCellEdit(){
		
		FacesMessage msg;
		try {
			if(tempodatedebut!=null)
				caisse.setDatedebutcaisse(tempodatedebut);
			if(tempodatefin!=null)
				caisse.setDatefincaisse(tempodatefin);
			
			managerjdbc.updateCaisse(caisse);
			sommeentrer=managerjdbc.getSommeCaisseEntrer();
			sommesortie=managerjdbc.getSommeCaisseSortie();
		    msg = new FacesMessage("la caisse est Modifier avec success");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			 msg = new FacesMessage("Exception:",e.getLocalizedMessage());
			    FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void insertcaisse(){
		
		FacesMessage msg;
		FacesMessage msgcond;
		int exceptionNum=0;
		try {
			LigneCaisse ligne=new LigneCaisse();
			caisse=new Caisse();
			listecaisse=null;
			
			caisse.setDatedebutcaisse(datedebutcaisse);
			caisse.setDatefincaisse(datefincaisse);
			int moistempo=caisse.getDatefincaisse().getMonth()+1;
			caisse.setMoiscaisse(""+moistempo);
			SimpleDateFormat ft= new SimpleDateFormat("yyyy");
			caisse.setObjyear(managerjdbc.getYears(Integer.parseInt(ft.format(caisse.getDatefincaisse()))));
			exceptionNum=1;
			GregorianCalendar cal=new GregorianCalendar();
			cal.setTime(caisse.getDatedebutcaisse());
			int month=cal.get(Calendar.MONTH);
			exceptionNum=2;
			//Premiere décaissement Par defaut
			ligne.setDateligne(caisse.getDatedebutcaisse());
			ligne.setDetails("Reste de Mois Presedante");
			ligne.setReglement("");
			ligne.setDebutjour(true);
			System.out.println("Mois "+month+"/"+cal.get(Calendar.YEAR));
			int idcaisse=managerjdbc.getIDLastCaisse();
			System.out.println("ID CAISSE "+idcaisse);
			exceptionNum=3;
			if(idcaisse!=0){
				
				ligne.setCaissePresedant(manager.getObject(idcaisse));
				caisse.setEntreetotal(ligne.getCaissePresedant().getRestetotal());
				caisse.setRestetotal(ligne.getCaissePresedant().getRestetotal());
				ligne.setEntree(ligne.getCaissePresedant().getRestetotal());
				ligne.setSortie(0);
			}else{
				msgcond = new FacesMessage("attend la fin de mois pour creer le total de mois presedant");
				caisse.setEntreetotal(0);
				caisse.setRestetotal(0);
				ligne.setEntree(0);
				ligne.setSortie(0);
				FacesContext.getCurrentInstance().addMessage(null, msgcond);
			}
			exceptionNum=4;
			caisse.setSortietotal(0);
			manager.insertObject(caisse);
			exceptionNum=5;
			

			ligne.setCaisse(caisse);
			managerlignecaisse.insertObject(ligne);
			exceptionNum=6;
			System.out.println("PREPARE UPDATE");
			managerjdbc.updatePrixCaisse(caisse);
			System.out.println(" UPDATE SUCCFUL ..");
			exceptionNum=7;
			datedebutcaisse=null;
			datefincaisse=null;
			caisse=null;
			listecaisse=null;
			exceptionNum=8;
			sommeentrer=managerjdbc.getSommeCaisseEntrer();
			sommesortie=managerjdbc.getSommeCaisseSortie();
		    msg = new FacesMessage("la caisse est creer avec success");
		    
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		    msg = new FacesMessage("Exception  Num : "+exceptionNum,e.getLocalizedMessage());
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		}


	}
	
	public void insertdecaisse(){
		
		FacesMessage msg;
		try {
			lignesparcaisse=null;
			lignetempo=new LigneCaisse();
			//lignetempo.setCaisse(caisse);
			lignetempo.setDateligne(jour);
			lignetempo.setDetails(detail);
			lignetempo.setReglement(reg);
			//recuperer le jour de la date 
			GregorianCalendar cal=new GregorianCalendar();
			cal.setTime(jour);
			int j=cal.get(Calendar.DAY_OF_MONTH);
			if(j==1){
				lignetempo.setDebutjour(true);
				lignetempo.setEntree(entree);					
				lignetempo.setSortie(sortie);
			}	
			else{
				lignetempo.setDebutjour(false);
				lignetempo.setEntree(entree);
				lignetempo.setSortie(sortie);
			}
				
			if(idemp!=0)
				lignetempo.setEmp(manageremp.getObject(idemp));

			managerlignecaisse.insertObject(lignetempo);
		    caisse.setEntreetotal(caisse.getEntreetotal()+entree);
		    caisse.setSortietotal(caisse.getSortietotal()+sortie);
		    caisse.setRestetotal(caisse.getEntreetotal()-caisse.getSortietotal());
		    jour=null;
		    entree=0;
		    sortie=0;
		    detail="";
			reg="";
		    lignetempo=new LigneCaisse();
			sommeentrer=managerjdbc.getSommeCaisseEntrer();
			sommesortie=managerjdbc.getSommeCaisseSortie();
			reste=sommeentrer-sommesortie;
		    msg = new FacesMessage("la decaissement créer avec success");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
		    msg = new FacesMessage("Exception: ",e.getLocalizedMessage());
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void onCellEditligne(CellEditEvent event){
		
		FacesMessage msg;
		try {
			System.out.println("Update les Lignes Caisse...");
			if(tempodate!=null)
				ligneparcaisse.setDateligne(tempodate);
			if(tempoentree!="")
				ligneparcaisse.setEntree(Double.parseDouble(tempoentree));
			if(temposortie!="")
				ligneparcaisse.setSortie(Double.parseDouble(temposortie));
			if(tempodetail!=""){
				ligneparcaisse.setDetails(tempodetail);
			}
			if(temporeg!=""){
				ligneparcaisse.setReglement(temporeg);
			}
			if(idempmodfi>0){
				ligneparcaisse.setEmp(manageremp.getObject(idempmodfi));
			}
			managerjdbc.updateLigneCaisse(ligneparcaisse);
			System.out.println("Update les Lignes Caisse success");
			tempodate=null;
			tempoentree="";
			temposortie="";
			tempodetail="";
			idempmodfi=-1;
			reg="";
			sommeentrer=managerjdbc.getSommeCaisseEntrer();
			sommesortie=managerjdbc.getSommeCaisseSortie();
			reste=sommeentrer-sommesortie;
		    msg = new FacesMessage("la ligne Modifier avec success");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
		    msg = new FacesMessage("Exception: ",e.getLocalizedMessage());
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}
	
	public Caisse getCaisse() {
		return caisse;
	}

	public void setCaisse(Caisse caisse) {
		this.caisse = caisse;
	}

	public List<Caisse> getListecaisse() {
		
		if(listecaisse==null)
			listecaisse=manager.getObject();
		
		return listecaisse;
	}

	public void setListecaisse(List<Caisse> listecaisse) {
		this.listecaisse = listecaisse;
	}

	public LigneCaisse getLigneparcaisse() {
		return ligneparcaisse;
	}

	public void setLigneparcaisse(LigneCaisse ligneparcaisse) {
		this.ligneparcaisse = ligneparcaisse;
	}

	public List<LigneCaisse> getLignesparcaisse() {
		if(lignesparcaisse==null)
			lignesparcaisse=managerlignecaisse.getObject();
		return lignesparcaisse;
	}

	public void setLignesparcaisse(List<LigneCaisse> lignesparcaisse) {
		this.lignesparcaisse = lignesparcaisse;
	}

	public List<Caisse> getFiltredlistecaisse() {
		return filtredlistecaisse;
	}

	public void setFiltredlistecaisse(List<Caisse> filtredlistecaisse) {
		this.filtredlistecaisse = filtredlistecaisse;
	}

	public Date getJour() {
		return jour;
	}

	public void setJour(Date jour) {
		this.jour = jour;
	}

	public double getEntree() {
		return entree;
	}

	public void setEntree(double entree) {
		this.entree = entree;
	}

	public double getSortie() {
		return sortie;
	}

	public void setSortie(double sortie) {
		this.sortie = sortie;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public List<Employee> getListemp() {
		return listemp;
	}

	public void setListemp(List<Employee> listemp) {
		this.listemp = listemp;
	}
	
	public Date getTempodate() {
		return tempodate;
	}

	public void setTempodate(Date tempodate) {
		this.tempodate = tempodate;
	}

	public String getTempodetail() {
		return tempodetail;
	}

	public void setTempodetail(String tempodetail) {
		this.tempodetail = tempodetail;
	}

	public ModelService<Caisse> getManager() {
		return manager;
	}

	public void setManager(ModelService<Caisse> manager) {
		this.manager = manager;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public LigneCaisse getLignetempo() {
		return lignetempo;
	}

	public void setLignetempo(LigneCaisse lignetempo) {
		this.lignetempo = lignetempo;
	}

	public ModelService<LigneCaisse> getManagerlignecaisse() {
		return managerlignecaisse;
	}

	public void setManagerlignecaisse(ModelService<LigneCaisse> managerlignecaisse) {
		this.managerlignecaisse = managerlignecaisse;
	}

	public int getIdemp() {
		return idemp;
	}

	public void setIdemp(int idemp) {
		this.idemp = idemp;
	}

	public ModelService<Employee> getManageremp() {
		return manageremp;
	}

	public void setManageremp(ModelService<Employee> manageremp) {
		this.manageremp = manageremp;
	}

	public int getIdempmodfi() {
		return idempmodfi;
	}

	public void setIdempmodfi(int idempmodfi) {
		this.idempmodfi = idempmodfi;
	}

	public List<LigneCaisse> getFiltredlignesparcaisse() {
		return filtredlignesparcaisse;
	}

	public void setFiltredlignesparcaisse(List<LigneCaisse> filtredlignesparcaisse) {
		this.filtredlignesparcaisse = filtredlignesparcaisse;
	}

	public String getTempoentree() {
		return tempoentree;
	}

	public void setTempoentree(String tempoentree) {
		this.tempoentree = tempoentree;
	}

	public String getTemposortie() {
		return temposortie;
	}

	public void setTemposortie(String temposortie) {
		this.temposortie = temposortie;
	}

	public Date getTempodatefin() {
		return tempodatefin;
	}

	public void setTempodatefin(Date tempodatefin) {
		this.tempodatefin = tempodatefin;
	}

	public Date getTempodatedebut() {
		return tempodatedebut;
	}

	public void setTempodatedebut(Date tempodatedebut) {
		this.tempodatedebut = tempodatedebut;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public String getTemporeg() {
		return temporeg;
	}

	public void setTemporeg(String temporeg) {
		this.temporeg = temporeg;
	}

	public Date getDatedebutcaisse() {
		return datedebutcaisse;
	}

	public void setDatedebutcaisse(Date datedebutcaisse) {
		this.datedebutcaisse = datedebutcaisse;
	}

	public Date getDatefincaisse() {
		return datefincaisse;
	}

	public void setDatefincaisse(Date datefincaisse) {
		this.datefincaisse = datefincaisse;
	}

	public SimpleDateFormat getFt() {
		return ft;
	}

	public void setFt(SimpleDateFormat ft) {
		this.ft = ft;
	}

	public SimpleDateFormat getFtletter() {
		return ftletter;
	}

	public void setFtletter(SimpleDateFormat ftletter) {
		this.ftletter = ftletter;
	}

	public boolean isCheckdjr() {
		return checkdjr;
	}

	public void setCheckdjr(boolean checkdjr) {
		this.checkdjr = checkdjr;
	}

	public ModelService<years> getManageryears() {
		return manageryears;
	}

	public void setManageryears(ModelService<years> manageryears) {
		this.manageryears = manageryears;
	}

	public String[] getTabmois() {
		return tabmois;
	}

	public void setTabmois(String[] tabmois) {
		this.tabmois = tabmois;
	}

	public int getIdannee() {
		return idannee;
	}

	public void setIdannee(int idannee) {
		this.idannee = idannee;
	}

	public String getChoixmois() {
		return choixmois;
	}

	public void setChoixmois(String choixmois) {
		this.choixmois = choixmois;
	}

	public double getSommeentrer() {
		return sommeentrer;
	}

	public void setSommeentrer(double sommeentrer) {
		this.sommeentrer = sommeentrer;
	}

	public double getSommesortie() {
		return sommesortie;
	}

	public void setSommesortie(double sommesortie) {
		this.sommesortie = sommesortie;
	}

	public double getReste() {
		return reste;
	}

	public void setReste(double reste) {
		this.reste = reste;
	}
	
	

}
