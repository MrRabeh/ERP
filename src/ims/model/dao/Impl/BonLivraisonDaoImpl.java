package ims.model.dao.Impl;

import ims.model.dao.ModelDao;
import ims.model.entities.BonLivraison;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

public class BonLivraisonDaoImpl implements ModelDao<BonLivraison> {

	@Override
	public boolean insert(BonLivraison object) {
        Session session=DataIms.getSessionFactory().getCurrentSession();
	       try {

	            session.beginTransaction();
	            Integer idlivraison=(Integer)session.save(object);
	            session.getTransaction().commit();
	            session.close();
	            object.setId(idlivraison);
	            session.close();
	            return true;
	        } catch (Exception e) {
	            session.close();
	            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
	            return false;
	        }
	}

	@Override
	public boolean update(BonLivraison object) {
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
					return false;
				}
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(BonLivraison object) {
	   	Session session=DataIms.sessionFactory.getCurrentSession();
		try {

			session.getTransaction().begin();
			session.delete(object);
			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception e) {
			session.close();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public BonLivraison get(int id) {
		  Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				session.clear();
				BonLivraison obj=(BonLivraison)session.load(BonLivraison.class,id);
				return obj;
			}catch (Exception e) {
				session.close();
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<BonLivraison> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BonLivraison> get() {
        Session session=DataIms.getSessionFactory().getCurrentSession();
		   try {

	            session.beginTransaction();
	            session.clear();
	            return ((List<BonLivraison>)session.createQuery("select obj from obj IN class BonLivraison where obj.activer=true order by obj.id desc").list());
	            
	        } catch (Exception e) {
				session.close();
	            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
	            return new ArrayList<BonLivraison>();
	        }
	}

	@Override
	public BonLivraison getByName(String name) {
		BonLivraison object=null;
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {

	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class BonLivraison where obj.ref=:ref");
	        q.setParameter("ref",name);
	        object=(BonLivraison)q.uniqueResult();
	        object=(BonLivraison)session.load(BonLivraison.class,object.getId());
		} catch (Exception e) {
			session.close();
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public BonLivraison getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BonLivraison> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BonLivraison getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
