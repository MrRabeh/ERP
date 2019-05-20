package ims.session.controller;

import java.util.List;

import ims.model.entities.Article;
import ims.model.entities.BonCommande;
import ims.model.entities.BonLivraison;
import ims.model.entities.Client;
import ims.model.entities.Contact;
import ims.model.entities.Departement;
import ims.model.entities.Employee;
import ims.model.entities.EnumFactureType;
import ims.model.entities.Facture;
import ims.model.entities.FactureFournisseur;
import ims.model.entities.Facture_Article;
import ims.model.entities.Fournisseur;
import ims.model.entities.Produit;
import ims.model.entities.Stock;
import ims.model.entities.TypeArticle;
import ims.service.ModelService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;


@ManagedBean(name = "managerDataBase")
@ApplicationScoped
public class ManagerDataBase {
	
	 @ManagedProperty(value="#{FactureManager}")
	 private ModelService<Facture> managerfacture;
	 
	 @ManagedProperty(value="#{FactFournisseurManager}")
	 private ModelService<FactureFournisseur> managerFactFournisseur;
	
     @ManagedProperty(value="#{FactArticleManage}")
     private ModelService<Facture_Article> managerFactArticle;
    
	 @ManagedProperty(value="#{StockManager}")
	 private ModelService<Stock> managerstock;
	
	 @ManagedProperty(value="#{ClientManager}")
	 private ModelService<Client> managerclient;
	
	 @ManagedProperty(value="#{ContactManager}")
	 private ModelService<Contact> managercontact;
	 
	 @ManagedProperty(value="#{FournisseurManager}")
	 private ModelService<Fournisseur> managerFournisseur;
	 
	 @ManagedProperty(value="#{boncommandeManager}")
	 private ModelService<BonCommande> manageCommande;
	 
	 @ManagedProperty(value="#{ArticleManager}")
	 private ModelService<Article> managerArticle;
	 
	 @ManagedProperty(value="#{ProduitManager}")
	 private ModelService<Produit> managerProduit;
	 
     @ManagedProperty(value="#{TypeArticleManager}")
     private ModelService<TypeArticle> managertypeArticle;
     

	@ManagedProperty(value="#{BonLivraisonManager}")
	private ModelService<BonLivraison> managerlivraison;
	
    @ManagedProperty(value="#{DepartementManager}")
    private ModelService<Departement> managerDept;
    
    @ManagedProperty(value="#{EmployeeManager}")
    private ModelService<Employee> managerEmp;
	 

	 private List<Facture> factures;
	 private List<Facture> facturescontrat;
	 private List<Facture> facturemov;
	 private List<FactureFournisseur> facturesfournisseur;
	 private List<Stock> stocks;
	 private List<Stock> stockslivrer;
	 private List<Client> clients;
	 private List<Fournisseur> fournisseurs;
	 private List<Contact> contactfournisseur;
	 private List<BonCommande> commandes;
	 private List<Article> articles;
	 
	 private List<Produit> produits;
	 private List<TypeArticle> typesArticles;
	 
	 private List<BonLivraison> livraisons;
	 
	 private List<Departement> depts;
	 private List<Employee> emps;
	 
 	
 	@PostConstruct
 	public void init(){
 		
 	}
 	
	public List<Contact> getContactfournisseur() {
		if(contactfournisseur==null)
			contactfournisseur=managercontact.getByNames("fournisseur");
		return contactfournisseur;
	}

	public void setContactfournisseur(List<Contact> contactfournisseur) {
		this.contactfournisseur = contactfournisseur;
	}

	public List<Client> getClients() {
		if(clients==null)
			clients=managerclient.getObject();
		return clients;
	}



	public void setClients(List<Client> clients) {
		this.clients = clients;
	}



	public ModelService<Facture> getManagerfacture() {
		return managerfacture;
	}

	public void setManagerfacture(ModelService<Facture> managerfacture) {
		this.managerfacture = managerfacture;
	}

	public ModelService<Facture_Article> getManagerFactArticle() {
		return managerFactArticle;
	}

	public void setManagerFactArticle(
			ModelService<Facture_Article> managerFactArticle) {
		this.managerFactArticle = managerFactArticle;
	}

	public List<Facture> getFactures() {
		if(factures==null)
			factures=managerfacture.getByNames(EnumFactureType.Simple.toString());
		return factures;
	}

	public void setFactures(List<Facture> factures) {
		this.factures = factures;
	}
	

	public List<Facture> getFacturescontrat() {
		if(facturescontrat==null)
			facturescontrat=managerfacture.getByNames(EnumFactureType.Contrat.toString());
		return facturescontrat;
	}

	public void setFacturescontrat(List<Facture> facturescontrat) {
		this.facturescontrat = facturescontrat;
	}

