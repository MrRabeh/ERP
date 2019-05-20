/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Departement;
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
public class ServiceDepartementImpl implements ModelService<Departement> ,Serializable{

     
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{DepartementHibernate}")
    ModelDao<Departement> modeldao;
     
    @Override
    public boolean insertObject(Departement object) {
       return modeldao.insert(object);
    }

    @Override
    public boolean updateObject(Departement object) {
        return modeldao.update(object);
    
    }

    @Override
    public boolean deleteObject(Departement object) {
       return modeldao.delete(object);
    }

    @Override
    public boolean deleteObject(int id) {
       return modeldao.delete(id);
    }

    @Override
    public List<Departement> getObject() {
        return modeldao.get();
    }

    @Override
    public Departement getObject(int id) {
        return (Departement)modeldao.get(id);
    }

    public ModelDao<Departement> getModeldao() {
        return modeldao;
    }

    public void setModeldao(ModelDao<Departement> modeldao) {
        this.modeldao = modeldao;
    }

    @Override
    public List<Departement> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public Departement getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Departement getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Departement> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Departement getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

    
}
