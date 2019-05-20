package ims.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.TypeOffre;
import ims.service.ModelService;

public class ServiceTypeOffreImpl implements ModelService<TypeOffre> ,Serializable{

	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{TypeOffreHibernate}")
	ModelDao<TypeOffre> modeldao;
	 
	@Override
	public boolean insertObject(TypeOffre object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(TypeOffre object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(TypeOffre object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<TypeOffre> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<TypeOffre> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeOffre getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public TypeOffre getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeOffre getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeOffre> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeOffre getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<TypeOffre> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<TypeOffre> modeldao) {
		this.modeldao = modeldao;
	}

}
