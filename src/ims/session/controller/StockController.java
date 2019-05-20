package ims.session.controller;

import ims.model.entities.Article;
import ims.model.entities.Client;
import ims.model.entities.Fournisseur;
import ims.model.entities.Stock;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@SessionScoped
public class StockController implements Serializable {

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
	
	@ManagedProperty(value="#{StockManager}")
	private ModelService<Stock> manager;
	
	@ManagedProperty(value="#{ArticleManager}")
    private ModelService<Article> managerarticle;
	
	@ManagedProperty(value="#{FournisseurManager}")
    private ModelService<Fournisseur> managerfournisseur;
	
	@ManagedProperty(value="#{ClientManager}")
	private ModelService<Client> managerclient;
	
	
	@ManagedProperty(value="#{JDBCManager}")
	private ModelServiceJDBC managerjdbc;
	
	
	private int idarticle=0;
	private String numserie;
	private int idclient;
	private float qte=1;
	private int idf;
	
	private String idarticleedit="";
	private String idclientedit="";
	private String idfedit="";
	
	private Stock stock=new Stock();
	private Stock stocktempo=new Stock();
	private List<Stock> stocks;
	private List<Stock> filtredstocks;
	private List<Stock> stocksup0=new ArrayList<Stock>();
	private List<Stock> stockslivrer;
	
	private String str_stock;
	

	
	public String getStr_stock() {
		str_stock="Stock";
		return str_stock;
	}

	public void setStr_stock(String str_stock) {
		this.str_stock = str_stock;
	}

	public void actualiser(){
		System.out.println(" -----actualiser------");
		managerApplication.setStockslivrer(null);
		managerApplication.setStocks(null);
		System.out.println(" -----actualiser------");
	}
	  
