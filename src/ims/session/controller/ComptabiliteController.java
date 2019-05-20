package ims.session.controller;


import ims.model.entities.BonCommande;
import ims.model.entities.CoordonneeBancaire;
import ims.model.entities.Facture;
import ims.model.entities.FactureFournisseur;
import ims.model.entities.TypeFacture;
import ims.model.entities.TypePaiement;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.CellEditEvent;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;


@ManagedBean
@SessionScoped
public class ComptabiliteController implements Serializable {

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
	
	@ManagedProperty(value="#{FactureManager}")
	private ModelService<Facture> managerFacture;
	
    @ManagedProperty(value="#{FactFournisseurManager}")
    private ModelService<FactureFournisseur> managerFactFournisseur;
	
	@ManagedProperty(value="#{boncommandeManager}")
	private ModelService<BonCommande> managerbc;
	
 	@ManagedProperty(value="#{TypefactManager}")
 	private ModelService<TypeFacture> managerTypefacture;
 	
    @ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
    
	@ManagedProperty(value="#{CoordonneeBanqueManager}")
	private ModelService<CoordonneeBancaire> managerbanque;
	
    @ManagedProperty(value="#{TypepaiementManager}")
    private ModelService<TypePaiement> managerTypePaiement;
    
 	private FactureFournisseur facturefournisseur=new FactureFournisseur();
 	private List<FactureFournisseur> facturesfournisseurs=null;
 	
 	private Facture facture=new Facture();
 	private List<Facture> factures=null;
 	private List<Facture> facturesforbc=null;
 	private List<Facture> filtredfactures=new ArrayList<Facture>();
 	private Facture fact=new Facture();
 	private List<Facture> tablefact;
 	
 	private BonCommande boncommande=new BonCommande();
 	private BonCommande bc=null;
 	private List<BonCommande> boncommandes=new ArrayList<BonCommande>();
 	
 	private BonCommande commande=new BonCommande();
 	private List<BonCommande> commandes=new ArrayList<BonCommande>();
 	private List<BonCommande> filtredbc=new ArrayList<BonCommande>();
 	
 	private List<TypePaiement> typepaiements;
 	private List<TypePaiement> filtredtypepaiements;
 	
 	private TypePaiement typepaiement=new TypePaiement();
 	
	private final List<String> etats=new ArrayList<String>() {
		private static final long serialVersionUID = 1L;

	{ add("");add("réglée");add("Non réglée");add("PROFORMA");}};
 	
	//------------------------------------------------------------
	private String 				temporegler="";
	private String 				temporester="";
	private String 				tempoetat="";
	private String 				temporeglement="";
	private Date   				tempodateechance=null;
	private Date 				tempodateechancep=null;
	private Date 				tempodatereglement;
	private String 				temporeglerbc="";
	private String 				temporesterbc="";
	private String 				tempoetatbc="";
	private String 				temporeglementbc="";
	private Date   				tempodateechancebc=null;
	private String 				tempocontsat="";
	private String 				tempocontsatbc="";
	private Date 				tempodateconstater=null;
	private SimpleDateFormat 	ft= new SimpleDateFormat("dd-MM-yyyy");
	//-----------------------------------------------------
	private String str_factures;
	private String str_facturesfournisseurs;
	
	
 	public String getStr_factures() {
 		str_factures="Mouvement Facture Client";
		return str_factures;
	}

	public void setStr_factures(String str_factures) {
		this.str_factures = str_factures;
	}

	public String getStr_facturesfournisseurs() {
		str_facturesfournisseurs="Mouvement facture Fournisseur";
		return str_facturesfournisseurs;
	}

	public void setStr_facturesfournisseurs(String str_facturesfournisseurs) {
		this.str_facturesfournisseurs = str_facturesfournisseurs;
	}

	@PostConstruct
 	public void init(){
 	}
 	
 	@PreDestroy
 	public void destroy(){
 		
 	}
 	
 	
 	
 	public String prepareList() {
		 return "index?faces-redirect=true";
	}
 	
