package ims.model.entities;

import java.util.Date;
import java.util.Set;

public class Stock {
	
	private int id;
	private Article article;
	private String numserie;
	private float qteinital;
	private float qte;
	private Client client;
	private Client clientFinal;
	private Fournisseur fournisseur;
	private Set<Stock_Livraison> livraisons;
	private String articlenumserie;
	private Date datestock;
	private Date datelivrer;
	
	
	public Stock() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}

	public Set<Stock_Livraison> getLivraisons() {
		return livraisons;
	}
	public void setLivraisons(Set<Stock_Livraison> livraisons) {
		this.livraisons = livraisons;
	}
	public Fournisseur getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}
	public float getQte() {
		return qte;
	}
	public void setQte(float qte) {
		this.qte = qte;
	}
	public String getNumserie() {
		return numserie;
	}
	public void setNumserie(String numserie) {
		this.numserie = numserie;
	}
	public String getArticlenumserie() {
		articlenumserie=article.getRef()+"-"+numserie;
		if(articlenumserie.length()>100){
			char[] charchaine=articlenumserie.toCharArray();
			articlenumserie="";
			for(int i=0;i<100;i++)
				articlenumserie+=charchaine[i];
			articlenumserie+="...";
		}
		return articlenumserie;
	}
	public void setArticlenumserie(String articlenumserie) {
		this.articlenumserie = articlenumserie;
	}
	public Date getDatestock() {
		return datestock;
	}
	public void setDatestock(Date datestock) {
		this.datestock = datestock;
	}
	public Date getDatelivrer() {
		return datelivrer;
	}
	public void setDatelivrer(Date datelivrer) {
		this.datelivrer = datelivrer;
	}
	public Client getClientFinal() {
		return clientFinal;
	}
	public void setClientFinal(Client clientFinal) {
		this.clientFinal = clientFinal;
	}
	public float getQteinital() {
		return qteinital;
	}
	public void setQteinital(float qteinital) {
		this.qteinital = qteinital;
	}
	

}
