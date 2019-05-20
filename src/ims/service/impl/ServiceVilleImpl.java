package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Ville;
import ims.service.ModelService;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

public class ServiceVilleImpl implements ModelService<Ville>,Serializable{

	private static final long serialVersionUID = 1L;
	 
	@ManagedProperty(value="#{VilleHibernate}")
    ModelDao<Ville> daoville;

    public ServiceVilleImpl() {
    }

    @Override
    public boolean insertObject(Ville ville) {
        return daoville.insert(ville);
    }

    @Override
    public boolean updateObject(Ville ville) {
       return daoville.update(ville);
    }

    @Override
    public List<Ville> getObject() {
        return daoville.get();
    }

    @Override
    public Ville getObject(int id) {
       return daoville.get(id);
    }

    public ModelDao<Ville> getDaoVille() {
        return daoville;
    }

    public void setDaoville(ModelDao<Ville> daoville) {
        this.daoville = daoville;
    }

    @Override
    public boolean deleteObject(int id) {
        return daoville.delete(id);
    }
    @Override
    public boolean deleteObject(Ville ville) {
        return daoville.delete(ville);
    }

    @Override
    public List<Ville> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public Ville getByName(String name) {
		return daoville.getByName(name);
	}

	@Override
	public Ville getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ville> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ville getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<Ville> getDaoville() {
		return daoville;
	}
    
    
}