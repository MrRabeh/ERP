/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.entities;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author rabeh
 */
public class Fournisseur implements java.io.Serializable{
    
    /**
	 * 
	 */
		private static final long serialVersionUID = 1L;
		private int idfournisseur;
	    private String nomsociete;
	    private String adresse;
	    private String tel;
	    private Pays pays=new Pays();
	    private Ville ville=new Ville();
	    private Set<BonCommande> commandes;
	    private Set<Stock> stocks=new HashSet<Stock>();
	    private int nombrefacturefournisseur;

			public Fournisseur() {
				super();
				// TODO Auto-generated constructor stub
			}
			
			public int getIdfournisseur() {
				return idfournisseur;
			}
			
			public void setIdfournisseur(int idfournisseur) {
				this.idfournisseur = idfournisseur;
			}
			
			public String getNomsociete() {
				return nomsociete;
			}
			
			public void setNomsociete(String nomsociete) {
				this.nomsociete = nomsociete;
			}
			
			public String getAdresse() {
				return adresse;
			}
			
			public void setAdresse(String adresse) {
				this.adresse = adresse;
			}
			
			public Pays getPays() {
				return pays;
			}
			
			public void setPays(Pays pays) {
				this.pays = pays;
			}
			
			public Ville getVille() {
				return ville;
			}
			
			public void setVille(Ville ville) {
				this.ville = ville;
			}
			
			public Set<BonCommande> getCommandes() {
				return commandes;
			}
			
			public void setCommandes(Set<BonCommande> commandes) {
				this.commandes = commandes;
			}
			
			public static long getSerialversionuid() {
				return serialVersionUID;
			}

			public Set<Stock> getStocks() {
				return stocks;
			}

			public void setStocks(Set<Stock> stocks) {
				this.stocks = stocks;
			}

			public int getNombrefacturefournisseur() {
				return nombrefacturefournisseur;
			}

			public void setNombrefacturefournisseur(int nombrefacturefournisseur) {
				this.nombrefacturefournisseur = nombrefacturefournisseur;
			}

			public String getTel() {
				return tel;
			}

			public void setTel(String tel) {
				this.tel = tel;
			}

			    

}
