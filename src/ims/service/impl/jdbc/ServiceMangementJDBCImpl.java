package ims.service.impl.jdbc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.faces.bean.ManagedProperty;





import ims.model.dao.ModelDaoJDBC;
import ims.model.entities.Article;
import ims.model.entities.AvoirClient;
import ims.model.entities.Avoir_Articles;
import ims.model.entities.BonCommande;
import ims.model.entities.BonLivraison;
import ims.model.entities.Boncommande_Article;
import ims.model.entities.Boncommande_Offre;
import ims.model.entities.Caisse;
import ims.model.entities.CategorieArticle;
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
import ims.model.entities.OrdreVirement;
import ims.model.entities.Pointage;
import ims.model.entities.PrixArticleBC;
import ims.model.entities.PrixArticlesOffre;
import ims.model.entities.ListeBoncommandeFactureOffre;
import ims.model.entities.ObjetPrix;
import ims.model.entities.Offre;
import ims.model.entities.Offre_Article;
import ims.model.entities.Pays;
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
import ims.service.ModelServiceJDBC;

public class ServiceMangementJDBCImpl implements ModelServiceJDBC {

    @ManagedProperty(value="#{MangementTablesJDBC}")
    ModelDaoJDBC modeldao;
    
    
	@Override
	public int getLastNumbre(String table,String Attribute, String date) {
		
		return modeldao.getTailleByDate(table,Attribute, date);
	}


	public ModelDaoJDBC getModeldao() {
		return modeldao;
	}


	public void setModeldao(ModelDaoJDBC modeldao) {
		this.modeldao = modeldao;
	}


	@Override
	public int getExisteArticle(String ref) {
		return this.modeldao.getexsiteRefArticle(ref);
	}


	@Override
	public int getExicteClient(String societe) {
		return modeldao.getexsiteclient(societe);
	}


	@Override
	public int getExisteFournisseur(String societe) {
		return modeldao.getexsitefournisseur(societe);
	}


	@Override
	public int getExisteArticleContratOrServiceInOffre(int idOffre) {
		return modeldao.getExisteArticleContratOrServiceInOffre(idOffre);
	}


	@Override
	public int getexsiteoffre(String numero) {
		return modeldao.getexsiteoffre(numero);
	}


	@Override
	public int getexsitefacture(String numero) {
		return modeldao.getexsitefacture(numero);
	}


	@Override
	public int getOffreClient(int idclient) {
		return modeldao.getcontratClient(idclient);
	}


	@Override
	public int gestionstock(Stock_Livraison object) {
		return modeldao.gestionstock(object);
	}


	@Override
	public int livraison_facture(int factureID, int LivraisonID) {
		return modeldao.liraison_facture(factureID, LivraisonID);
	}


	@Override
	public int UpdateOffre(Offre offre) {
		return modeldao.updateOffre(offre);
	}


	@Override
	public int updateprixfacture(Facture facture) {
		
		return modeldao.updatePrixfacture(facture);
	}


	@Override
	public int updateBoncommande(BonCommande commande, int idfournisseur,int idclient) {
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		String datebc=null;
		String datef=null;
		
		if(commande.getDatecommande()!=null)
			datebc=df.format(commande.getDatecommande());
		
		if(commande.getDateoffre()!=null)
			datef=df.format(commande.getDateoffre());
		
		return modeldao.updateBoncommande(commande.getId(),datebc, commande.getNumercommande(), idfournisseur, commande.getNumeroOffreFournisseur(),datef,commande.getFraitransport(), commande.getEcheance(),commande.getDateechance(),commande.getTva(),idclient,commande.getTypepaiement().getId());
	}


	@Override
	public int updateOffreforAddArticle(Offre offre) {
		return modeldao.updateOffreforArticles(offre.getTotalHT(), offre.getTVA(), offre.getTotalTTC(), offre.getId());
	}


	@Override
	public int updatePrixBoncommande(BonCommande commande) {
		return modeldao.updatePrixBonCommande(commande.getFraitransport(),commande.getTotalnet(),commande.getTotalttc(), commande.getTotalht(), commande.getTva(), commande.getId());
	}


