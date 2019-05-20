package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.ImagesbyTicker;

public class ImagesTickerDaoImpl implements ModelDao<ImagesbyTicker> {

	@Override
	public boolean insert(ImagesbyTicker object) {
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
	public boolean update(ImagesbyTicker object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(ImagesbyTicker object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImagesbyTicker get(int id) {
		 Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				session.clear();
				ImagesbyTicker obj=(ImagesbyTicker)session.load(ImagesbyTicker.class,id);
				return obj;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<ImagesbyTicker> getbyObjet(int id) {
		List<ImagesbyTicker> lignes=null;
		try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.clear();
	        Query q=session.createQuery("select obj from obj in class ImagesbyTicker where obj.ticker.id=:id");
	        q.setParameter("id",id);
	        lignes=((List<ImagesbyTicker>)q.list());
	        return lignes;
        } catch (Exception e) {
            System.err.println("ERROR ImagesbyTicker IN HIBERNATE->"+e.getMessage());
            return new ArrayList<ImagesbyTicker>();
        }
	}

	@Override
	public List<ImagesbyTicker> get() {
		 try {
	            Session session=DataIms.getSessionFactory().getCurrentSession();
	            session.beginTransaction();
	            session.clear();
	            return ((List<ImagesbyTicker>)session.createQuery("from ImagesbyTicker").list());
	            
	        } catch (Exception e) {
	            System.err.println("ERROR ImagesbyTicker IN HIBERNATE->"+e.getMessage());
	            return new ArrayList<ImagesbyTicker>();
	        }
	}

	@Override
	public ImagesbyTicker getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImagesbyTicker getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ImagesbyTicker> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImagesbyTicker getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
