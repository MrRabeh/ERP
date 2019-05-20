package ims.model.dao.Impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Ticker;

public class TickerDaoImpl implements ModelDao<Ticker> {

	@Override
	public boolean insert(Ticker object) {
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
	public boolean update(Ticker object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Ticker object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Ticker get(int id) {
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			Ticker obj=(Ticker)session.load(Ticker.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Ticker> getbyObjet(int id) {
		  List<Ticker> objs;
	      Session session=DataIms.getSessionFactory().getCurrentSession();
	     try {
	          session.getTransaction().begin();
	          Query q=session.createQuery("select o from o in class Ticker where o.user.iduser=:id");
	          q.setParameter("id", id);
	          objs=null;
	          return objs;
	      } catch (Exception e) {
	          session.close();
	          System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
	          return null;
	      }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ticker> get() {
        List<Ticker> objs;
      Session session=DataIms.getSessionFactory().getCurrentSession();
     try {
          session.getTransaction().begin();
          session.clear();
          objs=((List<Ticker>)session.createQuery("from Ticker").list());
          return objs;
      } catch (Exception e) {
          session.close();
          System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
          return null;
      }
	}

	@Override
	public Ticker getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticker getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ticker> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticker getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
