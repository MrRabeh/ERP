package ims.service.impl;

import java.util.List;

import ims.model.dao.ModelDao;
import ims.model.entities.FactureFournisseur_articles;
import ims.service.ModelService;

public class ServiceFactureFournisseurArticlesImpl implements
		ModelService<FactureFournisseur_articles> {

	
	ModelDao<FactureFournisseur_articles> modeldao;
			
	@Override
	public boolean insertObject(FactureFournisseur_articles object) {
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(FactureFournisseur_articles object) {
		return modeldao.update(object);
	}

	@Override
	public boolean deleteObject(FactureFournisseur_articles object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<FactureFournisseur_articles> getObject() {
		return modeldao.get();
	}

	@Override
	public List<FactureFournisseur_articles> getObjects(int id) {
		
		return modeldao.getbyObjet(id);
	}

	@Override
	public FactureFournisseur_articles getObject(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FactureFournisseur_articles getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FactureFournisseur_articles getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FactureFournisseur_articles> getByNames(String... name) {
		return modeldao.getByNames(name);
	}

	@Override
	public FactureFournisseur_articles getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<FactureFournisseur_articles> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<FactureFournisseur_articles> modeldao) {
		this.modeldao = modeldao;
	}
	
	

}
