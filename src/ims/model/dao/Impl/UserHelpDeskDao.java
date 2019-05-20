package ims.model.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ims.model.dao.ModelDao;
import ims.model.entities.Utilisateur;
import ims.model.entities.UtilisateurHelpDesk;

public class UserHelpDeskDao implements ModelDao<UtilisateurHelpDesk> {

	@Override
	public boolean insert(UtilisateurHelpDesk object) {
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
	public boolean update(UtilisateurHelpDesk object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(UtilisateurHelpDesk object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UtilisateurHelpDesk get(int id) {
		 Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				UtilisateurHelpDesk obj=(UtilisateurHelpDesk)session.load(UtilisateurHelpDesk.class,id);
				return obj;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<UtilisateurHelpDesk> getbyObjet(int id) {
		List<UtilisateurHelpDesk> users=null;
		try {
			Session session=DataIms.sessionFactory.getCurrentSession();
	        session.beginTransaction();
	        Query q=session.createQuery("select U from U in class UtilisateurHelpDesk where U.client.idclient=:id");
	        q.setParameter("id",id);
	        users=q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public List<UtilisateurHelpDesk> get() {
		  List<UtilisateurHelpDesk> users;
	        Session session=DataIms.getSessionFactory().getCurrentSession();
	       try {
	            session.getTransaction().begin();
	            session.clear();
	            users=((List<UtilisateurHelpDesk>)session.createQuery("from UtilisateurHelpDesk").list());
	            return users;
	        } catch (Exception e) {
	            session.close();
	            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
	            return new ArrayList<UtilisateurHelpDesk>();
	        }
	}

	@Override
	public UtilisateurHelpDesk getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UtilisateurHelpDesk getByID(int id) {
		 Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				UtilisateurHelpDesk obj=(UtilisateurHelpDesk)session.load(UtilisateurHelpDesk.class,id);
				return obj;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<UtilisateurHelpDesk> getByNames(String... name) {
		return null;
	}

	@Override
	public UtilisateurHelpDesk getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}

}
