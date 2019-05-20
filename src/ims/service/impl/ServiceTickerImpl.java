package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.Ticker;
import ims.service.ModelService;

public class ServiceTickerImpl implements ModelService<Ticker> {

	
	 @ManagedProperty(value="#{TickerHibernate}")
	 ModelDao<Ticker> modeldao;
	
	@Override
	public boolean insertObject(Ticker object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(Ticker object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(Ticker object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Ticker> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<Ticker> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticker getObject(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticker getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticker getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ticker> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticker getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<Ticker> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<Ticker> modeldao) {
		this.modeldao = modeldao;
	}
	
	

}
