/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.dao.Impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Utilisateur;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rabeh
 */
@Transactional
public class UtilisateurDaoImpl implements ModelDao<Utilisateur>{

    @Override
    public List<Utilisateur> getbyObjet(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insert(Utilisateur user) {
        
        try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return false;
        }
        
        
    }

    @Override
    public boolean update(Utilisateur user) {
        	try {
			Session session=DataIms.sessionFactory.getCurrentSession();
			session.getTransaction().begin();
			session.update(user);
			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public Utilisateur get(int id) {
		 Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				Utilisateur obj=(Utilisateur)session.load(Utilisateur.class,id);
				return obj;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Utilisateur> get() {
        List<Utilisateur> users;
        Session session=DataIms.getSessionFactory().getCurrentSession();
       try {
            session.getTransaction().begin();
            session.clear();
            users=((List<Utilisateur>)session.createQuery("from Utilisateur").list());
            return users;
        } catch (Exception e) {
            session.close();
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return new ArrayList<Utilisateur>();
        }
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Utilisateur user) {
          try {
		   	Session session=DataIms.sessionFactory.getCurrentSession();
			session.getTransaction().begin();
			session.delete(user);
			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }

	@Override
	public Utilisateur getByName(String name) {
		Utilisateur user=null;
		try {
			Session session=DataIms.sessionFactory.getCurrentSession();
	        session.beginTransaction();
	        Query q=session.createQuery("select U from U in class Utilisateur where U.Login=:login");
	        q.setParameter("login",name);
	        user=(Utilisateur)q.uniqueResult();
	        user=(Utilisateur)session.load(Utilisateur.class,user.getIduser());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Utilisateur getByID(int id) {
		 Session session=DataIms.sessionFactory.getCurrentSession();
			try {
				session.getTransaction().begin();
				Utilisateur obj=(Utilisateur)session.load(Utilisateur.class,id);
				return obj;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Utilisateur> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utilisateur getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
