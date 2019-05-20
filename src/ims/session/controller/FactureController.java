package ims.session.controller;



import ims.Exception.ImsErpException;
import ims.model.entities.Article;
import ims.model.entities.BonLivraison;
import ims.model.entities.CategorieArticle;
import ims.model.entities.Client;
import ims.model.entities.CoordonneeBancaire;
import ims.model.entities.Departement;
import ims.model.entities.Facture;
import ims.model.entities.Facture_Article;
import ims.model.entities.Facture_Livraison;
import ims.model.entities.Offre;
import ims.model.entities.Offre_Article;
import ims.model.entities.TypeFacture;
import ims.model.entities.TypePaiement;
import ims.model.entities.Ville;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DualListModel;

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
public class FactureController implements Serializable {

	   /**
		* creator RABEH TARIK
	    **/
		private static final long serialVersionUID = 1L;
	
		
		 @ManagedProperty(value = "#{managerDataBase}")
		 private ManagerDataBase managerApplication;
		 
	    @ManagedProperty(value="#{DepartementManager}")
	    private ModelService<Departement> managerdept;
	    
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
	    
	    @ManagedProperty(value="#{JDBCManager}")
	    private ModelServiceJDBC managerjdbc;
	    
		@ManagedProperty(value="#{BonLivraisonManager}")
		private ModelService<BonLivraison> managerliv;
	    
		@ManagedProperty(value="#{ClientManager}")
		private ModelService<Client> managerclient;
		
		@ManagedProperty(value="#{CoordonneeBanqueManager}")
		private ModelService<CoordonneeBancaire> managerbanque;
		
		@ManagedProperty(value="#{CategorieArticleManager}")
	    private ModelService<CategorieArticle> managercatArticle;
		
	    @ManagedProperty(value="#{TypepaiementManager}")
	    private ModelService<TypePaiement> managerTypePaiement;
	    
		@ManagedProperty(value="#{BonLivraisonManager}")
		private ModelService<BonLivraison> managerlivraisons;
	
		 
	 	private Facture facture=new Facture();
	 	private List<Facture> factures;
	 	private List<Facture> filtredFactures;

	    private int   idoffre=1;
	    private int   idarticle;
	    private double coefficient;
	    private double montantglobal;
	    private String designation;
	    private double mensualite;
	    private Date  datefacture;
	    private Date dateechance;
	    private Date dateechancep;
	    private Offre offre;
	    //////////////////////////////
	    private double TVA=0;
	    private double totalHT=0;
	    private double totalTTC=0;
	    private double totalTTCpaye=0;
	    private double coefacture=0.0f;
	    
	    //////////
	    private int   idtypefact;
	    private int idbanque;
	    private int idcatarticle;
	    private int deptID;
	    private int idtypepaiement;
	    /////////
	    
	    private double prixmarge=0;
	    private double nvqte;
	    private double mprixmarge;
	    
	    private TypeFacture factureType=new TypeFacture();
	    private List<Article> articles=new ArrayList<Article>();
	    
	    private List<Facture_Article> facturearticles=new ArrayList<Facture_Article>();
	    private List<Facture_Article> facturearticle=new ArrayList<Facture_Article>();
	    private List<Facture_Article> facturarticlessmodif;
	    
	    private List<Offre> offresmodif=new ArrayList<Offre>();
		private List<String> idoffres=new ArrayList<String>() ;
		private List<String> idoffresadd=new ArrayList<String>() ;
		private List<Offre> offres;
		private List<Offre> tempos=new ArrayList<Offre>();
		private List<Offre> offresadd=new ArrayList<Offre>();
		
		private List<Offre_Article> offrearticles=new ArrayList<Offre_Article>();
	    private List<Offre_Article> offre_article=new ArrayList<Offre_Article>();
	    private List<TypeFacture> factureTypes=new ArrayList<TypeFacture>();
	    private String str_facture;
	    private Offre_Article offreart=new Offre_Article();
	    private String numfacture;
	    private String nbc;
	    private double qte;
	    private double pu;
	    private String decription;
	    private String modereg;
	    private int idclient;
	    private boolean checkforfait;
	    private boolean exoneration=false;
	    //-------------------------------------------
	    
		private List<BonLivraison> livraisonsSource;
       


		private List<BonLivraison> livraisonsTarget;
		
		private DualListModel<BonLivraison> livraisons;

    	@ManagedProperty(value="#{FactureBonLivraisonManager}")
    	private ModelService<Facture_Livraison> managerFL;
    	
    	private DecimalFormat df = new DecimalFormat("0.##");
    	
		public FactureController() {
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
				System.out.println("---------- INIT FACTURE CLIENT--------------");
	   			//factures=manager.getByNames(EnumFactureType.Simple.toString());
	   			factures=managerApplication.getFactures();
				System.out.println("----------FIN  INIT FACTURE CLIENT--------------");
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage("Exception contacter Administration ou Redemarrer le serveur");
	   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			
		}
		
		@PreDestroy
		public void destroy(){
		}
		
		public void actualiser(){
			managerApplication.setFactures(null);
		    }
		
		public String annuler(){
			try {
				managerjdbc.Annuler(facture);
				managerApplication.setFacturemov(null);
				FacesMessage msg = new FacesMessage("la Facture est annuler");
	   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
	   		 return "FactureSimple?faces-redirect=false";
			} catch (Exception e) {
				return "index?faces-redirect=false";
			}

		}
		
		public String prepareList(){
			try {
				init();
				vider();
				offre_article=new ArrayList<Offre_Article>();
				return "index?faces-redirect=true";
			} catch (Exception e) {
				return "index?faces-redirect=true";
			}
		}
		
		public String prepareListDetail(){
			try {
				init();
				vider();
				offre_article=new ArrayList<Offre_Article>();
				return "ListefactureDetail?faces-redirect=true";
			} catch (Exception e) {
				return "index?faces-redirect=true";
			}
		}
		
		public String prepareaffectation(){
			try {
				init();
				vider();
				offre_article=new ArrayList<Offre_Article>();
				return "ListefactureDetail?faces-redirect=true";
			} catch (Exception e) {
				return "index?faces-redirect=true";
			}
		}
		
	   	public String prepareView(){
	   		try {
	   			System.out.println("prepareView");
				livraisonsSource=managerlivraisons.getObject();
				livraisonsTarget=new ArrayList<BonLivraison>();
				mprixmarge=0;
				List<Facture_Livraison> fl=managerFL.getByNames("facture",""+facture.getId());
				System.out.println("SIZE==>"+fl.size());
				if(fl!=null){
					for(int i=0;i<fl.size();i++)
						livraisonsTarget.add(fl.get(i).getLivraison());
				}
				livraisonsSource.removeAll(livraisonsTarget);
				livraisons = new DualListModel<BonLivraison>(livraisonsSource, livraisonsTarget);
	   			factures=null;
	   			System.out.println("------upoffres---------");
		   		upoffres();
		   		System.out.println("------upoffres---FIN------");
		   		
		   		if(facture.getTypepaiement()!=null)
		   			idtypepaiement=facture.getTypepaiement().getId();
		   		else
		   			idtypepaiement=0;
		   		
		   		if(facture.getClient()!=null)
		   			idclient=facture.getClient().getIdclient();
		   		else
		   			idclient=0;
		   		
		   		if(facture.getBu()!=null)
		   			deptID=facture.getBu().getId();
		   		else
		   			deptID=0;
		   		
		    	return "ViewFactureSimple?faces-redirect=true";
			} catch (Exception e) {
				return "index?faces-redirect=true";
			}

	    } 
		
