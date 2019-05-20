package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.AvoirFournisseur;
import ims.service.ModelService;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

public class ServiceAvoirFournisseurImpl implements ModelService<AvoirFournisseur>,Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@ManagedProperty(value="#{AvoirFournisseurHibernate}")
    ModelDao<AvoirFournisseur> daoavoirfournisseur;

    public ServiceAvoirFournisseurImpl() {
    }

    @Override
    public boolean insertObject(AvoirFournisseur avoirfournisseur) {
        return daoavoirfournisseur.insert(avoirfournisseur);
    }

    @Override
    public boolean updateObject(AvoirFournisseur avoirfournisseur) {
       return daoavoirfournisseur.update(avoirfournisseur);
    }

    @Override
    public List<AvoirFournisseur> getObject() {
        return daoavoirfournisseur.get();
    }

    @Override
    public AvoirFournisseur getObject(int id) {
       return (AvoirFournisseur)daoavoirfournisseur.get(id);
    }

    public ModelDao<AvoirFournisseur> getDaoavoirfournisseur() {
        return daoavoirfournisseur;
    }

    public void setDaoavoirfournisseur(ModelDao<AvoirFournisseur> daoavoirfournisseur) {
        this.daoavoirfournisseur= daoavoirfournisseur;
    }

    @Override
    public boolean deleteObject(int id) {
        return daoavoirfournisseur.delete(id);
    }
    @Override
    public boolean deleteObject(AvoirFournisseur avoirfournisseur) {
        return daoavoirfournisseur.delete(avoirfournisseur);
    }

    @Override
    public List<AvoirFournisseur> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public AvoirFournisseur getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AvoirFournisseur getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AvoirFournisseur> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AvoirFournisseur getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}