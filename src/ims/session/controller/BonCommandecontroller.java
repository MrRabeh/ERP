package ims.session.controller;

import ims.model.entities.Article;
import ims.model.entities.BonCommande;
import ims.model.entities.Boncommande_Article;
import ims.model.entities.Boncommande_Offre;
import ims.model.entities.Client;
import ims.model.entities.Fournisseur;
import ims.model.entities.ListeBoncommandeFactureOffre;
import ims.model.entities.ObjetPrix;
import ims.model.entities.Offre;
import ims.model.entities.Offre_Article;
import ims.model.entities.TypePaiement;
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



@ManagedBean
@SessionScoped
public class BonCommandecontroller implements Serializable{

	/**
	 * creator RABEH TARIK
	 */
	private static final long serialVersionUID = 1L;
	
	 @ManagedProperty(value = "#{managerDataBase}")
	 private ManagerDataBase managerApplication;
	 
	 
	public ManagerDataBase getManagerApplication() {
		return managerApplication;
	}

	public void setManagerApplication(ManagerDataBase managerApplication) {
		this.managerApplication = managerApplication;
	}
	
	@ManagedProperty(value="#{boncommandeManager}")
	private ModelService<BonCommande> manager;
	
    @ManagedProperty(value="#{BoncommandeArticleManager}")
    private ModelService<Boncommande_Article> managerarticle;
    
    @ManagedProperty(value="#{ArticleManager}")
    private ModelService<Article> managerObjetarticle;
    
    @ManagedProperty(value="#{FournisseurManager}")
    private ModelService<Fournisseur> managerF;
    
    @ManagedProperty(value="#{ClientManager}")
    private ModelService<Client> managerC;
    
    @ManagedProperty(value="#{OffreManager}")
    private ModelService<Offre> managerOffre;
    
    @ManagedProperty(value="#{BonCommandeOffreManage}")
    private ModelService<Boncommande_Offre> managerBoncommandeOffre;
    
    @ManagedProperty(value="#{OffreArticleManager}")
    private ModelService<Offre_Article> managerOffreArticle;
    
    @ManagedProperty(value="#{TypepaiementManager}")
    private ModelService<TypePaiement> managerTypePaiement;
    
    @ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
    
	private BonCommande commande=new BonCommande();
	private List<BonCommande> commandes;
	private List<BonCommande> filteredcommandes=new ArrayList<BonCommande>();
	
	private List<ListeBoncommandeFactureOffre> listes=new ArrayList<ListeBoncommandeFactureOffre>();
	private ListeBoncommandeFactureOffre liste=new ListeBoncommandeFactureOffre();
	
	private int idf=1;
	private int idc=1;
	private int idoffre=1;
	private int idtypepaiement;
	private List<String> idoffres=new ArrayList<String>() ;
	private List<String> idoffresadd=new ArrayList<String>() ;
	private List<Offre> offres=null;
	private List<Offre> tempos=new ArrayList<Offre>();//for delete Offre
	private List<Offre> offresadd=null;
	
	private List<Offre> offresmodif=new ArrayList<Offre>();
	
	private String numerocommande="";
	private String numerooffrefournisseur="";
	private String echeance="";
	private String designation;
	private Date datecommande;
	private Date dateechance;
	private boolean checkaffect=false;
	
	
	private Boncommande_Article ligne;
	private List<Boncommande_Article> lignetempo=new ArrayList<Boncommande_Article>();
	
    private List<Boncommande_Article> lignes=new ArrayList<Boncommande_Article>();
    private List<Boncommande_Article> ligneaddarticles=new ArrayList<Boncommande_Article>();
    
    private List<Boncommande_Article> lignesView;
	
    private String str_commande;
    
    private int idarticle=1;
    ///////////////////////////////////////////
    private double marget=0;
    private Article article=new Article();
    private double qte=1;
    private double pu=0;
    private double TVA=20;
    private double totalHT=0;
    private double totalTTC=0;
    private double totalNET=0;
    private String ref="";
    private double montantTVA=0;
    private Fournisseur fournisseur=new Fournisseur();
    
    private int generateurID;
    /////////////////////////////////////////////////
    
    //////////////////////////////////////////////////////
	private final List<String> etatannuler=new ArrayList<String>() {
		private static final long serialVersionUID = 1L;

	{ add("");add("Non");add("Oui");}
	};
    //////////////////////////////////////////////////////
	
    //////////////////////////////////////////////////////
	private final List<String> etats=new ArrayList<String>() {
		private static final long serialVersionUID = 1L;

	   {add("");add("Annulée");add("En cours");add("livré");add("Facturée");}
   };
    //////////////////////////////////////////////////////
	
