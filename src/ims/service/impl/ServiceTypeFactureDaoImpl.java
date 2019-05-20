package ims.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.TypeFacture;
import ims.service.ModelService;

public class ServiceTypeFactureDaoImpl implements ModelService<TypeFacture>,Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{typefactHibernate}")
	ModelDao<TypeFacture> modeldao;

	@Override
	public boolean insertObject(TypeFacture object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(TypeFacture object) {
		// TODO Auto-generated method stub
		return modeldao.update(object);
	}

	@Override
	public boolean deleteObject(TypeFacture object) {
		// TODO Auto-generated method stub
		return modeldao.delete(object);
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.delete(id);
	}

	@Override
	public List<TypeFacture> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<TypeFacture> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeFacture getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public TypeFacture getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeFacture getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeFacture> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeFacture getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<TypeFacture> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<TypeFacture> modeldao) {
		this.modeldao = modeldao;
	}
	 
	

}
