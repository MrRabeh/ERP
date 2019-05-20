package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Charge;

public class ChargeDaoImpl implements ModelDao<Charge> {

	@Override
	public boolean insert(Charge object) {
        Session session=DataIms.getSessionFactory().getCurrentSession();
	    try {

            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            session.close();
            System.err.println("ERRORRRRRRRRRRRRRRR ARTICLE IN HIBERNATE->"+e.getMessage());
            return false;
        }
	}

	@Override
	public boolean update(Charge object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Charge object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Charge get(int id) {
	       Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				Charge obj=(Charge)session.load(Charge.class,id);
				return obj;
			}catch (Exception e) {
	            session.close();
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Charge> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Charge> get() {
        Session session=DataIms.getSessionFactory().getCurrentSession();
	      try {
	    	    List<Charge> lignes=null;

	            session.beginTransaction();
	            session.clear();
		        Query q=session.createQuery("from Charge");
		        lignes=((List<Charge>)q.list());
	            return lignes;
	            
	        } catch (Exception e) {
	            session.close();
	            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
	            return new ArrayList<Charge>();
	        }
	}

	@Override
	public Charge getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Charge getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Charge> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Charge getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
