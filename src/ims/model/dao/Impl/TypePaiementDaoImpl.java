package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.TypePaiement;

public class TypePaiementDaoImpl implements ModelDao<TypePaiement> {

	@Override
	public boolean insert(TypePaiement object) {
		 try {
	            Session session=DataIms.getSessionFactory().getCurrentSession();
	            session.beginTransaction();
	            session.save(object);
	            session.getTransaction().commit();
	            session.close();
	            return true;
	        } catch (Exception e) {
	            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
	            return false;
	        }
	}

	@Override
	public boolean update(TypePaiement object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(TypePaiement object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TypePaiement get(int id) {
		 Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				TypePaiement obj=(TypePaiement)session.load(TypePaiement.class,id);
				return obj;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<TypePaiement> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypePaiement> get() {
		try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.clear();
            return ((List<TypePaiement>)session.createQuery("from TypePaiement").list());
            
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return new ArrayList<TypePaiement>();
        }
	}

	@Override
	public TypePaiement getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypePaiement getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypePaiement> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypePaiement getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
