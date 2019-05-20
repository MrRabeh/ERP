package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.ImagesbyTicker;
import ims.service.ModelService;

public class ServiceImagesTickerImpl implements ModelService<ImagesbyTicker> {

	 @ManagedProperty(value="#{ImagesTickerHibernate}")
	 ModelDao<ImagesbyTicker> modeldao;
	 
	@Override
	public boolean insertObject(ImagesbyTicker object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(ImagesbyTicker object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(ImagesbyTicker object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ImagesbyTicker> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<ImagesbyTicker> getObjects(int id) {
		// TODO Auto-generated method stub
		return modeldao.getbyObjet(id);
	}

	@Override
	public ImagesbyTicker getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public ImagesbyTicker getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImagesbyTicker getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ImagesbyTicker> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImagesbyTicker getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<ImagesbyTicker> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<ImagesbyTicker> modeldao) {
		this.modeldao = modeldao;
	}

}
