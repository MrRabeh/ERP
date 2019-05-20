package ims.model.dao.Impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.CotationOffre;

public class CotationOffreDaoImpl implements ModelDao<CotationOffre> {

	@Override
	public boolean insert(CotationOffre object) {
	    try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR contact IN HIBERNATE->"+e.getMessage());
            return false;
        }
	}

	@Override
	public boolean update(CotationOffre object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(CotationOffre object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CotationOffre get(int id) {
        Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			session.clear();
			CotationOffre obj=(CotationOffre)session.load(CotationOffre.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CotationOffre> getbyObjet(int id) {
		List<CotationOffre> lignes=null;
		try {
			Session session=DataIms.sessionFactory.getCurrentSession();
	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class CotationOffre where obj.offre.id=:id");
	        q.setParameter("id",id);
	        lignes=((List<CotationOffre>)q.list());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lignes;
	}

	@Override
	public List<CotationOffre> get() {
        try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.clear();
            return ((List<CotationOffre>)session.createQuery("from CotationOffre").list());
            
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR CotationOffre IN HIBERNATE->"+e.getMessage());
            return null;
        }
	}

	@Override
	public CotationOffre getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CotationOffre getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CotationOffre> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CotationOffre getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
