

package ims.session.controller;

import ims.model.entities.Article;
import ims.model.entities.BonCommande;
import ims.model.entities.BonLivraison;
import ims.model.entities.Client;
import ims.model.entities.Facture;
import ims.model.entities.Fournisseur;
import ims.model.entities.Offre;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;





import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;






import org.primefaces.component.chart.bar.BarChart;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;



/**
 *
 * @author rabeh
 */

@ManagedBean
@ApplicationScoped
public class IndexController implements Serializable {

	   /**
		* creator RABEH TARIK
	    **/
	
	private static final long serialVersionUID = 1L;
	
	 @ManagedProperty(value = "#{managerDataBase}")
	 private ManagerDataBase managerApplication;
	 
	 
	public ManagerDataBase getManagerApplication() {
		return managerApplication;
	}

	public void setManagerApplication(ManagerDataBase managerApplication) {
		this.managerApplication = managerApplication;
	}
	
	 	@ManagedProperty(value="#{OffreManager}")
	 	private ModelService<Offre> offre;
	 
	 	@ManagedProperty(value="#{boncommandeManager}")
     	private ModelService<BonCommande> commande;
		
     	@ManagedProperty(value="#{FactureManager}")
     	private ModelService<Facture> facture;
		
		@ManagedProperty(value="#{BonLivraisonManager}")
		private ModelService<BonLivraison> livraison;
		
		
		@ManagedProperty(value="#{FournisseurManager}")
	    private ModelService<Fournisseur> fournisseur;
		
		@ManagedProperty(value="#{ClientManager}")
		private ModelService<Client> client;
		
	    @ManagedProperty(value="#{JDBCManager}")
	    private ModelServiceJDBC managerjdbc;

		private List<Client> clients;
		private List<Fournisseur> fournisseurs;
		private List<Article> listarticle;
		
		private BarChart model=new BarChart();
		
		private CartesianChartModel categoryModel=null;
		private CartesianChartModel categoryModelbc=null;
		private CartesianChartModel categoryModelarticles=null;
		private CartesianChartModel categoryfactureconstat=null;
		
		private ChartSeries factmodel;
		private ChartSeries offremodel;
		private int xangle;
		private String str_reporting;
		
		@PostConstruct
		public void init(){
			System.out.println("------------ INIT INDEX -----------------");
		}
		
		public String getStr_reporting() {
			str_reporting="Reporting";
			return str_reporting;
		}


		public void setStr_reporting(String str_reporting) {
			this.str_reporting = str_reporting;
		}


		@PreDestroy
		public void destroy(){
			clients=null;
			fournisseurs=null;
		}
		

		private void createCategoryModel() {  
	        categoryModel = new CartesianChartModel(); 
	        clients=managerApplication.getClients();
	        factmodel = new ChartSeries(); 
	        offremodel = new ChartSeries();  
	        factmodel.setLabel("Facture"); 
	        offremodel.setLabel("Offre");
	        if(clients.size()>30){
	        	   for(int i=0;i<30;i++){
	   	        	if(!clients.get(i).equals("IMS")){
	   	        		if(clients.get(i).getFactures()!=null){
		   	        		if(clients.get(i).getFactures().size()!=0){
		   	        			factmodel.set(clients.get(i).getSociete(),clients.get(i).getFactures().size());
		   	        			offremodel.set(clients.get(i).getSociete(),clients.get(i).getOffres().size()); 
		   	        		}
	   	        		}
	   	        			
	   	        	}
	   	        }
	                 
	        }else{
	        	  for(int i=0;i<clients.size();i++){
		   	        	if(!clients.get(i).equals("IMS")){
		   	        		if(clients.get(i).getFactures()!=null){
		   	        			if(clients.get(i).getFactures().size()!=0){
		   	        				factmodel.set(clients.get(i).getSociete(),clients.get(i).getFactures().size());
		   	        				offremodel.set(clients.get(i).getSociete(),clients.get(i).getOffres().size());  
		   	        			}
		   	        		}	
		   	        	}
		   	        }
		                 
	        }
	        categoryModel.addSeries(factmodel);  
	       categoryModel.addSeries(offremodel); 
	       System.out.println("-----------------createCategoryModel FIn---------------------");
	    } 

		public CartesianChartModel getCategoryModel() {
			if(categoryModel==null)
				createCategoryModel();	
			return categoryModel;
		}

		public void setCategoryModel(CartesianChartModel categoryModel) {
			this.categoryModel = categoryModel;
		}

