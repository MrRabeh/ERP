package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;

import ims.model.entities.TypeTicker;
import ims.service.ModelService;

public class ServiceTypeTickerImpl implements ModelService<TypeTicker> {

	@ManagedProperty(value="#{TypeTickerHibernate}")
	ModelDao<TypeTicker> modeldao;
	
	@Override
	public boolean insertObject(TypeTicker object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(TypeTicker object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(TypeTicker object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<TypeTicker> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<TypeTicker> getObjects(int id) {
		// TODO Auto-generated method stub
		return modeldao.getbyObjet(id);
	}

	@Override
	public TypeTicker getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public TypeTicker getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeTicker getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeTicker> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeTicker getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<TypeTicker> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<TypeTicker> modeldao) {
		this.modeldao = modeldao;
	}

}
