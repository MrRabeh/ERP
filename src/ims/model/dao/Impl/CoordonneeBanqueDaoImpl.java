package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.CoordonneeBancaire;

public class CoordonneeBanqueDaoImpl implements ModelDao<CoordonneeBancaire> {

	@Override
	public boolean insert(CoordonneeBancaire object) {
        try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return false;
        }
	}

	@Override
	public boolean update(CoordonneeBancaire object) {
	       try {
				Session session=DataIms.sessionFactory.getCurrentSession();
				session.getTransaction().begin();
				session.update(object);
				session.getTransaction().commit();
				session.close();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(CoordonneeBancaire object) {
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
	public CoordonneeBancaire get(int id) {
	    Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				CoordonneeBancaire obj=(CoordonneeBancaire)session.load(CoordonneeBancaire.class,id);
				return obj;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<CoordonneeBancaire> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoordonneeBancaire> get() {
		List<CoordonneeBancaire> banque;
		   try {
	            Session session=DataIms.getSessionFactory().getCurrentSession();
	            session.beginTransaction();
	            session.beginTransaction();
	            banque=session.createQuery("from CoordonneeBancaire").list();
	            return banque;
	        } catch (Exception e) {
	            return new ArrayList<CoordonneeBancaire>();
	        }
	}

	@Override
	public CoordonneeBancaire getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoordonneeBancaire getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public CoordonneeBancaire getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoordonneeBancaire> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}



}
