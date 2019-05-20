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
public class ServiceSecteurImpl implements ModelService<Secteur>,Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{SecteurHibernate}")
    ModelDao<Secteur> modeldao;
    
    @Override
    public boolean insertObject(Secteur object) {
        
    return modeldao.insert(object);
    }

    @Override
    public boolean updateObject(Secteur object) {
    return modeldao.update(object);
    
    }

    @Override
    public boolean deleteObject(Secteur object) {
    return modeldao.delete(object);
    }

    @Override
    public boolean deleteObject(int id) {
    return modeldao.delete(id);
    }

    @Override
    public List<Secteur> getObject() {
    return (List<Secteur>)modeldao.get();
    }

    @Override
    public Secteur getObject(int id) {
    return (Secteur)modeldao.get(id);
    }

    public ModelDao<Secteur> getModeldao() {
        return modeldao;
    }

    public void setModeldao(ModelDao<Secteur> modeldao) {
        this.modeldao = modeldao;
    }

    @Override
    public List<Secteur> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public Secteur getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Secteur getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Secteur> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Secteur getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
    
}
