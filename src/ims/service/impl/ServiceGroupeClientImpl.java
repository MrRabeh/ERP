package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.GroupeClient;
import ims.service.ModelService;

public class ServiceGroupeClientImpl implements ModelService<GroupeClient> {

	
	@ManagedProperty(value="#{GroupeClientHibernate}")
    ModelDao<GroupeClient> modeldao;
    
	@Override
	public boolean insertObject(GroupeClient object) {
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(GroupeClient object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(GroupeClient object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<GroupeClient> getObject() {
		System.out.println("GET ALL SERVICE GROUPE");
		return modeldao.get();
	}

	@Override
	public List<GroupeClient> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupeClient getObject(int id) {
		System.out.println("GET SERVICE GROUPE");
		return modeldao.get(id);
	}

	@Override
	public GroupeClient getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupeClient getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GroupeClient> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupeClient getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<GroupeClient> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<GroupeClient> modeldao) {
		this.modeldao = modeldao;
	}

}
