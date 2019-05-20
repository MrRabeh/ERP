package ims.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.LigneCaisse;
import ims.service.ModelService;

public class ServiceLigneCaisseImpl implements ModelService<LigneCaisse>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@ManagedProperty(value="#{LigneCaisseHibernate}")
    ModelDao<LigneCaisse> modeldao;
	@Override
	public boolean insertObject(LigneCaisse object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(LigneCaisse object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(LigneCaisse object) {
		// TODO Auto-generated method stub
		return modeldao.delete(object);
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<LigneCaisse> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<LigneCaisse> getObjects(int id) {
		// TODO Auto-generated method stub
		return modeldao.getbyObjet(id);
	}

	@Override
	public LigneCaisse getObject(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LigneCaisse getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LigneCaisse getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LigneCaisse> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LigneCaisse getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<LigneCaisse> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<LigneCaisse> modeldao) {
		this.modeldao = modeldao;
	}

}
