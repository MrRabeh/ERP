package ims.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.Facture_Article;
import ims.service.ModelService;

public class ServiceFactureArticleImpl implements ModelService<Facture_Article>,Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{factarticleHibernate}")
    ModelDao<Facture_Article> modeldao;
	
	@Override
	public boolean insertObject(Facture_Article object) {
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(Facture_Article object) {
		return modeldao.update(object);
	}

	@Override
	public boolean deleteObject(Facture_Article object) {
		return modeldao.delete(object);
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Facture_Article> getObject() {
		return modeldao.get();
	}

	@Override
	public List<Facture_Article> getObjects(int id) {
		return modeldao.getbyObjet(id);
	}

	@Override
	public Facture_Article getObject(int id) {
		return modeldao.get(id);
	}

	@Override
	public Facture_Article getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Facture_Article getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Facture_Article> getByNames(String... name) {
		return modeldao.getByNames(name);
	}

	@Override
	public Facture_Article getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<Facture_Article> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<Facture_Article> modeldao) {
		this.modeldao = modeldao;
	}

}
