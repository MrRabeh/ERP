package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.TypeFacture;

public class TypeFactureDaoImpl implements ModelDao<TypeFacture> {

	@Override
	public boolean insert(TypeFacture object) {
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
	public boolean update(TypeFacture object) {
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
	public boolean delete(TypeFacture object) {
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
	public TypeFacture get(int id) {
		  Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				TypeFacture obj=(TypeFacture)session.load(TypeFacture.class,id);
				return obj;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<TypeFacture> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeFacture> get() {
		try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            return ((List<TypeFacture>)session.createQuery("from TypeFacture").list());
            
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return new ArrayList<TypeFacture>();
        }
	}

	@Override
	public TypeFacture getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeFacture getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeFacture> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeFacture getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
