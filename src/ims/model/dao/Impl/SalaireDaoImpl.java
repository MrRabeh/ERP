package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Salaires;

public class SalaireDaoImpl implements ModelDao<Salaires> {

	@Override
	public boolean insert(Salaires object) {
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
	public boolean update(Salaires object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Salaires object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Salaires get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Salaires> getbyObjet(int id) {
		  List<Salaires> lignes=null;
			try {
				Session session=DataIms.sessionFactory.getCurrentSession();
		        session.beginTransaction();
		        Query q=session.createQuery("select obj from obj in class Salaires where obj.ordre.id=:id");
		        q.setParameter("id",id);
		        lignes=((List<Salaires>)q.list());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lignes;
	}

	@Override
	public List<Salaires> get() {
	      try {
	    	    List<Salaires> lignes=null;
	            Session session=DataIms.getSessionFactory().getCurrentSession();
	            session.beginTransaction();
	            session.clear();
		        Query q=session.createQuery("from Salaires");
		        lignes=((List<Salaires>)q.list());
	            return lignes;
	            
	        } catch (Exception e) {
	            System.err.println("ERRORRRRRRRRRRRRRRR SalairesIN HIBERNATE->"+e.getMessage());
	            return new ArrayList<Salaires>();
	        }
	}

	@Override
	public Salaires getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Salaires getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Salaires> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Salaires getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
