package ims.model.entities;

import java.io.Serializable;
import java.util.Set;

public class Ville implements Serializable{

	private static final long serialVersionUID = 1L;
	private int idville;
	private String ville;
	private Pays pays;
	private Set<Fournisseur> fournisseurs;
	private Set<Client> clients;
	private Set<Contact> contacts;
	
	 public Ville() {
	    }
	 
	 public int getIdville() {
	        return idville;
	    }

	    public void setIdville(int idville) {
	        this.idville = idville;
	    }

	    public String getVille() {
	        return ville;
	    }

	    public void setVille(String ville) {
	        this.ville = ville;
	    }
	    
	    public Pays getPays() {
	        return pays;
	    }

	    public void setPays(Pays pays) {
	        this.pays = pays;
	    }

		public Set<Fournisseur> getFournisseurs() {
			return fournisseurs;
		}

		public void setFournisseurs(Set<Fournisseur> fournisseurs) {
			this.fournisseurs = fournisseurs;
		}

		public Set<Client> getClients() {
			return clients;
		}

		public void setClients(Set<Client> clients) {
			this.clients = clients;
		}

		public Set<Contact> getContacts() {
			return contacts;
		}

		public void setContacts(Set<Contact> contacts) {
			this.contacts = contacts;
		}
	    
	    
	    
	}
