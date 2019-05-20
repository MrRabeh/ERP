package ims.session.controller;

import ims.Exception.ImsErpException;
import ims.model.dao.SessionIMS;
import ims.model.entities.Article;
import ims.model.entities.CategorieArticle;
import ims.model.entities.Client;
import ims.model.entities.CoefficientFacture;
import ims.model.entities.CoordonneeBancaire;
import ims.model.entities.Departement;
import ims.model.entities.Facture;
import ims.model.entities.Facture_Article;
import ims.model.entities.Offre;
import ims.model.entities.Offre_Article;
import ims.model.entities.TypeFacture;
import ims.model.entities.TypePaiement;
import ims.model.entities.years;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
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

@ManagedBean
@SessionScoped
public class FactureContratController implements Serializable {
	   /**
		* creator RABEH TARIK
	    **/
		private static final long serialVersionUID = 1L;
		
		
		 @ManagedProperty(value = "#{managerDataBase}")
		 private ManagerDataBase managerApplication;
		 
		 
		public ManagerDataBase getManagerApplication() {
			return managerApplication;
		}


	    @ManagedProperty(value="#{DepartementManager}")
	    private ModelService<Departement> managerdept;
	    
		public void setManagerApplication(ManagerDataBase managerApplication) {
			this.managerApplication = managerApplication;
		}
	
		@ManagedProperty(value="#{FactureManager}")
		private ModelService<Facture> manager;
	
		@ManagedProperty(value="#{OffreArticleManager}")
	 	private ModelService<Offre_Article> managerarticle;
	 	
	 	@ManagedProperty(value="#{ArticleManager}")
	 	private ModelService<Article> managerObjectArticle;
	    
	 	@ManagedProperty(value="#{OffreManager}")
	    private ModelService<Offre> manageroffre;
	 	
	 	@ManagedProperty(value="#{TypefactManager}")
	 	private ModelService<TypeFacture> managerTypefacture;
	   
	    @ManagedProperty(value="#{FactArticleManage}")
	    private ModelService<Facture_Article> managerFactArticle;
	    
	    @ManagedProperty(value="#{ClientManager}")
	    private ModelService<Client> managerclient;
	 	
	    @ManagedProperty(value="#{JDBCManager}")
	    private ModelServiceJDBC managerjdbc;
	    
	    @ManagedProperty(value="#{CoordonneeBanqueManager}")
		private ModelService<CoordonneeBancaire> managerbanque;
	    
		@ManagedProperty(value="#{CategorieArticleManager}")
	    private ModelService<CategorieArticle> managercatArticle;
		
	    @ManagedProperty(value="#{TypepaiementManager}")
	    private ModelService<TypePaiement> managerTypePaiement;

	    private int idoffre=1;
	    private int idbanque;
	    private int idclient;
	    private int idtypepaiement;
	 	//facture contrat
	    private int idarticle;
	    private double coefficient=1;
	    private double montantglobal=0;
	    private double mensualite=0;
	    private double TVA=0;
	    private double pourcentage=20;
	    private double totalHT=0;
	    private double totalTTC=0;
	    private double coefacture=1.0f;
	    private String designation;
	    private int idcatarticle;
	    private List<Article> articles=new ArrayList<Article>();
	    private List<Facture_Article> facturearticles=new ArrayList<Facture_Article>();
	    private Facture_Article articlesupp=new Facture_Article();
	    private Facture_Article facturearticle;
	    private List<Offre_Article> offre_article=new ArrayList<Offre_Article>();
	    
	    private int deptID;
	    
	    //**************************facture*********************************************//
	    private String numfacture;
	    private String description;
	    private String nbc;
	    private String modereg;
	 	private Facture facture=new Facture();
	 	private List<Facture> factures;
	 	private List<Facture> filtredFactures;
	 	private String chaincoeff;
	 	private List<CoefficientFacture> coeffsacts=new ArrayList<CoefficientFacture>();
	 	private List<Offre> offres;
	 	private List<Offre> facturesoffre;
	 	private List<Offre> offresmodif=new ArrayList<Offre>();
		private List<String> idoffres=new ArrayList<String>() ;
		private List<String> idoffresadd=new ArrayList<String>() ;
		private List<Offre> offresadd=new ArrayList<Offre>();
		private List<Offre> tempos=new ArrayList<Offre>();
		
	    private List<Offre_Article> offrearticles=new ArrayList<Offre_Article>();
		private Date datefacture;
		private Date dateechance;
		private Date dateechancep;
		
		//----------------------
		private String str_facture;
	   	
		public FactureContratController() {
			super();
		}
		
