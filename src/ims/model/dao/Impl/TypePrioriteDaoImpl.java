package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.TypePriorite;

public class TypePrioriteDaoImpl implements ModelDao<TypePriorite> {

	@Override
	public boolean insert(TypePriorite object) {
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
	public boolean update(TypePriorite object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(TypePriorite object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TypePriorite get(int id) {
		 Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				TypePriorite obj=(TypePriorite)session.load(TypePriorite.class,id);
				return obj;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<TypePriorite> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypePriorite> get() {
		try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.clear();
            return ((List<TypePriorite>)session.createQuery("from TypePriorite").list());
            
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return new ArrayList<TypePriorite>();
        }
	}

	@Override
	public TypePriorite getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypePriorite getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypePriorite> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypePriorite getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
