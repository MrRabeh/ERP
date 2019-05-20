package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.years;
import ims.service.ModelService;

public class ServiceYearsImpl implements ModelService<years> {

	
	@ManagedProperty(value="#{YearsHibernate}")
	ModelDao<years> modeldao;
	
	@Override
	public boolean insertObject(years object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(years object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(years object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<years> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<years> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public years getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public years getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public years getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<years> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public years getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<years> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<years> modeldao) {
		this.modeldao = modeldao;
	}

	
}
