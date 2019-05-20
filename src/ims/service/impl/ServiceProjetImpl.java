package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Projet;
import ims.service.ModelService;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

public class ServiceProjetImpl implements ModelService<Projet>,Serializable{

	private static final long serialVersionUID = 1L;
	 @ManagedProperty(value="#{ProjetHibernate}")
    ModelDao<Projet> daoprojet;

    public ServiceProjetImpl() {
    }

    @Override
    public boolean insertObject(Projet projet) {
        return daoprojet.insert(projet);
    }

    @Override
    public boolean updateObject(Projet projet) {
       return daoprojet.update(projet);
    }

    @Override
    public List<Projet> getObject() {
        return daoprojet.get();
    }

    @Override
    public Projet getObject(int id) {
       return (Projet)daoprojet.get(id);
    }

    public ModelDao<Projet> getDaoProjet() {
        return daoprojet;
    }

    public void setDaoprojet(ModelDao<Projet> daoprojet) {
        this.daoprojet = daoprojet;
    }

    @Override
    public boolean deleteObject(int id) {
        return daoprojet.delete(id);
    }
    @Override
    public boolean deleteObject(Projet projet) {
        return daoprojet.delete(projet);
    }

    @Override
    public List<Projet> getObjects(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public Projet getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Projet getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Projet> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Projet getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}