	@Override
	public int insertOffre_facture(Offre offre, Facture facture) {
		return modeldao.insertOffre_facture(offre.getId(), facture.getId());
	}


	@Override
	public int AddArticleToFacture(Facture fact,Offre_Article offrart, float coeff) {
		double period=offrart.getPt_calculer()*coeff;
		return modeldao.AddArticleToFacture(offrart,fact,coeff,period);
	}


	@Override
	public int insertFactureLivraison(int idfacture, int idlivraison) {
		return modeldao.insertFactureLivraion(idfacture, idlivraison);
	}


	@Override
	public void closeconnexion() {
		modeldao.closeconnexion();
		
	}


	@Override
	public int insertstocklivraison(Stock_Livraison stockliv) {
			return modeldao.insertstocklivraison(stockliv);
	}


	@Override
	public int getMaxIdSockLivraison() {
		return modeldao.getMaxIdStockLivraison();
	}


	@Override
	public int updateStock(Stock stock) {
		return modeldao.updateStock(stock);
		
	}


	@Override
	public int updateClient(Client client) {
		return modeldao.updateClient(client);
	}


	@Override
	public int updateFournisseur(Fournisseur fournisseur) {
		return modeldao.updateFournisseur(fournisseur);
	}


	@Override
	public int getExisteProduit(Produit produit) {
		return modeldao.getExsiteProduit(produit.getTypeproduit());
	}


	@Override
	public int getExisteTypeArticle(TypeArticle typearticle) {
		return modeldao.getExisteTypeArticle(typearticle.getType());
	}


	@Override
	public int getExisteCategorieArticle(CategorieArticle categorie) {
		return modeldao.getExisteCategorieArticle(categorie.getCategorie());
	}


	@Override
	public List<ListeBoncommandeFactureOffre> getListeDetailFacture() {
		return modeldao.getListeDetailFacture();
	}


	@Override
	public int deleteArticleToBonLivraison(Stock_Livraison stocklivraison) {
		return modeldao.deleteArticleToBonlivraion(stocklivraison.getId());
	}


	@Override
	public void updateBonLivraison(BonLivraison livraison) {
		modeldao.updateBonlivraison(livraison.getId(),livraison.getRef(),livraison.getDatebl(),livraison.getClient().getIdclient(),livraison.getLivrepar(),livraison.getReceptionpar());
		
	}


	@Override
	public void desactiverOffre(int id) {
		modeldao.desactiverOffre(id);
		
	}


	@Override
	public void AnnulerOffre(int id, int valeur) {
		modeldao.AnnulerOffre(id,valeur);
		
	}


	@Override
	public int updatefacture(Facture facture) {

		return modeldao.updatefacture(facture);
	}


	@Override
	public void updatePrixOffre(Offre calcule) {
		modeldao.updateprixOffre(calcule.getId(),calcule.getTypepaiement());
		
	}


	@Override
	public void deleteOffre_Facture(String idoffre, int idfacture) {
		 modeldao.deleteOffre_Facture(Integer.parseInt(idoffre), idfacture);
	}


	@Override
	public boolean deleteArtilceToBoncommande(Boncommande_Article ligne) {
		return modeldao.deleteArticleToBoncommande(ligne.getId());
	}


	@Override
	public int updateArticle(Article article) {
		return modeldao.updateArticle(article.getIdArticle(),article.getDesignation(),article.getRef(),article.getTypeproduit().getId(),article.getTypearticle().getIdtype());
	}


	@Override
	public void retourstock(int id, float qte) {
		modeldao.retourStock(id,qte);
		
	}


	@Override
	public int AddArticleToFacturecontrat(Facture facture,
			Facture_Article facturearticle) {
		return modeldao.AddArticleToFacture(facturearticle,facture);
	}


	@Override
	public int deleteFacture_Livraison(int id) {
		return modeldao.deleteFacture_Livraison(id);
	}


