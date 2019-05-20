package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.TypeCharge;
import ims.service.ModelService;

public class ServiceTypeChargeImpl implements ModelService<TypeCharge> {

	@ManagedProperty(value="#{TypeChargeHibernate}")
	ModelDao<TypeCharge> modeldao;
	
	
	@Override
	public boolean insertObject(TypeCharge object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(TypeCharge object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(TypeCharge object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<TypeCharge> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<TypeCharge> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeCharge getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public TypeCharge getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeCharge getByID(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public List<TypeCharge> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeCharge getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<TypeCharge> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<TypeCharge> modeldao) {
		this.modeldao = modeldao;
	}

}