		public String prepareCreate(){
			try {
				livraisonsSource=managerlivraisons.getObject();
				livraisonsTarget=new ArrayList<BonLivraison>();
				livraisons = new DualListModel<BonLivraison>(livraisonsSource, livraisonsTarget);
				factures=null;
				idclient=0;
				idtypepaiement=0;
				idcatarticle=0;
				prixmarge=0;
				mprixmarge=0;
				deptID=0;
				vider();
				offre_article=new ArrayList<Offre_Article>();
				offreart=new Offre_Article();
				offres=manageroffre.getObject();
				tempos=manageroffre.getObject();
				offresadd=new ArrayList<Offre>();
				facture=new Facture();
				Calendar cal = Calendar.getInstance();
		    	datefacture=cal.getTime();
		    	modereg="30 jours";
		    	coefacture=0.0f;
				return "insertfactureClient?faces-redirect=true";
			} catch (Exception e) {
				return "index?faces-redirect=true";
			}

		}
		
		public String annuleropfacture(){
			try {
				return "index?faces-redirect=true";
			} catch (Exception e) {
				return "index?faces-redirect=true";
			}
		}
		
		public void delete(){
			managerApplication.setFactures(null);
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
		
		
		 public void depliquer(){
		    	FacesMessage msg;
		    	try {
		        	
		        	Calendar cal = Calendar.getInstance();
		        	String chaine=""+cal.get(Calendar.YEAR);
		        	String[] tab=chaine.split("0");
		        	int lastnumbre=managerjdbc.getLastNumbre("Facture","ref",chaine);
		        	lastnumbre++;
		        	String nombre=String.format("%03d",lastnumbre);
		        	String ref=tab[1]+"FA"+nombre;
		        	managerarticle.getObjects(facture.getId());
		        	facture.setRef(ref);
		        	manager.insertObject(facture);
		        	/*
		        	for(int i=0;i<lignes.size();i++){
		        		lignes.get(i).setOffre(calcule);
		        		 managerjdbc.insertOffre_Article(lignes.get(i));
		        	}
		        	*/
		            msg = new FacesMessage("Facture est Depliquer avec");
		            FacesContext.getCurrentInstance().addMessage(null, msg);
				} catch (Exception e) {
		            msg = new FacesMessage("Exception Deplication");
		            FacesContext.getCurrentInstance().addMessage(null, msg);
				}

		    }
		
	   	public void insert(){
	   		
	  		managerApplication.setFactures(null);
    		DecimalFormat df = new DecimalFormat("0.##");
            df.setMinimumFractionDigits(2);
	        FacesMessage msg;
	        try {
	        	if(idtypepaiement==0 || idclient==0 || datefacture==null || dateechance==null){
	    	        msg = new FacesMessage("selectionner le vide");
	    	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        	}else{
			        	if(managerjdbc.getexsitefacture(numfacture)==0){
							        	facture.setBanque(managerbanque.getObject(idbanque));
							        	facture.setRef(numfacture);
							        	facture.setConstater("Non");
							        	facture.setDatefacture(datefacture);
							        	facture.setNumeroBCClient(nbc);
							        	facture.setDescription(decription);
							        	facture.setConditiondepayment(modereg);
							        	facture.setFacturetype(managerTypefacture.getObject(1));
							        	facture.setClient(managerclient.getObject(idclient));
							        	facture.setEtat("Non réglée");
							        	facture.setDateechance(dateechance);
							        	facture.setDateechancep(dateechancep);
							        	facture.setDatereglement(dateechance);
							        	facture.setDateconstater(dateechancep);
							        	facture.setBu(managerdept.getObject(deptID));
							        	SimpleDateFormat dt = new SimpleDateFormat("yyyy"); 
							        	facture.setYears(managerjdbc.getYears(Integer.parseInt(dt.format(datefacture))));
							        	facture.setActiver(true);
							        	facture.setTypepaiement(managerTypePaiement.getObject(idtypepaiement));
							        	double prixtotalHT=0;
							         	for(int i=0;i<offre_article.size();i++){
							        		prixtotalHT+=offre_article.get(i).getPt_calculer();
							        		//prixmarge+=offre_article.get(i).getPrixmarge();
							        	}
							        	facture.setTotalht(Double.parseDouble(df.format(prixtotalHT).replace(',', '.')));
							        	
							        	double tempotva=prixtotalHT*0.2f;
										String chaintva=(""+tempotva).replace('.', ',');
										String[] tabtva=chaintva.split(",");
										char[] tvachar=tabtva[1].toCharArray();
										String novtva;
										
											if(tvachar.length>1)
												 novtva=tabtva[0]+"."+tvachar[0]+""+tvachar[1];
											else
												 novtva=tabtva[0]+"."+tvachar[0];
											facture.setTva(Double.parseDouble(novtva));	
										
										
							        	facture.setTotalttc(Double.parseDouble(df.format((prixtotalHT+facture.getTva())).replace(',', '.')));
							        	double tem=coefacture;
							        	facture.setCoefficientfacture(tem);
							        	if(coefacture>0){
							        		facture.setRemise(Double.parseDouble(df.format((facture.getCoefficientfacture()*facture.getTotalttc())/100).replace(',', '.')));
							        		facture.setTotalttcpaye(Double.parseDouble(df.format((facture.getTotalttc()-facture.getRemise())).replace(',', '.')));
							        		facture.setMontantrester(Double.parseDouble(df.format((facture.getTotalttc()-facture.getRemise())).replace(',', '.')));
							        	}else{
							        		facture.setTotalttcpaye(facture.getTotalttc());
							        		facture.setMontantrester(facture.getTotalttc());
							        	}
							        		
							        	facture.setMontantregler(0);
							        	
							        	boolean op=manager.insertObject(facture);
							        	if(op==true){
							        		Facture_Article t;
							        		for(int i=0;i<offre_article.size();i++){
							        		 t=new Facture_Article(); 
							        		System.out.println("-------- Facture_Article --------");
							        		if(offre_article.get(i).getCategorieArticle()!=null){
							        			System.out.println("Categorie DIFF NULL ");
							        			int idtempcat=offre_article.get(i).getCategorieArticle().getIdcategorie();
							        			offre_article.get(i).setCategorieArticle(managercatArticle.getObject(idtempcat));
							        			t.setCategorieArticle(offre_article.get(i).getCategorieArticle());
							        			
							        		}
							        		else
							        			t.setCategorieArticle(new CategorieArticle());
							        		
							        		t.setArticle(offre_article.get(i).getArticle());
							        		t.setQte(offre_article.get(i).getQtef());
							        		t.setLab(offre_article.get(i).getLbqte());
							        		t.setMontantglobal(offre_article.get(i).getPu_calculer());
							        		t.setPt(offre_article.get(i).getPt_calculer());
							        		t.setFacture(facture);
							        		t.setDesignation(offre_article.get(i).getDesignation());
							        		t.setMarge(offre_article.get(i).getMarge());
							        		t.setPrixmarge(offre_article.get(i).getPrixmarge());
							        		t.setCoefficient(1);
							        		t.setMensualite(t.getPt());
							        		t.setObjetOffreArticle(offre_article.get(i));
							        		System.out.println("Object =>"+t);
							        		System.out.println(t.getCategorieArticle().getCategorie());
							        		managerjdbc.AddArticleToFacture(facture,offre_article.get(i),1);
							        		//managerFactArticle.insertObject(t);
							        		//managerjdbc.insertFactureArticle(t);
							        	}
							        		System.out.println("FACTURE ARTICLE SUCCESS");
							        	
							        	
											for(int i=0;i<offresadd.size();i++){
												System.out.println(i+" AVANT ");
							        		managerjdbc.insertFactureOffre(facture.getId(),offresadd.get(i).getId());
							        		System.out.println(i+ " Apres SUCCESS ");
							        		managerjdbc.removeOffreToSansFacture(offresadd.get(i));
							        		managerjdbc.factureeOffre(offresadd.get(i));
							        	}
							        	
							        	
							        	System.out.println("concretiser SUCCESS");
							        	facture.setPrixmarge(Double.parseDouble(df.format(prixmarge).replace(',', '.')));
							        	System.out.println("-------------- EXO "+exoneration);
							        	facture.setExoneration(exoneration);
							        	managerjdbc.updateprixfacture(facture);
							        	
							        	System.out.println("UPDATE FACTURE SUCCESS");
							        	vider();
							        	offre_article=new ArrayList<Offre_Article>();
							        	Facture_Livraison obj;
							        	Iterator<BonLivraison> it=livraisons.getTarget().iterator();
							        	
							        	while(it.hasNext()){
							        		obj=new Facture_Livraison();
							        		String refbl=""+it.next();
							        		System.out.println(refbl);
							        		obj.setFacture(facture);
							        		obj.setLivraison(managerliv.getByName(refbl));
							        		
							        		managerFL.insertObject(obj);
							        	}
							        	years y=managerjdbc.getYears(Integer.parseInt(dt.format(new Date())));
							        	System.out.println("ID YEAR => "+y.getId());
							        	 System.out.println("prepare updateObjectif");
							        	managerjdbc.updateObjectif(y.getId());
							           System.out.println("updateObjectif");
							        msg = new FacesMessage("Facture est bien créé ");
							        	}
							        	else
							        		 msg = new FacesMessage("Probleme d'enregistrement");
							        FacesContext.getCurrentInstance().addMessage(null, msg);
			        	}else{
			    	        msg = new FacesMessage("Facture existe deja");
			    	        FacesContext.getCurrentInstance().addMessage(null, msg);
			        	}
	        	}
	        } catch (Exception e) {
	        	System.out.println(e.getMessage());
	        	e.getStackTrace();
		          msg = new FacesMessage("Exception de Création Facture "+e.getMessage());
		          FacesContext.getCurrentInstance().addMessage(null, msg);
		          
	        	}
		}
	   	
	   	public void update() {
	        FacesMessage msg;
	        factures=null;
	        managerApplication.setFactures(null);
    		DecimalFormat df = new DecimalFormat("0.##");
            df.setMinimumFractionDigits(2);
	        try {
	        	facture.setBanque(managerbanque.getObject(idbanque));
	        	facture.setClient(managerclient.getObject(idclient));
	        	if(facture.getCoefficientfacture()>0){
	        		facture.setRemise(Double.parseDouble(df.format(((facture.getCoefficientfacture()*facture.getTotalttc())/100)).replace(',', '.')));
	        		facture.setTotalttcpaye(Double.parseDouble(df.format(facture.getTotalttc()-facture.getRemise()).replace(',', '.')));
	        		facture.setMontantrester(Double.parseDouble(df.format(facture.getTotalttc()-facture.getRemise()).replace(',', '.')));
	        	}
	        	else{
	        		 facture.setRemise(0);
	        		 facture.setTotalttcpaye(facture.getTotalttc());
	        		 facture.setMontantrester(facture.getTotalttc());
	        	}
	        	facture.setTypepaiement(managerTypePaiement.getObject(idtypepaiement));
	        
	        	facture.setBu(managerdept.getObject(deptID));
	        	
	        	managerjdbc.updateprixfacture(facture);
	        	SimpleDateFormat dt = new SimpleDateFormat("yyyy"); 
	        	facture.setYears(managerjdbc.getYears(Integer.parseInt(dt.format(facture.getDatefacture()))));
	        	
	        	int op=managerjdbc.updatefacture(facture);
	        	if(op==0){
	        	     msg = new FacesMessage("Numero facture existe deja","");
		               FacesContext.getCurrentInstance().addMessage(null, msg);
	        	}else{
	        		managerjdbc.deleteFacture_Livraisonbyfacture(facture.getId());
	        		Iterator<BonLivraison> it=livraisons.getTarget().iterator();
	        		Facture_Livraison obj;
		        	while(it.hasNext()){
		        		obj=new Facture_Livraison();
		        		String refbl=""+it.next();
		        		System.out.println(refbl);
		        		obj.setFacture(facture);
		        		obj.setLivraison(managerliv.getByName(refbl));
		        		
		        		managerFL.insertObject(obj);
		        	}
		        	years y=managerjdbc.getYears(Integer.parseInt(dt.format(new Date())));
		        	managerjdbc.updateObjectif(y.getId());
		        	facture=manager.getObject(facture.getId());
	        	     msg = new FacesMessage("Facture est bien Modifier","");
		               FacesContext.getCurrentInstance().addMessage(null, msg);
	        	}
	       
	        } catch (Exception e) {
	           msg = new FacesMessage("Probleme de Modification de Facture","Contacter l'Administration");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
	   }
		
	   
	   	
		public void addart(){
			FacesMessage msg;
    		DecimalFormat df = new DecimalFormat("0.##");
            df.setMinimumFractionDigits(2);
			try {
				if(idarticle==0 || idcatarticle==0){
		   		 	msg = new FacesMessage("Selectionner l'article avec leur categorie");
		   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
				}else{
				Offre_Article tempo=new Offre_Article();
				Article tempoart= managerObjectArticle.getObject(idarticle);
				tempo.setArticle(tempoart);
		   		if(designation.equals("")){
		   			tempo.setDesignation(tempoart.getDesignation());
		   		}
		   		else{
		   			tempo.setDesignation(designation);
		   		}
		   		if(checkforfait==true)
		   			tempo.setLbqte("F");
		   		else
		   			tempo.setLbqte(""+qte);
				tempo.setPu_calculer(pu);
				tempo.setQtef(qte);
				tempo.setPt_calculer(pu*qte);
				tempo.setCategorieArticle(managercatArticle.getObject(idcatarticle));
				tempo.setPrixmarge(mprixmarge);
				prixmarge+=mprixmarge;
				offre_article.add(tempo);
				totalHT+=tempo.getPt_calculer();
				TVA=totalHT*0.2f;
				String chaintva=(""+TVA).replace('.', ',');
				String[] tabtva=chaintva.split(",");
				char[] tvachar=tabtva[1].toCharArray();
				String novtva;
				if(tvachar.length>1)
					 novtva=tabtva[0]+"."+tvachar[0]+""+tvachar[1];
				else
					 novtva=tabtva[0]+"."+tvachar[0];
				TVA=Double.parseDouble(novtva);
				totalTTC=totalHT+TVA;
				totalTTC=Double.parseDouble(df.format(totalTTC).replace(',', '.'));
				//totalTTCpaye=totalTTC-re
	   		 	msg = new FacesMessage("Article est ajouter");
	   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
	   		 	qte=1;
	   		 	pu=0;
	   		 	designation="";
	   		 	idarticle=0;
	   		 	checkforfait=false;
				}
			} catch (Exception e) {
	   		 	msg = new FacesMessage("Erreur de l'ajout d'article Contacter l'administration");
	   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}
		
		public void addartmodif(){
			
			FacesMessage msg;
    		DecimalFormat df = new DecimalFormat("0.##");
            df.setMinimumFractionDigits(2);
            if(idcatarticle!=0 && idtypepaiement!=0){
    			try {
    				Offre_Article tempo=new Offre_Article();
    				Article tempoart= managerObjectArticle.getObject(idarticle);
    				tempo.setArticle(tempoart);
    				if(designation.equals("")){
    		   			tempo.setDesignation(tempoart.getDesignation());
    		   		}
    		   		else{
    		   			tempo.setDesignation(designation);
    		   		}
    		   		if(checkforfait==true)
    		   			tempo.setLbqte("forfait");
    		   		else
    		   			tempo.setLbqte(""+qte);
    				tempo.setPu_calculer(pu);
    				tempo.setQtef(qte);
    				tempo.setPt_calculer(pu*qte);
    				tempo.setPrixmarge(mprixmarge);
    				tempo.setMarge(0);
    				tempo.setCategorieArticle(managercatArticle.getObject(idcatarticle));
    				facture.setTotalht(facture.getTotalht()+tempo.getPt_calculer());
    				facture.setTotalht(Double.parseDouble(df.format(facture.getTotalht()).replace(',', '.')));
    				
    				facture.setTva(facture.getTotalht()*0.2f);
    				facture.setTva(Double.parseDouble(df.format(facture.getTva()).replace(',', '.')));
    				
    				facture.setTotalttc(facture.getTotalht()+facture.getTva());
    				facture.setTotalttc(Double.parseDouble(df.format(facture.getTotalttc()).replace(',', '.')));				
    				if(facture.getCoefficientfacture()>0){
    	        		facture.setRemise(Double.parseDouble(df.format(((facture.getCoefficientfacture()*facture.getTotalttc())/100)).replace(',', '.')));
    	        		facture.setTotalttcpaye(Double.parseDouble(df.format(facture.getTotalttc()-facture.getRemise()).replace(',', '.')));
    	        		facture.setMontantrester(Double.parseDouble(df.format(facture.getTotalttc()-facture.getRemise()).replace(',', '.')));
    	        	}
    				
    				facture.setTotalttcpaye(Double.parseDouble(df.format(facture.getTotalttcpaye()).replace(',', '.')));
    				facture.setPrixmarge(facture.getPrixmarge()+mprixmarge);
    				managerjdbc.AddArticleToFacture(facture,tempo,1);
    				managerjdbc.updateprixfacture(facture);
    				
    				Facture_Article fact_article=new Facture_Article();
    				
    				fact_article.setArticle(tempoart);
    				fact_article.setCategorieArticle(managercatArticle.getObject(idcatarticle));
    				fact_article.setDesignation(tempo.getDesignation());
    				fact_article.setCoefficient(coefficient);
    				fact_article.setLab(""+qte);
    				fact_article.setQte(qte);
    				fact_article.setMontantglobal(pu);
    				fact_article.setPt(pu*qte);
    				fact_article.setPrixmarge(mprixmarge);
    				facturarticlessmodif.add(fact_article);
    				msg = new FacesMessage("Article est ajouter");
    	   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
    	   		 msg = new FacesMessage("Facture est Modifier");
    	   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
    	   		 years y=managerjdbc.getYears(Integer.parseInt(df.format(new Date())));
    	   		 managerjdbc.updateObjectif(y.getId());
    	   		 	qte=1;
    	   		 	pu=0;
    	   		 	designation="";
    	   		 	checkforfait=false;
    	   		 	mprixmarge=0;
    			} catch (Exception e) {
    	   		 	msg = new FacesMessage("Erreur de l'ajout d'article Contacter l'administration");
    	   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
    			}
            }else{
  	   		 	
  	   		 		msg = new FacesMessage("Remplire la categorie et le Devise");
  	   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
            }


		}
		
		public void DeleteArticleFacture(){
			try {
				offre_article.remove(offreart);
				totalHT=totalHT-offreart.getPt_calculer();
				TVA=totalHT*0.2f;
				totalTTC=totalHT+TVA;
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Article est Supprimer","");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Probleme de suppression de l'article","Contacter Administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}
		
		public void DeleteArticleFacturemodif(){
			FacesMessage msg;
			try {
				factures=null;
				managerApplication.setFactures(null);
				for(int i=0;i<facturearticle.size();i++){
						boolean op=true;
						op=managerjdbc.deleteArticleToFacture(facturearticle.get(i));
						if(op==true){
							double tempoPt=facturearticle.get(i).getPt();
							
							double ancienHT=facture.getTotalht();
							facture.setTotalht(ancienHT-tempoPt);
							
							facture.setTva(facture.getTotalht()*0.2f);
							facture.setTotalttc(facture.getTva()+facture.getTotalht());
							if(facture.getCoefficientfacture()==0)
								facture.setTotalttcpaye(facture.getTotalttc());
							else
								facture.setTotalttcpaye(facture.getTotalttc()*facture.getCoefficientfacture());
							facture.setPrixmarge(facture.getPrixmarge()-facturearticle.get(i).getPrixmarge());
							facturarticlessmodif.remove(facturearticle.get(i));
							
							msg = new FacesMessage("l'article "+facturearticle.get(i).getArticle().getRef()+" Supprimer avec Success");
							FacesContext.getCurrentInstance().addMessage(null, msg);
							
						}else{
							msg = new FacesMessage("l'article "+facturearticle.get(i).getArticle().getRef()+" Déja associé");
							FacesContext.getCurrentInstance().addMessage(null, msg);
						}
						
				}
				managerjdbc.updateprixfacture(facture);
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
				msg = new FacesMessage(" Exception Contacter Administration ",e.getLocalizedMessage());
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}
	
		public void addOffre(){
			try {
	    		DecimalFormat df = new DecimalFormat("0.##");
	            df.setMinimumFractionDigits(2);
				boolean trouve=false;
				for(int i=0;i<idoffres.size();i++){
					//boucle FOR
							Offre of=manageroffre.getObject(Integer.parseInt(idoffres.get(i)));
							
							if(deptID==0){
								if(of.getBu()!=null)
									deptID=of.getBu().getId();
							}
							
							for(int j=0;j<offresadd.size();j++){
								if(of.getRef().equals(offresadd.get(j).getRef()))
									trouve=true;
							}
							
							if(trouve==false){
									offres=null;
									offres=new ArrayList<Offre>();
									offresadd.add(of);
									for(int k=0;k<tempos.size();k++)
									{	boolean ajout=true;
										for(int kadd=0;kadd<offresadd.size();kadd++){
											if(tempos.get(k).getId()==offresadd.get(kadd).getId())
												ajout=false;
										}
										if(ajout==true)
											offres.add(tempos.get(k));
									}
							}
							
							Double nombrtemp=Double.parseDouble(df.format(of.getTotalHT()).replace(',', '.'));
							totalHT+=nombrtemp;
							Double nombrtempmarge=Double.parseDouble(df.format(of.getTotalmarge()).replace(',', '.'));
							prixmarge+=nombrtempmarge;
							totalHT=Double.parseDouble(df.format(totalHT).replace(',', '.'));
							
							TVA=(double)(totalHT*0.2);
							TVA=Double.parseDouble(df.format(TVA).replace(',', '.'));
							totalTTC=totalHT+TVA;
							totalTTC=Double.parseDouble(df.format(totalTTC).replace(',', '.'));
							double remisetempo=Double.parseDouble(df.format((coefacture*totalTTC)/100).replace(',', '.'));
							totalTTCpaye=totalTTC-remisetempo;
					 //FIN boucle FOR
				}
				for(int i=0;i<idoffres.size();i++)
					offre_article.addAll(managerarticle.getObjects(Integer.parseInt(idoffres.get(i))));
				
				if(idcatarticle!=0){
					for(int i=0;i<offre_article.size();i++)
						offre_article.get(i).setCategorieArticle(managercatArticle.getObject(idcatarticle));
				}else{
					if(offre_article!=null){
						if(offre_article.size()>0)
							idcatarticle=offre_article.get(0).getCategorieArticle().getIdcategorie();
					}
				}
				
				idoffres=null;
				idoffres=new ArrayList<String>();
				FacesMessage msg = new FacesMessage("Offre est Ajouter Avec Sucess");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage("Exception "+e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

	}
			
		public void removeOffre(){
			totalHT=0;
			TVA=0;
			totalTTC=0;
    	
            df.setMinimumFractionDigits(2);
				try {
					boolean trouve=false;
					List<Offre> tempoadd=new ArrayList<Offre>();
					tempoadd.addAll(offresadd);
					
				for(int i=0;i<idoffresadd.size();i++){
					Offre of=manageroffre.getObject(Integer.parseInt(idoffresadd.get(i)));
					for(int j=0;j<offres.size();j++)
					{
						if(of.getRef().equals(offres.get(j).getRef()))
							trouve=true;
					}
					if(trouve==false){
						offresadd=null;
						offresadd=new ArrayList<Offre>();
						offres.add(of);
						for(int j=0;j<tempoadd.size();j++){
							boolean ajout=true;
							for(int k=0;k<offres.size();k++){
								if(tempoadd.get(j).getId()==offres.get(k).getId())
									ajout=false;
							}
							if(ajout==true)
								offresadd.add(tempoadd.get(j));
								
						}
					}
				}
				offre_article=new ArrayList<Offre_Article>();
				for(int i=0;i<offresadd.size();i++){
					offre_article.addAll(managerarticle.getObjects(offresadd.get(i).getId())) ;
				}
				for(int i=0;i<offre_article.size();i++){
					totalHT+=offre_article.get(i).getPt_calculer();
				}
				totalHT=Double.parseDouble(df.format(totalHT).replace(',', '.'));
				TVA=Double.parseDouble(df.format(totalHT*0.2f).replace(',', '.'));
				totalTTC=Double.parseDouble(df.format(totalHT+TVA).replace(',', '.'));
				double remisetempo=Double.parseDouble(df.format((coefacture*totalTTC)/100).replace(',', '.'));
				totalTTCpaye=totalTTC-remisetempo;
				idoffresadd=null;
				idoffresadd=new ArrayList<String>();
				FacesMessage msg = new FacesMessage("l'offre est est Supprimer");
	   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
				} catch (Exception e) {
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Probleme de Suppressions","Contacter Administration");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
				
		}
		
		@SuppressWarnings("static-access")
		public void addmodifoffre(){
			
			try {
				factures=null;
	            df.setMinimumFractionDigits(2);
				FacesMessage msg;
				 for(int i=0;i<idoffres.size();i++){
					 //Boucle FOR
								Offre tempooffre=manageroffre.getObject(Integer.parseInt(idoffres.get(i)));
								
								if(deptID==0){
									if(tempooffre.getBu()!=null)
										deptID=tempooffre.getBu().getId();
								}
									
								
								managerjdbc.insertOffre_facture(tempooffre, facture);
								managerjdbc.VerifyOffreTosansFactureforAdd(tempooffre);
								List<Offre_Article> offrearticle=managerarticle.getObjects(Integer.parseInt(idoffres.get(i)));
								for(int j=0;j<offrearticle.size();j++)
									{
									Facture_Article fatempo=new Facture_Article();
									if(idcatarticle!=0)
										offrearticle.get(j).setCategorieArticle(managercatArticle.getObject(idcatarticle));
									int id=managerjdbc.AddArticleToFacture(facture, offrearticle.get(j),1);
									
									fatempo.setId(fatempo.getNombreinstance());
									fatempo.setArticle(offrearticle.get(j).getArticle());
									fatempo.setCoefficient(1);
									fatempo.setFacture(facture);
									fatempo.setDesignation(offrearticle.get(j).getDesignation());
									fatempo.setLab(offrearticle.get(j).getLbqte());
									fatempo.setQte(offrearticle.get(j).getQtef());
									fatempo.setMontantglobal(offrearticle.get(j).getPu_calculer());
									System.out.println("ID CATEGORIE ==>"+idcatarticle);
									fatempo.setCategorieArticle(managercatArticle.getObject(offrearticle.get(j).getCategorieArticle().getIdcategorie()));
									fatempo.setPt(offrearticle.get(j).getPt_calculer());
									fatempo.setMarge(offrearticle.get(j).getMarge());
									fatempo.setPrixmarge(offrearticle.get(j).getPrixmarge());
									
									facturarticlessmodif.add(fatempo);
									facture.setPrixmarge(facture.getPrixmarge()+offrearticle.get(j).getPrixmarge());
									facture.setTotalht(facture.getTotalht()+offrearticle.get(j).getPt_calculer());
									}
						//FIN boucle FOR		
				}
				double tempo=(facture.getTotalht()*20)/100;
				tempo=Double.parseDouble(df.format(tempo).replace(',', '.'));
				facture.setTva(tempo);
				facture.setTotalttc(Double.parseDouble(df.format(facture.getTotalht()+facture.getTva()).replace(',', '.')));
				if(facture.getCoefficientfacture()==0)
					facture.setTotalttcpaye(facture.getTotalttc());
				else{
					facture.setTotalttcpaye(facture.getTotalttc()*facture.getCoefficientfacture());
					facture.setTotalttcpaye(Double.parseDouble(df.format(facture.getTotalttcpaye()).replace(',', '.')));
				}
				managerjdbc.updateprixfacture(facture);
	   		 	upoffres();
	   		 	msg = new FacesMessage("offre est ajouter");
	   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
	   		 	msg = new FacesMessage("Article est modifier avec Success");
	   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage("Probleme de l'ajout","Contacter Administration");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}

		public void removremodifoffre(){
			factures=null;
			for(int i=0;i<idoffresadd.size();i++){
				
				managerjdbc.deleteOffre_Facture(idoffresadd.get(i),facture.getId());
				managerjdbc.VerifyOffreTosansFacture(manageroffre.getObject(Integer.parseInt(idoffresadd.get(i))));
			}
			upoffres();
			FacesMessage msg = new FacesMessage("l'offre est est Supprimer");
   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
			msg = new FacesMessage("Facture est Bien Modifier");
   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		public void onCellEdit(CellEditEvent event) {
			try {
				
	            offreart.setLbqte(offreart.getQtef()+"");
	    		DecimalFormat df = new DecimalFormat("0.##");
	            df.setMinimumFractionDigits(2);
	            offreart.setPt(offreart.getPu()*offreart.getQtef());
	            
	            System.out.println("-- CALC 1--");
	            
	            offreart.setPt_calculer(offreart.getQtef()*offreart.getPu_calculer());
	            
	            if(offreart.getMarge()!=0)
	            offreart.setPrixmarge(offreart.getPt_calculer()-offreart.getPt());
	           
	            totalHT=0;
	            System.out.println("-- CALC 2--");
	            for(int i=0;i<offre_article.size();i++){
	            	totalHT+=offre_article.get(i).getPt_calculer();
	            }
	            	
	            TVA=totalHT*0.2;
	            totalTTC=totalHT+TVA;
	            System.out.println("-- CALC 3--");
				double remisetempo=Double.parseDouble(df.format((coefacture*totalTTC)/100).replace(',', '.'));
				totalTTCpaye=totalTTC-remisetempo;
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Article Modifier","");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	            nvqte=0;
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exception "+e.getLocalizedMessage(),"");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
			}
	
		}
		
		public void onCellEditUpdate(){
			try {
				System.out.println("------ onCellEditUpdate  ---");
					factures=null;
					totalHT=0;
		            double m=0;
		            System.out.println("------ facturarticlessmodif DEBUT  ---");
		            System.out.println("SIZE facturarticlessmodif  =>"+facturarticlessmodif.size());
				for(int i=0;i<facturarticlessmodif.size();i++){
					System.out.println("ID ==>"+facturarticlessmodif.get(i).getId());
					facturarticlessmodif.get(i).setLab(facturarticlessmodif.get(i).getQte()+"");
					System.out.println("PREPARE Operation .");
					facturarticlessmodif.get(i).setPt(facturarticlessmodif.get(i).getMontantglobal()*facturarticlessmodif.get(i).getQte());
					System.out.println("PREPARE Operation ..");
					facturarticlessmodif.get(i).setMensualite(facturarticlessmodif.get(i).getPt()*facturarticlessmodif.get(i).getCoefficient());
					System.out.println("PREPARE Operation ....");
					totalHT+=facturarticlessmodif.get(i).getMensualite();
					System.out.println("PREPARE Operation ......");
					System.out.println("Prix marge ="+facturarticlessmodif.get(i).getPrixmarge());
					m+=facturarticlessmodif.get(i).getPrixmarge();
					System.out.println("PREPARE Operation .......");
					facturarticlessmodif.get(i).setCategorieArticle(managercatArticle.getObject(facturarticlessmodif.get(i).getCategorieArticle().getIdcategorie()));
	            	
	            	System.out.println("PREPARE Operation ............");
	            	System.out.println(facturarticlessmodif.get(i).getCategorieArticle().getCategorie());
	            	
	            	
	            	managerFactArticle.updateObject(facturarticlessmodif.get(i));
	            	
				}
				  System.out.println("------ facturarticlessmodif FIN  ---");	
				  
	            TVA=totalHT*0.2;
	            totalTTC=totalHT+TVA;
	            facture.setTotalht(totalHT);
	            facture.setTva(TVA);
	            facture.setTotalttc(totalTTC);
				double remisetempo=Double.parseDouble(df.format((coefacture*totalTTC)/100).replace(',', '.'));
				totalTTCpaye=totalTTC-remisetempo;
				facture.setTotalttcpaye(totalTTCpaye);
	            facture.setPrixmarge(m);
	      	  System.out.println("------ CALC FIN  ---");	
	            FacesMessage msg = new FacesMessage("les Articles est Modifier avec Success","");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				FacesMessage msg = new FacesMessage("Exception "+e.getLocalizedMessage(),e.getLocalizedMessage());
	            FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	   	
	   	public void addarttofact(){
	   		FacesMessage msg;
	   		try {
		   		mensualite=coefficient*montantglobal;
		   		Facture_Article tempo=new Facture_Article();
		   		tempo.setCategorieArticle(managercatArticle.getObject(idcatarticle));
		   		tempo.setArticle(managerObjectArticle.getObject(idarticle));
		   		tempo.setMontantglobal(montantglobal);
		   		tempo.setCoefficient(coefficient);
		   		tempo.setMensualite(mensualite);
		   		totalHT=totalHT+tempo.getMensualite();
		   		totalTTC=totalHT+offre.getTVA();
				double remisetempo=Double.parseDouble(df.format((coefacture*totalTTC)/100).replace(',', '.'));
				totalTTCpaye=totalTTC-remisetempo;
		   		facturearticles.add(tempo);
		   		msg = new FacesMessage("Article est Ajouter avec success","");
	               FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (Exception e) {
				msg = new FacesMessage("Probleme de l'ajout de l'article contacter l'administration","");
	               FacesContext.getCurrentInstance().addMessage(null, msg);
			}

	   	}
	   	
	   	public void viderarttofact(){
	   		coefficient=0;
	   		mensualite=0;
	   	}
	   	
	   	public void createPdf(String dest){
			   DateFormat format=new SimpleDateFormat("dd/MM/yy");
			   String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
		   		Paragraph saute=new Paragraph("\n");
		   		
		   	 float left = 36;
	         float right = 36;
	         float top = 60;
	         float bottom = 100;
	         
		       Document document = new Document(PageSize.A4,left, right, top, bottom);
		       HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		       try {
		    	   Font ftext=new Font(Font.FontFamily.HELVETICA,9,Font.NORMAL,new BaseColor(Color.black));
		    	   Font ftext10=new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL,new BaseColor(Color.black));
		            Font ftextgra=new Font(Font.FontFamily.HELVETICA,8,Font.BOLD,new BaseColor(Color.black));
		            Font forange=new Font(Font.FontFamily.HELVETICA,10,Font.UNDERLINE,new BaseColor(Color.black));
		    	   PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
		    	 
		           response.setContentType("application/pdf");
		           response.setHeader("Content-Disposition", "inline; filename="+facture.getRef()+".pdf");
		           document.open();
		          
		          // document.setMargins(36, 36, 60, 180);
		           document.setMargins(left, right,108, bottom);
		           
		           document.add(saute);
		           document.add(saute);
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
		           Paragraph Numerocommande=null;
		           if(facture.getEtat().equals("PROFORMA"))
		        	  Numerocommande=new Paragraph("Facture PROFORMA N° : "+facture.getRef(),ftextgrafact);
		          else
		        	  Numerocommande=new Paragraph("Facture N° : "+facture.getRef(),ftextgrafact);
		          
		           Numerocommande.setAlignment(Element.ALIGN_LEFT);
		           Numerocommande.setAlignment(Element.ALIGN_TOP);
		           Numerocommande.setIndentationLeft(53);
		           document.add(Numerocommande);
		           
		           document.add(saute);
		          
		           PdfPTable tableoffre = new PdfPTable(4);
		           tableoffre.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           tableoffre.setWidthPercentage(90);
		           PdfPCell c1 = new PdfPCell(new Phrase("Date BC",ftextgra));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           tableoffre.addCell(c1);
		           c1 = new PdfPCell(new Phrase("N°BC",ftextgra));
		           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           tableoffre.addCell(c1);
		           
		           c1 = new PdfPCell(new Phrase("BL",ftextgra));
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
			           
			           // BL
			           List<Facture_Livraison> fl=managerFL.getByNames("facture",""+facture.getId());
			           
			           String str_fl="";
			           for(int i=0;i<fl.size();i++){
			        	   if(i!=fl.size()-1)
			        		   str_fl+=fl.get(i).getLivraison().getRef()+"/";
			        	   else
			        		   str_fl+=fl.get(i).getLivraison().getRef();
			           }
			           c1 = new PdfPCell(new Phrase(str_fl,ftext));
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
		           
		           Paragraph pie=new Paragraph();
		           if(facture.getEtat().equals("PROFORMA"))
		        	   pie.add(new Phrase("Arrêtée la présente facture proforma à la somme de :\n",forange));
		           else
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
		        		  else{
		        			  pie.add(new Phrase(chainenombre+" "+facture.getTypepaiement().getTypepaiement().toLowerCase()+" et "+formatter.format(virgule),ftextgra));
		        		  }
		        			  
		        			  
		        	   }
				       
		        	   else{
		        		   
		        		   virgule=virgule*10;
		        		   
		        		   String chainenombre=formatter.format(nombre).replace("onze cents","mille cents");
		        		   if(facture.getTypepaiement().getTypepaiement().equals("MAD")) 
		        			   pie.add(new Phrase(chainenombre+" Dirhams et "+formatter.format(virgule)+ " centimes Toutes Taxes Comprises",ftextgra));
		        		   else
		        			   pie.add(new Phrase(chainenombre+" "+facture.getTypepaiement().getTypepaiement().toLowerCase()+" et "+formatter.format(virgule),ftextgra));
		        			   
		        	   }
		        		   
		           } catch (Exception e) {
		        	   System.out.println("-----------Exception----------");
		        	   if(facture.getTypepaiement().getTypepaiement().equals("MAD"))
		        		   pie.add(new Phrase(formatter.format(facture.getTotalttcpaye())+" Dirhams et zero centimes Toutes Taxes Comprises",ftextgra));
		        	   else
		        		   pie.add(new Phrase(formatter.format(facture.getTotalht())+" ",ftextgra));
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
		           System.out.println("ERROR PDF"+de.getMessage());
		           document.close();
		           FacesContext.getCurrentInstance().responseComplete();
		       }
		   }
	   	
	    public void imprimer() throws FileNotFoundException,ImsErpException{
	        String dest="D:/IMS.pdf";
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
	    
	    public void imprimerfond() throws FileNotFoundException{
	        String dest="D:/IMS.pdf";
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

	    public void createPdfImage() throws FileNotFoundException, DocumentException, IOException ,NullPointerException{

	        PdfReader reader;			
	        reader = new PdfReader("D:/IMS.pdf");

	        int n = reader.getNumberOfPages(); 
	        // Create a stamper that will copy the document to a new file 
	        //PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("D:/boncmd.pdf")); 
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
	        reader = new PdfReader("D:/IMS.pdf");

	        int n = reader.getNumberOfPages(); 
	        // Create a stamper that will copy the document to a new file 
	        //PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("D:/boncmd.pdf")); 
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
	   
	   	
		private void vider(){
			   numfacture="";
			   nbc="";
			   modereg="";
			   TVA=0;
			   totalHT=0;
			   totalTTC=0;
			   totalTTCpaye=0;
			   prixmarge=0;
		   }
		
		private void upoffres(){
			System.out.println("PREPARE UPOFFRE");
   			offresmodif=new ArrayList<Offre>();
   			tempos=manageroffre.getObject();
	   		idoffresadd=new ArrayList<String>();
	   		idoffres=new ArrayList<String>();
	   		System.out.println("PREPARE TYPE FACTURE");
	    	idtypefact=facture.getFacturetype().getId();
	    	System.out.println("GET TYPE FACTURE");
	    	System.out.println("PREPARE facturarticlessmodif");
	    	facturarticlessmodif=managerFactArticle.getObjects(facture.getId());
	    	System.out.println("GET facturarticlessmodif");
	    	if(facturarticlessmodif!=null){
	    		if(facturarticlessmodif.size()>0){
	    			if(facturarticlessmodif.get(0).getCategorieArticle()!=null)
		    			idcatarticle=facturarticlessmodif.get(0).getCategorieArticle().getIdcategorie();
	    			else
	    				idcatarticle=0;
	    		}else idcatarticle=0;
	    	}else
	    		idcatarticle=0;
	    	System.out.println("GET idcatarticle ");
	    	facture=manager.getObject(facture.getId());
	    	System.out.println("GET FACUTRE COMPLETE");
	    	for(int i=0;i<tempos.size();i++){
	    		boolean trouve=false;
	    		for(int j=0;j<facture.getListoffres().size();j++){
	    			if(tempos.get(i).getId()==facture.getListoffres().get(j).getId())
	    				trouve=true;
	    		}
	    		if(trouve==false){
	    			offresmodif.add(tempos.get(i));
	    		}
	    	}
	    	System.out.println("GET tempos COMPLETE");
		}
	   	
	   	/*
	   	 * Getter and Setter
	   	 */
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
	   
	   	public List<Facture> getFactures() {
	   		/*if(factures==null)
	   			factures=manager.getByNames(EnumFactureType.Simple.toString(),EnumFactureType.Bonus.toString());
		*/
	   		factures=managerApplication.getFactures();
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
	   
	   	public ModelService<Offre_Article> getManagerarticle() {
		return managerarticle;
	   }
	   
	   	public void setManagerarticle(ModelService<Offre_Article> managerarticle) {
		this.managerarticle = managerarticle;
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
			return nbc;
		}
		
		public void setNbc(String nbc) {
			this.nbc = nbc;
		}
		
		public String getModereg() {
			return modereg;
		}
		
		public void setModereg(String modereg) {
			this.modereg = modereg;
		}
	   	
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

		public TypeFacture getFactureType() {
			return factureType;
		}

		public void setFactureType(TypeFacture factureType) {
			this.factureType = factureType;
		}

		public List<TypeFacture> getFactureTypes() {
			factureTypes=managerTypefacture.getObject();
			return factureTypes;
		}

		public void setFactureTypes(List<TypeFacture> factureTypes) {
			this.factureTypes = factureTypes;
		}

		public int getIdtypefact() {
			return idtypefact;
		}
		
		public void setIdtypefact(int idtypefact) {
			this.idtypefact = idtypefact;
		}

		public List<Facture_Article> getFacturearticles() {
			return facturearticles;
		}

		public void setFacturearticles(List<Facture_Article> facturearticles) {
			this.facturearticles = facturearticles;
		}
		
		

		public List<Facture_Article> getFacturearticle() {
			return facturearticle;
		}

		public void setFacturearticle(List<Facture_Article> facturearticle) {
			this.facturearticle = facturearticle;
		}

		public int getIdarticle() {
			return idarticle;
		}

		public void setIdarticle(int idarticle) {
			this.idarticle = idarticle;
		}

		public List<Article> getArticles() {
			articles=null;
			articles=new ArrayList<Article>();
			offrearticles=managerarticle.getObjects(idoffre);
			for(int i=0;i<offrearticles.size();i++)
				{
					articles.add(managerObjectArticle.getObject(offrearticles.get(i).getArticle().getIdArticle()));
				}
			return articles;
		}

		public void setArticles(List<Article> articles) {
			this.articles = articles;
		}
	
		public double getMontantglobal() {
			offrearticles=managerarticle.getObjects(idoffre);
			for(int i=0;i<offrearticles.size();i++)
				{
				if(offrearticles.get(i).getArticle().getIdArticle()==idarticle)
					montantglobal=offrearticles.get(i).getPu_calculer();
				}
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

		public List<Offre_Article> getOffrearticles() {
			return offrearticles;
		}

		public void setOffrearticles(List<Offre_Article> offrearticles) {
			this.offrearticles = offrearticles;
		}

		public ModelService<Article> getManagerObjectArticle() {
			return managerObjectArticle;
		}

		public void setManagerObjectArticle(ModelService<Article> managerObjectArticle) {
			this.managerObjectArticle = managerObjectArticle;
		}
		
		public Offre getOffre() {
			return offre;
		}

		public void setOffre(Offre offre) {
			this.offre = offre;
		}

		public List<Offre_Article> getOffre_article() {
			return offre_article;
		}

		public void setOffre_article(List<Offre_Article> offre_article) {
			this.offre_article = offre_article;
		}

		public Offre_Article getOffreart() {
			return offreart;
		}

		public void setOffreart(Offre_Article offreart) {
			this.offreart = offreart;
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

		public List<Offre> getOffres() {
			return offres;
		}

		public void setOffres(List<Offre> offres) {
			this.offres = offres;
		}

		public List<Offre> getTempos() {
			return tempos;
		}

		public void setTempos(List<Offre> tempos) {
			this.tempos = tempos;
		}

		public List<Offre> getOffresadd() {
			return offresadd;
		}

		public void setOffresadd(List<Offre> offresadd) {
			this.offresadd = offresadd;
		}

		public List<Offre> getOffresmodif() {
			return offresmodif;
		}

		public void setOffresmodif(List<Offre> offresmodif) {
			this.offresmodif = offresmodif;
		}

		public Date getDatefacture() {
			return datefacture;
		}

		public void setDatefacture(Date datefacture) {
			this.datefacture = datefacture;
		}

		public ModelServiceJDBC getManagerjdbc() {
			return managerjdbc;
		}

		public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
			this.managerjdbc = managerjdbc;
		}

		public ModelService<BonLivraison> getManagerliv() {
			return managerliv;
		}

		public void setManagerliv(ModelService<BonLivraison> managerliv) {
			this.managerliv = managerliv;
		}

		public ModelService<Client> getManagerclient() {
			return managerclient;
		}

		public void setManagerclient(ModelService<Client> managerclient) {
			this.managerclient = managerclient;
		}

		public int getIdclient() {
			return idclient;
		}

		public void setIdclient(int idclient) {
			this.idclient = idclient;
		}

		public List<Facture_Article> getFacturarticlessmodif() {
			return facturarticlessmodif;
		}

		public void setFacturarticlessmodif(List<Facture_Article> facturarticlessmodif) {
			this.facturarticlessmodif = facturarticlessmodif;
		}

		public int getIdbanque() {
			return idbanque;
		}

		public void setIdbanque(int idbanque) {
			this.idbanque = idbanque;
		}

		public ModelService<CoordonneeBancaire> getManagerbanque() {
			return managerbanque;
		}

		public void setManagerbanque(ModelService<CoordonneeBancaire> managerbanque) {
			this.managerbanque = managerbanque;
		}

		public String getDecription() {
			return decription;
		}

		public void setDecription(String decription) {
			this.decription = decription;
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

		public String getDesignation() {
			return designation;
		}

		public void setDesignation(String designation) {
			this.designation = designation;
		}
	
		public double getCoefficient() {
			return coefficient;
		}

		public void setCoefficient(double coefficient) {
			this.coefficient = coefficient;
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

		public boolean isCheckforfait() {
			return checkforfait;
		}

		public void setCheckforfait(boolean checkforfait) {
			this.checkforfait = checkforfait;
		}

		public double getPrixmarge() {
			return prixmarge;
		}

		public void setPrixmarge(double prixmarge) {
			this.prixmarge = prixmarge;
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

		public double getNvqte() {
			return nvqte;
		}

		public void setNvqte(double nvqte) {
			this.nvqte = nvqte;
		}

		public DualListModel<BonLivraison> getLivraisons() {
			return livraisons;
		}

		public void setLivraisons(DualListModel<BonLivraison> livraisons) {
			this.livraisons = livraisons;
		}

		public List<BonLivraison> getLivraisonsSource() {
			if(livraisonsSource==null)
				livraisonsSource=managerlivraisons.getObject();
			return livraisonsSource;
		}

		public void setLivraisonsSource(List<BonLivraison> livraisonsSource) {
			this.livraisonsSource = livraisonsSource;
		}

		public List<BonLivraison> getLivraisonsTarget() {
			return livraisonsTarget;
		}

		public void setLivraisonsTarget(List<BonLivraison> livraisonsTarget) {
			this.livraisonsTarget = livraisonsTarget;
		}

		public ModelService<BonLivraison> getManagerlivraisons() {
			return managerlivraisons;
		}

		public void setManagerlivraisons(ModelService<BonLivraison> managerlivraisons) {
			this.managerlivraisons = managerlivraisons;
		}

		public ModelService<Facture_Livraison> getManagerFL() {
			return managerFL;
		}

		public void setManagerFL(ModelService<Facture_Livraison> managerFL) {
			this.managerFL = managerFL;
		}

		public double getMprixmarge() {
			return mprixmarge;
		}

		public void setMprixmarge(double mprixmarge) {
			this.mprixmarge = mprixmarge;
		}

		public double getTotalTTCpaye() {
			return totalTTCpaye;
		}

		public void setTotalTTCpaye(double totalTTCpaye) {
			this.totalTTCpaye = totalTTCpaye;
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

		public ManagerDataBase getManagerApplication() {
			return managerApplication;
		}

		public void setManagerApplication(ManagerDataBase managerApplication) {
			this.managerApplication = managerApplication;
		}
		
		public String getStr_facture() {
			str_facture="Facture Client";
				return str_facture;
		}

		public void setStr_facture(String str_facture) {
			this.str_facture = str_facture;
		}

		public boolean isExoneration() {
			return exoneration;
		}

		public void setExoneration(boolean exoneration) {
			this.exoneration = exoneration;
		}
		
}
