package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.Charge;
import ims.service.ModelService;

public class ServiceChargeImpl implements ModelService<Charge> {

	
	@ManagedProperty(value="#{ChargeHibernate}")
	ModelDao<Charge> modeldao;
	
	@Override
	public boolean insertObject(Charge object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(Charge object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(Charge object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Charge> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<Charge> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Charge getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public Charge getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Charge getByID(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public List<Charge> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Charge getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<Charge> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<Charge> modeldao) {
		this.modeldao = modeldao;
	}

}
