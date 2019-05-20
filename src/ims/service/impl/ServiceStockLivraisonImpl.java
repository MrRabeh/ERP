package ims.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import ims.model.dao.ModelDao;
import ims.model.entities.Stock_Livraison;
import ims.service.ModelService;

public class ServiceStockLivraisonImpl implements ModelService<Stock_Livraison>,Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{StockLivraisonHibernate}")
	private ModelDao<Stock_Livraison> modeldao;
	
	@Override
	public boolean insertObject(Stock_Livraison object) {
		System.out.println("insert stock livraison SERVICE");
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(Stock_Livraison object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(Stock_Livraison object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Stock_Livraison> getObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock_Livraison> getObjects(int id) {
		// TODO Auto-generated method stub
		return modeldao.getbyObjet(id);
	}

	@Override
	public Stock_Livraison getObject(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stock_Livraison getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stock_Livraison getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock_Livraison> getByNames(String... name) {
		return modeldao.getByNames(name);
	}

	@Override
	public Stock_Livraison getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<Stock_Livraison> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<Stock_Livraison> modeldao) {
		this.modeldao = modeldao;
	}

}
