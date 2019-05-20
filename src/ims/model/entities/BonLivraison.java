package ims.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BonLivraison implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String ref;
	private Date datebl;
	private int Total;
	private String livrepar;
	private String receptionpar;
	private Facture facture=new Facture();
	private Set<Facture_Livraison> factures=new HashSet<Facture_Livraison>();
	private Set<Stock_Livraison> stocks=new HashSet<Stock_Livraison>();
	private Client client;
	private years years;
	private boolean activer;
	
	@Override
	public String toString() {
		return ref;
	}
	public BonLivraison(){}
	
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

	public Date getDatebl() {
		return datebl;
	}

	public void setDatebl(Date datebl) {
		this.datebl = datebl;
	}

	public String getLivrepar() {
		return livrepar;
	}

	public void setLivrepar(String livrepar) {
		this.livrepar = livrepar;
	}

	public String getReceptionpar() {
		return receptionpar;
	}

	public void setReceptionpar(String receptionpar) {
		this.receptionpar = receptionpar;
	}

	public int getTotal() {
		return Total;
	}

	public void setTotal(int total) {
		Total = total;
	}

	public Set<Stock_Livraison> getStocks() {
		return stocks;
	}

	public void setStocks(Set<Stock_Livraison> stocks) {
		this.stocks = stocks;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Set<Facture_Livraison> getFactures() {
		return factures;
	}

	public void setFactures(Set<Facture_Livraison> factures) {
		this.factures = factures;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public years getYears() {
		return years;
	}

	public void setYears(years years) {
		this.years = years;
	}

	public Facture getFacture() {
		Iterator<Facture_Livraison> it=factures.iterator();
		if(it.hasNext())
			facture=it.next().getFacture();
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
	}

	public boolean isActiver() {
		return activer;
	}

	public void setActiver(boolean activer) {
		this.activer = activer;
	}
	
	
	
}
