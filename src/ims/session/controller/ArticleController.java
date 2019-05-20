/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.session.controller;


import ims.model.entities.Article;
import ims.model.entities.CategorieArticle;
import ims.model.entities.Produit;
import ims.model.entities.TypeArticle;
import ims.service.ModelService;
import ims.service.ModelServiceJDBC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author rabeh
 */

@ManagedBean
@SessionScoped
public class ArticleController implements Serializable{

    /**
	* creator RABEH TARIK
    **/
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{CategorieArticleManager}")
	private ModelService<CategorieArticle> managerC;
	
	@ManagedProperty(value="#{TypeArticleManager}")
	private ModelService<TypeArticle> managertype;
	
	@ManagedProperty(value="#{ProduitManager}")
	private ModelService<Produit> managerProduit;
	
	@ManagedProperty(value="#{ArticleManager}")
    private ModelService<Article> manager;
	
	@ManagedProperty(value="#{JDBCManager}")
    private ModelServiceJDBC managerjdbc;
	
	 @ManagedProperty(value = "#{managerDataBase}")
	 private ManagerDataBase managerApplication;
	 
	 
	public ManagerDataBase getManagerApplication() {
		return managerApplication;
	}

	public void setManagerApplication(ManagerDataBase managerApplication) {
		this.managerApplication = managerApplication;
	}
	
	private int idtypearticle=0;
	private int idcatarticle=0;
	private int idproduit;
    private String ref;
    private String des;
    
	private Article article;
    private List<Article> articles;
    private List<Article> filteredarticle=new ArrayList<Article>();
	private List<CategorieArticle> CategorieArticles=new ArrayList<CategorieArticle>();

	private ArrayList<String> filtredProduit;

	private List<Produit> listProduit;

	private ArrayList<String> filtredTypeArticle;

	private List<TypeArticle> listTypeArticle;

   private String str_article;
    
    public ArticleController() {
		super();
	}

	@PostConstruct
    public void init(){
		try {
			System.out.println("----------INIT ARTILCE--------------");
			article.setTypearticle(managertype.getObject(1));
			articles=managerApplication.getArticles();
			System.out.println("----------FIN INIT ARTILCE--------------");
		} catch (Exception e) {
		}
    	
    }
	public void actualiser(){
		managerApplication.setArticles(null);
	}
	
	public List<String> completerProduit(String query){
		try {
	    	filtredProduit=new ArrayList<String>();
	    	try {
				listProduit=managerProduit.getObject();
				
				 for (int i = 0; i < listProduit.size(); i++) {
			            Produit skin = listProduit.get(i);
			            if(skin.getTypeproduit().toLowerCase().startsWith(query) && skin.getTypeproduit()!=null) {
			            	filtredProduit.add(skin.getTypeproduit());
			            }
			        }
				
			} catch (Exception e) {
				e.getStackTrace();
				filtredProduit=null;
			}
	    	return filtredProduit;
			} catch (Exception e) {
				return null;
			}
	}
	public List<String> completerTypeArticle(String query){
		try {
	    	filtredTypeArticle=new ArrayList<String>();
	    	try {
				listTypeArticle=managertype.getObject();
				
				 for (int i = 0; i < listTypeArticle.size(); i++) {
			            TypeArticle skin = listTypeArticle.get(i);
			            if(skin.getType().toLowerCase().startsWith(query) && skin.getType()!=null) {
			            	filtredTypeArticle.add(skin.getType());
			            }
			        }
				
			} catch (Exception e) {
				e.getStackTrace();
				filtredTypeArticle=null;
			}
	    	return filtredTypeArticle;
			} catch (Exception e) {
				return null;
			}
	}
    
    public String preparecreer(){
    	try {
        	article=new Article();
        	article.setTypearticle(managertype.getObject(1));
        	return "insert?faces-redirect=true";
		} catch (Exception e) {
			return "index?faces-redirect=true";
		}
    	
    }
	
	public int getIdtypearticle() {
		return idtypearticle;
	}

	public void setIdtypearticle(int idtypearticle) {
		this.idtypearticle = idtypearticle;
	}


	public int getIdcatarticle() {
		return idcatarticle;
	}


	public void setIdcatarticle(int idcatarticle) {
		this.idcatarticle = idcatarticle;
	}


	public ModelService<TypeArticle> getManagertype() {
		return managertype;
	}


	public void setManagertype(ModelService<TypeArticle> managertype) {
		this.managertype = managertype;
	}
    
    
	public ModelService<CategorieArticle> getManagerC() {
		return managerC;
	}

	public void setManagerC(ModelService<CategorieArticle> managerC) {
		this.managerC = managerC;
	}
	
	public List<CategorieArticle> getCategorieArticles() {
		CategorieArticles=managerC.getObject();
		return CategorieArticles;
	}

	public void setCategorieArticles(List<CategorieArticle> CategorieArticles) {
		this.CategorieArticles = CategorieArticles;
	}
    
