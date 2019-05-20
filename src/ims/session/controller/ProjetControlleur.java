/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ims.model.entities.Client;
import ims.model.entities.Employee;
import ims.model.entities.Projet;
import ims.model.entities.Timesheet;
 
import ims.model.entities.Tache;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.awt.Color;
 
import java.io.Serializable;
 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@SessionScoped
public class ProjetControlleur implements Serializable{
 
	
	 /**
	* creator RABEH TARIK
    **/
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{projetManager}")
    private ModelService<Projet> manager;
	
	@ManagedProperty(value="#{tacheManager}")
	private ModelService<Tache> managerT;
	  
	@ManagedProperty(value="#{ClientManager}")
	private ModelService<Client> managerclient;
	
    @ManagedProperty(value="#{timesheetManager}")
    private ModelService<Timesheet> managert;
    
    @ManagedProperty(value="#{EmployeeManager}")
    private ModelService<Employee> managerEmp;
    
    @ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
    
    
    private Projet projet;
	private List<Projet> projets;
    private List<Projet> filteredprojets=new ArrayList<Projet>(); 
	private Timesheet timesheet=new Timesheet();
	private List<Timesheet> timesheets=new ArrayList<Timesheet>();
	
	
	private int idclient=0;
	private int idemp=0;
    private float total=0;
    private String projectname;
    private Date datedebut;
    private Date datefin;
    public static final String IMAGE =((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images")+"\\fond2.png";
	public static final String DEST = "C:/IMS.pdf";
    
	
	
	
	 public ProjetControlleur() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	 public void oncellEdit(){
	    	FacesMessage msg;
	    	projets=null;
	    	try {
	    		if(idclient!=0)
	    			projet.setClient(managerclient.getObject(idclient));
	    		if(idemp!=0)
	    			projet.setEmp(managerEmp.getObject(idemp));
				
	    		managerjdbc.updateProjet(projet);
	    		 msg = new FacesMessage("Projet Modifier avec success");
		           FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (Exception e) {
				 msg = new FacesMessage("Probleme de connexion","Contacter l'administration "+e.getLocalizedMessage());
			       FacesContext.getCurrentInstance().addMessage(null, msg);
			}
	 }

	public void insert(){

	    	FacesMessage msg;
	    	projets=null;
	    	try{
	    		projet=new Projet();
	    		projet.setProjectname(projectname);
	    		projet.setClient(managerclient.getObject(idclient));
	    		projet.setEmp(managerEmp.getObject(idemp));
	    		projet.setDatedebut(datedebut);
	    		projet.setDateFin(datefin);
	    		System.out.println("prepare insert");
	    		manager.insertObject(projet);
	    		
	           msg = new FacesMessage("Projet creer avec success");
	           FacesContext.getCurrentInstance().addMessage(null, msg);
	        } catch (Exception e) {
	               msg = new FacesMessage("Probleme de connexion","Contacter l'administration "+e.getLocalizedMessage());
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
	    	projectname="";
	    	projet=new Projet();
	    }
	 
	    public void Delete(){
	        FacesMessage msg;
	        try {
	        manager.deleteObject(projet);
	          getProjets();
	       msg = new FacesMessage("Projet est bien Supprimer");
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
	     
	     public void onRowEdit(RowEditEvent event) {
	         FacesMessage msg;
	         try {
	        	 
	        	 ((Projet) event.getObject()).setProjectname(projectname);
	        	 ((Projet) event.getObject()).setClient(managerclient.getObject(idclient));
	        	 ((Projet) event.getObject()).setEmp(managerEmp.getObject(idemp));
	        	 System.out.println( ((Projet) event.getObject()).getProjectname());
	            
	        	boolean op=manager.updateObject(((Projet) event.getObject()));
	            if(op==true)
	        	msg = new FacesMessage("Projet est bien Modifié",""+((Projet) event.getObject()).getIdprojet());
	            else
	            	msg = new FacesMessage("Probleme de modification",""+((Projet) event.getObject()).getIdprojet());
	               
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	         } catch (Exception e) {
	            msg = new FacesMessage("Probleme de Modification","Contacter l'administration");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	         }
	        
	    }
	     
	    public void onRowCancel(RowEditEvent event) {

	       FacesMessage msg = new FacesMessage("Modification été Annulé",""+((Projet) event.getObject()).getIdprojet());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }


		 public void createPdf(String dest) throws IOException, DocumentException {
			 
			 Paragraph saute=new Paragraph("\n");
			  /*************************************/ 
				 Document document = new Document(PageSize.A4);
				 String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
		       
		        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
		        document.open();
		        
		        System.out.println("Imprimer***************1 SUCCES");
	           
	            
	            document.add(saute);
	            document.add(saute);
	            document.add(saute);
	            Paragraph monsieur=new Paragraph("Projet : "+ projet.getProjectname(),new Font(Font.FontFamily.HELVETICA,15,Font.BOLD,new BaseColor(Color.blue)));
	            monsieur.setIndentationLeft(70);
	            document.add(monsieur);
	            document.add(saute);
	             
	            System.out.println("Imprimer***************2 SUCCES");
	            
	            
	           projet=manager.getObject(projet.getIdprojet());
	           document.setMargins(36, 36, 108, 180);
		       
		       document.add(saute); 
		       /*
		       Image buttprojet=Image.getInstance(new URL(webroot+"\\plan.png"));
	           
	            buttprojet.setAbsolutePosition(100, 420);
	            document.add(buttprojet);
	             */
	            document.add(saute);
	            document.add(saute);
	            document.add(saute);
	             
		      //************************table *************************
	            
	          
	        
	            Font fontable=new Font(Font.FontFamily.TIMES_ROMAN,9,Font.NORMAL,new BaseColor(Color.black));
	              
	            PdfPTable table = new PdfPTable(4);
	          

		           table.setWidths(new float[]{180,50,50,50});
	            
	           table.setHorizontalAlignment(Element.ALIGN_CENTER);
	            
	         
	           table.setHeaderRows(1);
	           table.setWidthPercentage(80);
	           
	            PdfPCell c1 = new PdfPCell(new Phrase("Nom de la tache",fontable));
	            
	           
	            c1.setBorderColor(new BaseColor(Color.black));
	            c1.setBorderWidth(2);
	            
	            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	            table.addCell(c1);
	            
	            c1 = new PdfPCell(new Phrase("Durée",fontable));
	            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	            c1.setBorderColor(new BaseColor(Color.black));
	            c1.setBorderWidth(2);
	            c1.setPadding(4);
	            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	            table.addCell(c1);
	            
	            c1 = new PdfPCell(new Phrase("Début",fontable));
	            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	            c1.setBorderColor(new BaseColor(Color.black));
	            c1.setBorderWidth(2);
	            c1.setPadding(4);
	            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	            table.addCell(c1);

	            c1 = new PdfPCell(new Phrase("Fin",fontable));
	            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	            c1.setBorderColor(new BaseColor(Color.black));
	            c1.setBorderWidth(2);
	            c1.setPadding(4);
	            c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	            table.addCell(c1);
	          
	              System.out.println("------------------Taches----------------------------\n");
	              List<Tache> taches=new ArrayList<Tache>();
	              taches.addAll(projet.getTaches());
	              SimpleDateFormat formatdate= new SimpleDateFormat("dd/MM/yyyy");
	            for(int j=0;j<taches.size();j++){

	            		 
			             PdfPCell name = new PdfPCell(new Phrase(taches.get(j).getTachename(),fontable));
			             name.setColspan(1);
			             name.setBorderWidth(2);
			             name.setPadding(4);
				         table.addCell(name);
			             
				         PdfPCell duree;
				         if(taches.get(j).getDuree()!=null)
				        	 duree = new PdfPCell(new Phrase(taches.get(j).getDuree()+" jours",fontable));
				         else
				        	 duree = new PdfPCell(new Phrase("0 jours",fontable));
			             duree.setColspan(1);
			             duree.setBorderWidth(2);
			             duree.setPadding(4);
				         table.addCell(duree);
			             
				          
				      
			             PdfPCell debut = new PdfPCell(new Phrase(formatdate.format(taches.get(j).getDatedebut()),fontable));
			             debut.setColspan(1);
			             debut.setBorderWidth(2);
			             debut.setPadding(4);
				         table.addCell(debut);
			              
			        
			             PdfPCell fin = new PdfPCell(new Phrase(formatdate.format(taches.get(j).getDatefin()),fontable));
			             fin.setColspan(1);
			             fin.setBorderWidth(2);
			             fin.setPadding(4);
				         table.addCell(fin);
	            }
	            document.add(table);
	           
	               document.add(saute);
	               
	            document.addAuthor("IMS Technology");
	            document.addTitle("Planning");
	            document.addSubject("planning N°"+projet.getIdprojet());
	            document.addCreator("RABEH");
	            
	             
	            document.close();
	         
		    
		 }
		
		 
		 
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
				 String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
				int n = reader.getNumberOfPages(); 
				// Create a stamper that will copy the document to a new file 
				//PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("C:/IMS_Planning_"+projet.getClient().getSociete()+"_"+projet.getIdprojet()+".pdf")); 
				HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
				PdfStamper stamp = new PdfStamper(reader,response.getOutputStream()); 
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
				over.showText("page " + i); 
				over.endText(); 
				} 
				stamp.close();	
				
			}


			public float getTotal() {
				timesheets=managert.getObject();
				float s=0;
				 
						
				 for(int i=0;i<timesheets.size();i++){
					s=s+Float.parseFloat(timesheets.get(i).getHeur());
					 total=s;
			 }
				
				return total;
			}
			
			public void setTotal(float total) {
				this.total = total;
			}
			
			public List<Timesheet> getTimesheets() {
				timesheets=managert.getObject();
				return timesheets;
			}
			public void setTimesheets(List<Timesheet> timesheets) {
				this.timesheets = timesheets;
			}
			
			public ModelService<Projet> getManager() {
				return manager;
			}
			
			public void setManager(ModelService<Projet> manager) {
				this.manager = manager;
			}
			
			public ModelService<Tache> getManagerT() {
				return managerT;
			}
			
			public void setManagerT(ModelService<Tache> managerT) {
				this.managerT = managerT;
			}
			
			public ModelService<Client> getManagerclient() {
				return managerclient;
			}
			
			public void setManagerclient(ModelService<Client> managerclient) {
				this.managerclient = managerclient;
			}
			
			public ModelService<Timesheet> getManagert() {
				return managert;
			}
			
			public void setManagert(ModelService<Timesheet> managert) {
				this.managert = managert;
			}
			
			public Projet getProjet() {
				return projet;
			}
			
			public void setProjet(Projet projet) {
				this.projet = projet;
			}
			
			public List<Projet> getProjets() {
				if(projets==null)
					projets=manager.getObject();
				return projets;
			}
			
			public void setProjets(List<Projet> projets) {
				this.projets = projets;
			}
			
			public List<Projet> getFilteredprojets() {
				return filteredprojets;
			}
			
			public void setFilteredprojets(List<Projet> filteredprojets) {
				this.filteredprojets = filteredprojets;
			}
			
			public Timesheet getTimesheet() {
				return timesheet;
			}
			
			public void setTimesheet(Timesheet timesheet) {
				this.timesheet = timesheet;
			}
			
			public int getIdclient() {
				return idclient;
			}
			
			public void setIdclient(int idclient) {
				this.idclient = idclient;
			}
			
			public String getProjectname() {
				return projectname;
			}
			
			public void setProjectname(String projectname) {
				this.projectname = projectname;
			}
			
			public Date getDatedebut() {
				return datedebut;
			}
			
			public void setDatedebut(Date datedebut) {
				this.datedebut = datedebut;
			}
			
			public Date getDatefin() {
				return datefin;
			}
			
			public void setDatefin(Date datefin) {
				this.datefin = datefin;
			}
			
			public ModelService<Employee> getManagerEmp() {
				return managerEmp;
			}
			
			public void setManagerEmp(ModelService<Employee> managerEmp) {
				this.managerEmp = managerEmp;
			}
			
			public int getIdemp() {
				return idemp;
			}
			
			public void setIdemp(int idemp) {
				this.idemp = idemp;
			}

			public ModelServiceJDBC getManagerjdbc() {
				return managerjdbc;
			}

			public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
				this.managerjdbc = managerjdbc;
			}
	 
}
	 