 	public void actualiser(){
 		managerApplication.setFacturemov(null);
 		managerApplication.setFacturesfournisseur(null);
 		boncommandes=null;
 	}
 	
	public String prepareListbc() {
		 return "movbc?faces-redirect=true";
	}
	
	
	public String prepare(){
		System.out.println("----------------preparecreate--------------------------");
		typepaiement=new TypePaiement();
		return "createdevise?faces-redirect=true";
	}
	
	public String preparetypepaiement(){
		return "typePaiement?faces-redirect=true";
	}
	
	public String preparecaisse(){
		return "caisses?faces-redirect=true";
	}
	
	public void insertdevise(){
		
		FacesMessage msg;
		
		try {
			managerTypePaiement.insertObject(typepaiement);
			typepaiement=new TypePaiement();
		    msg = new FacesMessage("devise est creer avec success");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			
		    msg = new FacesMessage(" Exception Probleme de regelement...");
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}
 	
 	
	public void exporterdatapdf(){
		System.out.println("Imprimer*************** SUCCES");
    	
    	String webroot= ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("\\resources\\images");
        System.out.println("webroot");
    	System.out.println(webroot); 
    	System.out.println("webroot FIN");
    	Phrase saute=new Phrase("\n");
        Document document = new Document(PageSize.A4);

        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        
        try {
            String dest="C:/IMS.pdf";
            File file = new File(dest);
            file.getParentFile().mkdirs();
        	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=exporter.pdf");

            //document.setMargins(36, 72, 108, 180);
            document.setMargins(36, 36, 108, 180);
            System.out.println("Imprimer***************2 SUCCES");
            document.add(saute);
            document.add(new Paragraph("HELLO"));
            document.add(saute);
            document.close();
            FacesContext.getCurrentInstance().responseComplete();

        } catch (DocumentException | IOException de) {
            System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROR PDF"+de.getLocalizedMessage());
            document.close();
            FacesContext.getCurrentInstance().responseComplete();
        }
	}
 		
 	public void onCellEditBc(){
 		FacesMessage msg;
 		DecimalFormat df = new DecimalFormat("0.##");
 		try {
 			System.out.println("-------onCellEditBc-------");
 	 		System.out.println("tempoetat  ====>"+tempoetatbc);
 	 		System.out.println("temporegler====>"+temporeglerbc);
 	 		System.out.println("temporester====>"+temporesterbc);
 	 		System.out.println("-------onCellEditBc-------");
 	 		System.out.println("Boncommande ID ====>"+boncommande.getId());
 	 		System.out.println("-------onCellEditBc-------");

 		 			if(tempoetatbc!=""){
 		 				System.out.println("tempoetatbc");
 		 				boncommande.setEtat(tempoetatbc);
 		 			}
 		 			if(temporeglementbc!=""){
 		 				System.out.println("temporeglementbc");
 		 				System.out.println("reg"+temporeglementbc);
 		 				boncommande.setReglement(temporeglementbc);
 		 			}
 		 			if(tempodateechancebc!=null){
 		 				System.out.println("tempodateechancebc");
 		 				System.out.println("date "+tempodateechancebc);
 		 				boncommande.setDateechance(tempodateechancebc);
 		 			}
 		 			if(tempocontsatbc!=""){
 		 				System.out.println("tempocontsatbc");
 		 				System.out.println("tempocontsatbc="+tempocontsatbc);
 		 				boncommande.setConstater(tempocontsatbc);
 		 			}
 		 		int res=managerjdbc.UpdateCompatbiliteBC(boncommande);
 		 		if(res>0){
 		 			msg = new FacesMessage("Reglement Bon commande est Bien Enregistrer");
 		 			FacesContext.getCurrentInstance().addMessage(null, msg);
 		 		}else{
 		 		    msg = new FacesMessage("Probleme de regelement...");
 		 		    FacesContext.getCurrentInstance().addMessage(null, msg);
 		 		}
 		 		
 			    tempoetatbc="";
 			    temporeglerbc="";
 			    temporesterbc="";
 			    temporeglementbc="";
 			    tempocontsatbc="";
 			    tempodateechancebc=null;
		} catch (Exception e) {
	 		    msg = new FacesMessage("Exception :",e.getMessage());
	 		    FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
 	}
 	
 	public void onCellEdit(CellEditEvent event){
 		FacesMessage msg;
 		managerApplication.setFacturemov(null);
 		DecimalFormat df = new DecimalFormat("0.##");
 		try {
 		 				facture.setMontantrester(facture.getTotalttcpaye()-facture.getMontantregler());
 		 			if(tempoetat!=""){
 		 				System.out.println("tempoetat");
 		 				facture.setEtat(tempoetat);
 		 				if(tempoetat.equals("PROFORMA"))
 		 					facture.setConstater("NA");
 		 			}
 		 			if(tempocontsat!=""){
 		 				System.out.println("tempocontsat");
 		 				System.out.println("tempocontsat="+tempocontsat);
 		 				facture.setConstater(tempocontsat);
 		 				if(facture.getConstater().equals("Oui"))
 		 					facture.setDateconstater(new Date());
 		 			}
 		 			facture.setMontantregler(Double.parseDouble(df.format(facture.getMontantregler()).replace(',','.'))); 
 		 	 		facture.setMontantrester(Double.parseDouble(df.format(facture.getMontantrester()).replace(',','.')));
 		 		int res=managerjdbc.UpdateCompatbiliteFacture(facture);
 		 		if(res>0){
 		 			msg = new FacesMessage("Reglement facture est Bien Enregistrer");
 		 			FacesContext.getCurrentInstance().addMessage(null, msg);
 		 		}else{
 		 		    msg = new FacesMessage("Probleme de regelement...");
 		 		    FacesContext.getCurrentInstance().addMessage(null, msg);
 		 		}
 		 		tempocontsat="";
 		 		tempoetat="";
		} catch (Exception e) {
	 		    msg = new FacesMessage("Exception :",e.getMessage());
	 		    FacesContext.getCurrentInstance().addMessage(null, msg);
		}

 	
 	}
 	
 	public void onCellEditfactfournisseur(CellEditEvent event){
 		FacesMessage msg;
 		managerApplication.setFacturesfournisseur(null);
 		DecimalFormat df = new DecimalFormat("0.##");
 		try {
 			facturefournisseur.setMontantrester(facturefournisseur.getTotalNet()-facturefournisseur.getMontantregler());
 		 			if(tempoetat!=""){
 		 				System.out.println("tempoetat");
 		 				facturefournisseur.setEtat(tempoetat);
 		 			}
 		 			if(tempocontsat!=""){
 		 				System.out.println("tempocontsat");
 		 				System.out.println("tempocontsat="+tempocontsat);
 		 				facturefournisseur.setConstater(tempocontsat);
 		 				if(facturefournisseur.getConstater().equals("Oui")){
 		 					facturefournisseur.setDateconstater(new Date());
 		 					managerjdbc.constaterBonCommande(facturefournisseur.getCommandefournissuer());
 		 				}else
 		 					managerjdbc.NonconstaterBonCommande(facturefournisseur.getCommandefournissuer());
 		 					
 		 			}
 		 			facturefournisseur.setMontantregler(Double.parseDouble(df.format(facturefournisseur.getMontantregler()).replace(',','.'))); 
 		 			facturefournisseur.setMontantrester(Double.parseDouble(df.format(facturefournisseur.getMontantrester()).replace(',','.')));
 		 		int res=managerjdbc.update(facturefournisseur);
 		 		if(res>0){
 		 			msg = new FacesMessage("Reglement facture Fournisseur est Bien Enregistrer");
 		 			FacesContext.getCurrentInstance().addMessage(null, msg);
 		 		}else{
 		 		    msg = new FacesMessage("Probleme de regelement...");
 		 		    FacesContext.getCurrentInstance().addMessage(null, msg);
 		 		}
 		 		tempocontsat="";
 		 		tempoetat="";
		} catch (Exception e) {
	 		    msg = new FacesMessage("Exception :",e.getMessage());
	 		    FacesContext.getCurrentInstance().addMessage(null, msg);
		}

 	
 	}
 	
 	
 	
	public ModelService<Facture> getManagerFacture() {
		return managerFacture;
	}
	public void setManagerFacture(ModelService<Facture> managerFacture) {
		this.managerFacture = managerFacture;
	}
	public ModelService<TypeFacture> getManagerTypefacture() {
		return managerTypefacture;
	}
	public void setManagerTypefacture(ModelService<TypeFacture> managerTypefacture) {
		this.managerTypefacture = managerTypefacture;
	}
	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}
	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}
	public ModelService<CoordonneeBancaire> getManagerbanque() {
		return managerbanque;
	}
	public void setManagerbanque(ModelService<CoordonneeBancaire> managerbanque) {
		this.managerbanque = managerbanque;
	}
	public ModelService<TypePaiement> getManagerTypePaiement() {
		return managerTypePaiement;
	}
	public void setManagerTypePaiement(
			ModelService<TypePaiement> managerTypePaiement) {
		this.managerTypePaiement = managerTypePaiement;
	}
	public Facture getFacture() {
		return facture;
	}
	public void setFacture(Facture facture) {
		this.facture = facture;
	}
	public List<Facture> getFactures() {
		factures=managerApplication.getFacturemov();
		return factures;
	}
	public void setFactures(List<Facture> factures) {
		this.factures = factures;
	}

