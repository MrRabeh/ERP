package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.TypeOffre;

public class TypeOffreDaoImpl implements ModelDao<TypeOffre> {

	@Override
	public boolean insert(TypeOffre object) {
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
	public boolean update(TypeOffre object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(TypeOffre object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TypeOffre get(int id) {
		  Session session=DataIms.sessionFactory.getCurrentSession();
				try {
					session.getTransaction().begin();
					TypeOffre obj=(TypeOffre)session.load(TypeOffre.class,id);
					return obj;
				}catch (Exception e) {
					e.printStackTrace();
					return null;
				}
	}

	@Override
	public List<TypeOffre> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeOffre> get() {
		try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.clear();
            return ((List<TypeOffre>)session.createQuery("from TypeOffre").list());
            
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return new ArrayList<TypeOffre>();
        }
	}

	@Override
	public TypeOffre getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeOffre getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeOffre> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeOffre getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
