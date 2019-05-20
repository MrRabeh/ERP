package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Tache;
import ims.service.ModelService;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

public class ServiceTacheImpl implements ModelService<Tache>,Serializable{

	private static final long serialVersionUID = 1L;
	
	 @ManagedProperty(value="#{TacheHibernate}")
    ModelDao<Tache> daotache;

    public ServiceTacheImpl() {
    }

    @Override
    public boolean insertObject(Tache tache) {
        return daotache.insert(tache);
    }

    @Override
    public boolean updateObject(Tache tache) {
       return daotache.update(tache);
    }

    @Override
    public List<Tache> getObject() {
        return daotache.get();
    }

    @Override
    public Tache getObject(int id) {
       return (Tache)daotache.get(id);
    }

    public ModelDao<Tache> getDaoTache() {
        return daotache;
    }

    public void setDaotache(ModelDao<Tache> daotache) {
        this.daotache = daotache;
    }

    @Override
    public boolean deleteObject(int id) {
        return daotache.delete(id);
    }
    @Override
    public boolean deleteObject(Tache tache) {
        return daotache.delete(tache);
    }

    @Override
    public List<Tache> getObjects(int id) {
       return daotache.getbyObjet(id);
    }

	@Override
	public Tache getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tache getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tache> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tache getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
}