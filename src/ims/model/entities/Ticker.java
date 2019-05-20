package ims.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Ticker implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String ref;
	private String description;
	private Date dateticker;
	private Date dateresolution;
	private double duree;
	private String statut;
	private boolean fermerticker;
	private String remarque;
	private Utilisateur user;
	private UtilisateurHelpDesk userhelpdesk;
	private TypeTicker typeticker;
	private TypePriorite priorite;
	private Set<SuiviTicker> listesuivi;
	
	
	public Ticker() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateticker() {
		return dateticker;
	}

	public void setDateticker(Date dateticker) {
		this.dateticker = dateticker;
	}

	public Date getDateresolution() {
		return dateresolution;
	}

	public void setDateresolution(Date dateresolution) {
		this.dateresolution = dateresolution;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String getRemarque() {
		return remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	public Set<SuiviTicker> getListesuivi() {
		return listesuivi;
	}

	public void setListesuivi(Set<SuiviTicker> listesuivi) {
		this.listesuivi = listesuivi;
	}

	public TypeTicker getTypeticker() {
		return typeticker;
	}

	public void setTypeticker(TypeTicker typeticker) {
		this.typeticker = typeticker;
	}

	public double getDuree() {
		return duree;
	}

	public void setDuree(double duree) {
		this.duree = duree;
	}

	public TypePriorite getPriorite() {
		return priorite;
	}

	public void setPriorite(TypePriorite priorite) {
		this.priorite = priorite;
	}

	public UtilisateurHelpDesk getUserhelpdesk() {
		return userhelpdesk;
	}

	public void setUserhelpdesk(UtilisateurHelpDesk userhelpdesk) {
		this.userhelpdesk = userhelpdesk;
	}

	public boolean isFermerticker() {
		return fermerticker;
	}

	public void setFermerticker(boolean fermerticker) {
		this.fermerticker = fermerticker;
	}

	
	
}
