package ims.model.dao.Impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Pays;

public class PaysDaoImpl implements ModelDao<Pays>{

	@Override
	public boolean insert(Pays object) {
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
	public boolean update(Pays object) {
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
	public boolean delete(Pays object) {
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
	public Pays get(int id) {
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			Pays obj=(Pays)session.load(Pays.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Pays> getbyObjet(int id) {
		  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pays> get() {
        List<Pays> objs;
      Session session=DataIms.getSessionFactory().getCurrentSession();
     try {
          session.getTransaction().begin();
          session.clear();
          objs=((List<Pays>)session.createQuery("from Pays").list());
          return objs;
      } catch (Exception e) {
          session.close();
          System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
          return null;
      }
	}

	@Override
	public Pays getByName(String name) {
		System.out.println("DAO Article "+name);
		Pays object=null;
		try {
			Session session=DataIms.sessionFactory.getCurrentSession();
	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class Pays where obj.pays=:ref");
	        q.setParameter("ref",name);
	        object=(Pays)q.uniqueResult();
	        object=(Pays)session.load(Pays.class,object.getIdpays());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public Pays getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pays> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pays getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
