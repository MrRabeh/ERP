package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Rubrique;

public class RubriqueDaoImpl implements ModelDao<Rubrique> {

	@Override
	public boolean insert(Rubrique object) {
		   Session session=DataIms.getSessionFactory().getCurrentSession();
        try {
         
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.err.println("ERRORRRRRR ARTICLE IN HIBERNATE->"+e.getMessage());
            session.close();
            return false;
        }
	}

	@Override
	public boolean update(Rubrique object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Rubrique object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Rubrique get(int id) {
		  Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				Rubrique obj=(Rubrique)session.load(Rubrique.class,id);
				return obj;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Rubrique> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rubrique> get() {
	       try {
	    	    List<Rubrique> lignes=null;
	            Session session=DataIms.getSessionFactory().getCurrentSession();
	            session.beginTransaction();
	            session.clear();
		        Query q=session.createQuery("from Rubrique");
		        lignes=((List<Rubrique>)q.list());
	            return lignes;
	            
	        } catch (Exception e) {
	            System.err.println("ERRORRRRRRRRRRRRRRR Rubrique IN HIBERNATE->"+e.getMessage());
	            return new ArrayList<Rubrique>();
	        }
	}

	@Override
	public Rubrique getByName(String name) {
		Rubrique object=null;
		try {
			Session session=DataIms.sessionFactory.getCurrentSession();
	        session.beginTransaction();
	        System.out.println("PREPARE DAO ");
	        Query q=session.createQuery("select obj from obj in class Rubrique where obj.ref=:ref");
	        q.setParameter("ref",name);
	        object=(Rubrique)q.uniqueResult();
	        System.out.println("===>"+object.getId());
	        session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public Rubrique getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rubrique> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rubrique getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
