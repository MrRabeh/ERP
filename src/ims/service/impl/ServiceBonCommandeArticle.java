package ims.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.Boncommande_Article;
import ims.service.ModelService;

public class ServiceBonCommandeArticle implements ModelService<Boncommande_Article>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{BonCommandeArticleHibernate}")
	private ModelDao<Boncommande_Article> modeldao;
	
	@Override
	public boolean insertObject(Boncommande_Article object) {
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(Boncommande_Article object) {
		return modeldao.update(object);
	}

	@Override
	public boolean deleteObject(Boncommande_Article object) {
		return modeldao.delete(object);
	}

	@Override
	public boolean deleteObject(int id) {
		return modeldao.delete(id);
	}

	@Override
	public List<Boncommande_Article> getObject() {
		return modeldao.get();
	}

	@Override
	public List<Boncommande_Article> getObjects(int id) {
		return modeldao.getbyObjet(id);
	}

	@Override
	public Boncommande_Article getObject(int id) {
		return modeldao.get(id);
	}

	@Override
	public Boncommande_Article getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boncommande_Article getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Boncommande_Article> getByNames(String... name) {
		return modeldao.getByNames(name);
	}

	@Override
	public Boncommande_Article getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<Boncommande_Article> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<Boncommande_Article> modeldao) {
		this.modeldao = modeldao;
	}
	

}
