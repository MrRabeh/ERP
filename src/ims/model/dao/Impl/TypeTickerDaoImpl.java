package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.TypeTicker;

public class TypeTickerDaoImpl implements ModelDao<TypeTicker> {

	@Override
	public boolean insert(TypeTicker object) {
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
	public boolean update(TypeTicker object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(TypeTicker object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TypeTicker get(int id) {
		 Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				TypeTicker obj=(TypeTicker)session.load(TypeTicker.class,id);
				return obj;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<TypeTicker> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeTicker> get() {
		try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.clear();
            return ((List<TypeTicker>)session.createQuery("from TypeTicker").list());
            
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return new ArrayList<TypeTicker>();
        }
	}

	@Override
	public TypeTicker getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeTicker getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeTicker> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeTicker getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