		private void createCategoryModelbc() {
			System.out.println("createCategoryModelbc");
	        categoryModelbc = new CartesianChartModel(); 
	        fournisseurs=managerApplication.getFournisseurs();
	        
	       // ChartSeries boys = new ChartSeries();  
	        ChartSeries girls = new ChartSeries();  
	        //boys.setLabel("Bon commande"); 
	        girls.setLabel("Facture"); 
	        System.out.println("createCategoryModelbc .");
	        if(fournisseurs.size()>30){
	            System.out.println("createCategoryModelbc ..");
	        	   for(int i=0;i<30;i++){
	   	        				//boys.set(fournisseurs.get(i).getNomsociete(),fournisseurs.get(i).getCommandes().size());
	   	        				fournisseurs.get(i).setNombrefacturefournisseur(managerjdbc.getNombreFactFournisseurByFournisseur(fournisseurs.get(i)));
	   	        				
	   	        				if(fournisseurs.get(i).getNombrefacturefournisseur()!=0)
	   	        				girls.set(fournisseurs.get(i).getNomsociete(),fournisseurs.get(i).getNombrefacturefournisseur());  
	   	        		}			    	 
		   	        	
	        }else{
	        	System.out.println("createCategoryModelbc ..");
	        	for(int i=0;i<fournisseurs.size();i++){
	        		System.out.println("createCategoryModelbc ...");
	   	        				//boys.set(fournisseurs.get(i).getNomsociete(),fournisseurs.get(i).getCommandes().size());
	   	        				fournisseurs.get(i).setNombrefacturefournisseur(managerjdbc.getNombreFactFournisseurByFournisseur(fournisseurs.get(i)));
	   	        				if(fournisseurs.get(i).getNombrefacturefournisseur()!=0)
	   	        					girls.set(fournisseurs.get(i).getNomsociete(),fournisseurs.get(i).getNombrefacturefournisseur());  
	   	        			}
	        	System.out.println("createCategoryModelbc ....");
	        //categoryModelbc.addSeries(boys); 
	        categoryModelbc.addSeries(girls); 
	        }
	        System.out.println("-------------------------createCategoryModelbc "+categoryModelbc.getSeries().size()+" FIN ------------------");
		}
		
		public CartesianChartModel getCategoryModelbc() {
				createCategoryModelbc();
			return categoryModelbc;
		}

		public void setCategoryModelbc(CartesianChartModel categoryModelbc) {
			this.categoryModelbc = categoryModelbc;
		}
		
		private void createCategoryModelArticle() {  
			categoryModelarticles = new CartesianChartModel(); 
	        listarticle=managerjdbc.getCountMaxAchat();
	        
	        ChartSeries boys = new ChartSeries();  
	        boys.setLabel("Articles"); 
	        if(listarticle.size()>20){
	        	for(int i=0;i<20;i++)
	   	        				boys.set(listarticle.get(i).getRef(),listarticle.get(i).getNbrAchat());
	        }else{
	        	for(int i=0;i<listarticle.size();i++)
	   	        				boys.set(listarticle.get(i).getRef(),listarticle.get(i).getNbrAchat());
	        }
	        	categoryModelarticles.addSeries(boys); 
	        	
	        	System.out.println("--------------createCategoryModelArticle FIN ---------------------");
	    } 
		
		public CartesianChartModel getCategoryModelarticles() {
				createCategoryModelArticle();
			return categoryModelarticles;
		}

		private void createCategoryfactureConstat() {  
			categoryfactureconstat = new CartesianChartModel(); 
	        listarticle=managerjdbc.getCountMaxAchat();
	        
	        ChartSeries constat = new ChartSeries();  
	        constat.setLabel("Facture Constater"); 
	        ChartSeries nonconstat  = new ChartSeries();  
	        nonconstat.setLabel("Facture Non Constater"); 

	        constat.set("Facture Constater",managerjdbc.getCountFactureConstater());
	        nonconstat.set("Facture Non Constater",managerjdbc.getCountFactureNonconstater());
	
	        categoryfactureconstat.addSeries(constat); 
	        categoryfactureconstat.addSeries(nonconstat); 
	        System.out.println("-------createCategoryfactureConstat FIN--------");
	    }

		public void setCategoryModelarticles(CartesianChartModel categoryModelarticles) {
			this.categoryModelarticles = categoryModelarticles;
		}

		public CartesianChartModel getCategoryfactureconstat() {
				createCategoryfactureConstat();
			return categoryfactureconstat;
		}


		public void setCategoryfactureconstat(CartesianChartModel categoryfactureconstat) {
			this.categoryfactureconstat = categoryfactureconstat;
		}


		public IndexController() {
			super();
			System.out.println(" Constructeur ..... IndexController");
		}

		public ModelService<Offre> getOffre() {
			return offre;
		}

		public void setOffre(ModelService<Offre> offre) {
			this.offre = offre;
		}

		public ModelService<BonCommande> getCommande() {
			return commande;
		}

		public void setCommande(ModelService<BonCommande> commande) {
			this.commande = commande;
		}

		public ModelService<Facture> getFacture() {
			return facture;
		}

		public void setFacture(ModelService<Facture> facture) {
			this.facture = facture;
		}

		public ModelService<BonLivraison> getLivraison() {
			return livraison;
		}

		public void setLivraison(ModelService<BonLivraison> livraison) {
			this.livraison = livraison;
		}

		public ModelService<Fournisseur> getFournisseur() {
			return fournisseur;
		}

		public void setFournisseur(ModelService<Fournisseur> fournisseur) {
			this.fournisseur = fournisseur;
		}

		public ModelService<Client> getClient() {
			return client;
		}

		public void setClient(ModelService<Client> client) {
			this.client = client;
		}

