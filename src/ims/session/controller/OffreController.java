package ims.session.controller;


import ims.model.entities.Article;
import ims.model.entities.CategorieArticle;
import ims.model.entities.Client;
import ims.model.entities.CotationOffre;
import ims.model.entities.Departement;
import ims.model.entities.Employee;
import ims.model.entities.PrixArticlesOffre;
import ims.model.entities.ObjetPrix;
import ims.model.entities.Offre;
import ims.model.entities.Offre_Article;
import ims.model.entities.Rubrique;
import ims.model.entities.TypeOffre;
import ims.model.entities.TypePaiement;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;



import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

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
public class OffreController implements Serializable{

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
    
	@ManagedProperty(value="#{CategorieArticleManager}")
    private ModelService<CategorieArticle> managercatarticle;
    
    @ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
    
    @ManagedProperty(value="#{TypepaiementManager}")
    private ModelService<TypePaiement> managerTypePaiement;
    
    @ManagedProperty(value="#{CotationOffreManager}")
    private ModelService<CotationOffre> managerCotation;

    @ManagedProperty(value="#{RubriqueManager}")
    private ModelService<Rubrique> rubriquemanager;
    
    @ManagedProperty(value="#{EmployeeManager}")
    private ModelService<Employee> manageremp;
    
    @ManagedProperty(value="#{DepartementManager}")
    private ModelService<Departement> managerdept;
    
    private Offre calcule=new Offre();
    private List<Offre> calcules;
    private List<Offre> offrecontrats=new ArrayList<Offre>();
    private List<Offre> filtredcalcules;
    private int idtypeoffre;
    private List<TypeOffre> typeoffres;
    private Offre_Article ligne=new Offre_Article();
    private Offre_Article temposupp=new Offre_Article();
    private List<Offre_Article> lignes=new ArrayList<Offre_Article>();
    private List<Offre_Article> lignesView;
    //------------------------------------------
    private int idarticle;
    private int idrubrique=1;
    private int idclient;
    private boolean checkforfait;
    private boolean checkmarge;
    private int idtypepaiement;
    private boolean parjour;
    //-----------------------------------
    private String str_devis;
    
    ///////////////////////////////////////////
    
    private double marget;
    private int idcatarticle;
    private Article article=new Article();
    private double qte=1;
    private int nbrper=1;
    private double pu;
    private double marge=25;
    private double prixmargetotal=0;
    private double TVA=0;
    private double totalHT;
    private double totalTTC;
    private double taux=1;
    private double retenu;
    private boolean checktaux=true;
    /////////////////////////////////////////////////
    DecimalFormat df = new DecimalFormat("0.##");
    /////////////////////////////////////////////////
    private String ref;
    private String cotationStr;
    private String description;
    private String designation;
    private String civilite;
    private String diponibilite;
    private String validiteoffre;
    private String modalitepaiment;
    private Date dateoffre;
    private Date datefincontrat;
    private boolean contrat;
    private Client client=new Client();

    
    private String destination="D:\\cotation\\";
    private UploadedFile file;
    private StreamedContent content;
    /////////////////////////////////////////////////
    
    private String idarticleligne="";
    private String tempodesignation="";
    private String tempoqte="";
    private String tempopu="";
    private String tempomarge="";
    private boolean puzero;
    
    private ArrayList<String> filtredArticle;
    private List<Article> listArticle;
    private boolean newregister;
    ///////////////////////cotations//////////////////::
    private List<CotationOffre> cotations=new ArrayList<CotationOffre>();
    private CotationOffre cotation=new CotationOffre();
    
    //////////////////////////////
    private double appmargetotal;
    private String tempoetat;
    //////////////////////////////////////////////////////
	private final List<String> etats=new ArrayList<String>() {
		private static final long serialVersionUID = 1L;

	{ add("");add("Sans suite");add("En cours");add("Option");add("Contrat");add("Commandée");add("Concrétisée");add("Facturée");}
	};
    //////////////////////////////////////////////////////
	
	private final List<String> listoption=new ArrayList<String>() {
		private static final long serialVersionUID = 1L;

	{ add("");add("Oui");add("Non");}
	};
	
	///////////////////////////////////
	
    private int empID;
    private int deptID;
    private double montantv;
    private String optionel;
    private String labelPu="PU F.";
    private boolean artoptionel;
    
    
    public OffreController() {
    }
    
    @PostConstruct
    public void init() {
    	System.out.println("-----INIT DEVIS------");
        lignes=new ArrayList<Offre_Article>();
    	System.out.println("-----FIN INIT DEVIS----");
    }
    
    @PreDestroy
    public void destroy(){
    	calcules=null;
    	cotations=null;
    	offrecontrats=null;
    	filtredcalcules=null;
    	
    }
    
    public void changetypeoffre()
    {
    	FacesMessage msg;
    	try {
    		System.out.println("---- changetypeoffre-----");

				String type=managertype.getObject(idtypeoffre).getType();
				System.out.println("---"+type+"--");
				if(type.toLowerCase().equals("contrat"))
					contrat=true;
				else
					contrat=false;
				System.out.println(contrat);
		} catch (Exception e) {
			msg = new FacesMessage("Exception "+e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
    
    public void editcelloffre(){
    	FacesMessage msg;
    	try {
    		
    		managerjdbc.updateEtatOffre(calcule);
            msg = new FacesMessage("Etat d'offre est bien Modifier");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
            msg = new FacesMessage("Exception "+e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
    
	public List<String> completerArticle(String query){
    	try {
    	filtredArticle=new ArrayList<String>();
    	try {
			listArticle=managerObjetarticle.getObject();
			
			 for (int i = 0; i < listArticle.size(); i++) {
		            Article skin = listArticle.get(i);
		            if(skin.getRef().toLowerCase().startsWith(query) && skin.getRef()!=null) {
		            	filtredArticle.add(skin.getRef());
		            }
		        }
			
		} catch (Exception e) {
			e.getStackTrace();
			filtredArticle=null;
		}
    	return filtredArticle;
		} catch (Exception e) {
			return null;
		}

	}
	
	public List<String> completerCategorie(String query){
    	try {
    	List<String> filtredCategorie=new ArrayList<String>();
    	try {
			List<CategorieArticle> listCat=managercatarticle.getObject();
			
			 for (int i = 0; i < listCat.size(); i++) {
				 CategorieArticle skin = listCat.get(i);
		            if(skin.getCategorie().toLowerCase().startsWith(query) && skin.getCategorie()!=null) {
		            	filtredCategorie.add(skin.getCategorie());
		            }
		        }
			
		} catch (Exception e) {
			e.getStackTrace();
			filtredCategorie=null;
		}
    	return filtredCategorie;
		} catch (Exception e) {
			return null;
		}

	}
	
	public List<String> completerRubrique(String query){
    	try {
    	List<String> filtredCat=new ArrayList<String>();
    	try {
    		List<Rubrique> listRubrique=rubriquemanager.getObject();
			
			 for (int i = 0; i < listRubrique.size(); i++) {
		            Rubrique skin = listRubrique.get(i);
		            if(skin.getRef().toLowerCase().startsWith(query) && skin.getRef()!=null) {
		            	filtredCat.add(skin.getRef());
		            }
		        }
			
		} catch (Exception e) {
			e.getStackTrace();
			filtredCat=null;
		}
    	return filtredCat;
		} catch (Exception e) {
			return null;
		}

	}
    
    public void eventchangedevise(){
    	System.out.println("taux");
    	if(checktaux==true)
    		taux=1;
    	else
    		taux=11;
    	
    }
    
    public void eventchangelabel(){
    	System.out.println("Event change");
    	if(puzero==true)
    		labelPu="PU Client";
    	else
    		labelPu="PU F.";
    }
    
    public void actualiser(){
    	System.out.println("----------------------Table Actualiser-----------------");
    	calcules=null;
    }
    
    public String preparejoindre(){
    	cotations=managerCotation.getObjects(calcule.getId());
    	return "joinfile?faces-redirect=false";
    }
    
    public void depliquer(){
    	FacesMessage msg;
    	try {
        	
        	Calendar cal = Calendar.getInstance();
        	String chaine=""+cal.get(Calendar.YEAR);
        	String[] tab=chaine.split("0");
        	int lastnumbre=managerjdbc.getLastNumbre("Offre","Numero_offre",chaine);
        	lastnumbre++;
        	String nombre=String.format("%03d",lastnumbre);
        	ref=tab[1]+"OP"+nombre;
        	lignes=managerarticle.getObjects(calcule.getId());
        	calcule.setRef(ref);
        	manager.insertObject(calcule);
        	for(int i=0;i<lignes.size();i++){
        		lignes.get(i).setOffre(calcule);
        		 managerjdbc.insertOffre_Article(lignes.get(i));
        	}
        	
            msg = new FacesMessage("Offre est Depliquer");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
            msg = new FacesMessage("Exception Deplication");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}

    }
    
    public String activer(){
    	try {
    		calcules=null;
        	FacesMessage msg;
        	managerjdbc.desactiverOffre(calcule.getId());
            msg = new FacesMessage("Offre est desactiver");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "index?faces-redirect=false";
		} catch (Exception e) {
			return "index?faces-redirect=false";
		}

    }
    
    public void eventchecktotal(){
    	try {
			marge=0;
		} catch (Exception e) {
		}
    }
    
    public void joinfile(){
    	FacesMessage msg;
    	try {
    		calcules=null;
        	CotationOffre cotation=new CotationOffre();
        	cotation.setChemin(getFile().getFileName());
        	cotation.setOffre(calcule);
        	boolean op=managerCotation.insertObject(cotation);
        	if(op==true){
        	if(getFile()!=null){  
    	        try {
    	            copyFile(getFile().getFileName(), getFile().getInputstream());
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }
    			}
        		cotations=managerCotation.getObjects(calcule.getId());
   		 		msg = new FacesMessage("Cotation créer avec success");
   		 		FacesContext.getCurrentInstance().addMessage(null, msg);
        	}
        	else{
   		 		msg = new FacesMessage("le Nom de fichier exsite deja modifier le nom de fichier");
   		 		FacesContext.getCurrentInstance().addMessage(null, msg);
        	}
		} catch (Exception e) {}
    }
    
    public String annuler(){
    	try {
    		calcules=null;
        	FacesMessage msg;
        	managerjdbc.AnnulerOffre(calcule.getId(),1);
            msg = new FacesMessage("Offre est Annuler");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "index?faces-redirect=false";
		} catch (Exception e) {
			return "index?faces-redirect=false";
		}
    }
    
    public void onCelloffre(){
    	
    	FacesMessage msg;
    	
    	try {
    		if(ligne.isGratuite()){
    			ligne.setPt_calculer(ligne.getQtef()*ligne.getPu_calculer());
    			ligne.setPrixmarge(ligne.getPt_calculer());
    		}else if(ligne.isOptionnel()){
    			
    		}else if(ligne.isMargetotal()!=true){
	    		System.out.println("NO Marge TOTAL");
	    		if(ligne.getLbqte().equals("f") || ligne.getLbqte().equals("F") || ligne.getLbqte().equals("j") || ligne.getLbqte().equals("J"))
	    			ligne.setQtef(1);
	    		else
	    			{
	    			try {
	    				ligne.setQtef(Double.parseDouble(ligne.getLbqte()));
					} catch (Exception e) {
						System.out.println("contient une chaine de caracete");
						String[] tab=ligne.getLbqte().split(" ");
						ligne.setQtef(Double.parseDouble(tab[0]));
					}
	    			}
	    		
		    	ligne.setPt(ligne.getPu()*ligne.getQtef());
		    	System.out.println("IS PRIX FOURNISSEUR ===> "+ligne.isPrixfournisseurisDH());
	    		if(ligne.isPrixfournisseurisDH()==true){
	    			System.out.println("PRIX DH");
	    			ligne.setPuDH_fournisseur(Double.parseDouble(df.format(ligne.getPu()).replace(",", ".")) );
	    			ligne.setPtDH_fournisseur(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()*ligne.getQtef()).replace(",", ".")));
		    		double prixmarge=(ligne.getPu()*ligne.getMarge())/100;
		    		ligne.setPu_calculer(Double.parseDouble(df.format(ligne.getPu()+prixmarge).replace(",", ".")));
		    		ligne.setPt_calculer(Double.parseDouble(df.format(ligne.getPu_calculer()*ligne.getQtef()).replace(",", ".")));
		    		ligne.setPrixmarge(ligne.getPt_calculer()-ligne.getPt());
		    		System.out.println("FIN PRIX DH");
	    		}else{
	    			ligne.setPuDH_fournisseur(Double.parseDouble(df.format(ligne.getPu()*ligne.getTaux()).replace(",", ".")));
	    			System.out.println("avant --- PU Fournisseur ==>"+ligne.getPuDH_fournisseur());
	        		double retenu=((ligne.getPuDH_fournisseur()*ligne.getRetenu())/100);
	        		ligne.setPuDH_fournisseur(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()+retenu).replace(",", ".")));
	        		System.out.println("apres --- PU Fournisseur ==>"+ligne.getPuDH_fournisseur());
	        	   	marget=((ligne.getPuDH_fournisseur()*ligne.getMarge())/100);
		            marget=Double.parseDouble(df.format(marget).replace(',', '.'));
		            ligne.setPrixmarge(marget);
		            
	    			ligne.setPtDH_fournisseur(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()*ligne.getQtef()).replace(",", ".")));
	    			System.out.println("PT Fournisseur ==>"+ligne.getPuDH_fournisseur());
		    		ligne.setPu_calculer(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()+ligne.getPrixmarge()).replace(",", ".")));
		    		ligne.setPt_calculer(Double.parseDouble(df.format(ligne.getPu_calculer()*ligne.getQtef()).replace(",", ".")));
		    		System.out.println("PT  ==>"+ligne.getPt_calculer());	    		
	    		}
	    	}else{
	    		System.out.println("Marge TOTAL");
	    		if(ligne.getLbqte().equals("f") || ligne.getLbqte().equals("F") || ligne.getLbqte().equals("j") || ligne.getLbqte().equals("J"))
	    			ligne.setQtef(1);
	    		else{
	    			try {
	    				ligne.setQtef(Double.parseDouble(ligne.getLbqte()));
					} catch (Exception e) {
						System.out.println("contien une chaine de caracete");
						String[] tab=ligne.getLbqte().split(" ");
						ligne.setQtef(Double.parseDouble(tab[0]));
					}
	    		}
	    			
    			
	    		ligne.setPt(ligne.getPu()*ligne.getQtef());
	    		
	    		if(ligne.isPrixfournisseurisDH()==true){
	    			
	    			ligne.setPuDH_fournisseur(ligne.getPu());
	    			ligne.setPtDH_fournisseur(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()*ligne.getQtef()).replace(",", ".")));
	    			