	public void editcellcommande(){
		FacesMessage msg;
		managerApplication.setCommandes(null);
		try {
			manager.updateObject(commande);
		    msg = new FacesMessage("la commande est "+commande.getEtat());
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
	  		msg = new FacesMessage("Exception : ",e.getMessage());
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}
	public BonCommandecontroller(){
	}

	@PostConstruct
	public void init(){
		System.out.println("-----INIT COMMANDE FOURNISSEUR----");
		checkaffect=false;
		//offres=null;
		//offresadd=null;
		offres=managerOffre.getObject();
		tempos=managerOffre.getObject();
		offresadd=new ArrayList<Offre>();
		commandes=managerApplication.getCommandes();
		System.out.println("-----FIN INIT COMMANDE FOURNISSEUR----");
	}
	
    public void actualiser(){
    	managerApplication.setCommandes(null);
    }
	
	public void onCellarticleModif(){
		FacesMessage msg;
		managerApplication.setCommandes(null);
		try {
			for(int i=0;i<lignes.size();i++){
				lignes.get(i).setPt(lignes.get(i).getQte()*lignes.get(i).getPu());
				managerarticle.updateObject(lignes.get(i));
			}

			managerjdbc.update(commande);
			commande=manager.getObject(commande.getId());
	  		msg = new FacesMessage("Ligne est Modifier avec success");
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
	  		msg = new FacesMessage("Exception:",e.getMessage());
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}
	
	public void onCellarticle(){
		FacesMessage msg;
		try {
	  		msg = new FacesMessage("Ligne est Modifier avec success");
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
	  		msg = new FacesMessage("Exception:",e.getMessage());
    		FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
  	public String prepareCreate(){
  		idarticle=0;
  		idtypepaiement=0;
  		idc=0;
  		idf=0;
   		ligneaddarticles=new ArrayList<Boncommande_Article>();
   		lignes=new ArrayList<Boncommande_Article>();
   		ligne=new Boncommande_Article();
   		commande=new BonCommande();
   		checkaffect=true;
   		generateurID=0;
   		vider();
    	return "Boncommande?faces-redirect=true";
    } 
  	
	public String prepareView(){
		FacesMessage msg;
		try {
			idarticle=0;
			checkaffect=true;
			idtypepaiement=commande.getTypepaiement().getId();
			idf=commande.getFournisseur().getIdfournisseur();
			idc=commande.getClient().getIdclient();
			lignes=managerarticle.getObjects(commande.getId());
	    	return "ViewBoncommande?faces-redirect=true";
		} catch (Exception e) {
			System.out.println("Exception.."+e.getLocalizedMessage());
	  		msg = new FacesMessage("Exception:",e.getMessage());
    		FacesContext.getCurrentInstance().addMessage(null, msg);
    		return "listBoncommande?faces-redirect=true";
		}
    } 
	
    public String preparecreer(){
    	lignes=new ArrayList<Boncommande_Article>();
    	generateurID=1;
    		vider();
    	return "Boncommande?faces-redirect=true";
    }
    
	public String annuler(){
		try {
			managerjdbc.Annuler(commande);
			FacesMessage msg = new FacesMessage("Bon commande est Annuler");
   		 	FacesContext.getCurrentInstance().addMessage(null, msg);
   		 return "listBoncommande?faces-redirect=false";
		} catch (Exception e) {
			return "listBoncommande?faces-redirect=false";
		}

	}
	
    public String annulerBoncommande(){
        
    	try {
    		return "listBoncommande?faces-redirect=true";
		} catch (Exception e) {
            return "listBoncommande?faces-redirect=true";
		}
}
    
    public void creecommande(){
		DecimalFormat df = new DecimalFormat("0.##");
        df.setMinimumFractionDigits(2);
		FacesMessage msg;
		managerApplication.setCommandes(null);
		try {
			if(idtypepaiement==0 || idc==0 || idf==0){
				 msg = new FacesMessage("selectionner le Vide");
		            FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
					commande.setTypepaiement(managerTypePaiement.getObject(idtypepaiement));
					commande.setDatecommande(datecommande);
				 	commande.setNumercommande(numerocommande);
				 	commande.setConstater("Non");
				 	commande.setNumeroOffreFournisseur(numerooffrefournisseur);
				 	commande.setEcheance(echeance);
				 	commande.setDateechance(dateechance);
				 	commande.setFournisseur(managerF.getObject(idf));
				 	commande.setClient(managerC.getObject(idc));
				 	SimpleDateFormat dt = new SimpleDateFormat("yyyy"); 
				 	commande.setYears(managerjdbc.getYears(Integer.parseInt(dt.format(datecommande))));
				 	commande.setActiver(true);
				 	
				 	if(commande.getTypepaiement().getTypepaiement().equals("MAD")){
					 	commande.setTotalht(totalHT);
					 	commande.setTotalttc(totalTTC);
					 	commande.setTotalnet(totalTTC+commande.getFraitransport());
					 	commande.setTva(montantTVA);
				 	}else{
					 	commande.setTotalht(totalHT);
					 	commande.setTotalttc(totalHT);
					 	commande.setTotalnet(totalHT+commande.getFraitransport());
					 	commande.setTva(0);
				 	}
					 	commande.setEtat("En cours");
					 	
					boolean op=manager.insertObject(commande);
					if(op==true){
						if(offresadd.size()>0){
						 	for(int i=0;i<offresadd.size();i++){
					 			Boncommande_Offre bnoff=new Boncommande_Offre();
					 			bnoff.setCommande(commande);
					 			bnoff.setOffre(offresadd.get(i));
					 			managerBoncommandeOffre.insertObject(bnoff);
					 			managerjdbc.removeOffreTosansBoncomande(offresadd.get(i));
					 			managerjdbc.concretiserOffre(offresadd.get(i));
						 	}
						}else{
							managerjdbc.insertBCSansOffre(commande);
						}
	
						for(int i=0;i<lignes.size();i++)
						{
							lignes.get(i).setCommande(commande);
							//managerarticle.insertObject(lignes.get(i));
							managerjdbc.insertBoncommandeArticle(lignes.get(i));
						}
						managerjdbc.updatePrixBoncommande(commande);
						managerjdbc.update(commande);
		            msg = new FacesMessage("la Commande est bien créé");
					}
					else
						 msg = new FacesMessage("numero bon commande existe deja");
		            FacesContext.getCurrentInstance().addMessage(null, msg);
		            	vider();
			}
		} catch (Exception e) {
			 msg = new FacesMessage("Exception BC");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
    
	public void update() {
		DecimalFormat df = new DecimalFormat("0.##");
        df.setMinimumFractionDigits(2);
        FacesMessage msg;
        managerApplication.setCommandes(null);
        try {
        	commande.setTypepaiement(managerTypePaiement.getObject(idtypepaiement));
        	managerjdbc.updateBoncommande(commande, idf,idc);
        	managerjdbc.update(commande);
        	commande=manager.getObject(commande.getId());
            msg = new FacesMessage("Bon Commande Fournisseur est bien Modifier","");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
           msg = new FacesMessage("Probleme de Base de données","Contacter l'administration");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
   }
	
    public void Delete(){
        FacesMessage msg;
        commandes=null;
        System.out.println("Boncommande");
        try {
        	boolean op=managerjdbc.deleteBncommande(commande);
        	if(op==true)
        		msg = new FacesMessage("Bon Commande Supprimer avec success");
        	else
        		msg = new FacesMessage("Probleme de Suppression");
        	
           FacesContext.getCurrentInstance().addMessage(null, msg);

            } catch (Exception e) {
                msg = new FacesMessage("ERORR SUPPRESSION");
           FacesContext.getCurrentInstance().addMessage(null, msg);
            }   
    }
    
	public void addOffre(){
		System.out.println("function addOffre");
		DecimalFormat df = new DecimalFormat("0.##");
        df.setMinimumFractionDigits(2);
		boolean trouve=false;
		
		for(int i=0;i<idoffres.size();i++)
		{	
			Offre of=managerOffre.getObject(Integer.parseInt(idoffres.get(i)));
			for(int j=0;j<offresadd.size();j++)
			{
				if(of.getRef().equals(offresadd.get(j).getRef()))
					trouve=true;
			}
			if(trouve==false)
				{
					offres=null;
					offres=new ArrayList<Offre>();
					offresadd.add(of);
					for(int k=0;k<tempos.size();k++)
					{	
						boolean ajout=true;
						for(int kadd=0;kadd<offresadd.size();kadd++){
							if(tempos.get(k).getId()==offresadd.get(kadd).getId())
								ajout=false;
						}
						if(ajout==true)
							offres.add(tempos.get(k));
					}
					if(checkaffect==true){
							List<Offre_Article> liste=new ArrayList<Offre_Article>();
							lignes=null;
							lignes=new ArrayList<Boncommande_Article>();
							for(int iligne=0;iligne<offresadd.size();iligne++)
								{
								liste.addAll(managerOffreArticle.getObjects(offresadd.get(iligne).getId()));
								}
							for(int k=0;k<liste.size();k++)
							{
								Boncommande_Article bncommandearticle=new Boncommande_Article();
								bncommandearticle.setId(generateurID);
								generateurID++;
								bncommandearticle.setPu(liste.get(k).getPu());
								bncommandearticle.setPt(liste.get(k).getPt());
								bncommandearticle.setDesignation(liste.get(k).getDesignation());
								bncommandearticle.setQte(liste.get(k).getQtef());
								bncommandearticle.setArticle(liste.get(k).getArticle());
								bncommandearticle.setCommande(commande);
								lignes.add(bncommandearticle);
							}
							for(int k=0;k<lignes.size();k++)
								totalHT+=lignes.get(k).getPt();
							
							montantTVA=((totalHT*TVA)/100);
							totalTTC=montantTVA+totalHT;
							totalNET=totalTTC+commande.getFraitransport();
							verifier(montantTVA, totalTTC, totalHT,0);
					}
				}
		}
		idoffres=null;
		idoffres=new ArrayList<String>();
	}

	public void removeOffre(){
		DecimalFormat df = new DecimalFormat("0.##");
        df.setMinimumFractionDigits(2);
			boolean trouve=false;
			List<Offre> tempoadd=new ArrayList<Offre>();
			tempoadd.addAll(offresadd);
			
		for(int i=0;i<idoffresadd.size();i++){
			Offre of=managerOffre.getObject(Integer.parseInt(idoffresadd.get(i)));
			for(int j=0;j<offres.size();j++){
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
		if(checkaffect==true){
			lignes=new ArrayList<Boncommande_Article>();
			List<Offre_Article> liste=new ArrayList<Offre_Article>();
			for(int iligne=0;iligne<offresadd.size();iligne++)
				liste.addAll(offresadd.get(iligne).getLigneArticleoffre());
			totalHT=0;
			totalTTC=0;
			montantTVA=0;
			for(int k=0;k<liste.size();k++)
			{
				Boncommande_Article bncommandearticle=new Boncommande_Article();
				bncommandearticle.setPu(liste.get(k).getPu_calculer());
				bncommandearticle.setPt(liste.get(k).getPt_calculer());
				bncommandearticle.setQte(liste.get(k).getQtef());
				bncommandearticle.setArticle(liste.get(k).getArticle());
				bncommandearticle.setCommande(commande);
				lignes.add(bncommandearticle);
			}
			lignes.addAll(ligneaddarticles);
			for(int k=0;k<lignes.size();k++)
				totalHT=+lignes.get(k).getPt();
			
			montantTVA=((totalHT*TVA)/100);
			totalTTC=montantTVA+totalHT;
			verifier(montantTVA, totalTTC, totalHT,0);
			idoffresadd=null;
			idoffresadd=new ArrayList<String>();
		}
	}
	
	public void add(){
		
		System.out.println("function ADD");
		DecimalFormat df = new DecimalFormat("0.##");
        df.setMinimumFractionDigits(2);
		FacesMessage msg;
		managerApplication.setCommandes(null);
		boolean trouve=false;
		
		
		for(int i=0;i<idoffres.size();i++)
		{
			System.out.println(idoffres.get(i));
			Offre tmpoffre=managerOffre.getObject(Integer.parseInt(idoffres.get(i)));
			for(int j=0;j<commande.getListeoffres().size();j++)
			{
				
				System.out.println("Offre ="+tmpoffre.getRef());
				if(tmpoffre.getRef().equals(commande.getListeoffres().get(j).getRef()))
					{
						trouve=true;
					}
				System.out.println(" SUCCESS"+commande.getListeoffres().get(j).getRef());
			}
			if(trouve==false)
			{

				Boncommande_Offre bn=new Boncommande_Offre();
				bn.setCommande(commande);
				//Offre tempoOffre=managerOffre.getObject(Integer.parseInt(idoffres.get(i)));
				bn.setOffre(tmpoffre);
				
				//offre to bncommance
				managerjdbc.insertBC_Offre(bn);
				//managerBoncommandeOffre.insertObject(bn);

				
				//verifier si l'offre est deja affecter a sans bon commande
				managerjdbc.removeOffreTosansBoncomande(tmpoffre);					
				managerjdbc.concretiserOffre(tmpoffre);
				if(checkaffect==true){
					
					Boncommande_Article tempoligne=new Boncommande_Article();
					tempoligne.setCommande(commande);
					List<Offre_Article> tempoOffrearticle=managerOffreArticle.getObjects(tmpoffre.getId());
					for(int k=0;k<tempoOffrearticle.size();k++){
						
						tempoligne.setArticle(tempoOffrearticle.get(k).getArticle());
						tempoligne.setPu(tempoOffrearticle.get(k).getPu());
						tempoligne.setDesignation(tempoOffrearticle.get(k).getDesignation());
						tempoligne.setQte(tempoOffrearticle.get(k).getQtef());
						tempoligne.setPt(tempoOffrearticle.get(k).getPt());
						//managerarticle.insertObject(tempoligne);
						managerjdbc.insertBoncommandeArticle(tempoligne);
						commande.setTotalht(commande.getTotalht()+tempoligne.getPt());
						commande.setTva(commande.getTotalht()*(TVA/100));
						commande.setTotalttc(commande.getTotalht()+commande.getTva());
						verifier(commande.getTva(), commande.getTotalttc(), commande.getTotalht(),commande.getTotalnet());
						}
					
				}
			}
			for(int k=0;k<offresmodif.size();k++){
				if(tmpoffre.getRef().equals(offresmodif.get(k).getRef()))
					offresmodif.remove(offres.get(k));
			}
			
			verifier(commande.getTva(), commande.getTotalttc(), commande.getTotalht(),commande.getTotalnet());
			
		}
		
		managerjdbc.update(commande);
		lignes=managerarticle.getObjects(commande.getId());
		
		msg = new FacesMessage("Offre ete ajouter a la bon commande");
        FacesContext.getCurrentInstance().addMessage(null, msg);

		idoffres=null;
		idoffres=new ArrayList<String>();
		
		
	}
	
	public void remove(){
		FacesMessage msg;
		managerApplication.setCommandes(null);
			for(int i=0;i<idoffresadd.size();i++)
			{
				Offre objremove=managerOffre.getObject(Integer.parseInt(idoffresadd.get(i)));
				Boncommande_Offre bo=managerBoncommandeOffre.getByIids(commande.getId(),objremove.getId());
				managerBoncommandeOffre.deleteObject(bo);
				offresmodif.remove(objremove);
				offresadd.add(objremove);
				managerjdbc.VerifyOffreTosansBoncommande(objremove);
				
			}

		
		idoffresadd=null;
		idoffresadd=new ArrayList<String>();
		verifier(commande.getTva(), commande.getTotalttc(), commande.getTotalht(),commande.getTotalnet());
		msg = new FacesMessage("Offre Supprimer de Bon Commande");
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void ajouterligne(){
		DecimalFormat df = new DecimalFormat("0.##");
        df.setMinimumFractionDigits(2);
	    	try {
	    		FacesMessage msg;

	        	ligne=new Boncommande_Article();
	        	article=managerObjetarticle.getObject(idarticle);
	        	ligne.setPu(pu);
	        	ligne.setQte(qte);
	        	ligne.setArticle(article);
	        	
	        	if(designation.equals(""))
	        		ligne.setDesignation(article.getDesignation());
	        	else
	        		ligne.setDesignation(designation);
	        	
	        	double res=(ligne.getPu()*ligne.getQte());
	        	
	        	ligne.setPt(Double.parseDouble(df.format(res).replace(',', '.')));
	        	
	        	ligneaddarticles.add(ligne);
	            lignes.add(ligne);

	            
	            totalHT=totalHT+ligne.getPt();
	            
	            totalHT=Double.parseDouble(df.format(totalHT).replace(',', '.'));
	            
	            montantTVA=Double.parseDouble(df.format(((totalHT*TVA)/100)).replace(',', '.'));;
	            
	            totalTTC=Double.parseDouble(df.format(totalHT+montantTVA).replace(',', '.'));
	            totalNET=totalTTC+commande.getFraitransport();
	            pu=0;
	            qte=1;
	            designation="";
	            verifier(montantTVA, totalTTC, totalHT,0);
	            msg = new FacesMessage("Article ajouter avec success");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	            ligne=null;
	    	} catch (Exception e) {
	    		 System.out.println("Erreur " +e.getLocalizedMessage());
			}
	    }
	  
	public void ajouterligne_modif(){
    	try {
    		FacesMessage msg;
    		DecimalFormat df = new DecimalFormat("0.##");
            df.setMinimumFractionDigits(2);
            managerApplication.setCommandes(null);
        	ligne=new Boncommande_Article();
        	article=managerObjetarticle.getObject(idarticle);
        	ligne.setPu(pu);
        	ligne.setQte(qte);
        	ligne.setArticle(article);
        	
        	if(designation.equals(""))
        		ligne.setDesignation(article.getDesignation());
        	else
        		ligne.setDesignation(designation);
        	
        	double res=(ligne.getPu()*ligne.getQte());
        	ligne.setPt(Double.parseDouble(df.format(res).replace(',', '.').replace(',', '.')));
            commande.setTotalht(Double.parseDouble(df.format(commande.getTotalht()+ligne.getPt()).replace(',', '.')));
            res=((TVA*commande.getTotalht())/100);
            
            commande.setTva(Double.parseDouble(df.format(res).replace(',', '.')));
           
            commande.setTotalttc( Double.parseDouble(df.format(commande.getTotalht()+commande.getTva()).replace(',', '.')));
            
            commande.setTotalnet(Double.parseDouble(df.format(commande.getTotalttc()+commande.getFraitransport()).replace(',', '.')));
            
            verifier(montantTVA, totalTTC, totalHT,0);
            managerjdbc.updatePrixBoncommande(commande);
            ligne.setCommande(commande);
            //managerarticle.insertObject(ligne);
            managerjdbc.insertBoncommandeArticle(ligne);
            pu=0;
            qte=0;
            designation="";
            lignes.add(ligne);
            msg = new FacesMessage("Article ajouter avec success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            msg = new FacesMessage("Bon commande est modifier avec success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
    	} catch (Exception e) {
    		 System.out.println("ERROOOOORRRRRRRRRR");
		}
    }
	    
	public void DeleteArticle(){
		
	        FacesMessage msg;
			DecimalFormat df = new DecimalFormat("0.##");
	        df.setMinimumFractionDigits(2);
	        try {	

	        	for (int i = 0; i < lignetempo.size(); i++) {
					
				
	        		totalHT=Double.parseDouble(df.format(totalHT-lignetempo.get(i).getPt()).replace(',', '.'));
	        		
	        		montantTVA=Double.parseDouble(df.format(((totalHT*TVA)/100)).replace(',', '.'));
	        		
	        		totalTTC=Double.parseDouble(df.format(totalHT+montantTVA).replace(',', '.'));
	        		totalNET=totalTTC+commande.getFraitransport();
	        		lignes.remove(lignetempo.get(i)); 
	        		ligneaddarticles.remove(lignetempo.get(i));
	        		msg = new FacesMessage("Article est supprimer de Bon Commande");
	        		FacesContext.getCurrentInstance().addMessage(null, msg);
	        	}
	           verifier(montantTVA, totalTTC, totalHT,0);
	            } catch (Exception e) {
	                msg = new FacesMessage("ERORR SUPPRESSION");
	           FacesContext.getCurrentInstance().addMessage(null, msg);
	            }   
	    }
	
    public void DeleteArticle_modif(){
        FacesMessage msg;
		DecimalFormat df = new DecimalFormat("0.##");
        df.setMinimumFractionDigits(2);
        managerApplication.setCommandes(null);
        try {
        	for(int i=0;i<lignetempo.size();i++){
         		boolean op=managerjdbc.deleteArtilceToBoncommande(lignetempo.get(i));

        		if(op==true){
            		lignes.remove(lignetempo.get(i));
    	        		commande.setTotalht(Double.parseDouble(df.format(commande.getTotalht()-lignetempo.get(i).getPt()).replace(',', '.')));
    	        		double resultattva=Double.parseDouble(df.format(((commande.getTotalht()*TVA)/100)).replace(',', '.'));
    	        		commande.setTva(resultattva);
    	        		
    	        		commande.setTotalttc(Double.parseDouble(df.format(commande.getTotalht()+commande.getTva()).replace(',', '.')));
    	        		
    	        		commande.setTotalnet(Double.parseDouble(df.format(commande.getTotalht()+commande.getTva()+commande.getFraitransport()).replace(',', '.')));
    	        		
  
        		}
        	}
    		int  opupdate=managerjdbc.updatePrixBoncommande(commande);
    		if(opupdate==1){
        		msg = new FacesMessage("Article est supprimer de Bon commande");
        		FacesContext.getCurrentInstance().addMessage(null, msg);
    		}else{
        		msg = new FacesMessage("Probleme de Modification","contacter Administration");
        		FacesContext.getCurrentInstance().addMessage(null, msg);
    		}

       
            } catch (Exception e) {
                msg = new FacesMessage("Exception "+ e.getLocalizedMessage());
           FacesContext.getCurrentInstance().addMessage(null, msg);
            }   
    }
    
	private void verifier(double tva,double ttc,double ht,double net){
		if(ht<0){
			ht=0;
			ttc=0;
			tva=0;
			net=0;
		}
	}

	private void vider() {
		numerocommande="";
		numerooffrefournisseur="";
		echeance="";
		TVA=20;
		totalHT=0;
		totalTTC=0;
		montantTVA=0;
		offres=new ArrayList<Offre>();
		offres=managerOffre.getObject();
		tempos=managerOffre.getObject();
		offresadd=new ArrayList<Offre>();
	}

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg;
        try {
           manager.updateObject(((BonCommande) event.getObject()));
            msg = new FacesMessage("Bon Commande est bien Modifier",""+((BonCommande) event.getObject()).getNumercommande());
               FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
           msg = new FacesMessage("ERROR commande",""+((BonCommande) event.getObject()).getNumercommande());
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
       
   }
    
   public void onRowCancel(RowEditEvent event) {
      FacesMessage msg = new FacesMessage("Modification ete Annuler",""+((BonCommande) event.getObject()).getNumercommande());
       FacesContext.getCurrentInstance().addMessage(null, msg);
   }
   
   public void createPdf(String dest) throws NullPointerException{
	   
	
	   	DateFormat format=new SimpleDateFormat("dd/MM/yy");

	   	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");

   		Paragraph saute=new Paragraph("\n");
   		//commande=manager.getObject(commande.getId());
   		try {
   			
   		 float left = 36;
         float right = 36;
         float top = 60;
         float bottom = 100;
         
   			Document document = new Document(PageSize.A4,left, right, top, bottom);
   			HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();

           response.setContentType("application/pdf");
           response.setHeader("Content-Disposition", "inline; filename=BonCommande_"+commande.getNumercommande()+".pdf");
           PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
           document.open();
           Font ftext=new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL,new BaseColor(Color.black));
           Font ftextgra=new Font(Font.FontFamily.HELVETICA,10,Font.BOLD,new BaseColor(Color.black));
           Font funderline=new Font(Font.FontFamily.HELVETICA,12,Font.UNDERLINE,new BaseColor(Color.black));
           
           
           //document.setMargins(36, 36, 108, 180);
           document.setMargins(left, right,108, bottom);
           
           document.add(saute);
           document.add(saute);
           document.add(saute);
           
           PdfPTable tableinfo = new PdfPTable(1);
           PdfPCell l = new PdfPCell(new Phrase("Date BC:",ftextgra));
           l.setHorizontalAlignment(Element.ALIGN_LEFT);
           l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
           tableinfo.addCell(l);
           PdfPCell lval;
           if(commande.getDatecommande()!=null){
           lval = new PdfPCell(new Phrase(""+format.format(commande.getDatecommande()),ftext));
           lval.setHorizontalAlignment(Element.ALIGN_CENTER);
           tableinfo.addCell(lval);
           }
           l = new PdfPCell(new Phrase("Fournisseur:",ftextgra));
           l.setHorizontalAlignment(Element.ALIGN_LEFT);
           l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
           tableinfo.addCell(l);
           if(commande.getFournisseur().getNomsociete()==null)
        	   commande.getFournisseur().setNomsociete(" ");
           lval = new PdfPCell(new Phrase(commande.getFournisseur().getNomsociete(),ftext));
           lval.setHorizontalAlignment(Element.ALIGN_CENTER);
           tableinfo.addCell(lval);
           
           l = new PdfPCell(new Phrase("Adresse:",ftextgra));
           l.setHorizontalAlignment(Element.ALIGN_LEFT);
           l.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
           tableinfo.addCell(l);
           
           lval = new PdfPCell(new Phrase(commande.getFournisseur().getAdresse() +"\n"+commande.getFournisseur().getVille().getVille(),ftext));
           lval.setHorizontalAlignment(Element.ALIGN_CENTER);
           tableinfo.addCell(lval);
           
           tableinfo.setHorizontalAlignment(tableinfo.ALIGN_RIGHT);
           tableinfo.setWidthPercentage(30);
           document.add(tableinfo);
           
           document.add(saute);
           Paragraph Numerocommande=new Paragraph("Bon de Commande N° : "+commande.getNumercommande(),new Font(Font.FontFamily.COURIER,15,Font.BOLD,new BaseColor(Color.black)));
           Numerocommande.setAlignment(Element.ALIGN_LEFT);
           Numerocommande.setIndentationLeft(70);
           document.add(Numerocommande);
           document.add(saute);
           
           PdfPTable tableoffre = new PdfPTable(3);
           tableoffre.setHeaderRows(1);
           tableoffre.setHorizontalAlignment(Element.ALIGN_RIGHT);
           tableoffre.setWidthPercentage(90);
           PdfPCell c1 = new PdfPCell(new Phrase("Date OP",ftextgra));
           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
           tableoffre.addCell(c1);
           
           c1 = new PdfPCell(new Phrase("N°OP",ftextgra));
           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
           tableoffre.addCell(c1);
           
           c1 = new PdfPCell(new Phrase("Echeance",ftextgra));
           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
           tableoffre.addCell(c1);

           if(commande.getDateoffre()!=null)
           c1 = new PdfPCell(new Phrase(format.format(commande.getDateoffre()),ftext));
           else
        	   c1 = new PdfPCell(new Phrase(" ")); 
           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
           tableoffre.addCell(c1);
           
           c1 = new PdfPCell(new Phrase(commande.getNumeroOffreFournisseur(),ftext));
           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
           tableoffre.addCell(c1);
           
           c1 = new PdfPCell(new Phrase(commande.getEcheance(),ftext));
           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
           tableoffre.addCell(c1);
           
           document.add(tableoffre);
           document.add(saute);
           PdfPTable table = new PdfPTable(5);
           table.setWidths(new float[]{30,90,20,30,30});
           table.setHeaderRows(1);
           table.setHorizontalAlignment(Element.ALIGN_RIGHT);
           table.setWidthPercentage(90);
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

           c1 = new PdfPCell(new Phrase("P.T HT",ftextgra));
           c1.setHorizontalAlignment(Element.ALIGN_CENTER);
           c1.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
           table.addCell(c1);
           table.setHeaderRows(1);
           List<Boncommande_Article> offreArticle=managerarticle.getObjects(commande.getId());

           DecimalFormat df = new DecimalFormat();
           df.setDecimalSeparatorAlwaysShown(true);
           df.setMaximumFractionDigits(2);
           df.setMinimumFractionDigits(2);
           
           for(int i=0;i<offreArticle.size();i++)
           {
        	PdfPCell cart = new PdfPCell(new Phrase(offreArticle.get(i).getArticle().getRef(),ftext));
      
        	cart.setHorizontalAlignment(Element.ALIGN_CENTER);
        	cart.setVerticalAlignment(Element.ALIGN_CENTER);
        	table.addCell(cart);
        	cart = new PdfPCell(new Phrase(offreArticle.get(i).getDesignation(),ftext));
        	table.addCell(cart);
        	cart = new PdfPCell(new Phrase(""+offreArticle.get(i).getQte(),ftext));
        	cart.setHorizontalAlignment(Element.ALIGN_CENTER);
        	cart.setVerticalAlignment(Element.ALIGN_CENTER);
        	table.addCell(cart);
        	cart = new PdfPCell(new Phrase(df.format(offreArticle.get(i).getPu()),ftext));
        	cart.setHorizontalAlignment(Element.ALIGN_RIGHT);
        	table.addCell(cart);
        	cart = new PdfPCell(new Phrase(df.format(offreArticle.get(i).getPt()),ftext));
        	cart.setHorizontalAlignment(Element.ALIGN_RIGHT);
        	table.addCell(cart);
           }
           ObjetPrix cmdprix=managerjdbc.gettotalPrix(commande);
           if(commande.getTypepaiement().getTypepaiement().equals("MAD")){
		           PdfPCell totalHT = new PdfPCell(new Phrase("Total HT",ftextgra));
		           totalHT.setColspan(4);
		           totalHT.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(totalHT);
		           
		           PdfPCell restotalHT = new PdfPCell(new Phrase(df.format(Double.parseDouble(cmdprix.getTotalHT())),ftextgra));
		           restotalHT.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.addCell(restotalHT);
		           
		           PdfPCell tva = new PdfPCell(new Phrase("TVA 20%",ftextgra));
		           tva.setColspan(4);
		           tva.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(tva);
		           
		           PdfPCell restva = new PdfPCell(new Phrase(df.format(Double.parseDouble(cmdprix.getTva()))+"",ftextgra));
		           restva.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.addCell(restva);
		           PdfPCell totalTTC = new PdfPCell(new Phrase("Total TTC",ftextgra));
		           totalTTC.setColspan(4);
		           totalTTC.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(totalTTC);
		          
		           cmdprix.setTotalTTC(cmdprix.getTotalTTC().replace(".", ","));
		           
		           String[] reel=cmdprix.getTotalTTC().split(",");

		           PdfPCell restotalTTC;
		           if(reel.length>1){
		        	   char[] caracteres=reel[1].toCharArray();
		        	   if(caracteres.length>1)
		        		   restotalTTC = new PdfPCell(new Phrase(df.format(Double.parseDouble(reel[0])).split(",")[0]+","+caracteres[0]+""+caracteres[1],ftextgra));
		        	   else
		        		   restotalTTC = new PdfPCell(new Phrase(df.format(Double.parseDouble(reel[0])).split(",")[0]+","+caracteres[0]+"0",ftextgra));
		           }
		        	 
		           else
		        	  restotalTTC = new PdfPCell(new Phrase(df.format(Double.parseDouble(cmdprix.getTotalTTC())),ftextgra));
		           restotalTTC.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.addCell(restotalTTC);
		           if(commande.getFraitransport()>0){
			           PdfPCell fraitranspot = new PdfPCell(new Phrase("Frais d'enregistrement de transit",ftextgra));
			           fraitranspot.setColspan(4);
			           fraitranspot.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
			           table.addCell(fraitranspot);
			           PdfPCell resfrai = new PdfPCell(new Phrase(df.format(commande.getFraitransport())+"",ftextgra));
			           resfrai.setHorizontalAlignment(Element.ALIGN_RIGHT);
			           table.addCell(resfrai);
			           PdfPCell totalnet = new PdfPCell(new Phrase("TOTAL NET 1",ftextgra));
			           totalnet.setColspan(4);
			           totalnet.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
			           table.addCell(totalnet);
			           PdfPCell restotalNET = new PdfPCell(new Phrase(df.format(commande.getTotalnet())+"",ftextgra));
			           restotalNET.setHorizontalAlignment(Element.ALIGN_RIGHT);
			           table.addCell(restotalNET);
		           	}
           }else{
	           PdfPCell totalHT = new PdfPCell(new Phrase("Total en "+commande.getTypepaiement().getTypepaiement().toLowerCase(),ftextgra));
	           totalHT.setColspan(4);
	           totalHT.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
	           table.addCell(totalHT);
	           
	           PdfPCell restotalHT = new PdfPCell(new Phrase(df.format(commande.getTotalht()),ftextgra));
	           restotalHT.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           table.addCell(restotalHT);
	           if(commande.getFraitransport()>0){
		           PdfPCell fraitranspot = new PdfPCell(new Phrase("Frais d'enregistrement de transit et de transport ",ftextgra));
		           fraitranspot.setColspan(4);
		           fraitranspot.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(fraitranspot);
		           PdfPCell resfrai = new PdfPCell(new Phrase(df.format(commande.getFraitransport())+"",ftextgra));
		           resfrai.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.addCell(resfrai);
		           PdfPCell totalnet = new PdfPCell(new Phrase("TOTAL NET 1 en "+commande.getTypepaiement().getTypepaiement().toLowerCase(),ftextgra));
		           totalnet.setColspan(4);
		           totalnet.setBackgroundColor(new BaseColor(Color.decode("#BCE1EC")));
		           table.addCell(totalnet);
		           PdfPCell restotalNET = new PdfPCell(new Phrase(df.format(commande.getTotalnet())+"",ftextgra));
		           restotalNET.setHorizontalAlignment(Element.ALIGN_RIGHT);
		           table.addCell(restotalNET);
	           	}
           }
           
           document.add(table);
           
		           if(commande.getClient()!=null){
		        	   
		        	   System.out.println("Client Final");
			        	   Paragraph pie=new Paragraph();
			        	   if(commande.getClient().getSociete()!=null){
			        		   if(!commande.getClient().getSociete().equals("")){
			        			   pie.add(new Phrase("Client Final :",ftextgra));
						           pie.add(new Phrase(commande.getClient().getSociete()));
			        		   }	
			        	   }
				   
				           if(commande.getClient().getContactprincipal()!=null )
				           {
				        	   System.out.println("TEST CONTACT");
				        	   if(!commande.getClient().getContactprincipal().equals("")){
				        		   System.out.println("TEST 2 CONTACT");
				        		   pie.add(new Phrase("\nContact       :",ftextgra));
					        	   pie.add(new Phrase(commande.getClient().getContactprincipal().getNom()+" "+commande.getClient().getContactprincipal().getPrenom()));
					           
				        	   }
				           }
				           if(commande.getClient().getTel()!=null)
				           {
				        	   if(!commande.getClient().getTel().equals("")){
				        		   pie.add(new Phrase("\nTel               :",ftextgra));
					        	   pie.add(new Phrase(commande.getClient().getTel()));
				        	   }
				        	   
				           }
				           if(commande.getClient().getContactprincipal()!=null)
				           {
				        	   if(!commande.getClient().getContactprincipal().equals("")){
				        		   pie.add(new Phrase("\nmail             :",ftextgra));
					        	   pie.add(new Phrase(commande.getClient().getContactprincipal().getEmail()));
				        	   }
				        	
				           }
				           
				           if(!commande.getClient().getAdresse().equals("")){
				        	   pie.add(new Phrase("\nAdresse      :",ftextgra));
					           pie.add(new Phrase(commande.getClient().getAdresse()+"\n                   "+commande.getClient().getVille().getVille()+" "+commande.getClient().getPays().getPays()));
					            
				           }
				         pie.setIndentationLeft(53);
				           document.add(saute);
				           document.add(saute);
				           document.add(pie);
		           }

    

           document.addAuthor("IMS Technology");
           document.addTitle("Bon Commande");
           document.addSubject("Bon Commande N°"+commande.getNumercommande());
           document.addCreator("RABEH");
           document.close();
           
       	FacesContext.getCurrentInstance().responseComplete();
       } catch (DocumentException | IOException de) {
           System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF");
           FacesContext.getCurrentInstance().responseComplete();
           FacesMessage msg;
           msg = new FacesMessage("Exception:",de.getMessage());
    		FacesContext.getCurrentInstance().addMessage(null, msg);
       }
   }
   
		   /****************PDF************************/
			 
		public void imprimerfont() throws NullPointerException{
		String dest="D:/IMS.pdf";
		File file = new File(dest);
		file.getParentFile().mkdirs();
		try {
			  
			createPdf(dest);
			createPdfImagefont();
			 
			
		} catch (IOException | DocumentException | NullPointerException e) {
		
			e.printStackTrace();
		    FacesMessage msg;
		    msg = new FacesMessage("Exception:",e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		}
		
		public void imprimer() throws NullPointerException{
		String dest="D:/IMS.pdf";
		File file = new File(dest);
		file.getParentFile().mkdirs();
		try {
			  
			createPdf(dest);
			createPdfImage();
			 
			
		} catch (IOException | DocumentException | NullPointerException e) {
		
			e.printStackTrace();
		    FacesMessage msg;
		    msg = new FacesMessage("Exception:",e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		}
		
		public void createPdfImagefont() throws FileNotFoundException, DocumentException, IOException,NullPointerException{
		
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
		Image img = Image.getInstance(webroot+"\\fondfacture.png"); 
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
		over.setFontAndSize(bf, 15);
		over.endText(); 
		} 
		stamp.close();	
		
		}

	public void createPdfImage() throws FileNotFoundException, DocumentException, IOException,NullPointerException{
	
	PdfReader reader;			
	reader = new PdfReader("D:/IMS.pdf");
	
	int n = reader.getNumberOfPages(); 
	// Create a stamper that will copy the document to a new file 
	//PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("D:/boncmd.pdf")); 
	HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	PdfStamper stamp = new PdfStamper(reader,response.getOutputStream()); 
		String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
	int i = 1; 
	//com.itextpdf.text.pdf.PdfContentByte under; 
	com.itextpdf.text.pdf.PdfContentByte over; 
	Image img = Image.getInstance(webroot+"\\fondlogoancien.png"); 
	img.scaleAbsoluteHeight(PageSize.A4.getHeight());
	img.scaleAbsoluteWidth(PageSize.A4.getWidth());
	img.setAbsolutePosition(0, 0);
	BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI, BaseFont.EMBEDDED); 
	
	
	for(i=1;i<=n;i++){ 
		over = stamp.getOverContent(i); 
		over.beginText(); 
		over.setFontAndSize(bf, 15);
		over.endText(); 
	} 
	stamp.close();	
	
	}

   /*****************fin pdf**********************/
   
	public ModelService<BonCommande> getManager() {
		return manager;
	}
	public void setManager(ModelService<BonCommande> manager) {
		this.manager = manager;
	}
	public BonCommande getCommande() {
		return commande;
	}
	public void setCommande(BonCommande commande) {
		this.commande = commande;
	}
	public List<BonCommande> getCommandes() {
			commandes=managerApplication.getCommandes();
		return commandes;
	}
	public void setCommandes(List<BonCommande> commandes) {
		this.commandes = commandes;
	}
	public List<BonCommande> getFilteredcommandes() {
		return filteredcommandes;
	}
	public void setFilteredcommandes(List<BonCommande> filteredcommandes) {
		this.filteredcommandes = filteredcommandes;
	}

	public ModelService<Boncommande_Article> getManagerarticle() {
		return managerarticle;
	}

	public void setManagerarticle(ModelService<Boncommande_Article> managerarticle) {
		this.managerarticle = managerarticle;
	}

	public ModelService<Fournisseur> getManagerF() {
		return managerF;
	}

	public void setManagerF(ModelService<Fournisseur> managerF) {
		this.managerF = managerF;
	}

	public ModelService<Offre> getManagerOffre() {
		return managerOffre;
	}

	public void setManagerOffre(ModelService<Offre> managerOffre) {
		this.managerOffre = managerOffre;
	}

	public int getIdf() {
		return idf;
	}

	public void setIdf(int idf) {
		this.idf = idf;
	}

	public int getIdoffre() {
		return idoffre;
	}

	public void setIdoffre(int idoffre) {
		this.idoffre = idoffre;
	}

	public String getNumerocommande() {
    	Calendar cal = Calendar.getInstance();
    	String chaine=""+cal.get(Calendar.YEAR);
    	String[] tab=chaine.split("0");
    	int lastnumbre=managerjdbc.getLastNumbre("BonCommande","numercommande",chaine);
    	lastnumbre++;
    	String nombre=String.format("%03d",lastnumbre);
    	numerocommande=tab[1]+"BC"+nombre;
		return numerocommande;
	}

	public void setNumerocommande(String numerocommande) {
		this.numerocommande = numerocommande;
	}

	public String getNumerooffrefournisseur() {
		return numerooffrefournisseur;
	}

	public void setNumerooffrefournisseur(String numerooffrefournisseur) {
		this.numerooffrefournisseur = numerooffrefournisseur;
	}

	public String getEcheance() {
		return echeance;
	}

	public void setEcheance(String echeance) {
		this.echeance = echeance;
	}

	public Boncommande_Article getLigne() {
		return ligne;
	}

	public void setLigne(Boncommande_Article ligne) {
		this.ligne = ligne;
	}

	public List<Boncommande_Article> getLignes() {
		return lignes;
	}

	public void setLignes(List<Boncommande_Article> lignes) {
		this.lignes = lignes;
	}

	public List<Boncommande_Article> getLignesView() {
		return lignesView;
	}

	public void setLignesView(List<Boncommande_Article> lignesView) {
		this.lignesView = lignesView;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Fournisseur getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}

	public int getIdarticle() {
		return idarticle;
	}

	public void setIdarticle(int idarticle) {
		this.idarticle = idarticle;
	}

	public ModelService<Article> getManagerObjetarticle() {
		return managerObjetarticle;
	}

	public void setManagerObjetarticle(ModelService<Article> managerObjetarticle) {
		this.managerObjetarticle = managerObjetarticle;
	}

	public List<Offre> getOffres() {
		return offres;
	}

	public void setOffres(List<Offre> offres) {
		this.offres = offres;
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

	public ModelService<Boncommande_Offre> getManagerBoncommandeOffre() {
		return managerBoncommandeOffre;
	}

	public void setManagerBoncommandeOffre(
			ModelService<Boncommande_Offre> managerBoncommandeOffre) {
		this.managerBoncommandeOffre = managerBoncommandeOffre;
	}

	public List<Offre> getOffresmodif() {
		offresmodif=managerOffre.getObject();
		offresmodif.removeAll(commande.getListeoffres());
		return offresmodif;
	}

	public void setOffresmodif(List<Offre> offresmodif) {
		this.offresmodif = offresmodif;
	}

	public List<Offre> getTempos() {
		return tempos;
	}

	public void setTempos(List<Offre> tempos) {
		this.tempos = tempos;
	}

	public List<Boncommande_Article> getLigneaddarticles() {
		return ligneaddarticles;
	}

	public void setLigneaddarticles(List<Boncommande_Article> ligneaddarticles) {
		this.ligneaddarticles = ligneaddarticles;
	}

	public Date getDatecommande() {
		Calendar cal = Calendar.getInstance();
		datecommande=cal.getTime();
		return datecommande;
	}

	public void setDatecommande(Date datecommande) {
		this.datecommande = datecommande;
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public boolean isCheckaffect() {
		return checkaffect;
	}

	public void setCheckaffect(boolean checkaffect) {
		this.checkaffect = checkaffect;
	}

	public ModelService<Offre_Article> getManagerOffreArticle() {
		return managerOffreArticle;
	}

	public void setManagerOffreArticle(
			ModelService<Offre_Article> managerOffreArticle) {
		this.managerOffreArticle = managerOffreArticle;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public ModelService<Client> getManagerC() {
		return managerC;
	}

	public void setManagerC(ModelService<Client> managerC) {
		this.managerC = managerC;
	}

	public int getIdc() {
		return idc;
	}

	public void setIdc(int idc) {
		this.idc = idc;
	}

	public void setListes(List<ListeBoncommandeFactureOffre> listes) {
		this.listes = listes;
	}

	public ListeBoncommandeFactureOffre getListe() {
		return liste;
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

	public List<ListeBoncommandeFactureOffre> getListes() {
		return listes;
	}

	public void setListe(ListeBoncommandeFactureOffre liste) {
		this.liste = liste;
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

	public double getPu() {
		return pu;
	}

	public void setPu(double pu) {
		this.pu = pu;
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

	public double getMontantTVA() {
		return montantTVA;
	}

	public void setMontantTVA(double montantTVA) {
		this.montantTVA = montantTVA;
	}

	public Date getDateechance() {
		return dateechance;
	}

	public void setDateechance(Date dateechance) {
		this.dateechance = dateechance;
	}


	public double getTotalNET() {
		return totalNET;
	}

	public void setTotalNET(double totalNET) {
		this.totalNET = totalNET;
	}

	public List<Boncommande_Article> getLignetempo() {
		return lignetempo;
	}

	public void setLignetempo(List<Boncommande_Article> lignetempo) {
		this.lignetempo = lignetempo;
	}

	public String getStr_commande() {
		str_commande="Bon Commande Fournisseur";
		return str_commande;
	}

	public void setStr_commande(String str_commande) {
		this.str_commande = str_commande;
	}

	public List<String> getEtatannuler() {
		return etatannuler;
	}

	public List<String> getEtats() {
		return etats;
	}
	
	
	
}
