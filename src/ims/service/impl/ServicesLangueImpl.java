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
@Transactional
public class ServicesLangueImpl implements ModelService<Langue>,Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{LangueHibernate}")
    ModelDao<Langue> modeldao;
    
    @Override
    public boolean insertObject(Langue object) {
        
    return modeldao.insert(object);
    }

    @Override
    public boolean updateObject(Langue object) {
    return modeldao.update(object);
    
    }

    @Override
    public boolean deleteObject(Langue object) {
    return modeldao.delete(object);
    }

    @Override
    public boolean deleteObject(int id) {
    return modeldao.delete(id);
    }

    @Override
    public List<Langue> getObject() {
    return modeldao.get();
    }

    @Override
    public Langue getObject(int id) {
    return (Langue)modeldao.get(id);
    }

    public ModelDao<Langue> getModeldao() {
        return modeldao;
    }

    public void setModeldao(ModelDao<Langue> modeldao) {
        this.modeldao = modeldao;
    }

    @Override
    public List<Langue> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public Langue getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Langue getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Langue> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Langue getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

    
   
    
}
