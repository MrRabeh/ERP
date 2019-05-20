package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.SuiviTicker;

public class SuiviTickerDaoImpl implements ModelDao<SuiviTicker> {

	@Override
	public boolean insert(SuiviTicker object) {
        try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            System.out.println("insert stock livraison");
            session.save(object);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return false;
        }
	}

	@Override
	public boolean update(SuiviTicker object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(SuiviTicker object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SuiviTicker get(int id) {
		 Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				session.clear();
				SuiviTicker obj=(SuiviTicker)session.load(SuiviTicker.class,id);
				return obj;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<SuiviTicker> getbyObjet(int id) {
		List<SuiviTicker> lignes=null;
		try {
			Session session=DataIms.sessionFactory.getCurrentSession();
	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class SuiviTicker where obj.ticker.id=:id");
	        q.setParameter("id",id);
	        lignes=((List<SuiviTicker>)q.list());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lignes;
	}

	@Override
	public List<SuiviTicker> get() {
		 try {
	    	    List<SuiviTicker> lignes=null;
	            Session session=DataIms.getSessionFactory().getCurrentSession();
	            session.beginTransaction();
	            session.clear();
		        Query q=session.createQuery("from SuiviTicker");
		        lignes=((List<SuiviTicker>)q.list());
	            return lignes;
	            
	        } catch (Exception e) {
	            System.err.println("ERROR SuiviTicker IN HIBERNATE->"+e.getMessage());
	            return new ArrayList<SuiviTicker>();
	        }
	}

	@Override
	public SuiviTicker getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuiviTicker getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SuiviTicker> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuiviTicker getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
