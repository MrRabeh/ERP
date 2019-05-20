package ims.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.Boncommande_Offre;
import ims.service.ModelService;

public class ServiceBonCommandeOffreImpl implements ModelService<Boncommande_Offre>,Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{BonCommandeOffreHibernate}")
	private ModelDao<Boncommande_Offre> modeldao;
	
	@Override
	public boolean insertObject(Boncommande_Offre object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(Boncommande_Offre object) {
		// TODO Auto-generated method stub
		return modeldao.update(object);
	}

	@Override
	public boolean deleteObject(Boncommande_Offre object) {
		// TODO Auto-generated method stub
		return modeldao.delete(object);
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Boncommande_Offre> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<Boncommande_Offre> getObjects(int id) {
		// TODO Auto-generated method stub
		return modeldao.getbyObjet(id);
	}

	@Override
	public Boncommande_Offre getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public Boncommande_Offre getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boncommande_Offre getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Boncommande_Offre> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boncommande_Offre getByIids(int... id) {
		
		return modeldao.getByIids(id);
	}

	public ModelDao<Boncommande_Offre> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<Boncommande_Offre> modeldao) {
		this.modeldao = modeldao;
	}
	

}
