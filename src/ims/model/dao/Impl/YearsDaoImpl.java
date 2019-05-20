package ims.model.dao.Impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.years;

public class YearsDaoImpl implements ModelDao<years>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean insert(years object) {
	    Session session=DataIms.getSessionFactory().getCurrentSession();
        try {
           
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            session.close();
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return false;
        }
	}

	@Override
	public boolean update(years object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(years object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public years get(int id) {
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			years obj=(years)session.load(years.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<years> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<years> get() {
		  List<years> objs;
	      Session session=DataIms.getSessionFactory().getCurrentSession();
	     try {
	          session.getTransaction().begin();
	          session.clear();
	          objs=((List<years>)session.createQuery("from years").list());
	          return objs;
	      } catch (Exception e) {
	          session.close();
	          System.err.println("EROR YEARS IN HIBERNATE->"+e.getMessage());
	          return null;
	      }
	}

	@Override
	public years getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public years getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<years> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public years getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