		@PostConstruct
		public void init(){
			try {
				offres=manageroffre.getObject();
				tempos=manageroffre.getObject();
				offresadd=new ArrayList<Offre>();
				articles=new ArrayList<Article>();
				offrearticles=managerarticle.getObjects(idoffre);
				boolean trouve=false;
				for(int i=0;i<offrearticles.size();i++)
					{
						if(trouve==false){
							if(offrearticles.get(i).getArticle()!=null){
								trouve=true;
								montantglobal=offrearticles.get(i).getPu_calculer();
							}
						}
						articles.add(managerObjectArticle.getObject(offrearticles.get(i).getArticle().getIdArticle()));
					}
				System.out.println("------------- INIT FAC CONTRAT -----------");
				factures=managerApplication.getFacturescontrat();
				System.out.println("-----------FIN INIT FAC CONTRAT -----------");
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage("Exception INIT");
	   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		
		public String getStr_facture() {
				str_facture="Facture Contrat";
			return str_facture;
		}

		public void setStr_facture(String str_facture) {
			this.str_facture = str_facture;
		}

		public List<String> completerCategorie(String query){
			try {
		     List<String>	filtredcat=new ArrayList<String>();
		    	try {
		    		 List<CategorieArticle> listCategorie=managercatArticle.getObject();
					
					 for (int i = 0; i < listCategorie.size(); i++) {
				            CategorieArticle skin = listCategorie.get(i);
				            if(skin.getCategorie().toLowerCase().startsWith(query.toLowerCase()) && skin.getCategorie()!=null) {
				            	filtredcat.add(skin.getCategorie());
				            }
				        }
					
				} catch (Exception e) {
					e.getStackTrace();
					filtredcat=null;
				}
		    	return filtredcat;
				} catch (Exception e) {
					return null;
				}
		}
		
		public List<String> completercoeff(String query){
			try {
		     List<String>	filtredcat=new ArrayList<String>();
		    	try {
					
					 for (int i = 0; i < coeffsacts.size(); i++) {
				            String skin = coeffsacts.get(i).getCoefficient();
				            if(skin.toLowerCase().startsWith(query.toLowerCase()) && skin!=null) {
				            	filtredcat.add(skin);
				            }
				        }
					
				} catch (Exception e) {
					e.getStackTrace();
					filtredcat=null;
				}
		    	return filtredcat;
				} catch (Exception e) {
					return null;
				}
		}
		
		
		
		public void updatecell(){
			 FacesMessage msg;
			 try {
				 
				//facturearticle.setCategorieArticle(managercatArticle.getByName(facturearticle.getCategorieArticle().getCategorie()));
			System.out.println(facturearticle.getLab());
			System.out.println(facturearticle.getCoefficient());
   			//coefficient=0.8888888888888889f;
   			facturearticle.setCoefficient(coefficient);
				System.out.println("conversion ..complete");
				 facturearticle.setMensualite(facturearticle.getMontantglobal()*facturearticle.getCoefficient());
				 facturearticle.setPt(facturearticle.getMensualite()*facturearticle.getQte());
				facturearticle.setPrixmarge(facturearticle.getMensualite());
				managerFactArticle.updateObject(facturearticle);
				managerjdbc.updateprixfacture(facture);
				facture=manager.getObject(facture.getId());
				 
				
				 msg = new FacesMessage("Article est Modifier Avec Success","");
					FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (Exception e) {
				 msg = new FacesMessage("Exception",""+e.getMessage());
					FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		
		public void delete(){;
			managerApplication.setFacturescontrat(null);
			  FacesMessage msg;
			  try {
				boolean op=managerjdbc.deleteFacture(facture);
				if(op==true)
				 msg = new FacesMessage("Facture Supprimer Avec Success");
				else
					 msg = new FacesMessage("Probleme de Suppression");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				SimpleDateFormat dt = new SimpleDateFormat("yyyy"); 
				System.out.println("GET YEAR");
	        	years y=managerjdbc.getYears(Integer.parseInt(dt.format(new Date())));
	        	System.out.println("ID YEAR => "+y.getId());
	        	 System.out.println("prepare updateObjectif");
	        	managerjdbc.updateObjectif(y.getId());
	           System.out.println("updateObjectif");
			} catch (Exception e) {
				 msg = new FacesMessage("Exception","contacter l'administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	
		public void actualiser(){
		    	managerApplication.setFacturescontrat(null);
		    }
		
		public String annuler(){
			try {
				managerApplication.setFacturescontrat(null);
				managerjdbc.Annuler(facture);
				factures=managerApplication.getFacturescontrat();
				FacesMessage msg = new FacesMessage("la Facture Contrat est annuler");
	   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
	   		 return "FactureContrat?faces-redirect=false";
			} catch (Exception e) {
				return "FactureContrat?faces-redirect=false";
			}

		}
		
		public String prepareList(){
			try {
				init();
				vider();
				facturearticles=new ArrayList<Facture_Article>();
				return "FactureContrat?faces-redirect=true";
			} catch (Exception e) {
				return "FactureContrat?faces-redirect=true";
			}

		}
		
	   	public String prepareView(){
	   		try {
	   			managerApplication.setFacturescontrat(null);
		   		facture=manager.getObject(facture.getId());
		   		idclient=facture.getClient().getIdclient();
		   		idoffre=facture.getListoffres().get(0).getId();
		   		facturearticles=managerFactArticle.getObjects(facture.getId());
		   		montantglobal=0.0f;
		   		idtypepaiement=facture.getTypepaiement().getId();
		   		if(facture.getBu()!=null)
		   			deptID=facture.getBu().getId();
		   		else
		   			deptID=0;
		    	return "ViewFactureContrat?faces-redirect=true";
			} catch (Exception e) {
				return "FactureContrat?faces-redirect=true";
			}

	    } 
	   	
		public String prepareCreate(){
			try {
				managerApplication.setFacturescontrat(null);
				idtypepaiement=0;
				idclient=0;
				idoffre=0;
				facture=new Facture();
				montantglobal=0;
				deptID=0;
				Calendar cal = Calendar.getInstance();
		    	datefacture=cal.getTime();
		    	facturearticles=new ArrayList<Facture_Article>();
				return "insertcontrat?faces-redirect=true";
			} catch (Exception e) {
				return "FactureContrat?faces-redirect=true";
			}

		}
		
		public String annuleropfacture(){
			try {
				return "FactureContrat?faces-redirect=true";
			} catch (Exception e) {
				return "FactureContrat?faces-redirect=true";
			}
		}
		
		public void creerfactureContrat(){
			managerApplication.setFacturescontrat(null);
	   		FacesMessage msg;
    		DecimalFormat df = new DecimalFormat("0.##");
            df.setMinimumFractionDigits(2);
	        try {
	        	if(idtypepaiement==0 || idclient==0 || idoffre==0 || datefacture==null || dateechance==null){
	        		msg = new FacesMessage("selectionner le vide");
	        		FacesContext.getCurrentInstance().addMessage(null, msg);
	        	}else{
			        	if(managerjdbc.getexsitefacture(numfacture)==0){
								        	facture.setBanque(managerbanque.getObject(idbanque));	
								        	facture.setRef(numfacture);
								        	facture.setConstater("Non");
								        	facture.setDatefacture(datefacture);
								        	facture.setDescription(description);
								        	facture.setNumeroBCClient(nbc);
								        	facture.setConditiondepayment(modereg);
								        	facture.setDateechance(dateechance);
								        	facture.setDateechancep(dateechancep);
								        	facture.setDatereglement(dateechance);
								        	facture.setFacturetype(managerTypefacture.getObject(2));
								        	SimpleDateFormat dt = new SimpleDateFormat("yyyy"); 
								        	facture.setYears(managerjdbc.getYears(Integer.parseInt(dt.format(datefacture))));
								        	facture.setClient(managerclient.getObject(idclient));
								        	facture.setActiver(true);
								        	facture.setTypepaiement(managerTypePaiement.getObject(idtypepaiement));
								        	facture.setBu(managerdept.getObject(deptID));
								        	double prixtotalHT=0;
								         	for(int i=0;i<facturearticles.size();i++){
								        		prixtotalHT+=facturearticles.get(i).getMensualite();
								        	}
								         	
								        	facture.setTotalht(Double.parseDouble(df.format(prixtotalHT).replace(',','.')));
								        	facture.setTva(Double.parseDouble(df.format(prixtotalHT*0.2f).replace(',', '.')));
								        	facture.setTotalttc(Double.parseDouble(df.format(prixtotalHT+facture.getTva()).replace(',', '.')));
								        	
								        	double tem=coefacture;
								        	facture.setCoefficientfacture(tem);
								        	facture.setTotalttcpaye(Double.parseDouble(df.format(facture.getTotalttc()).replace(',', '.')));
								        	facture.setMontantrester(facture.getTotalttcpaye());
								        	if(!facture.getTypepaiement().getTypepaiement().equals("MAD")){
								        		facture.setTotalttc(facture.getTotalht());
								        		facture.setTotalttcpaye(facture.getTotalht());
								        		facture.setTva(0);
								        	}
								        	facture.setPrixmarge(facture.getTotalht());
								        	boolean op=manager.insertObject(facture);
								        	
								        	if(op==true){
								        		Offre offre=manageroffre.getObject(idoffre);
								        		managerjdbc.insertOffre_facture(manageroffre.getObject(idoffre),facture);
								        		managerjdbc.contratOffre(offre);
									        	for(int i=0;i<facturearticles.size();i++){
									        		CategorieArticle cat=managercatArticle.getObject(facturearticles.get(i).getCategorieArticle().getIdcategorie());
									        		
									        		Facture_Article t=new Facture_Article(); 		
									        		t.setArticle(facturearticles.get(i).getArticle());
									        		t.setQte(facturearticles.get(i).getQte());
									        		t.setLab(facturearticles.get(i).getLab());
									        		t.setPt(facturearticles.get(i).getPt());
									        		t.setDesignation(facturearticles.get(i).getDesignation());
									        		t.setCoefficient(facturearticles.get(i).getCoefficient());
									        		t.setMontantglobal(facturearticles.get(i).getMontantglobal());
									        		t.setMensualite(facturearticles.get(i).getMensualite());
									        		t.setPrixmarge(facturearticles.get(i).getMensualite());
									        		t.setCategorieArticle(cat);
									        		t.setMarge(0);
									        		t.setFacture(facture);
									        		managerFactArticle.insertObject(t);
									        	}
									        	vider();
									        	facturearticles=null;
									        	facturearticles=new ArrayList<Facture_Article>();
									        		msg = new FacesMessage("Facture est bien créé");
									        		FacesContext.getCurrentInstance().addMessage(null, msg);
									        		vider();
									        		
										        	years y=managerjdbc.getYears(Integer.parseInt(dt.format(new Date())));
										        	
										        	managerjdbc.updateObjectif(y.getId());
										           
								        	}
								        	else{
								        		msg = new FacesMessage("Probleme de creation de facture Contrat","contacter l'Administration");
								        		FacesContext.getCurrentInstance().addMessage(null, msg);
								        	}
			        	}
			        	else{
			        		msg = new FacesMessage("numero Facture existe deja");
			        		FacesContext.getCurrentInstance().addMessage(null, msg);
			        	}
	        	}
	        } catch (Exception e) {
	          msg = new FacesMessage("Exception Facture "+e.getMessage(),"Contacter l'administration");
	          FacesContext.getCurrentInstance().addMessage(null, msg);
	        	}
	   	}
	
	   	public void update() {
	   		managerApplication.setFacturescontrat(null);
	        FacesMessage msg;
    		DecimalFormat df = new DecimalFormat("0.##");
            df.setMinimumFractionDigits(2);
	        try {
	        	
	        	facture.setTotalttcpaye(Double.parseDouble(df.format(facture.getTotalttc()).replace(',', '.')));
	        	facture.setBanque(managerbanque.getObject(idbanque));
	        	facture.setTypepaiement(managerTypePaiement.getObject(idtypepaiement));
	        	facture.setBu(managerdept.getObject(deptID));
	        	int op=managerjdbc.updatefacture(facture);
	        	managerjdbc.updateFacture_Offrecontrat(facture.getId(),idoffre);
	        	Offre offre=manageroffre.getObject(idoffre);
	        	managerjdbc.contratOffre(offre);
	        	if(op!=0){
	        	 	msg = new FacesMessage("Facture est bien Modifier","");
		               FacesContext.getCurrentInstance().addMessage(null, msg);
						SimpleDateFormat dt = new SimpleDateFormat("yyyy"); 
						System.out.println("GET YEAR");
			        	years y=managerjdbc.getYears(Integer.parseInt(dt.format(new Date())));
			        	System.out.println("ID YEAR => "+y.getId());
			        	 System.out.println("prepare updateObjectif");
			        	managerjdbc.updateObjectif(y.getId());
			           System.out.println("updateObjectif");
			       	facture=manager.getObject(facture.getId());
	        	}
	        	else{
	        		msg = new FacesMessage("numero Facture Existe Deja","");
		               FacesContext.getCurrentInstance().addMessage(null, msg);
	        	}
	        	
	        } catch (Exception e) {
	           msg = new FacesMessage("Exception Facture"+e.getMessage(),"Contacter l'administration");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
	   }
		
	   	public void addarttofactmodif(){
	   		managerApplication.setFacturescontrat(null);
		   	  FacesMessage msg;
	    		DecimalFormat df = new DecimalFormat("0.##");
	            df.setMinimumFractionDigits(2);
		   		try {
		   		Article art=managerObjectArticle.getObject(idarticle);
		   		facturearticle=new Facture_Article();
		   		
		   		facturearticle.setQte(1);
		   		facturearticle.setCategorieArticle(managercatArticle.getObject(idcatarticle));
		   		
		   		if(chaincoeff.equals("1/36")){
		   			coefficient=0.0277777777777778f;
		   			facturearticle.setLab("1/36");
		   			}
		   		else if(chaincoeff.equals("1/41")){
		   			coefficient=0.024390243902439f;
		   			facturearticle.setLab("1/41");
		   			}
		   		else if(chaincoeff.equals("1/4"))
		   			{
		   			coefficient=0.25f;
		   			facturearticle.setLab("1/4");
		   			}
		   		else if(chaincoeff.equals("1/12")){
	   			coefficient=0.0833333333333333f;
	   			facturearticle.setLab("1/12");
	   			}
		   		else if(chaincoeff.equals("1/2")){
	   			coefficient=0.5f;
	   			facturearticle.setLab("1/2");
	   			}
		   		else if(chaincoeff.equals("3/12")){
		   			coefficient=0.25f;
		   			facturearticle.setLab("3/12");
		   			}
		   		else if(chaincoeff.equals("56/365")){
		   			coefficient=0.1534246575342466f;
		   			facturearticle.setLab("56/365");
		   			}
		   		else if(chaincoeff.equals("65/365")){
		   			coefficient=0.1780821917808219f;
		   			facturearticle.setLab("65/365");
		   			}
		   		else if(chaincoeff.equals("66/365")){
		   			coefficient=0.1808219178082192f;
		   			facturearticle.setLab("66/365");
		   			}
		   	
		   		else if(chaincoeff.equals("67/365")){
		   			coefficient=0.1835616438356164f;
		   			facturearticle.setLab("67/365");
		   			}
		   		else if(chaincoeff.equals("68/365")){
	   			coefficient=0.1863013698630137f;
	   			facturearticle.setLab("68/365");
	   			}
		   		else if(chaincoeff.equals("79/90")){
		   			coefficient=0.8777777777777778f;
		   			facturearticle.setLab("79/90");
		   			}
		   		else if(chaincoeff.equals("80/91")){
		   			coefficient=0.8791208791208791f;
		   			facturearticle.setLab("80/91");
		   			}
		   		else if(chaincoeff.equals("8/9")){
		   			coefficient=0.8888888888888889;
		   			facturearticle.setLab("8/9");
		   			}
		   		else if(chaincoeff.equals("2/12")){
		   			coefficient=0.1666666666666667;
		   			facturearticle.setLab("2/12");
		   			}
		   		else if(chaincoeff.equals("90/90")){
		   			coefficient=1;
		   			facturearticle.setLab("90/90");
		   			}
		   		else if(chaincoeff.equals("91/365")){
		   			coefficient=0.2493150684931507f;
		   			facturearticle.setLab("91/365");
		   			}
		   		else if(chaincoeff.equals("92/365")){
		   			coefficient=0.2520547945205479f;
		   			facturearticle.setLab("92/365");
		   			}
		   		else{
	   			coefficient=1;
	   			facturearticle.setLab("1");
	   			}
		   		
		   		
		   		facturearticle.setArticle(art);
		   		
		   		if(designation.equals("")){
		   			facturearticle.setDesignation(art.getDesignation());
		   		}
		   		else{
		   			facturearticle.setDesignation(designation);
		   		}
		   		
		   		System.err.print("coeff lab==>"+facturearticle.getLab());
		   		mensualite=coefficient*montantglobal;
		   		facturearticle.setCoefficient(coefficient);
		   		facturearticle.setMensualite(mensualite);
		   		facturearticle.setMontantglobal(montantglobal);
		   		facturearticle.setPt(montantglobal);
		   		facturearticle.setPrixmarge(facturearticle.getMensualite());
		   		facturearticles.add(facturearticle);
		   		double tht=facture.getTotalht()+mensualite;
		   		facture.setTotalht(Double.parseDouble(df.format(tht).replace(',', '.')));
		   		double tempo=(facture.getTotalht()*pourcentage)/100;
		   		facture.setTva(Double.parseDouble(df.format(tempo).replace(',', '.')));
		   		double ttc=facture.getTotalht()+facture.getTva();
		   		facture.setTotalttc(Double.parseDouble(df.format(ttc).replace(',', '.')));
		   		
		   		System.out.println("PREPARE ----- CONTROLL");
		   		int idtempo=managerjdbc.AddArticleToFacturecontrat(facture, facturearticle);
		   		facturearticle.setId(idtempo);
		   		managerjdbc.updateprixfacture(facture);
		       
		   		msg = new FacesMessage("Article est bien créé");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
		       msg = new FacesMessage("Facture est Modifier");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
				SimpleDateFormat dt = new SimpleDateFormat("yyyy"); 
				System.out.println("GET YEAR");
	        	years y=managerjdbc.getYears(Integer.parseInt(dt.format(new Date())));
	        	System.out.println("ID YEAR => "+y.getId());
	        	 System.out.println("prepare updateObjectif");
	        	managerjdbc.updateObjectif(y.getId());
	           System.out.println("updateObjectif");
				} catch (Exception e) {
			           msg = new FacesMessage("Probleme d'ajout contacter l'administration","Probleme d'ajout contacter l'administration");
				       FacesContext.getCurrentInstance().addMessage(null, msg);
				}
		   	}
	   	
	   	public void addarttofact(){
	   	  FacesMessage msg;
	   	  System.out.println("ADD ARTICLE");
  		DecimalFormat df = new DecimalFormat("0.##");
        df.setMinimumFractionDigits(2);
	   		try {
	   			System.out.println("GET ARTICLE");
	   		Article art=managerObjectArticle.getObject(idarticle);
	   		facturearticle=new Facture_Article();
	   		System.out.println("ADD 2");
	   		facturearticle.setQte(1);
	   		System.out.println("GET CAT");
	   		CategorieArticle cat=managercatArticle.getObject(idcatarticle);
	   		facturearticle.setCategorieArticle(cat);
	   		System.out.println("ADD 3");
	   		if(chaincoeff.equals("1/36"))
	   			{
	   			coefficient=0.0277777777777778f;
	   			facturearticle.setLab("1/36");
	   			}
	   		else if(chaincoeff.equals("1/41"))
	   			{
	   			coefficient=0.024390243902439f;
	   			facturearticle.setLab("1/41");
	   			}
	   		else if(chaincoeff.equals("1/4"))
	   			{
	   			coefficient=0.25f;
	   			facturearticle.setLab("1/4");
	   			}
	   		else if(chaincoeff.equals("1/12"))
   			{
   			coefficient=0.0833333333333333f;
   			facturearticle.setLab("1/12");
   			}else if(chaincoeff.equals("1/2")){
	   			coefficient=0.5f;
	   			facturearticle.setLab("1/2");
	   			}
   	 		else if(chaincoeff.equals("3/12")){
	   			coefficient=0.25f;
	   			facturearticle.setLab("3/12");
	   			}
	   		else if(chaincoeff.equals("65/365")){
	   			coefficient=0.1780821917808219f;
	   			facturearticle.setLab("65/365");
	   			}
	   		else if(chaincoeff.equals("66/365")){
	   			coefficient=0.1808219178082192f;
	   			facturearticle.setLab("66/365");
	   			}
	   		
	   		else if(chaincoeff.equals("67/365")){
	   			System.out.println("67/365");
	   			coefficient=0.1861111f;
	   			facturearticle.setLab("67/365");
	   			}
	   		else if(chaincoeff.equals("68/365")){
   			coefficient=0.1863013698630137f;
   			facturearticle.setLab("68/365");
   			}
	   		else if(chaincoeff.equals("79/90")){
	   			coefficient=0.8777777777777778f;
	   			facturearticle.setLab("79/90");
	   			}
	   		else if(chaincoeff.equals("80/91")){
	   			coefficient=0.8791208791208791f;
	   			facturearticle.setLab("80/91");
	   			}
	   		else if(chaincoeff.equals("8/9")){
	   			coefficient=0.8888888888888889;
	   			facturearticle.setLab("08/9");
	   			}
	   		else if(chaincoeff.equals("2/12")){
	   			coefficient=0.1666666666666667;
	   			facturearticle.setLab("02/12");
	   			}
	   		else if(chaincoeff.equals("90/90")){
	   			coefficient=1;
	   			facturearticle.setLab("90/90");
	   			}
	   		else if(chaincoeff.equals("90/365")){
	   			coefficient=0.2465753424657534f;
	   			facturearticle.setLab("90/365");
	   			}
	   		else if(chaincoeff.equals("91/365")){
	   			coefficient=0.2493150684931507f;
	   			facturearticle.setLab("91/365");
	   			}
	   		else if(chaincoeff.equals("92/365")){
	   			coefficient=0.2520547945205479f;
	   			facturearticle.setLab("92/365");
	   			}
	   		else
   			{
   			coefficient=1;
   			facturearticle.setLab("1");
   			}
	   		System.out.println("TOTO TEST");
	   		facturearticle.setArticle(art);
	   		if(designation.equals("")){
	   			
	   			facturearticle.setDesignation(art.getDesignation());
	   		}
	   		else{
	   			
	   			facturearticle.setDesignation(designation);
	   		}
	   		
	   		mensualite=coefficient*montantglobal;
	   		facturearticle.setCoefficient(coefficient);
	   		
	   		facturearticle.setMensualite(Double.parseDouble(df.format(mensualite).replace(',', '.')));
	   		facturearticle.setPrixmarge(Double.parseDouble(df.format(mensualite).replace(',', '.')));
	   		facturearticle.setMontantglobal(montantglobal);
	   		
	   		facturearticle.setPt(montantglobal);
	   		
	   		
	   		facturearticles.add(facturearticle);
			totalHT+=mensualite;
			
			totalHT=Double.parseDouble(df.format(totalHT).replace(',', '.'));
			
	   		TVA=(totalHT*pourcentage)/100;
			TVA=Double.parseDouble(df.format(TVA).replace(',', '.'));
			
	   		totalTTC=totalHT+TVA;
	   		totalTTC=Double.parseDouble(df.format(totalTTC).replace(',', '.'));
	   		
	   		designation="";
	           msg = new FacesMessage("Article est bien créé");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (NullPointerException e) {
		           msg = new FacesMessage("ERROR AJOUT","ERROR AJOUT");
			       FacesContext.getCurrentInstance().addMessage(null, msg);
			}
   			designation="";
   			montantglobal=0;
   			facturearticle=null;
	   	}
	   	
	   	public String getDesignation() {
			return designation;
		}

		public void setDesignation(String designation) {
			this.designation = designation;
		}

		public void DeleteArticle(){
		   	  FacesMessage msg;
		  		DecimalFormat df = new DecimalFormat("0.##");
		        df.setMinimumFractionDigits(2);
		   		try {
		   			facturearticles.remove(articlesupp);
		   		totalHT-=articlesupp.getMensualite();
		   		
		   		TVA=(totalHT*pourcentage)/100;
		   		
		   		TVA=Double.parseDouble(df.format(TVA).replace(',','.'));
		   		
		   		totalTTC=Double.parseDouble(df.format(totalHT+TVA).replace(',', '.')) ;
		           msg = new FacesMessage("Article est supprimer avec success","Article est supprimer avec success");
		       FacesContext.getCurrentInstance().addMessage(null, msg);
				} catch (Exception e) {
			           msg = new FacesMessage("Exception","ERROR SUPPRESSION");
				       FacesContext.getCurrentInstance().addMessage(null, msg);
				}
	   	}
	   	
		public void DeleteArticlemodif(){
			try {
		  		DecimalFormat df = new DecimalFormat("0.##");
		        df.setMinimumFractionDigits(2);
				boolean op=true;
				op=managerFactArticle.deleteObject(facturearticle);
				op=true;
				if(op==true){
					facturearticles.remove(facturearticle);
					double tempomensualite=facturearticle.getMensualite();
					
					double ancienHT=facture.getTotalht();
					facture.setTotalht(ancienHT-tempomensualite);
					
					facture.setTva(Double.parseDouble(df.format(facture.getTotalht()*0.2f).replace(',', '.')));
					
					facture.setTotalttc(Double.parseDouble(df.format(facture.getTva()+facture.getTotalht()).replace(',', '.')));
					
					facture.setTotalttcpaye(Double.parseDouble(df.format(facture.getTotalttc()*facture.getCoefficientfacture()).replace(',', '.')));
					

					

						managerjdbc.updateprixfacture(facture);
						FacesMessage msg = new FacesMessage("Article est Supprimer");
						FacesContext.getCurrentInstance().addMessage(null, msg);
						msg = new FacesMessage("Facture est Modifier");
						FacesContext.getCurrentInstance().addMessage(null, msg);
						SimpleDateFormat dt = new SimpleDateFormat("yyyy"); 
						System.out.println("GET YEAR");
			        	years y=managerjdbc.getYears(Integer.parseInt(dt.format(new Date())));
			        	System.out.println("ID YEAR => "+y.getId());
			        	 System.out.println("prepare updateObjectif");
			        	managerjdbc.updateObjectif(y.getId());
			           System.out.println("updateObjectif");
				}
				else{
					FacesMessage msg = new FacesMessage("Article n'est Supprimer repeter l'operation","contacter l'administration");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage("Probleme de suppression de l'article","Contacter Administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		
		public void viderarttofact(){
	   		coefficient=0;
	   		mensualite=0;
	   	}
	   	
		public void createPdf(String dest) throws ImsErpException{

			   SimpleDateFormat format=new SimpleDateFormat("dd/MM/yy");
			   String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
		   		Paragraph saute=new Paragraph("\n");
		   		
		    	 float left = 36;
		         float right = 36;
		         float top = 60;
		         float bottom = 100;
		         
		       Document document = new Document(PageSize.A4,left, right, top, bottom);
		       HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		       try {
		    	   PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
		           response.setContentType("application/pdf");
		           response.setHeader("Content-Disposition", "inline; filename="+facture.getRef()+".pdf");
		           document.open();
		          
		           // document.setMargins(36, 36, 108, 180);
		           
		           document.setMargins(left, right,108, bottom);
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
			           
			           c1 = new PdfPCell(new Phrase("Qté",ftextgra));
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
		           document.addSubject("Facture N°"+facture.getRef());
		           document.addCreator("RABEH");
		           document.close();
		          
		           SessionIMS.session.clear();
		           SessionIMS.session.close();
		           FacesContext.getCurrentInstance().responseComplete();
		       } catch (DocumentException | IOException de) {
		           System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF");
		           document.close();
		           FacesContext.getCurrentInstance().responseComplete();
		       }
		   }
	   	
	    public void imprimer() throws ImsErpException{
	        
	    	String dest="D:/IMS.pdf";
	        File file = new File(dest);
	        file.getParentFile().mkdirs();
	        try { 	
					createPdf(dest);
	        	createPdfImage();
	        	 
	        	
	        } catch (IOException | DocumentException | NullPointerException e) {
	        	// TODO Auto-generated catch block
	        	e.printStackTrace();
	   		 FacesMessage msg;
	         msg = new FacesMessage("Exception dans l'application Contacter Administration","");
	         FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
	        }	
	    
	    public void imprimerfond() throws ImsErpException{
	        String dest="D:/IMS.pdf";
	        File file = new File(dest);
	        file.getParentFile().mkdirs();
	        try { 	
	        	try {
					createPdf(dest);
				} catch (ImsErpException e) {
					// TODO Auto-generated catch block
					e.AfficherException();
				}
	        	createPdfImageavecfond();
	        	 
	        	
	        } catch (IOException | DocumentException e) {
	        	// TODO Auto-generated catch block
	        	e.printStackTrace();
	        }

	        }

	    public void createPdfImage() throws ImsErpException,FileNotFoundException, DocumentException, IOException{

	        PdfReader reader;			
	        reader = new PdfReader("D:/IMS.pdf");

	        int n = reader.getNumberOfPages(); 
	        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	        PdfStamper stamp = new PdfStamper(reader,response.getOutputStream()); 
	        	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
	        int i = 1; 
	        com.itextpdf.text.pdf.PdfContentByte under; 
	        com.itextpdf.text.pdf.PdfContentByte over; 
	       /*
	        Image img = Image.getInstance(webroot+"\\fondfacture.jpg"); 
	       
	        img.scaleAbsoluteHeight(PageSize.A4.getHeight());
	        img.scaleAbsoluteWidth(PageSize.A4.getWidth());
	        img.setAbsolutePosition(0, 0);
	         */
	        BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI, BaseFont.EMBEDDED); 


	        for(i=1;i<=n;i++){
	        under = stamp.getUnderContent(i); 
	        over = stamp.getOverContent(i); 
	        over.beginText(); 
	        over.setFontAndSize(bf, 18);
	        over.endText(); 
	        } 
	        stamp.close();	

	        }
	    
	    public void createPdfImageavecfond() throws FileNotFoundException, DocumentException, IOException{

	        PdfReader reader;			
	        reader = new PdfReader("D:/IMS.pdf");

	        int n = reader.getNumberOfPages(); 
	        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	        PdfStamper stamp = new PdfStamper(reader,response.getOutputStream()); 
	        	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
	        int i = 1; 
	        com.itextpdf.text.pdf.PdfContentByte under; 
	        com.itextpdf.text.pdf.PdfContentByte over; 
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
		
	   	public void onRowEdit(RowEditEvent event) {
	        FacesMessage msg;
	        try {
	        	
	           manager.updateObject(((Facture) event.getObject()));
	            msg = new FacesMessage("Bon Commande est bien Modifier",""+((Facture) event.getObject()).getRef());
	               FacesContext.getCurrentInstance().addMessage(null, msg);
	        } catch (Exception e) {
	           msg = new FacesMessage("ERROR commande",""+((Facture) event.getObject()).getRef());
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
	       
	   }
	    
	   	public void onRowCancel(RowEditEvent event) {
		   
	      FacesMessage msg = new FacesMessage("Modification ete Annuler",""+((Facture) event.getObject()).getRef());
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	   }
	   
	   	public ModelService<Facture> getManager() {
		return manager;
	}
	   
	   	public void setManager(ModelService<Facture> manager) {
		this.manager = manager;
	   }
	   
	   	public Facture getFacture() {
		return facture;
	   }
	   
	   	public void setFacture(Facture facture) {
		this.facture = facture;
	   }

		public ModelService<Offre> getManageroffre() {
			return manageroffre;
		}
		public void setManageroffre(ModelService<Offre> manageroffre) {
			this.manageroffre = manageroffre;
		}
		public int getIdoffre() {
			return idoffre;
		}
		public void setIdoffre(int idoffre) {
			this.idoffre = idoffre;
		}
		public String getNbc() {
			nbc="contrat";
			return nbc;
		}
		public void setNbc(String nbc) {
			this.nbc = nbc;
		}
		public String getModereg() {
			modereg="30 jours à compter de la date  de réception de la facture";
			return modereg;
		}
		public void setModereg(String modereg) {
			this.modereg = modereg;
		}
	   	
		
		private void vider(){
		   numfacture="";
		   nbc="";
		   modereg="";
		   facturearticles=new ArrayList<Facture_Article>();
				   totalHT=0;
		   		totalTTC=0;
		   			TVA=0;
		   			description="";
		   			designation="";
	   }
		
		/*
		 * Getter and Setter
		 */
		public String getNumfacture() {

		 	Calendar cal = Calendar.getInstance();
	    	String chaine=""+cal.get(Calendar.YEAR);
	    	String[] tab=chaine.split("0");
	    	int lastnumbre=managerjdbc.getLastNumbre("Facture","numero_facture",chaine);
	    	lastnumbre++;
	    	String nombre=String.format("%03d",lastnumbre);
	    	numfacture=tab[1]+"FA"+nombre;
			return numfacture;
		}

		public void setNumfacture(String numfacture) {
			this.numfacture = numfacture;
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

		public List<Facture_Article> getFacturearticles() {
			return facturearticles;
		}

		public void setFacturearticles(List<Facture_Article> facturearticles) {
			this.facturearticles = facturearticles;
		}

		public Facture_Article getFacturearticle() {
			return facturearticle;
		}

		public void setFacturearticle(Facture_Article facturearticle) {
			this.facturearticle = facturearticle;
		}

		public int getIdarticle() {
			return idarticle;
		}

		public void setIdarticle(int idarticle) {
			this.idarticle = idarticle;
		}

		public List<Article> getArticles() {
			return articles;
		}

		public void setArticles(List<Article> articles) {
			this.articles = articles;
		}

	

		public ModelService<Article> getManagerObjectArticle() {
			return managerObjectArticle;
		}

		public void setManagerObjectArticle(ModelService<Article> managerObjectArticle) {
			this.managerObjectArticle = managerObjectArticle;
		}

	
		public void setPourcentage(double pourcentage) {
			this.pourcentage = pourcentage;
		}

		public List<Offre> getOffres() {
			return offres;
		}

		public void setOffres(List<Offre> offres) {
			this.offres = offres;
		}

		public List<Offre> getFacturesoffre() {
			return facturesoffre;
		}

		public void setFacturesoffre(List<Offre> facturesoffre) {
			this.facturesoffre = facturesoffre;
		}

		public List<Facture> getFactures() {
				factures=managerApplication.getFacturescontrat();
			return factures;
		}

		public void setFactures(List<Facture> factures) {
			this.factures = factures;
		}

		public List<Facture> getFiltredFactures() {
			return filtredFactures;
		}

		public void setFiltredFactures(List<Facture> filtredFactures) {
			this.filtredFactures = filtredFactures;
		}

		public List<Offre> getOffresmodif() {
			return offresmodif;
		}

		public void setOffresmodif(List<Offre> offresmodif) {
			this.offresmodif = offresmodif;
		}

		public List<Offre> getOffresadd() {
			return offresadd;
		}

		public void setOffresadd(List<Offre> offresadd) {
			this.offresadd = offresadd;
		}

		public List<String> getIdoffres() {
			return idoffres;
		}

		public void setIdoffres(List<String> idoffres) {
			this.idoffres = idoffres;
		}

		public List<String> getIdoffresadd() {
			return idoffresadd;
		}

		public void setIdoffresadd(List<String> idoffresadd) {
			this.idoffresadd = idoffresadd;
		}

		public List<Offre> getTempos() {
			return tempos;
		}

		public void setTempos(List<Offre> tempos) {
			this.tempos = tempos;
		}

		public ModelServiceJDBC getManagerjdbc() {
			return managerjdbc;
		}

		public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
			this.managerjdbc = managerjdbc;
		}

		public List<Offre_Article> getOffre_article() {
			return offre_article;
		}

		public void setOffre_article(List<Offre_Article> offre_article) {
			this.offre_article = offre_article;
		}

		public ModelService<Offre_Article> getManagerarticle() {
			return managerarticle;
		}

		public void setManagerarticle(ModelService<Offre_Article> managerarticle) {
			this.managerarticle = managerarticle;
		}

		public ModelService<Client> getManagerclient() {
			return managerclient;
		}

		public void setManagerclient(ModelService<Client> managerclient) {
			this.managerclient = managerclient;
		}

		public String getChaincoeff() {
			return chaincoeff;
		}

		public void setChaincoeff(String chaincoeff) {
			this.chaincoeff = chaincoeff;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Date getDatefacture() {
			return datefacture;
		}

		public void setDatefacture(Date datefacture) {
			this.datefacture = datefacture;
		}

		public int getIdclient() {
			return idclient;
		}

		public void setIdclient(int idclient) {
			this.idclient = idclient;
		}

		public void setCoeffsacts(List<CoefficientFacture> coeffsacts) {
			this.coeffsacts = coeffsacts;
		}

		public ModelService<CoordonneeBancaire> getManagerbanque() {
			return managerbanque;
		}

		public void setManagerbanque(ModelService<CoordonneeBancaire> managerbanque) {
			this.managerbanque = managerbanque;
		}

		public int getIdbanque() {
			return idbanque;
		}

		public void setIdbanque(int idbanque) {
			this.idbanque = idbanque;
		}

		public List<Offre_Article> getOffrearticles() {
			return offrearticles;
		}

		public void setOffrearticles(List<Offre_Article> offrearticles) {
			this.offrearticles = offrearticles;
		}
		
		public List<CoefficientFacture> getCoeffsacts() {
			coeffsacts.removeAll(coeffsacts);
			coeffsacts.add(new CoefficientFacture("1"));
			coeffsacts.add(new CoefficientFacture("1/2"));
			coeffsacts.add(new CoefficientFacture("1/4"));
			coeffsacts.add(new CoefficientFacture("1/12"));
			coeffsacts.add(new CoefficientFacture("1/36"));
			coeffsacts.add(new CoefficientFacture("1/41"));
			coeffsacts.add(new CoefficientFacture("2/12"));
			coeffsacts.add(new CoefficientFacture("3/12"));
			coeffsacts.add(new CoefficientFacture("56/365"));
			coeffsacts.add(new CoefficientFacture("65/365"));
			coeffsacts.add(new CoefficientFacture("66/365"));
			coeffsacts.add(new CoefficientFacture("67/365"));
			coeffsacts.add(new CoefficientFacture("68/365"));
			coeffsacts.add(new CoefficientFacture("79/90"));
			coeffsacts.add(new CoefficientFacture("80/91"));
			coeffsacts.add(new CoefficientFacture("8/9"));
			coeffsacts.add(new CoefficientFacture("90/90"));
			coeffsacts.add(new CoefficientFacture("90/365"));
			coeffsacts.add(new CoefficientFacture("91/365"));
			coeffsacts.add(new CoefficientFacture("92/365"));
			
			return coeffsacts;
		}

		public ModelService<CategorieArticle> getManagercatArticle() {
			return managercatArticle;
		}

		public void setManagercatArticle(
				ModelService<CategorieArticle> managercatArticle) {
			this.managercatArticle = managercatArticle;
		}

		public int getIdcatarticle() {
			return idcatarticle;
		}

		public void setIdcatarticle(int idcatarticle) {
			this.idcatarticle = idcatarticle;
		}

		public int getIdtypepaiement() {
			return idtypepaiement;
		}

		public void setIdtypepaiement(int idtypepaiement) {
			this.idtypepaiement = idtypepaiement;
		}

		public ModelService<TypePaiement> getManagerTypePaiement() {
			return managerTypePaiement;
		}

		public void setManagerTypePaiement(
				ModelService<TypePaiement> managerTypePaiement) {
			this.managerTypePaiement = managerTypePaiement;
		}

		public double getCoefficient() {
			return coefficient;
		}

		public void setCoefficient(double coefficient) {
			this.coefficient = coefficient;
		}

		public double getMontantglobal() {
			return montantglobal;
		}

		public void setMontantglobal(double montantglobal) {
			this.montantglobal = montantglobal;
		}

		public double getMensualite() {
			return mensualite;
		}

		public void setMensualite(double mensualite) {
			this.mensualite = mensualite;
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

		public double getCoefacture() {
			return coefacture;
		}

		public void setCoefacture(double coefacture) {
			this.coefacture = coefacture;
		}

		public double getPourcentage() {
			return pourcentage;
		}

		public Date getDateechance() {
			return dateechance;
		}

		public void setDateechance(Date dateechance) {
			this.dateechance = dateechance;
		}

		public Date getDateechancep() {
			return dateechancep;
		}

		public void setDateechancep(Date dateechancep) {
			this.dateechancep = dateechancep;
		}

		public Facture_Article getArticlesupp() {
			return articlesupp;
		}

		public void setArticlesupp(Facture_Article articlesupp) {
			this.articlesupp = articlesupp;
		}

		public int getDeptID() {
			return deptID;
		}

		public void setDeptID(int deptID) {
			this.deptID = deptID;
		}

		public ModelService<Departement> getManagerdept() {
			return managerdept;
		}

		public void setManagerdept(ModelService<Departement> managerdept) {
			this.managerdept = managerdept;
		}
		
		
}
