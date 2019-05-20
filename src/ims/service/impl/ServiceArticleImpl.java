/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Article;
import ims.service.ModelService;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rabeh
 */
@Transactional(readOnly = true)
public class ServiceArticleImpl implements ModelService<Article>,Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Creates a new instance of ServiceArticleImpl
     */
  
    @ManagedProperty(value="#{ArticleHibernate}")
    ModelDao<Article> modeldao;
     
    public ServiceArticleImpl() {
    }

     @Override
    public boolean insertObject(Article object) {
       return modeldao.insert(object);
    }

    @Override
    public boolean updateObject(Article object) {
    	System.out.println("service");
       return modeldao.update(object);
    }

    @Override
    public boolean deleteObject(Article object) {
       return modeldao.delete(object);
    }

    @Override
    public boolean deleteObject(int id) {
       return modeldao.delete(id);
    }

    @Override
    public List<Article> getObject() {
       return modeldao.get();
    }

    @Override
    public Article getObject(int id) {
        return modeldao.get(id);
    }

    public ModelDao<Article> getModeldao() {
        return modeldao;
    }

    public void setModeldao(ModelDao<Article> modeldao) {
        this.modeldao = modeldao;
    }

    @Override
    public List<Article> getObjects(int id) {
    	return modeldao.getbyObjet(id);
    }

	@Override
	public Article getByName(String name) {
		return this.modeldao.getByName(name);
	}

	@Override
	public Article getByID(int id) {
		return modeldao.get(id);
	}

	@Override
	public List<Article> getByNames(String... name) {
		// TODO Auto-generated method stub
		return modeldao.getByNames(name);
	}

	@Override
	public Article getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
