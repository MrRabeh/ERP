package ims.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.TypePaiement;
import ims.service.ModelService;

public class ServiceTypePaiementImpl implements ModelService<TypePaiement>,Serializable {

	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{TypePaiementHibernate}")
	ModelDao<TypePaiement> modeldao;
	 
	@Override
	public boolean insertObject(TypePaiement object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(TypePaiement object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(TypePaiement object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<TypePaiement> getObject() {
		return modeldao.get();
	}

	@Override
	public List<TypePaiement> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypePaiement getObject(int id) {
		return modeldao.get(id);
	}

	@Override
	public TypePaiement getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypePaiement getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypePaiement> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypePaiement getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<TypePaiement> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<TypePaiement> modeldao) {
		this.modeldao = modeldao;
	}

}
