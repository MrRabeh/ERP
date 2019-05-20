package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.Rubrique;
import ims.service.ModelService;

public class ServiceRubriqueDaoImpl implements ModelService<Rubrique> {

	
	
	
	@ManagedProperty(value="#{RubriqueHibernate}")
	ModelDao<Rubrique> modeldao;
	
	@Override
	public boolean insertObject(Rubrique object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(Rubrique object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(Rubrique object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Rubrique> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<Rubrique> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rubrique getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public Rubrique getByName(String name) {
		return modeldao.getByName(name);
	}

	@Override
	public Rubrique getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rubrique> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rubrique getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<Rubrique> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<Rubrique> modeldao) {
		this.modeldao = modeldao;
	}

}
