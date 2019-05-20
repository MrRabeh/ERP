/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.TypeArticle;
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
public class ServiceTypeArticle implements ModelService<TypeArticle>,Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{TypeArticleHibernate}")
    ModelDao<TypeArticle> modeldao;
    
    @Override
    public boolean insertObject(TypeArticle object) {
        return modeldao.insert(object);
    }

    @Override
    public boolean updateObject(TypeArticle object) {
      return modeldao.update(object);
    }

    @Override
    public boolean deleteObject(TypeArticle object) {
      return modeldao.delete(object);
    }

    @Override
    public boolean deleteObject(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TypeArticle> getObject() {
     return modeldao.get();
    }

    @Override
    public TypeArticle getObject(int id) {
        return (TypeArticle)modeldao.get(id);
    }

    public ModelDao<TypeArticle> getModeldao() {
        return modeldao;
    }

    public void setModeldao(ModelDao<TypeArticle> modeldao) {
        this.modeldao = modeldao;
    }

    @Override
    public List<TypeArticle> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public TypeArticle getByName(String name) {
		// TODO Auto-generated method stub
		return modeldao.getByName(name);
	}

	@Override
	public TypeArticle getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeArticle> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeArticle getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
  
    
}

