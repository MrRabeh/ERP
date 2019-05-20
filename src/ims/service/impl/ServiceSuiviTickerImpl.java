package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.SuiviTicker;
import ims.service.ModelService;

public class ServiceSuiviTickerImpl implements ModelService<SuiviTicker> {

	@ManagedProperty(value="#{SuviTickerHibernate}")
	ModelDao<SuiviTicker> modeldao;
	
	@Override
	public boolean insertObject(SuiviTicker object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(SuiviTicker object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(SuiviTicker object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.delete(id);
	}

	@Override
	public List<SuiviTicker> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<SuiviTicker> getObjects(int id) {
		// TODO Auto-generated method stub
		return modeldao.getbyObjet(id);
	}

	@Override
	public SuiviTicker getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public SuiviTicker getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuiviTicker getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SuiviTicker> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuiviTicker getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<SuiviTicker> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<SuiviTicker> modeldao) {
		this.modeldao = modeldao;
	}
	
	

}
