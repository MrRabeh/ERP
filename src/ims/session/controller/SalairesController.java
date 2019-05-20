package ims.session.controller;

import ims.model.entities.CoordonneeBancaire;
import ims.model.entities.Employee;
import ims.model.entities.OrdreVirement;
import ims.model.entities.Salaires;
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
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

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
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;


@ManagedBean
@SessionScoped
public class SalairesController implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{OrdreVirementManager}")
    private ModelService<OrdreVirement> ordremanager;
	
	@ManagedProperty(value="#{SalairesManager}")
    private ModelService<Salaires> manager;
	
	@ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
	
	@ManagedProperty(value="#{EmployeeManager}")
	private ModelService<Employee> manageremp;
	
	@ManagedProperty(value="#{CoordonneeBanqueManager}")
	private ModelService<CoordonneeBancaire> banquemanager;
	
	private OrdreVirement ordre=new OrdreVirement();
	private OrdreVirement ordresupp=new OrdreVirement();
	private List<OrdreVirement> ordres;
	
	private Salaires salaire=new Salaires();
	private List<Salaires> listsalaires;
	private boolean checksalaire;
	private double montantempo;
	private List<Employee> emps=new ArrayList<Employee>();
	
	public String preparecreate(){
		ordre=new OrdreVirement();
		emps=manageremp.getObject();
		double total=0;
		for (int i = 0; i < emps.size(); i++) {
			total+=emps.get(i).getSalaire();
		}
		Calendar cal = Calendar.getInstance();
		ordre.setDatevirement(cal.getTime());
		ordre.setMontantGlobal(total);
		return "createsalaires?faces-redirect=true";
	}
	
	public String prepareView(){
		checksalaire=true;
		montantempo=ordre.getMontantGlobal();
		return "viewsalaires?faces-redirect=true";
	}
	
	public void eventchecksalaire(){
		try {
			if(checksalaire==false){
				emps=manageremp.getObject();
				double total=0;
				for (int i = 0; i < emps.size(); i++) {
					total+=emps.get(i).getSalaire();
				}
				montantempo=total;
			}else
			montantempo=ordre.getMontantGlobal();
			System.out.println("eventchecksalaire");
		} catch (Exception e) {
			System.out.println("Erreur");
		}

		
	}
	
	public void onCellEdit(){
		FacesMessage msg;
		try {
		managerjdbc.updatesalaires(salaire);
		msg = new FacesMessage("le salaire est bien constater");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
	          msg = new FacesMessage("Exception");
	           FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void insert(){
		FacesMessage msg;
		try {
			   SimpleDateFormat dateFormat;
			    dateFormat = new SimpleDateFormat("YYYY");
			    
			    ordre.setYears(managerjdbc.getYears(Integer.parseInt(dateFormat.format(ordre.getDatevirement()))));
			    ordre.setBanque(banquemanager.getObject(1));
			    boolean op=ordremanager.insertObject(ordre);
			    
			    if(op==true){
			    	for (int i = 0; i < emps.size(); i++) {
						salaire.setEmp(emps.get(i));
						salaire.setYears(managerjdbc.getYears(Integer.parseInt(dateFormat.format(ordre.getDatevirement()))));
						salaire.setDatesalaire(ordre.getDatevirement());
						salaire.setMoisordre(ordre.getMoisordre());
						salaire.setMontant(emps.get(i).getSalaire());
						salaire.setOrdre(ordre);
						manager.insertObject(salaire);
					}
			    }

				msg = new FacesMessage("salaire est creer avec success");
				FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
	          msg = new FacesMessage("Exception");
	           FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void update(){
		FacesMessage msg;
		try {
			    SimpleDateFormat dateFormat;
			    dateFormat = new SimpleDateFormat("YYYY");
			    if(checksalaire==true){
			    	managerjdbc.updateOrdre(ordre);
			    }else{
			    	managerjdbc.deleteAllSalaire(ordre);
			    	ordre.setMontantGlobal(montantempo);
			    	int op=managerjdbc.updateOrdre(ordre);
			    	   if(op>0){
					    	for (int i = 0; i < emps.size(); i++) {
								salaire.setEmp(emps.get(i));
								salaire.setYears(managerjdbc.getYears(Integer.parseInt(dateFormat.format(ordre.getDatevirement()))));
								salaire.setDatesalaire(ordre.getDatevirement());
								salaire.setMoisordre(ordre.getMoisordre());
								salaire.setMontant(emps.get(i).getSalaire());
								salaire.setOrdre(ordre);
								manager.insertObject(salaire);
							}
					    }
			    }
			    
				msg = new FacesMessage("Order modifier avec success");
				FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
	          msg = new FacesMessage("Exception"+e.getMessage());
	           FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void deleteordre(){
		FacesMessage msg;
		try {
			managerjdbc.deleteOrdre(ordresupp);
			msg = new FacesMessage("Order est supprimer avec success");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			msg = new FacesMessage("Exception");
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
		
		   SimpleDateFormat ftdateyears= new SimpleDateFormat("yyyy");
		   SimpleDateFormat ftdatecomplete= new SimpleDateFormat("dd MMMM yyyy");
		   System.out.println("Imprimer......Ordre Virement");
		   DateFormat format=new SimpleDateFormat("dd/MM/yy");
		   System.out.println("Imprimer*************** SUCCES");
		   String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
	   	   Paragraph saute=new Paragraph("\n");
	   	   Paragraph pargraphe;
	       Document document = new Document(PageSize.A4);
	       HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	       try {
	    	   Font ftext=new Font(Font.FontFamily.HELVETICA,11,Font.NORMAL,new BaseColor(Color.black));
	    	   Font ftextblod=new Font(Font.FontFamily.HELVETICA,11,Font.BOLD,new BaseColor(Color.black));
	    	   Font ftextblodgra=new Font(Font.FontFamily.HELVETICA,13,Font.BOLD,new BaseColor(Color.black));
	           Font ftextgraUNDERLINE=new Font(Font.FontFamily.HELVETICA,13,Font.UNDERLINE,new BaseColor(Color.black));
	           Font ftextgraTitreUNDERLINE=new Font(Font.FontFamily.HELVETICA,13,Font.UNDERLINE,new BaseColor(Color.black));
	           Font ftextUNDERLINE=new Font(Font.FontFamily.HELVETICA,13,Font.UNDERLINE,new BaseColor(Color.black));
	    	   
	           PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
	    	   System.out.println("Ordre ID===>"+ordre.getId());
	           response.setContentType("application/pdf");
	           response.setHeader("Content-Disposition", "inline; filename=Ordre_"+ordre.getMoisordre()+".pdf");
	           document.open();
	           document.setMargins(36, 36, 108, 180);
	           document.add(saute);
	           
	           pargraphe=new Paragraph("Ordre de virement",ftextgraTitreUNDERLINE);
	           pargraphe.setAlignment(Element.ALIGN_CENTER);
	           pargraphe.setIndentationLeft(53);
	           document.add(pargraphe);
	           
	           pargraphe=new Paragraph(ordre.getMoisordre()+" "+ftdateyears.format(ordre.getDatevirement()),ftext);
	           pargraphe.setAlignment(Element.ALIGN_CENTER);
	           pargraphe.setIndentationLeft(53);
	           document.add(pargraphe);
	           
	           document.add(saute);
	           
	           pargraphe=new Paragraph();
	           pargraphe.add(new Phrase("Banque : ",ftextblod));
	           pargraphe.add(new Phrase(ordre.getBanque().getBanque()+"\n",ftext));
	           pargraphe.add(new Phrase("Agence : ",ftextblod));
	           pargraphe.add(new Phrase(ordre.getBanque().getAgence()+"\n",ftext));
	           pargraphe.add(new Phrase("\n"));
	           pargraphe.add(new Phrase("Messieurs,\n"));
	           pargraphe.add(new Phrase("Veuillez virer par le débit de mon compte numéro :\n"));
	           pargraphe.add(new Phrase("                       "+ordre.getBanque().getNumerobancaire(),ftextblodgra));
	           pargraphe.setAlignment(Element.ALIGN_LEFT);
	           pargraphe.setIndentationLeft(53);
	           document.add(pargraphe);
	           
	           pargraphe=new Paragraph("Donneur d’ordre :",ftextUNDERLINE);
	           pargraphe.setAlignment(Element.ALIGN_LEFT);
	           pargraphe.setIndentationLeft(53);
	           document.add(pargraphe);
	           document.add(saute);
	           DecimalFormat df = new DecimalFormat();
	           df.setDecimalSeparatorAlwaysShown(true);
	           df.setMaximumFractionDigits(2);
	           df.setMinimumFractionDigits(2);
	           
	           PdfPTable tableinfo = new PdfPTable(2);
	           tableinfo.setWidths(new float[]{100,300});
	           tableinfo.setWidthPercentage(100);
	           PdfPCell l = new PdfPCell(new Phrase("Raison Social",ftextblod));
	           l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           l.setHorizontalAlignment(Element.ALIGN_LEFT);
	           tableinfo.addCell(l);
	           
	           l = new PdfPCell(new Phrase("IMS TECHNOLOGY S.A.R.L.A.U",ftextblod));
	           tableinfo.addCell(l);
	          
	           l = new PdfPCell(new Phrase("Adresse",ftextblod));
	           l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           l.setHorizontalAlignment(Element.ALIGN_LEFT);
	           tableinfo.addCell(l);
	           
	           l = new PdfPCell(new Phrase("96, Rue Pierre Parent Bureau 28,3eme étage,20 000-Casablanca",ftext));
	           tableinfo.addCell(l);
	           
	           tableinfo.setHorizontalAlignment(tableinfo.ALIGN_CENTER);
	           
	           pargraphe=new Paragraph();
	           pargraphe.setAlignment(Element.ALIGN_LEFT);
	           pargraphe.setIndentationLeft(53);
	           
	           pargraphe.add(tableinfo);
	           
	           document.add(pargraphe);
	           document.add(saute);
	           
	           
	           pargraphe=new Paragraph("Bénéficiaires:",ftextUNDERLINE);
	           pargraphe.setAlignment(Element.ALIGN_LEFT);
	           pargraphe.setIndentationLeft(53);
	           document.add(pargraphe);
	           
	           document.add(saute);
	          
	           PdfPTable tableoffre = new PdfPTable(3);
	           tableoffre.setWidths(new float[]{180,250,100});
	           tableoffre.setWidthPercentage(100);
	           tableoffre.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           PdfPCell c1 = new PdfPCell(new Phrase("Nom et Prénom",ftextblod));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           tableoffre.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("RIB",ftextblod));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           tableoffre.addCell(c1);
	           
	           c1 = new PdfPCell(new Phrase("La Somme",ftextblod));
	           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           tableoffre.addCell(c1);
	           
	           List<Salaires> salaires=manager.getObjects(ordre.getId());
	           if(salaires!=null){
	        	   for (int i = 0; i < salaires.size(); i++) {
	        		   if(salaires.get(i).getMontant()>0){
	        			   
	        		  
	        		  
	        		   c1 = new PdfPCell(new Phrase("",ftext));
	    	           c1.setBackgroundColor(new BaseColor(Color.decode("#D9D9D9")));
	    	           c1.setPadding(5);
	    	           c1.setColspan(3);
	    	           tableoffre.addCell(c1);
	    	           
	        		   c1 = new PdfPCell(new Phrase(salaires.get(i).getEmp().getPrenom()+" "+salaires.get(i).getEmp().getNom(),ftextblod));
	    	           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    	           tableoffre.addCell(c1);
	    	           
	    	           c1 = new PdfPCell(new Phrase(salaires.get(i).getEmp().getComptebancaire(),ftextblod));
	    	           c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	    	           tableoffre.addCell(c1);
	    	           
	    	           c1 = new PdfPCell(new Phrase(df.format(salaires.get(i).getMontant())+" DHS",ftextblod));
	    	           c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    	           tableoffre.addCell(c1);
	    	           
	    	           NumberFormat formatter = new RuleBasedNumberFormat(Locale.FRANCE, RuleBasedNumberFormat.SPELLOUT);
	    	           String resultat="";
	    	           String chaineMontant=""+salaires.get(i).getMontant();
	    	           String [] var=chaineMontant.replace(".", ",").split(",");
	    	           System.out.println("converion var 0"+var[0]);
		        	   System.out.println("converion var 1"+var[1]);
		        	   double nombre=Double.parseDouble(var[0]);
		        	   double virgule=Double.parseDouble(var[1]);
		        	   if(var[1].length()>1){
		        		   System.out.println("NOMBRE avec virgule");
		        			   String chainenombre=formatter.format(nombre).replace("onze cents","mille cents");
		        		 resultat=chainenombre+" Dirhams et "+formatter.format(virgule)+ " centimes";
		        		
		        	   }
		        	   else{
		        		   System.out.println("NOMBRE fois 10");
		        		   virgule=virgule*10;
		        		   System.out.println(formatter.format(nombre));
		        		   String chainenombre=formatter.format(nombre).replace("onze cents","mille cents");
		        		 
		        		   resultat=chainenombre+" Dirhams et "+formatter.format(virgule)+ " centimes";
		        	   }
	    	           c1 = new PdfPCell(new Phrase(resultat,ftext));
	    	           c1.setColspan(3);
	    	           tableoffre.addCell(c1);
	        		   }
	    	           
				}
	           }
	           pargraphe=new Paragraph();
	           pargraphe.setAlignment(Element.ALIGN_LEFT);
	           pargraphe.setIndentationLeft(53);
	           pargraphe.add(tableoffre);
	           document.add(pargraphe);
	           
	           pargraphe=new Paragraph("Casablanca ,le "+ftdatecomplete.format(ordre.getDatevirement()),ftext);
	           pargraphe.setAlignment(Element.ALIGN_CENTER);
	           pargraphe.setIndentationLeft(53);
	           document.add(pargraphe);
	           
	           document.add(saute);
	           System.out.println("Imprimer***************FIN SUCCES");
	           document.addAuthor("IMS Technology");
	           document.addTitle("Ordre Virement");
	           document.addSubject("Ordre");
	           document.addCreator("RABEH Ing IMS Technology");
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

    public ModelService<Salaires> getManager() {
		return manager;
	}

	public void setManager(ModelService<Salaires> manager) {
		this.manager = manager;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public Salaires getSalaire() {
		return salaire;
	}

	public void setSalaire(Salaires salaire) {
		this.salaire = salaire;
	}

	public List<Salaires> getListsalaires() {
			listsalaires=manager.getObject();
		return listsalaires;
	}

	public void setListsalaires(List<Salaires> listsalaires) {
		this.listsalaires = listsalaires;
	}

	public ModelService<Employee> getManageremp() {
		return manageremp;
	}

	public void setManageremp(ModelService<Employee> manageremp) {
		this.manageremp = manageremp;
	}

	public OrdreVirement getOrdre() {
		return ordre;
	}

	public void setOrdre(OrdreVirement ordre) {
		this.ordre = ordre;
	}

	public List<OrdreVirement> getOrdres() {
		ordres=ordremanager.getObject();
		return ordres;
	}

	public void setOrdres(List<OrdreVirement> ordres) {
		this.ordres = ordres;
	}

	public ModelService<OrdreVirement> getOrdremanager() {
		return ordremanager;
	}

	public void setOrdremanager(ModelService<OrdreVirement> ordremanager) {
		this.ordremanager = ordremanager;
	}

	public ModelService<CoordonneeBancaire> getBanquemanager() {
		return banquemanager;
	}

	public void setBanquemanager(ModelService<CoordonneeBancaire> banquemanager) {
		this.banquemanager = banquemanager;
	}

	public List<Employee> getEmps() {
		return emps;
	}

	public void setEmps(List<Employee> emps) {
		this.emps = emps;
	}

	public boolean isChecksalaire() {
		return checksalaire;
	}

	public void setChecksalaire(boolean checksalaire) {
		this.checksalaire = checksalaire;
	}

	public double getMontantempo() {
		return montantempo;
	}

	public void setMontantempo(double montantempo) {
		this.montantempo = montantempo;
	}

	public OrdreVirement getOrdresupp() {
		return ordresupp;
	}

	public void setOrdresupp(OrdreVirement ordresupp) {
		this.ordresupp = ordresupp;
	}
	

}
