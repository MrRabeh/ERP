package ims.session.controller;

import ims.model.dao.Impl.DataIms;
import ims.model.entities.Article;
import ims.model.entities.BonCommande;
import ims.model.entities.CategorieArticle;
import ims.model.entities.CategorieIISociete;
import ims.model.entities.CoordonneeBancaire;
import ims.model.entities.Facture;
import ims.model.entities.Langue;
import ims.model.entities.Offre;
import ims.model.entities.Offre_Article;
import ims.model.entities.Pays;
import ims.model.entities.Produit;
import ims.model.entities.Secteur;
import ims.model.entities.TypeArticle;
import ims.model.entities.TypeFacture;
import ims.model.entities.TypeOffre;
import ims.model.entities.TypePaiement;
import ims.model.entities.Utilisateur;
import ims.model.entities.Ville;
import ims.model.entities.years;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.hibernate.classic.Session;

@ManagedBean
@RequestScoped
public class InstallController implements Serializable {

	   /**
		* creator RABEH TARIK
	    **/
	private static final long serialVersionUID = 1L;
	
	

	@ManagedProperty(value="#{FactureManager}")
	private ModelService<Facture> managerfacture;
	
	@ManagedProperty(value="#{boncommandeManager}")
	private ModelService<BonCommande> managerbc;
	
	@ManagedProperty(value="#{yearsManager}")
    private ModelService<years> manageryears;

	@ManagedProperty(value="#{userManager}")
    private ModelService<Utilisateur> manageruser;
    
    @ManagedProperty(value="#{CategorieArticleManager}")
    private ModelService<CategorieArticle> mcategorieArticle;
    
    @ManagedProperty(value="#{TypeArticleManager}")
    private ModelService<TypeArticle> mTypeArticle;
    
    @ManagedProperty(value="#{LangueManager}")
    private ModelService<Langue> managerlangue;
    
    @ManagedProperty(value="#{CategorieIIManager}")
    private ModelService<CategorieIISociete> mcategorieclient;
    
    @ManagedProperty(value="#{SecteurManager}")
    private ModelService<Secteur> managersecteur;
    
    @ManagedProperty(value="#{PaysManager}")
    private ModelService<Pays> managerpays;
    
    @ManagedProperty(value="#{VilleManager}")
    private ModelService<Ville> managerville;
    
    @ManagedProperty(value="#{TypefactManager}")
    private ModelService<TypeFacture> managerTypefacture;
    
    @ManagedProperty(value="#{CoordonneeBanqueManager}")
    private ModelService<CoordonneeBancaire> managercoord;
    
    @ManagedProperty(value="#{TypeOffreManager}")
    private ModelService<TypeOffre> managertypeoffre;
    
    @ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
    
	@ManagedProperty(value="#{CategorieArticleManager}")
	private ModelService<CategorieArticle> managerC;
	
	@ManagedProperty(value="#{TypeArticleManager}")
	private ModelService<TypeArticle> managertype;
	
	@ManagedProperty(value="#{ProduitManager}")
	private ModelService<Produit> managerProduit;
	
	@ManagedProperty(value="#{ArticleManager}")
    private ModelService<Article> managerarticle;
	
    @ManagedProperty(value="#{TypepaiementManager}")
    private ModelService<TypePaiement> managerdevise;
	
    @ManagedProperty(value="#{OffreManager}")
    private ModelService<Offre> manageroffre;
    
