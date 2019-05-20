package ims.service.impl;

import java.io.Serializable;
import java.util.List;

import ims.model.dao.ModelDao;
import ims.model.entities.Produit;
import ims.service.ModelService;

import javax.faces.bean.ManagedProperty;

public class ServiceProduitImpl implements ModelService<Produit>,Serializable{

	
		private static final long serialVersionUID = 1L;
	
		@ManagedProperty(value="#{ProduitHibernate}")
		ModelDao<Produit> daomodel;

		@Override
		public boolean insertObject(Produit object) {
			
			return daomodel.insert(object);
		}

		@Override
		public boolean updateObject(Produit object) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean deleteObject(Produit object) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean deleteObject(int id) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<Produit> getObject() {
			return daomodel.get();
		}

		@Override
		public List<Produit> getObjects(int id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Produit getObject(int id) {
			return daomodel.get(id);
		}

		@Override
		public Produit getByName(String name) {
			return daomodel.getByName(name);
		}

		@Override
		public Produit getByID(int id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Produit> getByNames(String... name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Produit getByIids(int... id) {
			// TODO Auto-generated method stub
			return null;
		}

		public ModelDao<Produit> getDaomodel() {
			return daomodel;
		}

		public void setDaomodel(ModelDao<Produit> daomodel) {
			this.daomodel = daomodel;
		}
	 

}