		    		ligne.setPu_calculer(ligne.getPu());
		    		ligne.setPt_calculer(Double.parseDouble(df.format(ligne.getPu_calculer()*ligne.getQtef()).replace(",", ".")));
		    		ligne.setPrixmarge(ligne.getPt_calculer());
		    		
	    		}else{
	    			
	    			ligne.setPuDH_fournisseur(Double.parseDouble(df.format(ligne.getPu()*ligne.getTaux()).replace(",", ".")));
	    			System.out.println("avant --- PU Fournisseur ==>"+ligne.getPuDH_fournisseur());
	        		double retenu=((ligne.getPuDH_fournisseur()*ligne.getRetenu())/100);
	        		ligne.setPuDH_fournisseur(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()+retenu).replace(",", ".")));
	        		System.out.println("apres --- PU Fournisseur ==>"+ligne.getPuDH_fournisseur());
	        	   	marget=((ligne.getPuDH_fournisseur()*ligne.getMarge())/100);
		            marget=Double.parseDouble(df.format(marget).replace(',', '.'));
		            ligne.setPrixmarge(marget);
		            
	    			ligne.setPtDH_fournisseur(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()*ligne.getQtef()).replace(",", ".")));
	    			System.out.println("PT Fournisseur ==>"+ligne.getPuDH_fournisseur());
		    		ligne.setPu_calculer(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()+ligne.getPrixmarge()).replace(",", ".")));
		    		ligne.setPt_calculer(Double.parseDouble(df.format(ligne.getPu_calculer()*ligne.getQtef()).replace(",", ".")));
		    		ligne.setPrixmarge(ligne.getPt_calculer());
		    		System.out.println("PT  ==>"+ligne.getPt_calculer());
		    		
	    		}
	    	}
    	 	totalHT=0;
    	 	for(int i=0;i<lignes.size();i++){
    	 		totalHT+=lignes.get(i).getPt_calculer();
    	 	}
    	 	if(idtypepaiement==1)
    	 		TVA=totalHT*0.2;
    	 	else
    	 		TVA=0;
    	 	totalTTC=totalHT+TVA;
			msg = new FacesMessage("l'article est modifier avec Success");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			msg = new FacesMessage("Exception");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }

    public void onCellEditoffre(){
    	FacesMessage msg;
    	try {
        	
        	System.out.println("Modification En Cours ....");
        	System.out.println("Option IS "+ ligne.getOptionmodif());
        	
        	if(ligne.getOptionmodif()!=null){
            	if(ligne.getOptionmodif().equals("Oui"))
            		ligne.setOptionnel(true);
            	else
            		ligne.setOptionnel(false);
        	}
        	System.out.println(" ref update ="+ligne.getArticle().getRefupdate());
	    	if(!ligne.getArticle().getRefupdate().equals("")){
	    		System.out.println(" --- UPDATE REEF ---- ");
	    		ligne.getArticle().setRef(ligne.getArticle().getRefupdate());
	    		ligne.setArticle(managerObjetarticle.getByName(ligne.getArticle().getRef()));
	    		ligne.setDesignation(ligne.getArticle().getDesignation());
	    	}

	    	//ligne.setDesignation(ligne.getDesignation());
	    	CategorieArticle  cattempo=managercatarticle.getByName(ligne.getCategorieArticle().getCategorie());
	    	if(cattempo!=null){
	    		ligne.setCategorieArticle(cattempo);
	    	}
	    	Rubrique rtempo=rubriquemanager.getByName(ligne.getRubrique().getRef());
	    	if(rtempo!=null){
	    		ligne.setRubrique(rtempo);
	    	}
	    	
	    	if(ligne.getLbqte().equals("f") || ligne.getLbqte().equals("F") || ligne.getLbqte().equals("j") || ligne.getLbqte().equals("J"))
    			ligne.setQtef(1);
    		else
    			{
    			try {
    				ligne.setQtef(Double.parseDouble(ligne.getLbqte()));
				} catch (Exception e) {
					System.out.println("contient une chaine de caracete");
					String[] tab=ligne.getLbqte().split(" ");
					ligne.setQtef(Double.parseDouble(tab[0]));
				}
    			}
	    	//Condition ....
    		if(ligne.isMargetotal()!=true){
								    		
								    		
									    	ligne.setPt(ligne.getPu()*ligne.getQtef());
									    	
								    		if(ligne.isPrixfournisseurisDH()==true){
								    			ligne.setPuDH_fournisseur(Double.parseDouble(df.format(ligne.getPu()).replace(",", ".")) );
								    			ligne.setPtDH_fournisseur(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()*ligne.getQtef()).replace(",", ".")));
									    		double prixmarge=(ligne.getPu()*ligne.getMarge())/100;
									    		ligne.setPu_calculer(Double.parseDouble(df.format(ligne.getPu()+prixmarge).replace(",", ".")));
									    		ligne.setPt_calculer(Double.parseDouble(df.format(ligne.getPu_calculer()*ligne.getQtef()).replace(",", ".")));
									    		ligne.setPrixmarge(ligne.getPt_calculer()-ligne.getPt());
								    		}else{
								    			ligne.setPuDH_fournisseur(Double.parseDouble(df.format(ligne.getPu()*ligne.getTaux()).replace(",", ".")));
								    			
								        		double retenu=((ligne.getPuDH_fournisseur()*ligne.getRetenu())/100);
								        		ligne.setPuDH_fournisseur(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()+retenu).replace(",", ".")));
								        		
								        	   	marget=((ligne.getPuDH_fournisseur()*ligne.getMarge())/100);
									            marget=Double.parseDouble(df.format(marget).replace(',', '.'));
									            ligne.setPrixmarge(marget);
									            
								    			ligne.setPtDH_fournisseur(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()*ligne.getQtef()).replace(",", ".")));
								    			
									    		ligne.setPu_calculer(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()+ligne.getPrixmarge()).replace(",", ".")));
									    		ligne.setPt_calculer(Double.parseDouble(df.format(ligne.getPu_calculer()*ligne.getQtef()).replace(",", ".")));
									    		   		
								    		}
			    	}else{
					    		ligne.setPt(ligne.getPu()*ligne.getQtef());
					    		
					    		if(ligne.isPrixfournisseurisDH()==true){
					    			
					    			ligne.setPuDH_fournisseur(ligne.getPu());
					    			ligne.setPtDH_fournisseur(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()*ligne.getQtef()).replace(",", ".")));
					    			
						    		ligne.setPu_calculer(ligne.getPu());
						    		ligne.setPt_calculer(Double.parseDouble(df.format(ligne.getPu_calculer()*ligne.getQtef()).replace(",", ".")));
						    		ligne.setPrixmarge(ligne.getPt_calculer());
						    		
					    		}else{
					    			
					    			ligne.setPuDH_fournisseur(Double.parseDouble(df.format(ligne.getPu()*ligne.getTaux()).replace(",", ".")));
					    			
					        		double retenu=((ligne.getPuDH_fournisseur()*ligne.getRetenu())/100);
					        		ligne.setPuDH_fournisseur(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()+retenu).replace(",", ".")));
					        		
					        	   	marget=((ligne.getPuDH_fournisseur()*ligne.getMarge())/100);
						            marget=Double.parseDouble(df.format(marget).replace(',', '.'));
						            ligne.setPrixmarge(marget);
						            
					    			ligne.setPtDH_fournisseur(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()*ligne.getQtef()).replace(",", ".")));
					    			
						    		ligne.setPu_calculer(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()+ligne.getPrixmarge()).replace(",", ".")));
						    		ligne.setPt_calculer(Double.parseDouble(df.format(ligne.getPu_calculer()*ligne.getQtef()).replace(",", ".")));
						    		ligne.setPrixmarge(ligne.getPt_calculer());
						    		
						    		
					    		}
			    	}
		    	/// Fin Condition .....
    		
	    	if(ligne.isGratuite()){
	    		
    			ligne.setPt_calculer(ligne.getQtef()*ligne.getPu_calculer());
    			ligne.setPrixmarge(ligne.getPt_calculer());
    		}
	    	if(ligne.isOptionnel()){
    			
    			//ligne.setPt(ligne.getPu()*ligne.getQtef());
	    		if(ligne.isPrixfournisseurisDH()==true){
	    			ligne.setPuDH_fournisseur(Double.parseDouble(df.format(ligne.getPu()).replace(",", ".")) );
	    			//ligne.setPtDH_fournisseur(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()*ligne.getQtef()).replace(",", ".")));
	    		}else{
	    			ligne.setPuDH_fournisseur(Double.parseDouble(df.format(ligne.getPu()*ligne.getTaux()).replace(",", ".")));
	        		double retenu=((ligne.getPuDH_fournisseur()*ligne.getRetenu())/100);
	        		ligne.setPuDH_fournisseur(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()+retenu).replace(",", ".")));
	    			//ligne.setPtDH_fournisseur(Double.parseDouble(df.format(ligne.getPuDH_fournisseur()*ligne.getQtef()).replace(",", ".")));   		
	    		}
    			ligne.setPt(0);
    			ligne.setPtDH_fournisseur(0);
    			ligne.setPrixmarge(0);
    			ligne.setPt_calculer(0);
    		}
	    	
	    		System.out.println(" OPTIONEL "+ligne.isOptionnel());

		    	managerjdbc.updateOffre_Article(ligne);
		    	
    			if(lignes!=null){
    				prixmargetotal=0;
    				for(int i=0;i<lignes.size();i++){
    					if(lignes.get(i).isOptionnel()==false)
    						prixmargetotal+=lignes.get(i).getPrixmarge();
    				}
    					
    			}
    			
    			calcule.setTotalmarge(prixmargetotal);
    			
		    	managerjdbc.updatePrixOffre(calcule);
		        Offre tempo=manager.getObject(calcule.getId());
		        calcule.setTVA(tempo.getTVA());
		        calcule.setTotalHT(tempo.getTotalHT());
		        calcule.setTotalTTC(tempo.getTotalTTC());
				msg = new FacesMessage("l'article est modifier avec Success");
		        FacesContext.getCurrentInstance().addMessage(null, msg);
    	} catch (Exception e) {
			msg = new FacesMessage("Exception"+e.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
  
    public void appliquemargeAll(){
    	try {
    		managerjdbc.updateAllPrixMarge(appmargetotal,calcule);
    		calcules=null;
    		calcule=manager.getObject(calcule.getId());
    		lignes=managerarticle.getObjects(calcule.getId());
    		FacesMessage msg;
			msg = new FacesMessage("la marge Applique avec success");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    public String prepareCreate(){
    	try {
    		System.out.println("prepareCreate");
    		newregister=true;
    		checktaux=true;
    		checkmarge=false;
    		artoptionel=false;
    		contrat=false;
    		marge=20;
    		taux=1;
    		retenu=0;
        	calcule=new Offre();
        	idclient=0;
        	idtypeoffre=0;
        	idtypepaiement=0;
        	idcatarticle=0;
        	lignes=null;
        	lignes=new ArrayList<Offre_Article>();
        	totalHT=0;
        	totalTTC=0;
        	TVA=0;
        	empID=0;
        	deptID=0;
        	optionel="";
        	cotationStr="";
        	modalitepaiment="à réception de la facture";
        	validiteoffre="Cette offre est valide jusqu’au ";
        	System.out.println("prepareCreate fin");
        	return "insert?faces-redirect=true";
		} catch (Exception e) {
			return "index?faces-redirect=true";
		}

    }

	public String prepareView(){
    	try {
    		System.out.println("prepareView");
    		if(calcule.getTypeoffre().getType().toLowerCase().equals("contrat"))
    			contrat=true;
    		else
    			contrat=false;
    		
    		newregister=false;
    		checktaux=true;
    		checkmarge=false;
    		artoptionel=false;
    		
    		marge=20;
    		taux=1;
    		retenu=0;
        	lignes=null;
        	idarticle=0;
        	optionel="";
        	lignes=managerarticle.getObjects(calcule.getId());
        	idtypeoffre=calcule.getTypeoffre().getId();
        	idtypepaiement=calcule.getTypepaiement().getId();
        	idclient=calcule.getClient().getIdclient();
        	civilite=calcule.getCivilite();
        	if(calcule.getBu()!=null)
        		deptID=calcule.getBu().getId();
        	else
        		deptID=0;
        	if(calcule.getEmp()!=null)
        		empID=calcule.getEmp().getId();
        	else
        		empID=0;
        	
        	if(lignes!=null){
        		if(lignes.size()>0)
        			idcatarticle=lignes.get(0).getCategorieArticle().getIdcategorie();
        		else
        			idcatarticle=0;
        	}else
        		idcatarticle=0;
        	System.out.println("prepareView success");
        	return "Viewoffre?faces-redirect=true";
		} catch (Exception e) {
			return prepareList();
		}

    }

    public String prepareList(){
    	lignes=null;
    	lignes=new ArrayList<Offre_Article>();
    	vider();
    	return "index?faces-redirect=true";
    } 
    
    public String annulerOffre(){
    
        	try {
        	
        		return "index?faces-redirect=true";
    		} catch (Exception e) {
                return "index?faces-redirect=true";
    		}
    }
    
    public void insert(){
    	FacesMessage msg;
    	SimpleDateFormat dt = new SimpleDateFormat("yyyy"); 
    	calcules=null;
    	try{
    		if(idtypepaiement==0 || idtypeoffre==0 || idclient==0 || deptID==0 || empID==0 || cotationStr.equals("")){
    			 msg = new FacesMessage("selectionner le vide");
        		 FacesContext.getCurrentInstance().addMessage(null, msg);
    		}else{
    			TypePaiement paiement=managerTypePaiement.getObject(idtypepaiement);
    			 calcule=new Offre();
    			 calcule.setRef(ref);
    			 calcule.setNumcotation(cotationStr);
            	 calcule.setTVA(TVA);
            	 calcule.setTotalHT(totalHT);
            	 calcule.setTotalTTC(totalTTC);
            	 client=managerclient.getObject(idclient);
            	 calcule.setClient(client);
            	 calcule.setCivilite(civilite);
            	 calcule.setDateoffre(dateoffre);
            	 
            	 if(isContrat()==true)
            		 calcule.setDatearreteContrat(datefincontrat);
            	 
            	 calcule.setDescription(new String(description.getBytes("ISO-8859-1"), Charset.forName("UTF-8")));
            	 calcule.setDisponibilite(diponibilite);
            	 calcule.setValiditeoffre(validiteoffre);
            	 calcule.setModalitepaiment(modalitepaiment);
            	 calcule.setEtatoffre("En cours");
            	 calcule.setTypeoffre(managertype.getObject(idtypeoffre));
            	 calcule.setYears(managerjdbc.getYears(Integer.parseInt(dt.format(dateoffre))));
            	 calcule.setTypepaiement(new TypePaiement(1));
            	 calcule.setActiver(true);
    			 calcule.setTypepaiement(paiement);
    			 calcule.setTotalmarge(prixmargetotal);
    			 
            	 calcule.setEmp(manageremp.getObject(empID));
            	 calcule.setBu(managerdept.getObject(deptID));
            	 
    			 if(!calcule.getTypepaiement().getTypepaiement().equals("MAD")){
    				 calcule.setTotalTTC(calcule.getTotalHT());
    				 calcule.setTVA(0);
    			 }
    			 if(managerjdbc.getexsiteoffre(ref)==0){
	            	 boolean op=manager.insertObject(calcule);
	
	            	 if(op==true){
		            	 for(int i=0;i<lignes.size();i++){
		            		 int idcattempo=lignes.get(i).getCategorieArticle().getIdcategorie();
		            		 
		            		 if(lignes.get(i).isMargetotal()==true)
		            			 lignes.get(i).setPrixmarge(lignes.get(i).getPt_calculer());
		            		 
		            		 lignes.get(i).setCategorieArticle(managercatarticle.getObject(idcattempo));
		                     lignes.get(i).setOffre(calcule);
		                     managerjdbc.insertOffre_Article(lignes.get(i));
		                 }
		            	 managerjdbc.AddOffreToSansfactBC(calcule,managertype.getObject(idtypeoffre).getType());
		            	 managerjdbc.updatePrixOffre(calcule);
		            	if(getFile()!=null){  
		            	        try {
		            	            copyFile(getFile().getFileName(), getFile().getInputstream());
		            	        } catch (IOException e) {
		            	            e.printStackTrace();
		            	        }
		            			}
		            	 
		            	 msg = new FacesMessage("L'offre est bien créé");
		            	 
	            	 }
	            	 else
	            		 msg = new FacesMessage("Probleme de l'insertion","contacter l'Administration");
	            	 
	                 FacesContext.getCurrentInstance().addMessage(null, msg);
	                 vider();
    			 }
            	 else{
            		 msg = new FacesMessage("Offre existe deja");
            		 FacesContext.getCurrentInstance().addMessage(null, msg);
            		 }
    		}
    	}catch(Exception e){
    		msg = new FacesMessage("Exception Offre","Type Erreur"+e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
    }
    
	public void copyFile(String fileName, InputStream in) {
        try {
             
             OutputStream out = new FileOutputStream(new File(destination+ fileName));
           
             int read = 0;
             byte[] bytes = new byte[(int) getFile().getSize()];
           
             while ((read = in.read(bytes)) != -1) {
                 out.write(bytes, 0, read);
             }
           
             in.close();
             out.flush();
             out.close();
             } catch (IOException e) {
             System.out.println(e.getMessage());
             }
 }

	public void update() {
        FacesMessage msg;
        try {
        	if(idtypeoffre!=0 && idclient!=0 && deptID!=0 && empID!=0) {
        		// IF
			        		calcules=null;
			        		calcule.setTypeoffre(managertype.getObject(idtypeoffre));
			        		calcule.setClient(managerclient.getObject(idclient));
			        		calcule.setCivilite(civilite);
				        	calcule.setTotalmarge(prixmargetotal);
			        		calcule.setBu(managerdept.getObject(deptID));
			        		calcule.setEmp(manageremp.getObject(empID));
			          	int op=managerjdbc.UpdateOffre(calcule);
			        	managerjdbc.updatePrixOffre(calcule);
			          	calcule=manager.getObject(calcule.getId());
			          	if(op>0 && calcule!=null){
			                msg = new FacesMessage("Offre est bien Modifier","");
			
			                FacesContext.getCurrentInstance().addMessage(null, msg);
			          	}else
			          	{
			          		msg = new FacesMessage("numero Offre existe deja","");
			                FacesContext.getCurrentInstance().addMessage(null, msg);
			          	}
			     // fin IF
        	}else{
          		msg = new FacesMessage("Selectionner les champs sont obligatoire","");
                FacesContext.getCurrentInstance().addMessage(null, msg);
        	}
        } catch (NullPointerException e) {
           msg = new FacesMessage("Probleme de Modification de l'offre changer le reference","contacter l'administration");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
   }
    
    public void Delete(){
        FacesMessage msg;
        try {
        	calcules=null;
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
    
    public void menu(){
    	calcules=null;
        calcules=manager.getObject();
        
    }
    
    public void ajouterligne(){
    	FacesMessage msg;
		DecimalFormat df = new DecimalFormat("0.##");
    	try {
    		if(idcatarticle==0 || idarticle==0 || idrubrique==0){
	            msg = new FacesMessage("selectionner les champs obligatoire");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
    		}else{
        			
		    			Offre_Article tempoarticle=new Offre_Article();
			        	tempoarticle.setPu(pu);
			        	tempoarticle.setMargetotal(checkmarge);
			        	tempoarticle.setQtef(qte);
			        	tempoarticle.setEtat("En cours");
			        	double m=marge;
			        	tempoarticle.setMarge(m);
			        	Article article=managerObjetarticle.getObject(idarticle);
			        	tempoarticle.setArticle(article);
			        	tempoarticle.setRubrique(rubriquemanager.getObject(idrubrique));
			        	if(checkforfait==true){
			        		tempoarticle.setLbqte("F");
			        		tempoarticle.setQtef(1);
			        		nbrper=0;
			        	}
			        	else{
				        	if(parjour==true)
				        		{
				        		tempoarticle.setLbqte(qte+" J");
				        		}
				        	else{
				            	if(tempoarticle.getArticle().getTypearticle().getType().toLowerCase().equals("contrat"))
				            		{
				            		tempoarticle.setLbqte("contrat");
				            		tempoarticle.setQtef(1);
				            		nbrper=0;
				            		}
				            	else
				            		tempoarticle.setLbqte(qte+"");
				        	}
			        	}
			        	
			        	if(designation.equals(""))
				            tempoarticle.setDesignation(article.getDesignation());
				        else
				            tempoarticle.setDesignation(designation);
			        	
				           if(!optionel.equals("")){
				        	   tempoarticle.setDesignation(tempoarticle.getDesignation()+"\n \n"+optionel);
				           }
				           
				          
				        tempoarticle.setCategorieArticle(managercatarticle.getObject(idcatarticle));
			        	tempoarticle.setPrixfournisseurisDH(checktaux);
			        	tempoarticle.setTaux(taux);
			        	tempoarticle.setRetenu(retenu);
			        	tempoarticle.setNombrepersonne(nbrper);
			        	tempoarticle.setPt(tempoarticle.getPu()*tempoarticle.getQtef());
			        	tempoarticle.setPt(Double.parseDouble(df.format(tempoarticle.getPt()).replace(',', '.')));
			            tempoarticle.setGratuite(puzero);
			            tempoarticle.setOptionnel(artoptionel);
							if(puzero==true){
									tempoarticle.setPu(0);
									tempoarticle.setPt(0);
									tempoarticle.setPuDH_fournisseur(0);
									tempoarticle.setPtDH_fournisseur(0);
									tempoarticle.setMargetotal(true);
									tempoarticle.setMarge(100);
									tempoarticle.setPrixmarge(pu*tempoarticle.getQtef());
									tempoarticle.setPu_calculer(pu);
									tempoarticle.setPt_calculer(pu*tempoarticle.getQtef());
							}else{
								   	if(tempoarticle.isPrixfournisseurisDH()==true){
						        		
							        		tempoarticle.setPuDH_fournisseur(tempoarticle.getPu());
							        		tempoarticle.setPtDH_fournisseur(tempoarticle.getPt());
							            	
							        		
							        		marget=((tempoarticle.getPu()*tempoarticle.getMarge())/100);
								            marget=Double.parseDouble(df.format(marget).replace(',', '.'));
								            tempoarticle.setPu_calculer(tempoarticle.getPu()+marget);
								            
								            double nombre=tempoarticle.getPu_calculer();
								           
								            Double tcalc=Double.parseDouble(df.format(nombre).replace(',','.'));
								            tempoarticle.setPu_calculer(tcalc);
								            

							            	
							            	tempoarticle.setPt_calculer(tempoarticle.getPu_calculer()*tempoarticle.getQtef());
								            tempoarticle.setPt_calculer(Double.parseDouble(df.format(tempoarticle.getPt_calculer()).replace(',', '.')));
								
								            
								            if(checkmarge==true){
								            	System.err
														.println("check marge true");
								            	tempoarticle.setPrixmarge(tempoarticle.getPt_calculer());
								            }
								            else{
								            	
						            	 tempoarticle.setPrixmarge(tempoarticle.getPt_calculer()-tempoarticle.getPt());
									        tempoarticle.setPrixmarge(Double.parseDouble(df.format(tempoarticle.getPrixmarge()).replace(',', '.')));
									         
								            }
								          
						            }
						        	else{
								        		tempoarticle.setPuDH_fournisseur(tempoarticle.getPu()*tempoarticle.getTaux());
								        		double retenu=((tempoarticle.getPuDH_fournisseur()*tempoarticle.getRetenu())/100);
								        		tempoarticle.setPuDH_fournisseur(tempoarticle.getPuDH_fournisseur()+retenu);
								        		tempoarticle.setPtDH_fournisseur(tempoarticle.getPuDH_fournisseur()*tempoarticle.getQtef());
									           
								        		System.out
														.println(" PU Fournisseur "+tempoarticle.getPuDH_fournisseur());
								        		System.out
												.println(" PT Fournisseur "+tempoarticle.getPtDH_fournisseur());
								        		
								        		 	marget=((tempoarticle.getPuDH_fournisseur()*tempoarticle.getMarge())/100);
										            marget=Double.parseDouble(df.format(marget).replace(',', '.'));
										            tempoarticle.setPrixmarge(marget);
										            
										            System.out
													.println(" marget "+tempoarticle.getPrixmarge());
										            
										            tempoarticle.setPu_calculer(tempoarticle.getPuDH_fournisseur()+tempoarticle.getPrixmarge());
										            System.out.println("PU calc EN DH"+tempoarticle.getPu_calculer());
										            
										            //====
									        		 double nombre=tempoarticle.getPu_calculer();
									        		 Double tcalc=Double.parseDouble(df.format(nombre).replace(',','.'));
											            tempoarticle.setPu_calculer(tcalc);
											            tempoarticle.setPt_calculer(tempoarticle.getPu_calculer()*tempoarticle.getQtef());
											            tempoarticle.setPt_calculer(Double.parseDouble(df.format(tempoarticle.getPt_calculer()).replace(',', '.')));
											         //====
											            System.out
														.println(" PT calc "+tempoarticle.getPt_calculer());
									            
									            if(checkmarge==true)
									            	tempoarticle.setPrixmarge(tempoarticle.getPt_calculer());
									            else{
									            	tempoarticle.setPrixmarge(tempoarticle.getPt_calculer()-tempoarticle.getPtDH_fournisseur());
									            	tempoarticle.setPrixmarge(Double.parseDouble(df.format(tempoarticle.getPrixmarge()).replace(',', '.')));
									            }
								          
							                System.out.println("Marge EN DH "+tempoarticle.getPrixmarge());
								            
						        	}
							}
							if(artoptionel==true){
									tempoarticle.setPt_calculer(0);
    								}
				            tempoarticle.setId(lignes.size()+1);
				            lignes.add(tempoarticle);
				            totalHT=totalHT+tempoarticle.getPt_calculer();
				            
				    	 	if(idtypepaiement==1){
		
					            TVA=(totalHT*0.2f);
					            TVA=Double.parseDouble(df.format(TVA).replace(',','.'));
				    	 	}else{
				    	 		TVA=0;
				    	 	}
				            totalTTC=totalHT+TVA;
				
				            msg = new FacesMessage("Article ajouter avec success");
				            FacesContext.getCurrentInstance().addMessage(null, msg);
				            pu=0;
				            qte=1;
				            designation="";
				            optionel="";
				            parjour=false;
				            checkforfait=false;
				            artoptionel=false;
				            idarticle=0;
        		
    		}
    	} catch (Exception e) {
    		msg = new FacesMessage("Erreur d'insertion","contacter l'Administration");
            FacesContext.getCurrentInstance().addMessage(null, msg);
    		 System.out.println("---- ERROR"+e.getLocalizedMessage());
		}
    }
    
    public void ajouterligne_modif(){
    	try {    		
    		DecimalFormat df = new DecimalFormat("0.##");
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
    		FacesMessage msg;
    		
    		if(idcatarticle==0 || idarticle==0){
	            msg = new FacesMessage("selectionner l'article avec leur categorie");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
    		}
    		else{
    				Offre_Article tmpligne=new Offre_Article();
		        	article=managerObjetarticle.getObject(idarticle);
		        	tmpligne.setArticle(article);
		        	tmpligne.setPu(pu);
		        	tmpligne.setQtef(qte);
		        	tmpligne.setCategorieArticle(managercatarticle.getObject(idcatarticle));
		        	tmpligne.setEtat(calcule.getEtatoffre());
		        	if(checkforfait==true){
		        		
			        		tmpligne.setLbqte("F");
			        		tmpligne.setQtef(1);
			        		nbrper=0;
		        	}
		        	else{
			        		if(checkforfait==true){
			        			tmpligne.setLbqte("F");
			        			tmpligne.setQtef(1);
			            		nbrper=0;
			            	}
			            	else{
			    	        	if(parjour==true)
			    	        		{
			    	        		tmpligne.setLbqte(qte+" J");
			    	        		}
			    	        	else{
			    	            	if(tmpligne.getArticle().getTypearticle().getType().toLowerCase().equals("contrat"))
			    	            		{
			    	            		tmpligne.setLbqte("contrat");
			    	            		tmpligne.setQtef(1);
			    	            		nbrper=0;
			    	            		}
			    	            	else
			    	            		tmpligne.setLbqte(qte+"");
			    	        	}
			            	}
		        	}
		        	
		        	if(designation.equals("")){
		        		 tmpligne.setDesignation(article.getDesignation());
		            	}
		            else{
		            	tmpligne.setDesignation(designation);
		            }
		        	 if(!optionel.equals("")){
		        		 tmpligne.setDesignation(tmpligne.getDesignation()+"\n \n"+optionel);
			           }
		        	 
		        	tmpligne.setTaux(taux);
		        	tmpligne.setRetenu(retenu);
		        	tmpligne.setPrixfournisseurisDH(checktaux);
		        	tmpligne.setMarge(marge);
		        	tmpligne.setPt(tmpligne.getPu()*tmpligne.getQtef());
		        	tmpligne.setPt(Double.parseDouble(df.format(tmpligne.getPt()).replace(',', '.')));
		        	tmpligne.setGratuite(puzero);
		        	tmpligne.setOptionnel(artoptionel);
		        	if(puzero==true){
			        		tmpligne.setPu(0);
			        		tmpligne.setPt(0);
			        		tmpligne.setPuDH_fournisseur(0);
			        		tmpligne.setPtDH_fournisseur(0);
			        		tmpligne.setMargetotal(true);
			        		tmpligne.setMarge(100);
			        		tmpligne.setPrixmarge(pu*tmpligne.getQtef());
			        		tmpligne.setPu_calculer(pu);
			        		tmpligne.setPt_calculer(pu*tmpligne.getQtef());
					}
		        	else{
						        	if(tmpligne.isPrixfournisseurisDH()==true){
						        		
						        		tmpligne.setPuDH_fournisseur(tmpligne.getPu());
						        		tmpligne.setPtDH_fournisseur(tmpligne.getPt());
						        		
							            marget=((tmpligne.getPu()*tmpligne.getMarge())/100);
							            marget=Double.parseDouble(df.format(marget).replace(',', '.'));
							            tmpligne.setPu_calculer(tmpligne.getPu()+marget);
							            double nombre=tmpligne.getPu_calculer();
							            double tcalc=Double.parseDouble(df.format(nombre).replace(',','.'));
							            tmpligne.setPu_calculer(tcalc);
							            tmpligne.setPt_calculer(tmpligne.getPu_calculer()*tmpligne.getQtef());
							            tmpligne.setPt_calculer(Double.parseDouble(df.format(tmpligne.getPt_calculer()).replace(',', '.')));
							        	tmpligne.setMargetotal(checkmarge);
							        	if(checkmarge==true)
							        		 tmpligne.setPrixmarge(tmpligne.getPt_calculer());
							        	 else{
							        		 tmpligne.setPrixmarge(tmpligne.getPt_calculer()-tmpligne.getPt());
							        		 tmpligne.setPrixmarge(Double.parseDouble(df.format(tmpligne.getPrixmarge()).replace(',', '.')));
							        	} 
						        	}else{
						        		
						        		tmpligne.setPuDH_fournisseur(tmpligne.getPu()*tmpligne.getTaux());
						        		double retenu=((tmpligne.getPuDH_fournisseur()*tmpligne.getRetenu())/100);
						        		tmpligne.setPuDH_fournisseur(tmpligne.getPuDH_fournisseur()+retenu);
						        		tmpligne.setPtDH_fournisseur(tmpligne.getPuDH_fournisseur()*tmpligne.getQtef());
						        		
						        	
						        		marget=((tmpligne.getPuDH_fournisseur()*tmpligne.getMarge())/100);
							            marget=Double.parseDouble(df.format(marget).replace(',', '.'));
							            tmpligne.setPrixmarge(marget);
								            tmpligne.setPu_calculer(tmpligne.getPuDH_fournisseur()+tmpligne.getPrixmarge());
								            tmpligne.setPuDH_fournisseur(tmpligne.getPuDH_fournisseur()+tmpligne.getPrixmarge());
								            tmpligne.setPu_calculer(Double.parseDouble(df.format(tmpligne.getPu_calculer()).replace(',','.')));
								            
								            tmpligne.setPt_calculer(tmpligne.getPu_calculer()*tmpligne.getQtef());
								            tmpligne.setPt_calculer(Double.parseDouble(df.format(tmpligne.getPt_calculer()).replace(',', '.')));
								        	
								            tmpligne.setMargetotal(checkmarge);
								        	if(checkmarge==true)
								        		 tmpligne.setPrixmarge(tmpligne.getPt_calculer());
								        	 else{
								        		 tmpligne.setPrixmarge(tmpligne.getPt_calculer()-tmpligne.getPtDH_fournisseur());
								        		 tmpligne.setPrixmarge(Double.parseDouble(df.format(tmpligne.getPrixmarge()).replace(',', '.')));
								        	} 
						        	}

					}
		        	if(artoptionel==true){
		        		tmpligne.setPt_calculer(0);
		        		tmpligne.setPt(0);
		        		tmpligne.setPrixmarge(0);
		        	}
		        		
		        	 
		        	lignes.add(tmpligne);
		           
		            calcule.setTotalHT(calcule.getTotalHT()+tmpligne.getPt_calculer());
		            calcule.setTotalHT(Double.parseDouble(df.format(calcule.getTotalHT()).replace(',', '.')));
		            calcule.setTVA(calcule.getTotalHT()*0.2f);
		            calcule.setTVA(Double.parseDouble(df.format(calcule.getTVA()).replace(',', '.')));
		            calcule.setTotalTTC(calcule.getTotalHT()+calcule.getTVA());
		            pu=0;
		            qte=1;
		            designation="";
		            optionel="";
		            artoptionel=false;
		            parjour=false;
		            checkforfait=false;
		            tmpligne.setOffre(calcule);
		            tmpligne.setRubrique(rubriquemanager.getObject(idrubrique));
		                 int op=managerjdbc.insertOffre_Article(tmpligne);
		                 if(op==1){
		                		
		                			if(lignes!=null){
		                				prixmargetotal=0;
		                				for(int i=0;i<lignes.size();i++){
		                					if(lignes.get(i).isOptionnel()==false)
		                						prixmargetotal+=lignes.get(i).getPrixmarge();
		                				}
		                					
		                			}
		                			calcule.setTotalmarge(prixmargetotal);
		                	
		                	 	managerjdbc.updatePrixOffre(calcule);
		                	 	msg = new FacesMessage("Article ajouter avec success");
		                	 	FacesContext.getCurrentInstance().addMessage(null, msg);
		                	 	msg = new FacesMessage("offre est modifier");
		                	 	}
		                 else
		                	 msg = new FacesMessage("Exception DATA BASE");
		                 
		           idarticle=0;
		           FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
    	} catch (Exception e) {
    		 System.out.println("EXCEPTION APPLICATION");
		}
    }
    
    public void DeleteArticle(){
        FacesMessage msg;
        try {
        	
        	lignes.remove(ligne); 
        	
     		DecimalFormat df = new DecimalFormat("0.##");
            df.setMinimumFractionDigits(2);
            
        		marget=((temposupp.getPu()*temposupp.getMarge())/100);
        		totalHT=totalHT-temposupp.getPt_calculer()-marget;
        		totalHT=Double.parseDouble(df.format(totalHT).replace(',', '.'));
        		
        		TVA=(totalHT*0.2f);
        		TVA=Double.parseDouble(df.format(TVA).replace(',', '.'));
        		
        		totalTTC=totalHT+TVA;
        		
        		 
        		if(totalTTC<0)
        		{
        			totalTTC=0;
        			TVA=0;
        			totalHT=0;	
        		}
        		msg = new FacesMessage("Article est supprimer de l'offre");
              FacesContext.getCurrentInstance().addMessage(null, msg);

            } catch (Exception e) {
                msg = new FacesMessage("Probleme de suppression contacter Administration");
           FacesContext.getCurrentInstance().addMessage(null, msg);
            }   
    }
   
    public void DeleteArticle_Modif(){
        FacesMessage msg;
        try {

        		marget=((ligne.getPu()*ligne.getMarge())/100);
        		
        		
        		double tempopu=ligne.getPt_calculer();
        		double tempoht=calcule.getTotalHT();
        		double nouvht=tempoht-tempopu;
        		
        		DecimalFormat df = new DecimalFormat("0.##");
                df.setMinimumFractionDigits(2);
        		calcule.setTotalHT(Double.parseDouble(df.format(nouvht).replace(',', '.')));
        		
        		calcule.setTVA(calcule.getTotalHT()*0.2f);
        		calcule.setTVA(Double.parseDouble(df.format(calcule.getTVA()).replace(',', '.')));
        		
        		calcule.setTotalTTC(calcule.getTotalHT()+calcule.getTVA());
        		calcule.setTotalTTC(Double.parseDouble(df.format(calcule.getTotalTTC()).replace(',','.')));
        		
        		if(calcule.getTotalTTC()<=0)
        		{
            		calcule.setTotalHT(0);
            		calcule.setTVA(0);
            		calcule.setTotalTTC(0);
        		}
        		boolean op=managerjdbc.deleteArticleOffre(ligne.getId());
        	
        		if(op==true){
        			lignes.remove(ligne);
        			
        			if(lignes!=null){
        				prixmargetotal=0;
        				for(int i=0;i<lignes.size();i++){
        					if(lignes.get(i).isOptionnel()==false)
        					prixmargetotal+=lignes.get(i).getPrixmarge();
        				}
        					
        			}
        			
        			calcule.setTotalmarge(prixmargetotal);
        			System.err.println("---------"+prixmargetotal+"----------------");
        			managerjdbc.updatePrixOffre(calcule);
        			//update en niveau offre
        			msg = new FacesMessage("Article est Supprimer");
        			FacesContext.getCurrentInstance().addMessage(null, msg);
        			msg = new FacesMessage("offre est modifier");
        		}
        			
        		else
        			msg = new FacesMessage("Article Déja liée a une facture");	
        		
        		FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception e) {
                msg = new FacesMessage("Exception ","Type Erreur : "+e.getMessage());
           FacesContext.getCurrentInstance().addMessage(null, msg);
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
        civilite="Monsieur";
    }
    
    public void createPdf(String dest){
    	
    	
    	DateFormat format=new SimpleDateFormat("dd/MM/yyyy");
    	
    	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");

    	Phrase saute=new Phrase("\n");
    	 float left = 36;
         float right = 36;
         float top = 60;
         float bottom = 100;
        Document document = new Document(PageSize.A4,left, right, top, bottom);

        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        
        try {
        	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();
        	
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename="+calcule.getRef()+".pdf");
            Font ftextpetite=new Font(Font.FontFamily.HELVETICA,7,Font.NORMAL,new BaseColor(Color.black));
            Font ftext=new Font(Font.FontFamily.HELVETICA,9,Font.NORMAL,new BaseColor(Color.black));
            Font ftextgra=new Font(Font.FontFamily.HELVETICA,10,Font.BOLD,new BaseColor(Color.black));
            Font forange=new Font(Font.FontFamily.HELVETICA,12,Font.UNDERLINE,new BaseColor(247, 150, 70));
           // document.setMargins(36, 36, 108, 50);
            document.setMargins(left, right,108, bottom);
            document.add(saute);
            document.add(saute);
            Paragraph villedate=new Paragraph("Casablanca, le "+format.format(calcule.getDateoffre()),ftext);
            villedate.setIndentationLeft(300);
            document.add(villedate);
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
            Phrase textobjet=new Phrase("Proposition Financière",ftextgra);
            Phrase lbobjet=new Phrase("Objet     : ",ftext);
            Paragraph objet=new Paragraph(lbobjet);
            objet.add(textobjet);
            objet.setIndentationLeft(53);
            document.add(objet);
            document.add(saute);
            Paragraph presentation;
            
            if(calcule.getTypeoffre().getType().toLowerCase().equals("prestation")){
            	presentation=new Paragraph(calcule.getCivilite()+",\n\nFaisant suite à  votre demande nous vous communiquons notre meilleure offre de prix pour la prestation des services suivants:",ftext);
            }
            else if(calcule.getTypeoffre().getType().toLowerCase().equals("renouvellement")){
            	presentation=new Paragraph(calcule.getCivilite()+",\n\nFaisant suite à votre demande nous vous communiquons notre meilleure offre de prix pour le renouvellement des produits suivants :",ftext);
            }
            else if(calcule.getTypeoffre().getType().toLowerCase().equals("formation")){
            	presentation=new Paragraph(calcule.getCivilite()+",\n\nNous avons le plaisir de vous faire parvenir notre offre de prix concernant la formation suivante :",ftext);
            }
            else if(calcule.getTypeoffre().getType().toLowerCase().equals("certification")){
            	presentation=new Paragraph(calcule.getCivilite()+",\n\nNous avons le plaisir de vous faire parvenir notre offre de prix pour les certifications suivantes :",ftext);
            }
            else
            	presentation=new Paragraph(calcule.getCivilite()+",\n\nFaisant suite à  votre demande nous vous communiquons notre meilleure offre de prix pour l'acquisition des produits suivants:",ftext);
            
            presentation.setIndentationLeft(53);
            document.add(presentation);
            document.add(saute);
          
            
            
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
              
               List<PrixArticlesOffre> str_Sring=managerjdbc.getArticleOffre(calcule);
               List<String> Titrerubriques=new ArrayList<String>();
               List<PrixArticlesOffre> str_tree=new ArrayList<>();
               boolean trouve=false;
               String rubriquetitre="";
            if(str_Sring!=null){
            	
            	int nbrtest= str_Sring.size();
	               for(int i=0;i<str_Sring.size();i++)
	            {
	            	 trouve=false;
	            	 rubriquetitre="";
	            	 
		            		int incrubrique=0;
		            		if(!str_Sring.get(i).getRubrique().equals("")){
	            			while(incrubrique<Titrerubriques.size()){
	            					if(str_Sring.get(i).getRubrique().equals(Titrerubriques.get(incrubrique)))
	            						trouve=true;
	            					incrubrique++;
	            				}
	            			
		            			if(!trouve){
		            				Titrerubriques.add(str_Sring.get(i).getRubrique());	
		            				rubriquetitre=str_Sring.get(i).getRubrique();
		            			}
	            			}
	            			
	            				if(!rubriquetitre.equals("")){
			            			c1 = new PdfPCell(new Phrase(rubriquetitre,ftext));
			    		            c1.setColspan(5);
			    		            c1.setBackgroundColor(new BaseColor(Color.decode("#fcbd6a")));
			    		            table.addCell(c1);
	            				}
		            		c1 = new PdfPCell(new Phrase(str_Sring.get(i).getReference(),ftext));
		                    
		                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		                    c1.setVerticalAlignment(100);
		                    if(nbrtest<10)
				            	c1.setPadding(5);
		                    
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
				            	c1.setPadding(5);
				            table.addCell(c1);
				         
				            c1 = new PdfPCell(new Phrase(str_Sring.get(i).getChaineqantite(),ftext));
				            
				            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
					           if(nbrtest<10){
					        	   c1.setPaddingTop(5);
					            	c1.setPaddingBottom(5);
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
					        	   c1.setPaddingTop(5);
					            	c1.setPaddingBottom(5);
					           }
	
				            table.addCell(c1);
				            if(str_Sring.get(i).getOptionnel()==1)
				            	c1 = new PdfPCell(new Phrase("Option",ftext));
				            else{
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
				            }
				            
				            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           if(nbrtest<10){
					        	   c1.setPaddingTop(5);
					            	c1.setPaddingBottom(5);
					           }
				            table.addCell(c1);
	            
	            }
            }
            if(calcule.getTypepaiement().getTypepaiement().equals("MAD")){
            	
            	  ObjetPrix objettotal=managerjdbc.gettotalPrix(calcule);
            	
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
                PdfPCell totalHT = new PdfPCell(new Phrase("Total en "+calcule.getTypepaiement().getTypepaiement().toLowerCase(),ftextgra));
                	totalHT.setColspan(4);
                
                totalHT.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
                table.addCell(totalHT);
                	System.out.println("HT =>"+df.format(calcule.getTotalHT()));
                	  
                	String chaine=""+calcule.getTotalHT();
                	chaine=chaine.replace(".", ",");
                	String[] nombre=chaine.split(",");
                	System.out.println(nombre);
                	if(nombre[1]!=null){
                		System.out.println("not null");
                		char[] virgule=nombre[1].toCharArray();
                		
                		if(virgule.length>1)
                			c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+""+virgule[1],ftextgra));
                		else
                			c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+"0",ftextgra));
                	}else
                	c1 = new PdfPCell(new Phrase(df.format(nombre[0])+",00",ftextgra));

    	        
                c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(c1);
            }
            
            document.add(table);
            document.add(saute);
            document.add(saute);
            
            Paragraph pie=new Paragraph();
            
            
            if(calcule.getDisponibilite()!=null){
            	if(!calcule.getDisponibilite().equals("")){
		            pie.add(new Paragraph("Disponibilité",forange));
		            pie.add(new Paragraph(calcule.getDisponibilite(),ftext));
            	}
            }
            if(calcule.getValiditeoffre()!=null){
            	if(!calcule.getValiditeoffre().equals("")){
            	pie.add(new Paragraph("Validité de l'offre",forange));
	            pie.add(new Paragraph(calcule.getValiditeoffre(),ftext));
            	}

            }
            if(calcule.getModalitepaiment()!=null){
            	if(!calcule.getModalitepaiment().equals("")){
	            	   pie.add(new Paragraph("Modalité de paiement",forange));
	   	            pie.add(new Paragraph(calcule.getModalitepaiment(),ftext));
            	}
            }
            pie.add(saute);

            pie.add(new Phrase("Nous restons à votre entière disposition pour toute information complémentaire.\nDans l’attente, veuillez accepter, "+calcule.getCivilite()+", nos cordiales salutations",ftext));
            pie.setIndentationLeft(53);
            document.add(pie);
            
            document.addAuthor("IMS Technology");
            document.addTitle("Offre");
            document.addSubject("Offre N°"+calcule.getRef());
            document.addCreator("RABEH");
            document.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (DocumentException | IOException de) {
            System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF");
            document.close();
            FacesContext.getCurrentInstance().responseComplete();
        }
      }
    
 public void createPdfsansref(String dest){
    	
    	
    	DateFormat format=new SimpleDateFormat("dd/MM/yyyy");
    	
    	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");

    	Phrase saute=new Phrase("\n");
    	 float left = 36;
         float right = 36;
         float top = 60;
         float bottom = 100;
        Document document = new Document(PageSize.A4,left, right, top, bottom);

        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        
        try {
        	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();
        	
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename="+calcule.getRef()+".pdf");
            Font ftextpetite=new Font(Font.FontFamily.HELVETICA,7,Font.NORMAL,new BaseColor(Color.black));
            Font ftext=new Font(Font.FontFamily.HELVETICA,9,Font.NORMAL,new BaseColor(Color.black));
            Font ftextgra=new Font(Font.FontFamily.HELVETICA,10,Font.BOLD,new BaseColor(Color.black));
            Font forange=new Font(Font.FontFamily.HELVETICA,12,Font.UNDERLINE,new BaseColor(247, 150, 70));
           // document.setMargins(36, 36, 108, 50);
            document.setMargins(left, right,108, bottom);
            Paragraph villedate=new Paragraph("Casablanca, le "+format.format(calcule.getDateoffre()),ftext);
            villedate.setIndentationLeft(300);
            document.add(villedate);
            Paragraph client=new Paragraph(""+calcule.getClient().getSociete(),ftextgra);
            client.setIndentationLeft(300);
            document.add(client);
            Phrase textref=new Phrase(calcule.getRef(),ftextgra);
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
            
            if(calcule.getTypeoffre().getType().toLowerCase().equals("prestation")){
            	presentation=new Paragraph(calcule.getCivilite()+",\n\nFaisant suite à  votre demande nous vous communiquons notre meilleure offre de prix pour la prestation des services suivants:",ftext);
            }
            else if(calcule.getTypeoffre().getType().toLowerCase().equals("renouvellement")){
            	presentation=new Paragraph(calcule.getCivilite()+",\n\nFaisant suite à votre demande nous vous communiquons notre meilleure offre de prix pour le renouvellement des produits suivants :",ftext);
            }
            else if(calcule.getTypeoffre().getType().toLowerCase().equals("formation")){
            	presentation=new Paragraph(calcule.getCivilite()+",\n\nNous avons le plaisir de vous faire parvenir notre offre de prix concernant la formation suivante :",ftext);
            }
            else if(calcule.getTypeoffre().getType().toLowerCase().equals("certification")){
            	presentation=new Paragraph(calcule.getCivilite()+",\n\nNous avons le plaisir de vous faire parvenir notre offre de prix pour les certifications suivantes :",ftext);
            }
            else
            	presentation=new Paragraph(calcule.getCivilite()+",\n\nFaisant suite à  votre demande nous vous communiquons notre meilleure offre de prix pour l'acquisition des produits suivants:",ftext);
            
            presentation.setIndentationLeft(53);
            document.add(presentation);
            document.add(saute);
          
            
            
            DecimalFormat df = new DecimalFormat();
            df.setDecimalSeparatorAlwaysShown(true);
            PdfPTable table;
            	table= new PdfPTable(4);
            	table.setWidths(new float[]{180,25,40,50});
            
            table.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        table.setWidthPercentage(90);
	        
            String titre="";
            
            PdfPCell c1 ;
            
            

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
              
               List<PrixArticlesOffre> str_Sring=managerjdbc.getArticleOffre(calcule);
               List<String> Titrerubriques=new ArrayList<String>();
               List<PrixArticlesOffre> str_tree=new ArrayList<>();
               boolean trouve=false;
               String rubriquetitre="";
            if(str_Sring!=null){
            	
            	int nbrtest= str_Sring.size();
	               for(int i=0;i<str_Sring.size();i++)
	            {
	            	 trouve=false;
	            	 rubriquetitre="";
	            	 
		            		int incrubrique=0;
		            		if(!str_Sring.get(i).getRubrique().equals("")){
	            			while(incrubrique<Titrerubriques.size()){
	            					if(str_Sring.get(i).getRubrique().equals(Titrerubriques.get(incrubrique)))
	            						trouve=true;
	            					incrubrique++;
	            				}
	            			
		            			if(!trouve){
		            				Titrerubriques.add(str_Sring.get(i).getRubrique());	
		            				rubriquetitre=str_Sring.get(i).getRubrique();
		            			}
	            			}
	            			
	            				if(!rubriquetitre.equals("")){
			            			c1 = new PdfPCell(new Phrase(rubriquetitre,ftext));
			    		            c1.setColspan(5);
			    		            c1.setBackgroundColor(new BaseColor(Color.decode("#fcbd6a")));
			    		            table.addCell(c1);
	            				}
		            		
				            
				            if(str_Sring.get(i).getDesignation()==null)
				            	str_Sring.get(i).setDesignation(" ");
				            Phrase para;
				            if(str_Sring.get(i).getDesignation().length()>1000)
				            		para=new Phrase(str_Sring.get(i).getDesignation(),ftextpetite);
				            	else
				            		para=new Phrase(str_Sring.get(i).getDesignation(),ftext);
				      
				            	c1 = new PdfPCell(para);
				            	if(nbrtest<10)
				            	c1.setPadding(5);
				            table.addCell(c1);
				         
				            c1 = new PdfPCell(new Phrase(str_Sring.get(i).getChaineqantite(),ftext));
				            
				            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
					           if(nbrtest<10){
					        	   c1.setPaddingTop(5);
					            	c1.setPaddingBottom(5);
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
					        	   c1.setPaddingTop(5);
					            	c1.setPaddingBottom(5);
					           }
	
				            table.addCell(c1);
				            if(str_Sring.get(i).getOptionnel()==1)
				            	c1 = new PdfPCell(new Phrase("Option",ftext));
				            else{
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
				            }
				            
				            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					           if(nbrtest<10){
					        	   c1.setPaddingTop(5);
					            	c1.setPaddingBottom(5);
					           }
				            table.addCell(c1);
	            
	            }
            }
            if(calcule.getTypepaiement().getTypepaiement().equals("MAD")){
            	
            	  ObjetPrix objettotal=managerjdbc.gettotalPrix(calcule);
            	
                PdfPCell totalHT = new PdfPCell(new Phrase("Total HT",ftextgra));
                	totalHT.setColspan(3);
                
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
                	tva.setColspan(3);
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
                
                	totalTTC.setColspan(3);
                
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
                PdfPCell totalHT = new PdfPCell(new Phrase("Total en "+calcule.getTypepaiement().getTypepaiement().toLowerCase(),ftextgra));
                	totalHT.setColspan(3);
                
                totalHT.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
                table.addCell(totalHT);
                	System.out.println("HT =>"+df.format(calcule.getTotalHT()));
                	  
                	String chaine=""+calcule.getTotalHT();
                	chaine=chaine.replace(".", ",");
                	String[] nombre=chaine.split(",");
                	System.out.println(nombre);
                	if(nombre[1]!=null){
                		System.out.println("not null");
                		char[] virgule=nombre[1].toCharArray();
                		
                		if(virgule.length>1)
                			c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+""+virgule[1],ftextgra));
                		else
                			c1 = new PdfPCell(new Phrase(df.format(Double.parseDouble(nombre[0]))+virgule[0]+"0",ftextgra));
                	}else
                	c1 = new PdfPCell(new Phrase(df.format(nombre[0])+",00",ftextgra));

    	        
                c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(c1);
            }
            
            document.add(table);
            document.add(saute);
            document.add(saute);
            
            Paragraph pie=new Paragraph();
            
            
            if(calcule.getDisponibilite()!=null){
            	if(!calcule.getDisponibilite().equals("")){
		            pie.add(new Paragraph("Disponibilité",forange));
		            pie.add(new Paragraph(calcule.getDisponibilite(),ftext));
            	}
            }
            if(calcule.getValiditeoffre()!=null){
            	if(!calcule.getValiditeoffre().equals("")){
            	pie.add(new Paragraph("Validité de l'offre",forange));
	            pie.add(new Paragraph(calcule.getValiditeoffre(),ftext));
            	}

            }
            if(calcule.getModalitepaiment()!=null){
            	if(!calcule.getModalitepaiment().equals("")){
	            	   pie.add(new Paragraph("Modalité de paiement",forange));
	   	            pie.add(new Paragraph(calcule.getModalitepaiment(),ftext));
            	}
            }
            pie.add(saute);

            pie.add(new Phrase("Nous restons à votre entière disposition pour toute information complémentaire.\nDans l’attente, veuillez accepter, "+calcule.getCivilite()+", nos cordiales salutations",ftext));
            pie.setIndentationLeft(53);
            document.add(pie);
            
            document.addAuthor("IMS Technology");
            document.addTitle("Offre");
            document.addSubject("Offre N°"+calcule.getRef());
            document.addCreator("RABEH");
            document.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (DocumentException | IOException de) {
            System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF");
            document.close();
            FacesContext.getCurrentInstance().responseComplete();
        }
      }
    
    public void imprimer(){
    String dest="D:/IMS.pdf";
    File file = new File(dest);
    file.getParentFile().mkdirs();
    try { 	
    	createPdf(dest);
    	createPdfImage();
    	 
    	
    } catch (IOException | DocumentException e) {
    	e.printStackTrace();
    }

    }
    
    public void imprimerfont(){
    	String dest="D:/IMS.pdf";
        File file = new File(dest);
        file.getParentFile().mkdirs();
        try { 	
        	createPdf(dest);
        	createPdfImagefont();
        	 
        	
        } catch (IOException | DocumentException e) {
        	e.printStackTrace();
        }

        }
    
    public void imprimersanref(){
    	String dest="D:/IMS.pdf";
        File file = new File(dest);
        file.getParentFile().mkdirs();
        try { 	
        	createPdfsansref(dest);
        	createPdfImagefont();
        	 
        	
        } catch (IOException | DocumentException e) {
        	e.printStackTrace();
        }

        }	

    public void createPdfImage() throws FileNotFoundException, DocumentException, IOException{

    PdfReader reader;			
    reader = new PdfReader("D:/IMS.pdf");

    int n = reader.getNumberOfPages(); 
    HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
    PdfStamper stamp = new PdfStamper(reader,response.getOutputStream()); 
    	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
    int i = 1; 
    //com.itextpdf.text.pdf.PdfContentByte under; 
    com.itextpdf.text.pdf.PdfContentByte over; 
    BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI, BaseFont.EMBEDDED); 


    for(i=1;i<=n;i++){ 
    over = stamp.getOverContent(i); 
    over.beginText(); 
    over.setFontAndSize(bf, 18);
    over.endText(); 
    } 
    stamp.close();	

    }
    
    public void createPdfImagefont() throws FileNotFoundException, DocumentException, IOException{

        PdfReader reader;			
        reader = new PdfReader("D:/IMS.pdf");

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
    	if(calcules==null){
    		System.out.println("----------GET OFFRES----------------");
    		calcules=manager.getObject();
    		System.out.println("----------GET OFFRES-SUCCESS---------------");
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

	public double getPu() {
		return pu;
	}

	public void setPu(double pu) {
		this.pu = pu;
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

	public void setMarge(double marge) {
		this.marge = marge;
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
    	String nombre=String.format("%03d",lastnumbre);
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

	public int getIdcatarticle() {
		return idcatarticle;
	}

	public void setIdcatarticle(int idcatarticle) {
		this.idcatarticle = idcatarticle;
	}

	public ModelService<CategorieArticle> getManagercatarticle() {
		return managercatarticle;
	}

	public void setManagercatarticle(
			ModelService<CategorieArticle> managercatarticle) {
		this.managercatarticle = managercatarticle;
	}

	public int getIdtypepaiement() {
		return idtypepaiement;
	}

	public void setIdtypepaiement(int idtypepaiement) {
		this.idtypepaiement = idtypepaiement;
	}

	public boolean isParjour() {
		return parjour;
	}

	public void setParjour(boolean parjour) {
		this.parjour = parjour;
	}

	public ModelService<TypePaiement> getManagerTypePaiement() {
		return managerTypePaiement;
	}

	public void setManagerTypePaiement(
			ModelService<TypePaiement> managerTypePaiement) {
		this.managerTypePaiement = managerTypePaiement;
	}
	
	public double getMarge() {
		return marge;
	}

	public List<Offre> getOffrecontrats() {
		offrecontrats=manager.getByNames("contrat");
		offrecontrats.addAll(manager.getByNames("contrat location"));
		return offrecontrats;
	}

	public void setOffrecontrats(List<Offre> offrecontrats) {
		this.offrecontrats = offrecontrats;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public StreamedContent getContent() {
		try {
			

		try {
			if(!cotation.getChemin().equals(""))
				content = new DefaultStreamedContent(new FileInputStream(new File("D:\\cotation\\"+cotation.getChemin())),"application/xls",cotation.getChemin());
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

	public boolean isCheckmarge() {
		return checkmarge;
	}

	public void setCheckmarge(boolean checkmarge) {
		this.checkmarge = checkmarge;
	}

	public String getIdarticleligne() {
		return idarticleligne;
	}

	public void setIdarticleligne(String idarticleligne) {
		this.idarticleligne = idarticleligne;
	}

	public String getTempodesignation() {
		return tempodesignation;
	}

	public void setTempodesignation(String tempodesignation) {
		this.tempodesignation = tempodesignation;
	}

	public String getTempoqte() {
		return tempoqte;
	}

	public void setTempoqte(String tempoqte) {
		this.tempoqte = tempoqte;
	}

	public String getTempopu() {
		return tempopu;
	}

	public void setTempopu(String tempopu) {
		this.tempopu = tempopu;
	}

	public String getTempomarge() {
		return tempomarge;
	}

	public void setTempomarge(String tempomarge) {
		this.tempomarge = tempomarge;
	}

	public ModelService<CotationOffre> getManagerCotation() {
		return managerCotation;
	}

	public void setManagerCotation(ModelService<CotationOffre> managerCotation) {
		this.managerCotation = managerCotation;
	}

	public List<CotationOffre> getCotations() {
		return cotations;
	}

	public void setCotations(List<CotationOffre> cotations) {
		this.cotations = cotations;
	}

	public CotationOffre getCotation() {
		return cotation;
	}

	public void setCotation(CotationOffre cotation) {
		this.cotation = cotation;
	}

	public int getIdrubrique() {
		return idrubrique;
	}

	public void setIdrubrique(int idrubrique) {
		this.idrubrique = idrubrique;
	}

	public ModelService<Rubrique> getRubriquemanager() {
		return rubriquemanager;
	}

	public void setRubriquemanager(ModelService<Rubrique> rubriquemanager) {
		this.rubriquemanager = rubriquemanager;
	}

	public String getCivilite() {
		return civilite;
	}

	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}

	public Offre_Article getTemposupp() {
		return temposupp;
	}

	public void setTemposupp(Offre_Article temposupp) {
		this.temposupp = temposupp;
	}

	public double getPrixmargetotal() {
		prixmargetotal=0;
		if(newregister==true){
			if(lignes!=null){
				for(int i=0;i<lignes.size();i++){
					if(lignes.get(i).isOptionnel()==false)
						prixmargetotal+=lignes.get(i).getPrixmarge();
				}
					
			}
		}else{
			prixmargetotal=calcule.getTotalmarge();
		}
		
		return prixmargetotal;
	}

	public void setPrixmargetotal(double prixmargetotal) {
		this.prixmargetotal = prixmargetotal;
	}

	public double getTaux() {
		return taux;
	}

	public void setTaux(double taux) {
		this.taux = taux;
	}

	public boolean isChecktaux() {
		return checktaux;
	}

	public void setChecktaux(boolean checktaux) {
		this.checktaux = checktaux;
	}

	public double getRetenu() {
		return retenu;
	}

	public void setRetenu(double retenu) {
		this.retenu = retenu;
	}

	public ArrayList<String> getFiltredArticle() {
		return filtredArticle;
	}

	public void setFiltredArticle(ArrayList<String> filtredArticle) {
		this.filtredArticle = filtredArticle;
	}

	public List<Article> getListArticle() {
		return listArticle;
	}

	public void setListArticle(List<Article> listArticle) {
		this.listArticle = listArticle;
	}

    
   public DecimalFormat getDf() {
		return df;
	}

	public void setDf(DecimalFormat df) {
		this.df = df;
	}

	public String getStr_devis() {
		/*
		 if(calcules==null)
			calcules=manager.getObject();
			*/
		str_devis="Devis";
		return str_devis;
	}

	public void setStr_devis(String str_devis) {
		this.str_devis = str_devis;
	}

	public boolean isNewregister() {
		return newregister;
	}

	public void setNewregister(boolean newregister) {
		this.newregister = newregister;
	}

	public int getEmpID() {
		return empID;
	}

	public void setEmpID(int empID) {
		this.empID = empID;
	}

	public int getDeptID() {
		return deptID;
	}

	public void setDeptID(int deptID) {
		this.deptID = deptID;
	}

	public ModelService<Employee> getManageremp() {
		return manageremp;
	}

	public void setManageremp(ModelService<Employee> manageremp) {
		this.manageremp = manageremp;
	}

	public ModelService<Departement> getManagerdept() {
		return managerdept;
	}

	public void setManagerdept(ModelService<Departement> managerdept) {
		this.managerdept = managerdept;
	}

	public double getAppmargetotal() {
		return appmargetotal;
	}

	public void setAppmargetotal(double appmargetotal) {
		this.appmargetotal = appmargetotal;
	}

	public double getMontantv() {
		return montantv;
	}

	public void setMontantv(double montantv) {
		this.montantv = montantv;
	}

	
	public String getOptionel() {
		return optionel;
	}

	
	public void setOptionel(String optionel) {
		this.optionel = optionel;
	}

	public boolean isPuzero() {
		return puzero;
	}

	public void setPuzero(boolean puzero) {
		this.puzero = puzero;
	}

	public String getLabelPu() {
		return labelPu;
	}

	public void setLabelPu(String labelPu) {
		this.labelPu = labelPu;
	}

	public boolean isArtoptionel() {
		return artoptionel;
	}

	public void setArtoptionel(boolean artoptionel) {
		this.artoptionel = artoptionel;
	}

	public String getTempoetat() {
		return tempoetat;
	}

	public void setTempoetat(String tempoetat) {
		this.tempoetat = tempoetat;
	}

	public List<String> getEtats() {
		return etats;
	}

	public List<String> getListoption() {
		return listoption;
	}

	public Date getDatefincontrat() {
		return datefincontrat;
	}

	public void setDatefincontrat(Date datefincontrat) {
		this.datefincontrat = datefincontrat;
	}

	public boolean isContrat() {
		return contrat;
	}

	public void setContrat(boolean contrat) {
		this.contrat = contrat;
	}

	public String getCotationStr() {
		return cotationStr;
	}

	public void setCotationStr(String cotationStr) {
		this.cotationStr = cotationStr;
	}
	
	
    
}