	public String install(){
		
		try {
			SimpleDateFormat dt = new SimpleDateFormat("yyyy"); 
			System.out.println("Install.");
			Session session=DataIms.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.close();
			
			Utilisateur user=new Utilisateur("admin", "admin", "admin");
			user.setCle("IT1BBL-RUXEV-M0ZRJ-66ASV-3QX4");
			
		manageruser.insertObject(user);
		System.out.println("Install Utilisateur Par Defaut avec success");
		TypeArticle typearticle=new TypeArticle();
		typearticle.setType("Autre");
		mTypeArticle.insertObject(typearticle);
		typearticle.setType("Bonus");
		mTypeArticle.insertObject(typearticle);
		typearticle.setType("HW");
		mTypeArticle.insertObject(typearticle);
		typearticle.setType("SW");
		mTypeArticle.insertObject(typearticle);
		typearticle.setType("HW/SW");
		mTypeArticle.insertObject(typearticle);
		typearticle.setType("HW/SW");
		mTypeArticle.insertObject(typearticle);
		System.out.println("Install Type Article Par Defaut avec success");
		CategorieArticle cat=new CategorieArticle();
		
		cat.setCategorie("Achat/vente");
		mcategorieArticle.insertObject(cat);
		
		cat.setCategorie("Bonus");
		mcategorieArticle.insertObject(cat);
		
		cat.setCategorie("Projet");
		mcategorieArticle.insertObject(cat);
		
		cat.setCategorie("Service");
		mcategorieArticle.insertObject(cat);
		
		System.out.println("Install Categorie Article Par Defaut avec success");
		for(int i=2010;i<=2060;i++){
			years y=new years();
			String yearscompte="01-01-"+i;
			dt = new SimpleDateFormat("dd-mm-yyyy"); 
			Date dateyears = dt.parse(yearscompte); 
			y.setYears(dateyears);
			manageryears.insertObject(y);
		}
		System.out.println("Install Années Par Defaut avec success");
		
		managerlangue.insertObject(new Langue("Français"));
		managerlangue.insertObject(new Langue("Anglais"));
		managerlangue.insertObject(new Langue("Arabe"));
		
		System.out.println("Install les Langue Par Defaut avec success");
		
		mcategorieclient.insertObject(new CategorieIISociete("prive"));
		mcategorieclient.insertObject(new CategorieIISociete("public"));
		System.out.println("Install Categorie Client Par Defaut avec success");
		
		Secteur s=new Secteur("informatique");
		s.setCategorie(mcategorieclient.getObject(1));
		managersecteur.insertObject(s);
		
		s=new Secteur("informatique");
		s.setCategorie(mcategorieclient.getObject(2));
		managersecteur.insertObject(s);
		
		System.out.println("Install les Secteurs Par Defaut avec success");
		
		managerpays.insertObject(new Pays("maroc"));
		managerpays.insertObject(new Pays("france"));
		managerpays.insertObject(new Pays("USA"));
		
		System.out.println("Install les Pays Par Defaut avec success");
		
		Ville ville=new Ville();
		ville.setVille("Casablanca");
		ville.setPays(managerpays.getObject(1));
		managerville.insertObject(ville);
		
		ville.setVille("Rabat");
		ville.setPays(managerpays.getObject(1));
		managerville.insertObject(ville);
		
		ville.setVille("Agadir");
		ville.setPays(managerpays.getObject(1));
		managerville.insertObject(ville);
		
		ville.setVille("Al jadida");
		ville.setPays(managerpays.getObject(1));
		managerville.insertObject(ville);
		
		ville.setVille("Paris");
		ville.setPays(managerpays.getObject(2));
		managerville.insertObject(ville);
		
		ville.setVille("toulouse");
		ville.setPays(managerpays.getObject(2));
		managerville.insertObject(ville);
		
		ville.setVille("new york");
		ville.setPays(managerpays.getObject(3));
		managerville.insertObject(ville);
		
		System.out.println("Install les Ville Par Defaut avec success");
		TypeFacture facttype=new TypeFacture();
		facttype.setTypefacture("Simple");
		managerTypefacture.insertObject(facttype);
		
		facttype.setTypefacture("Contrat");
		managerTypefacture.insertObject(facttype);
		
		facttype.setTypefacture("Formation");
		managerTypefacture.insertObject(facttype);
		
		System.out.println("Install Type Facture Par Defaut avec success");
		
		CoordonneeBancaire banque=new CoordonneeBancaire();
		banque.setNumerobancaire("007 780 00 0398400000011668");
		banque.setBanque("AttijariWafa Bank");
		banque.setAgence("Casablanca");
		
		managercoord.insertObject(banque);
		
		System.out.println("Install Coordonnées Banquaire Par Defaut avec success");
		
		TypeOffre typeoffre=new TypeOffre();
		typeoffre.setType("Acquisition");
		typeoffre.setCotenutype("Faisant suite ..Acquisition");
		
		managertypeoffre.insertObject(typeoffre);
		
		typeoffre.setType("prestation");
		typeoffre.setCotenutype("Faisant suite ..prestation");
		
		managertypeoffre.insertObject(typeoffre);
		
		typeoffre.setType("Contrat");
		typeoffre.setCotenutype("Faisant suite ..Contrat");
		
		managertypeoffre.insertObject(typeoffre);
		
		System.out.println("Install les types des Offres Par Defaut avec success");
		
		
		Offre offredefaut=new Offre();
		offredefaut.setRef("sans Offre");
		offredefaut.setDateoffre(new Date());
		dt = new SimpleDateFormat("yyyy"); 
		offredefaut.setYears(managerjdbc.getYears(Integer.parseInt(dt.format(offredefaut.getDateoffre()))));
		
		manageroffre.insertObject(offredefaut);
		
		System.out.println("Install l'Offre Par Defaut avec Success");
		
		Facture fact=new Facture();
		BonCommande bc=new BonCommande();
		
		
        bc.setId(1);
        bc.setNumercommande("sans Boncommande");
		fact.setId(1);
		fact.setRef("sans Facture");
		Offre_Article offreart=new Offre_Article();
		offreart.setId(1);
		
		managerfacture.insertObject(fact);
		System.out.println("Install la Facture Par Defaut avec success");
		
		managerbc.insertObject(bc);
		System.out.println("Install la Boncommande Par Defaut avec success");
		
		TypePaiement devise=new TypePaiement();
		devise.setTypepaiement("MAD");
		managerdevise.insertObject(devise);
		
		devise.setTypepaiement("EURO");
		managerdevise.insertObject(devise);
		
		devise.setTypepaiement("DOLLAR");
		managerdevise.insertObject(devise);
		
		System.out.println("FIN INSTALL");
		
		} catch (Exception e) {
			System.out.println("ERROR DATA BASE, "+e.getMessage());
		}
		return "login.xhtml";
	}

