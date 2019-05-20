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
public class ServiceFournisseurImpl implements ModelService<Fournisseur>,Serializable {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{FournisseurHibernate}")
    ModelDao<Fournisseur> modeldao;
    
    @Override
    public boolean insertObject(Fournisseur object) {
        
    return modeldao.insert(object);
    }

    @Override
    public boolean updateObject(Fournisseur object) {
    return modeldao.update(object);
    
    }

    @Override
    public boolean deleteObject(Fournisseur object) {
    return modeldao.delete(object);
    }

    @Override
    public boolean deleteObject(int id) {
    return modeldao.delete(id);
    }

    @Override
    public List<Fournisseur> getObject() {
    return modeldao.get();
    }

    @Override
    public Fournisseur getObject(int id) {
    return modeldao.get(id);
    }

    public ModelDao<Fournisseur> getModeldao() {
        return modeldao;
    }

    public void setModeldao(ModelDao<Fournisseur> modeldao) {
        this.modeldao = modeldao;
    }

    @Override
    public List<Fournisseur> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public Fournisseur getByName(String name) {
		return modeldao.getByName(name);
	}

	@Override
	public Fournisseur getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fournisseur> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fournisseur getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
