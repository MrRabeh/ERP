package ims.model.entities;

public class Boncommande_Article {
	
	
	private int id;
    private double qte;
    private double pu;
    private double pt;
    private String designation;
    private Article article;
    private BonCommande commande;
    
	public Boncommande_Article() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getQte() {
		return qte;
	}
	public void setQte(double qte) {
		this.qte = qte;
	}
	public double getPu() {
		return pu;
	}
	public void setPu(double pu) {
		this.pu = pu;
	}
	public double getPt() {
		return pt;
	}
	public void setPt(double pt) {
		this.pt = pt;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public BonCommande getCommande() {
		return commande;
	}
	public void setCommande(BonCommande commande) {
		this.commande = commande;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	
	
	
	
    
}
