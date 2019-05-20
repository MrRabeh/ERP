package ims.service.impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Facture_Livraison;
import ims.service.ModelService;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

public class ServiceFactureBonLivraison implements ModelService<Facture_Livraison>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@ManagedProperty(value="#{FactureLivraisonHibernate}")
	private ModelDao<Facture_Livraison> modeldao;
	
	@Override
	public boolean insertObject(Facture_Livraison object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(Facture_Livraison object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(Facture_Livraison object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Facture_Livraison> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<Facture_Livraison> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Facture_Livraison getObject(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Facture_Livraison getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Facture_Livraison getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Facture_Livraison> getByNames(String... name) {
			return this.modeldao.getByNames(name);
	}

	@Override
	public Facture_Livraison getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<Facture_Livraison> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<Facture_Livraison> modeldao) {
		this.modeldao = modeldao;
	}

}
