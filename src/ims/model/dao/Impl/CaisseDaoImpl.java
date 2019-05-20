package ims.model.dao.Impl;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Caisse;

public class CaisseDaoImpl implements ModelDao<Caisse>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean insert(Caisse object) {
        Session session=DataIms.getSessionFactory().getCurrentSession();
        try {

            session.beginTransaction();
             int id=(Integer) session.save(object);
             object.setId(id);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            session.close();
            System.err.println("ERRORRRRRRRRRRRRRRR CAISSE IN HIBERNATE->"+e.getMessage());
            return false;
        }
	}

	@Override
	public boolean update(Caisse object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Caisse object) {
        Session session=DataIms.getSessionFactory().getCurrentSession();
	      try {
	            session.beginTransaction();
	            session.delete(object);
	            session.getTransaction().commit();
	            session.close();
	            return true;
	        } catch (Exception e) {
	            session.close();
	            System.err.println("ERRORRRRRRRRRRRRRRR CAISSE IN HIBERNATE->"+e.getMessage());
	            return false;
	        }
	}

	@Override
	public Caisse get(int id) {
		  Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				Caisse obj=(Caisse)session.load(Caisse.class,id);
				return obj;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Caisse> getbyObjet(int id) {
		return null;
	}

	@Override
	public List<Caisse> get() {
        Session session=DataIms.getSessionFactory().getCurrentSession();
	    try {
    	    List<Caisse> lignes=null;

            session.beginTransaction();
            session.clear();
	        Query q=session.createQuery("select obj from obj in class Caisse order by obj.datedebutcaisse ,obj.moiscaisse asc");
	        lignes=((List<Caisse>)q.list());
            return lignes;
            
        } catch (Exception e) {
        	session.close();
            System.err.println("ERRORRRRRRRRRRRRRRR IN CAISSE HIBERNATE->"+e.getMessage());
            return new ArrayList<Caisse>();
        }
	}

	@Override
	public Caisse getByName(String name) {
		Session session=DataIms.getSessionFactory().getCurrentSession();
		try {
			 session.beginTransaction();
		        System.out.println("------------------name=01/"+name+"--------------------");
		        Query q=session.createQuery("select obj from obj in class Caisse where obj.datedebutcaisse='01/"+name+"'");
		        Caisse caisse=(Caisse)q.uniqueResult();
		        return caisse;
		} catch (Exception e) {
			session.close();
			 return null;
		}
       
	}

	@Override
	public Caisse getByID(int id) {
        Session session=DataIms.getSessionFactory().getCurrentSession();
	    try {
    	    Caisse caisse=null;

            session.beginTransaction();
            session.clear();
	        caisse=(Caisse)session.load(Caisse.class, id);
            return caisse;
            
        } catch (Exception e) {
        	session.close();
            System.err.println("ERRORRRRRRRRRRRRRRR IN CAISSE HIBERNATE->"+e.getMessage());
            return null;
        }
	}

	@Override
	public List<Caisse> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Caisse getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
