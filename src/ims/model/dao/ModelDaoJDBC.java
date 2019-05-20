package ims.model.dao;

import ims.model.entities.Article;
import ims.model.entities.AvoirClient;
import ims.model.entities.Avoir_Articles;
import ims.model.entities.BonCommande;
import ims.model.entities.BonLivraison;
import ims.model.entities.Boncommande_Article;
import ims.model.entities.Boncommande_Offre;
import ims.model.entities.Caisse;
import ims.model.entities.CategorieIISociete;
import ims.model.entities.Charge;
import ims.model.entities.Client;
import ims.model.entities.Cnss;
import ims.model.entities.Contact;
import ims.model.entities.CoordonneeBancaire;
import ims.model.entities.Departement;
import ims.model.entities.Employee;
import ims.model.entities.Facture;
import ims.model.entities.FactureFournisseur;
import ims.model.entities.Facture_Article;
import ims.model.entities.Facture_Avoir;
import ims.model.entities.Fournisseur;
import ims.model.entities.Langue;
import ims.model.entities.LicenceApplication;
import ims.model.entities.LigneCaisse;
import ims.model.entities.Offre_Article;
import ims.model.entities.OrdreVirement;
import ims.model.entities.Pays;
import ims.model.entities.Pointage;
import ims.model.entities.PrixArticleBC;
import ims.model.entities.PrixArticlesOffre;
import ims.model.entities.ListeBoncommandeFactureOffre;
import ims.model.entities.ObjetPrix;
import ims.model.entities.Offre;
import ims.model.entities.Produit;
import ims.model.entities.Projet;
import ims.model.entities.Rubrique;
import ims.model.entities.Salaires;
import ims.model.entities.Secteur;
import ims.model.entities.Stock;
import ims.model.entities.Stock_Livraison;
import ims.model.entities.Tache;
import ims.model.entities.Ticker;
import ims.model.entities.Timesheet;
import ims.model.entities.TypeArticle;
import ims.model.entities.TypeCharge;
import ims.model.entities.TypePaiement;
import ims.model.entities.TypePriorite;
import ims.model.entities.TypeTicker;
import ims.model.entities.Utilisateur;
import ims.model.entities.UtilisateurHelpDesk;
import ims.model.entities.Ville;
import ims.model.entities.years;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface ModelDaoJDBC {

	public int getTailleByDate(String table,String Attribute,String date);
	
	public int getexsiteRefArticle(String ref);
	public int getexsiteclient(String societe);
	public int getexsitefournisseur(String societe);
	public int getexsitefacture(String numero);
	public int getexsiteoffre(String numero);
	public int getExisteArticleContratOrServiceInOffre(int idOffre);
	
	public int getExsiteProduit(String produit);
	public int getExisteCategorieArticle(String cat);
	public int getExisteTypeArticle(String type);
	
	public int getcontratClient(int idclient);
	
	public int gestionstock(Stock_Livraison object);
	public int liraison_facture(int factureID,int livraison);
	public int updateOffre(Offre offre);
	public int updateBoncommande(int idcommande,String date,String numerocommande,int idfournisseur,String numoffrefounrnisseur,String datefournisseur,double fraitransport,String echance,Date dateechance,double tva,int idclient,int idtypepaiement);
	public int updatePrixBonCommande(double transport,double totalnet,double totalttc,double totalht,double tva,int idcommande);
	public int updateOffreforArticles(double totalht,double tva,double totalttc,int idoffre);
	
	public int insertOffre_facture(int idoffre,int idfacture);
	public int AddArticleToFacture(Offre_Article offrearticle,Facture fact,double coeff,double period);
	public int AddArticleToFacture(Facture_Article factarticle,Facture fact);
    public int updatePrixfacture(Facture fact);

	public int updatefacture(Facture facture);
	
	public int insertFactureLivraion(int idfacture,int idlivraison);
	public void closeconnexion();
	public int insertstocklivraison(Stock_Livraison stockliv);
	public int getMaxIdStockLivraison();

	public int updateStock(Stock stock);

	public int updateClient(Client client);

	public int updateFournisseur(Fournisseur fournisseur);

	public int deleteArticleToBonlivraion(int id);

	public int updateBonlivraison(int id, String ref, Date datebl,
			int idclient, String livrepar, String receptionpar);

	public void desactiverOffre(int id);

	public void AnnulerOffre(int id, int valeur);

	public int deleteOffre_Facture(int idOffre, int idfacture);

	public int updateArticle(int idArticle, String designation, String ref,
			int idproduit, int idtype);

	public boolean deleteArticleToBoncommande(int id);

	public void retourStock(int id, float qte);

	public int deleteFacture_Livraison(int id);

	public int deleteUser(int iduser);

	public int updateCategorieArticle(int idcategorie, String categorie);

	public int updateTypeArticle(int idtype, String type);

	public int updateUser(Utilisateur objetuser);

	public int update(Pays pays);

	public years getyears(int years);

	public int updateComptabiliteFacture(Facture facture);

	public int Annuler(BonCommande commande);

	public int Annuler(Facture facture);
	public List<ListeBoncommandeFactureOffre> getListeDetailFacture();
	
	public ObjetPrix gettotalPrix(Offre offre);
	public List<PrixArticlesOffre> getArticleOffre(Offre offre);
	public List<PrixArticleBC> getArticleBC(BonCommande bc);
	public ObjetPrix gettotalPrix(BonCommande bc);

	public int insertOffre_Article(Offre_Article offre_Article);
	public int insertBoncommandeArticle(Boncommande_Article objet);

	public int insertFactureArticle(Facture_Article obj);

	public boolean deletestock(Stock stock);

	public int AddOffreToSansfactBC(Offre calcule,String typeoffre);

	public int removeOffreTosansBoncomande(Offre offre);

	public int VerifyOffreTosansBoncommande(Offre objremove);

	public int removeOffreToSansFacture(Offre offre);

	public int VerifyOffreTosansFacture(Offre tempooffre);

	public int updateFacture_Offrecontrat(int idfacture, int idoffre);

	public int updateContact(Contact contact);

	public int UpdateCompatbiliteBC(BonCommande boncommande);

	public List<BonCommande> getNumbcForFacture(Facture facture);

	public List<Facture> getNumfactForBC(BonCommande bc);
	
	
	/*
	 * Caisse
	 */
	
	public int updatecaisse(Caisse caisse);
	public int updatePrixCaisse(Caisse caisse);
	public int updatelignecaisse(LigneCaisse ligne);
	//

	public boolean deleteArticleOffre(int id);

	public boolean deleteDepartement(int id);

	public boolean deleteEmployee(int id);

	public int updateprixOffre(int id,TypePaiement devise);

	public int updateEmployee(Employee employee);

	public int updateDepartement(Departement dept);

	public int updateOffre_Article(Offre_Article ligne);

	public int insertFactureAvoir(int idfacture, int avoirID);

	public int udpate(Cnss cnss);

	public int udpate(Salaires salaire);

	public int update(TypeCharge typecharge);

	public int update(Charge charge);

	public int delete(Cnss cnss);

	public int delete(Salaires salaire);

	public int delete(Charge charge);

	public int update(Avoir_Articles avoirarticle);

	public int update(AvoirClient avoirclient);

	public int delete(Avoir_Articles avoirarticle);

	public int delete(Facture_Avoir facture_avoir);

	public int confirmer(Facture facttempo);

	public int update(BonCommande commande);

	public int deletesalairesfromOrdre(OrdreVirement ordre);

	public int update(OrdreVirement ordre);

	public int update(Produit produit);

	public int update(FactureFournisseur factf);

	public int delete(OrdreVirement ordre);

	public int delete(Contact contacttempo);

	public int deleteArticlesToFactFournisseur(FactureFournisseur factf);

	public int update(Boncommande_Article ligne);

	public int update(CategorieIISociete categorieII);

	public int update(Secteur secteur);

	public int update(TypePaiement typepaiementtempo);

	public boolean delete(TypePaiement typepaiementsupp);

	public boolean delete(TypeArticle typearticle);

	public boolean delete(Article article);

	public boolean update(CoordonneeBancaire banquetempo);

	public boolean delete(Pays countrytempo);

	public boolean update(Ville ville);

	public boolean update(Langue vlangue);

	public boolean delete(Ville ville);

	public boolean delete(UtilisateurHelpDesk user);

	public boolean update(UtilisateurHelpDesk user);

	public boolean delete(LicenceApplication licenceappstempo);

	public boolean update(Ticker ticker);

	public boolean getUsercle(String cle);

    public  boolean FermerTicker(Ticker tickertempo);

	public boolean update(TypePriorite typeprioriteTempo);

	public boolean update(TypeTicker typetickertempo);

	public boolean delete(TypeTicker typetickertempo);

	public boolean delete(TypePriorite typeprioriteTempo);

	public boolean update(Tache tache);

	public boolean update(Projet projet);

	public boolean update(Rubrique rubriquetempo);

	public boolean update(Timesheet timesheet);

	public boolean update(Pointage pointage);

	public boolean concretiseroffre(Offre offre);

	public boolean delete(CategorieIISociete categorieII);

	public boolean delete(BonCommande commande);

	public boolean delete(BonLivraison livraison);

	public boolean delete(Facture facture);

	public boolean delete(FactureFournisseur factf);

	public boolean delete(AvoirClient avoirclient);

	public boolean updateContactFournisseur(Contact contacttempo);

	public boolean deleteFacture_Livraisonbyfacture(int id);

	public int getNombreFactureFounrisseurByFournisseur(Fournisseur fournisseur);

	public List<Article> getCountMaxAchat();

	public int getCountFactureConstater();

	public int getCountFactureNonconstater();

	public boolean delete(Produit produit);

	public boolean delete(Secteur secteurtempo);

	public boolean delete(Fournisseur fournisseur);

	public int getCaisseForMonth(String string);

	public boolean deleteArticleToFacture(Facture_Article facturearticle);

	public boolean insertFactureOffre(int factureID, int offreID);

	public boolean insertBCSansOffre(BonCommande commande);

	public int getIDLastCaisse();

	public List<LigneCaisse> getLigneCaisse(String choixmois, int year);

	public double getSommeCaisse(String typesomme);

	public Offre updateAllPrixMarge(double appmargetotal, Offre calcule);


	public boolean updateObjectif(int yearsID);

	public boolean updateEtatOffre(Offre calcule);

	public boolean factureeOffre(Offre offre);

	public boolean contratOffre(Offre offre);

	public int VerifyOffreTosansFactureforAdd(Offre tempooffre);

	public boolean factureeCommande(BonCommande commande);

	public boolean Encourscommande(BonCommande commandeModifier,
			FactureFournisseur factf);

	public boolean constaterBonCommande(BonCommande commandefournissuer);

	public boolean NonconstaterBonCommande(BonCommande commandefournissuer);

	public boolean updateclassementRubrique(int rubriqueID);

	public boolean insertBC_Offre(Boncommande_Offre bn);

	public boolean updateContactPrincipalForClient(Contact contacttempo);
}