	@Override
	public int deleteUser(Utilisateur user) {
		return modeldao.deleteUser(user.getIduser());
	}


	@Override
	public int updateCategorieArticle(CategorieArticle categorieArticle) {
		return modeldao.updateCategorieArticle(categorieArticle.getIdcategorie(),categorieArticle.getCategorie());
	}


	@Override
	public int updateTypeArticle(TypeArticle typeArticle) {
		return modeldao.updateTypeArticle(typeArticle.getIdtype(),typeArticle.getType());
	}


	@Override
	public int updateUser(Utilisateur utilisateur) {
		return modeldao.updateUser(utilisateur);
	}


	@Override
	public int updatePays(Pays pays) {
		return modeldao.update(pays);
	}


	@Override
	public years getYears(int years) {
		return modeldao.getyears(years);
	}


	@Override
	public int UpdateCompatbiliteFacture(Facture facture) {
		return modeldao.updateComptabiliteFacture(facture);
	}


	@Override
	public int Annuler(Facture facture) {
		
		return modeldao.Annuler(facture);
	}


	@Override
	public int Annuler(BonCommande commande) {
		return modeldao.Annuler(commande);
	}


	@Override
	public ObjetPrix gettotalPrix(Offre offre) {
		return modeldao.gettotalPrix(offre);
	}


	@Override
	public List<PrixArticlesOffre> getArticleOffre(Offre offre) {
		return modeldao.getArticleOffre(offre);
	}


	@Override
	public List<PrixArticleBC> getArticleBC(BonCommande bc) {
		return modeldao.getArticleBC(bc);
	}


	@Override
	public ObjetPrix gettotalPrix(BonCommande bc) {
		return modeldao.gettotalPrix(bc);
	}


	@Override
	public int insertOffre_Article(Offre_Article objet) {
		return modeldao.insertOffre_Article(objet);
		
	}


	@Override
	public int insertBoncommandeArticle(Boncommande_Article objet) {
		return modeldao.insertBoncommandeArticle(objet);
	}


	@Override
	public int insertFactureArticle(Facture_Article obj) {
		return modeldao.insertFactureArticle(obj);
	}


	@Override
	public boolean deleteStock(Stock stock) {
		
		return modeldao.deletestock(stock);
	}


	@Override
	public int AddOffreToSansfactBC(Offre calcule,String typeoffre) {
		
		return modeldao.AddOffreToSansfactBC(calcule,typeoffre);
	}


	@Override
	public int removeOffreTosansBoncomande(Offre offre) {
		return modeldao.removeOffreTosansBoncomande(offre);
	}


	@Override
	public int VerifyOffreTosansBoncommande(Offre objremove) {
		return modeldao.VerifyOffreTosansBoncommande(objremove);
	}


	@Override
	public int removeOffreToSansFacture(Offre offre) {
		return modeldao.removeOffreToSansFacture(offre);
	}


	@Override
	public int VerifyOffreTosansFacture(Offre tempooffre) {
		return modeldao.VerifyOffreTosansFacture(tempooffre);
	}


	@Override
	public int updateFacture_Offrecontrat(int id, int idoffre) {
		
		return modeldao.updateFacture_Offrecontrat(id,idoffre);
	}


	@Override
	public int updateContact(Contact contact) {
		return modeldao.updateContact(contact);
	}


	@Override
	public int UpdateCompatbiliteBC(BonCommande boncommande) {

		return modeldao.UpdateCompatbiliteBC(boncommande);
	}


	@Override
	public List<BonCommande> getNumbcForFacture(Facture facture) {
		return modeldao.getNumbcForFacture(facture);
	}


	@Override
	public List<Facture> getNumfactForBC(BonCommande bc) {
		return modeldao.getNumfactForBC(bc);
	}


	@Override
	public int updateCaisse(Caisse caisse) {
		return modeldao.updatecaisse(caisse);
	}


	@Override
	public int updatePrixCaisse(Caisse caisse) {

		return modeldao.updatePrixCaisse(caisse);
	}


