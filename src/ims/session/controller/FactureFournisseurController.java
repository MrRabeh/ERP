package ims.session.controller;

import ims.model.entities.BonCommande;
import ims.model.entities.Boncommande_Article;
import ims.model.entities.FactureFournisseur;
import ims.model.entities.FactureFournisseur_articles;
import ims.model.entities.TypePaiement;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class FactureFournisseurController implements Serializable {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
    
	@ManagedProperty(value="#{boncommandeManager}")
	private ModelService<BonCommande> bcmanager;
	
    @ManagedProperty(value="#{BoncommandeArticleManager}")
    private ModelService<Boncommande_Article> managerarticle;
    
    @ManagedProperty(value="#{FactFournisseurArticleManager}")
    private ModelService<FactureFournisseur_articles> managerFactArticle;
    
    @ManagedProperty(value="#{FactFournisseurManager}")
    private ModelService<FactureFournisseur> managerFactFournisseur;
    
	
    @ManagedProperty(value="#{TypepaiementManager}")
    private ModelService<TypePaiement> managerTypePaiement;
	
	private FactureFournisseur factf=new FactureFournisseur();
	private List<FactureFournisseur> facts;
	
	private List<BonCommande> listbn=new ArrayList<BonCommande>();
	
	private List<FactureFournisseur_articles> articles=new ArrayList<FactureFournisseur_articles>();
	private FactureFournisseur_articles articletempo=new FactureFournisseur_articles();
	private FactureFournisseur_articles articlesupp=new FactureFournisseur_articles();
	
	private String bncommandeId="";
	private String labcontsat="";
	private int idtypepaiement;
	private int commandeacien;
	
	private String str_facture;
	
    @PostConstruct
    public void init() {
    	System.out.println("-----INIT FACT FOURNISSEUR----");
		  facts=managerFactFournisseur.getObject();
		  System.out.println("-----FIN INIT FACT FOURNISSEUR----");
		  
    }
	
	
	public String getStr_facture() {
		  str_facture="Facture Fournisseur";
		return str_facture;
	}

	public void setStr_facture(String str_facture) {
		this.str_facture = str_facture;
	}

	public void actualiser(){
	    	facts=null;
	    }
	
	public String prepareView(){
		idtypepaiement=factf.getTypepaiement().getId();
		bncommandeId=""+factf.getCommandefournissuer().getId();
		commandeacien=factf.getCommandefournissuer().getId();
		labcontsat=factf.getConstater();
		articles=managerFactArticle.getObjects(factf.getId());
		articletempo=new FactureFournisseur_articles();
		return "viewFactureFournisseur?faces-redirect=true";
	}
	
	public String preparecreer(){
		try {
		 	idtypepaiement=0;
   		 	bncommandeId="0";
			labcontsat="";
			factf=new FactureFournisseur();
			articles=new ArrayList<FactureFournisseur_articles>();
			articletempo=new FactureFournisseur_articles();
			
			return "insertfactureFournisseur?faces-redirect=true";
		} catch (Exception e) {
			return "index?faces-redirect=true";
		}
	}
	
	public void onCellarticle(){
		try {
			articletempo.setPt(articletempo.getQte()*articletempo.getPu());
			factf.setTotalht(0);
			for (int i = 0; i < articles.size(); i++) {
				factf.setTotalht(factf.getTotalht()+articles.get(i).getPt());
			}
			factf.setTva(factf.getTotalht()*0.2);
			factf.setTotalttc(factf.getTotalht()+factf.getTva());
			factf.setTotalNet(factf.getFraitransport()+factf.getTotalttc());
			
			FacesMessage msg = new FacesMessage("La ligne est Modifier avec success");
   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Exception contacter Administration");
   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}
	
	public void onCellarticlemodif(){
		try {
			articletempo.setPt(articletempo.getQte()*articletempo.getPu());
			factf.setTotalht(0);
			for (int i = 0; i < articles.size(); i++) {
				factf.setTotalht(factf.getTotalht()+articles.get(i).getPt());
			}
			if(factf.getTypepaiement().getTypepaiement().equals("MAD")){
				factf.setTva(factf.getTotalht()*0.2);
				factf.setTotalttc(factf.getTotalht()+factf.getTva());
				factf.setTotalNet(factf.getFraitransport()+factf.getTotalttc());
			}else{
				factf.setTva(0);
				factf.setTotalttc(factf.getTotalht()+factf.getTva()-factf.getMontantdavoire());
				factf.setTotalNet(factf.getFraitransport()+factf.getTotalttc());
			}
		
			FacesMessage msg = new FacesMessage("La ligne est Modifier avec success");
   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Exception Enregistrement");
   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}
	
	public String annuleropfacture(){
		try {
			return "listfactureFournisseur?faces-redirect=true";
		} catch (Exception e) {
			return "listfactureFournisseur?faces-redirect=true";
		}
	}
	
	public void delete(){
		facts=null;
		  FacesMessage msg;
		  try {
			boolean op=managerjdbc.deleteFactureFourniseur(factf);
			if(op==true)
			 msg = new FacesMessage("Facture Supprimer Avec Success");
			else
				 msg = new FacesMessage("Probleme de Suppression");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			 msg = new FacesMessage("Exception","contacter l'administration");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
		
	public void insert(){
		facts=null;
		try {
			BonCommande commande=bcmanager.getObject(Integer.parseInt(bncommandeId));
			factf.setTypepaiement(managerTypePaiement.getObject(idtypepaiement));
			
			factf.setCommandefournissuer(commande);
			factf.setMontantrester(factf.getTotalNet());
			factf.setConstater("Non");
			factf.setDateconstater(factf.getDatepevisionnel());
			factf.setAnnuler(false);
			factf.setActiver(true);
			factf.setTypepaiement(factf.getCommandefournissuer().getTypepaiement());
			SimpleDateFormat dt = new SimpleDateFormat("yyyy");
			factf.setObjetyears(managerjdbc.getYears(Integer.parseInt(dt.format(factf.getDatefacture()))));
			if(!factf.getTypepaiement().getTypepaiement().equals("MAD"))
				{
					factf.setTva(0);
					factf.setTotalttc(factf.getTotalht()-factf.getMontantdavoire());
					factf.setTotalNet(factf.getTotalht()+factf.getFraitransport());
					
				}
			managerFactFournisseur.insertObject(factf);
			managerjdbc.factureeCommande(commande);
			for (int i = 0; i < articles.size(); i++) {
				articles.get(i).setTva(20);
				articles.get(i).setFacture(factf);
				managerFactArticle.insertObject(articles.get(i));
			}
			FacesMessage msg = new FacesMessage("Facture Fournisseur est bien enregistrer");
   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
   		 	facts=null;
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Exception Enregistrement");
   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void update(){
		
		facts=null;
		try {
			BonCommande commandeModifier=bcmanager.getObject(commandeacien);
			BonCommande commandefacturee=bcmanager.getObject(Integer.parseInt(bncommandeId));
			factf.setCommandefournissuer(commandefacturee);
			managerjdbc.deleteArticlesToFactFournisseur(factf);
			for (int i = 0; i < articles.size(); i++) {
				articles.get(i).setTva(20);
				articles.get(i).setFacture(factf);
				managerFactArticle.insertObject(articles.get(i));
				factf.setTotalht(articles.get(i).getPt());
			}
			factf.setTypepaiement(managerTypePaiement.getObject(idtypepaiement));
			managerjdbc.factureeCommande(commandefacturee);
			if(!factf.getTypepaiement().getTypepaiement().equals("MAD"))
			{
				factf.setTva(0);
				factf.setTotalttc(factf.getTotalht()-factf.getMontantdavoire());
				factf.setTotalNet(factf.getTotalht()+factf.getFraitransport());
			}else{
				factf.setTva(factf.getTotalht()*0.2);
				factf.setTotalttc(factf.getTotalht()+factf.getTva()-factf.getMontantdavoire());
				factf.setTotalNet(factf.getTotalttc()+factf.getFraitransport());
			}
			SimpleDateFormat dt = new SimpleDateFormat("yyyy");
			factf.setObjetyears(managerjdbc.getYears(Integer.parseInt(dt.format(factf.getDatefacture()))));
			managerjdbc.update(factf);
			System.out.println("update success");
			facts=null;
			factf=managerFactFournisseur.getObject(factf.getId());
			System.out.println("Total TTC"+factf.getTotalttc());
			managerjdbc.Encourscommande(commandeModifier,factf);
			FacesMessage msg = new FacesMessage("Facture Fournisseur est modifier avec success");
   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Exception");
   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void eventchangeBCID(){
		try {
			articles=null;
			articles=new ArrayList<FactureFournisseur_articles>();
			if(!bncommandeId.equals("0")){
				BonCommande commande=bcmanager.getObject(Integer.parseInt(bncommandeId));
				List<Boncommande_Article> bcarticles=managerarticle.getObjects(Integer.parseInt(bncommandeId));
				for(int i=0;i<bcarticles.size();i++){
					articles.add(new FactureFournisseur_articles(bcarticles.get(i).getArticle(),bcarticles.get(i).getDesignation(),bcarticles.get(i).getPu(),bcarticles.get(i).getQte(),bcarticles.get(i).getPt()));
				}
				System.out.println("size=>"+articles.size());
				System.out.println("ID BC");
				factf.setTotalht(commande.getTotalht());
				factf.setTva(commande.getTva());
				factf.setTotalttc(commande.getTotalttc());
				factf.setFraitransport(commande.getFraitransport());
				factf.setTotalNet(commande.getTotalnet());
			}else
			{
				factf.setTotalht(0);
				factf.setTva(0);
				factf.setTotalttc(0);
				factf.setFraitransport(0);
				factf.setTotalNet(0);
			}
			articletempo=null;
		} catch (Exception e) {
			articles=null;
			articles=new ArrayList<FactureFournisseur_articles>();
			factf.setTotalht(0);
			factf.setTva(0);
			factf.setTotalttc(0);
			factf.setFraitransport(0);
			factf.setTotalNet(0);
		}
		
	}
	
	public void supparticle(){
		System.out.println("ID Article=====>"+articlesupp.getId());
		articles.remove(articlesupp);
		factf.setTotalht(factf.getTotalht()-articlesupp.getPt());
		factf.setTva(factf.getTotalht()*0.2);
		factf.setTotalttc(factf.getTotalht()+factf.getTva()-factf.getMontantdavoire());
		factf.setTotalNet(factf.getFraitransport()+factf.getTotalttc());
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public FactureFournisseur getFactf() {
		return factf;
	}

	public void setFactf(FactureFournisseur factf) {
		this.factf = factf;
	}

	public List<FactureFournisseur> getFacts() {
		if(facts==null)
			facts=managerFactFournisseur.getObject();
		return facts;
	}

	public void setFacts(List<FactureFournisseur> facts) {
		this.facts = facts;
	}

	public List<BonCommande> getListbn() {
		listbn=bcmanager.getObject();
		return listbn;
	}

	public void setListbn(List<BonCommande> listbn) {
		this.listbn = listbn;
	}

	public ModelService<BonCommande> getBcmanager() {
		return bcmanager;
	}

	public void setBcmanager(ModelService<BonCommande> bcmanager) {
		this.bcmanager = bcmanager;
	}

	public String getBncommandeId() {
		return bncommandeId;
	}

	public void setBncommandeId(String bncommandeId) {
		this.bncommandeId = bncommandeId;
	}

	public List<FactureFournisseur_articles> getArticles() {
		return articles;
	}

	public void setArticles(List<FactureFournisseur_articles> articles) {
		this.articles = articles;
	}

	public FactureFournisseur_articles getArticletempo() {
		return articletempo;
	}

	public void setArticletempo(FactureFournisseur_articles articletempo) {
		this.articletempo = articletempo;
	}

	public ModelService<Boncommande_Article> getManagerarticle() {
		return managerarticle;
	}

	public void setManagerarticle(ModelService<Boncommande_Article> managerarticle) {
		this.managerarticle = managerarticle;
	}

	public ModelService<FactureFournisseur_articles> getManagerFactArticle() {
		return managerFactArticle;
	}

	public void setManagerFactArticle(
			ModelService<FactureFournisseur_articles> managerFactArticle) {
		this.managerFactArticle = managerFactArticle;
	}

	public ModelService<FactureFournisseur> getManagerFactFournisseur() {
		return managerFactFournisseur;
	}

	public void setManagerFactFournisseur(
			ModelService<FactureFournisseur> managerFactFournisseur) {
		this.managerFactFournisseur = managerFactFournisseur;
	}

	public ModelService<TypePaiement> getManagerTypePaiement() {
		return managerTypePaiement;
	}

	public void setManagerTypePaiement(
			ModelService<TypePaiement> managerTypePaiement) {
		this.managerTypePaiement = managerTypePaiement;
	}

	public String getLabcontsat() {
		return labcontsat;
	}

	public void setLabcontsat(String labcontsat) {
		this.labcontsat = labcontsat;
	}

	public FactureFournisseur_articles getArticlesupp() {
		return articlesupp;
	}

	public void setArticlesupp(FactureFournisseur_articles articlesupp) {
		this.articlesupp = articlesupp;
	}

	public int getIdtypepaiement() {
		return idtypepaiement;
	}

	public void setIdtypepaiement(int idtypepaiement) {
		this.idtypepaiement = idtypepaiement;
	}
	
	
	
}