	public void annuler(){
		  FacesMessage msg;
		  try {
				numserie="";
				qte=1;
				idarticle=0;
				idclient=0;
				idf=0;
		  		msg = new FacesMessage("Stock Annuler avec Success");
	    		FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
	  		msg = new FacesMessage("Exception:",e.getLocalizedMessage());
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	  }
	  
	public void onCellEdit(){
		  FacesMessage msg;
		  try {
			  
			  managerApplication.setStocks(null);
			  managerApplication.setStockslivrer(null);
			  //stock.setArticle(managerarticle.getByName(stock.getArticle().getRef()));
			  managerjdbc.updateStock(stock);
		  		msg = new FacesMessage("Stock est Modifier avec Success");
	    		FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
	  		msg = new FacesMessage("Exception:",e.getLocalizedMessage());
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		  
	  }
	  
	public String prepareView(){
		  FacesMessage msg;
		  try {
			  System.out.println("-------------TEST----------");
			  //stock=manager.getObject(stock.getId());
			  System.out.println(stock);
			  idarticle=stock.getArticle().getIdArticle();
			  idclient=stock.getClient().getIdclient();
			  idf=stock.getFournisseur().getIdfournisseur();
			  return "viewstock??faces-redirect=true";
		} catch (Exception e) {
			System.out.println("----Probleme de Recuperation----"+e.getLocalizedMessage()+"-------------");
	  		msg = new FacesMessage("Exception:",e.getMessage());
    		FacesContext.getCurrentInstance().addMessage(null, msg);
			return "stock??faces-redirect=true";
		}

	  }
	
	
	public void insert(){
		FacesMessage msg;
		try {
			  managerApplication.setStocks(null);
			stocktempo.setQte(qte);
			stocktempo.setNumserie(numserie);
			stocktempo.setArticle(managerarticle.getObject(idarticle));
			stocktempo.setClient(managerclient.getObject(idclient));
			stocktempo.setClientFinal(managerclient.getObject(idclient));
			stocktempo.setFournisseur(managerfournisseur.getObject(idf));
			stocktempo.setDatestock(new Date());
			stocktempo.setQteinital(qte);
			manager.insertObject(stocktempo);
	    	msg = new FacesMessage("Article est créé dans le stock");
	    	FacesContext.getCurrentInstance().addMessage(null, msg);
			numserie="";
			qte=1;
			idarticle=0;
		} catch (Exception e) {
    		msg = new FacesMessage("Exception ","contacter l'administrateur");
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	public void update(){
		FacesMessage msg;
		try {
			  managerApplication.setStocks(null);
			  stock.setArticle(managerarticle.getByName(stock.getArticle().getRef()));
			  System.out.println(stock.getArticle().getRef());
			managerjdbc.updateStock(stock);
			stocks=manager.getObject();
	  		msg = new FacesMessage("le Stock est Bien Modifier");
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
	  		msg = new FacesMessage("Probleme de l'insertion","contacter l'administrateur");
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void deletestock(){
		FacesMessage msg;
		try {
			  managerApplication.setStocks(null);
			boolean op=managerjdbc.deleteStock(stock);
	  		if(op==true){
				msg = new FacesMessage("le Stock est supprimer");
	    		FacesContext.getCurrentInstance().addMessage(null, msg);
	  		}else{
				msg = new FacesMessage("quelque quantité déja livré","Vérfier Bon livraison");
	    		FacesContext.getCurrentInstance().addMessage(null, msg);
	    		
	  		}
		} catch (Exception e) {
	  		msg = new FacesMessage("Probleme de l'insertion","contacter l'administrateur");
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public ModelService<Stock> getManager() {
		return manager;
	}
	public void setManager(ModelService<Stock> manager) {
		this.manager = manager;
	}
	public ModelService<Article> getManagerarticle() {
		return managerarticle;
	}
	public void setManagerarticle(ModelService<Article> managerarticle) {
		this.managerarticle = managerarticle;
	}
	public ModelService<Fournisseur> getManagerfournisseur() {
		return managerfournisseur;
	}
	public void setManagerfournisseur(ModelService<Fournisseur> managerfournisseur) {
		this.managerfournisseur = managerfournisseur;
	}
	public ModelService<Client> getManagerclient() {
		return managerclient;
	}
	public void setManagerclient(ModelService<Client> managerclient) {
		this.managerclient = managerclient;
	}
	public int getIdarticle() {
		return idarticle;
	}
	public void setIdarticle(int idarticle) {
		this.idarticle = idarticle;
	}
	public String getNumserie() {
		return numserie;
	}
	public void setNumserie(String numserie) {
		this.numserie = numserie;
	}
	public int getIdclient() {
		return idclient;
	}
	public void setIdclient(int idclient) {
		this.idclient = idclient;
	}
	public int getIdf() {
		return idf;
	}
	public void setIdf(int idf) {
		this.idf = idf;
	}
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	public List<Stock> getStocks() {
		stocks=managerApplication.getStocks();
		return stocks;
	}
	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}
	public float getQte() {
		return qte;
	}
	public void setQte(float qte) {
		this.qte = qte;
	}
	
	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}
	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}
	public List<Stock> getStocksup0() {
				stocksup0=managerApplication.getStocks();
				return stocksup0;
	}
	public void setStocksup0(List<Stock> stocksup0) {
		this.stocksup0 = stocksup0;
	}

	public List<Stock> getFiltredstocks() {
		return filtredstocks;
	}

	public void setFiltredstocks(List<Stock> filtredstocks) {
		this.filtredstocks = filtredstocks;
	}

	public String getIdarticleedit() {
		return idarticleedit;
	}

	public void setIdarticleedit(String idarticleedit) {
		this.idarticleedit = idarticleedit;
	}

	public String getIdclientedit() {
		return idclientedit;
	}

	public void setIdclientedit(String idclientedit) {
		this.idclientedit = idclientedit;
	}

	public String getIdfedit() {
		return idfedit;
	}

	public void setIdfedit(String idfedit) {
		this.idfedit = idfedit;
	}

	public Stock getStocktempo() {
		return stocktempo;
	}

	public void setStocktempo(Stock stocktempo) {
		this.stocktempo = stocktempo;
	}

	public List<Stock> getStockslivrer() {
		stockslivrer=managerApplication.getStockslivrer();
		return stockslivrer;
	}

	public void setStockslivrer(List<Stock> stockslivrer) {
		this.stockslivrer = stockslivrer;
	}

	
}
