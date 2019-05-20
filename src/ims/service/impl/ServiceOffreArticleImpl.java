/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Offre_Article;
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
public class ServiceOffreArticleImpl implements ModelService<Offre_Article>,Serializable {

    
    /**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{OffreArticleHibernate}")
    ModelDao<Offre_Article> modeldao;
      
    @Override
    public boolean insertObject(Offre_Article object) {
       return modeldao.insert(object);
    }

    @Override
    public List<Offre_Article> getObjects(int id) {
      
        return modeldao.getbyObjet(id);
    }

    @Override
    public boolean updateObject(Offre_Article object) {
      return modeldao.update(object);
    }

    @Override
    public boolean deleteObject(Offre_Article object) {
      return modeldao.delete(object);
    }

    @Override
    public boolean deleteObject(int id) {
      return modeldao.delete(id);
    }

    @Override
    public List<Offre_Article> getObject() {
       return modeldao.get();
    }

    @Override
    public Offre_Article getObject(int id) {
        return (Offre_Article)modeldao.get(id);
    }

    public ModelDao<Offre_Article> getModeldao() {
        return modeldao;
    }

    public void setModeldao(ModelDao<Offre_Article> modeldao) {
        this.modeldao = modeldao;
    }

	@Override
	public Offre_Article getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Offre_Article getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Offre_Article> getByNames(String... name) {
		return modeldao.getByNames(name);
	}

	@Override
	public Offre_Article getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
