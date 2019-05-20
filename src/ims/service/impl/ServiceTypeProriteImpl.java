package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.TypePriorite;
import ims.service.ModelService;

public class ServiceTypeProriteImpl implements ModelService<TypePriorite> {

	@ManagedProperty(value="#{TypePrioriteHibernate}")
	ModelDao<TypePriorite> modeldao;
	
	@Override
	public boolean insertObject(TypePriorite object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(TypePriorite object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(TypePriorite object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<TypePriorite> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<TypePriorite> getObjects(int id) {
		// TODO Auto-generated method stub
		return modeldao.getbyObjet(id);
	}

	@Override
	public TypePriorite getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public TypePriorite getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypePriorite getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypePriorite> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypePriorite getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<TypePriorite> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<TypePriorite> modeldao) {
		this.modeldao = modeldao;
	}
	
	

}
