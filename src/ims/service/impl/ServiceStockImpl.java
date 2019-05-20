package ims.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.Stock;
import ims.service.ModelService;

public class ServiceStockImpl implements ModelService<Stock>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{StockHibernate}")
	ModelDao<Stock> modeldao;
	
	@Override
	public boolean insertObject(Stock object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(Stock object) {
		// TODO Auto-generated method stub
		return modeldao.update(object);
	}

	@Override
	public boolean deleteObject(Stock object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Stock> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<Stock> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stock getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public Stock getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stock getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock> getByNames(String... name) {
		// TODO Auto-generated method stub
		return modeldao.getByNames(name);
	}

	@Override
	public Stock getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<Stock> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<Stock> modeldao) {
		this.modeldao = modeldao;
	}

}
