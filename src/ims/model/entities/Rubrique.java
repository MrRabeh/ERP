package ims.model.entities;

import java.io.Serializable;
import java.util.Set;

public class Rubrique implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int classement;
	private String ref;
	private String description;
	private Set<Offre_Article> offreArticle;
	
	public Rubrique() {
		super();
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
	public Set<Offre_Article> getOffreArticle() {
		return offreArticle;
	}
	public void setOffreArticle(Set<Offre_Article> offreArticle) {
		this.offreArticle = offreArticle;
	}
	public int getClassement() {
		return classement;
	}
	public void setClassement(int classement) {
		this.classement = classement;
	}
	
	
	
	
	

}