		public BarChart getModel() {
			return model;
		}

		public void setModel(BarChart model) {
			this.model = model;
		}



		public List<Client> getClients() {
			return clients;
		}



		public void setClients(List<Client> clients) {
			this.clients = clients;
		}

		public List<Fournisseur> getFournisseurs() {
			return fournisseurs;
		}

		public void setFournisseurs(List<Fournisseur> fournisseurs) {
			this.fournisseurs = fournisseurs;
		}
		

		public void redirectdevis(){
			  try {
	    	ExternalContext ec;
			 ec = FacesContext.getCurrentInstance().getExternalContext();
					ec.redirect(ec.getRequestContextPath() + "/moffre/index.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	
		public void redirectcorrespondance(){
			  try {
			    	ExternalContext ec;
					 ec = FacesContext.getCurrentInstance().getExternalContext();
							ec.redirect(ec.getRequestContextPath() + "/mfactures/ListefactureDetail.xhtml");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		}
		
		public void redirectccommende(){
			  try {
	    	ExternalContext ec;
			 ec = FacesContext.getCurrentInstance().getExternalContext();
					ec.redirect(ec.getRequestContextPath() + "/mboncommande/listBoncommande.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		public void redirectlivraison(){
			  try {
	    	ExternalContext ec;
			 ec = FacesContext.getCurrentInstance().getExternalContext();
					ec.redirect(ec.getRequestContextPath() + "/mbonlivraison/index.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		public void redirectfactclient(){
			  try {
	    	ExternalContext ec;
			 ec = FacesContext.getCurrentInstance().getExternalContext();
					ec.redirect(ec.getRequestContextPath() + "/mfactures/index.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		public void redirectfactcontrat(){
			  try {
	    	ExternalContext ec;
			 ec = FacesContext.getCurrentInstance().getExternalContext();
					ec.redirect(ec.getRequestContextPath() + "/mfactures/FactureContrat.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		public void redirectfactfournisseur(){
			  try {
	    	ExternalContext ec;
			 ec = FacesContext.getCurrentInstance().getExternalContext();
					ec.redirect(ec.getRequestContextPath() + "/mfactures/listfactureFournisseur.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		public void redirectArticle(){
			  try {
	    	ExternalContext ec;
			 ec = FacesContext.getCurrentInstance().getExternalContext();
					ec.redirect(ec.getRequestContextPath() + "/mArticles/index.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		public void redirectProduit(){
			  try {
	    	ExternalContext ec;
			 ec = FacesContext.getCurrentInstance().getExternalContext();
					ec.redirect(ec.getRequestContextPath() + "/mArticles/produit.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		public void redirectStock(){
			  try {
	    	ExternalContext ec;
			 ec = FacesContext.getCurrentInstance().getExternalContext();
					ec.redirect(ec.getRequestContextPath() + "/mArticles/stock.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		public void redirectMovclient(){
			  try {
	    	ExternalContext ec;
			 ec = FacesContext.getCurrentInstance().getExternalContext();
					ec.redirect(ec.getRequestContextPath() + "/mcompta/index.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		public void redirectMovFournisseur(){
			  try {
	    	ExternalContext ec;
			 ec = FacesContext.getCurrentInstance().getExternalContext();
					ec.redirect(ec.getRequestContextPath() + "/mcompta/mocfactfournisseur.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		public void redirecthelpdesk(){
			 try {
			    	ExternalContext ec;
					 ec = FacesContext.getCurrentInstance().getExternalContext();
							ec.redirect(ec.getRequestContextPath() + "/mintervention/usershelpdesk.xhtml");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		}
		
		public void redirectclient(){
			  try {
	    	ExternalContext ec;
			 ec = FacesContext.getCurrentInstance().getExternalContext();
					ec.redirect(ec.getRequestContextPath() + "/mclient/index.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		public void redirectfournisseur(){
			  try {
	    	ExternalContext ec;
			 ec = FacesContext.getCurrentInstance().getExternalContext();
					ec.redirect(ec.getRequestContextPath() + "/mfournisseur/index.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		public void redirectReporting(){
			  try {
	    	ExternalContext ec;
			 ec = FacesContext.getCurrentInstance().getExternalContext();
					ec.redirect(ec.getRequestContextPath() + "/mreporting/index.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	    public int getXangle() {
	    	xangle=clients.size();
			return xangle;
		}



		public void setXangle(int xangle) {
			this.xangle = xangle;
		}


		public ModelServiceJDBC getManagerjdbc() {
			return managerjdbc;
		}


		public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
			this.managerjdbc = managerjdbc;
		}


		public List<Article> getListarticle() {
			return listarticle;
		}


		public void setListarticle(List<Article> listarticle) {
			this.listarticle = listarticle;
		}


		public ChartSeries getFactmodel() {
			return factmodel;
		}


		public void setFactmodel(ChartSeries factmodel) {
			this.factmodel = factmodel;
		}


		public ChartSeries getOffremodel() {
			return offremodel;
		}


		public void setOffremodel(ChartSeries offremodel) {
			this.offremodel = offremodel;
		}



}

	     
	 
	   