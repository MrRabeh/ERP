/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Offre;
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
public class ServiceOffreImpl implements ModelService<Offre>,Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{OffreHibernate}")
    ModelDao<Offre> modeldao;
    
    @Override
    public boolean insertObject(Offre object) {
       return modeldao.insert(object);
    }

    @Override
    public boolean updateObject(Offre object) {
       return modeldao.update(object);
    }

    @Override
    public boolean deleteObject(Offre object) {
       return modeldao.delete(object);
    }

    @Override
    public boolean deleteObject(int id) {
       return modeldao.delete(id);
    }

    @Override
    public List<Offre> getObject() {
       return modeldao.get();
    }

    @Override
    public Offre getObject(int id) {
        return modeldao.get(id);
    }

    public ModelDao<Offre> getModeldao() {
        return modeldao;
    }

    public void setModeldao(ModelDao<Offre> modeldao) {
        this.modeldao = modeldao;
    }

    @Override
    public List<Offre> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public Offre getByName(String name) {
		return modeldao.getByName(name);
	}

	@Override
	public Offre getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Offre> getByNames(String... name) {
		return modeldao.getByNames(name);
	}

	@Override
	public Offre getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
