package ims.session.controller;

import ims.model.entities.BonLivraison;
import ims.model.entities.Facture;
import ims.model.entities.Facture_Livraison;
import ims.model.entities.ListeBoncommandeFactureOffre;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@SessionScoped
public class FactureBonLivraisonController implements Serializable {

	   /**
		* creator RABEH TARIK
	    **/
	private static final long serialVersionUID = 1L;
	
	private String idfacture="";
	private String idbonlivraison="";
	private List<Facture_Livraison> fact_livs=new ArrayList<Facture_Livraison>();
	private Facture_Livraison fact_liv=new Facture_Livraison();
	
	@ManagedProperty(value="#{FactureBonLivraisonManager}")
	private ModelService<Facture_Livraison> managerFL;
	
	@ManagedProperty(value="#{FactureManager}")
	private ModelService<Facture> managerfact;
	
	@ManagedProperty(value="#{BonLivraisonManager}")
	private ModelService<BonLivraison> managerbl;
	
    @ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
    
	private List<ListeBoncommandeFactureOffre> listes;
	private List<ListeBoncommandeFactureOffre> filtredlistes=new ArrayList<ListeBoncommandeFactureOffre>();
	private ListeBoncommandeFactureOffre liste=new ListeBoncommandeFactureOffre();
	
	public FactureBonLivraisonController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init(){
		System.out.println("***init****");
	}
	
	@PreDestroy
	public void destroy(){
		System.out.print("*****destroy*****");
	}
	
	public String prepareList(){
		System.out.println("Facture_livraison?faces-redirect=true");
		return "Facture_livraison?faces-redirect=true";
	}
	
	public void actualiser(){
		listes=null;
	}
	
	public String prepareCreate(){
		System.out.println("Facture_livraison Create");
		fact_liv=new Facture_Livraison();
		return "InsertFactureLivraison?faces-redirect=true";
	}
	
	public void insert(){
		  FacesMessage msg;
	        try {
	        	if(!idfacture.equals("") && !idbonlivraison.equals("")){
	        		listes=null;
		        	fact_liv=new Facture_Livraison();
		        	fact_liv.setFacture(managerfact.getObject(Integer.parseInt(idfacture)));
		        	fact_liv.setLivraison(managerbl.getObject(Integer.parseInt(idbonlivraison)));
		        	managerFL.insertObject(fact_liv);
		        	msg = new FacesMessage("Creer avec Success");
	    	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    	        idbonlivraison="0";
	    	        idfacture="0";
	        	}else{
	        		msg = new FacesMessage("Sélectionner les Selections");
	    	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        	}
	        		
	        	
	        } catch (Exception e) {
	          msg = new FacesMessage("Exception Application"+e.getMessage(),"Contacter l'Administrateur");
	          FacesContext.getCurrentInstance().addMessage(null, msg);
	        	}
	}
	
	public void annuler(){
		 FacesMessage msg;
		 try {
			 idbonlivraison="0";
			 idfacture="0";
	        	msg = new FacesMessage("Annuler Avec Success");
    	        FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
		    msg = new FacesMessage("Exception Application"+e.getMessage(),"Contacter l'Administrateur");
	          FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void Delete(){
		  FacesMessage msg;
	        try {
	        	System.out.println("ID=="+fact_liv.getId());
	        	int op=managerjdbc.deleteFacture_Livraison(fact_liv.getId());
	        	if(op==1){
	        		msg = new FacesMessage("Suppression est Terminier avec Success");
	        		FacesContext.getCurrentInstance().addMessage(null, msg);
	        	}else{
	        		msg = new FacesMessage("Probleme de suppression");
	        		FacesContext.getCurrentInstance().addMessage(null, msg);
	        	}

	        } catch (Exception e) {
	          msg = new FacesMessage("Exception Application"+e.getMessage(),"Contacter l'Administrateur");
	          FacesContext.getCurrentInstance().addMessage(null, msg);
	        	}
	}
	
	
	public String getIdfacture() {
		return idfacture;
	}
	public void setIdfacture(String idfacture) {
		this.idfacture = idfacture;
	}
	public String getIdbonlivraison() {
		return idbonlivraison;
	}
	public void setIdbonlivraison(String idbonlivraison) {
		this.idbonlivraison = idbonlivraison;
	}


	public List<Facture_Livraison> getFact_livs() {
	    fact_livs=managerFL.getObject();
		return fact_livs;
	}


	public void setFact_livs(List<Facture_Livraison> fact_livs) {
		this.fact_livs = fact_livs;
	}


	public Facture_Livraison getFact_liv() {
		return fact_liv;
	}


	public void setFact_liv(Facture_Livraison fact_liv) {
		this.fact_liv = fact_liv;
	}


	public ModelService<Facture_Livraison> getManagerFL() {
		return managerFL;
	}


	public void setManagerFL(ModelService<Facture_Livraison> managerFL) {
		this.managerFL = managerFL;
	}


	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}


	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public ModelService<Facture> getManagerfact() {
		return managerfact;
	}

	public void setManagerfact(ModelService<Facture> managerfact) {
		this.managerfact = managerfact;
	}

	public ModelService<BonLivraison> getManagerbl() {
		return managerbl;
	}

	public void setManagerbl(ModelService<BonLivraison> managerbl) {
		this.managerbl = managerbl;
	}
	
	public boolean filterByPrice(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if(filterText == null||filterText.equals("")) {
            return true;
        }
         
        if(value == null) {
            return false;
        }
         
        return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
    }

	public List<ListeBoncommandeFactureOffre> getListes() {
			listes=managerjdbc.getListeDetailFacture();
		return listes;
	}

	public void setListes(List<ListeBoncommandeFactureOffre> listes) {
		this.listes = listes;
	}

	public List<ListeBoncommandeFactureOffre> getFiltredlistes() {
		return filtredlistes;
	}

	public void setFiltredlistes(List<ListeBoncommandeFactureOffre> filtredlistes) {
		this.filtredlistes = filtredlistes;
	}

	public ListeBoncommandeFactureOffre getListe() {
		return liste;
	}

	public void setListe(ListeBoncommandeFactureOffre liste) {
		this.liste = liste;
	}

	

	
}
