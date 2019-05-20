package ims.service.impl;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.UtilisateurHelpDesk;
import ims.service.ModelService;

public class ServiceUserHelpDeskImpl implements
		ModelService<UtilisateurHelpDesk> {

	
	@ManagedProperty(value="#{UserHelpDeskHibernate}")
	ModelDao<UtilisateurHelpDesk> modeldao;
	
	@Override
	public boolean insertObject(UtilisateurHelpDesk object) {
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(UtilisateurHelpDesk object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(UtilisateurHelpDesk object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<UtilisateurHelpDesk> getObject() {
		return modeldao.get();
	}

	@Override
	public List<UtilisateurHelpDesk> getObjects(int id) {
		// TODO Auto-generated method stub
		return modeldao.getbyObjet(id);
	}

	@Override
	public UtilisateurHelpDesk getObject(int id) {
		return modeldao.get(id);
	}

	@Override
	public UtilisateurHelpDesk getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UtilisateurHelpDesk getByID(int id) {
		return modeldao.getByID(id);
	}

	@Override
	public List<UtilisateurHelpDesk> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UtilisateurHelpDesk getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<UtilisateurHelpDesk> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<UtilisateurHelpDesk> modeldao) {
		this.modeldao = modeldao;
	}
	
	

}
