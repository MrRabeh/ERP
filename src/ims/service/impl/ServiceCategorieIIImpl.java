/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.service.impl;
import ims.model.dao.ModelDao;
import ims.model.entities.*;
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
public class ServiceCategorieIIImpl implements ModelService<CategorieIISociete>,Serializable{
   
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{CategorieIIHibernate}")
    ModelDao<CategorieIISociete> modeldao;

    @Override
    public List<CategorieIISociete> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean insertObject(CategorieIISociete object) {
        
    return modeldao.insert(object);
    }

    @Override
    public boolean updateObject(CategorieIISociete object) {
    return modeldao.update(object);
    
    }

    @Override
    public boolean deleteObject(CategorieIISociete object) {
    return modeldao.delete(object);
    }

    @Override
    public boolean deleteObject(int id) {
    return modeldao.delete(id);
    }

    @Override
    public List<CategorieIISociete> getObject() {
    return modeldao.get();
    }

    @Override
    public CategorieIISociete getObject(int id) {
    return (CategorieIISociete)modeldao.get(id);
    }

    public ModelDao<CategorieIISociete> getModeldao() {
        return modeldao;
    }

    public void setModeldao(ModelDao<CategorieIISociete> modeldao) {
        this.modeldao = modeldao;
    }

	@Override
	public CategorieIISociete getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategorieIISociete getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategorieIISociete> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategorieIISociete getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
  
}
