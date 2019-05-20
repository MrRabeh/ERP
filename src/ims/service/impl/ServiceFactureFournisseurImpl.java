package ims.service.impl;

import java.util.List;

import ims.model.dao.ModelDao;
import ims.model.entities.FactureFournisseur;
import ims.service.ModelService;

public class ServiceFactureFournisseurImpl implements
		ModelService<FactureFournisseur> {

	ModelDao<FactureFournisseur> modeldao;
	
	@Override
	public boolean insertObject(FactureFournisseur object) {
		// TODO Auto-generated method stub
		return modeldao.insert(object);
	}

	@Override
	public boolean updateObject(FactureFournisseur object) {
		// TODO Auto-generated method stub
		return modeldao.update(object);
	}

	@Override
	public boolean deleteObject(FactureFournisseur object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<FactureFournisseur> getObject() {
		// TODO Auto-generated method stub
		return modeldao.get();
	}

	@Override
	public List<FactureFournisseur> getObjects(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FactureFournisseur getObject(int id) {
		// TODO Auto-generated method stub
		return modeldao.get(id);
	}

	@Override
	public FactureFournisseur getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FactureFournisseur getByID(int id) {
		return modeldao.get(id);
	}

	@Override
	public List<FactureFournisseur> getByNames(String... name) {
		return modeldao.getByNames(name);
	}

	@Override
	public FactureFournisseur getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelDao<FactureFournisseur> getModeldao() {
		return modeldao;
	}

	public void setModeldao(ModelDao<FactureFournisseur> modeldao) {
		this.modeldao = modeldao;
	}

	
}
