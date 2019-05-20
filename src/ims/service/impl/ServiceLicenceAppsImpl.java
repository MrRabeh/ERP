package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.LicenceApplication;
import ims.service.ModelService;

public class ServiceLicenceAppsImpl implements ModelService<LicenceApplication> {

	@ManagedProperty(value="#{LicenceAppsHibernate}")
    ModelDao<LicenceApplication> modeldao;
    
	@Override
	public boolean insertObject(LicenceApplication object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(LicenceApplication object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(LicenceApplication object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<LicenceApplication> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<LicenceApplication> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LicenceApplication getObject(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LicenceApplication getByName(String cle) {
		return modeldao.getByName(cle);
	}

	@Override
	public LicenceApplication getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LicenceApplication> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LicenceApplication getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<LicenceApplication> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<LicenceApplication> modeldao) {
		this.modeldao = modeldao;
	}

}
