package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.CotationOffre;
import ims.service.ModelService;

public class ServiceCotationOffreImpl implements ModelService<CotationOffre> {

	
	
	@ManagedProperty(value="#{CotationOffreHibernate}")
    ModelDao<CotationOffre> modeldao;
	
	@Override
	public boolean insertObject(CotationOffre object) {
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(CotationOffre object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(CotationOffre object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CotationOffre> getObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CotationOffre> getObjects(int id) {
		// TODO Auto-generated method stub
		return modeldao.getbyObjet(id);
	}

	@Override
	public CotationOffre getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public CotationOffre getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CotationOffre getByID(int id) {
		// TODO Auto-generated method stub
		return  modeldao.get(id);
	}

	@Override
	public List<CotationOffre> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CotationOffre getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<CotationOffre> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<CotationOffre> modeldao) {
		this.modeldao = modeldao;
	}

	
}