	public ModelService<Utilisateur> getManageruser() {
		return manageruser;
	}

	public void setManageruser(ModelService<Utilisateur> manageruser) {
		this.manageruser = manageruser;
	}

	public ModelService<CategorieArticle> getMcategorieArticle() {
		return mcategorieArticle;
	}

	public void setMcategorieArticle(
			ModelService<CategorieArticle> mcategorieArticle) {
		this.mcategorieArticle = mcategorieArticle;
	}

	public ModelService<TypeArticle> getmTypeArticle() {
		return mTypeArticle;
	}

	public void setmTypeArticle(ModelService<TypeArticle> mTypeArticle) {
		this.mTypeArticle = mTypeArticle;
	}

	public ModelService<Langue> getManagerlangue() {
		return managerlangue;
	}

	public void setManagerlangue(ModelService<Langue> managerlangue) {
		this.managerlangue = managerlangue;
	}

	public ModelService<CategorieIISociete> getMcategorieclient() {
		return mcategorieclient;
	}

	public void setMcategorieclient(
			ModelService<CategorieIISociete> mcategorieclient) {
		this.mcategorieclient = mcategorieclient;
	}

	public ModelService<Secteur> getManagersecteur() {
		return managersecteur;
	}

	public void setManagersecteur(ModelService<Secteur> managersecteur) {
		this.managersecteur = managersecteur;
	}

	public ModelService<Pays> getManagerpays() {
		return managerpays;
	}

	public void setManagerpays(ModelService<Pays> managerpays) {
		this.managerpays = managerpays;
	}

	public ModelService<Ville> getManagerville() {
		return managerville;
	}

	public void setManagerville(ModelService<Ville> managerville) {
		this.managerville = managerville;
	}

	public ModelService<TypeFacture> getManagerTypefacture() {
		return managerTypefacture;
	}

	public void setManagerTypefacture(ModelService<TypeFacture> managerTypefacture) {
		this.managerTypefacture = managerTypefacture;
	}

	public ModelService<CoordonneeBancaire> getManagercoord() {
		return managercoord;
	}

	public void setManagercoord(ModelService<CoordonneeBancaire> managercoord) {
		this.managercoord = managercoord;
	}

	public ModelService<TypeOffre> getManagertypeoffre() {
		return managertypeoffre;
	}

	public void setManagertypeoffre(ModelService<TypeOffre> managertypeoffre) {
		this.managertypeoffre = managertypeoffre;
	}

	public ModelService<years> getManageryears() {
		return manageryears;
	}

	public void setManageryears(ModelService<years> manageryears) {
		this.manageryears = manageryears;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public ModelService<Facture> getManagerfacture() {
		return managerfacture;
	}

	public void setManagerfacture(ModelService<Facture> managerfacture) {
		this.managerfacture = managerfacture;
	}

	public ModelService<BonCommande> getManagerbc() {
		return managerbc;
	}

	public void setManagerbc(ModelService<BonCommande> managerbc) {
		this.managerbc = managerbc;
	}

	public ModelService<CategorieArticle> getManagerC() {
		return managerC;
	}

	public void setManagerC(ModelService<CategorieArticle> managerC) {
		this.managerC = managerC;
	}

	public ModelService<TypeArticle> getManagertype() {
		return managertype;
	}

	public void setManagertype(ModelService<TypeArticle> managertype) {
		this.managertype = managertype;
	}

	public ModelService<Produit> getManagerProduit() {
		return managerProduit;
	}

	public void setManagerProduit(ModelService<Produit> managerProduit) {
		this.managerProduit = managerProduit;
	}

	public ModelService<Article> getManagerarticle() {
		return managerarticle;
	}

	public void setManagerarticle(ModelService<Article> managerarticle) {
		this.managerarticle = managerarticle;
	}

	public ModelService<TypePaiement> getManagerdevise() {
		return managerdevise;
	}

	public void setManagerdevise(ModelService<TypePaiement> managerdevise) {
		this.managerdevise = managerdevise;
	}

	public ModelService<Offre> getManageroffre() {
		return manageroffre;
	}

	public void setManageroffre(ModelService<Offre> manageroffre) {
		this.manageroffre = manageroffre;
	}

	
}
