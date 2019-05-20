package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.Salaires;
import ims.service.ModelService;

public class ServiceSalaireImpl implements ModelService<Salaires> {

	
	@ManagedProperty(value="#{SalaireHibernate}")
    ModelDao<Salaires> modeldao;
	
	@Override
	public boolean insertObject(Salaires object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(Salaires object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(Salaires object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Salaires> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<Salaires> getObjects(int id) {
		return modeldao.getbyObjet(id);
	}

	@Override
	public Salaires getObject(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Salaires getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Salaires getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Salaires> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Salaires getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<Salaires> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<Salaires> modeldao) {
		this.modeldao = modeldao;
	}

}
