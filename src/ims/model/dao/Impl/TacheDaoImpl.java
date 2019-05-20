package ims.model.dao.Impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Tache;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

public class TacheDaoImpl implements ModelDao<Tache>{

	@Override
	public boolean insert(Tache object) {
		 //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
	public boolean update(Tache object) {
	      //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	     Session session=DataIms.sessionFactory.getCurrentSession();
	        try {
				session.getTransaction().begin();
				session.update(object);
				session.getTransaction().commit();
				session.close();
				return true;
			} catch (Exception e) {
	                        session.close();
	                            e.printStackTrace();
	                         System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
				return false;
			}
	}

	@Override
	public boolean delete(int id) {
		 throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		   
	}

	@Override
	public boolean delete(Tache object) {
		  // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	     try {
			   	Session session=DataIms.sessionFactory.getCurrentSession();
				session.getTransaction().begin();
				session.delete(object);
				session.getTransaction().commit();
				session.close();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public Tache get(int id) {
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			Tache obj=(Tache)session.load(Tache.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Tache> getbyObjet(int id) {
		  List<Tache> objs;
	      Session session=DataIms.getSessionFactory().getCurrentSession();
	     try {
	          session.getTransaction().begin();
	          Query q=session.createQuery("select o from o in class Tache where o.projet.idprojet=:id");
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
	public List<Tache> get() {
        List<Tache> objs;
      Session session=DataIms.getSessionFactory().getCurrentSession();
     try {
          session.getTransaction().begin();
          session.clear();
          objs=((List<Tache>)session.createQuery("from Tache").list());
          return objs;
      } catch (Exception e) {
          session.close();
          System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
          return null;
      }
	}

	@Override
	public Tache getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tache getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tache> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tache getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
