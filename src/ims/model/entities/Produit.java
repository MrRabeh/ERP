package ims.model.entities;

import java.util.HashSet;
import java.util.Set;

public class Produit {
	
	private int id;
	private String typeproduit;
	private Set<Article> articles=new HashSet<>();
	
	public Produit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeproduit() {
		return typeproduit;
	}
	public void setTypeproduit(String typeproduit) {
		this.typeproduit = typeproduit;
	}
	public Set<Article> getArticles() {
		return articles;
	}
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

}