	public String getTempoetat() {
		return tempoetat;
	}

	public void setTempoetat(String tempoetat) {
		this.tempoetat = tempoetat;
	}

	public List<String> getEtats() {
		return etats;
	}

	public String getTemporegler() {
		return temporegler;
	}

	public void setTemporegler(String temporegler) {
		this.temporegler = temporegler;
	}

	public String getTemporester() {
		return temporester;
	}

	public void setTemporester(String temporester) {
		this.temporester = temporester;
	}

	public String getTemporeglement() {
		return temporeglement;
	}

	public void setTemporeglement(String temporeglement) {
		this.temporeglement = temporeglement;
	}

	public Facture getFact() {
		return fact;
	}

	public void setFact(Facture fact) {
		this.fact = fact;
	}

	public ModelService<BonCommande> getManagerbc() {
		return managerbc;
	}

	public void setManagerbc(ModelService<BonCommande> managerbc) {
		this.managerbc = managerbc;
	}

	public BonCommande getBoncommande() {
		return boncommande;
	}

	public void setBoncommande(BonCommande boncommande) {
		this.boncommande = boncommande;
	}

	public void preparedialog(){
			if(fact!=null)
			boncommandes=managerjdbc.getNumbcForFacture(fact);
	}
	public void preparedialogfacture(){
		if(bc!=null)
		facturesforbc=managerjdbc.getNumfactForBC(bc);
    }
	public List<BonCommande> getBoncommandes() {
		if(facture!=null)
		boncommandes=managerjdbc.getNumbcForFacture(facture);
		return boncommandes;
	}