    public void insert(){
        FacesMessage msg;
        try {
        	article=new Article();
        	managerApplication.setArticles(null);
        	int testinsert=managerjdbc.getExisteArticle(ref);
        	System.out.println(testinsert);
        	if(testinsert<=0){
        		article.setTypearticle(managertype.getObject(idtypearticle));
        		article.setTypeproduit(managerProduit.getObject(idproduit));
        		article.setRef(ref);
        		article.setDesignation(des);
        		manager.insertObject(article);
        		vider();
        		msg = new FacesMessage("Article est créé avec success");
        		FacesContext.getCurrentInstance().addMessage(null, msg);
        		ref="";
        		des="";
        		idtypearticle=0;
        		idproduit=0;
        		idcatarticle=0;
        		article=new Article();
        	}else{
        		msg = new FacesMessage("reference Article est existe deja");
        	       FacesContext.getCurrentInstance().addMessage(null, msg);
        	}
        } catch (Exception e) {
        	msg = new FacesMessage("Exception" + e.getLocalizedMessage(),"Actualisée la page");
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void annuler(){
        FacesMessage msg;
        try {
        		vider();
          		ref="";
        		des="";
        		article=new Article();
        		idcatarticle=0;
        		idproduit=0;
        		idtypearticle=0;
        		msg = new FacesMessage("Article annuler avec success");
        		FacesContext.getCurrentInstance().addMessage(null, msg);
  
        	
        } catch (Exception e) {
                       msg = new FacesMessage("Exception");
                       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void update(){
    	   FacesMessage msg;
           try {
        	   		managerApplication.setArticles(null);
            		article.setTypearticle(managertype.getByName(article.getTypearticle().getType()));
            		article.setTypeproduit(managerProduit.getByName(article.getTypeproduit().getTypeproduit()));
            		
            		Article upartile=article;
            		managerjdbc.updateArticle(upartile);     
           		msg = new FacesMessage("Article est Modifier avec success");
              FacesContext.getCurrentInstance().addMessage(null, msg);
           } catch (Exception e) {
                          msg = new FacesMessage("Article est  exisite deja contacter Administration");
                          FacesContext.getCurrentInstance().addMessage(null, msg);
           }
    }
    

    public void Delete(){
        FacesMessage msg;
        try {
        	
        boolean op=managerjdbc.deleteArticle(article);
        managerApplication.setArticles(null);
        if(op==true){
            msg = new FacesMessage("Article est Supprimer avec success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }else{
            msg = new FacesMessage("Article n'est pas supprimer","Article déja liée");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        } catch (Exception e) {
            msg = new FacesMessage("Exception" +e.getMessage());
       FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
    public String prepareView(){
    	article=manager.getObject(article.getIdArticle());
    	idproduit=article.getTypeproduit().getId();
    	idtypearticle=article.getTypearticle().getIdtype();
       	return "Viewarticle?faces-redirect=true";
       } 

    public ModelService<Article> getManager() {
        return manager;
    }

    public void setManager(ModelService<Article> manager) {
        this.manager = manager;
    }
    
     @SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean filterByPrice(Object value, Object filter, Locale locale) {
        try {
        	String filterText = (filter == null) ? null : filter.toString().trim();
            if(filterText == null||filterText.equals("")) {
                return true;
            }
             
            if(value == null) {
                return false;
            }
             
            return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
		} catch (Exception e) {
			return false;
		}

    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<Article> getArticles() {
    	articles=managerApplication.getArticles();
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Article> getFilteredarticle() {
        return filteredarticle;
    }

    public void setFilteredarticle(List<Article> filteredarticle) {
        this.filteredarticle = filteredarticle;
    }

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
    
	private void vider(){
		des="";
		ref="";
	}

	public ModelServiceJDBC getManagerjdbc() {
		return managerjdbc;
	}

	public void setManagerjdbc(ModelServiceJDBC managerjdbc) {
		this.managerjdbc = managerjdbc;
	}

	public ModelService<Produit> getManagerProduit() {
		return managerProduit;
	}

	public void setManagerProduit(ModelService<Produit> managerProduit) {
		this.managerProduit = managerProduit;
	}

	public int getIdproduit() {
		return idproduit;
	}

	public void setIdproduit(int idproduit) {
		this.idproduit = idproduit;
	}

	public ArrayList<String> getFiltredProduit() {
		return filtredProduit;
	}

	public void setFiltredProduit(ArrayList<String> filtredProduit) {
		this.filtredProduit = filtredProduit;
	}

	public List<Produit> getListProduit() {
		return listProduit;
	}

	public void setListProduit(List<Produit> listProduit) {
		this.listProduit = listProduit;
	}

	public ArrayList<String> getFiltredTypeArticle() {
		return filtredTypeArticle;
	}

	public void setFiltredTypeArticle(ArrayList<String> filtredTypeArticle) {
		this.filtredTypeArticle = filtredTypeArticle;
	}

	public List<TypeArticle> getListTypeArticle() {
		return listTypeArticle;
	}

	public void setListTypeArticle(List<TypeArticle> listTypeArticle) {
		this.listTypeArticle = listTypeArticle;
	}

	public String getStr_article() {
		str_article="Articles";
		return str_article;
	}

	public void setStr_article(String str_article) {
		this.str_article = str_article;
	}
	
	
}
