package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.BonCommande;
import ims.service.ModelService;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

public class ServicesBonCommande implements ModelService<BonCommande>,Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{BonCommandeHibernate}")
	private ModelDao<BonCommande> modeldao;
	
	@Override
	public boolean insertObject(BonCommande object) {
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(BonCommande object) {
		return modeldao.update(object);
	}

	@Override
	public boolean deleteObject(BonCommande object) {
		return modeldao.delete(object);
	}

	@Override
	public boolean deleteObject(int id) {
		return modeldao.delete(id);
	}

	@Override
	public List<BonCommande> getObject() {
		return modeldao.get();
	}

	@Override
	public List<BonCommande> getObjects(int id) {
		return modeldao.getbyObjet(id);
	}

	@Override
	public BonCommande getObject(int id) {
		return modeldao.get(id);
	}

	public ModelDao<BonCommande> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<BonCommande> modeldao) {
		this.modeldao = modeldao;
	}

	@Override
	public BonCommande getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BonCommande getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BonCommande> getByNames(String... name) {
		return modeldao.getByNames(name);
	}

	@Override
	public BonCommande getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