	@Override
	public int updateLigneCaisse(LigneCaisse ligne) {
		return modeldao.updatelignecaisse(ligne);
	}


	@Override
	public boolean deleteArticleOffre(int id) {
		
		return modeldao.deleteArticleOffre(id);
	}


	@Override
	public boolean deletDepartement(int id) {
		return modeldao.deleteDepartement(id);
	}


	@Override
	public boolean deleteEmployee(int id) {
		return modeldao.deleteEmployee(id);
	}


	@Override
	public int updateEmployee(Employee employee) {
		return modeldao.updateEmployee(employee);
	}


	@Override
	public int updateDepartement(Departement dept) {
		return modeldao.updateDepartement(dept);
	}


	@Override
	public int updateOffre_Article(Offre_Article ligne) {
		return modeldao.updateOffre_Article(ligne);
	}


	@Override
	public int insertFactureAvoir(int idfacture, int avoirID) {
		return modeldao.insertFactureAvoir(idfacture,avoirID);
	}


	@Override
	public int updatecnss(Cnss cnss) {
		return modeldao.udpate(cnss);
	}


	@Override
	public int updatesalaires(Salaires salaire) {
		return modeldao.udpate(salaire);
	}


	@Override
	public int updateTypeCharge(TypeCharge typecharge) {
	
		return modeldao.update(typecharge);
	}


	@Override
	public int updateCharge(Charge charge) {
		return modeldao.update(charge);
	}


	@Override
	public int deleteCNSS(Cnss cnss) {
		return modeldao.delete(cnss);
	}


	@Override
	public int deleteSalaire(Salaires salaire) {
		return modeldao.delete(salaire);
	}


	@Override
	public int deleteCharge(Charge charge) {
		return modeldao.delete(charge);
	}


	@Override
	public int updateAvoir_Article(Avoir_Articles avoirarticle) {
		return modeldao.update(avoirarticle);
	}


	@Override
	public int updatePrixAvoirClient(AvoirClient avoirclient) {
		return modeldao.update(avoirclient);
	}


	@Override
	public int deleteAvoirArticle(Avoir_Articles avoirarticle) {
		return modeldao.delete(avoirarticle);
	}


	@Override
	public int deleteFactureAvoir(Facture_Avoir facture_avoir) {
		return modeldao.delete(facture_avoir);
	}


	@Override
	public int updateAvoirClient(AvoirClient avoirclient) {
		return modeldao.update(avoirclient);
	}


	@Override
	public int confirmer(Facture facttempo) {
		return modeldao.confirmer(facttempo);
	}


	@Override
	public int update(BonCommande commande) {
		return modeldao.update(commande);
	}


	@Override
	public int deleteAllSalaire(OrdreVirement ordre) {
		return modeldao.deletesalairesfromOrdre(ordre);
	}


	@Override
	public int updateOrdre(OrdreVirement ordre) {
		return modeldao.update(ordre);
	}


	@Override
	public int updateProduit(Produit produit) {
		// TODO Auto-generated method stub
		return modeldao.update(produit);
	}


	@Override
	public int update(FactureFournisseur factf) {
		
		return modeldao.update(factf);
	}


	@Override
	public int deleteOrdre(OrdreVirement ordre) {
		return modeldao.delete(ordre);
	}


	@Override
	public int deleteContact(Contact contacttempo) {
		return modeldao.delete(contacttempo);
	}


	@Override
	public int deleteArticlesToFactFournisseur(FactureFournisseur factf) {
		return modeldao.deleteArticlesToFactFournisseur(factf);
	}


	@Override
	public int updateBoncommandeArticle(Boncommande_Article ligne) {
		return modeldao.update(ligne);
	}


	@Override
	public int updateCategorieClient(CategorieIISociete categorieII) {
		return modeldao.update(categorieII);
	}


	@Override
	public int updateSecteur(Secteur secteur) {
		return modeldao.update(secteur);
	}


