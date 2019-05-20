package ims.model.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Pays implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idpays;
	private String cod_pays;
	private String pays;
    private Set<Ville> villes=new HashSet<Ville>();
	private Set<Fournisseur> fournisseurs;
	private Set<Client> clients;
	private Set<Contact> contacts;
	
	 public Pays() {
	    }
	 
	  public Pays(String pays) {
		this.pays=pays;
	}

	public int getIdpays() {
	        return idpays;
	    }

	    public void setIdpays(int idpays) {
	        this.idpays = idpays;
	    }
	 
	    public String getCod_pays() {
	        return cod_pays;
	    }

	    public void setCod_pays(String cod_pays) {
	        this.cod_pays = cod_pays;
	    }

	    public String getPays() {
	        return pays;
	    }

	    public void setPays(String pays) {
	        this.pays = pays;
	    }
	    
	    @Override
	    public int hashCode() {
	        int hash = 5;
	        hash = 59 * hash + this.idpays;
	        hash = 59 * hash + Objects.hashCode(this.pays);
	        hash = 59 * hash + Objects.hashCode(this.villes);
	        return hash;
	    }
	    
	    @Override
	    public boolean equals(Object obj) {
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final Pays other = (Pays) obj;
	        if (this.cod_pays != other.cod_pays) {
	            return false;
	        }
	        if (!Objects.equals(this.pays, other.pays)) {
	            return false;
	        }
	        if (!Objects.equals(this.villes, other.villes)) {
	            return false;
	        }
	        return true;
	    }

		public Set<Ville> getVilles() {
			return villes;
		}

		public void setVilles(Set<Ville> villes) {
			this.villes = villes;
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
