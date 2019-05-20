package ims.model.entities;

import java.io.Serializable;

public class AvoirFournisseur implements Serializable{

		private static final long serialVersionUID = 1L;
		private int id;
		private String numero_avoir;
		private Fournisseur fournisseur;
		private String dateavoir;
		private boolean activer;
		
		public AvoirFournisseur() {
			super();
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getNumero_avoir() {
			return numero_avoir;
		}
		public void setNumero_avoir(String numero_avoir) {
			this.numero_avoir = numero_avoir;
		}
		
		public Fournisseur getFournisseur() {
			return fournisseur;
		}
		public void setFournisseur(Fournisseur fournisseur) {
			this.fournisseur = fournisseur;
		}
		public String getDateavoir() {
			return dateavoir;
		}
		public void setDateavoir(String dateavoir) {
			this.dateavoir = dateavoir;
		}
		public boolean isActiver() {
			return activer;
		}
		public void setActiver(boolean activer) {
			this.activer = activer;
		}
		
		
	
	 }