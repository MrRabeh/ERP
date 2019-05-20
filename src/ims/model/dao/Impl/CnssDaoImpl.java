package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Cnss;

public class CnssDaoImpl implements ModelDao<Cnss> {

	@Override
	public boolean insert(Cnss object) {
        Session session=DataIms.getSessionFactory().getCurrentSession();
	    try {

            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();

            return true;
        } catch (Exception e) {
            session.close();
            System.err.println("ERRORRRRRRRRRRRRRRR ARTICLE IN HIBERNATE->"+e.getMessage());
            return false;
        }
	}

	@Override
	public boolean update(Cnss object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Cnss object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cnss get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cnss> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cnss> get() {
        Session session=DataIms.getSessionFactory().getCurrentSession();
	      try {
	    	    List<Cnss> lignes=null;
	            session.beginTransaction();
	            session.clear();
		        Query q=session.createQuery("from Cnss");
		        lignes=((List<Cnss>)q.list());
	            return lignes;
	            
	        } catch (Exception e) {
	        	session.close();
	            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
	            return new ArrayList<Cnss>();
	        }
	}

	@Override
	public Cnss getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cnss getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cnss> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cnss getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
