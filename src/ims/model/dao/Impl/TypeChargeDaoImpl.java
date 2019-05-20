package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Charge;
import ims.model.entities.TypeCharge;

public class TypeChargeDaoImpl implements ModelDao<TypeCharge> {

	@Override
	public boolean insert(TypeCharge object) {
	    try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR ARTICLE IN HIBERNATE->"+e.getMessage());
            return false;
        }
	}

	@Override
	public boolean update(TypeCharge object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(TypeCharge object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TypeCharge get(int id) {
		   Session session=DataIms.sessionFactory.getCurrentSession();
					try {
						session.getTransaction().begin();
						TypeCharge obj=(TypeCharge)session.load(TypeCharge.class,id);
						return obj;
					}catch (Exception e) {
						e.printStackTrace();
						return null;
					}
	}

	@Override
	public List<TypeCharge> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeCharge> get() {
	      try {
	    	    List<TypeCharge> lignes=null;
	            Session session=DataIms.getSessionFactory().getCurrentSession();
	            session.beginTransaction();
	            session.clear();
		        Query q=session.createQuery("from TypeCharge");
		        lignes=((List<TypeCharge>)q.list());
	            return lignes;
	            
	        } catch (Exception e) {
	            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
	            return new ArrayList<TypeCharge>();
	        }
	}

	@Override
	public TypeCharge getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeCharge getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeCharge> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeCharge getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
