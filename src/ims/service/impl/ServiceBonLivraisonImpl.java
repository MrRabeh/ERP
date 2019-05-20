package ims.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.BonLivraison;
import ims.service.ModelService;

public class ServiceBonLivraisonImpl implements ModelService<BonLivraison> ,Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{BonLivraisonHibernate}")
    ModelDao<BonLivraison> modeldao;
    
	@Override
	public boolean insertObject(BonLivraison object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(BonLivraison object) {
		// TODO Auto-generated method stub
		return modeldao.update(object);
	}

	@Override
	public boolean deleteObject(BonLivraison object) {
		// TODO Auto-generated method stub
		return modeldao.delete(object);
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.delete(id);
	}

	@Override
	public List<BonLivraison> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<BonLivraison> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BonLivraison getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	public ModelDao<BonLivraison> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<BonLivraison> modeldao) {
		this.modeldao = modeldao;
	}

	@Override
	public BonLivraison getByName(String name) {
		// TODO Auto-generated method stub
		return this.modeldao.getByName(name);
	}

	@Override
	public BonLivraison getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BonLivraison> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BonLivraison getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
