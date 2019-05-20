package ims.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.CoordonneeBancaire;
import ims.service.ModelService;

public class ServiceCoordonneeBanqueImpl implements ModelService<CoordonneeBancaire>,Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{CoordonneeBanqueHibernate}")
    ModelDao<CoordonneeBancaire> modeldao;
	
	@Override
	public boolean insertObject(CoordonneeBancaire object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(CoordonneeBancaire object) {
		// TODO Auto-generated method stub
		return modeldao.update(object);
	}

	@Override
	public boolean deleteObject(CoordonneeBancaire object) {
		// TODO Auto-generated method stub
		return modeldao.delete(object);
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.delete(id);
	}

	@Override
	public List<CoordonneeBancaire> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<CoordonneeBancaire> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoordonneeBancaire getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	public ModelDao<CoordonneeBancaire> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<CoordonneeBancaire> modeldao) {
		this.modeldao = modeldao;
	}

	@Override
	public CoordonneeBancaire getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoordonneeBancaire getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoordonneeBancaire> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoordonneeBancaire getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