	public void setBoncommandes(List<BonCommande> boncommandes) {
		this.boncommandes = boncommandes;
	}

	public String getTemporeglementbc() {
		return temporeglementbc;
	}

	public void setTemporeglementbc(String temporeglementbc) {
		this.temporeglementbc = temporeglementbc;
	}

	public String getTemporeglerbc() {
		return temporeglerbc;
	}

	public void setTemporeglerbc(String temporeglerbc) {
		this.temporeglerbc = temporeglerbc;
	}

	public String getTemporesterbc() {
		return temporesterbc;
	}

	public void setTemporesterbc(String temporesterbc) {
		this.temporesterbc = temporesterbc;
	}

	public String getTempoetatbc() {
		return tempoetatbc;
	}

	public void setTempoetatbc(String tempoetatbc) {
		this.tempoetatbc = tempoetatbc;
	}

	public List<Facture> getFiltredfactures() {
		return filtredfactures;
	}

	public void setFiltredfactures(List<Facture> filtredfactures) {
		this.filtredfactures = filtredfactures;
	}

	public BonCommande getCommande() {
		return commande;
	}

	public void setCommande(BonCommande commande) {
		this.commande = commande;
	}

	public List<BonCommande> getCommandes() {
		commandes=managerbc.getObject();
		return commandes;
	}