	@Override
	public int updateTypePaiement(TypePaiement typepaiementtempo) {
		return modeldao.update(typepaiementtempo);
	}


	@Override
	public boolean deleteTypePaiement(TypePaiement typepaiementsupp) {
		return modeldao.delete(typepaiementsupp);
	}


	@Override
	public boolean deleteTypeArticle(TypeArticle typearticle) {
		return modeldao.delete(typearticle);
	}


	@Override
	public boolean deleteArticle(Article article) {
		return modeldao.delete(article);
	}


	@Override
	public boolean updateCoordonneeBanquaire(CoordonneeBancaire banquetempo) {
		return modeldao.update(banquetempo);
	}


	@Override
	public boolean deletePays(Pays countrytempo) {
		return modeldao.delete(countrytempo);
	}


	@Override
	public boolean updateVille(Ville ville) {
		return modeldao.update(ville);
	}


	@Override
	public boolean updateLangue(Langue vlangue) {
		return modeldao.update(vlangue);
	}


	@Override
	public boolean deleteVille(Ville ville) {
		return modeldao.delete(ville);
	}


	@Override
	public boolean updateUserHelpDesk(UtilisateurHelpDesk user) {
		return modeldao.update(user);
	}


	@Override
	public boolean deleteUserHelpdesk(UtilisateurHelpDesk userhelpdesktempo) {
		return modeldao.delete(userhelpdesktempo);
	}


	@Override
	public boolean deleteLiceneApps(LicenceApplication licenceappstempo) {
		return modeldao.delete(licenceappstempo);
	}


	@Override
	public boolean updateTicker(Ticker ticker) {
		return modeldao.update(ticker);
	}


	@Override
	public boolean getUsercle(String cle) {
		return modeldao.getUsercle(cle);
	}


	@Override
	public boolean FermerTicker(Ticker tickertempo) {
		return modeldao.FermerTicker(tickertempo);
	}

	@Override
	public boolean update(TypePriorite typeprioriteTempo) {
		return modeldao.update(typeprioriteTempo);
	}


	@Override
	public boolean update(TypeTicker typetickertempo) {
		return modeldao.update(typetickertempo);
	}


	@Override
	public boolean deleteTypeTicker(TypeTicker typetickertempo) {
		return modeldao.delete(typetickertempo);
	}


	@Override
	public boolean deleteTypeProriter(TypePriorite typeprioriteTempo) {
		return modeldao.delete(typeprioriteTempo);
	}


	@Override
	public boolean updateTache(Tache tache) {
		return modeldao.update(tache);
	}


	@Override
	public boolean updateProjet(Projet projet) {
		return modeldao.update(projet);
	}


	@Override
	public boolean updateRubrique(Rubrique rubriquetempo) {
		return modeldao.update(rubriquetempo);
	}


	@Override
	public boolean updateTimesheet(Timesheet timesheet) {
		return modeldao.update(timesheet);
	}


	@Override
	public boolean updatePointage(Pointage pointage) {
		return modeldao.update(pointage);
	}


	@Override
	public boolean concretiserOffre(Offre offre) {
		return modeldao.concretiseroffre(offre);
	}


	@Override
	public boolean deleteCategorieclient(CategorieIISociete categorieII) {
		return modeldao.delete(categorieII);
	}


	@Override
	public boolean deleteBncommande(BonCommande commande) {
		return modeldao.delete(commande);
	}


	@Override
	public boolean deleteBnlivraison(BonLivraison livraison) {
		return modeldao.delete(livraison);
	}


	@Override
	public boolean deleteFacture(Facture facture) {
		return modeldao.delete(facture);
	}


	@Override
	public boolean deleteFactureFourniseur(FactureFournisseur factf) {
		return modeldao.delete(factf);
	}


	@Override
	public boolean deleteAvoir(AvoirClient avoirclient) {
		return modeldao.delete(avoirclient);
	}


	@Override
	public boolean updateContactFournisseur(Contact contacttempo) {
		return modeldao.updateContactFournisseur(contacttempo);
	}


