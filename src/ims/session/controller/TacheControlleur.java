/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;
 
  
import ims.model.entities.Projet;
import ims.model.entities.Tache;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;




import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;



/**
 *
 * @author rabeh
 */
@ManagedBean
@RequestScoped
public class TacheControlleur implements Serializable{
 
	
	 /**
	* creator RABEH TARIK
    **/
	private static final long serialVersionUID = 1L;
 

	/**
     * Creates a new instance of TacheControlleur
     */
   
    @ManagedProperty(value="#{tacheManager}")
    private ModelService<Tache> manager;
     
    @ManagedProperty(value="#{projetManager}")
    private ModelService<Projet> managerprojet;
    
    @ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
    

	private Tache tache;
    private List<Tache> taches; 
    private List<Tache> filteredtaches; 
 
    

    private int idprojet=0;
	private String duree="";
	private String commentaire="";
	private String tachename;
	private Date datedebut;
	private Date datefin;
	
	private SimpleDateFormat ftheur= new SimpleDateFormat("HH:mm:ss");
	
	 
	
	 public void oncellEdit(){
	    	FacesMessage msg;
	    	taches=null;
	    	try {
	    		if(idprojet!=0)
	    			tache.setProjet(managerprojet.getObject(idprojet));
	    		
				 managerjdbc.updateTache(tache);
	    		 msg = new FacesMessage("Tache Modifier avec success");
		           FacesContext.getCurrentInstance().addMessage(null, msg);
		           idprojet=0;
			} catch (Exception e) {
				 msg = new FacesMessage("Probleme de connexion","Contacter l'administration "+e.getLocalizedMessage());
			       FacesContext.getCurrentInstance().addMessage(null, msg);
			}
	 }

	public void insert(){
	    	FacesMessage msg;
	    	taches=null;
	    	tache=new Tache();
	    	try{
	    		if(idprojet!=0)
	    			tache.setProjet(managerprojet.getObject(idprojet));
	    			
	    			tache.setTachename(tachename);
	    			tache.setDatedebut(datedebut);
	    			tache.setDatefin(datefin);
	    			tache.setHeurdebart(ftheur.format(datedebut));
	    			tache.setHeureFin(ftheur.format(datefin));
	    			tache.setCommentaire(commentaire);
	    			Long diff=datefin.getTime()-datedebut.getTime();
	    			float nombrejour=(diff / (1000*60*60*24));
	    			tache.setDuree(nombrejour+"");
	    			manager.insertObject(tache);
	           msg = new FacesMessage("tache créer avec success");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	       videforminsert();

	        } catch (Exception e) {
	               msg = new FacesMessage("Probleme d'insertion","contacter l'administration");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	       videforminsert();
	        } 
	    	tache=new Tache();
	    }
	 
	    public void Delete(){
	        FacesMessage msg;
	        taches=null;
	        try {
	        manager.deleteObject(tache);
	          this.getTaches();
	       msg = new FacesMessage("Tache Supprimer avec Success");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        } catch (Exception e) {
	            msg = new FacesMessage("ERORR de Suppression");
	       FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
	        
	    }
	    @SuppressWarnings({ "unchecked", "rawtypes" })
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
	     
		public ModelService<Tache> getManager() {
			return manager;
		}
		public void setManager(ModelService<Tache> manager) {
			this.manager = manager;
		}
		public Tache getTache() {
			return tache;
		}
		public void setTache(Tache tache) {
			this.tache = tache;
		}
		public List<Tache> getTaches() {
			if(taches==null)
				taches=manager.getObject();
			return taches;
		}
		public void setTaches(List<Tache> taches) {
			this.taches = taches;
		}	
		
		public List<Tache> getFilteredtaches() {
			return filteredtaches;
		}
		public void setFilteredtaches(List<Tache> filteredtaches) {
			this.filteredtaches = filteredtaches;
		}

		public ModelService<Projet> getManagerprojet() {
			return managerprojet;
		}

		public void setManagerprojet(ModelService<Projet> managerprojet) {
			this.managerprojet = managerprojet;
		}

		public int getIdprojet() {
			return idprojet;
		}

		public void setIdprojet(int idprojet) {
			this.idprojet = idprojet;
		}

		public String getDuree() {
			return duree;
		}

		public void setDuree(String duree) {
			this.duree = duree;
		}

		public String getTachename() {
			return tachename;
		}

		public void setTachename(String tachename) {
			this.tachename = tachename;
		}

		public Date getDatedebut() {
			return datedebut;
		}

		public void setDatedebut(Date datedebut) {
			this.datedebut = datedebut;
		}

		public Date getDatefin() {
			return datefin;
		}

		public void setDatefin(Date datefin) {
			this.datefin = datefin;
		}
		
		private void videforminsert(){
		       tachename="";
		       datefin=null;
		       datedebut=null;
		}

		public ModelServiceJDBC getManagerjdbc() {
			return managerjdbc;
		}

		public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
			this.managerjdbc = managerjdbc;
		}

		public SimpleDateFormat getFtheur() {
			return ftheur;
		}

		public void setFtheur(SimpleDateFormat ftheur) {
			this.ftheur = ftheur;
		}

		public String getCommentaire() {
			return commentaire;
		}

		public void setCommentaire(String commentaire) {
			this.commentaire = commentaire;
		}
		
		
}

