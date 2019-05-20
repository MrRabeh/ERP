/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.service.ModelService;
import ims.model.entities.CategorieArticle;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author rabeh
 */
@Transactional(readOnly = true)
public class ServiceCategorieArticle implements ModelService<CategorieArticle>,Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{CategorieArticleHibernate}")
    ModelDao<CategorieArticle> modeldao;
    
    @Override
    public boolean insertObject(CategorieArticle object) {
        return modeldao.insert(object);
    }

    @Override
    public boolean updateObject(CategorieArticle object) {
      return modeldao.update(object);
    }

    @Override
    public boolean deleteObject(CategorieArticle object) {
      return modeldao.delete(object);
    }

    @Override
    public boolean deleteObject(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CategorieArticle> getObject() {
     return modeldao.get();
    }

    @Override
    public CategorieArticle getObject(int id) {
      return (CategorieArticle)modeldao.get(id);
    }

    public ModelDao<CategorieArticle> getModeldao() {
        return modeldao;
    }

    public void setModeldao(ModelDao<CategorieArticle> modeldao) {
        this.modeldao = modeldao;
    }

    @Override
    public List<CategorieArticle> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public CategorieArticle getByName(String name) {
		return modeldao.getByName(name);
	}

	@Override
	public CategorieArticle getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategorieArticle> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategorieArticle getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
  
    
}
