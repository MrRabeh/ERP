/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Utilisateur;
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
public class ServiceUtilisateurImpl implements ModelService<Utilisateur>,Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{UserHibernate}")
    ModelDao<Utilisateur> daouser;

    public ServiceUtilisateurImpl() {
    }
    
    
    
    @Override
    public boolean insertObject(Utilisateur user) {
        return daouser.insert(user);
    }

    @Override
    public boolean updateObject(Utilisateur user) {
       return daouser.update(user);
    }

    @Override
    public List<Utilisateur> getObject() {
        return daouser.get();
    }

    @Override
    public Utilisateur getObject(int id) {
       return daouser.get(id);
    }

    public ModelDao<Utilisateur> getDaouser() {
        return daouser;
    }

    public void setDaouser(ModelDao<Utilisateur> daouser) {
        this.daouser = daouser;
    }

    @Override
    public boolean deleteObject(int id) {
        return daouser.delete(id);
    }
    @Override
    public boolean deleteObject(Utilisateur user) {
        return daouser.delete(user);
    }

    @Override
    public List<Utilisateur> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



	@Override
	public Utilisateur getByName(String name) {
		
		return daouser.getByName(name);
	}



	@Override
	public Utilisateur getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Utilisateur> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Utilisateur getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
    
}
