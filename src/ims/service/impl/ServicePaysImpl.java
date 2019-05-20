package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Pays;
import ims.service.ModelService;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

public class ServicePaysImpl implements ModelService<Pays>,Serializable{

	private static final long serialVersionUID = 1L;
	
	
	 @ManagedProperty(value="#{PaysHibernate}")
	 ModelDao<Pays> daopays;

    public ServicePaysImpl() {
    }
	
    @Override
    public boolean insertObject(Pays pays) {
        return daopays.insert(pays);
    }

    @Override
    public boolean updateObject(Pays pays) {
       return daopays.update(pays);
    }

    @Override
    public List<Pays> getObject() {
        return daopays.get();
    }

    @Override
    public Pays getObject(int id) {
       return (Pays)daopays.get(id);
    }

    public ModelDao<Pays> getDaoPays() {
        return daopays;
    }

    public void setDaopays(ModelDao<Pays> daopays) {
        this.daopays = daopays;
    }

    @Override
    public boolean deleteObject(int id) {
        return daopays.delete(id);
    }
    @Override
    public boolean deleteObject(Pays pays) {
        return daopays.delete(pays);
    }

    @Override
    public List<Pays> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public Pays getByName(String name) {
		return daopays.getByName(name);
	}

	@Override
	public Pays getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pays> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pays getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<Pays> getDaopays() {
		return daopays;
	}
    
    
}