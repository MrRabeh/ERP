package ims.model.dao.Impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Contact;

public class ContactDaoImpl implements ModelDao<Contact> {

	@Override
	public boolean insert(Contact object) {
        Session session=DataIms.getSessionFactory().getCurrentSession();
	    try {
 
            session.beginTransaction();
            object.setCodeclient((Integer) session.save(object));
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            session.close();
            System.err.println("ERRORRRRRRRRRRRRRRR contact IN HIBERNATE->"+e.getMessage());
            return false;
        }
	}

	@Override
	public boolean update(Contact object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Contact object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Contact get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contact> getbyObjet(int id) {
		List<Contact> lignes=null;
		Session session=DataIms.sessionFactory.getCurrentSession();
		try {

	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class Contact where obj.client.idclient=:id");
	        q.setParameter("id",id);
	        lignes=q.list();
		} catch (Exception e) {
			session.close();
			e.printStackTrace();
		}
		return lignes;
	}

	@Override
	public List<Contact> get() {
        try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.clear();
            return ((List<Contact>)session.createQuery("from Contact").list());
            
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR contact IN HIBERNATE->"+e.getMessage());
            return null;
        }
	}

	@Override
	public Contact getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contact getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contact> getByNames(String... name) {
		List<Contact> lignes=null;
		try {
			Session session=DataIms.sessionFactory.getCurrentSession();
	        session.beginTransaction();
	        Query q=session.createQuery("select obj from obj in class Contact where obj.fournisseur!=null");
	        lignes=((List<Contact>)q.list());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lignes;
	}

	@Override
	public Contact getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
