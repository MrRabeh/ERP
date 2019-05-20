package ims.model.dao.Impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Produit;

public class ProduitDaoImpl implements ModelDao<Produit> {

	@Override
	public boolean insert(Produit object) {
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
	public boolean update(Produit object) {
		 Session session=DataIms.getSessionFactory().getCurrentSession();
	        try {
	           
	            session.beginTransaction();
	            session.update(object);
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
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Produit object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Produit get(int id) {
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			Produit obj=(Produit)session.load(Produit.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Produit> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produit> get() {
        List<Produit> objs;
      Session session=DataIms.getSessionFactory().getCurrentSession();
     try {
          session.getTransaction().begin();
          session.clear();
          objs=((List<Produit>)session.createQuery("from Produit").list());
          return objs;
      } catch (Exception e) {
          session.close();
          System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
          return null;
      }
	}

	@Override
	public Produit getByName(String name) {
		System.out.println("DAO Produit "+name);
		Produit object=null;
		try {
			Session session=DataIms.sessionFactory.getCurrentSession();
	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class Produit where obj.typeproduit=:ref");
	        q.setParameter("ref",name);
	        object=(Produit)q.uniqueResult();
	        object=(Produit)session.load(Produit.class,object.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public Produit getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produit> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Produit getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
