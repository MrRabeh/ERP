package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.LicenceApplication;
import ims.model.entities.Utilisateur;

public class LicenceAppsDao implements ModelDao<LicenceApplication> {

	@Override
	public boolean insert(LicenceApplication object) {
		   Session session=DataIms.getSessionFactory().getCurrentSession();
	        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	    try {
	           
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
	public boolean update(LicenceApplication object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(LicenceApplication object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LicenceApplication get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LicenceApplication> getbyObjet(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LicenceApplication> get() {
		try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.clear();
            List<LicenceApplication> licences=((List<LicenceApplication>)session.createQuery("select obj from obj in class LicenceApplication order by obj.dategenerer , obj.dateexpiration asc").list());
            return licences;
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return new ArrayList<LicenceApplication>();
        }
	}

	@Override
	public LicenceApplication getByName(String cle) {
		LicenceApplication licence=null;
		try {
			Session session=DataIms.sessionFactory.getCurrentSession();
	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class LicenceApplication where obj.cle=:cle");
	        q.setParameter("cle",cle);
	        licence=(LicenceApplication)q.uniqueResult();
	        licence=(LicenceApplication)session.load(LicenceApplication.class,licence.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return licence;
	}

	@Override
	public LicenceApplication getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LicenceApplication> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LicenceApplication getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
