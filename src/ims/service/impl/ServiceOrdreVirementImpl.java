package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.OrdreVirement;
import ims.service.ModelService;

public class ServiceOrdreVirementImpl implements ModelService<OrdreVirement> {

	
	@ManagedProperty(value="#{OrdreVirementHibernate}")
    ModelDao<OrdreVirement> modeldao;
	
	@Override
	public boolean insertObject(OrdreVirement object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(OrdreVirement object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(OrdreVirement object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<OrdreVirement> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<OrdreVirement> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrdreVirement getObject(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrdreVirement getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrdreVirement getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrdreVirement> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrdreVirement getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<OrdreVirement> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<OrdreVirement> modeldao) {
		this.modeldao = modeldao;
	}

}
