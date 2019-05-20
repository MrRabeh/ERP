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
public class ServiceClientImpl implements ModelService<Client>,Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{ClientHibernate}")
    ModelDao<Client> modeldao;
    
    @Override
    public boolean insertObject(Client object) {
        
    return modeldao.insert(object);
    }

    @Override
    public boolean updateObject(Client object) {
    return modeldao.update(object);
    
    }

    @Override
    public boolean deleteObject(Client object) {
    return modeldao.delete(object);
    }

    @Override
    public boolean deleteObject(int id) {
    return modeldao.delete(id);
    }

    @Override
    public List<Client> getObject() {
    return modeldao.get();
    }

    @Override
    public Client getObject(int id) {
    return (Client)modeldao.get(id);
    }     

    public ModelDao<Client> getModeldao() {
        return modeldao;
    }

    public void setModeldao(ModelDao<Client> modeldao) {
        this.modeldao = modeldao;
    }

    @Override
    public List<Client> getObjects(int id) {
      return modeldao.getbyObjet(id);
    }

	@Override
	public Client getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Client> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
