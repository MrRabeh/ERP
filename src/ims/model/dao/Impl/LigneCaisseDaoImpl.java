package ims.model.dao.Impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.LigneCaisse;

public class LigneCaisseDaoImpl implements ModelDao<LigneCaisse>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean insert(LigneCaisse object) {
        try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR LigneCaisse IN HIBERNATE->"+e.getMessage());
            return false;
        }
	}

	@Override
	public boolean update(LigneCaisse object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(LigneCaisse object) {
	      try {
	            Session session=DataIms.getSessionFactory().getCurrentSession();
	            session.beginTransaction();
	            session.delete(object);
	            session.getTransaction().commit();
	            session.close();
	            return true;
	        } catch (Exception e) {
	            System.err.println("ERRORRRRRRRRRRRRRRR CAISSE IN HIBERNATE->"+e.getMessage());
	            return false;
	        }
	}

	@Override
	public LigneCaisse get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LigneCaisse> getbyObjet(int id) {
		// TODO Auto-generated method stub
	      List<LigneCaisse> lignes=null;
			try {
				Session session=DataIms.sessionFactory.getCurrentSession();
		        session.beginTransaction();
		        Query q=session.createQuery("select obj from obj in class LigneCaisse where obj.caisse.id=:id order by obj.dateligne asc");
		        q.setParameter("id",id);
		        lignes=((List<LigneCaisse>)q.list());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lignes;
	}

	@Override
	public List<LigneCaisse> get() {
	    try {
    	    List<LigneCaisse> lignes=null;
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.clear();
	        Query q=session.createQuery("from LigneCaisse");
	        lignes=((List<LigneCaisse>)q.list());
            return lignes;
            
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN LigneCaisse HIBERNATE->"+e.getMessage());
            return new ArrayList<LigneCaisse>();
        }
	}

	@Override
	public LigneCaisse getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LigneCaisse getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LigneCaisse> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LigneCaisse getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
