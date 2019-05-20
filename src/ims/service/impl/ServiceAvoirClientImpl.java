package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.AvoirClient;
import ims.service.ModelService;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

public class ServiceAvoirClientImpl implements ModelService<AvoirClient>,Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@ManagedProperty(value="#{AvoirClientHibernate}")
    ModelDao<AvoirClient> daoavoirclient;

    public ServiceAvoirClientImpl() {
    }

    @Override
    public boolean insertObject(AvoirClient avoirclient) {
        return daoavoirclient.insert(avoirclient);
    }

    @Override
    public boolean updateObject(AvoirClient avoirclient) {
       return daoavoirclient.update(avoirclient);
    }

    @Override
    public List<AvoirClient> getObject() {
        return daoavoirclient.get();
    }

    @Override
    public AvoirClient getObject(int id) {
       return daoavoirclient.get(id);
    }

    public ModelDao<AvoirClient> getDaoAvoirClient() {
        return daoavoirclient;
    }

    public void setDaoavoirclient(ModelDao<AvoirClient> daoavoirclient) {
        this.daoavoirclient = daoavoirclient;
    }

    @Override
    public boolean deleteObject(int id) {
        return daoavoirclient.delete(id);
    }
    @Override
    public boolean deleteObject(AvoirClient avoirclient) {
        return daoavoirclient.delete(avoirclient);
    }

    @Override
    public List<AvoirClient> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public AvoirClient getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AvoirClient getByID(int id) {
		// TODO Auto-generated method stub
		return daoavoirclient.get(id);
	}

	@Override
	public List<AvoirClient> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AvoirClient getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}