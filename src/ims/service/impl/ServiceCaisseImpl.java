package ims.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.Caisse;
import ims.service.ModelService;

public class ServiceCaisseImpl implements ModelService<Caisse>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@ManagedProperty(value="#{CaisseHibernate}")
    ModelDao<Caisse> modeldao;
	
	@Override
	public boolean insertObject(Caisse object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(Caisse object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(Caisse object) {
		return modeldao.delete(object);
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Caisse> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<Caisse> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Caisse getObject(int id) {
		return modeldao.get(id);
	}

	@Override
	public Caisse getByName(String name) {
		return modeldao.getByName(name);
	}

	@Override
	public Caisse getByID(int id) {
		// TODO Auto-generated method stub
		return modeldao.getByID(id);
	}

	@Override
	public List<Caisse> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Caisse getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<Caisse> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<Caisse> modeldao) {
		this.modeldao = modeldao;
	}

}