	public List<Stock> getStocks() {
		if(stocks==null){
			System.out.println("-----GET STOCK-----");
			stocks=managerstock.getByNames("stock");
		}
			
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public List<Stock> getStockslivrer() {
		
		if(stockslivrer==null){
			System.out.println("GET STOCK LIVREE");
			stockslivrer=managerstock.getByNames("livrer");
		}
		
		return stockslivrer;
	}

	public void setStockslivrer(List<Stock> stockslivrer) {
		this.stockslivrer = stockslivrer;
	}

	public ModelService<Stock> getManagerstock() {
		return managerstock;
	}

	public void setManagerstock(ModelService<Stock> managerstock) {
		this.managerstock = managerstock;
	}
	
	

	public List<BonLivraison> getLivraisons() {
		if(livraisons==null)
			livraisons=managerlivraison.getObject();
		return livraisons;
	}

	public void setLivraisons(List<BonLivraison> livraisons) {
		this.livraisons = livraisons;
	}

	public ModelService<Client> getManagerclient() {
		return managerclient;
	}

	public void setManagerclient(ModelService<Client> managerclient) {
		this.managerclient = managerclient;
	}

	public ModelService<Contact> getManagercontact() {
		return managercontact;
	}

	public void setManagercontact(ModelService<Contact> managercontact) {
		this.managercontact = managercontact;
	}

	public ModelService<Fournisseur> getManagerFournisseur() {
		return managerFournisseur;
	}

	public void setManagerFournisseur(ModelService<Fournisseur> managerFournisseur) {
		this.managerFournisseur = managerFournisseur;
	}

	public List<Fournisseur> getFournisseurs() {
		if(fournisseurs==null)
			fournisseurs=managerFournisseur.getObject();
		return fournisseurs;
	}

	public void setFournisseurs(List<Fournisseur> fournisseurs) {
		this.fournisseurs = fournisseurs;
	}

	public List<BonCommande> getCommandes() {
		if(commandes==null)
			commandes=manageCommande.getObject();
		return commandes;
	}

	public void setCommandes(List<BonCommande> commandes) {
		this.commandes = commandes;
	}

	public ModelService<BonCommande> getManageCommande() {
		return manageCommande;
	}

	public void setManageCommande(ModelService<BonCommande> manageCommande) {
		this.manageCommande = manageCommande;
	}

	public List<Facture> getFacturemov() {
		if(facturemov==null){
			factures=null;
			facturescontrat=null;
			facturemov=managerfacture.getObject();
		}	
		return facturemov;
	}

	public void setFacturemov(List<Facture> facturemov) {
		this.facturemov = facturemov;
	}

	public ModelService<FactureFournisseur> getManagerFactFournisseur() {
		return managerFactFournisseur;
	}

	public void setManagerFactFournisseur(
			ModelService<FactureFournisseur> managerFactFournisseur) {
		this.managerFactFournisseur = managerFactFournisseur;
	}

	public List<FactureFournisseur> getFacturesfournisseur() {
		if(facturesfournisseur==null)
			facturesfournisseur=managerFactFournisseur.getObject();
		return facturesfournisseur;
	}

	public void setFacturesfournisseur(List<FactureFournisseur> facturesfournisseur) {
		this.facturesfournisseur = facturesfournisseur;
	}

	public ModelService<Article> getManagerArticle() {
		return managerArticle;
	}

	public void setManagerArticle(ModelService<Article> managerArticle) {
		this.managerArticle = managerArticle;
	}

	public List<Article> getArticles() {
		if(articles==null)
			articles=managerArticle.getObject();
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

	public List<Produit> getProduits() {
		if(produits==null){
			produits=managerProduit.getObject();
			articles=managerArticle.getObject();
		}	
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}

	public List<TypeArticle> getTypesArticles() {
		if(typesArticles==null){
			typesArticles=managertypeArticle.getObject();
			articles=managerArticle.getObject();
		}

		return typesArticles;
	}

	public void setTypesArticles(List<TypeArticle> typesArticles) {
		this.typesArticles = typesArticles;
	}

	public ModelService<TypeArticle> getManagertypeArticle() {
		return managertypeArticle;
	}

	public void setManagertypeArticle(ModelService<TypeArticle> managertypeArticle) {
		this.managertypeArticle = managertypeArticle;
	}

	public ModelService<BonLivraison> getManagerlivraison() {
		return managerlivraison;
	}

	public void setManagerlivraison(ModelService<BonLivraison> managerlivraison) {
		this.managerlivraison = managerlivraison;
	}

	public ModelService<Departement> getManagerDept() {
		return managerDept;
	}

	public void setManagerDept(ModelService<Departement> managerDept) {
		this.managerDept = managerDept;
	}

	public ModelService<Employee> getManagerEmp() {
		return managerEmp;
	}

	public void setManagerEmp(ModelService<Employee> managerEmp) {
		this.managerEmp = managerEmp;
	}

	public List<Departement> getDepts() {
		if(depts==null)
			depts=managerDept.getObject();
		return depts;
	}

	public void setDepts(List<Departement> depts) {
		this.depts = depts;
	}

	public List<Employee> getEmps() {
		if(emps==null)
			emps=managerEmp.getObject();
		return emps;
	}

	public void setEmps(List<Employee> emps) {
		this.emps = emps;
	}
}
