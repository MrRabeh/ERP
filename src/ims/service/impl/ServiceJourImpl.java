package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Jour;
import ims.service.ModelService;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

public class ServiceJourImpl implements ModelService<Jour>,Serializable{

	private static final long serialVersionUID = 1L;
	
	 @ManagedProperty(value="#{JourHibernate}")
	 ModelDao<Jour> daojour;

    public ServiceJourImpl() {
    }

    @Override
    public boolean insertObject(Jour jour) {
        return daojour.insert(jour);
    }

    @Override
    public boolean updateObject(Jour jour) {
       return daojour.update(jour);
    }

    @Override
    public List<Jour> getObject() {
        return daojour.get();
    }

    @Override
    public Jour getObject(int id) {
       return (Jour)daojour.get(id);
    }

    public ModelDao<Jour> getDaoJour() {
        return daojour;
    }

    public void setDaojour(ModelDao<Jour> daojour) {
        this.daojour = daojour;
    }

    @Override
    public boolean deleteObject(int id) {
        return daojour.delete(id);
    }
    @Override
    public boolean deleteObject(Jour jour) {
        return daojour.delete(jour);
    }

    @Override
    public List<Jour> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public Jour getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Jour getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Jour> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Jour getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}