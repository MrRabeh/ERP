/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Pointage;
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
public class ServicePointageImpl implements ModelService<Pointage>,Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{PointageHibernate}")
    ModelDao<Pointage> daopointage;

    public ServicePointageImpl() {
    }
    
    
    
    @Override
    public boolean insertObject(Pointage pointage) {
    	System.out.println("SERVICE OK");
        return daopointage.insert(pointage);
    }

    @Override
    public boolean updateObject(Pointage pointage) {
       return daopointage.update(pointage);
    }

    @Override
    public List<Pointage> getObject() {
        return daopointage.get();
    }

    @Override
    public Pointage getObject(int id) {
       return (Pointage)daopointage.get(id);
    }

    public ModelDao<Pointage> getDaopointage() {
        return daopointage;
    }

    public void setDaopointage(ModelDao<Pointage> daopointage) {
        this.daopointage = daopointage;
    }

    @Override
    public boolean deleteObject(int id) {
        return daopointage.delete(id);
    }
    @Override
    public boolean deleteObject(Pointage pointage) {
        return daopointage.delete(pointage);
    }

    @Override
    public List<Pointage> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



	@Override
	public Pointage getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Pointage getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Pointage> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Pointage getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}


 
    
    
}
