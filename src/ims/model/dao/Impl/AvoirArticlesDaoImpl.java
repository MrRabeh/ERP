package ims.model.dao.Impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Avoir_Articles;

public class AvoirArticlesDaoImpl implements ModelDao<Avoir_Articles> {

	@Override
	public boolean insert(Avoir_Articles object) {
		 
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
	public boolean update(Avoir_Articles object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Avoir_Articles object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Avoir_Articles get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Avoir_Articles> getbyObjet(int id) {
		 List<Avoir_Articles> lignes=null;
			Session session=DataIms.sessionFactory.getCurrentSession();
			try {

		        session.beginTransaction();
		        Query q=session.createQuery("select obj from obj in class Avoir_Articles where obj.avoir.id=:id");
		        q.setParameter("id",id);
		        lignes=((List<Avoir_Articles>)q.list());
			} catch (Exception e) {
				session.close();
				e.printStackTrace();
			}
			return lignes;
	}

	@Override
	public List<Avoir_Articles> get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Avoir_Articles getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Avoir_Articles getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Avoir_Articles> getByNames(String... name) {
		return null;
	}

	@Override
	public Avoir_Articles getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
