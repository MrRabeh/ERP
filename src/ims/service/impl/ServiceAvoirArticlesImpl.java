package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.Avoir_Articles;
import ims.service.ModelService;

public class ServiceAvoirArticlesImpl implements ModelService<Avoir_Articles> {

	
    @ManagedProperty(value="#{AvoirArticlesHibernate}")
    ModelDao<Avoir_Articles> modeldao;
	
	@Override
	public boolean insertObject(Avoir_Articles object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(Avoir_Articles object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(Avoir_Articles object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Avoir_Articles> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<Avoir_Articles> getObjects(int id) {
		return modeldao.getbyObjet(id);
	}

	@Override
	public Avoir_Articles getObject(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Avoir_Articles getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Avoir_Articles getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Avoir_Articles> getByNames(String... name) {
		// TODO Auto-generated method stub
		return modeldao.getByNames(name);
	}

	@Override
	public Avoir_Articles getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<Avoir_Articles> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<Avoir_Articles> modeldao) {
		this.modeldao = modeldao;
	}

}
