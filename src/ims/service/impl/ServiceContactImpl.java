package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.Contact;
import ims.service.ModelService;

public class ServiceContactImpl implements ModelService<Contact>{

	
	@ManagedProperty(value="#{ContactHibernate}")
    ModelDao<Contact> modeldao;
	
	@Override
	public boolean insertObject(Contact object) {
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(Contact object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(Contact object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Contact> getObject() {
		return modeldao.get();
	}

	@Override
	public List<Contact> getObjects(int id) {
		// TODO Auto-generated method stub
		return modeldao.getbyObjet(id);
	}

	@Override
	public Contact getObject(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contact getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contact getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contact> getByNames(String... name) {
		return modeldao.getByNames(name);
	}

	@Override
	public Contact getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<Contact> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<Contact> modeldao) {
		this.modeldao = modeldao;
	}

}
