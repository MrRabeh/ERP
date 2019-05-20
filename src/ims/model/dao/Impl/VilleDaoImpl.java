package ims.model.dao.Impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Ville;

public class VilleDaoImpl implements ModelDao<Ville>{

	@Override
	public boolean insert(Ville object) {
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
	public boolean update(Ville object) {
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
	public boolean delete(Ville object) {
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
	public Ville get(int id) {
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			Ville obj=(Ville)session.load(Ville.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Ville> getbyObjet(int id) {
		  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ville> get() {
        List<Ville> objs;
      Session session=DataIms.getSessionFactory().getCurrentSession();
     try {
          session.getTransaction().begin();
          session.clear();
          objs=((List<Ville>)session.createQuery("from Ville").list());
          return objs;
      } catch (Exception e) {
          session.close();
          System.err.println("EROR VILLE IN HIBERNATE->"+e.getMessage());
          return null;
      }
	}

	@Override
	public Ville getByName(String name) {
		System.out.println("DAO Article "+name);
		Ville object=null;
		try {
			Session session=DataIms.sessionFactory.getCurrentSession();
	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class Ville where obj.ville=:ref");
	        q.setParameter("ref",name);
	        object=(Ville)q.uniqueResult();
	        object=(Ville)session.load(Ville.class,object.getIdville());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public Ville getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ville> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ville getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
