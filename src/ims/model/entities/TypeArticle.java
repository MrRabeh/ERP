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
public class TypeArticle implements java.io.Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idtype;
    private String type="";
    private Set<Article> articles=new HashSet<Article>();
    public TypeArticle() {
    }

    public int getIdtype() {
        return idtype;
    }

    public void setIdtype(int idtype) {
        this.idtype = idtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }
    
}