	@Override
	public boolean deleteFacture_Livraisonbyfacture(int id) {
		return modeldao.deleteFacture_Livraisonbyfacture(id);
	}


	@Override
	public int getNombreFactFournisseurByFournisseur(Fournisseur fournisseur) {
		return modeldao.getNombreFactureFounrisseurByFournisseur(fournisseur);
	}


	@Override
	public List<Article> getCountMaxAchat() {
		return modeldao.getCountMaxAchat();
	}


	@Override
	public int getCountFactureConstater() {
		return modeldao.getCountFactureConstater();
	}


	@Override
	public int getCountFactureNonconstater() {
		return modeldao.getCountFactureNonconstater();
	}


	@Override
	public boolean deleteProduit(Produit produit) {
		return modeldao.delete(produit);
	}


	@Override
	public boolean delete(Secteur secteurtempo) {
		return modeldao.delete(secteurtempo);
	}


	@Override
	public boolean delete(Fournisseur fournisseur) {
		return modeldao.delete(fournisseur);
	}


	@Override
	public int getCaisseForMonth(String string) {
		return modeldao.getCaisseForMonth(string);
	}


	@Override
	public boolean deleteArticleToFacture(Facture_Article facturearticle) {
		return modeldao.deleteArticleToFacture(facturearticle);
	}


	@Override
	public boolean insertFactureOffre(int FactureID, int OffreID) {
		return modeldao.insertFactureOffre(FactureID,OffreID);
	}


	@Override
	public boolean insertBCSansOffre(BonCommande commande) {
		return modeldao.insertBCSansOffre(commande);
	}


	@Override
	public int getIDLastCaisse() {
		return modeldao.getIDLastCaisse();
	}


	@Override
	public List<LigneCaisse> getLigneCaisse(String choixmois, int year) {
		return modeldao.getLigneCaisse(choixmois,year);
	}


	@Override
	public double getSommeCaisseEntrer() {
		return modeldao.getSommeCaisse("e");
	}


	@Override
	public double getSommeCaisseSortie() {
		return modeldao.getSommeCaisse("s");
	}


	@Override
	public Offre updateAllPrixMarge(double appmargetotal, Offre calcule) {

		return modeldao.updateAllPrixMarge(appmargetotal,calcule);
	}


	@Override
	public boolean updateObjectif(int yearsID) {
		return modeldao.updateObjectif(yearsID);
		
	}


	@Override
	public boolean updateEtatOffre(Offre calcule) {
		
		return modeldao.updateEtatOffre(calcule);
	}


	@Override
	public boolean factureeOffre(Offre offre) {
		return modeldao.factureeOffre(offre);
	}


	@Override
	public boolean contratOffre(Offre offre) {
		return modeldao.contratOffre(offre);
	}


	@Override
	public int VerifyOffreTosansFactureforAdd(Offre tempooffre) {
		return modeldao.VerifyOffreTosansFactureforAdd(tempooffre);
	}


	@Override
	public boolean factureeCommande(BonCommande commande) {
		return modeldao.factureeCommande(commande);
	}


	@Override
	public boolean Encourscommande(BonCommande commandeModifier,
			FactureFournisseur factf) {

		return modeldao.Encourscommande(commandeModifier,factf);
	}


	@Override
	public boolean constaterBonCommande(BonCommande commandefournissuer) {
		return modeldao.constaterBonCommande(commandefournissuer);
	}


	@Override
	public boolean NonconstaterBonCommande(BonCommande commandefournissuer) {
		return modeldao.NonconstaterBonCommande(commandefournissuer);
	}


	@Override
	public boolean updateclassementRubrique(int rubriqueID) {
		return modeldao.updateclassementRubrique(rubriqueID);
	}


	@Override
	public boolean insertBC_Offre(Boncommande_Offre bn) {
		return modeldao.insertBC_Offre(bn);
	}


	@Override
	public boolean updateContactPrincipalForClient(Contact contacttempo) {
	
		return modeldao.updateContactPrincipalForClient(contacttempo);
	}
	

}
