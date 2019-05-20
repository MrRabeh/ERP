/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ims.model.dao.Impl;

import ims.model.dao.ModelDao;
import ims.model.entities.Departement;

import java.util.List;

import org.hibernate.classic.Session;

/**
 *
 * @author rabeh
 */
public class DepartementDaoImpl implements ModelDao<Departement>{

    @Override
    public List<Departement> getbyObjet(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
     @Override
    public boolean insert(Departement object) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    Session session=DataIms.getSessionFactory().getCurrentSession();
        try {
           
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
            
            return true;
        } catch (Exception e) {
            session.close();
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Departement object) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     try {
			Session session=DataIms.sessionFactory.getCurrentSession();
			session.getTransaction().begin();
			session.update(object);
			session.getTransaction().commit();
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
    public boolean delete(Departement object) {
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
    public Departement get(int id) {
       Session session=DataIms.sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			Departement obj=(Departement)session.load(Departement.class,id);
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Departement> get() {
           try {
            Session session=DataIms.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.clear();
            return ((List<Departement>)session.createQuery("from Departement").list());
            
        } catch (Exception e) {
            System.err.println("ERRORRRRRRRRRRRRRRR IN HIBERNATE->"+e.getMessage());
            return null;
        }
    }




	@Override
	public Departement getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Departement getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}







	@Override
	public Departement getByIids(int... id) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<Departement> getByNames(String... name) {
		// TODO Auto-generated method stub
		return null;
	}
}