	public void setCommandes(List<BonCommande> commandes) {
		this.commandes = commandes;
	}

	public List<BonCommande> getFiltredbc() {
		return filtredbc;
	}

	public void setFiltredbc(List<BonCommande> filtredbc) {
		this.filtredbc = filtredbc;
	}

	public List<Facture> getTablefact() {
		return tablefact;
	}

	public void setTablefact(List<Facture> tablefact) {
		this.tablefact = tablefact;
	}
	
 	public List<TypePaiement> getTypepaiements() {
 			typepaiements=managerTypePaiement.getObject();
		return typepaiements;
	}

	public void setTypepaiements(List<TypePaiement> typepaiements) {
		this.typepaiements = typepaiements;
	}

	public TypePaiement getTypepaiement() {
		return typepaiement;
	}

	public void setTypepaiement(TypePaiement typepaiement) {
		this.typepaiement = typepaiement;
	}

	public List<TypePaiement> getFiltredtypepaiements() {
		return filtredtypepaiements;
	}

	public void setFiltredtypepaiements(List<TypePaiement> filtredtypepaiements) {
		this.filtredtypepaiements = filtredtypepaiements;
	}
	public Date getTempodateechance() {
		return tempodateechance;
	}

	public void setTempodateechance(Date tempodateechance) {
		this.tempodateechance = tempodateechance;
	}

	public Date getTempodateechancebc() {
		return tempodateechancebc;
	}

	public void setTempodateechancebc(Date tempodateechancebc) {
		this.tempodateechancebc = tempodateechancebc;
	}

	public List<Facture> getFacturesforbc() {
		if(bc!=null)
			facturesforbc=managerjdbc.getNumfactForBC(bc);
		return facturesforbc;
	}

	public void setFacturesforbc(List<Facture> facturesforbc) {
		this.facturesforbc = facturesforbc;
	}

	public BonCommande getBc() {
		return bc;
	}

	public void setBc(BonCommande bc) {
		this.bc = bc;
	}

	public String getTempocontsat() {
		return tempocontsat;
	}

	public void setTempocontsat(String tempocontsat) {
		this.tempocontsat = tempocontsat;
	}

	public String getTempocontsatbc() {
		return tempocontsatbc;
	}

	public void setTempocontsatbc(String tempocontsatbc) {
		this.tempocontsatbc = tempocontsatbc;
	}

	public Date getTempodateechancep() {
		return tempodateechancep;
	}

	public void setTempodateechancep(Date tempodateechancep) {
		this.tempodateechancep = tempodateechancep;
	}

	public Date getTempodatereglement() {
		return tempodatereglement;
	}

	public void setTempodatereglement(Date tempodatereglement) {
		this.tempodatereglement = tempodatereglement;
	}

	public SimpleDateFormat getFt() {
		return ft;
	}

	public void setFt(SimpleDateFormat ft) {
		this.ft = ft;
	}

	public Date getTempodateconstater() {
		return tempodateconstater;
	}

	public void setTempodateconstater(Date tempodateconstater) {
		this.tempodateconstater = tempodateconstater;
	}

	public ModelService<FactureFournisseur> getManagerFactFournisseur() {
		return managerFactFournisseur;
	}

	public void setManagerFactFournisseur(
			ModelService<FactureFournisseur> managerFactFournisseur) {
		this.managerFactFournisseur = managerFactFournisseur;
	}

	public List<FactureFournisseur> getFacturesfournisseurs() {
		facturesfournisseurs=managerApplication.getFacturesfournisseur();
		return facturesfournisseurs;
	}

	public void setFacturesfournisseurs(
			List<FactureFournisseur> facturesfournisseurs) {
		this.facturesfournisseurs = facturesfournisseurs;
	}

	public FactureFournisseur getFacturefournisseur() {
		return facturefournisseur;
	}

	public void setFacturefournisseur(FactureFournisseur facturefournisseur) {
		this.facturefournisseur = facturefournisseur;
	}
	

	

}
