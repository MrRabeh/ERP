package ims.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.Facture;
import ims.service.ModelService;

public class ServiceFactureImpl implements ModelService<Facture>,Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{FactureHibernate}")
    ModelDao<Facture> modeldao;
    
	@Override
	public boolean insertObject(Facture object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(Facture object) {
		// TODO Auto-generated method stub
		return modeldao.update(object);
	}

	@Override
	public boolean deleteObject(Facture object) {
		// TODO Auto-generated method stub
		return modeldao.delete(object);
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.delete(id);
	}

	@Override
	public List<Facture> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<Facture> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Facture getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	public ModelDao<Facture> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<Facture> modeldao) {
		this.modeldao = modeldao;
	}

	@Override
	public Facture getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Facture getByID(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public List<Facture> getByNames(String... name) {
		// TODO Auto-generated method stub
		return modeldao.getByNames(name);
	}

	@Override
	public Facture getByIids(int... id) {
		return modeldao.getByIids(id);
	}

}
