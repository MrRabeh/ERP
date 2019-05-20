package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.Cnss;
import ims.service.ModelService;

public class ServiceCnssImpl implements ModelService<Cnss> {

	
	@ManagedProperty(value="#{CnssHibernate}")
	ModelDao<Cnss> modeldao;
	
	
	@Override
	public boolean insertObject(Cnss object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(Cnss object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(Cnss object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Cnss> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<Cnss> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cnss getObject(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cnss getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cnss getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cnss> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cnss getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<Cnss> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<Cnss> modeldao) {
		this.modeldao = modeldao;
	}
	
	

}
