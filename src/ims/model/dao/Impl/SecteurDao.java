/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.dao.Impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Secteur;

import java.util.List;

import org.hibernate.classic.Session;

/**
 *
 * @author rabeh
*/
public class SecteurDao implements ModelDao<Secteur>{

    @Override
    public List<Secteur> getbyObjet(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insert(Secteur object) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public boolean update(Secteur object) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Secteur object) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public Secteur get(int id) {
          Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			Secteur obj=(Secteur)session.load(Secteur.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Secteur> get() {
        
               try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.clear();
            return ((List<Secteur>)session.createQuery("from Secteur").list());
            
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return null;
        }
    }

	@Override
	public Secteur getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Secteur getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Secteur> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Secteur getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
